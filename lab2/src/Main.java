// 1. Клас Автомобіль
class Car {
  private String model; // модель автомобіля
  private int maxCapacity; // максимальний розмір вантажу в тоннах
  private boolean isWorking; // стан: справна чи ні

  public Car(String model, int maxCapacity, boolean isWorking) {
    this.model = model;
    this.maxCapacity = maxCapacity;
    this.isWorking = isWorking;
  } //конструктор класу

  public String getModel() { return model; }
  public int getMaxCapacity() { return maxCapacity; }
  public boolean isWorking() { return isWorking; }

  public void setWorking(boolean working) { this.isWorking = working; }
}

// 2. Клас Водій (Асоціація: Водій має автомобіль)
class Driver {
  private String name;
  private Car car; // Поле-посилання на інший клас

  public Driver(String name, Car car) {
    this.name = name;
    this.car = car;
  }

  public String getName() { return name; }
  public Car getCar() { return car; }
}

// 3. Клас Рейс (Асоціація: Рейс має Водія, який керує Автомобілем)
class Trip {
  private String destination;
  private String departure;
  private int requiredCapacity;
  private Driver assignedDriver;
  private boolean isCompleted;

  public Trip(String destination, String departure, int requiredCapacity) {
    this.departure = departure;
    this.destination = destination;
    this.requiredCapacity = requiredCapacity;
    this.isCompleted = false;
  }

  // Логіка Диспетчера: перевірка умов та призначення водія
  public boolean assignDriver(Driver driver) {
    Car car = driver.getCar();

    if (!car.isWorking()) {
      System.out.println("Помилка: Автомобіль водія " + driver.getName() + " несправний!");
      return false;
    }
    if (car.getMaxCapacity() < this.requiredCapacity) {
      System.out.println("Помилка: Автомобіль водія " + driver.getName() + " замалий для цього рейсу.");
      return false;
    }

    this.assignedDriver = driver;
    System.out.println("Диспетчер: Водія " + driver.getName() + " успішно призначено на рейс з " + departure + " до " + destination);
    return true;
  }

  // Водій робить позначку про виконання
  public void completeTrip(boolean carStatusAfterTrip) {
    if (assignedDriver == null) {
      System.out.println("Неможливо завершити рейс: водія не призначено.");
      return;
    }
    this.isCompleted = true;
    this.assignedDriver.getCar().setWorking(carStatusAfterTrip);

    System.out.println("\n--- Звіт водія ---");
    System.out.println("Водій " + assignedDriver.getName() + " виконав рейс до " + destination);
    System.out.println("Стан автомобіля після рейсу: " + (carStatusAfterTrip ? "Справний" : "Потребує ремонту"));
  }
}

// Головний клас для демонстрації (Створення об'єктів)
public class Main {
  public static void main(String[] args) {
    // Створюємо об'єкти автомобілів (Умова 1: Створити об'єкти)
    Car truck1 = new Car("Volvo FH", 20, true);
    Car truck2 = new Car("Газель", 2, false); // зламана

    // Створюємо об'єкти водіїв із прив'язкою до авто
    Driver driver1 = new Driver("Олег", truck1);
    Driver driver2 = new Driver("Ігор", truck2);

    // Створюємо об'єкт рейсу (потрібно перевезти 15 тонн до Львова)
    Trip tripToLvivFromKyiv = new Trip("Львів", "Київ", 15);

    System.out.println("=== Спроба 1: Призначення Ігоря на зламаній Газелі ===");
    tripToLvivFromKyiv.assignDriver(driver2);

    System.out.println("\n=== Спроба 2: Призначення Олега на Volvo ===");
    if (tripToLvivFromKyiv.assignDriver(driver1)) {
      // Водій виконує рейс і звітує, що машина в порядку
      tripToLvivFromKyiv.completeTrip(true);
    }
  }
}