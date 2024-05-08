package snackscription.subscriptionadmin.dto;

import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class AdminDTO {
    private String subscriptionType;
    private String subscriberName;
    private String subscriberId;
    private String subscriptionBoxId;
    private String subscriptionStatus;
    private String uniqueCode;
}
