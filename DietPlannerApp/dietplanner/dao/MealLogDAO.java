
package dietplanner.dao;

import dietplanner.db.DatabaseConnection;
import dietplanner.model.MealLog;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MealLogDAO {

  public boolean logMeal(MealLog mealLog) {
    String sql = "INSERT INTO meal_logs (user_id, meal_id, date, meal_name, calories) VALUES (?, ?, ?, ?, ?)";

    try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

      pstmt.setInt(1, mealLog.getUserId());
      pstmt.setInt(2, mealLog.getMealId());
      pstmt.setTimestamp(3, new Timestamp(mealLog.getDate().getTime()));
      pstmt.setString(4, mealLog.getMealName());
      pstmt.setInt(5, mealLog.getCalories());

      int affectedRows = pstmt.executeUpdate();
      if (affectedRows > 0) {
        try (ResultSet rs = pstmt.getGeneratedKeys()) {
          if (rs.next()) {
            mealLog.setId(rs.getInt(1));
            return true;
          }
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  public List<MealLog> getUserMealLogs(int userId, Date date) {
    List<MealLog> mealLogs = new ArrayList<>();
    String sql = "SELECT * FROM meal_logs WHERE user_id = ? AND DATE(date) = DATE(?)";

    try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {

      pstmt.setInt(1, userId);
      pstmt.setTimestamp(2, new Timestamp(date.getTime()));

      try (ResultSet rs = pstmt.executeQuery()) {
        while (rs.next()) {
          MealLog mealLog = new MealLog(
              rs.getInt("user_id"),
              rs.getInt("meal_id"),
              rs.getTimestamp("date"),
              rs.getString("meal_name"),
              rs.getInt("calories"));
          mealLog.setId(rs.getInt("id"));
          mealLogs.add(mealLog);
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return mealLogs;
  }

  public int getTotalCaloriesForDay(int userId, Date date) {
    String sql = "SELECT SUM(calories) FROM meal_logs WHERE user_id = ? AND DATE(date) = DATE(?)";

    try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {

      pstmt.setInt(1, userId);
      pstmt.setTimestamp(2, new Timestamp(date.getTime()));

      try (ResultSet rs = pstmt.executeQuery()) {
        if (rs.next()) {
          return rs.getInt(1);
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return 0;
  }
}
