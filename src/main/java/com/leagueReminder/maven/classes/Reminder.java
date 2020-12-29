package com.leagueReminder.maven.classes;

import java.io.Serializable;

public class Reminder implements Serializable {
	private String summonerName;
	private String reminderText;
	private int numGames;
	
	public Reminder(String summonerName, String reminderText, int numGames) {
		this.summonerName = summonerName;
		this.reminderText = reminderText;
		this.numGames = numGames;
	}
	
	public void decrement() {
		numGames--;
	}
	
	public String toString() {
		return "Reminder [" + summonerName + ", " + reminderText + ", " + numGames + "]";
	}
}
