package ua.autostation.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "drivers")
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Ім'я водія обов'язкове для заповнення")
    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "driver", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TripRequest> trips = new ArrayList<>();
    
    public Driver() {}
    public Driver(String name) { this.name = name; }

    // Хелпер для каскадного зв'язку
    public void addTrip(TripRequest trip) {
        trips.add(trip);
        trip.setDriver(this);
    }

    // Getters / Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public List<TripRequest> getTrips() { return trips; }
    public void setTrips(List<TripRequest> trips) { this.trips = trips; }
}