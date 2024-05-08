package snackscription.subscriptionadmin.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import snackscription.subscriptionadmin.enums.SubscriptionStatus;

import java.util.UUID;

@Getter @Setter
public class AdminSubscription {
    String subscriberName;
    String subscriberId;
    String uniqueCode;
    String subscriptionId;
    String subscriptionBoxId;
    String subscriptionStatus;

    public AdminSubscription() {
        this.subscriptionId = UUID.randomUUID().toString();
    }

    public AdminSubscription(String subscriberName, String subscriberId, String uniqueCode, String subscriptionBoxId, String subscriptionStatus) {
        this.subscriptionId = UUID.randomUUID().toString();

        this.subscriberName = subscriberName;
        this.subscriberId = subscriberId;

        if (!uniqueCode.startsWith("MTH-") && !uniqueCode.startsWith("QTR-") && !uniqueCode.startsWith("SAA-")) {
            throw new IllegalArgumentException("Invalid unique code");
        }

        this.uniqueCode = uniqueCode;
        this.subscriptionBoxId = subscriptionBoxId;
        this.getSubscriptionStatus();
        this.setSubscriptionStatus(subscriptionStatus);
    }

    public void setSubscriptionStatus(String subscriptionStatus) {
        if (SubscriptionStatus.contains(subscriptionStatus)) {
            this.subscriptionStatus = subscriptionStatus;
        } else {
            throw new IllegalArgumentException("Invalid subscription status");
        }
    }

}