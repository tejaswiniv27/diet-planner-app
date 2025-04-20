-- Create database
CREATE DATABASE IF NOT EXISTS diet_planner;
USE diet_planner;

-- USERS table
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    name VARCHAR(100),
    age INT,
    gender VARCHAR(10),
    weight DOUBLE,
    height DOUBLE,
    goal VARCHAR(20),
    activity_level VARCHAR(20)
);

-- MEALS table
CREATE TABLE IF NOT EXISTS meals (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    type VARCHAR(20) NOT NULL,
    calories INT NOT NULL,
    proteins INT NOT NULL,
    carbs INT NOT NULL,
    fats INT NOT NULL
);

-- MEAL LOGS table
CREATE TABLE IF NOT EXISTS meal_logs (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    meal_id INT NOT NULL,
    date DATETIME NOT NULL,
    meal_name VARCHAR(255) NOT NULL,
    calories INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (meal_id) REFERENCES meals(id) ON DELETE CASCADE
);

-- Insert Sample Meals
INSERT INTO meals (name, type, calories, proteins, carbs, fats) VALUES
('Oatmeal with Banana', 'BREAKFAST', 350, 12, 60, 7),
('Greek Yogurt with Berries', 'BREAKFAST', 250, 20, 25, 8),
('Scrambled Eggs with Toast', 'BREAKFAST', 400, 22, 30, 18),
('Protein Smoothie', 'BREAKFAST', 300, 25, 20, 10),
('Grilled Chicken Salad', 'LUNCH', 450, 35, 20, 15),
('Brown Rice with Vegetables', 'LUNCH', 500, 15, 70, 10),
('Paneer Wrap', 'LUNCH', 550, 30, 40, 20),
('Quinoa Bowl', 'LUNCH', 480, 20, 60, 12),
('Grilled Salmon with Veggies', 'DINNER', 500, 40, 15, 20),
('Vegetable Stir Fry', 'DINNER', 450, 18, 50, 10),
('Tofu Curry with Rice', 'DINNER', 520, 25, 55, 18),
('Chicken Soup with Bread', 'DINNER', 400, 30, 25, 12),
('Apple with Peanut Butter', 'SNACK', 200, 5, 25, 10),
('Protein Bar', 'SNACK', 220, 20, 18, 9),
('Boiled Eggs', 'SNACK', 150, 13, 1, 10),
('Mixed Nuts', 'SNACK', 300, 10, 12, 25);
