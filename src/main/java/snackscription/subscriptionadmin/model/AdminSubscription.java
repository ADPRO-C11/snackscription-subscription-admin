package snackscription.subscriptionadmin.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import snackscription.subscriptionadmin.enums.SubscriptionStatus;

import java.util.UUID;

@Entity @Getter @Setter
@Table(name = "admin_subscription")
public class AdminSubscription {
    @Column(name = "subscriber_name", nullable = false)
    String subscriberName;

    @Column(name = "subscriber_id", nullable = false)
    String subscriberId;

    @Column(name = "unique_code", nullable = false, unique = true)
    String uniqueCode;

    @Id
    String subscriptionId;

    @Column(name = "subscription_box_id", nullable = false)
    String subscriptionBoxId;

    @Column(name = "subscription_status", nullable = false)
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