package com.leagueReminder.maven.classes;

import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Serializable {
	private String summonerName;
	private int gamesLeft;
	private ArrayList<Reminder> reminders = new ArrayList<Reminder>();
	private boolean ingame;
	//**This might also remove the need for a reminders.ser file, as players.ser will store all reminders under each player.
	//	**Will need to think about the effects of this wrt an options menu for deleting reminders.
	// e.g. could have reminders menu where each player's name is listed, and then below that are the reminders for them etc.
	
	public Player(String summonerName, int gamesLeft) {
		this.summonerName = summonerName;
		this.gamesLeft = gamesLeft;
		this.ingame = false;
	}
	
	public void addReminder(Reminder r) {
		reminders.add(r);
	}
	
	public void removeReminder(Reminder r) {
		reminders.remove(r);
	}
	
	public ArrayList<Reminder> getReminders() {
		return reminders;
	}
	
	public String getName() {
		return this.summonerName;
	}
	
	public void setGamesLeft(int gamesLeft) {
		this.gamesLeft = gamesLeft;
	}
	
	public int getGamesLeft() {
		return this.gamesLeft;
	}
	
	public void decrement() {
		gamesLeft--;
	}
	
	public void setIngame(boolean s) {
		this.ingame = s;
	}
	
	public boolean getIngame() {
		return this.ingame;
	}
	
	public String toString() {
		return "Player [" + summonerName + ", reminders: " + reminders + "]";
	}
}
