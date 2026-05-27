package ua.autostation.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "trip_requests")
public class TripRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String destination;
    private int requiredCapacity;

    // 3. Демонстрація OneToMany (Many-сторона): Багато заявок закріплюються за одним водієм
    @ManyToOne
    @JoinColumn(name = "driver_id")
    private Driver driver;

    public TripRequest() {}
    public TripRequest(String destination, int requiredCapacity) {
        this.destination = destination;
        this.requiredCapacity = requiredCapacity;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }
    public int getRequiredCapacity() { return requiredCapacity; }
    public void setRequiredCapacity(int requiredCapacity) { this.requiredCapacity = requiredCapacity; }
    public Driver getDriver() { return driver; }
    public void setDriver(Driver driver) { this.driver = driver; }
}