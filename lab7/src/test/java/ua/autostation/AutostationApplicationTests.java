package ua.autostation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ua.autostation.entity.Driver;
import ua.autostation.repository.DriverRepository;
import ua.autostation.service.DriverService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
class AutostationApplicationTests {

    @Autowired
    private DriverService driverService;

    @Autowired
    private DriverRepository driverRepository;

    @Test
    @Transactional
    void testCreateDriverAndCalculatePriceStrategy() {
        // Очищаємо попередні тестові дані
        driverRepository.deleteAll();

        // Створюємо водія з крихким вантажем (вага 10 тонн, ціна має бути 10 * 8500 = 85000)
        driverService.createDriverWithTrip("Тестовий Водій JUnit", "Київ-Львів", 10, "fragile");

        // Зчитуємо дані з БД через репозиторій
        List<Driver> drivers = driverRepository.findAll();

        // Тести
        Assertions.assertFalse(drivers.isEmpty(), "Дані не були збережені у MySQL!");
        Assertions.assertEquals(1, drivers.size());
        Assertions.assertEquals("Тестовий Водій JUnit", drivers.get(0).getName());

        // Перевіряємо точність роботи патернів Builder та Strategy під капотом
        Assertions.assertEquals(85000.0, drivers.get(0).getTrips().get(0).getPrice(),
                "Помилка в розрахунку паттерну Strategy!");
    }
}