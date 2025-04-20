package dietplanner.gui;

import dietplanner.gui.LoginFrame;

import dietplanner.dao.MealLogDAO;
import dietplanner.model.Meal;
import dietplanner.model.MealLog;
import dietplanner.model.User;
import dietplanner.observer.MealLogSubject;
import dietplanner.observer.Observer;
import dietplanner.strategy.CalorieCalculatorContext;
// import src.dietplanner.strategy.User as StrategyUser;
import dietplanner.template.MealPlanFactory;
import dietplanner.template.MealPlanTemplate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DashboardFrame extends JFrame implements Observer {
  private User user;
  private MealLogDAO mealLogDAO;
  private MealLogSubject mealLogSubject;
  private List<Meal> currentMealPlan;

  private JLabel welcomeLabel;
  private JLabel calorieGoalLabel;
  private JLabel currentCaloriesLabel;
  private JPanel mealPlanPanel;
  private JPanel mealLogPanel;

  public DashboardFrame(User user) {
    this.user = user;
    this.mealLogDAO = new MealLogDAO();
    this.mealLogSubject = new MealLogSubject();
    this.mealLogSubject.attach(this);
    initComponents();
    loadMealPlan();
    loadMealLogs();
  }

  private void initComponents() {
    setTitle("Diet Planner - Dashboard");
    setSize(800, 600);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);

    JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
    mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    // Welcome and info panel
    JPanel infoPanel = new JPanel(new GridLayout(3, 1));
    welcomeLabel = new JLabel("Welcome, " + user.getName() + "!");
    welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));

    // Calculate calorie goal based on user profile and goal
    int calorieGoal = calculateCalorieGoal();
    calorieGoalLabel = new JLabel("Daily Calorie Goal: " + calorieGoal + " calories");
    calorieGoalLabel.setFont(new Font("Arial", Font.PLAIN, 14));

    // Get current calories consumed today
    int currentCalories = mealLogDAO.getTotalCaloriesForDay(user.getId(), new Date());
    currentCaloriesLabel = new JLabel("Calories Consumed Today: " + currentCalories + " calories");
    currentCaloriesLabel.setFont(new Font("Arial", Font.PLAIN, 14));

    infoPanel.add(welcomeLabel);
    infoPanel.add(calorieGoalLabel);
    infoPanel.add(currentCaloriesLabel);

    mainPanel.add(infoPanel, BorderLayout.NORTH);

    // Tab panel for meal plan and meal log
    JTabbedPane tabbedPane = new JTabbedPane();

    // Meal plan panel
    mealPlanPanel = new JPanel(new BorderLayout());
    JLabel mealPlanLabel = new JLabel("Today's Meal Plan", JLabel.CENTER);
    mealPlanLabel.setFont(new Font("Arial", Font.BOLD, 16));
    mealPlanPanel.add(mealPlanLabel, BorderLayout.NORTH);

    tabbedPane.addTab("Meal Plan", mealPlanPanel);

    // Meal log panel
    mealLogPanel = new JPanel(new BorderLayout());
    JLabel mealLogLabel = new JLabel("Today's Meal Log", JLabel.CENTER);
    mealLogLabel.setFont(new Font("Arial", Font.BOLD, 16));
    mealLogPanel.add(mealLogLabel, BorderLayout.NORTH);

    tabbedPane.addTab("Meal Log", mealLogPanel);

    mainPanel.add(tabbedPane, BorderLayout.CENTER);

    // Button panel
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));

    JButton logMealButton = new JButton("Log a Meal");
    logMealButton.addActionListener(this::logMealButtonActionPerformed);

    JButton profileButton = new JButton("Edit Profile");
    profileButton.addActionListener(this::profileButtonActionPerformed);

    JButton logoutButton = new JButton("Logout");
    logoutButton.addActionListener(this::logoutButtonActionPerformed);

    buttonPanel.add(logMealButton);
    buttonPanel.add(profileButton);
    buttonPanel.add(logoutButton);

    mainPanel.add(buttonPanel, BorderLayout.SOUTH);

    add(mainPanel);
  }

  private int calculateCalorieGoal() {
    // Create a strategy user from the model user
    dietplanner.strategy.User strategyUser = new dietplanner.strategy.User(
        user.getAge(),
        user.getGender(),
        user.getWeight(),
        user.getHeight(),
        "MODERATELY_ACTIVE" // Default activity level
    );

    // Use the strategy to calculate calories
    CalorieCalculatorContext context = new CalorieCalculatorContext();
    return context.calculateCalories(strategyUser, user.getGoal());
  }

  private void loadMealPlan() {
    // Get the appropriate meal plan template
    MealPlanTemplate mealPlanTemplate = MealPlanFactory.getMealPlan(user.getGoal());

    // Calculate daily calories
    int calorieGoal = calculateCalorieGoal();

    // Generate meal plan
    currentMealPlan = mealPlanTemplate.createMealPlan(calorieGoal);

    // Create panel to display meal plan
    JPanel mealListPanel = new JPanel();
    mealListPanel.setLayout(new BoxLayout(mealListPanel, BoxLayout.Y_AXIS));

    for (Meal meal : currentMealPlan) {
      JPanel mealPanel = new JPanel(new BorderLayout());
      mealPanel.setBorder(BorderFactory.createEtchedBorder());

      JLabel nameLabel = new JLabel(meal.getName());
      nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
      mealPanel.add(nameLabel, BorderLayout.NORTH);

      JLabel detailsLabel = new JLabel(
          String.format("<html>Type: %s<br>Calories: %d<br>Proteins: %dg<br>Carbs: %dg<br>Fats: %dg</html>",
              meal.getType(), meal.getCalories(), meal.getProteins(), meal.getCarbs(), meal.getFats()));
      mealPanel.add(detailsLabel, BorderLayout.CENTER);

      JButton logButton = new JButton("Log this meal");
      logButton.addActionListener(e -> logCurrentMeal(meal));
      mealPanel.add(logButton, BorderLayout.SOUTH);

      mealListPanel.add(mealPanel);
      mealListPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    }

    // Add to scroll pane
    JScrollPane scrollPane = new JScrollPane(mealListPanel);
    scrollPane.setBorder(null);

    // Add to meal plan panel
    mealPlanPanel.add(scrollPane, BorderLayout.CENTER);
  }

  private void loadMealLogs() {
    // Get today's meal logs
    List<MealLog> mealLogs = mealLogDAO.getUserMealLogs(user.getId(), new Date());

    // Create panel to display meal logs
    JPanel logListPanel = new JPanel();
    logListPanel.setLayout(new BoxLayout(logListPanel, BoxLayout.Y_AXIS));

    if (mealLogs.isEmpty()) {
      logListPanel.add(new JLabel("No meals logged today."));
    } else {
      for (MealLog log : mealLogs) {
        JPanel logPanel = new JPanel(new BorderLayout());
        logPanel.setBorder(BorderFactory.createEtchedBorder());

        JLabel nameLabel = new JLabel(log.getMealName());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        logPanel.add(nameLabel, BorderLayout.NORTH);

        JLabel caloriesLabel = new JLabel("Calories: " + log.getCalories());
        logPanel.add(caloriesLabel, BorderLayout.CENTER);

        logListPanel.add(logPanel);
        logListPanel.add(Box.createRigidArea(new Dimension(0, 10)));
      }
    }

    // Add to scroll pane
    JScrollPane scrollPane = new JScrollPane(logListPanel);
    scrollPane.setBorder(null);

    // Add to meal log panel
    mealLogPanel.removeAll();
    JLabel mealLogLabel = new JLabel("Today's Meal Log", JLabel.CENTER);
    mealLogLabel.setFont(new Font("Arial", Font.BOLD, 16));
    mealLogPanel.add(mealLogLabel, BorderLayout.NORTH);
    mealLogPanel.add(scrollPane, BorderLayout.CENTER);
    mealLogPanel.revalidate();
    mealLogPanel.repaint();

    // Update calorie counter
    int currentCalories = mealLogDAO.getTotalCaloriesForDay(user.getId(), new Date());
    currentCaloriesLabel.setText("Calories Consumed Today: " + currentCalories + " calories");
  }

  private void logCurrentMeal(Meal meal) {
    // Create a new meal log
    MealLog mealLog = new MealLog(
        user.getId(),
        meal.getId(),
        new Date(),
        meal.getName(),
        meal.getCalories());

    // Save to database
    boolean success = mealLogDAO.logMeal(mealLog);

    if (success) {
      // Notify observers (this dashboard) about the new meal log
      mealLogSubject.logMeal(mealLog);
      JOptionPane.showMessageDialog(this, "Meal logged successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    } else {
      JOptionPane.showMessageDialog(this, "Failed to log meal.", "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  private void logMealButtonActionPerformed(ActionEvent evt) {
    new LogMealFrame(user, mealLogSubject).setVisible(true);
  }

  private void profileButtonActionPerformed(ActionEvent evt) {
    this.dispose();
    new ProfileFrame(user).setVisible(true);
  }

  private void logoutButtonActionPerformed(ActionEvent evt) {
    this.dispose();
    new LoginFrame().setVisible(true);
  }

  @Override
  public void update(Object data) {
    // Observer pattern - update UI when a new meal is logged
    loadMealLogs();
  }
}