package dietplanner.strategy;

public class CalorieCalculatorContext {
  private CalorieCalculator calculator;

  public void setCalculator(CalorieCalculator calculator) {
    this.calculator = calculator;
  }

  public CalorieCalculator getCalculator(String goal) {
    switch (goal) {
      case "WEIGHT_LOSS":
        return new WeightLossCalculator();
      case "MAINTENANCE":
        return new MaintenanceCalculator();
      case "MUSCLE_GAIN":
        return new MuscleGainCalculator();
      default:
        return new MaintenanceCalculator();
    }
  }

  public int calculateCalories(User user, String goal) {
    setCalculator(getCalculator(goal));
    return calculator.calculateDailyCalories(user);
  }
}