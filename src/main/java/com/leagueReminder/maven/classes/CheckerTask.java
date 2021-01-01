package com.leagueReminder.maven.classes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TimerTask;
import com.merakianalytics.orianna.*;
import com.merakianalytics.orianna.types.common.Region;
import com.merakianalytics.orianna.types.core.summoner.Summoner;

public class CheckerTask extends TimerTask {
	private ArrayList<Player> removeP;
	private ArrayList<Reminder> removeR;
	private Summoner s;
	private Reminder r;
	
	@Override
	public void run() {
		removeP = new ArrayList<Player>();
		
		for (Player p: App.players) {	//loop through all players with active reminders
			removeR = new ArrayList<Reminder>();
			System.out.print(p.getName());
			
			Orianna.loadConfiguration("config.json");
			Orianna.setRiotAPIKey("API KEY HERE");
			
			s = Orianna.summonerNamed(p.getName()).withRegion(Region.OCEANIA).get();	//fetch summoner
			System.out.println(s.getCurrentMatch().exists());
			
			if (p.getIngame() == true) {	// if player was but is no longer ingame, activate reminders	
				if (s.getCurrentMatch().exists() == false) {
					System.out.println("finished game - set false and activate reminder");
					p.setIngame(false);
					for (Iterator<Reminder> it = p.getReminders().iterator(); it.hasNext();) {
						r = it.next();
						App.activateReminder(r);
						if (r.getNumGames() == 0) {			//skip this iteration if reminder is unlimited (0)
							continue;
						} else {
							r.decrement();
							if (r.getNumGames() == 0) {		//remove reminder if there are no games left
								removeR.add(r);				//add to removal list (avoids concurrentmodificationexception)
							}
						}
					}
				}
			} else {
				if (s.getCurrentMatch().exists() == true) {	// if player was not but now is ingame, change status
					p.setIngame(true);
					System.out.println("ingame - set true");
				}
			}
			
			s = null;
			for (Reminder rem: removeR) {
				p.removeReminder(rem);	//remove reminders that have no games left
				System.out.println(App.players);
			}
			
			if (p.getReminders().size() == 0) {
				removeP.add(p);		//add to removal list
			}
		}
		
		for (Player p: removeP) {
			App.players.remove(p);	//remove players that have no reminders left
			
		}
		App.serializeData();	//save changes onto disk
	}
}
