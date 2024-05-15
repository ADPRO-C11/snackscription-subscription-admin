package snackscription.subscriptionadmin.service;

import snackscription.subscriptionadmin.model.AdminSubscription;
import snackscription.subscriptionadmin.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import snackscription.subscriptionadmin.dto.AdminDTO;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import snackscription.subscriptionadmin.dto.DTOMapper;

@Service
public class AdminServiceImpl implements AdminService{

    @Autowired
    private AdminRepository adminRepository;

    @Override
    @Async
    public CompletableFuture<AdminSubscription> create(AdminDTO adminDTO) {
        AdminSubscription adminSubscription = DTOMapper.convertDTOtoModel(adminDTO);
        return CompletableFuture.completedFuture(adminRepository.create(adminSubscription));
    }

    @Override
    @Async
    public CompletableFuture<List<AdminDTO>> findAll() {
        return CompletableFuture.completedFuture(adminRepository.findAll()
                .stream()
                .map(DTOMapper::convertModelToDto)
                .collect(Collectors.toList()));
    }

    @Override
    @Async
    public CompletableFuture<Optional<AdminDTO>> findById(String subscriptionId) {
        if(subscriptionId == null || subscriptionId.isBlank()){
            throw new IllegalArgumentException("ID cannot be null or empty");
        }

        AdminSubscription adminSubscription = adminRepository.findById(subscriptionId)
                .orElseThrow(() -> new IllegalArgumentException("Subscription not found"));

        return CompletableFuture.completedFuture(Optional.of(DTOMapper.convertModelToDto(adminSubscription)));
    }

    @Override
    @Async
    public CompletableFuture<AdminSubscription> update(AdminDTO adminDTO) {
        if(adminDTO == null) {
            throw new IllegalArgumentException("AdminDTO cannot be null");
        }

        AdminSubscription adminSubscription = adminRepository.findById(adminDTO.getSubscriptionId())
                .orElseThrow(() -> new IllegalArgumentException("Subscription not found"));

        DTOMapper.updateAdminSubscription(adminSubscription, adminDTO);
        return CompletableFuture.completedFuture(adminRepository.update(adminSubscription));
    }

    @Override
    @Async
    public CompletableFuture<Void> delete(String subscriptionId) {
        if(subscriptionId == null || subscriptionId.isBlank()){
            throw new IllegalArgumentException("ID cannot be null or empty");
        }

        adminRepository.delete(subscriptionId);
        return CompletableFuture.completedFuture(null);
    }
}