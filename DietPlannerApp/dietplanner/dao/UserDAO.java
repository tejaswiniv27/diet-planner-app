package dietplanner.dao;

import dietplanner.db.DatabaseConnection;
import dietplanner.model.User;
import java.sql.*;

public class UserDAO {

  public boolean registerUser(User user) {
    String sql = "INSERT INTO users (email, password_hash) VALUES (?, ?)";

    try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

      pstmt.setString(1, user.getEmail());
      pstmt.setString(2, user.getPasswordHash());

      int affectedRows = pstmt.executeUpdate();
      if (affectedRows > 0) {
        try (ResultSet rs = pstmt.getGeneratedKeys()) {
          if (rs.next()) {
            user.setId(rs.getInt(1));
            return true;
          }
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  public User loginUser(String email, String passwordHash) {
    String sql = "SELECT * FROM users WHERE email = ? AND password_hash = ?";

    try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {

      pstmt.setString(1, email);
      pstmt.setString(2, passwordHash);

      try (ResultSet rs = pstmt.executeQuery()) {
        if (rs.next()) {
          User user = new User();
          user.setId(rs.getInt("id"));
          user.setEmail(rs.getString("email"));
          user.setPasswordHash(rs.getString("password_hash"));
          user.setName(rs.getString("name"));
          user.setAge(rs.getInt("age"));
          user.setGender(rs.getString("gender"));
          user.setWeight(rs.getDouble("weight"));
          user.setHeight(rs.getDouble("height"));
          user.setGoal(rs.getString("goal"));
          return user;
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  public boolean updateProfile(User user) {
    String sql = "UPDATE users SET name = ?, age = ?, gender = ?, weight = ?, height = ?, goal = ? WHERE id = ?";

    try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {

      pstmt.setString(1, user.getName());
      pstmt.setInt(2, user.getAge());
      pstmt.setString(3, user.getGender());
      pstmt.setDouble(4, user.getWeight());
      pstmt.setDouble(5, user.getHeight());
      pstmt.setString(6, user.getGoal());
      pstmt.setInt(7, user.getId());

      int affectedRows = pstmt.executeUpdate();
      return affectedRows > 0;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }
}
