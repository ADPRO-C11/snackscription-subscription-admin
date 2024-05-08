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
                "MONTHLY",
                "Hwang Hyunjin",
                "12345678910",
                "12345678910"
        );

        assertNotNull(adminSubscription.getSubscriptionId());
        assertNotNull(adminSubscription.getUniqueCode());
        assertEquals("Hwang Hyunjin", adminSubscription.getSubscriberName());
        assertEquals("12345678910", adminSubscription.getSubscriberId());
        assertEquals("12345678910", adminSubscription.getSubscriptionBoxId());
        assertEquals(SubscriptionStatus.PENDING.getValue(), adminSubscription.getSubscriptionStatus());
    }
}
