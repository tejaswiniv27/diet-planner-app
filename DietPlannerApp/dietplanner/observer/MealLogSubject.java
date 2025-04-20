package dietplanner.observer;

import dietplanner.model.MealLog;

// Concrete Subject for meal logging
public class MealLogSubject extends Subject {
  public void logMeal(MealLog mealLog) {
    // Notify all observers about the new meal log
    notifyObservers(mealLog);
  }
}
