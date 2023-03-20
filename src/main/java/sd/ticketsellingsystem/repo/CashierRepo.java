package sd.ticketsellingsystem.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import sd.ticketsellingsystem.model.Cashier;

public interface CashierRepo extends JpaRepository<Cashier, Long>{

    Cashier findCashierByUsername(String username);

    void deleteCashierById(Long id);

}
