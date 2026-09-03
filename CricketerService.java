package cricketer.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import cricketer.exception.DuplicatePlayerException;
import cricketer.exception.InvalidPlayerDataException;
import cricketer.exception.PlayerNotFoundException;
import cricketer.model.Cricketer;

public class CricketerService {

    private List<Cricketer> players;

    public CricketerService() {
        players = new ArrayList<>();
        loadDefaultPlayers();
    }

    public void addPlayer(Cricketer player)
            throws DuplicatePlayerException,
            InvalidPlayerDataException {

        validatePlayer(player);

        if (findByIdWithoutException(player.getPlayerId()) != null) {
            throw new DuplicatePlayerException(
                "Player ID " + player.getPlayerId() + " already exists."
            );
        }

        players.add(player);
    }

    public List<Cricketer> getAllPlayers() {
        return players;
    }

    public Cricketer findById(int id)
            throws PlayerNotFoundException {

        Cricketer player = findByIdWithoutException(id);

        if (player == null) {
            throw new PlayerNotFoundException(
                "Player ID " + id + " not found."
            );
        }

        return player;
    }

    private Cricketer findByIdWithoutException(int id) {

        return players.stream()
                .filter(p -> p.getPlayerId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<Cricketer> searchByName(String name) {

        return players.stream()
                .filter(p -> p.getName()
                        .toLowerCase()
                        .contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Cricketer> searchByCountry(String country) {

        return players.stream()
                .filter(p -> p.getCountry()
                        .equalsIgnoreCase(country))
                .collect(Collectors.toList());
    }

    public List<Cricketer> searchByRole(String role) {

        return players.stream()
                .filter(p -> p.getRole()
                        .equalsIgnoreCase(role))
                .collect(Collectors.toList());
    }

    public void updatePlayer(Cricketer updated)
            throws PlayerNotFoundException,
            InvalidPlayerDataException {

        validatePlayer(updated);

        Cricketer existing =
                findById(updated.getPlayerId());

        existing.setName(updated.getName());
        existing.setCountry(updated.getCountry());
        existing.setAge(updated.getAge());
        existing.setRole(updated.getRole());
        existing.setMatches(updated.getMatches());
        existing.setRuns(updated.getRuns());
        existing.setHighestScore(updated.getHighestScore());
        existing.setBattingAverage(updated.getBattingAverage());
        existing.setWickets(updated.getWickets());
        existing.setBowlingAverage(updated.getBowlingAverage());
    }

    public void deletePlayer(int id)
            throws PlayerNotFoundException {

        Cricketer player = findById(id);

        players.remove(player);
    }

    public Cricketer getHighestRunScorer() {

        return players.stream()
                .max(Comparator.comparingInt(Cricketer::getRuns))
                .orElse(null);
    }

    public Cricketer getHighestWicketTaker() {

        return players.stream()
                .max(Comparator.comparingInt(Cricketer::getWickets))
                .orElse(null);
    }

    public Cricketer getHighestIndividualScore() {

        return players.stream()
                .max(Comparator.comparingInt(
                        Cricketer::getHighestScore))
                .orElse(null);
    }

    public List<Cricketer> getTopRunScorers() {

        return players.stream()
                .sorted(Comparator.comparingInt(
                        Cricketer::getRuns).reversed())
                .limit(5)
                .collect(Collectors.toList());
    }

    public List<Cricketer> getTopWicketTakers() {

        return players.stream()
                .sorted(Comparator.comparingInt(
                        Cricketer::getWickets).reversed())
                .limit(5)
                .collect(Collectors.toList());
    }

    public List<Cricketer> sortByRuns() {

        return players.stream()
                .sorted(Comparator.comparingInt(
                        Cricketer::getRuns).reversed())
                .collect(Collectors.toList());
    }

    public List<Cricketer> sortByAge() {

        return players.stream()
                .sorted(Comparator.comparingInt(
                        Cricketer::getAge))
                .collect(Collectors.toList());
    }

    private void validatePlayer(Cricketer player)
            throws InvalidPlayerDataException {

        if (player.getPlayerId() <= 0) {
            throw new InvalidPlayerDataException(
                "Player ID should be greater than 0."
            );
        }

        if (player.getName() == null ||
                player.getName().trim().isEmpty()) {

            throw new InvalidPlayerDataException(
                "Name cannot be empty."
            );
        }

        if (player.getCountry() == null ||
                player.getCountry().trim().isEmpty()) {

            throw new InvalidPlayerDataException(
                "Country cannot be empty."
            );
        }

        if (player.getAge() <= 0) {
            throw new InvalidPlayerDataException(
                "Invalid player age."
            );
        }

        if (player.getMatches() < 0 ||
                player.getRuns() < 0 ||
                player.getHighestScore() < 0 ||
                player.getWickets() < 0) {

            throw new InvalidPlayerDataException(
                "Statistics cannot be negative."
            );
        }

        if (player.getBattingAverage() < 0 ||
                player.getBowlingAverage() < 0) {

            throw new InvalidPlayerDataException(
                "Average cannot be negative."
            );
        }
    }

    private void loadDefaultPlayers() {

        players.add(new Cricketer(
            101, "Virat Kohli", "India", 37,
            "Batsman", 292, 13848, 254,
            58.67, 5, 45.00
        ));

        players.add(new Cricketer(
            102, "Rohit Sharma", "India", 39,
            "Batsman", 499, 19400, 264,
            48.96, 47, 36.00
        ));

        players.add(new Cricketer(
            103, "Jasprit Bumrah", "India", 32,
            "Bowler", 196, 1000, 43,
            15.00, 450, 22.50
        ));

        players.add(new Cricketer(
            104, "Joe Root", "England", 35,
            "Batsman", 350, 13000, 262,
            50.20, 20, 50.00
        ));

        players.add(new Cricketer(
            105, "Steve Smith", "Australia", 37,
            "Batsman", 330, 10500, 239,
            49.50, 28, 60.00
        ));

        players.add(new Cricketer(
            106, "Kane Williamson", "New Zealand", 36,
            "Batsman", 320, 9500, 251,
            48.75, 37, 42.00
        ));

        players.add(new Cricketer(
            107, "Ravindra Jadeja", "India", 37,
            "All Rounder", 300, 6500, 175,
            35.50, 550, 29.00
        ));

        players.add(new Cricketer(
            108, "Ben Stokes", "England", 35,
            "All Rounder", 260, 7000, 258,
            38.50, 300, 32.00
        ));

        players.add(new Cricketer(
            109, "Babar Azam", "Pakistan", 31,
            "Batsman", 280, 12000, 196,
            52.40, 12, 55.00
        ));

        players.add(new Cricketer(
            110, "Pat Cummins", "Australia", 33,
            "Bowler", 250, 2500, 95,
            20.50, 500, 25.40
        ));
    }
}
