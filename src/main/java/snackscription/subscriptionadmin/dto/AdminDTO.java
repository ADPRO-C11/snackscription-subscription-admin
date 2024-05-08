package snackscription.subscriptionadmin.dto;

import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class AdminDTO {
    private String subscriptionId;
    private String subscriptionType;
    private String subscriberName;
    private String subscriberId;
    private String subscriptionBoxId;
    private String subscriptionStatus;
    private String uniqueCode;

    public AdminDTO(String subscriptionId, String uniqueCode, String subscriberName, String subscriberId, String subscriptionBoxId, String subscriptionStatus) {
        this.subscriptionId = subscriptionId;
        this.uniqueCode = uniqueCode;
        this.subscriberName = subscriberName;
        this.subscriberId = subscriberId;
        this.subscriptionBoxId = subscriptionBoxId;
        this.subscriptionStatus = subscriptionStatus;
    }
}
