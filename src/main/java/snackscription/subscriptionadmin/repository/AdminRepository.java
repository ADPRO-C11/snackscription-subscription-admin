package snackscription.subscriptionadmin.repository;

import org.springframework.stereotype.Repository;
import snackscription.subscriptionadmin.model.AdminSubscription;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class AdminRepository {
    private List<AdminSubscription> subscriptionData = new ArrayList<>();

    public AdminSubscription create(AdminSubscription adminSubscription) {
        subscriptionData.add(adminSubscription);
        return adminSubscription;
    }

    public Iterator<AdminSubscription> findAll() {
        return subscriptionData.iterator();
    }
}