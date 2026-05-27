package ua.autostation.service;

// Стратегія 1: Стандартний вантаж
public class StandardCargoStrategy implements CostCalculationStrategy {
    @Override
    public double calculate(int weight) { return weight * 5000.0; }
}