package dietplanner.gui;

import dietplanner.dao.MealDAO;
import dietplanner.dao.MealLogDAO;
import dietplanner.model.Meal;
import dietplanner.model.MealLog;
import dietplanner.model.User;
import dietplanner.observer.MealLogSubject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Date;
import java.util.List;

public class LogMealFrame extends JFrame {
  private User user;
  private MealDAO mealDAO;
  private MealLogDAO mealLogDAO;
  private MealLogSubject mealLogSubject;

  private JComboBox<String> mealTypeCombo;
  private JComboBox<Meal> mealCombo;
  private JButton logButton;

  public LogMealFrame(User user, MealLogSubject mealLogSubject) {
    this.user = user;
    this.mealDAO = new MealDAO();
    this.mealLogDAO = new MealLogDAO();
    this.mealLogSubject = mealLogSubject;
    initComponents();
  }

  private void initComponents() {
    setTitle("Log a Meal");
    setSize(400, 200);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setLocationRelativeTo(null);

    JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
    mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    // Title label
    JLabel titleLabel = new JLabel("Log a Meal", JLabel.CENTER);
    titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
    mainPanel.add(titleLabel, BorderLayout.NORTH);

    // Form panel
    JPanel formPanel = new JPanel(new GridLayout(2, 2, 10, 10));

    // Meal type combo
    JLabel typeLabel = new JLabel("Select meal type:");
    String[] mealTypes = { "BREAKFAST", "LUNCH", "DINNER", "SNACK" };
    mealTypeCombo = new JComboBox<>(mealTypes);
    mealTypeCombo.addActionListener(this::mealTypeChanged);

    formPanel.add(typeLabel);
    formPanel.add(mealTypeCombo);

    // Meal combo
    JLabel mealLabel = new JLabel("Select meal:");
    mealCombo = new JComboBox<>();
    loadMeals((String) mealTypeCombo.getSelectedItem());

    formPanel.add(mealLabel);
    formPanel.add(mealCombo);

    mainPanel.add(formPanel, BorderLayout.CENTER);

    // Button panel
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

    logButton = new JButton("Log Meal");
    logButton.addActionListener(this::logButtonActionPerformed);

    buttonPanel.add(logButton);

    mainPanel.add(buttonPanel, BorderLayout.SOUTH);

    add(mainPanel);
  }

  private void mealTypeChanged(ActionEvent evt) {
    loadMeals((String) mealTypeCombo.getSelectedItem());
  }

  private void loadMeals(String mealType) {
    // Clear existing items
    mealCombo.removeAllItems();

    // Get meals of selected type
    List<Meal> meals = mealDAO.getMealsByType(mealType);

    // Add to combo box
    for (Meal meal : meals) {
      mealCombo.addItem(meal);
    }
  }

  private void logButtonActionPerformed(ActionEvent evt) {
    Meal selectedMeal = (Meal) mealCombo.getSelectedItem();

    if (selectedMeal != null) {
      // Create meal log
      MealLog mealLog = new MealLog(
          user.getId(),
          selectedMeal.getId(),
          new Date(),
          selectedMeal.getName(),
          selectedMeal.getCalories());

      // Save to database
      boolean success = mealLogDAO.logMeal(mealLog);

      if (success) {
        // Notify observers about the new meal log
        mealLogSubject.logMeal(mealLog);
        JOptionPane.showMessageDialog(this, "Meal logged successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        this.dispose();
      } else {
        JOptionPane.showMessageDialog(this, "Failed to log meal.", "Error", JOptionPane.ERROR_MESSAGE);
      }
    } else {
      JOptionPane.showMessageDialog(this, "Please select a meal.", "Error", JOptionPane.ERROR_MESSAGE);
    }
  }
}
