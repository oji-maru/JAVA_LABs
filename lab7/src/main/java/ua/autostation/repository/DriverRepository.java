package ua.autostation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.autostation.entity.Driver;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {
    // CRUD (save, findById, delete) вже вшиті
}