package ua.autostation.service;

// Стратегія 2: Крихкий вантаж
public class FragileCargoStrategy implements CostCalculationStrategy {
    @Override
    public double calculate(int weight) { return weight * 8500.0; }
}
