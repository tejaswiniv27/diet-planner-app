package dietplanner.template;

import dietplanner.dao.MealDAO;
import dietplanner.model.Meal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

// Concrete implementation for maintenance meal plan
public class MaintenanceMealPlan extends MealPlanTemplate {
  private MealDAO mealDAO;

  public MaintenanceMealPlan() {
    this.mealDAO = new MealDAO();
  }

  @Override
  protected List<Meal> selectBreakfast(int dailyCalories) {
    // Get all breakfast meals
    List<Meal> allBreakfasts = mealDAO.getMealsByType("BREAKFAST");

    // Filter for balanced breakfast options (approximately 25% of daily calories)
    int targetBreakfastCalories = (int) (dailyCalories * 0.25);
    int minCalories = targetBreakfastCalories - 100;
    int maxCalories = targetBreakfastCalories + 100;

    return allBreakfasts.stream()
        .filter(meal -> meal.getCalories() >= minCalories && meal.getCalories() <= maxCalories)
        .collect(Collectors.toList());
  }

  @Override
  protected List<Meal> selectLunch(int dailyCalories) {
    // Get all lunch meals
    List<Meal> allLunches = mealDAO.getMealsByType("LUNCH");

    // Filter for balanced lunch options (approximately 35% of daily calories)
    int targetLunchCalories = (int) (dailyCalories * 0.35);
    int minCalories = targetLunchCalories - 150;
    int maxCalories = targetLunchCalories + 150;

    return allLunches.stream()
        .filter(meal -> meal.getCalories() >= minCalories && meal.getCalories() <= maxCalories)
        .collect(Collectors.toList());
  }

  @Override
  protected List<Meal> selectDinner(int dailyCalories) {
    // Get all dinner meals
    List<Meal> allDinners = mealDAO.getMealsByType("DINNER");

    // Filter for balanced dinner options (approximately 35% of daily calories)
    int targetDinnerCalories = (int) (dailyCalories * 0.35);
    int minCalories = targetDinnerCalories - 150;
    int maxCalories = targetDinnerCalories + 150;

    return allDinners.stream()
        .filter(meal -> meal.getCalories() >= minCalories && meal.getCalories() <= maxCalories)
        .collect(Collectors.toList());
  }
}
