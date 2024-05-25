package snackscription.subscriptionadmin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import snackscription.subscriptionadmin.dto.AdminDTO;
import snackscription.subscriptionadmin.model.AdminSubscription;
import snackscription.subscriptionadmin.service.AdminService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:3000")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/create")
    public CompletableFuture<ResponseEntity<AdminSubscription>> create(@RequestBody AdminDTO adminDTO) {
        return adminService.create(adminDTO).thenApply(ResponseEntity::ok)
                .exceptionally(ex -> ResponseEntity.badRequest().build());

    }

    @GetMapping("/list")
    public CompletableFuture<ResponseEntity<List<AdminDTO>>> findAll() {
        return adminService.findAll().thenApply(ResponseEntity::ok);
    }

    @GetMapping("/{subscriptionId}")
    public CompletableFuture<ResponseEntity<AdminDTO>> findById(@PathVariable String subscriptionId) {
        try {
            UUID.fromString(subscriptionId);
        } catch (IllegalArgumentException e) {
            return CompletableFuture.completedFuture(ResponseEntity.badRequest().build());
        }
        return adminService.findById(subscriptionId).thenApply(ResponseEntity::ok)
                .exceptionally(ex -> ResponseEntity.notFound().build());
    }

    @PutMapping("/update")
    public CompletableFuture<ResponseEntity<AdminSubscription>> update(@RequestBody AdminDTO adminDTO) {
        if (adminDTO.getSubscriptionId() == null) {
            return CompletableFuture.completedFuture(ResponseEntity.badRequest().build());
        }

        return adminService.update(adminDTO).thenApply(ResponseEntity::ok)
                .exceptionally(ex -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{subscriptionId}")
    public CompletableFuture<ResponseEntity<String>> delete(@PathVariable String subscriptionId) {
        try {
            UUID.fromString(subscriptionId);
        } catch (IllegalArgumentException e) {
            return CompletableFuture.completedFuture(ResponseEntity.badRequest().build());
        }
        return adminService.delete(subscriptionId).thenApply(deleted -> ResponseEntity.ok("DELETE SUCCESS"))
                .exceptionally(ex -> ResponseEntity.notFound().build());
    }
}