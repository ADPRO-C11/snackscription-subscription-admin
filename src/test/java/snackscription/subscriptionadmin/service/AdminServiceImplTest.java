package snackscription.subscriptionadmin.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import snackscription.subscriptionadmin.dto.AdminDTO;
import snackscription.subscriptionadmin.factory.AdminSubscriptionFactory;
import snackscription.subscriptionadmin.model.AdminSubscription;
import snackscription.subscriptionadmin.repository.AdminRepository;

import java.util.Collections;
import java.util.List;

public class AdminServiceImplTest {

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private AdminSubscriptionFactory adminSubscriptionFactory;

    @InjectMocks
    private AdminServiceImpl adminService;

    private AdminSubscription adminSubscription;
    private AdminDTO adminDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        adminDTO = new AdminDTO();
        adminDTO.setSubscriptionId("1");
        adminDTO.setSubscriptionType("MONTHLY");
        adminDTO.setSubscriberName("Stray Kids");
        adminDTO.setSubscriberId("0325");
        adminDTO.setSubscriptionBoxId("143ily");
        adminDTO.setSubscriptionStatus("PENDING");

        adminSubscription = new AdminSubscription();
        adminSubscription.setSubscriptionId("1");
        adminSubscription.setSubscriptionType("MONTHLY");
        adminSubscription.setSubscriberName("Stray Kids");
        adminSubscription.setSubscriberId("0325");
        adminSubscription.setSubscriptionBoxId("143ily");
        adminSubscription.setSubscriptionStatus("PENDING");

        when(adminSubscriptionFactory.create(anyString(), anyString(), anyString(), anyString())).thenReturn(adminSubscription);
    }

    @Test
    void testCreate() {
        when(adminRepository.create(any(AdminSubscription.class))).thenReturn(adminSubscription);

        AdminSubscription result = adminService.create(adminDTO);

        assertNotNull(result);
        assertEquals(adminSubscription, result);
    }

    @Test
    void testFindAll() {
        when(adminRepository.findAll()).thenReturn(Collections.singletonList(adminSubscription));

        List<AdminDTO> result = adminService.findAll();

        assertEquals(1, result.size());
        assertEquals(adminSubscription.getSubscriptionId(), result.get(0).getSubscriptionId());
    }

    @Test
    void testFindById() {
        String subscriptionId = "1";

        when(adminRepository.findById(subscriptionId)).thenReturn(java.util.Optional.of(adminSubscription));

        AdminDTO result = adminService.findById(subscriptionId);

        assertNotNull(result);
        assertEquals(adminSubscription.getSubscriptionId(), result.getSubscriptionId());
    }
}
