package snackscription.subscriptionadmin.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import snackscription.subscriptionadmin.model.AdminSubscription;

import java.util.List;
import java.util.Optional;

@Repository
public class AdminRepository {
    @Autowired
    private EntityManager entityManager;

    @Transactional
    public AdminSubscription create(AdminSubscription adminSubscription) {
        entityManager.persist(adminSubscription);
        return adminSubscription;
    }

    @Transactional
    public List<AdminSubscription> findAll() {
        String jpql = "SELECT a FROM AdminSubscription a";
        TypedQuery<AdminSubscription> query = entityManager.createQuery(jpql, AdminSubscription.class);
        return query.getResultList();
    }

    @Transactional
    public Optional<AdminSubscription> findById(String subscriptionId) {
        AdminSubscription adminSubscription = entityManager.find(AdminSubscription.class, subscriptionId);
        return Optional.ofNullable(adminSubscription);
    }

    @Transactional
    public AdminSubscription update(AdminSubscription adminSubscription) {
        return entityManager.merge(adminSubscription);
    }

    @Transactional
    public void delete(String subscriptionId) {
        AdminSubscription adminSubscription = findById(subscriptionId).orElseThrow(() -> new IllegalArgumentException("Subscription not found"));
        entityManager.remove(adminSubscription);
    }
}