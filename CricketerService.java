package service;

import dao.CricketerDAO;
import exception.DuplicatePlayerException;
import exception.InvalidPlayerDataException;
import exception.PlayerNotFoundException;
import model.Cricketer;

import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class CricketerService {
    private final CricketerDAO dao = new CricketerDAO();

    public CricketerService() {
        // Data is loaded from MySQL. No in-memory copy is used.
    }

    public void addPlayer(Cricketer player) throws InvalidPlayerDataException, DuplicatePlayerException {
        Objects.requireNonNull(player, "Player cannot be null"); validatePlayer(player);
        try {
            if (dao.exists(player.getPlayerId())) throw new DuplicatePlayerException("Player ID already exists: " + player.getPlayerId());
            dao.insert(player);
        } catch (SQLException e) { throw dbError(e); }
    }

    public List<Cricketer> getAllPlayers() { try { return dao.findAll(); } catch(SQLException e){ throw dbError(e); } }
    public Cricketer findById(int id) throws PlayerNotFoundException { try { Cricketer p=dao.findById(id); if(p==null) throw new PlayerNotFoundException("No player found with ID: " + id); return p; } catch(SQLException e){ throw dbError(e); } }
    public List<Cricketer> searchByName(String name) throws InvalidPlayerDataException { String s=requireSearchTerm(name,"Name"); try{return dao.searchName(s);}catch(SQLException e){throw dbError(e);} }
    public List<Cricketer> searchByCountry(String country) throws InvalidPlayerDataException { String s=requireSearchTerm(country,"Country"); try{return dao.searchCountry(s);}catch(SQLException e){throw dbError(e);} }
    public List<Cricketer> searchByRole(String role) throws InvalidPlayerDataException { String s=requireSearchTerm(role,"Role"); try{return dao.searchRole(s);}catch(SQLException e){throw dbError(e);} }

    public void updatePlayer(int id, Cricketer updatedPlayer) throws PlayerNotFoundException, InvalidPlayerDataException {
        Objects.requireNonNull(updatedPlayer,"Player cannot be null"); findById(id);
        if(updatedPlayer.getPlayerId()!=id) throw new InvalidPlayerDataException("Updated player ID must match the existing ID: " + id);
        validatePlayer(updatedPlayer); try{dao.update(updatedPlayer);}catch(SQLException e){throw dbError(e);}
    }
    public void updatePlayer(Cricketer p) throws PlayerNotFoundException, InvalidPlayerDataException { updatePlayer(p.getPlayerId(),p); }
    public void deletePlayer(int id) throws PlayerNotFoundException { findById(id); try{dao.delete(id);}catch(SQLException e){throw dbError(e);} }
    public Cricketer getHighestRunScorer() throws PlayerNotFoundException { try{return require(dao.highestRuns());}catch(SQLException e){throw dbError(e);} }
    public Cricketer getHighestWicketTaker() throws PlayerNotFoundException { try{return require(dao.highestWickets());}catch(SQLException e){throw dbError(e);} }
    public Cricketer getHighestIndividualScore() throws PlayerNotFoundException { try{return require(dao.highestScore());}catch(SQLException e){throw dbError(e);} }
    public List<Cricketer> getTopRunScorers(){try{return dao.topRuns();}catch(SQLException e){throw dbError(e);}}
    public List<Cricketer> getTopWicketTakers(){try{return dao.topWickets();}catch(SQLException e){throw dbError(e);}}
    public List<Cricketer> sortByRuns(){try{return dao.sortRuns();}catch(SQLException e){throw dbError(e);}}
    public List<Cricketer> sortByAge(){try{return dao.sortAge();}catch(SQLException e){throw dbError(e);}}

    private Cricketer require(Cricketer p) throws PlayerNotFoundException { if(p==null) throw new PlayerNotFoundException("No players available"); return p; }
    private void validatePlayer(Cricketer p) throws InvalidPlayerDataException {
        if(p.getPlayerId()<=0) throw new InvalidPlayerDataException("Player ID must be greater than 0");
        if(isBlank(p.getName())) throw new InvalidPlayerDataException("Name cannot be empty");
        if(isBlank(p.getCountry())) throw new InvalidPlayerDataException("Country cannot be empty");
        if(p.getAge()<=0||p.getAge()>120) throw new InvalidPlayerDataException("Age must be between 1 and 120");
        if(p.getMatches()<0||p.getRuns()<0||p.getHighestScore()<0||p.getBattingAverage()<0||p.getWickets()<0||p.getBowlingAverage()<0) throw new InvalidPlayerDataException("Statistics cannot be negative");
    }
    private String requireSearchTerm(String s,String field) throws InvalidPlayerDataException { if(isBlank(s)) throw new InvalidPlayerDataException(field+" search term cannot be empty"); return s.trim().toLowerCase(Locale.ROOT); }
    private boolean isBlank(String s){return s==null||s.trim().isEmpty();}
    private RuntimeException dbError(SQLException e){ return new RuntimeException("Database error: " + e.getMessage(), e); }
}
