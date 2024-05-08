package snackscription.subscriptionadmin.service;

import snackscription.subscriptionadmin.dto.AdminDTO;
import snackscription.subscriptionadmin.model.AdminSubscription;

import java.util.List;

public interface AdminService {
    AdminSubscription create(AdminDTO adminDTO);
    List<AdminDTO> findAll();
    AdminDTO findById(String subscriptionId);
    AdminSubscription update(AdminDTO adminDTO);
    void delete(String subscriptionId);
}