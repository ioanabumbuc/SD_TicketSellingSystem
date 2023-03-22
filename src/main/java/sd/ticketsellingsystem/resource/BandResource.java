package sd.ticketsellingsystem.resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sd.ticketsellingsystem.dto.BandDto;
import sd.ticketsellingsystem.model.Band;
import sd.ticketsellingsystem.model.Concert;
import sd.ticketsellingsystem.service.BandService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/bands")
@CrossOrigin
public class BandResource {
    private final BandService bandService;

    public BandResource(BandService bandService) {
        this.bandService = bandService;
    }

    @GetMapping("/all")
    @ResponseBody
    public ResponseEntity<List<Band>> getAllBands() {
        List<Band> bands = bandService.findAllBands();
//        List<BandDto> bandsResponse = new ArrayList<>();
//        for (Band b : bands) {
//            System.out.println(b.toString());
//            BandDto bandDto = new BandDto();
//            bandDto.setBandName(b.getBandName());
//            bandDto.setId(b.getId());
//            bandDto.setGenre(b.getGenre());
////            for (Concert c : b.getConcerts()) {
////                bandDto.addConcertId(c.getId());
////            }
//            bandsResponse.add(bandDto);
//        }
        return new ResponseEntity<>(bands, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BandDto> getBandById(@PathVariable Long id) {
        Band band = bandService.findById(id);
        if(band == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        BandDto bandDto = new BandDto();
        bandDto.setId(band.getId());
        bandDto.setBandName(band.getBandName());
        bandDto.setGenre(band.getGenre());
        bandDto.setConcertIds(band.getConcerts().stream().map(Concert::getId).toList());
        return new ResponseEntity<>(bandDto, HttpStatus.OK);
    }

    @GetMapping("/{id}/concerts")
    public ResponseEntity<List<Concert>> getBandConcerts(@PathVariable Long id) {
        Band band = bandService.findById(id);
        if (band != null) {
            return new ResponseEntity<>( band.getConcerts(),HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }


    @PostMapping("/add")
    public ResponseEntity<Band> addBand(@RequestBody Band band) {
        Band savedBand = bandService.addBand(band);
        if(savedBand != null){
            return new ResponseEntity<>(savedBand, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(null,HttpStatus.CONFLICT);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Band> updateBand(@RequestBody Band band, @PathVariable Long id) {
        band.setId(id);
        Band updateBand = bandService.updateBand(band);
        if(updateBand != null) {
            return new ResponseEntity<>(updateBand, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBand(@PathVariable("id") Long id) {
        bandService.deleteBandById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
