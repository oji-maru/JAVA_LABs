package ua.autostation.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "trucks")
public class Truck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String model;
    private boolean isConditionOk; // Стан автомобіля (справний/несправний)

    // 1. Демонстрація 1:1 : за вантажівкою закріплено один техпаспорт
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "passport_id", referencedColumnName = "id")
    private Passport passport;

    // 2. Демонстрація Б:Б : Водії та Вантажівки ділять зміни
    @ManyToMany(mappedBy = "trucks")
    private List<Driver> drivers = new ArrayList<>();

    public Truck() {}
    public Truck(String model, boolean isConditionOk, Passport passport) {
        this.model = model;
        this.isConditionOk = isConditionOk;
        this.passport = passport;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    public boolean isConditionOk() { return isConditionOk; }
    public void setConditionOk(boolean conditionOk) { isConditionOk = conditionOk; }
    public Passport getPassport() { return passport; }
    public void setPassport(Passport passport) { this.passport = passport; }
    public List<Driver> getDrivers() { return drivers; }
}