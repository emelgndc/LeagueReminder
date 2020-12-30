package com.leagueReminder.maven.classes;

import java.util.TimerTask;
import com.merakianalytics.orianna.*;
import com.merakianalytics.orianna.types.core.summoner.Summoner;

public class CheckerTask extends TimerTask {
	private Summoner s;
	
	@Override
	public void run() {
		for (Player p: App.players) {	//loop through all players with active reminders
			s = Summoner.named(p.getName()).get();	//fetch summoner
			
			if (p.getIngame() == true) {	// if player was but is no longer ingame, activate reminders	
				if (s.isInGame() == false) {
					p.setIngame(false);
					for (Reminder r: p.getReminders()) {
						App.activateReminder(r);
						r.decrement();
						if (r.getNumGames() == 0) {
							p.removeReminder(r);
						}
					}
				}
			} else {
				if (s.isInGame() == true) {	// if player was not but now is ingame, change status
					p.setIngame(true);
				}
			}
		}
	}
}
