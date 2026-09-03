package cricketer.main;

import java.util.List;
import java.util.Scanner;

import cricketer.exception.DuplicatePlayerException;
import cricketer.exception.InvalidPlayerDataException;
import cricketer.exception.PlayerNotFoundException;
import cricketer.model.Cricketer;
import cricketer.service.CricketerService;

public class CricketerManagementApp {

    static Scanner scanner = new Scanner(System.in);
    static CricketerService service = new CricketerService();

    public static void main(String[] args) {

        int choice;

        do {

            displayMenu();

            choice = readInt("Enter your choice: ");

            try {

                switch (choice) {

                    case 1:
                        addPlayer();
                        break;

                    case 2:
                        viewAll();
                        break;

                    case 3:
                        searchByName();
                        break;

                    case 4:
                        searchByCountry();
                        break;

                    case 5:
                        searchByRole();
                        break;

                    case 6:
                        updatePlayer();
                        break;

                    case 7:
                        deletePlayer();
                        break;

                    case 8:
                        highestRunScorer();
                        break;

                    case 9:
                        highestWicketTaker();
                        break;

                    case 10:
                        highestScore();
                        break;

                    case 11:
                        topRunScorers();
                        break;

                    case 12:
                        topWicketTakers();
                        break;

                    case 13:
                        displayStatistics();
                        break;

                    case 14:
                        sortByRuns();
                        break;

                    case 15:
                        sortByAge();
                        break;

                    case 0:
                        System.out.println(
                            "Thank you for using the system."
                        );
                        break;

                    default:
                        System.out.println(
                            "Invalid choice."
                        );
                }

            } catch (Exception e) {

                System.out.println(
                    "ERROR: " + e.getMessage()
                );
            }

        } while (choice != 0);

        scanner.close();
    }

    static void displayMenu() {

        System.out.println();
        System.out.println(
            "=============================================="
        );
        System.out.println(
            "        CRICKETER MANAGEMENT SYSTEM"
        );
        System.out.println(
            "=============================================="
        );

        System.out.println("1.  Add Cricketer");
        System.out.println("2.  View All Cricketers");
        System.out.println("3.  Search Cricketer by Name");
        System.out.println("4.  Search by Country");
        System.out.println("5.  Search by Role");
        System.out.println("6.  Update Cricketer");
        System.out.println("7.  Delete Cricketer");
        System.out.println("8.  Highest Run Scorer");
        System.out.println("9.  Highest Wicket Taker");
        System.out.println("10. Highest Individual Score");
        System.out.println("11. Top 5 Run Scorers");
        System.out.println("12. Top 5 Wicket Takers");
        System.out.println("13. Display Player Statistics");
        System.out.println("14. Sort Players by Runs");
        System.out.println("15. Sort Players by Age");
        System.out.println("0.  Exit");

        System.out.println(
            "=============================================="
        );
    }

    static void addPlayer()
            throws DuplicatePlayerException,
            InvalidPlayerDataException {

        System.out.println("\nADD CRICKETER");

        int id = readInt("Player ID: ");
        String name = readString("Player Name: ");
        String country = readString("Country: ");
        int age = readInt("Age: ");
        String role = readString("Role: ");

        int matches = readInt("Matches: ");
        int runs = readInt("Runs: ");
        int highest = readInt("Highest Score: ");
        double batting = readDouble("Batting Average: ");
        int wickets = readInt("Wickets: ");
        double bowling = readDouble("Bowling Average: ");

        Cricketer player = new Cricketer(
            id, name, country, age, role,
            matches, runs, highest,
            batting, wickets, bowling
        );

        service.addPlayer(player);

        System.out.println(
            "Player added successfully."
        );
    }

    static void viewAll() {

        List<Cricketer> players =
                service.getAllPlayers();

        printHeader();

        for (Cricketer player : players) {
            System.out.println(player);
        }
    }

    static void searchByName() {

        String name = readString(
            "Enter player name: "
        );

        List<Cricketer> result =
                service.searchByName(name);

        printResults(result);
    }

    static void searchByCountry() {

        String country = readString(
            "Enter country: "
        );

        List<Cricketer> result =
                service.searchByCountry(country);

        printResults(result);
    }

    static void searchByRole() {

        String role = readString(
            "Enter role: "
        );

        List<Cricketer> result =
                service.searchByRole(role);

        printResults(result);
    }

