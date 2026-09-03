package util;

import model.Cricketer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class FileUtil {
    private static final String FILE_NAME = "cricketers.txt";

    private FileUtil() {
    }

    public static void savePlayers(List<Cricketer> players) throws IOException {
        try (FileWriter fileWriter = new FileWriter(FILE_NAME);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            for (Cricketer player : players) {
                bufferedWriter.write(serialize(player));
                bufferedWriter.newLine();
            }
        }
    }

    public static List<Cricketer> loadPlayers() throws IOException {
        List<Cricketer> players = new ArrayList<>();
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return players;
        }

        try (FileReader fileReader = new FileReader(file);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            String line;
            int lineNumber = 0;
            while ((line = bufferedReader.readLine()) != null) {
                lineNumber++;
                if (!line.trim().isEmpty()) {
                    players.add(deserialize(line, lineNumber));
                }
            }
        }
        return players;
    }

    private static String serialize(Cricketer player) {
        return player.getPlayerId() + "|"
                + safeValue(player.getName()) + "|"
                + safeValue(player.getCountry()) + "|"
                + player.getAge() + "|"
                + safeValue(player.getRole()) + "|"
                + player.getMatches() + "|"
                + player.getRuns() + "|"
                + player.getHighestScore() + "|"
                + player.getBattingAverage() + "|"
                + player.getWickets() + "|"
                + player.getBowlingAverage();
    }

    private static Cricketer deserialize(String line, int lineNumber) throws IOException {
        String[] values = line.split("\\|", -1);
        if (values.length != 11) {
            throw new IOException("Invalid player data on line " + lineNumber);
        }

        try {
            return new Cricketer(
                    Integer.parseInt(values[0]),
                    values[1],
                    values[2],
                    Integer.parseInt(values[3]),
                    values[4],
                    Integer.parseInt(values[5]),
                    Integer.parseInt(values[6]),
                    Integer.parseInt(values[7]),
                    Double.parseDouble(values[8]),
                    Integer.parseInt(values[9]),
                    Double.parseDouble(values[10]));
        } catch (NumberFormatException exception) {
            throw new IOException("Invalid numeric player data on line " + lineNumber, exception);
        }
    }

    private static String safeValue(String value) {
        return value == null ? "" : value.replace("|", " ");
    }
}
