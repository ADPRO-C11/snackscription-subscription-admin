package snackscription.subscriptionadmin.controller;

import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import snackscription.subscriptionadmin.dto.AdminDTO;
import snackscription.subscriptionadmin.model.AdminSubscription;
import snackscription.subscriptionadmin.service.AdminService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AdminControllerTest {

    @Mock
    private AdminService adminService;

    @InjectMocks
    private AdminController adminController;

    private AdminDTO adminDTO;
    private AdminSubscription adminSubscription;

    @BeforeEach
    void setUp(){
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
    void testCreate(){
        when(adminService.create(adminDTO)).thenReturn(CompletableFuture.completedFuture(adminSubscription));

        CompletableFuture<ResponseEntity<AdminSubscription>> response = adminController.create(adminDTO);
        assertNotNull(response);
        assertEquals(ResponseEntity.ok(adminSubscription), response.join());
    }

    @Test
    void testFindAll(){
        List<AdminDTO> adminDTOList = Collections.singletonList(adminDTO);
        when(adminService.findAll()).thenReturn(CompletableFuture.completedFuture(adminDTOList));

        CompletableFuture<ResponseEntity<List<AdminDTO>>> response = adminController.findAll();
        assertNotNull(response);
        assertEquals(ResponseEntity.ok(adminDTOList), response.join());
    }

    @Test
    void testFindById(){
        String validUUID = "8a56e04b-d0c8-4e43-b2e0-fdf43e304d9e";
        adminDTO.setSubscriptionId(validUUID);
        adminSubscription.setSubscriptionId(validUUID);
        when(adminService.findById(validUUID)).thenReturn(CompletableFuture.completedFuture(adminDTO));

        CompletableFuture<ResponseEntity<AdminDTO>> result = adminController.findById(validUUID);

        assertNotNull(result);
        assertTrue(result.isDone());
        assertEquals(ResponseEntity.ok(adminDTO), result.join());
    }

    @Test
    void testUpdate(){
        when(adminService.update(adminDTO)).thenReturn(CompletableFuture.completedFuture(adminSubscription));

        CompletableFuture<ResponseEntity<AdminSubscription>> response = adminController.update(adminDTO);
        assertNotNull(response);
        assertEquals(ResponseEntity.ok(adminSubscription), response.join());
    }

    @Test
    void testDelete(){
        String validUUID = "8a56e04b-d0c8-4e43-b2e0-fdf43e304d9e";
        when(adminService.delete(validUUID)).thenReturn(CompletableFuture.completedFuture(null));

        CompletableFuture<ResponseEntity<String>> response = adminController.delete(validUUID);
        assertNotNull(response);
        assertEquals(ResponseEntity.ok("DELETE SUCCESS"), response.join());
    }
}
