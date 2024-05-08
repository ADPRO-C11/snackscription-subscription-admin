package snackscription.subscriptionadmin.factory;

import org.junit.jupiter.api.Test;
import snackscription.subscriptionadmin.enums.SubscriptionStatus;
import snackscription.subscriptionadmin.model.AdminSubscription;

import static org.junit.jupiter.api.Assertions.*;

public class AdminSubscriptionFactoryTest {
    @Test
    void testCreateAdminSubscription() {
        AdminSubscriptionFactory adminSubscriptionFactory = new AdminSubscriptionFactory();
        AdminSubscription adminSubscription = adminSubscriptionFactory.create();

        assertNotNull(adminSubscription);
    }

    @Test
    void testCreateAdminSubscriptionComplete() {
        AdminSubscriptionFactory adminSubscriptionFactory = new AdminSubscriptionFactory();

        AdminSubscription adminSubscription = adminSubscriptionFactory.create(
                "Hwang Hyunjin",
                "12345678910",
                "MTH-12345678910",
                "12345678910",
                SubscriptionStatus.PENDING.getValue()
        );
        
        assertNotNull(adminSubscription.getSubscriptionId());
        assertEquals("Hwang Hyunjin", adminSubscription.getSubscriberName());
        assertEquals("12345678910", adminSubscription.getSubscriberId());
        assertEquals("MTH-12345678910", adminSubscription.getUniqueCode());
        assertEquals("12345678910", adminSubscription.getSubscriptionBoxId());
        assertEquals(SubscriptionStatus.PENDING.getValue(), adminSubscription.getSubscriptionStatus());
    }
}
