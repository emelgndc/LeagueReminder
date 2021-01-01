package com.leagueReminder.maven.classes;

import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4046055443996823139L;
	private String summonerName;
	private ArrayList<Reminder> reminders = new ArrayList<Reminder>();
	private boolean ingame;
	
	public Player(String summonerName, int gamesLeft) {
		this.summonerName = summonerName;
		this.ingame = false;
	}
	
	public void addReminder(Reminder r) {
		reminders.add(r);
	}
	
	public void removeReminder(Reminder r) {
		reminders.remove(r);
		System.out.println("reminder removed. remaining: " + reminders);
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
	
	public boolean getIngame() {
		return this.ingame;
	}
	
	public String toString() {
		return "Player [" + summonerName + ingame + ", reminders: " + reminders + "]";
	}
}
