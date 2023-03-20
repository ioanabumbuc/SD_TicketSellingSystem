package sd.ticketsellingsystem.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import sd.ticketsellingsystem.model.Band;
import sd.ticketsellingsystem.model.Concert;

import java.util.List;

public interface ConcertRepo extends JpaRepository<Concert, Long> {

    boolean existsByTitle(String title);

    void deleteConcertById(Long id);
}
