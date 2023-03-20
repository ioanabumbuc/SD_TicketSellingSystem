package sd.ticketsellingsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sd.ticketsellingsystem.model.Admin;
import sd.ticketsellingsystem.model.Cashier;
import sd.ticketsellingsystem.model.Concert;
import sd.ticketsellingsystem.repo.AdminRepo;

import java.util.List;

@Service
public class AdminService {

    private final AdminRepo adminRepo;

    @Autowired
    public AdminService(AdminRepo adminRepo) {
        this.adminRepo = adminRepo;
    }

    public Admin findAdminByCredentials(String username, String password) {
        Admin admin = adminRepo.findAdminByUsername(username);
        if (admin != null && admin.checkPassword(password)) {
            return admin;
        }
        return null;
    }

    public Admin findAdminByUsername(String username) {
        return adminRepo.findAdminByUsername(username);
    }

    public Admin addAdmin(Admin admin) {
        return adminRepo.save(admin);
    }

    public List<Admin> findAllAdmins() {
        return adminRepo.findAll();
    }

    public void deleteAdminById(Long id) {
        adminRepo.deleteAdminById(id);
    }

    public Admin updateAdmin(Admin admin) {
        return adminRepo.save(admin);
    }

}
