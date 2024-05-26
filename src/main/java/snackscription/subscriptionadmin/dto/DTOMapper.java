package snackscription.subscriptionadmin.dto;

import org.springframework.stereotype.Component;
import snackscription.subscriptionadmin.factory.AdminSubscriptionFactory;
import snackscription.subscriptionadmin.model.AdminSubscription;

import java.util.Optional;

@Component
public class DTOMapper {
    public static AdminDTO convertModelToDto(AdminSubscription adminSubscription) {
        AdminDTO adminDTO = new AdminDTO();
        adminDTO.setSubscriptionId(adminSubscription.getSubscriptionId());
        adminDTO.setSubscriptionType(adminSubscription.getSubscriptionType());
        adminDTO.setSubscriberName(adminSubscription.getSubscriberName());
        adminDTO.setSubscriberId(adminSubscription.getSubscriberId());
        adminDTO.setSubscriptionBoxId(adminSubscription.getSubscriptionBoxId());
        adminDTO.setSubscriptionStatus(adminSubscription.getSubscriptionStatus());
        adminDTO.setUniqueCode(adminSubscription.getUniqueCode());
        return adminDTO;
    }

    public static AdminSubscription convertDTOtoModel(AdminDTO adminDTO) {
        String subscriptionType = adminDTO.getSubscriptionType();
        String subscriberName = adminDTO.getSubscriberName();
        String subscriberId = adminDTO.getSubscriberId();
        String subscriptionBoxId = adminDTO.getSubscriptionBoxId();
        return new AdminSubscriptionFactory().create(subscriptionType, subscriberName, subscriberId, subscriptionBoxId);
    }

    public static AdminSubscription updateAdminSubscription(AdminSubscription adminSubscription, AdminDTO adminDTO) {
        Optional.ofNullable(adminDTO.getSubscriptionStatus()).ifPresent(adminSubscription::setSubscriptionStatus);
        return adminSubscription;
    }
}







