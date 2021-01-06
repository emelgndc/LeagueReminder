package com.leagueReminder.maven.classes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TimerTask;

import org.joda.time.DateTime;

import com.merakianalytics.orianna.*;
import com.merakianalytics.orianna.types.common.Region;
import com.merakianalytics.orianna.types.core.spectator.CurrentMatch;
import com.merakianalytics.orianna.types.core.summoner.Summoner;

public class CheckerTask extends TimerTask {
	private ArrayList<Player> removeP;
	private ArrayList<Reminder> removeR;
	private Summoner s;
	private CurrentMatch cm;
	private Reminder r;
	private DateTime creationTime;
	private DateTime cmct;
	
	@Override
	public void run() {
		removeP = new ArrayList<Player>();
		
		for (Player p: App.players) {	//loop through all players with active reminders
			removeR = new ArrayList<Reminder>();
			System.out.print(p.getName() + " ");
			
			Orianna.loadConfiguration("config.json");
			Orianna.setRiotAPIKey("API KEY HERE");
			
			s = Orianna.summonerNamed(p.getName()).withRegion(Region.OCEANIA).get();	//fetch summoner
			cm = s.getCurrentMatch();
			cmct = cm.getCreationTime();
			//System.out.println(cm);
			
			//CHECKING IF PLAYER IS NO LONGER INGAME 
			if (p.getIngame() == true) {	// if player was but is no longer ingame, activate reminders
				System.out.println(creationTime + " " + cmct + "INGAME");
				if (cm.exists() == false) {	// (no longer ingame)
					System.out.println("finished game - set false and activate reminder");	
					System.out.println(creationTime + " " + cm.getCreationTime() + "FINISHED");
					p.setIngame(false);
					for (Iterator<Reminder> it = p.getReminders().iterator(); it.hasNext();) {
						r = it.next();
						App.activateReminder(r);
						if (r.getNumGames() == 0) {			//skip this iteration if reminder is unlimited (0)
							//System.out.println(r + "is unlimited");
							continue;
						} else {
							r.decrement();
							if (r.getNumGames() == 0) {		//remove reminder if there are no games left
								removeR.add(r);				//add to removal list (avoids concurrentmodificationexception)
							}
						}
					}
				}
				
			//CHECKING IF PLAYER HAS STARTED A GAME
			} else {
				if (cm.exists() == true && cmct.isEqual(0) == false) {	//api sometimes shows a match creation time as 0
					if (cm.getCreationTime().isEqual(creationTime)) {
						System.out.println("bugged trigger, ignore");	//api sometimes shows a player as ingame after game has ended
					} else {
						System.out.println(creationTime);
						creationTime = cm.getCreationTime();	//save the creationTime of the match to avoid bugged reminder triggers
						System.out.println(creationTime);
						p.setIngame(true);
						System.out.println("ingame - set true");
					}
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
