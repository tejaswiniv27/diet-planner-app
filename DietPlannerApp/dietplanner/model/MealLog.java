package dietplanner.model;

import java.util.Date;

public class MealLog {
  private int id;
  private int userId;
  private int mealId;
  private Date date;
  private String mealName;
  private int calories;

  public MealLog(int userId, int mealId, Date date, String mealName, int calories) {
    this.userId = userId;
    this.mealId = mealId;
    this.date = date;
    this.mealName = mealName;
    this.calories = calories;
  }

  // Getters and setters
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public int getMealId() {
    return mealId;
  }

  public void setMealId(int mealId) {
    this.mealId = mealId;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public String getMealName() {
    return mealName;
  }

  public void setMealName(String mealName) {
    this.mealName = mealName;
  }

  public int getCalories() {
    return calories;
  }

  public void setCalories(int calories) {
    this.calories = calories;
  }
}
