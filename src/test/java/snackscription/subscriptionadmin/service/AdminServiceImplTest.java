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
import snackscription.subscriptionadmin.model.AdminSubscription;
import snackscription.subscriptionadmin.repository.AdminRepository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.function.Supplier;
import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
class AdminServiceImplTest {

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

    private <T> T invokeJoin(Supplier<CompletableFuture<T>> supplier) {
        try {
            return supplier.get().join();
        } catch (CompletionException e) {
            if (e.getCause() instanceof RuntimeException) {
                throw (RuntimeException) e.getCause();
            } else {
                throw new RuntimeException(e.getCause());
            }
        }
    }

    @Test
    void findByIdNullOrEmpty() {
        assertThrows(IllegalArgumentException.class, () -> {
            Stream.of(null, "")
                    .forEach(id -> invokeJoin(() -> adminService.findById(id)));
        });
    }

    @Test
    void updateNull() {
        assertThrows(IllegalArgumentException.class, () -> invokeJoin(() -> adminService.update(null)));
    }

    @Test
    void updateNonExistent() {
        assertThrows(IllegalArgumentException.class, () -> {
            when(adminRepository.findById(adminDTO.getSubscriptionId())).thenReturn(Optional.empty());
            invokeJoin(() -> adminService.update(adminDTO));
        });
    }

    @Test
    void deleteNullOrEmpty() {
        assertThrows(IllegalArgumentException.class, () -> {
            invokeJoin(() -> adminService.delete(null));
            invokeJoin(() -> adminService.delete(""));
        });
    }

    @Test
    void deleteNonExistent() {
        assertThrows(IllegalArgumentException.class, () -> {
            String id = "non-existent-id";
            when(adminRepository.findById(id)).thenReturn(Optional.empty());
            invokeJoin(() -> adminService.delete(id));
        });
    }

    @Test
    void createInvalid() {
        assertThrows(IllegalArgumentException.class, () -> {
            AdminDTO invalidDTO = new AdminDTO();
            invokeJoin(() -> adminService.create(invalidDTO));
        });
    }
}
