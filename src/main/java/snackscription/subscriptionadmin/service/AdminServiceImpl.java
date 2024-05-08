package snackscription.subscriptionadmin.service;

import snackscription.subscriptionadmin.model.AdminSubscription;
import snackscription.subscriptionadmin.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import snackscription.subscriptionadmin.dto.AdminDTO;

import java.util.List;

import snackscription.subscriptionadmin.dto.DTOMapper;

@Service
public class AdminServiceImpl implements AdminService{

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public AdminSubscription create(AdminDTO adminDTO) {
        AdminSubscription adminSubscription = DTOMapper.convertDTOtoModel(adminDTO);
        return adminRepository.create(adminSubscription);
    }

    @Override
    public List<AdminDTO> findAll() {
        return adminRepository.findAll()
                .stream()
                .map(DTOMapper::convertModelToDto)
                .toList();
    }

    @Override
    public AdminDTO findById(String subscriptionId) {
        if(subscriptionId == null || subscriptionId.isBlank()){
            throw new IllegalArgumentException("ID cannot be null or empty");
        }

        AdminSubscription adminSubscription = adminRepository.findById(subscriptionId)
                .orElseThrow(() -> new IllegalArgumentException("Subscription not found"));

        return DTOMapper.convertModelToDto(adminSubscription);
    }

    @Override
    public AdminSubscription update(AdminDTO adminDTO) {
        if(adminDTO == null) {
            throw new IllegalArgumentException("AdminDTO cannot be null");
        }

        AdminSubscription adminSubscription = adminRepository.findById(adminDTO.getSubscriptionId())
                .orElseThrow(() -> new IllegalArgumentException("Subscription not found"));

        DTOMapper.updateAdminSubscription(adminSubscription, adminDTO);
        return adminRepository.update(adminSubscription);
    }

    @Override
    public void delete(String subscriptionId) {
        if(subscriptionId == null || subscriptionId.isBlank()){
            throw new IllegalArgumentException("ID cannot be null or empty");
        }

        if(adminRepository.findById(subscriptionId).isEmpty()){
            throw new IllegalArgumentException("Subscription not found");
        }

        adminRepository.delete(subscriptionId);
    }
}