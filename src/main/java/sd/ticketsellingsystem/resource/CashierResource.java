package sd.ticketsellingsystem.resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sd.ticketsellingsystem.dto.TicketDto;
import sd.ticketsellingsystem.model.Cashier;
import sd.ticketsellingsystem.model.Concert;
import sd.ticketsellingsystem.model.Ticket;
import sd.ticketsellingsystem.service.CashierService;
import sd.ticketsellingsystem.service.ConcertService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users/cashier")
@CrossOrigin
public class CashierResource {

    private final CashierService cashierService;
    private final ConcertService concertService;

    public CashierResource(CashierService cashierService, ConcertService concertService) {
        this.cashierService = cashierService;
        this.concertService = concertService;
    }

    @PostMapping("/login")
    public ResponseEntity<Cashier> getCashierByCredentials(@RequestBody Cashier cashier) {
        Cashier foundCashier = cashierService.findCashierByCredentials(cashier.getUsername(), cashier.getPassword());
        if (foundCashier != null) {
            return new ResponseEntity<>(cashier, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Cashier>> getAllCashiers() {
        List<Cashier> cashiers = cashierService.findAllCashiers();
        return new ResponseEntity<>(cashiers, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Cashier> addCashier(@RequestBody Cashier cashier) {
        if (cashierService.findCashierByUsername(cashier.getUsername()) != null) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }
        Cashier newCashier = cashierService.addCashier(cashier);
        return new ResponseEntity<>(newCashier, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Cashier> updateCashier(@RequestBody Cashier cashier) {
        Cashier cashier1 = cashierService.findCashierByUsername(cashier.getUsername());
        if(cashier1 == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        cashier1.setPassword(cashier.getPassword());
        cashier1.prePersist();
        Cashier newCashier = cashierService.updateCashier(cashier1);
        return new ResponseEntity<>(newCashier, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCashier(@PathVariable("id") Long id) {
        cashierService.deleteCashierById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/sell/concertId={id}&quantity={quantity}")
    public ResponseEntity<?> sellTickets(@PathVariable("id") Long id, @PathVariable("quantity") int quantity,
                                         @RequestBody String[] buyerNames) {
        if(buyerNames.length != quantity){
            return new ResponseEntity<>("The number of buyers you provided doesn't match the number of tickets", HttpStatus.BAD_REQUEST);
        }
        Concert concert = concertService.findConcertById(id);
        if(concert == null){
            return new ResponseEntity<>("Concert not found", HttpStatus.NOT_FOUND);
        }
        List<Ticket> tickets = cashierService.sellTickets(concert, quantity, buyerNames);
        if (tickets == null) {
            return new ResponseEntity<>("Available tickets exceeded", HttpStatus.BAD_REQUEST);
        }
        List<TicketDto> ticketDtos = new ArrayList<>();
        for(Ticket t: tickets){
            TicketDto ticketDto = new TicketDto();
            ticketDto.setId(t.getId());
            ticketDto.setTicketCode(t.getTicketCode());
            ticketDto.setBuyerName(t.getBuyerName());
            ticketDto.setConcertId(t.getConcert().getId());
            ticketDtos.add(ticketDto);
        }
        return new ResponseEntity<>(ticketDtos, HttpStatus.CREATED);
    }
}
