package sd.ticketsellingsystem.resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sd.ticketsellingsystem.dto.TicketDto;
import sd.ticketsellingsystem.model.Concert;
import sd.ticketsellingsystem.model.Ticket;
import sd.ticketsellingsystem.service.ConcertService;
import sd.ticketsellingsystem.service.Factory;
import sd.ticketsellingsystem.service.TicketService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/tickets")
@CrossOrigin
public class TicketResource {
    private final TicketService ticketService;
    private final ConcertService concertService;
    private final Factory factory;

    public TicketResource(TicketService ticketService, ConcertService concertService, Factory factory) {
        this.ticketService = ticketService;
        this.concertService = concertService;
        this.factory = factory;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Ticket>> getAllTickets() {
        List<Ticket> tickets = ticketService.findAllTickets();
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }

    @GetMapping("/concert={concertId}")
    public ResponseEntity<List<TicketDto>> getTicketsByConcertId(@PathVariable("concertId") Long concertId) {
        List<Ticket> tickets = ticketService.findTicketsByConcertId(concertId);
        List<TicketDto> ticketDtos = new ArrayList<>();
        for (Ticket t : tickets) {
            TicketDto ticketDto = new TicketDto();
            ticketDto.setId(concertId);
            ticketDto.setTicketCode(t.getTicketCode());
            ticketDto.setBuyerName(t.getBuyerName());
            ticketDto.setConcertId(t.getId());
            ticketDtos.add(ticketDto);
        }
        return new ResponseEntity<>(ticketDtos, HttpStatus.OK);
    }

    @GetMapping("/concert={concertId}/export")
    public ResponseEntity<List<TicketDto>> exportTicketsByConcertId(@PathVariable("concertId") Long concertId) throws IOException {
        List<Ticket> tickets = ticketService.findTicketsByConcertId(concertId);
        List<TicketDto> ticketDtos = new ArrayList<>();
        for (Ticket t : tickets) {
            TicketDto ticketDto = new TicketDto();
            ticketDto.setId(concertId);
            ticketDto.setTicketCode(t.getTicketCode());
            ticketDto.setBuyerName(t.getBuyerName());
            ticketDto.setConcertId(t.getId());
            ticketDtos.add(ticketDto);
        }

        factory.exportTickets(ticketDtos, concertId);

        return new ResponseEntity<>(ticketDtos, HttpStatus.OK);
    }


    @PostMapping("/add")
    public ResponseEntity<Ticket> addTicket(@RequestBody TicketDto ticketDto) {
        Ticket ticket = new Ticket();
        Concert concert = concertService.findConcertById(ticketDto.getConcertId());
        if (concert == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        ticket.setBuyerName(ticketDto.getBuyerName());
        ticket.setConcert(concert);
        Ticket savedTicket = ticketService.addTicket(ticket);
        return new ResponseEntity<>(savedTicket, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteTicket(@PathVariable("id") Long id) {
        ticketService.deleteTicketById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}