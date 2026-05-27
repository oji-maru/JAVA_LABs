package ua.autostation;

import org.hibernate.Session;
import ua.autostation.entity.Driver;
import ua.autostation.entity.Truck;
import ua.autostation.entity.Passport;
import ua.autostation.util.HibernateUtil;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== ЗАПУСК СИСТЕМИ АВТОБАЗА ===");

        // 1. Перевіряємо підключення до БД та ініціалізацію фабрики
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            System.out.println("-> [Успіх] Підключення до бази даних MySQL встановлено успішно!");

            // 2. Виводимо тестове повідомлення про готовність
            System.out.println("-> [Успіх] Структура таблиць (Trucks, Drivers, Passports, TripRequests) сформована.");

        } catch (Exception e) {
            System.err.println("-> [Помилка] Не вдалося запустити Hibernate:");
            e.printStackTrace();
        }

        // 3. Закриваємо фабрику сесій при завершенні програми
        HibernateUtil.getSessionFactory().close();
        System.out.println("=== ПРОГРАМУ ЗАВЕРШЕНО ===");
    }
}