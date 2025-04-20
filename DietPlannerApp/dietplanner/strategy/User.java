package dietplanner.strategy;

public class User {
  private int age;
  private String gender;
  private double weight; // in kg
  private double height; // in cm
  private String activityLevel; // SEDENTARY, LIGHTLY_ACTIVE, MODERATELY_ACTIVE, VERY_ACTIVE, EXTRA_ACTIVE

  public User(int age, String gender, double weight, double height, String activityLevel) {
    this.age = age;
    this.gender = gender;
    this.weight = weight;
    this.height = height;
    this.activityLevel = activityLevel;
  }

  // Getters
  public int getAge() {
    return age;
  }

  public String getGender() {
    return gender;
  }

  public double getWeight() {
    return weight;
  }

  public double getHeight() {
    return height;
  }

  public String getActivityLevel() {
    return activityLevel;
  }
}