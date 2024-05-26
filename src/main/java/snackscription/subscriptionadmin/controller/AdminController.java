package snackscription.subscriptionadmin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import snackscription.subscriptionadmin.dto.AdminDTO;
import snackscription.subscriptionadmin.model.AdminSubscription;
import snackscription.subscriptionadmin.service.AdminService;
import snackscription.subscriptionadmin.utils.JWTUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:3000")
public class AdminController {
    private final AdminService adminService;
    private final JWTUtils jwtUtils;

    public AdminController(AdminService adminService, JWTUtils jwtUtils) {
        this.adminService = adminService;
        this.jwtUtils = jwtUtils;
    }

    private void validateToken(String token) throws IllegalAccessException {
        String jwt = token.replace("Bearer ", "");
        if (!jwtUtils.isTokenValid(jwt)) {
            throw new IllegalAccessException("You have no permission.");
        }
    }

    @GetMapping("")
    public ResponseEntity<String> home() {
        return ResponseEntity.ok("Snackscription - Admin Subscription Management API");
    }

    @PostMapping("/create")
    public CompletableFuture<ResponseEntity<AdminSubscription>> create(@RequestHeader(value="Authorization") String token, @RequestBody AdminDTO adminDTO)
            throws IllegalAccessException {
        validateToken(token);
        return adminService.create(adminDTO).thenApply(ResponseEntity::ok)
                .exceptionally(ex -> ResponseEntity.badRequest().build());

    }

    @GetMapping("/list")
    public CompletableFuture<ResponseEntity<List<AdminDTO>>> findAll(@RequestHeader(value="Authorization") String token) throws IllegalAccessException {
        validateToken(token);
        return adminService.findAll().thenApply(ResponseEntity::ok);
    }

    @GetMapping("/{subscriptionId}")
    public CompletableFuture<ResponseEntity<AdminDTO>> findById(@RequestHeader(value="Authorization") String token, @PathVariable String subscriptionId) throws IllegalAccessException {
        validateToken(token);
        try {
            UUID.fromString(subscriptionId);
        } catch (IllegalArgumentException e) {
            return CompletableFuture.completedFuture(ResponseEntity.badRequest().build());
        }
        return adminService.findById(subscriptionId).thenApply(ResponseEntity::ok)
                .exceptionally(ex -> ResponseEntity.notFound().build());
    }

    @PutMapping("/update")
    public CompletableFuture<ResponseEntity<AdminSubscription>> update(@RequestHeader(value="Authorization") String token, @RequestBody AdminDTO adminDTO) throws IllegalAccessException {
        validateToken(token);
        if (adminDTO.getSubscriptionId() == null) {
            return CompletableFuture.completedFuture(ResponseEntity.badRequest().build());
        }

        CompletableFuture<AdminSubscription> updatedSubscription = adminService.update(adminDTO);
        if(updatedSubscription == null) {
            return CompletableFuture.completedFuture(ResponseEntity.notFound().build());
        }

        return updatedSubscription.thenApply(ResponseEntity::ok)
                .exceptionally(ex -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{subscriptionId}")
    public CompletableFuture<ResponseEntity<String>> delete(@RequestHeader(value="Authorization") String token, @PathVariable String subscriptionId) throws IllegalAccessException{
        validateToken(token);
        try {
            UUID.fromString(subscriptionId);
        } catch (IllegalArgumentException e) {
            return CompletableFuture.completedFuture(ResponseEntity.badRequest().build());
        }
        try {
            return adminService.delete(subscriptionId).thenApply(deleted -> ResponseEntity.ok("DELETE SUCCESS"))
                    .exceptionally(ex -> ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            return CompletableFuture.completedFuture(ResponseEntity.notFound().build());
        }
    }
}