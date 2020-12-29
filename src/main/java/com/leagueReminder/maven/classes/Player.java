package com.leagueReminder.maven.classes;

import java.io.Serializable;

public class Player implements Serializable {
	private String summonerName;
	private int gamesLeft;
	//list of reminders? when gamestatuschecker detects that Player has finished a game, it'll make it easier to activate
	//all reminders that need to be activated, rather than having to cycle through all reminders.
	//**This might also remove the need for a reminders.ser file, as players.ser will store all reminders under each player.
	//	**Will need to think about the effects of this wrt an options menu for deleting reminders.
	// e.g. could have reminders menu where each player's name is listed, and then below that are the reminders for them etc.
	
	public Player(String summonerName, int gamesLeft) {
		this.summonerName = summonerName;
		this.gamesLeft = gamesLeft;
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
	
	public String toString() {
		return "Player [" + summonerName + "]";
	}
}
