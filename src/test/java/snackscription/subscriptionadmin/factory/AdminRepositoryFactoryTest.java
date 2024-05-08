package snackscription.subscriptionadmin.factory;

import org.junit.jupiter.api.Test;
import snackscription.subscriptionadmin.repository.AdminRepository;

import static org.junit.jupiter.api.Assertions.*;

public class AdminRepositoryFactoryTest {
    @Test
    void testCreateAdminRepository() {
        AdminRepositoryFactory adminRepositoryFactory = new AdminRepositoryFactory();
        AdminRepository adminRepository = adminRepositoryFactory.create();

        assertNotNull(adminRepository);
        assertInstanceOf(AdminRepository.class, adminRepository);
    }
}
