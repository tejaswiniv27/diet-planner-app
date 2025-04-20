package dietplanner.template;

import dietplanner.dao.MealDAO;
import dietplanner.model.Meal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

// Concrete implementation for muscle gain meal plan
public class MuscleGainMealPlan extends MealPlanTemplate {
  private MealDAO mealDAO;

  public MuscleGainMealPlan() {
    this.mealDAO = new MealDAO();
  }

  @Override
  protected List<Meal> selectBreakfast(int dailyCalories) {
    // Get all breakfast meals
    List<Meal> allBreakfasts = mealDAO.getMealsByType("BREAKFAST");

    // Filter for protein-rich breakfast options (approximately 25% of daily
    // calories)
    int targetBreakfastCalories = (int) (dailyCalories * 0.25);
    int minCalories = targetBreakfastCalories - 50;

    return allBreakfasts.stream()
        .filter(meal -> meal.getCalories() >= minCalories && meal.getProteins() >= 20)
        .collect(Collectors.toList());
  }

  @Override
  protected List<Meal> selectLunch(int dailyCalories) {
    // Get all lunch meals
    List<Meal> allLunches = mealDAO.getMealsByType("LUNCH");

    // Filter for protein-rich lunch options (approximately 35% of daily calories)
    int targetLunchCalories = (int) (dailyCalories * 0.35);
    int minCalories = targetLunchCalories - 100;

    return allLunches.stream()
        .filter(meal -> meal.getCalories() >= minCalories && meal.getProteins() >= 30)
        .collect(Collectors.toList());
  }

  @Override
  protected List<Meal> selectDinner(int dailyCalories) {
    // Get all dinner meals
    List<Meal> allDinners = mealDAO.getMealsByType("DINNER");

    // Filter for protein-rich dinner options (approximately 35% of daily calories)
    int targetDinnerCalories = (int) (dailyCalories * 0.35);
    int minCalories = targetDinnerCalories - 100;

    return allDinners.stream()
        .filter(meal -> meal.getCalories() >= minCalories && meal.getProteins() >= 30)
        .collect(Collectors.toList());
  }
}
