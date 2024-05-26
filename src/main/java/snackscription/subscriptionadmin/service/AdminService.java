package snackscription.subscriptionadmin.service;

import snackscription.subscriptionadmin.dto.AdminDTO;
import snackscription.subscriptionadmin.model.AdminSubscription;

import java.util.concurrent.CompletableFuture;

import java.util.List;

public interface AdminService {
    CompletableFuture<AdminSubscription> create(AdminDTO adminDTO);
    CompletableFuture<List<AdminDTO>> findAll();
    CompletableFuture<AdminDTO> findById(String subscriptionId);
    CompletableFuture<AdminSubscription> update(AdminDTO adminDTO);
    CompletableFuture<Void> delete(String subscriptionId);
}