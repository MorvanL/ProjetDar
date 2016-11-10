package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;


@Entity
@Table(name="GameSessions")
public class GameSession {
	
	public GameSession(){}
	
	// Properties 
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer idSession;
	
	@ManyToOne(optional=false)
	private Player root;
	
    @Basic(optional=false)
    private String label;
    
    private String desc;

    @ManyToMany
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Player> players = new ArrayList<>();
	
	@Basic(optional=false)
	private String address;
	
	@Basic(optional=false)
	private String postCode;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Basic(optional=false)
    private Date timestamp;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Basic(optional=false)
	private Date date; 
	
	@ElementCollection
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<String> games = new ArrayList<>();
	
	@ElementCollection
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<String> platforms = new ArrayList<>();
		
	
	// Getters/Setters 
	public Integer getIdSession() {
		return idSession;
	}
	public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    public String getPostCode() {
        return postCode;
    }
    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }
    public Date getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
    public void setIdSession(Integer idSession) {
		this.idSession = idSession;
	}
	public Player getRoot() {
		return root;
	}
	public void setRoot(Player root) {
		this.root = root;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public List<Player> getPlayers() {
		return players;
	}
	public void setPlayers(List<Player> players) {
		this.players = players;
	}
	public List<String> getGames() {
		return games;
	}
	public void setGames(List<String> games) {
        this.games = games;
    }
    public void setPlatforms(List<String> platforms) {
        this.platforms = platforms;
    }
    public List<String> getPlatforms() {
		return platforms;
	}
    public String getLabel() {
        return label;
    }
    public void setLabel(String label) {
        this.label = label;
    }
	
}
