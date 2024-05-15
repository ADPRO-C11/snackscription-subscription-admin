package snackscription.subscriptionadmin.factory;

import snackscription.subscriptionadmin.model.AdminSubscription;

public class AdminSubscriptionFactory implements Factory<AdminSubscription> {
    @Override
    public AdminSubscription create() {
        return new AdminSubscription();
    }

    public AdminSubscription create(String subscriptionType, String subscriberName, String subscriberId, String subscriptionBoxId) {
        return new AdminSubscription(subscriptionType, subscriberName, subscriberId, subscriptionBoxId);
    }
}