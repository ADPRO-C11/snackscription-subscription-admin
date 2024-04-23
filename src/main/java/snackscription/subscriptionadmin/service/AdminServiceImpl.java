package snackscription.subscriptionadmin.service;

import snackscription.subscriptionadmin.model.AdminSubscription;
import snackscription.subscriptionadmin.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService{
    @Autowired
    private AdminRepository adminRepository;

    @Override
    public AdminSubscription create(AdminSubscription adminSubscription) {
        adminRepository.create(adminSubscription);
        return adminSubscription;
    }

    @Override
    public List<AdminSubscription> findAll() {
        List<AdminSubscription> allAdminSubscriptions = new ArrayList<>();
        Iterator<AdminSubscription> subscriptionIterator = adminRepository.findAll();
        subscriptionIterator.forEachRemaining(allAdminSubscriptions::add);
        return allAdminSubscriptions;
    }
}