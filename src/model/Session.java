package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name="Sessions")
public class Session {
	
	// Properties 
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer idSession;
	
	@ManyToOne(optional=false, cascade = CascadeType.ALL)
	private Player root;
	
	@ManyToMany(cascade = CascadeType.ALL)
	private List<Player> players = new ArrayList<Player>();
	
	@Basic(optional=false)
	private String address;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Basic(optional=false)
	private Date date; 
		
	
	// Getters/Setters 
	public Integer getIdSession() {
		return idSession;
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
	
}