package sd.ticketsellingsystem.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sd.ticketsellingsystem.model.Band;
import sd.ticketsellingsystem.repo.BandRepo;

import java.util.List;

@Service
@Transactional
public class BandService {
    private final BandRepo bandRepo;

    @Autowired
    public BandService(BandRepo bandRepo) {
        this.bandRepo = bandRepo;
    }

    public List<Band> findAllBands() {
        return bandRepo.findAll();
    }

    public Band findById(Long id) {
        return bandRepo.findById(id).orElse(null);
    }

    public List<Band> findBandsByConcertId(Long concertId) {
        return bandRepo.findAllByConcerts_Id(concertId);
    }

    public List<Band> findAllById(List<Long> bandIds){
        return bandRepo.findAllById(bandIds);
    }
    public Band addBand(Band band) {
        if (bandRepo.existsBandByBandName(band.getBandName())) {
            return null;
        }
        return bandRepo.save(band);
    }

    public Band updateBand(Band band) {
        if (bandRepo.existsById(band.getId())) {
            return bandRepo.save(band);
        }
        return null;
    }

    public void deleteBandById(Long id) {
        bandRepo.deleteBandById(id);
    }
}
