package sd.ticketsellingsystem.resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sd.ticketsellingsystem.dto.ConcertDto;
import sd.ticketsellingsystem.model.Band;
import sd.ticketsellingsystem.model.Concert;
import sd.ticketsellingsystem.service.BandService;
import sd.ticketsellingsystem.service.ConcertService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/concerts")
@CrossOrigin
public class ConcertResource {
    private final ConcertService concertService;
    private final BandService bandService;

    public ConcertResource(ConcertService concertService, BandService bandService) {
        this.concertService = concertService;
        this.bandService = bandService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Concert>> getAllConcerts() {
        List<Concert> concerts = concertService.findAllConcerts();
        return new ResponseEntity<>(concerts, HttpStatus.OK);
    }

    @GetMapping("/{concertId}")
    public ResponseEntity<Concert> getBandsByConcertId(@PathVariable("concertId") Long id) {
        Concert concert = concertService.findConcertById(id);
        if (concert != null) {
            return new ResponseEntity<>(concert, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{concertId}/bands")
    public ResponseEntity<List<Band>> getConcertById(@PathVariable("concertId") Long id) {
        Concert concert = concertService.findConcertById(id);
        if (concert != null) {
            List<Band> bands = bandService.findBandsByConcertId(id);
            return new ResponseEntity<>(bands, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PostMapping("/add")
    public ResponseEntity<ConcertDto> addConcert(@RequestBody ConcertDto concertDto) {
        Concert concert = new Concert();
        concert.setId(concertDto.getId());
        concert.setTitle(concertDto.getTitle());
        concert.setAvailableTickets(concertDto.getAvailableTickets());
        concert.setConcertDate(concertDto.getConcertDate());
        if (!concertDto.getBandIds().isEmpty()) {
            List<Band> bands = concertDto.getBandIds().stream().map(bandService::findById).toList();
            concert.setBands(bands);
        }
        Concert newConcert = concertService.addConcert(concert);
        if (newConcert == null) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }
        concertDto.setId(newConcert.getId());
        return new ResponseEntity<>(concertDto, HttpStatus.CREATED);
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<Concert> updateConcert(@RequestBody ConcertDto concertDto, @PathVariable Long id) {
        Concert concert = concertService.findConcertById(id);
        concert.setTitle(concertDto.getTitle());
        concert.setAvailableTickets(concertDto.getAvailableTickets());
        concert.setConcertDate(concertDto.getConcertDate());
        if (!concertDto.getBandIds().isEmpty()) {
            List<Long> bandIds = new ArrayList<>(concertDto.getBandIds());
            List<Band> bands = bandService.findAllById(bandIds);
            concert.setBands(bands);
        }
        Concert updateConcert = concertService.updateConcert(concert);
        return new ResponseEntity<>(updateConcert, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteConcert(@PathVariable("id") Long id) {
        concertService.deleteConcertById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
