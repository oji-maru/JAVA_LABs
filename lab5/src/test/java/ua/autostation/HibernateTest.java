package ua.autostation;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ua.autostation.entity.*;
import ua.autostation.util.HibernateUtil;

public class HibernateTest {

    @Test
    public void testAutobaseDatabaseStructure() {
        // Відкриваємо одну сесію на весь тест
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            // Створюємо дані
            Passport passport = new Passport("AA-998811");
            Truck volvo = new Truck("Volvo FH16", true, passport);
            Driver driver = new Driver("Олексій Коваленко");

            driver.addTrip(new TripRequest("Одеса Порт", 20));
            driver.addTrip(new TripRequest("Львів Термінал", 18));
            driver.addTruck(volvo);

            // Зберігаємо
            session.persist(driver);
            transaction.commit();

            session.clear(); // Очищуємо кеш Hibernate, щоб він зробив SELECT з MySQL

            // Вичитуємо назад поки сесія відкрита
            Driver dbDriver = session.get(Driver.class, driver.getId());

            Assertions.assertNotNull(dbDriver);
            Assertions.assertEquals(2, dbDriver.getTrips().size());
            Assertions.assertEquals(1, dbDriver.getTrucks().size());

            System.out.println(">>> [JUnit Успіх]: Тест на MySQL пройдено");
        }
    }
}