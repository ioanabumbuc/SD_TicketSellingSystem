package sd.ticketsellingsystem.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sd.ticketsellingsystem.model.Concert;
import sd.ticketsellingsystem.repo.ConcertRepo;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ConcertService {
    private final ConcertRepo concertRepo;

    @Autowired
    public ConcertService(ConcertRepo concertRepo) {
        this.concertRepo = concertRepo;
    }

    public List<Concert> findAllConcerts() {
        return concertRepo.findAll();
    }

    public Concert findConcertById(Long id){
        return concertRepo.findById(id).orElse(null);
    }

    public Concert addConcert(Concert concert) {
        if(concertRepo.existsByTitle(concert.getTitle())){
            return null;
        }
        return concertRepo.save(concert);
    }

    public Concert updateConcert(Concert concert) {
        if(concertRepo.existsById(concert.getId())){
            return concertRepo.save(concert);
        }
        return null;
    }

    public void deleteConcertById(Long id) {
        concertRepo.deleteConcertById(id);
    }

}
