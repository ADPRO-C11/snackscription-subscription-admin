package snackscription.subscriptionadmin.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdminSubscriptionTest {
    AdminSubscription adminSubscription;

    @BeforeEach
    void setUp() {
        this.adminSubscription = new AdminSubscription();
        this.adminSubscription.setSubscriberId(1);
        this.adminSubscription.setSubscriberName("Hwang Hyunjin");
        this.adminSubscription.setSubscriptionType("Monthly");
        this.adminSubscription.setSubscriptionUniqueCode("123456");
        this.adminSubscription.setSubscribtionStatus("Pending");
    }

    @Test
    void testGetSubscriberId() {
        assertEquals(1, this.adminSubscription.getSubscriberId());
    }

    @Test
    void testGetSubscriberName() {
        assertEquals("Hwang Hyunjin", this.adminSubscription.getSubscriberName());
    }

    @Test
    void testGetSubscriptionType() {
        assertEquals("Monthly", this.adminSubscription.getSubscriptionType());
    }

    @Test
    void testGetSubscriptionUniqueCode() {
        assertEquals("123456", this.adminSubscription.getSubscriptionUniqueCode());
    }

    @Test
    void testGetSubscribtionStatus() {
        assertEquals("Pending", this.adminSubscription.getSubscribtionStatus());
    }
}
