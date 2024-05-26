package snackscription.subscriptionadmin.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import snackscription.subscriptionadmin.factory.AdminSubscriptionFactory;
import snackscription.subscriptionadmin.model.AdminSubscription;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class AdminRepositoryTest {
    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private AdminRepository adminRepository;

    private final AdminSubscriptionFactory adminSubscriptionFactory = new AdminSubscriptionFactory();

    @Test
    void testCreateAdminSubscription() {
        AdminSubscription adminSubscription = adminSubscriptionFactory.create();
        AdminSubscription createdAdminSubscription = adminRepository.create(adminSubscription);

        assertEquals(adminSubscription, createdAdminSubscription);
        verify(entityManager, times(1)).persist(adminSubscription);
    }

    @Test
    void testFindAll() {
        AdminSubscription adminSubscription1 = adminSubscriptionFactory.create();
        AdminSubscription adminSubscription2 = adminSubscriptionFactory.create();

        TypedQuery<AdminSubscription> query = mock(TypedQuery.class);
        when(entityManager.createQuery("SELECT a FROM AdminSubscription a", AdminSubscription.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(Arrays.asList(adminSubscription1, adminSubscription2));

        List<AdminSubscription> adminSubscriptions = adminRepository.findAll();

        assertEquals(2, adminSubscriptions.size());
        verify(entityManager, times(1)).createQuery("SELECT a FROM AdminSubscription a", AdminSubscription.class);
        verify(query, times(1)).getResultList();
    }

    @Test
    void testFindById() {
        AdminSubscription adminSubscription = adminSubscriptionFactory.create();
        adminSubscription.setSubscriptionId("1");

        when(entityManager.find(AdminSubscription.class, "1")).thenReturn(adminSubscription);

        Optional<AdminSubscription> optionalAdminSubscription = adminRepository.findById("1");

        assertEquals(Optional.of(adminSubscription), optionalAdminSubscription);
        verify(entityManager, times(1)).find(AdminSubscription.class, "1");
    }

    @Test
    void testFindByIdSubscriptionNotFound() {
        when(entityManager.find(AdminSubscription.class, "nonexistent")).thenReturn(null);

        assertNull(adminRepository.findById("nonexistent").orElse(null));

        verify(entityManager, times(1)).find(AdminSubscription.class, "nonexistent");
    }

    @Test
    void testUpdate() {
        AdminSubscription adminSubscription = adminSubscriptionFactory.create();

        when(entityManager.merge(adminSubscription)).thenReturn(adminSubscription);

        AdminSubscription updatedAdminSubscription = adminRepository.update(adminSubscription);

        assertEquals(adminSubscription, updatedAdminSubscription);
        verify(entityManager, times(1)).merge(adminSubscription);
    }

    @Test
    void testDelete() {
        AdminSubscription adminSubscription = adminSubscriptionFactory.create();
        adminSubscription.setSubscriptionId("1");

        when(entityManager.find(AdminSubscription.class, "1")).thenReturn(adminSubscription);
        doNothing().when(entityManager).remove(adminSubscription);

        adminRepository.delete("1");

        verify(entityManager, times(1)).find(AdminSubscription.class, "1");
        verify(entityManager, times(1)).remove(adminSubscription);
    }

    @Test
    void testDeleteSubscriptionNotFound() {
        when(entityManager.find(AdminSubscription.class, "nonexistent")).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> adminRepository.delete("nonexistent"));

        verify(entityManager, times(1)).find(AdminSubscription.class, "nonexistent");
        verify(entityManager, never()).remove(any());
    }
}
