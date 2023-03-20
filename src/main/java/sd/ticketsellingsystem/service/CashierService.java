package sd.ticketsellingsystem.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sd.ticketsellingsystem.model.Cashier;
import sd.ticketsellingsystem.model.Concert;
import sd.ticketsellingsystem.model.Ticket;
import sd.ticketsellingsystem.repo.CashierRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CashierService {
    private final CashierRepo cashierRepo;
    private final TicketService ticketService;
    private final ConcertService concertService;

    @Autowired
    public CashierService(CashierRepo cashierRepo, TicketService ticketService, ConcertService concertService) {
        this.cashierRepo = cashierRepo;
        this.ticketService = ticketService;
        this.concertService = concertService;
    }

    public Cashier addCashier(Cashier cashier) {
        return cashierRepo.save(cashier);
    }

    public List<Cashier> findAllCashiers() {
        return cashierRepo.findAll();
    }

    public Cashier findCashierById(Long id) {
        return cashierRepo.findById(id).orElse(null);
    }

    public Cashier updateCashier(Cashier cashier) {
        return cashierRepo.save(cashier);
    }

    public void deleteCashierById(Long id) {
        cashierRepo.deleteCashierById(id);
    }

    public Cashier findCashierByCredentials(String username, String password) {
        Cashier foundCashier = cashierRepo.findCashierByUsername(username);
        if (foundCashier != null && foundCashier.checkPassword(password)) {
            return foundCashier;
        }
        return null;
    }

    public Cashier findCashierByUsername(String username) {
        return cashierRepo.findCashierByUsername(username);
    }

    public List<Ticket> sellTickets(Concert concert, int quantity, String[] buyerNames) {
        if (concert.getAvailableTickets() < quantity) {
            return null;
        }
        List<Ticket> tickets = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            Ticket t = new Ticket();
            t.setConcert(concert);
            t.setBuyerName(buyerNames[i]);
            tickets.add(t);
        }
        concert.setAvailableTickets(concert.getAvailableTickets() - quantity);
        concertService.updateConcert(concert);
        return ticketService.addTickets(tickets);
    }
}