    static void updatePlayer()
            throws PlayerNotFoundException,
            InvalidPlayerDataException {

        int id = readInt(
            "Enter Player ID to update: "
        );

        String name = readString("Player Name: ");
        String country = readString("Country: ");
        int age = readInt("Age: ");
        String role = readString("Role: ");

        int matches = readInt("Matches: ");
        int runs = readInt("Runs: ");
        int highest = readInt("Highest Score: ");
        double batting = readDouble(
            "Batting Average: "
        );
        int wickets = readInt("Wickets: ");
        double bowling = readDouble(
            "Bowling Average: "
        );

        Cricketer updated = new Cricketer(
            id, name, country, age, role,
            matches, runs, highest,
            batting, wickets, bowling
        );

        service.updatePlayer(updated);

        System.out.println(
            "Player updated successfully."
        );
    }

    static void deletePlayer()
            throws PlayerNotFoundException {

        int id = readInt(
            "Enter Player ID to delete: "
        );

        service.deletePlayer(id);

        System.out.println(
            "Player deleted successfully."
        );
    }

    static void highestRunScorer() {

        Cricketer player =
                service.getHighestRunScorer();

        displayPlayer(player);
    }

    static void highestWicketTaker() {

        Cricketer player =
                service.getHighestWicketTaker();

        displayPlayer(player);
    }

    static void highestScore() {

        Cricketer player =
                service.getHighestIndividualScore();

        displayPlayer(player);
    }

    static void topRunScorers() {

        List<Cricketer> result =
                service.getTopRunScorers();

        System.out.println("\nTOP 5 RUN SCORERS");
        System.out.println("----------------------------");

        for (Cricketer p : result) {

            System.out.printf(
                "%-20s %d%n",
                p.getName(),
                p.getRuns()
            );
        }
    }

    static void topWicketTakers() {

        List<Cricketer> result =
                service.getTopWicketTakers();

        System.out.println("\nTOP 5 WICKET TAKERS");
        System.out.println("----------------------------");

        for (Cricketer p : result) {

            System.out.printf(
                "%-20s %d%n",
                p.getName(),
                p.getWickets()
            );
        }
    }

    static void displayStatistics()
            throws PlayerNotFoundException {

        int id = readInt(
            "Enter Player ID: "
        );

        Cricketer p = service.findById(id);

        displayPlayer(p);
    }

    static void sortByRuns() {

        List<Cricketer> result =
                service.sortByRuns();

        printResults(result);
    }

    static void sortByAge() {

        List<Cricketer> result =
                service.sortByAge();

        printResults(result);
    }

    static void displayPlayer(Cricketer p) {

        System.out.println();
        System.out.println(
            "=========================================="
        );
        System.out.println(
            "           PLAYER STATISTICS"
        );
        System.out.println(
            "=========================================="
        );

        System.out.println(
            "Player ID        : " + p.getPlayerId()
        );

        System.out.println(
            "Name             : " + p.getName()
        );

        System.out.println(
            "Country          : " + p.getCountry()
        );

        System.out.println(
            "Age              : " + p.getAge()
        );

        System.out.println(
            "Role             : " + p.getRole()
        );

        System.out.println(
            "Matches          : " + p.getMatches()
        );

        System.out.println(
            "Runs             : " + p.getRuns()
        );

        System.out.println(
            "Highest Score    : " + p.getHighestScore()
        );

        System.out.println(
            "Batting Average  : " +
            p.getBattingAverage()
        );

        System.out.println(
            "Wickets          : " + p.getWickets()
        );

        System.out.println(
            "Bowling Average  : " +
            p.getBowlingAverage()
        );

        System.out.println(
            "=========================================="
        );
    }

    static void printHeader() {

        System.out.println();

        System.out.printf(
            "%-5s %-20s %-15s %-5s %-12s %-8s %-10s %-10s %-10s %-8s %-10s%n",
            "ID",
            "Name",
            "Country",
            "Age",
            "Role",
            "Matches",
            "Runs",
            "Highest",
            "Bat Avg",
            "Wickets",
            "Bowl Avg"
        );

        System.out.println(
            "----------------------------------------------------------------------------------------------------------------"
        );
    }

    static void printResults(
            List<Cricketer> players) {

        if (players.isEmpty()) {

            System.out.println(
                "No players found."
            );

            return;
        }

        printHeader();

        for (Cricketer player : players) {
            System.out.println(player);
        }
    }

    static int readInt(String message) {

        while (true) {

            try {

                System.out.print(message);

                return Integer.parseInt(
                    scanner.nextLine()
                );

            } catch (NumberFormatException e) {

                System.out.println(
                    "Please enter a valid number."
                );
            }
        }
    }

    static double readDouble(String message) {

        while (true) {

            try {

                System.out.print(message);

                return Double.parseDouble(
                    scanner.nextLine()
                );

            } catch (NumberFormatException e) {

                System.out.println(
                    "Please enter a valid number."
                );
            }
        }
    }

    static String readString(String message) {

        System.out.print(message);

        return scanner.nextLine().trim();
    }
}