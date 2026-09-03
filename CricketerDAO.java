package dao;

import model.Cricketer;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CricketerDAO {
    private static final String INSERT_PLAYER = "INSERT INTO players (player_id,name,country,age,role) VALUES (?,?,?,?,?)";
    private static final String INSERT_BATTING = "INSERT INTO batting_statistics (player_id,matches,runs,highest_score,batting_average) VALUES (?,?,?,?,?)";
    private static final String INSERT_BOWLING = "INSERT INTO bowling_statistics (player_id,wickets,bowling_average) VALUES (?,?,?)";
    private static final String SELECT_ALL = "SELECT p.player_id,p.name,p.country,p.age,p.role,b.matches,b.runs,b.highest_score,b.batting_average,w.wickets,w.bowling_average FROM players p JOIN batting_statistics b ON b.player_id=p.player_id JOIN bowling_statistics w ON w.player_id=p.player_id";

    public boolean exists(int id) throws SQLException {
        try (Connection c = DatabaseConnection.getConnection(); PreparedStatement p = c.prepareStatement("SELECT 1 FROM players WHERE player_id=?")) {
            p.setInt(1, id);
            try (ResultSet r = p.executeQuery()) { return r.next(); }
        }
    }

    public void insert(Cricketer x) throws SQLException {
        try (Connection c = DatabaseConnection.getConnection()) {
            c.setAutoCommit(false);
            try (PreparedStatement player = c.prepareStatement(INSERT_PLAYER); PreparedStatement batting = c.prepareStatement(INSERT_BATTING); PreparedStatement bowling = c.prepareStatement(INSERT_BOWLING)) {
                fillPlayer(player, x); fillBatting(batting, x); fillBowling(bowling, x);
                player.executeUpdate(); batting.executeUpdate(); bowling.executeUpdate(); c.commit();
            } catch (SQLException e) { c.rollback(); throw e; }
        }
    }

    public void update(Cricketer x) throws SQLException {
        try (Connection c = DatabaseConnection.getConnection()) {
            c.setAutoCommit(false);
            try (PreparedStatement player = c.prepareStatement("UPDATE players SET name=?,country=?,age=?,role=? WHERE player_id=?"); PreparedStatement batting = c.prepareStatement("UPDATE batting_statistics SET matches=?,runs=?,highest_score=?,batting_average=? WHERE player_id=?"); PreparedStatement bowling = c.prepareStatement("UPDATE bowling_statistics SET wickets=?,bowling_average=? WHERE player_id=?")) {
                player.setString(1,x.getName()); player.setString(2,x.getCountry()); player.setInt(3,x.getAge()); player.setString(4,x.getRole()); player.setInt(5,x.getPlayerId());
                fillBatting(batting, x); fillBowling(bowling, x);
                player.executeUpdate(); batting.executeUpdate(); bowling.executeUpdate(); c.commit();
            } catch (SQLException e) { c.rollback(); throw e; }
        }
    }

    public void delete(int id) throws SQLException {
        try (Connection c = DatabaseConnection.getConnection(); PreparedStatement p = c.prepareStatement("DELETE FROM players WHERE player_id=?")) {
            p.setInt(1,id); p.executeUpdate();
        }
    }

    public Cricketer findById(int id) throws SQLException {
        try (Connection c = DatabaseConnection.getConnection(); PreparedStatement p = c.prepareStatement(SELECT_ALL + " WHERE p.player_id=?")) {
            p.setInt(1,id); try (ResultSet r=p.executeQuery()) { return r.next() ? map(r) : null; }
        }
    }

    public List<Cricketer> findAll() throws SQLException { return query(SELECT_ALL + " ORDER BY p.player_id"); }
    public List<Cricketer> searchName(String s) throws SQLException { return query(SELECT_ALL + " WHERE LOWER(p.name) LIKE ? ORDER BY p.name", "%"+s.toLowerCase()+"%"); }
    public List<Cricketer> searchCountry(String s) throws SQLException { return query(SELECT_ALL + " WHERE LOWER(p.country) LIKE ? ORDER BY p.name", "%"+s.toLowerCase()+"%"); }
    public List<Cricketer> searchRole(String s) throws SQLException { return query(SELECT_ALL + " WHERE LOWER(p.role)=? ORDER BY p.name", s.toLowerCase()); }
    public List<Cricketer> topRuns() throws SQLException { return query(SELECT_ALL + " ORDER BY b.runs DESC LIMIT 5"); }
    public List<Cricketer> topWickets() throws SQLException { return query(SELECT_ALL + " ORDER BY w.wickets DESC LIMIT 5"); }
    public List<Cricketer> sortRuns() throws SQLException { return query(SELECT_ALL + " ORDER BY b.runs DESC"); }
    public List<Cricketer> sortAge() throws SQLException { return query(SELECT_ALL + " ORDER BY p.age ASC"); }
    public Cricketer highestRuns() throws SQLException { return one(SELECT_ALL + " ORDER BY b.runs DESC LIMIT 1"); }
    public Cricketer highestWickets() throws SQLException { return one(SELECT_ALL + " ORDER BY w.wickets DESC LIMIT 1"); }
    public Cricketer highestScore() throws SQLException { return one(SELECT_ALL + " ORDER BY b.highest_score DESC LIMIT 1"); }

    private List<Cricketer> query(String sql, Object... args) throws SQLException {
        List<Cricketer> list=new ArrayList<>();
        try(Connection c=DatabaseConnection.getConnection(); PreparedStatement p=c.prepareStatement(sql)) {
            for(int i=0;i<args.length;i++) p.setObject(i+1,args[i]);
            try(ResultSet r=p.executeQuery()){ while(r.next()) list.add(map(r)); }
        }
        return list;
    }
    private Cricketer one(String sql) throws SQLException { List<Cricketer> x=query(sql); return x.isEmpty()?null:x.get(0); }
    private void fillPlayer(PreparedStatement p, Cricketer x) throws SQLException {
        p.setInt(1,x.getPlayerId()); p.setString(2,x.getName()); p.setString(3,x.getCountry()); p.setInt(4,x.getAge()); p.setString(5,x.getRole());
    }
    private void fillBatting(PreparedStatement p, Cricketer x) throws SQLException {
        p.setInt(1,x.getPlayerId()); p.setInt(2,x.getMatches()); p.setInt(3,x.getRuns()); p.setInt(4,x.getHighestScore()); p.setDouble(5,x.getBattingAverage());
    }
    private void fillBowling(PreparedStatement p, Cricketer x) throws SQLException {
        p.setInt(1,x.getPlayerId()); p.setInt(2,x.getWickets()); p.setDouble(3,x.getBowlingAverage());
    }
    private Cricketer map(ResultSet r) throws SQLException {
        return new Cricketer(r.getInt("player_id"),r.getString("name"),r.getString("country"),r.getInt("age"),r.getString("role"),r.getInt("matches"),r.getInt("runs"),r.getInt("highest_score"),r.getDouble("batting_average"),r.getInt("wickets"),r.getDouble("bowling_average"));
    }
}
