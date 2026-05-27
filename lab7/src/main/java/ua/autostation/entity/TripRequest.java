package ua.autostation.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "trip_requests")
public class TripRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Пункт призначення не може бути порожнім")
    private String destination;

    @Min(value = 1, message = "Вага вантажу повинна бути не менше 1 тонни")
    private int weight;

    private double price; // Розраховується через бізнес-логіку (паттерн Strategy)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id")
    private Driver driver;

    // Конструктор за замовчуванням для JPA
    public TripRequest() {}

    // Приватний конструктор для Builder
    private TripRequest(Builder builder) {
        this.destination = builder.destination;
        this.weight = builder.weight;
        this.price = builder.price;
        this.driver = builder.driver;
    }

    // Патерн GOF: Builder
    public static class Builder {
        private String destination;
        private int weight;
        private double price;
        private Driver driver;

        public Builder destination(String destination) {
            this.destination = destination;
            return this;
        }

        public Builder weight(int weight) {
            this.weight = weight;
            return this;
        }

        public Builder price(double price) {
            this.price = price;
            return this;
        }

        public Builder driver(Driver driver) {
            this.driver = driver;
            return this;
        }

        public TripRequest build() {
            return new TripRequest(this);
        }
    }

    // Геттери та сеттери
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }
    public int getWeight() { return weight; }
    public void setWeight(int weight) { this.weight = weight; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public Driver getDriver() { return driver; }
    public void setDriver(Driver driver) { this.driver = driver; }
}