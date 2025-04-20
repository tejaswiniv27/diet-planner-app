package dietplanner.strategy;

public class WeightLossCalculator implements CalorieCalculator {
  @Override
  public int calculateDailyCalories(User user) {
    // Calculate BMR (Basal Metabolic Rate)
    double bmr;
    if (user.getGender().equalsIgnoreCase("MALE")) {
      bmr = 10 * user.getWeight() + 6.25 * user.getHeight() - 5 * user.getAge() + 5;
    } else {
      bmr = 10 * user.getWeight() + 6.25 * user.getHeight() - 5 * user.getAge() - 161;
    }

    // Apply activity multiplier
    double activityMultiplier = getActivityMultiplier(user.getActivityLevel());
    double maintenance = bmr * activityMultiplier;

    // For weight loss, subtract 500 calories from maintenance
    return (int) (maintenance - 500);
  }

  private double getActivityMultiplier(String activityLevel) {
    switch (activityLevel) {
      case "SEDENTARY":
        return 1.2;
      case "LIGHTLY_ACTIVE":
        return 1.375;
      case "MODERATELY_ACTIVE":
        return 1.55;
      case "VERY_ACTIVE":
        return 1.725;
      case "EXTRA_ACTIVE":
        return 1.9;
      default:
        return 1.2;
    }
  }
}