package ua.autostation.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "drivers")
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // Демонстрація 1:Б: у одного водія може бути список виконаних рейсів
    @OneToMany(mappedBy = "driver", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TripRequest> trips = new ArrayList<>();

    // Демонстрація Б:Б (Головна сторона): створюється таблиця зв'язку links
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "driver_truck_links",
            joinColumns = @JoinColumn(name = "driver_id"),
            inverseJoinColumns = @JoinColumn(name = "truck_id")
    )
    private List<Truck> trucks = new ArrayList<>();

    public Driver() {}
    public Driver(String name) { this.name = name; }

    public void addTrip(TripRequest trip) {
        trips.add(trip);
        trip.setDriver(this);
    }

    public void addTruck(Truck truck) {
        trucks.add(truck);
        truck.getDrivers().add(this);
    }

    // Getters/ Setters
    public Long getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public List<TripRequest> getTrips() { return trips; }
    public List<Truck> getTrucks() { return trucks; }
}