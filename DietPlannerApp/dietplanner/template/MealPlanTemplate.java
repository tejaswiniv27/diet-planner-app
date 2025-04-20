package dietplanner.template;

import java.util.*;

import dietplanner.model.Meal;
import java.util.List;

// Template Method Pattern for meal plan generation
public abstract class MealPlanTemplate {

  // Template method defining the algorithm structure
  public final List<Meal> createMealPlan(int dailyCalories) {
    List<Meal> breakfast = selectBreakfast(dailyCalories);
    List<Meal> lunch = selectLunch(dailyCalories);
    List<Meal> dinner = selectDinner(dailyCalories);

    return combineMeals(breakfast, lunch, dinner);
  }

  // These methods will be implemented by concrete classes
  protected abstract List<Meal> selectBreakfast(int dailyCalories);

  protected abstract List<Meal> selectLunch(int dailyCalories);

  protected abstract List<Meal> selectDinner(int dailyCalories);

  // Common method for all subclasses
  protected List<Meal> combineMeals(List<Meal> breakfast, List<Meal> lunch, List<Meal> dinner) {
    List<Meal> plan = new ArrayList<>();

    // Add one random meal from each list
    if (!breakfast.isEmpty())
      plan.add(breakfast.get(new Random().nextInt(breakfast.size())));
    if (!lunch.isEmpty())
      plan.add(lunch.get(new Random().nextInt(lunch.size())));
    if (!dinner.isEmpty())
      plan.add(dinner.get(new Random().nextInt(dinner.size())));

    return plan;
  }
}
