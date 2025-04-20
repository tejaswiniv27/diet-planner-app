package dietplanner.template;

public class MealPlanFactory {
  public static MealPlanTemplate getMealPlan(String goal) {
    switch (goal) {
      case "WEIGHT_LOSS":
        return new WeightLossMealPlan();
      case "MAINTENANCE":
        return new MaintenanceMealPlan();
      case "MUSCLE_GAIN":
        return new MuscleGainMealPlan();
      default:
        return new MaintenanceMealPlan();
    }
  }
}
