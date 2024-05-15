package snackscription.subscriptionadmin.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import snackscription.subscriptionadmin.dto.AdminDTO;
import snackscription.subscriptionadmin.model.AdminSubscription;
import snackscription.subscriptionadmin.service.AdminService;

import java.util.List;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:3000")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/create")
    public ResponseEntity<AdminSubscription> create(@RequestBody AdminDTO adminDTO) {
        AdminSubscription adminSubscription = adminService.create(adminDTO);
        return new ResponseEntity<>(adminSubscription, HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public ResponseEntity<List<AdminDTO>> findAll() {
        List<AdminDTO> adminDTOList = adminService.findAll();
        return new ResponseEntity<>(adminDTOList, HttpStatus.OK);
    }

    @GetMapping("/{subscriptionId}")
    public ResponseEntity<AdminDTO> findById(@PathVariable String subscriptionId) {
        AdminDTO adminDTO = adminService.findById(subscriptionId);
        return new ResponseEntity<>(adminDTO, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<AdminSubscription> update(@RequestBody AdminDTO adminDTO) {
        AdminSubscription adminSubscription = adminService.update(adminDTO);
        return new ResponseEntity<>(adminSubscription, HttpStatus.OK);
    }

    @DeleteMapping("/{subscriptionId}")
    public ResponseEntity<Void> delete(@PathVariable String subscriptionId) {
        adminService.delete(subscriptionId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}