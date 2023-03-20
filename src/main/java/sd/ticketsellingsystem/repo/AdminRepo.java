package sd.ticketsellingsystem.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import sd.ticketsellingsystem.model.Admin;

public interface AdminRepo extends JpaRepository<Admin, Long>{

    Admin findAdminByUsername(String username);

    void deleteAdminById(Long id);

}
