package dietplanner.auth;

import dietplanner.dao.UserDAO;
import dietplanner.model.User;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

// Proxy Pattern implementation for user authentication
public class UserAuthProxy {
  private UserDAO userDAO;

  public UserAuthProxy() {
    this.userDAO = new UserDAO();
  }

  public User login(String email, String password) {
    // Hash the password
    String passwordHash = hashPassword(password);

    // Try to login
    return userDAO.loginUser(email, passwordHash);
  }

  public boolean register(String email, String password) {
    // Hash the password
    String passwordHash = hashPassword(password);

    // Create a new user and register
    User user = new User(email, passwordHash);
    return userDAO.registerUser(user);
  }

  private String hashPassword(String password) {
    try {
      MessageDigest md = MessageDigest.getInstance("SHA-256");
      byte[] hash = md.digest(password.getBytes());
      return Base64.getEncoder().encodeToString(hash);
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
      return null;
    }
  }
}