import java.util.ArrayList;
import java.util.List;

// ==========================================
// 1. ПАТЕРН STATE (УПРАВЛІННЯ СТАНАМИ АВТІВКИ)
// ==========================================

// Спільний інтерфейс для всіх можливих станів ТЗ
interface TruckState {
    void assignToTrip(Truck truck, String tripName);
    void completeTrip(Truck truck);
    void sendToRepair(Truck truck);
    String getStateName();
}

// Перший стан ТЗ (вільний і справний в автопарку)
class IdleState implements TruckState {
    @Override
    public void assignToTrip(Truck truck, String tripName) {
        System.out.println("-> [Успіх] Автомобіль " + truck.getModel() + " успішно призначено на рейс: " + tripName);
        // ТЗ переходить у стан "В рейсі"
        truck.changeState(new InTripState(tripName));
    }

    @Override
    public void completeTrip(Truck truck) {
        System.out.println("-> [Помилка] Неможливо завершити рейс: машина зараз знаходиться в автопарку.");
    }

    @Override
    public void sendToRepair(Truck truck) {
        System.out.println("-> [Ремонт] Автомобіль " + truck.getModel() + " відправлено в ремонтну зону.");
        truck.changeState(new InRepairState());
    }

    @Override
    public String getStateName() { return "Вільний (Справний)"; }
}

// Другий стан ТЗ (виконує рейс на дорозі)
class InTripState implements TruckState {
    private String currentTrip;

    public InTripState(String currentTrip) { this.currentTrip = currentTrip; }

    @Override
    public void assignToTrip(Truck truck, String tripName) {
        System.out.println("-> [Помилка] Автомобіль вже виконує рейс: " + currentTrip);
    }

    @Override
    public void completeTrip(Truck truck) {
        System.out.println("-> [Звіт] Водій успішно завершив рейс '" + currentTrip + "'. Машина повернулася.");
        truck.changeState(new IdleState()); // Повертається в режим очікування
    }

    @Override
    public void sendToRepair(Truck truck) {
        System.out.println("-> [Помилка] Машина на трасі! Не можна відправити в ремонт до завершення рейсу.");
    }

    @Override
    public String getStateName() { return "В рейсі [" + currentTrip + "]"; }
}

// Третій стан (ТЗ зламане, знаходиться на ремонтній базі)
class InRepairState implements TruckState {
    @Override
    public void assignToTrip(Truck truck, String tripName) {
        System.out.println("-> [Помилка] Заявку відхилено! Машина ремонтується.");
    }

    @Override
    public void completeTrip(Truck truck) {
        System.out.println("-> [Помилка] Неможливо завершити рейс, якого не було! :) Машина ремонтується.");
    }

    @Override
    public void sendToRepair(Truck truck) {
        System.out.println("-> [Попередження] Машина вже ремонтується!");
    }

    @Override
    public String getStateName() { return "В ремонті (Несправна)"; }
}

// ==========================================
// 2. КОНТЕКСТ СИСТЕМИ (ОБ'ЄКТ АВТОМОБІЛЯ)
// ==========================================

class Truck {
    private String model;
    private int capacity; // Вантажопідйомність в тоннах
    private TruckState currentState; // Посилання на інтерфейс стану

    public Truck(String model, int capacity) {
        this.model = model;
        this.capacity = capacity;
        this.currentState = new IdleState(); // Початковий стан при створенні
    }

    // Метод, який дозволяє станам перемикати один одного всередині об'єкта
    public void changeState(TruckState newState) {
        this.currentState = newState;
    }

    // Делегування дій поточному стану
    public void assignToTrip(String tripName, int requiredCapacity) {
        // Додаткова перевірка характеристик перед запуском логіки стану
        if (this.capacity < requiredCapacity) {
            System.out.println("-> [Відхилено диспетчером] Характеристики машини не відповідають заявці. Потрібно: " + requiredCapacity + "т");
            return;
        }
        currentState.assignToTrip(this, tripName);
    }

    public void completeTrip() { currentState.completeTrip(this); }
    public void sendToRepair() { currentState.sendToRepair(this); }

    public String getModel() { return model; }
    public String getStatusInfo() { return model + " (" + capacity + "т) - Поточний стан: " + currentState.getStateName(); }
}

// ==========================================
// 3. ТЕСТУВАННЯ
// ==========================================

public class Main {
    public static void main(String[] args) {
        System.out.println("=== ІНІЦІАЛІЗАЦІЯ АВТОПАРКУ АВТОБАЗИ ===");
        Truck volvo = new Truck("Volvo FH16", 20);
        Truck man = new Truck("MAN TGX", 10);

        System.out.println(volvo.getStatusInfo());
        System.out.println(man.getStatusInfo());

        System.out.println("\n=== РЕЙС 1: Спроба відправити MAN на важку заявку (15 тонн) ===");
        man.assignToTrip("Доставка металу в Київ", 15);

        System.out.println("\n=== РЕЙС 2: Спроба відправити Volvo на відповідну заявку (18 тонн) ===");
        volvo.assignToTrip("Експорт зерна в Одесу", 18);
        System.out.println(volvo.getStatusInfo());

        System.out.println("\n=== РЕЙС 3: Спроба диспетчера дати нову роботу для Volvo, поки вона в дорозі ===");
        volvo.assignToTrip("Доставка техніки", 5);

        System.out.println("\n=== ЗВІТ ВОДІЯ: Водій Volvo завершує рейс ===");
        volvo.completeTrip();
        System.out.println(volvo.getStatusInfo());

        System.out.println("\n=== ТЕХНІЧНЕ ОБСЛУГОВУВАННЯ: Відправка MAN на ремонт ===");
        man.sendToRepair();
        System.out.println(man.getStatusInfo());

        System.out.println("\n=== РЕЙС 4: Спроба диспетчера дати заявку на MAN, поки він в ремонті ===");
        man.assignToTrip("Доставка продуктів", 5);
    }
}