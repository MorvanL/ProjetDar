package dao;

import java.util.ArrayList;
import java.util.List;

import models.GameSession;
import models.GameSessionDao;
import models.Player;

import org.hibernate.Session;
import org.hibernate.Transaction;

import utils.HibernateUtils;


public class GameSessionDaoImpl implements GameSessionDao {
	
	public GameSessionDaoImpl() {
	}
	
    @Override
	public void insert(GameSession gameSession) {
		Session s = HibernateUtils.getSession();
        Transaction t = s.beginTransaction();

        s.save(gameSession);
        t.commit();
        s.close();
	}
	
    @Override
	public void delete(GameSession gameSession) {
		Session s = HibernateUtils.getSession();
        Transaction t = s.beginTransaction();

        s.delete(gameSession);
        t.commit();
        s.close();
	}
	
    @Override
	public void update(GameSession gameSession) {
		Session s = HibernateUtils.getSession();
        Transaction t = s.beginTransaction();

        s.update(gameSession);
        t.commit();
        s.close();
	}
    
    @Override
    public List<GameSession> getGameSessionByRoot(Player p) {
        Session s = HibernateUtils.getSession();
        // Take all GameSession
        List<GameSession> gameSessions = s.createQuery("select gs from GameSession gs", GameSession.class)
                                                   .getResultList();
        
        List<GameSession> resultGameSessions = new ArrayList<>();
        for (GameSession gameSession : gameSessions) {
            List<Player> players = gameSession.getPlayers();
            for (Player player : players) {
                // Only keep game sessions which the player participates
                if (player.getIdPlayer() == p.getIdPlayer()) {
                    resultGameSessions.add(gameSession);
                }
            }
        }
        
        s.close();
        return resultGameSessions;
    }
	
    @Override
	public List<GameSession> getGameSessionCreatedByRoot(Player p) {
		Session s = HibernateUtils.getSession();
		List<GameSession> gameSessions = s.createQuery("select gs from GameSession gs where gs.root like :root", GameSession.class)
							              .setParameter("root", p)
							              .getResultList();
		
		s.close();
		return gameSessions;
	}
    
	@Override
	public List<GameSession> getGameSessionByGame(String gameName) {
		// A completer
		return null;
	}
	
	@Override
	public List<GameSession> getGameSessionByPlatform(String platformName) {
		// A completer
		return null;
	}

}
