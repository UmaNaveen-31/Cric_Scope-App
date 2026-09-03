package cricketer.model;

public class Cricketer {

    private int playerId;
    private String name;
    private String country;
    private int age;
    private String role;

    private int matches;
    private int runs;
    private int highestScore;
    private double battingAverage;

    private int wickets;
    private double bowlingAverage;

    public Cricketer() {
    }

    public Cricketer(int playerId, String name, String country,
            int age, String role, int matches, int runs,
            int highestScore, double battingAverage,
            int wickets, double bowlingAverage) {

        this.playerId = playerId;
        this.name = name;
        this.country = country;
        this.age = age;
        this.role = role;
        this.matches = matches;
        this.runs = runs;
        this.highestScore = highestScore;
        this.battingAverage = battingAverage;
        this.wickets = wickets;
        this.bowlingAverage = bowlingAverage;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getMatches() {
        return matches;
    }

    public void setMatches(int matches) {
        this.matches = matches;
    }

    public int getRuns() {
        return runs;
    }

    public void setRuns(int runs) {
        this.runs = runs;
    }

    public int getHighestScore() {
        return highestScore;
    }

    public void setHighestScore(int highestScore) {
        this.highestScore = highestScore;
    }

    public double getBattingAverage() {
        return battingAverage;
    }

    public void setBattingAverage(double battingAverage) {
        this.battingAverage = battingAverage;
    }

    public int getWickets() {
        return wickets;
    }

    public void setWickets(int wickets) {
        this.wickets = wickets;
    }

    public double getBowlingAverage() {
        return bowlingAverage;
    }

    public void setBowlingAverage(double bowlingAverage) {
        this.bowlingAverage = bowlingAverage;
    }

    @Override
    public String toString() {

        return String.format(
            "%-5d %-20s %-15s %-5d %-12s %-8d %-10d %-10d %-10.2f %-8d %-10.2f",
            playerId,
            name,
            country,
            age,
            role,
            matches,
            runs,
            highestScore,
            battingAverage,
            wickets,
            bowlingAverage
        );
    }
}