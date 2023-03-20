package sd.ticketsellingsystem.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import sd.ticketsellingsystem.model.Ticket;

import java.util.List;

public interface TicketRepo extends JpaRepository<Ticket,Long>{

    void deleteTicketById(Long id);

    List<Ticket> findTicketsByConcert_Id(Long id);
}
