🏏 Cricketer Management System

A simple Java JDBC + MySQL console application for managing cricket player records.

The project uses JDBC PreparedStatement to connect a Java application with a MySQL database and provides CRUD operations for players.

📌 Project Overview

The Cricketer Management System allows users to:

View all players

Add a new player

Search for a player

Update player details

Delete a player

Exit the application

The application connects to a MySQL database named cricket_db.

🛠️ Technologies Used

Java

JDBC (Java Database Connectivity)

MySQL

MySQL Connector/J

PreparedStatement

SQL

Scanner for console input

📂 Project Files

Cricketer-Management-System/
│
├── Main.java
├── DatabaseConnection.java
├── cricket_db.sql
└── README.md

DatabaseConnection.java is required by Main.java and should contain the JDBC connection configuration.

🗄️ Database

Database name:

cricket_db

The SQL script creates tables for:

TEAM

PLAYER

MATCH

SCORE

BALL

INNINGS

TOURNAMENT

TOURNAMENT_TEAM

MATCH_RESULT

The PLAYER table stores player information such as player name, date of birth, country, role, and team ID.

⚙️ Setup Instructions

1. Install MySQL

Install MySQL Server and MySQL Workbench (or another MySQL client).

2. Create the Database

Open MySQL and run:

CREATE DATABASE cricket_db;
USE cricket_db;

3. Run the SQL File

Import and execute:

cricket_db.sql

This creates the required tables and inserts sample cricket data.

4. Configure JDBC Connection

Create/update DatabaseConnection.java with your MySQL username, password, and JDBC URL.

Example:

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {

    public static Connection getConnection() {

        try {
            String url = "jdbc:mysql://localhost:3306/cricket_db";
            String username = "root";
            String password = "YOUR_MYSQL_PASSWORD";

            return DriverManager.getConnection(url, username, password);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

Important: Replace YOUR_MYSQL_PASSWORD with your own MySQL password. Do not upload real passwords to GitHub.

5. Add MySQL Connector/J

Add the MySQL Connector/J JAR to your Java project classpath.

If using an IDE such as Eclipse or IntelliJ IDEA, add the MySQL Connector/J dependency/library to the project.

▶️ Running the Application

Compile and run the Main.java file.

The application displays:

========== CRICKETER APP ==========
1. View Players
2. Add Player
3. Search Player
4. Update Player
5. Delete Player
6. Exit
Enter your choice:

✨ Features

1. View Players

Displays player records from the MySQL PLAYER table.

2. Add Player

Accepts:

Player name

Date of birth

Country

Role

Team ID

and inserts the record into the database.

3. Search Player

Searches players by name using SQL LIKE.

4. Update Player

Updates a player's name using the player's ID.

5. Delete Player

Deletes a player using the player's ID.

6. Exit

Closes the database connection and exits the application.

🔐 JDBC and PreparedStatement

The project uses PreparedStatement for database operations instead of directly concatenating user input into SQL queries.

Example:

String sql = "UPDATE player SET player_name = ? WHERE player_id = ?";

PreparedStatement ps = con.prepareStatement(sql);

ps.setString(1, name);
ps.setInt(2, id);

ps.executeUpdate();

This approach improves query handling and helps protect against SQL injection.

🧩 Database Relationships

The database uses primary keys and foreign keys to connect cricket entities.

For example:

TEAM
  │
  └── PLAYER
        │
        ├── SCORE
        └── BALL

TEAM ─── MATCH
          │
          ├── SCORE
          ├── BALL
          ├── INNINGS
          └── MATCH_RESULT

TOURNAMENT ─── TOURNAMENT_TEAM ─── TEAM

📊 Sample Data

The SQL file includes sample teams and players such as:

India

Australia

England

South Africa

New Zealand

and sample players such as:

Virat Kohli

Rohit Sharma

Jasprit Bumrah

Pat Cummins

Steve Smith

Joe Root

Ben Stokes

Kagiso Rabada

Kane Williamson

⚠️ Important Note About the SQL File

Before running cricket_db.sql, review the tournament insertion section. The file contains an earlier INSERT INTO TOURNAMENT using a location column and then another insertion using venue. Since the table definition contains venue, the location insertion should be removed or corrected before executing the complete script.

Also, the sample MATCH_RESULT descriptions should be reviewed against the corresponding match teams if you want the sample data to be logically consistent.

🚀 Future Improvements

Possible enhancements include:

Display team names instead of only team IDs

Search by country or player role

Update complete player details

Add match management through JDBC

Add score and innings management

Add transaction handling

Add input validation

Separate code into DAO, service, model, and utility packages

Add a graphical user interface

Add Maven/Gradle dependency management

👩‍💻 Author

Cricketer Management System

Developed as a Java JDBC and MySQL database project.

📄 License

This project is intended for educational and academic purposes.
