package ua.autostation.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "passports")
public class Passport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "serial_number", nullable = false, unique = true)
    private String serialNumber;

    // Зв'язок до вантажівки
    @OneToOne(mappedBy = "passport")
    private Truck truck;

    public Passport() {}
    public Passport(String serialNumber) { this.serialNumber = serialNumber; }

    // Getters and Setters
    public Long getId() { return id; }
    public String getSerialNumber() { return serialNumber; }
    public void setSerialNumber(String serialNumber) { this.serialNumber = serialNumber; }
    public Truck getTruck() { return truck; }
    public void setTruck(Truck truck) { this.truck = truck; }
}