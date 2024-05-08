package snackscription.subscriptionadmin.factory;

import snackscription.subscriptionadmin.repository.AdminRepository;

public class AdminRepositoryFactory implements Factory<AdminRepository> {
    @Override
    public AdminRepository create() {
        return null;
    }

    public AdminRepository create(String subscriberName, String subscriberId, String uniqueCode, String subscriptionBoxId, String subscriptionStatus) {
        return null;
    }
}
