package dietplanner.gui;

import dietplanner.dao.UserDAO;
import dietplanner.model.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ProfileFrame extends JFrame {
  private User user;
  private UserDAO userDAO;

  private JTextField nameField;
  private JTextField ageField;
  private JComboBox<String> genderComboBox;
  private JTextField weightField;
  private JTextField heightField;
  private JComboBox<String> goalComboBox;
  private JButton saveButton;

  public ProfileFrame(User user) {
    this.user = user;
    this.userDAO = new UserDAO();
    initComponents();
  }

  private void initComponents() {
    setTitle("Diet Planner - Profile");
    setSize(500, 400);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);

    JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
    mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    // Title label
    JLabel titleLabel = new JLabel("Complete Your Profile", JLabel.CENTER);
    titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
    mainPanel.add(titleLabel, BorderLayout.NORTH);

    // Form panel
    JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));

    // Name field
    JLabel nameLabel = new JLabel("Name:");
    nameField = new JTextField(user.getName());
    formPanel.add(nameLabel);
    formPanel.add(nameField);

    // Age field
    JLabel ageLabel = new JLabel("Age:");
    ageField = new JTextField(user.getAge() > 0 ? String.valueOf(user.getAge()) : "");
    formPanel.add(ageLabel);
    formPanel.add(ageField);

    // Gender combo box
    JLabel genderLabel = new JLabel("Gender:");
    String[] genders = { "MALE", "FEMALE" };
    genderComboBox = new JComboBox<>(genders);
    if (user.getGender() != null) {
      genderComboBox.setSelectedItem(user.getGender());
    }
    formPanel.add(genderLabel);
    formPanel.add(genderComboBox);

    // Weight field
    JLabel weightLabel = new JLabel("Weight (kg):");
    weightField = new JTextField(user.getWeight() > 0 ? String.valueOf(user.getWeight()) : "");
    formPanel.add(weightLabel);
    formPanel.add(weightField);

    // Height field
    JLabel heightLabel = new JLabel("Height (cm):");
    heightField = new JTextField(user.getHeight() > 0 ? String.valueOf(user.getHeight()) : "");
    formPanel.add(heightLabel);
    formPanel.add(heightField);

    // Goal combo box
    JLabel goalLabel = new JLabel("Fitness Goal:");
    String[] goals = { "WEIGHT_LOSS", "MAINTENANCE", "MUSCLE_GAIN" };
    goalComboBox = new JComboBox<>(goals);
    if (user.getGoal() != null) {
      goalComboBox.setSelectedItem(user.getGoal());
    }
    formPanel.add(goalLabel);
    formPanel.add(goalComboBox);

    mainPanel.add(formPanel, BorderLayout.CENTER);

    // Save button
    saveButton = new JButton("Save Profile");
    saveButton.addActionListener(this::saveButtonActionPerformed);

    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    buttonPanel.add(saveButton);

    mainPanel.add(buttonPanel, BorderLayout.SOUTH);

    add(mainPanel);
  }

  private void saveButtonActionPerformed(ActionEvent evt) {
    try {
      // Validate fields
      String name = nameField.getText().trim();
      int age = Integer.parseInt(ageField.getText().trim());
      String gender = (String) genderComboBox.getSelectedItem();
      double weight = Double.parseDouble(weightField.getText().trim());
      double height = Double.parseDouble(heightField.getText().trim());
      String goal = (String) goalComboBox.getSelectedItem();

      // Update user object
      user.setName(name);
      user.setAge(age);
      user.setGender(gender);
      user.setWeight(weight);
      user.setHeight(height);
      user.setGoal(goal);

      // Save to database
      boolean success = userDAO.updateProfile(user);

      if (success) {
        JOptionPane.showMessageDialog(this, "Profile saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        this.dispose();
        new DashboardFrame(user).setVisible(true);
      } else {
        JOptionPane.showMessageDialog(this, "Failed to save profile.", "Error", JOptionPane.ERROR_MESSAGE);
      }
    } catch (NumberFormatException e) {
      JOptionPane.showMessageDialog(this, "Please enter valid numbers for age, weight, and height.", "Input Error",
          JOptionPane.ERROR_MESSAGE);
    }
  }
}