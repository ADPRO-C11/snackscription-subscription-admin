package snackscription.subscriptionadmin.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import snackscription.subscriptionadmin.dto.AdminDTO;
import snackscription.subscriptionadmin.factory.AdminSubscriptionFactory;
import snackscription.subscriptionadmin.model.AdminSubscription;
import snackscription.subscriptionadmin.repository.AdminRepository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@ExtendWith(MockitoExtension.class)
public class AdminServiceImplTest {

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private AdminDTO adminDTO;

    @InjectMocks
    private AdminServiceImpl adminService;

    @Mock
    private AdminSubscription adminSubscription;

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
    }


    @Test
    void testCreate() {
        when(adminRepository.create(any(AdminSubscription.class))).thenReturn(adminSubscription);

        CompletableFuture<AdminSubscription> result = adminService.create(adminDTO);

        assertNotNull(result);
        assertTrue(result.isDone());
        assertEquals(adminSubscription, result.join());
    }

    @Test
    void testFindAll() {
        List<AdminSubscription> adminSubscriptions = List.of(adminSubscription);
        when(adminRepository.findAll()).thenReturn(adminSubscriptions);

        CompletableFuture<List<AdminDTO>> result = adminService.findAll();

        assertNotNull(result);
        assertTrue(result.isDone());
        assertEquals(List.of(adminDTO), result.join());
    }

    @Test
    void testFindById() {
        String id = "1";
        when(adminRepository.findById(id)).thenReturn(Optional.of(adminSubscription));

        CompletableFuture<AdminDTO> result = adminService.findById(id);

        assertNotNull(result);
        assertTrue(result.isDone());
        assertEquals(adminDTO, result.join());
    }

    @Test
    void testUpdate() {
        when(adminRepository.findById(adminDTO.getSubscriptionId())).thenReturn(Optional.of(adminSubscription));
        when(adminRepository.update(adminSubscription)).thenReturn(adminSubscription);

        CompletableFuture<AdminSubscription> result = adminService.update(adminDTO);

        assertNotNull(result);
        assertTrue(result.isDone());
        assertEquals(adminSubscription, result.join());
    }

    @Test
    void testDelete() {
        String id = "1";
        when(adminRepository.findById(id)).thenReturn(Optional.of(adminSubscription));

        CompletableFuture<Void> result = adminService.delete(id);

        assertNotNull(result);
        assertTrue(result.isDone());
        assertNull(result.join());
    }

    @Test
    void findByIdNullOrEmpty() {
        assertThrows(IllegalArgumentException.class, () -> {
            adminService.findById(null).join();
            adminService.findById("").join();
        });
    }

    @Test
    void updateNull() {
        assertThrows(IllegalArgumentException.class, () -> adminService.update(null).join());
    }

    @Test
    void updateNonExistent() {
        assertThrows(IllegalArgumentException.class, () -> {
            when(adminRepository.findById(adminDTO.getSubscriptionId())).thenReturn(Optional.empty());
            adminService.update(adminDTO).join();
        });
    }

    @Test
    void deleteNullOrEmpty() {
        assertThrows(IllegalArgumentException.class, () -> {
            adminService.delete(null).join();
            adminService.delete("").join();
        });
    }

    @Test
    void deleteNonExistent() {
        assertThrows(IllegalArgumentException.class, () -> {
            String id = "non-existent-id";
            when(adminRepository.findById(id)).thenReturn(Optional.empty());
            adminService.delete(id).join();
        });
    }

    @Test
    void createInvalid() {
        assertThrows(IllegalArgumentException.class, () -> {
            AdminDTO invalidDTO = new AdminDTO();
            adminService.create(invalidDTO).join();
        });
    }
}
