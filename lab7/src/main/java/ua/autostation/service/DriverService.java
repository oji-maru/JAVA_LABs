package ua.autostation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.autostation.entity.Driver;
import ua.autostation.entity.TripRequest;
import ua.autostation.repository.DriverRepository;
import ua.autostation.repository.TripRequestRepository;

import java.util.List;

@Service
public class DriverService {

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private TripRequestRepository tripRequestRepository;

    public List<Driver> getAllDrivers() {
        return driverRepository.findAll();
    }

    @Transactional
    public void createDriverWithTrip(String driverName, String destination, int weight, String cargoType) {
        // Створюємо водія
        Driver driver = new Driver(driverName);

        // Визначаємо стратегію розрахунку ціни (Strategy)
        CostCalculationStrategy strategy = cargoType.equalsIgnoreCase("fragile")
                ? new FragileCargoStrategy()
                : new StandardCargoStrategy();

        double calculatedPrice = strategy.calculate(weight);

        // Збираємо об'єкт рейсу через Паттерн Builder
        TripRequest trip = new TripRequest.Builder()
                .destination(destination)
                .weight(weight)
                .price(calculatedPrice)
                .build();

        // Пов'язуємо об'єкти
        driver.addTrip(trip);

        // Зберігаємо в БД через репозиторій
        driverRepository.save(driver);
    }

    public void deleteDriver(Long id) {
        driverRepository.deleteById(id);
    }
}