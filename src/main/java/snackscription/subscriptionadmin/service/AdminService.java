package snackscription.subscriptionadmin.service;

import snackscription.subscriptionadmin.model.AdminSubscription;

import java.util.List;

public interface AdminService {
    public AdminSubscription create(AdminSubscription adminSubscription);
    public List<AdminSubscription> findAll();
}