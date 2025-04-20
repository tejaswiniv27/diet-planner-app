package dietplanner.dao;

import dietplanner.db.DatabaseConnection;
import dietplanner.model.Meal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MealDAO {

  public List<Meal> getMealsByType(String type) {
    List<Meal> meals = new ArrayList<>();
    String sql = "SELECT * FROM meals WHERE type = ?";

    try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {

      pstmt.setString(1, type);

      try (ResultSet rs = pstmt.executeQuery()) {
        while (rs.next()) {
          Meal meal = new Meal(
              rs.getString("name"),
              rs.getString("type"),
              rs.getInt("calories"),
              rs.getInt("proteins"),
              rs.getInt("carbs"),
              rs.getInt("fats"));
          meal.setId(rs.getInt("id"));
          meals.add(meal);
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return meals;
  }

  public Meal getMealById(int id) {
    String sql = "SELECT * FROM meals WHERE id = ?";

    try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {

      pstmt.setInt(1, id);

      try (ResultSet rs = pstmt.executeQuery()) {
        if (rs.next()) {
          Meal meal = new Meal(
              rs.getString("name"),
              rs.getString("type"),
              rs.getInt("calories"),
              rs.getInt("proteins"),
              rs.getInt("carbs"),
              rs.getInt("fats"));
          meal.setId(rs.getInt("id"));
          return meal;
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }
}

// File: src/main/java/dietplanner/dao/MealLogDAO.java
