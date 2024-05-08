package snackscription.subscriptionadmin.factory;

public interface Factory <F> {
    F create();

    F create(String subscriptionType, String subscriberName, String subscriberId, String subscriptionBoxId);
}
