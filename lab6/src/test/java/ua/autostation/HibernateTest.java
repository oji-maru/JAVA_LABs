package ua.autostation;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ua.autostation.entity.*;
import ua.autostation.util.HibernateUtil;

//LB6
import ua.autostation.dao.GenericDAO;
import java.util.List;

public class HibernateTest {

    @Test
    public void testNativeSqlQuery() {
        GenericDAO<Driver> driverDAO = new GenericDAO<>(Driver.class);

        // Додаємо тестового водія
        Driver driver = new Driver("Василь Стус");
        driverDAO.save(driver);

        // Тестуємо Native SQL метод пошуку за іменем
        System.out.println(">>> ЗАПУСК ТЕСТУ: testNativeSqlQuery");
        List<Driver> foundDrivers = driverDAO.findDriversByNameNative("Василь");

        // Перевірки результату
        Assertions.assertFalse(foundDrivers.isEmpty(), "Водія не знайдено через Native SQL!");
        Assertions.assertEquals("Василь Стус", foundDrivers.get(0).getName());
        System.out.println("-> [Успіх] Native SQL знайшов водія: " + foundDrivers.get(0).getName());
    }

    @Test
    public void testHqlQuery() {
        GenericDAO<Driver> driverDAO = new GenericDAO<>(Driver.class);
        GenericDAO<TripRequest> tripDAO = new GenericDAO<>(TripRequest.class);

        //  Створюємо водія та додаємо йому рейс
        Driver driver = new Driver("Ліна Костенко");
        TripRequest trip = new TripRequest("Харків Центр", 12);
        driver.addTrip(trip);

        // Зберігаємо
        driverDAO.save(driver);

        // Тестуємо HQL метод пошуку за пунктом призначення
        System.out.println(">>> ЗАПУСК ТЕСТУ: testHqlQuery");
        List<TripRequest> foundTrips = driverDAO.findTripsByDestinationHQL("Харків Центр");

        // Перевірки результату
        Assertions.assertFalse(foundTrips.isEmpty(), "Рейс не знайдено через HQL!");
        Assertions.assertEquals(1, foundTrips.size(), "Кількість знайдених рейсів має бути рівною 1");
        Assertions.assertEquals("Ліна Костенко", foundTrips.get(0).getDriver().getName(), "Рейс прив'язаний до невірного водія!");
        System.out.println("-> [Успіх] HQL знайшов рейс куди: " + foundTrips.get(0).getDestination());
    }
}