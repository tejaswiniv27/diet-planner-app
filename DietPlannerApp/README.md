# Diet Planner Application

A Java-based desktop application for personalized meal planning and dietary tracking, developed as part of the Object Oriented Analysis and Design (OOAD) course — UE22CS352B.

The application helps users plan meals based on health goals like weight loss, maintenance, or muscle gain. It features a Java Swing GUI and uses MySQL for data storage. The design demonstrates proper object-oriented principles and the use of design patterns.

---

## Features

- 🔐 User Authentication (Proxy Pattern)
- 👤 Profile & Goal Management
- 🍽️ Meal Plan Generation (Template Method & Strategy Patterns)
- 📊 Meal Logging & Progress Tracking (Observer Pattern)

---

## Technologies Used

- Java (JDK 17+)
- Java Swing (GUI)
- MySQL
- JDBC
- MySQL Connector/J
- Object-Oriented Programming
- Design Patterns (Proxy, Strategy, Template Method, Observer)

---

## Project Structure

dietplanner/
├── auth/          → Authentication logic
├── dao/           → Data Access Objects
├── db/            → Database connection
├── gui/           → Java Swing UI
├── model/         → User, Meal, MealLog models
├── observer/      → Observer pattern implementation
├── strategy/      → Calorie strategy classes
├── template/      → Meal plan templates
├── Main.java      → Application entry point
lib/
└── mysql-connector-j-8.x.x.jar

---

## How to Run

1. Ensure MySQL server is running.
2. Import `schema.sql` into your database.
3. Compile the code:
   javac -d out -cp "lib/mysql-connector-j-8.x.x.jar" dietplanner/**/*.java
4.Run the application:
   java -cp "lib/mysql-connector-j-8.x.x.jar;out" dietplanner.Main


🙌 Acknowledgements  
Developed as part of the Object Oriented Analysis and Design (OOAD) mini project under course code UE22CS352B.

Made with 💻 and ☕ by Tejaswini V.

📜 License  
This project is for academic and educational use only.

   
