package snackscription.subscriptionadmin.controller;

import org.junit.jupiter.api.Test;
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

    @Test
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

        CompletableFuture<ResponseEntity<AdminSubscription>> result = adminController.create(adminDTO);

        assertNotNull(result);
        assertTrue(result.isDone());
        assertEquals(ResponseEntity.ok(adminSubscription), result.join());
    }

    @Test
    void testFindAll(){
        when(adminService.findAll()).thenReturn(CompletableFuture.completedFuture(Collections.singletonList(adminDTO)));

        CompletableFuture<ResponseEntity<List<AdminDTO>>> result = adminController.findAll();

        assertNotNull(result);
        assertTrue(result.isDone());
        assertEquals(ResponseEntity.ok(Collections.singletonList(adminDTO)), result.join());
    }

    @Test
    void testFindById(){
        when(adminService.findById("1")).thenReturn(CompletableFuture.completedFuture(Optional.of(adminDTO)));

        CompletableFuture<ResponseEntity<Optional<AdminDTO>>> result = adminController.findById("1");

        assertNotNull(result);
        assertTrue(result.isDone());
        assertEquals(ResponseEntity.ok(Optional.of(adminDTO)), result.join());
    }

    @Test
    void testUpdate(){
        when(adminService.update(adminDTO)).thenReturn(CompletableFuture.completedFuture(adminSubscription));

        CompletableFuture<ResponseEntity<AdminSubscription>> result = adminController.update(adminDTO);

        assertNotNull(result);
        assertTrue(result.isDone());
        assertEquals(ResponseEntity.ok(adminSubscription), result.join());
    }

    @Test
    void testDelete(){
        when(adminService.delete("1")).thenReturn(CompletableFuture.completedFuture(null));

        CompletableFuture<ResponseEntity<String>> result = adminController.delete("1");

        assertNotNull(result);
        assertTrue(result.isDone());
        assertEquals(ResponseEntity.ok("DELETE SUCCESS"), result.join());
    }
}
