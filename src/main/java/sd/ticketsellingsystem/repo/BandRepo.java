package sd.ticketsellingsystem.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import sd.ticketsellingsystem.model.Band;

import java.util.List;

public interface BandRepo extends JpaRepository<Band, Long> {

    List<Band> findAllByConcerts_Id(Long concertId);

    boolean existsBandByBandName(String bandName);

    void deleteBandById(Long id);

    void deleteById(Long id);
}
