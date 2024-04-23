package snackscription.subscriptionadmin.repository;

import snackscription.subscriptionadmin.model.AdminSubscription;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AdminRepositoryTest {
    @InjectMocks
    AdminRepository adminRepository;

    @BeforeEach
    void setUp() {}

    @Test
    void testCreateAndFind() {
        AdminSubscription adminSubscription = new AdminSubscription();
        adminSubscription.setSubscriberId(1);
        adminSubscription.setSubscriberName("Hwang Hyunjin");
        adminSubscription.setSubscriptionType("Monthly");
        adminSubscription.setSubscriptionUniqueCode("123456");
        adminSubscription.setSubscribtionStatus("Pending");

        adminRepository.create(adminSubscription);

        Iterator<AdminSubscription> subscriptionIterator = adminRepository.findAll();
        assertTrue(subscriptionIterator.hasNext());

        AdminSubscription foundSubscription = subscriptionIterator.next();
        assertEquals(adminSubscription.getSubscriberId(), foundSubscription.getSubscriberId());
        assertEquals(adminSubscription.getSubscriberName(), foundSubscription.getSubscriberName());
        assertEquals(adminSubscription.getSubscriptionType(), foundSubscription.getSubscriptionType());
        assertEquals(adminSubscription.getSubscriptionUniqueCode(), foundSubscription.getSubscriptionUniqueCode());
        assertEquals(adminSubscription.getSubscribtionStatus(), foundSubscription.getSubscribtionStatus());
    }

    @Test
    void testFindEmpty() {
        Iterator<AdminSubscription> subscriptionIterator = adminRepository.findAll();
        assertFalse(subscriptionIterator.hasNext());
    }

    @Test
    void testFindMultiple() {
        AdminSubscription adminSubscription1 = new AdminSubscription();
        adminSubscription1.setSubscriberId(1);
        adminSubscription1.setSubscriberName("Hwang Hyunjin");
        adminSubscription1.setSubscriptionType("Monthly");
        adminSubscription1.setSubscriptionUniqueCode("123456");
        adminSubscription1.setSubscribtionStatus("Pending");

        AdminSubscription adminSubscription2 = new AdminSubscription();
        adminSubscription2.setSubscriberId(2);
        adminSubscription2.setSubscriberName("Bang Chan");
        adminSubscription2.setSubscriptionType("Weekly");
        adminSubscription2.setSubscriptionUniqueCode("654321");
        adminSubscription2.setSubscribtionStatus("Active");

        adminRepository.create(adminSubscription1);
        adminRepository.create(adminSubscription2);

        Iterator<AdminSubscription> subscriptionIterator = adminRepository.findAll();
        assertTrue(subscriptionIterator.hasNext());
        AdminSubscription foundSubscription = subscriptionIterator.next();
        assertEquals(adminSubscription1.getSubscriberId(), foundSubscription.getSubscriberId());
        assertEquals(adminSubscription1.getSubscriberName(), foundSubscription.getSubscriberName());
        assertEquals(adminSubscription1.getSubscriptionType(), foundSubscription.getSubscriptionType());
        assertEquals(adminSubscription1.getSubscriptionUniqueCode(), foundSubscription.getSubscriptionUniqueCode());
        assertEquals(adminSubscription1.getSubscribtionStatus(), foundSubscription.getSubscribtionStatus());
        foundSubscription = subscriptionIterator.next();
        assertEquals(adminSubscription2.getSubscriberId(), foundSubscription.getSubscriberId());
        assertEquals(adminSubscription2.getSubscriberName(), foundSubscription.getSubscriberName());
        assertEquals(adminSubscription2.getSubscriptionType(), foundSubscription.getSubscriptionType());
        assertEquals(adminSubscription2.getSubscriptionUniqueCode(), foundSubscription.getSubscriptionUniqueCode());
        assertEquals(adminSubscription2.getSubscribtionStatus(), foundSubscription.getSubscribtionStatus());
        assertFalse(subscriptionIterator.hasNext());
    }
}
