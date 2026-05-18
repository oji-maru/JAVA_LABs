import java.util.HashMap;
import java.util.Map;

// ==========================================
// ЧАСТИНА FLYWEIGHT (ОПТИМІЗАЦІЯ ПАМ'ЯТІ)
// ==========================================

// Сам Flyweight зберігає спільні незмінні дані моделі
class TruckModelFlyweight {
    private String modelName;
    private int baseCapacity;

    public TruckModelFlyweight(String modelName, int baseCapacity) {
        this.modelName = modelName;
        this.baseCapacity = baseCapacity;
        System.out.println("-> [Flyweight] Створено новий тип моделі в пам'яті: " + modelName);
    }

    public String getModelName() { return modelName; }
    public int getBaseCapacity() { return baseCapacity; }
}

// FlyweightFactory гарантує, що об'єкти моделей не дублюються
class FlyweightFactory {
    private static Map<String, TruckModelFlyweight> truckModels = new HashMap<>();

    public static TruckModelFlyweight getModel(String modelName, int capacity) {
        // Якщо така модель уже є в базі, ми повертаємо її, а не створюємо нову
        if (!truckModels.containsKey(modelName)) {
            truckModels.put(modelName, new TruckModelFlyweight(modelName, capacity));
        }
        return truckModels.get(modelName);
    }
}

// ==========================================
// БАЗОВІ ІНТЕРФЕЙСИ ТА ДЕКОРАТОРИ
// ==========================================

interface Transport {
    String getDescription();
    int getCargoCapacity();
    boolean isEligibleForTrip(int requiredCapacity, boolean needsRefrigeration);
}

// Конкретний компонент тепер використовуж Flyweight
class BasicTruck implements Transport {
    private String licensePlate; // Унікальне для кожної машини (зовнішній стан)
    private TruckModelFlyweight modelType; // Спільне для тисяч машин (внутрішній стан)

    public BasicTruck(String licensePlate, TruckModelFlyweight modelType) {
        this.licensePlate = licensePlate;
        this.modelType = modelType;
    }

    @Override
    public String getDescription() {
        return "Вантажівка " + modelType.getModelName() + " [Номер: " + licensePlate + "]";
    }

    @Override
    public int getCargoCapacity() {
        return modelType.getBaseCapacity();
    }

    @Override
    public boolean isEligibleForTrip(int requiredCapacity, boolean needsRefrigeration) {
        if (needsRefrigeration) return false;
        return modelType.getBaseCapacity() >= requiredCapacity;
    }
}

// Базовий декоратор (залишається незмінним)
abstract class TruckDecorator implements Transport {
    protected Transport decoratedTruck;
    public TruckDecorator(Transport truck) { this.decoratedTruck = truck; }
    @Override public String getDescription() { return decoratedTruck.getDescription(); }
    @Override public int getCargoCapacity() { return decoratedTruck.getCargoCapacity(); }
    @Override public boolean isEligibleForTrip(int r, boolean n) { return decoratedTruck.isEligibleForTrip(r, n); }
}

// Конкретний декоратор (рефрижератор)
class RefrigeratorDecorator extends TruckDecorator {
    public RefrigeratorDecorator(Transport truck) { super(truck); }
    @Override
    public String getDescription() { return super.getDescription() + " + Холодильник"; }
    @Override
    public boolean isEligibleForTrip(int requiredCapacity, boolean needsRefrigeration) {
        return super.getCargoCapacity() >= requiredCapacity;
    }
}

// ==========================================
// ТОЧКА ВХОДУ, ГОЛОВНИЙ КЛАС
// ==========================================
public class Main {
    public static void main(String[] args) {
        System.out.println("=== КРОК 1: Робота фабрики Flyweight ===");

        // Отримуємо модель
        TruckModelFlyweight volvoType = FlyweightFactory.getModel("Volvo FH", 20);

        // Створюємо 3 різні фізичні вантажівки з різними номерами, але вони ділять 1 об'єкт моделі в пам'яті!
        Transport truck1 = new BasicTruck("AA1121BB", volvoType);
        Transport truck2 = new BasicTruck("AM2442CO", volvoType);
        Transport truck3 = new BasicTruck("BC3393AI", volvoType);

        // Спробуємо знову отримати "Volvo FH". Повідомлення про створення нового об'єкта не з'явиться
        TruckModelFlyweight duplicateVolvoType = FlyweightFactory.getModel("Volvo FH", 20);

        System.out.println("\n=== КРОК 2: Робота Декоратора ===");
        // Беремо truck1 і динамічно покращуємо її рефрижератором
        Transport upgradedTruck1 = new RefrigeratorDecorator(truck1);

        System.out.println(upgradedTruck1.getDescription());
        System.out.println("Чи підходить truck1 для 15т з охолодженням? " + (upgradedTruck1.isEligibleForTrip(15, true) ? "Так" : "Ні"));
        System.out.println("Чи підходить звичайна truck2 для 15т з охолодженням? " + (truck2.isEligibleForTrip(15, true) ? "Так" : "Ні"));
    }
}