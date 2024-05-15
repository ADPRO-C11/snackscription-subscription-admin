package snackscription.subscriptionadmin.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import snackscription.subscriptionadmin.controller.AdminController;
import snackscription.subscriptionadmin.dto.AdminDTO;
import snackscription.subscriptionadmin.factory.AdminSubscriptionFactory;
import snackscription.subscriptionadmin.model.AdminSubscription;
import snackscription.subscriptionadmin.repository.AdminRepository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class AdminServiceImplTest {

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private AdminSubscriptionFactory adminSubscriptionFactory;

    @InjectMocks
    private AdminServiceImpl adminService;
    private AdminController adminController;

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
        when(adminRepository.create(adminSubscription)).thenReturn(adminSubscription);

        CompletableFuture<AdminSubscription> result = adminService.create(adminDTO);

        assertNotNull(result);
        assertTrue(result.isDone());
        assertEquals(adminSubscription, result.join());
    }

    @Test
    void testFindAll() {
        List<AdminDTO> adminDTOList = List.of(adminDTO);

        when(adminService.findAll()).thenReturn(CompletableFuture.completedFuture(adminDTOList));

        CompletableFuture<ResponseEntity<List<AdminDTO>>> result = adminController.findAll();

        assertNotNull(result);
        assertTrue(result.isDone());
        assertEquals(ResponseEntity.ok(adminDTOList), result.join());
    }

    @Test
    void testFindById() {
        Optional<AdminDTO> adminDTOOptional = Optional.of(adminDTO);

        when(adminRepository.findById(anyString())).thenReturn(Optional.of(adminSubscription));
        when(adminService.findById(anyString())).thenReturn(CompletableFuture.completedFuture(adminDTOOptional));

        CompletableFuture<ResponseEntity<Optional<AdminDTO>>> result = adminController.findById("1");

        assertNotNull(result);
        assertTrue(result.isDone());
        assertEquals(ResponseEntity.ok(adminDTOOptional), result.join());
    }

    @Test
    void testUpdate() {
        when(adminService.findById(adminDTO.getSubscriptionId())).thenReturn(CompletableFuture.completedFuture(Optional.of(adminDTO)));
        when(adminService.update(adminDTO)).thenReturn(CompletableFuture.completedFuture(adminSubscription));

        CompletableFuture<ResponseEntity<AdminSubscription>> result = adminController.update(adminDTO);

        assertNotNull(result);
        assertTrue(result.isDone());
        assertEquals(ResponseEntity.ok(adminSubscription), result.join());
    }

    @Test
    void testDelete() {
        when(adminService.delete(anyString())).thenReturn(CompletableFuture.completedFuture(null));

        CompletableFuture<ResponseEntity<String>> result = adminController.delete("1");

        assertNotNull(result);
        assertTrue(result.isDone());
        assertEquals(ResponseEntity.ok("DELETE SUCCESS"), result.join());
    }
}
