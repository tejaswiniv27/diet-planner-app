package dietplanner.model;

public class Meal {
  private int id;
  private String name;
  private String type; // BREAKFAST, LUNCH, DINNER
  private int calories;
  private int proteins;
  private int carbs;
  private int fats;

  public Meal(String name, String type, int calories, int proteins, int carbs, int fats) {
    this.name = name;
    this.type = type;
    this.calories = calories;
    this.proteins = proteins;
    this.carbs = carbs;
    this.fats = fats;
  }

  // Getters and setters
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public int getCalories() {
    return calories;
  }

  public void setCalories(int calories) {
    this.calories = calories;
  }

  public int getProteins() {
    return proteins;
  }

  public void setProteins(int proteins) {
    this.proteins = proteins;
  }

  public int getCarbs() {
    return carbs;
  }

  public void setCarbs(int carbs) {
    this.carbs = carbs;
  }

  public int getFats() {
    return fats;
  }

  public void setFats(int fats) {
    this.fats = fats;
  }

  @Override
  public String toString() {
    return name + " (" + calories + " cal)";
  }
}
