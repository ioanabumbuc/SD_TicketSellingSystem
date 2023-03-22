package sd.ticketsellingsystem.resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sd.ticketsellingsystem.model.Admin;
import sd.ticketsellingsystem.service.AdminService;

import java.util.List;

@RestController
@RequestMapping("/users/admin")
@CrossOrigin
public class AdminResource {

    private final AdminService adminService;

    public AdminResource(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/login")
    public ResponseEntity<Admin> getCashierByCredentials(@RequestBody Admin admin) {
        Admin foundAdmin = adminService.findAdminByCredentials(admin.getUsername(), admin.getPassword());
        if (foundAdmin != null) {
            admin.prePersist();
            return new ResponseEntity<>(foundAdmin, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Admin>> getAllAdmins() {
        List<Admin> admins = adminService.findAllAdmins();
        return new ResponseEntity<>(admins, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Admin> addAdmin(@RequestBody Admin admin) {
        if (adminService.findAdminByUsername(admin.getUsername()) != null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        Admin newAdmin = adminService.addAdmin(admin);
        return new ResponseEntity<>(newAdmin, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Admin> updateAdmin(@RequestBody Admin admin) {
        Admin updatedAdmin = adminService.updateAdmin(admin);
        return new ResponseEntity<>(updatedAdmin, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAdmin(@PathVariable("id") Long id) {
        adminService.deleteAdminById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
