package snackscription.subscriptionadmin.controller;

import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import snackscription.subscriptionadmin.dto.AdminDTO;
import snackscription.subscriptionadmin.model.AdminSubscription;
import snackscription.subscriptionadmin.service.AdminService;
import snackscription.subscriptionadmin.utils.JWTUtils;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AdminControllerTest {

    @Mock
    private AdminService adminService;

    @Mock
    private JWTUtils jwtUtils;

    @InjectMocks
    private AdminController adminController;

    private AdminDTO adminDTO;
    private AdminSubscription adminSubscription;
    private final String validToken =
            "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiQURNSU4iLCJzdWIiOiJhZG1pbkBnbWFpbC5jb20iLCJpYXQiOjE3MTY0NTUyMzgsImV4cCI6MTcxNjU0MTYzOH0.D5PKsxm3jr7SybMfsBylQqd2lT8S_cuX3jQWGolOD78";

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

        when(jwtUtils.isTokenValid(validToken)).thenReturn(true);
    }

    @Test
    void testHome(){
        ResponseEntity<String> response = adminController.home();
        assertNotNull(response);
        assertEquals(ResponseEntity.ok("Snackscription - Admin Subscription Management API"), response);
    }

    @Test
    void testCreate() throws IllegalAccessException{
        when(adminService.create(adminDTO)).thenReturn(CompletableFuture.completedFuture(adminSubscription));

        CompletableFuture<ResponseEntity<AdminSubscription>> response = adminController.create(validToken, adminDTO);
        assertNotNull(response);
        assertEquals(ResponseEntity.ok(adminSubscription), response.join());
    }

    @Test
    void testFindAll() throws IllegalAccessException{
        List<AdminDTO> adminDTOList = Collections.singletonList(adminDTO);
        when(adminService.findAll()).thenReturn(CompletableFuture.completedFuture(adminDTOList));

        CompletableFuture<ResponseEntity<List<AdminDTO>>> response = adminController.findAll(validToken);
        assertNotNull(response);
        assertEquals(ResponseEntity.ok(adminDTOList), response.join());
    }

    @Test
    void testFindById() throws IllegalAccessException{
        String validUUID = "8a56e04b-d0c8-4e43-b2e0-fdf43e304d9e";
        adminDTO.setSubscriptionId(validUUID);
        adminSubscription.setSubscriptionId(validUUID);
        when(adminService.findById(validUUID)).thenReturn(CompletableFuture.completedFuture(adminDTO));

        CompletableFuture<ResponseEntity<AdminDTO>> result = adminController.findById(validToken, validUUID);

        assertNotNull(result);
        assertTrue(result.isDone());
        assertEquals(ResponseEntity.ok(adminDTO), result.join());
    }

    @Test
    void testUpdate() throws IllegalAccessException{
        when(adminService.update(adminDTO)).thenReturn(CompletableFuture.completedFuture(adminSubscription));

        CompletableFuture<ResponseEntity<AdminSubscription>> response = adminController.update(validToken, adminDTO);
        assertNotNull(response);
        assertEquals(ResponseEntity.ok(adminSubscription), response.join());
    }

    @Test
    void testDelete() throws IllegalAccessException{
        String validUUID = "8a56e04b-d0c8-4e43-b2e0-fdf43e304d9e";
        when(adminService.delete(validUUID)).thenReturn(CompletableFuture.completedFuture(null));

        CompletableFuture<ResponseEntity<String>> response = adminController.delete(validToken, validUUID);
        assertNotNull(response);
        assertEquals(ResponseEntity.ok("DELETE SUCCESS"), response.join());
    }

    @Test
    void testUpdateInvalidSubscriptionId() throws IllegalAccessException{
        AdminDTO adminDTO = new AdminDTO();

        CompletableFuture<ResponseEntity<AdminSubscription>> expectedResponse = CompletableFuture.completedFuture(ResponseEntity.badRequest().build());
        CompletableFuture<ResponseEntity<AdminSubscription>> response = adminController.update(validToken, adminDTO);

        assertTrue(response.isDone());
        assertEquals(expectedResponse.join(), response.join());
    }

    @Test
    void testUpdateNonexistentSubscription() throws IllegalAccessException{
        AdminDTO adminDTO = new AdminDTO();
        adminDTO.setSubscriptionId(UUID.randomUUID().toString());
        CompletableFuture<ResponseEntity<AdminSubscription>> expectedResponse = CompletableFuture.completedFuture(ResponseEntity.notFound().build());

        when(adminService.findById(adminDTO.getSubscriptionId())).thenReturn(CompletableFuture.completedFuture(null));

        CompletableFuture<ResponseEntity<AdminSubscription>> response = adminController.update(validToken, adminDTO);

        assertTrue(response.isDone());
        assertEquals(expectedResponse.join(), response.join());
    }

    @Test
    void testDeleteInvalidSubscriptionId() throws IllegalAccessException{
        CompletableFuture<ResponseEntity<String>> expectedResponse = CompletableFuture.completedFuture(ResponseEntity.badRequest().build());
        CompletableFuture<ResponseEntity<String>> response = adminController.delete(validToken, "invalid-id");

        assertTrue(response.isDone());
        assertEquals(expectedResponse.join(), response.join());
    }

    @Test
    void testDeleteNonexistentSubscription() throws IllegalAccessException{
        String validUUID = UUID.randomUUID().toString();
        CompletableFuture<ResponseEntity<String>> expectedResponse = CompletableFuture.completedFuture(ResponseEntity.notFound().build());

        when(adminService.delete(validUUID)).thenThrow(new IllegalArgumentException("Subscription not found"));

        CompletableFuture<ResponseEntity<String>> response = adminController.delete(validToken, validUUID);

        assertTrue(response.isDone());
        assertEquals(expectedResponse.join(), response.join());
    }
}
