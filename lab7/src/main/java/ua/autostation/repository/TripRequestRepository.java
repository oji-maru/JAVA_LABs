package ua.autostation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.autostation.entity.TripRequest;

@Repository
public interface TripRequestRepository extends JpaRepository<TripRequest, Long> {
}