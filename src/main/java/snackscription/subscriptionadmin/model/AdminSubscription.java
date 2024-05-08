package snackscription.subscriptionadmin.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import snackscription.subscriptionadmin.enums.SubscriptionStatus;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "admin_subscription")
public class AdminSubscription {
    @Id
    String subscriptionId;

    @Column(name = "unique_code", unique = true)
    String uniqueCode;

    @Column(name = "subscription_type")
    String subscriptionType;

    @Column(name = "subscriber_name")
    String subscriberName;

    @Column(name = "subscriber_id")
    String subscriberId;

    @Column(name = "subscription_box_id")
    String subscriptionBoxId;

    @Column(name = "subscription_status")
    String subscriptionStatus;

    public AdminSubscription() {
        this.subscriptionId = UUID.randomUUID().toString();
    }

    public AdminSubscription(String subscriptionType, String subscriberName, String subscriberId, String subscriptionBoxId) {
        this.subscriptionId = UUID.randomUUID().toString();

        this.subscriberName = subscriberName;
        this.subscriberId = subscriberId;

        this.subscriptionType = subscriptionType;
        this.setUniqueCode(subscriptionType);

        this.subscriptionBoxId = subscriptionBoxId;
        this.setSubscriptionStatus(SubscriptionStatus.PENDING.getValue());
    }

    public void setUniqueCode(String subscriptionType) {
        String prefix = switch (subscriptionType) {
            case "Monthly" -> "MTH-";
            case "Quarterly" -> "QTR-";
            case "Semi-Annual" -> "SAA-";
            default -> throw new IllegalArgumentException("Invalid type");
        };
        String randomPart = UUID.randomUUID().toString();
        randomPart = randomPart.replace("-", "").toUpperCase().substring(0, 16);
        this.uniqueCode = prefix + randomPart;
    }

    public void setSubscriptionStatus(String subscriptionStatus) {
        if (SubscriptionStatus.contains(subscriptionStatus)) {
            this.subscriptionStatus = subscriptionStatus;
        } else {
            throw new IllegalArgumentException("Invalid subscription status");
        }
    }

}