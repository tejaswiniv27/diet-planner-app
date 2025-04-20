package dietplanner.template;

import dietplanner.dao.MealDAO;
import dietplanner.model.Meal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

// Concrete implementation for weight loss meal plan
public class WeightLossMealPlan extends MealPlanTemplate {
  private MealDAO mealDAO;

  public WeightLossMealPlan() {
    this.mealDAO = new MealDAO();
  }

  @Override
  protected List<Meal> selectBreakfast(int dailyCalories) {
    // Get all breakfast meals
    List<Meal> allBreakfasts = mealDAO.getMealsByType("BREAKFAST");

    // Filter for low-calorie breakfast options (approximately 20% of daily
    // calories)
    int maxBreakfastCalories = (int) (dailyCalories * 0.25);
    return allBreakfasts.stream()
        .filter(meal -> meal.getCalories() <= maxBreakfastCalories)
        .collect(Collectors.toList());
  }

  @Override
  protected List<Meal> selectLunch(int dailyCalories) {
    // Get all lunch meals
    List<Meal> allLunches = mealDAO.getMealsByType("LUNCH");

    // Filter for moderate-calorie lunch options (approximately 30% of daily
    // calories)
    int maxLunchCalories = (int) (dailyCalories * 0.35);
    return allLunches.stream()
        .filter(meal -> meal.getCalories() <= maxLunchCalories)
        .collect(Collectors.toList());
  }

  @Override
  protected List<Meal> selectDinner(int dailyCalories) {
    // Get all dinner meals
    List<Meal> allDinners = mealDAO.getMealsByType("DINNER");

    // Filter for moderate-calorie dinner options (approximately 30% of daily
    // calories)
    int maxDinnerCalories = (int) (dailyCalories * 0.35);
    return allDinners.stream()
        .filter(meal -> meal.getCalories() <= maxDinnerCalories)
        .collect(Collectors.toList());
  }
}
