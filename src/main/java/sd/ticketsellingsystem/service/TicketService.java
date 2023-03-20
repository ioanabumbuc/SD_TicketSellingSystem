package sd.ticketsellingsystem.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sd.ticketsellingsystem.model.Ticket;
import sd.ticketsellingsystem.repo.TicketRepo;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class TicketService {
    private final TicketRepo ticketRepo;

    @Autowired
    public TicketService(TicketRepo ticketRepo) {
        this.ticketRepo = ticketRepo;
    }

    public List<Ticket> findAllTickets() {
        return ticketRepo.findAll();
    }

    public List<Ticket> findTicketsByConcertId(Long id) {
        return ticketRepo.findTicketsByConcert_Id(id);
    }

    public Ticket addTicket(Ticket ticket) {
        ticket.setTicketCode(UUID.randomUUID().toString());
        return ticketRepo.save(ticket);
    }

    public List<Ticket> addTickets(List<Ticket> tickets) {
        for (Ticket t : tickets) {
            t.setTicketCode(UUID.randomUUID().toString());
        }
        return ticketRepo.saveAll(tickets);
    }

    public void deleteTicketById(Long id) {
        ticketRepo.deleteTicketById(id);
    }

}
