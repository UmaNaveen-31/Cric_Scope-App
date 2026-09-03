package cricketer.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;
import cricketer.model.Cricketer;
import cricketer.util.DBConnection;

public class CricketerDAO {

    public List<Cricketer> getAllCricketers() {

        List<Cricketer> list = new ArrayList<>();

        String sql = "SELECT * FROM cricketers";

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                Cricketer c = new Cricketer();

                c.setPlayerId(
                    rs.getInt("player_id")
                );

                c.setName(
                    rs.getString("name")
                );

                c.setCountry(
                    rs.getString("country")
                );

                c.setAge(
                    rs.getInt("age")
                );

                c.setRole(
                    rs.getString("role")
                );

                c.setMatches(
                    rs.getInt("matches")
                );

                c.setRuns(
                    rs.getInt("runs")
                );

                c.setHighestScore(
                    rs.getInt("highest_score")
                );

                c.setBattingAverage(
                    rs.getDouble("batting_average")
                );

                c.setWickets(
                    rs.getInt("wickets")
                );

                c.setBowlingAverage(
                    rs.getDouble("bowling_average")
                );

                list.add(c);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return list;
    }
    public boolean addCricketer(Cricketer c) {

        String sql = "INSERT INTO cricketers "
                + "(player_id, name, country, age, role, "
                + "matches, runs, highest_score, batting_average, "
                + "wickets, bowling_average) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, c.getPlayerId());
            ps.setString(2, c.getName());
            ps.setString(3, c.getCountry());
            ps.setInt(4, c.getAge());
            ps.setString(5, c.getRole());
            ps.setInt(6, c.getMatches());
            ps.setInt(7, c.getRuns());
            ps.setInt(8, c.getHighestScore());
            ps.setDouble(9, c.getBattingAverage());
            ps.setInt(10, c.getWickets());
            ps.setDouble(11, c.getBowlingAverage());

            int rows = ps.executeUpdate();

            return rows > 0;

        } catch (SQLException e) {

            e.printStackTrace();
            return false;
        }
    }
}
