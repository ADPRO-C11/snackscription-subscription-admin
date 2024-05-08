package snackscription.subscriptionadmin.controller;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import snackscription.subscriptionadmin.dto.AdminDTO;
import snackscription.subscriptionadmin.model.AdminSubscription;
import snackscription.subscriptionadmin.service.AdminService;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AdminControllerTest {

    @Mock
    private AdminService adminService;

    @InjectMocks
    private AdminController adminController;

    public AdminControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() {
        List<AdminDTO>  adminDTOList = Collections.singletonList(new AdminDTO());

        when(adminService.findAll()).thenReturn(adminDTOList);

        ResponseEntity<List<AdminDTO>> responseEntity = adminController.findAll();
    }
}
