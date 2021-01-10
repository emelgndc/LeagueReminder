package com.leagueReminder.maven.classes;

import java.io.Serializable;
import java.util.ArrayList;

import org.joda.time.DateTime;

public class Player implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4046055443996823139L;
	private String summonerName;
	private ArrayList<Reminder> reminders = new ArrayList<Reminder>();
	private boolean ingame;
	private DateTime lastCreationTime;
	
	public Player(String summonerName, int gamesLeft) {
		this.summonerName = summonerName;
		this.ingame = false;
	}
	
	public void addReminder(Reminder r) {
		reminders.add(r);
		r.setPlayer(this);
	}
	
	public void removeReminder(Reminder r) {
		reminders.remove(r);
		r.setPlayer(null);
	}
	
	public int size() {
		return reminders.size();
	}
	
	public ArrayList<Reminder> getReminders() {
		return reminders;
	}
	
	public String getName() {
		return this.summonerName;
	}
	
	public void setIngame(boolean s) {
		this.ingame = s;
	}
	
	public void setLCT(DateTime ct) {
		this.lastCreationTime = ct;		
	}
	
	public DateTime getLCT() {
		return this.lastCreationTime;
	}
	
	public boolean getIngame() {
		return this.ingame;
	}
	
	public String toString() {
		return "Player [" + summonerName + ingame + ", reminders: " + reminders + "]";
	}
}
