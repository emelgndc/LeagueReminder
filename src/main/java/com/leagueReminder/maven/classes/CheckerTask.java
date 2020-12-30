package com.leagueReminder.maven.classes;

import java.util.TimerTask;
import com.merakianalytics.orianna.*;
import com.merakianalytics.orianna.types.common.Region;
import com.merakianalytics.orianna.types.core.summoner.Summoner;

public class CheckerTask extends TimerTask {
	private Summoner s;
	
	@Override
	public void run() {
		for (Player p: App.players) {	//loop through all players with active reminders
			System.out.print(p.getName());
			s = Summoner.named(p.getName()).withRegion(Region.OCEANIA).get();	//fetch summoner
			System.out.println(s.isInGame());
			if (p.getIngame() == true) {	// if player was but is no longer ingame, activate reminders	
				if (s.isInGame() == false) {
					System.out.println("finished game - set false and activate reminder");
					p.setIngame(false);
					for (Reminder r: p.getReminders()) {
						App.activateReminder(r);
						if (r.getNumGames() == 0) {			//skip this iteration if reminder is unlimited (0)
							continue;
						} else {
							r.decrement();
							p.decrement();
							if (r.getNumGames() == 0) {		//remove reminder if there are no games left
								p.removeReminder(r);
								if (p.getReminders().size() == 0) {
									App.players.remove(p);	//remove player if it has no active reminders
								}
							}
						}
					}
					App.serializeData();	//save changes onto disk
				}
			} else {
				if (s.isInGame() == true) {	// if player was not but now is ingame, change status
					p.setIngame(true);
					System.out.println("ingame - set true");
				}
			}
		}
	}
}
