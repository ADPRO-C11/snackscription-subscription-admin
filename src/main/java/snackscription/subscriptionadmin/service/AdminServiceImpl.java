package snackscription.subscriptionadmin.service;

import snackscription.subscriptionadmin.model.AdminSubscription;
import snackscription.subscriptionadmin.repository.AdminRepository;
import org.springframework.stereotype.Service;
import snackscription.subscriptionadmin.dto.AdminDTO;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import snackscription.subscriptionadmin.dto.DTOMapper;


@Service
public class AdminServiceImpl implements AdminService{

    private final AdminRepository adminRepository;
    private static final String SUBSCRIPTION_NOT_FOUND = "Subscription not found";

    public AdminServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public CompletableFuture<AdminSubscription> create(AdminDTO adminDTO) {
        if (adminDTO == null || adminDTO.getSubscriptionId() == null) {
            throw new IllegalArgumentException("AdminDTO cannot be null and must have a valid subscriptionId");
        }
        AdminSubscription adminSubscription = DTOMapper.convertDTOtoModel(adminDTO);
        return CompletableFuture.completedFuture(adminRepository.create(adminSubscription));
    }

    @Override
    public CompletableFuture<List<AdminDTO>> findAll() {
        List<AdminSubscription> adminSubscriptions = adminRepository.findAll();
        List<AdminDTO> adminDtos = adminSubscriptions.stream()
                .map(DTOMapper::convertModelToDto)
                .toList();
        return CompletableFuture.completedFuture(adminDtos);
    }

    @Override
    public CompletableFuture<AdminDTO> findById(String subscriptionId) {
        if(subscriptionId == null || subscriptionId.isBlank()){
            throw new IllegalArgumentException("ID cannot be null or empty");
        }
        return adminRepository.findById(subscriptionId)
                .map(DTOMapper::convertModelToDto)
                .map(CompletableFuture::completedFuture)
                .orElseThrow(() -> new IllegalArgumentException(SUBSCRIPTION_NOT_FOUND));
    }

    @Override
    public CompletableFuture<AdminSubscription> update(AdminDTO adminDTO) {
        if(adminDTO == null) {
            throw new IllegalArgumentException("AdminDTO cannot be null");
        }

        return adminRepository.findById(adminDTO.getSubscriptionId()).
                map(adminSubscription -> {
                    DTOMapper.updateAdminSubscription(adminSubscription, adminDTO);
                    return CompletableFuture.completedFuture(adminRepository.update(adminSubscription));
                }).orElseThrow(() -> new IllegalArgumentException(SUBSCRIPTION_NOT_FOUND));
    }

    @Override
    public CompletableFuture<Void> delete(String subscriptionId) {
        if(subscriptionId == null || subscriptionId.isBlank()){
            throw new IllegalArgumentException("ID cannot be null or empty");
        }

        if (adminRepository.findById(subscriptionId).isEmpty()) {
            throw new IllegalArgumentException(SUBSCRIPTION_NOT_FOUND);
        }
        adminRepository.delete(subscriptionId);
        return CompletableFuture.completedFuture(null);
    }
}