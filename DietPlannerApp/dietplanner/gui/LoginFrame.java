package dietplanner.gui;

import dietplanner.auth.UserAuthProxy;
import dietplanner.model.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LoginFrame extends JFrame {
  private JTextField emailField;
  private JPasswordField passwordField;
  private JButton loginButton;
  private JButton registerButton;
  private UserAuthProxy authProxy;

  public LoginFrame() {
    authProxy = new UserAuthProxy();
    initComponents();
  }

  private void initComponents() {
    setTitle("Diet Planner - Login");
    setSize(400, 300);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);

    JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
    mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    // Title label
    JLabel titleLabel = new JLabel("Diet Planner", JLabel.CENTER);
    titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
    mainPanel.add(titleLabel, BorderLayout.NORTH);

    // Form panel
    JPanel formPanel = new JPanel(new GridLayout(2, 2, 10, 10));

    JLabel emailLabel = new JLabel("Email:");
    emailField = new JTextField();
    JLabel passwordLabel = new JLabel("Password:");
    passwordField = new JPasswordField();

    formPanel.add(emailLabel);
    formPanel.add(emailField);
    formPanel.add(passwordLabel);
    formPanel.add(passwordField);

    mainPanel.add(formPanel, BorderLayout.CENTER);

    // Button panel
    JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 0));

    loginButton = new JButton("Login");
    registerButton = new JButton("Register");

    buttonPanel.add(loginButton);
    buttonPanel.add(registerButton);

    mainPanel.add(buttonPanel, BorderLayout.SOUTH);

    // Add action listeners
    loginButton.addActionListener(this::loginButtonActionPerformed);
    registerButton.addActionListener(this::registerButtonActionPerformed);

    add(mainPanel);
  }

  private void loginButtonActionPerformed(ActionEvent evt) {
    String email = emailField.getText();
    String password = new String(passwordField.getPassword());

    if (email.isEmpty() || password.isEmpty()) {
      JOptionPane.showMessageDialog(this, "Please enter both email and password", "Error", JOptionPane.ERROR_MESSAGE);
      return;
    }

    User user = authProxy.login(email, password);

    if (user != null) {
      // Successful login
      this.dispose();
      if (user.getGoal() == null || user.getGoal().isEmpty()) {
        // User needs to complete profile
        new ProfileFrame(user).setVisible(true);
      } else {
        // User already has a profile, go to dashboard
        new DashboardFrame(user).setVisible(true);
      }
    } else {
      JOptionPane.showMessageDialog(this, "Invalid email or password", "Login Failed", JOptionPane.ERROR_MESSAGE);
    }
  }

  private void registerButtonActionPerformed(ActionEvent evt) {
    String email = emailField.getText();
    String password = new String(passwordField.getPassword());

    if (email.isEmpty() || password.isEmpty()) {
      JOptionPane.showMessageDialog(this, "Please enter both email and password", "Error", JOptionPane.ERROR_MESSAGE);
      return;
    }

    boolean success = authProxy.register(email, password);

    if (success) {
      JOptionPane.showMessageDialog(this, "Registration successful! Please login.", "Success",
          JOptionPane.INFORMATION_MESSAGE);
      passwordField.setText("");
    } else {
      JOptionPane.showMessageDialog(this, "Registration failed. Email might already be in use.", "Error",
          JOptionPane.ERROR_MESSAGE);
    }
  }
}
