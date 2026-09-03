package main;

import exception.DuplicatePlayerException;
import exception.InvalidPlayerDataException;
import exception.PlayerNotFoundException;
import model.Cricketer;
import service.CricketerService;
import util.DatabaseConnection;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class CricketerManagementApp {
    private static final Scanner scanner = new Scanner(System.in);
    private static final CricketerService service = new CricketerService();

    public static void main(String[] args) {
        try { DatabaseConnection.testConnection(); } catch (Exception e) {
            System.out.println("ERROR: Unable to connect to MySQL. Check MySQL Server and util/DatabaseConnection.java password.");
            System.out.println("Details: " + e.getMessage());
            return;
        }
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
                        displayPlayers(service.getAllPlayers());
                        break;
                    case 3:
                        displayPlayers(service.searchByName(readString("Enter player name: ")));
                        break;
                    case 4:
                        displayPlayers(service.searchByCountry(readString("Enter country: ")));
                        break;
                    case 5:
                        displayPlayers(service.searchByRole(readString("Enter role: ")));
                        break;
                    case 6:
                        updatePlayer();
                        break;
                    case 7:
                        deletePlayer();
                        break;
                    case 8:
                        displayHeading("HIGHEST RUN SCORER");
                        displayPlayerDetails(service.getHighestRunScorer());
                        break;
                    case 9:
                        displayHeading("HIGHEST WICKET TAKER");
                        displayPlayerDetails(service.getHighestWicketTaker());
                        break;
                    case 10:
                        displayHeading("HIGHEST INDIVIDUAL SCORE");
                        displayPlayerDetails(service.getHighestIndividualScore());
                        break;
                    case 11:
                        displayHeading("TOP 5 RUN SCORERS");
                        displayPlayers(service.getTopRunScorers());
                        break;
                    case 12:
                        displayHeading("TOP 5 WICKET TAKERS");
                        displayPlayers(service.getTopWicketTakers());
                        break;
                    case 13:
                        displayStatistics();
                        break;
                    case 14:
                        displayPlayers(service.sortByRuns());
                        break;
                    case 15:
                        displayPlayers(service.sortByAge());
                        break;
                    case 16:
                        saveData();
                        break;
                    case 0:
                        System.out.println("Exiting application.");
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }
            } catch (DuplicatePlayerException exception) {
                System.out.println("ERROR: Player ID " + extractId(exception.getMessage()) + " already exists.");
            } catch (InvalidPlayerDataException exception) {
                displayValidationError(exception.getMessage());
            } catch (PlayerNotFoundException exception) {
                System.out.println("ERROR: " + exception.getMessage().replace("No player found with ID: ", "Player ID ") + " not found.");
            } catch (RuntimeException exception) {
                System.out.println("ERROR: " + exception.getMessage());
            }
        } while (choice != 0);
    }

    public static int readInt(String message) {
        while (true) {
            System.out.print(message);
            try {
                int value = scanner.nextInt();
                scanner.nextLine();
                return value;
            } catch (InputMismatchException exception) {
                System.out.println("Please enter a valid number.");
                scanner.nextLine();
            }
        }
    }

    public static double readDouble(String message) {
        while (true) {
            System.out.print(message);
            try {
                double value = scanner.nextDouble();
                scanner.nextLine();
                return value;
            } catch (InputMismatchException exception) {
                System.out.println("Please enter a valid number.");
                scanner.nextLine();
            }
        }
    }

    public static String readString(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }

    private static void displayMenu() {
        System.out.println("\n==============================================");
        System.out.println("       CRICKETER MANAGEMENT SYSTEM");
        System.out.println("==============================================");
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
        System.out.println("16. Save Data");
        System.out.println("0.  Exit");
        System.out.println("==============================================");
    }

    private static void addPlayer() throws InvalidPlayerDataException, DuplicatePlayerException {
        Cricketer player = readPlayer(false, 0);
        service.addPlayer(player);
        System.out.println("Player added successfully.");
    }

    private static void updatePlayer() throws InvalidPlayerDataException, PlayerNotFoundException {
        int id = readInt("Enter Player ID to update: ");
        Cricketer player = readPlayer(true, id);
        service.updatePlayer(id, player);
        System.out.println("Player updated successfully.");
    }

    private static void deletePlayer() throws PlayerNotFoundException {
        int id = readInt("Enter Player ID to delete: ");
        service.deletePlayer(id);
        System.out.println("Player deleted successfully.");
    }

    private static Cricketer readPlayer(boolean updating, int playerId) {
        if (!updating) {
            playerId = readInt("Player ID: ");
        }
        String name = readString("Player Name: ");
        String country = readString("Country: ");
        int age = readInt("Age: ");
        String role = readString("Role: ");
        int matches = readInt("Matches: ");
        int runs = readInt("Runs: ");
        int highestScore = readInt("Highest Score: ");
        double battingAverage = readDouble("Batting Average: ");
        int wickets = readInt("Wickets: ");
        double bowlingAverage = readDouble("Bowling Average: ");
        return new Cricketer(playerId, name, country, age, role, matches, runs,
                highestScore, battingAverage, wickets, bowlingAverage);
    }

    private static void displayStatistics() throws PlayerNotFoundException {
        int id = readInt("Enter Player ID: ");
        displayHeading("PLAYER STATISTICS");
        displayPlayerDetails(service.findById(id));
    }

    private static void saveData() {
        // Add/Update/Delete are written to MySQL immediately.
        System.out.println("Data is already saved in MySQL.");
    }

    private static void displayPlayers(List<Cricketer> players) {
        if (players.isEmpty()) {
            System.out.println("No players found.");
            return;
        }
        System.out.printf("%-5s %-20s %-15s %-5s %-13s %-9s %-9s %-13s %-17s %-9s %-15s%n",
                "ID", "Name", "Country", "Age", "Role", "Matches", "Runs",
                "Highest Score", "Batting Average", "Wickets", "Bowling Average");
        System.out.println("-".repeat(135));
        for (Cricketer player : players) {
            System.out.printf("%-5d %-20s %-15s %-5d %-13s %-9d %-9d %-13d %-17.2f %-9d %-15.2f%n",
                    player.getPlayerId(), player.getName(), player.getCountry(), player.getAge(),
                    player.getRole(), player.getMatches(), player.getRuns(), player.getHighestScore(),
                    player.getBattingAverage(), player.getWickets(), player.getBowlingAverage());
        }
    }

    private static void displayHeading(String heading) {
        System.out.println("\n==========================================");
        System.out.println(heading);
        System.out.println("==========================================");
    }

    private static void displayPlayerDetails(Cricketer player) {
        System.out.println("Player ID       : " + player.getPlayerId());
        System.out.println("Name            : " + player.getName());
        System.out.println("Country         : " + player.getCountry());
        System.out.println("Age             : " + player.getAge());
        System.out.println("Role            : " + player.getRole());
        System.out.println("Matches         : " + player.getMatches());
        System.out.println("Runs            : " + player.getRuns());
        System.out.println("Highest Score   : " + player.getHighestScore());
        System.out.printf("Batting Average : %.2f%n", player.getBattingAverage());
        System.out.println("Wickets         : " + player.getWickets());
        System.out.printf("Bowling Average : %.2f%n", player.getBowlingAverage());
        System.out.println("------------------------------------------");
    }

    private static void displayValidationError(String message) {
        if (message.startsWith("Age")) {
            System.out.println("ERROR: Invalid player age.");
        } else if (message.startsWith("Statistics")) {
            System.out.println("ERROR: Statistics cannot be negative.");
        } else {
            System.out.println("ERROR: " + message + ".");
        }
    }

    private static String extractId(String message) {
        return message.substring(message.lastIndexOf(' ') + 1);
    }
}
