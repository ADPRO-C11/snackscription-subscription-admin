package snackscription.subscriptionadmin.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import snackscription.subscriptionadmin.enums.SubscriptionStatus;

import static org.junit.jupiter.api.Assertions.*;

class AdminSubscriptionTest {
    AdminSubscription adminSubscription;

    @BeforeEach
    void setUp() {
        this.adminSubscription = new AdminSubscription();
        this.adminSubscription.setSubscriberName("Hwang Hyunjin");
        this.adminSubscription.setSubscriberId("12345678910");
        this.adminSubscription.setSubscriptionId("1234567890");
        this.adminSubscription.setSubscriptionType("MONTHLY");
        this.adminSubscription.setUniqueCode("MONTHLY");
        this.adminSubscription.setSubscriptionBoxId("12345678910");
        this.adminSubscription.setSubscriptionStatus(SubscriptionStatus.PENDING.getValue());
    }

    @Test
    void testGetSubscriberName() {
        assertEquals("Hwang Hyunjin", this.adminSubscription.getSubscriberName());
    }

    @Test
    void testGetSubscriberId() {
        assertEquals("12345678910", this.adminSubscription.getSubscriberId());
    }

    @Test
    void testGetSubscriptionId() {
        assertEquals("1234567890", this.adminSubscription.getSubscriptionId());
    }

    @Test
    void testGetUniqueCode() {
        assertTrue(adminSubscription.getUniqueCode().startsWith("MTH-"));
    }

    @Test
    void testGetSubscriptionBoxId() {
        assertEquals("12345678910", this.adminSubscription.getSubscriptionBoxId());
    }

    @Test
    void testGetSubscriptionStatus() {
        assertEquals(SubscriptionStatus.PENDING.getValue(), adminSubscription.getSubscriptionStatus());
    }

    @Test
    void testSetSubscriptionStatus() {
        this.adminSubscription.setSubscriptionStatus(SubscriptionStatus.SUBSCRIBED.getValue());
        assertEquals(SubscriptionStatus.SUBSCRIBED.getValue(), this.adminSubscription.getSubscriptionStatus());
    }

    @Test
    void testSetInvalidSubscriptionStatus() {
        assertThrows(IllegalArgumentException.class, () -> {
            this.adminSubscription.setSubscriptionStatus("INVALID");
        });
    }
}
