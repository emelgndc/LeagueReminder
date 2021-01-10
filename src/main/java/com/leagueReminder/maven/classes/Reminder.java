package com.leagueReminder.maven.classes;

import java.io.Serializable;

public class Reminder implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1400397103907294682L;
	private String summonerName;
	private String reminderText;
	private int numGames;
	private Player player;
	
	public Reminder(String summonerName, String reminderText, int numGames) {
		this.summonerName = summonerName;
		this.reminderText = reminderText;
		this.numGames = numGames;
	}
	
	public String getText() {
		return this.reminderText;
	}
	
	public int getNumGames() {
		return numGames;
	}
	
	public void setPlayer(Player p) {
		this.player = p;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public void decrement() {
		numGames--;
	}
	
	public String toString() {
		if (numGames == 0) return summonerName + " | unlimited | '" + reminderText + "'";
		return summonerName + " | " + numGames + " games" + " | '" + reminderText + "'";
	}
}
