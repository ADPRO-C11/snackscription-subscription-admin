package snackscription.subscriptionadmin.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AdminSubscription {
    private String subscriberName;
    private int subscriberId;
    private String subscriptionUniqueCode;
    private String subscriptionType;
    private String subscribtionStatus;
}