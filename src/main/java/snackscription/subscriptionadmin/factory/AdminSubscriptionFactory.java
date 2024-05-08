package snackscription.subscriptionadmin.factory;

import snackscription.subscriptionadmin.model.AdminSubscription;

public class AdminSubscriptionFactory implements Factory<AdminSubscription> {
    @Override
    public AdminSubscription create() {
        return new AdminSubscription();
    }

    public AdminSubscription create(String subscriberName, String subscriberId, String uniqueCode, String subscriptionBoxId, String subscriptionStatus) {
        return new AdminSubscription(subscriberName, subscriberId, uniqueCode, subscriptionBoxId, subscriptionStatus);
    }
}