package com.leagueReminder.maven.classes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TimerTask;

import javax.swing.JOptionPane;

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
	private DateTime cmct;
	private ArrayList<Player> playersCopy;
	
	@Override
	public void run() {
		removeP = new ArrayList<Player>();
		playersCopy = new ArrayList<Player>(App.players);
		for (Player p: playersCopy) {	//loop through all players with active reminders

			removeR = new ArrayList<Reminder>();
			System.out.println(p.getName());
			
			Orianna.loadConfiguration("config.json");
			Orianna.setRiotAPIKey("RGAPI-6a5e8e8b-1516-471a-94a0-bb76365696d2");
			
			s = Orianna.summonerNamed(p.getName()).withRegion(Region.OCEANIA).get();	//fetch summoner
			
    		try {
    			cm = s.getCurrentMatch();
    			cmct = cm.getCreationTime();
    		} catch (NullPointerException x) {	// summoner doesnt exist
    			System.out.println("summoner " + p.getName() + " does not exist");
    			
    			s = null;
    			boolean empty = App.cleanReminders(p, removeR);
    			if (empty) removeP.add(p);
    			
    			continue;
    		}

			//CHECKING IF PLAYER IS NO LONGER INGAME 
			if (p.getIngame() == true) {	// if player was but is no longer ingame, activate reminders
				System.out.println(cmct + " INGAME");
				if (cm.exists() == false) {	// (no longer ingame)
					System.out.println("finished game - set false and activate reminder");	
					System.out.println(cmct + " FINISHED");
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
					if (cm.getCreationTime().isEqual(p.getLCT())) {
						System.out.println("bugged trigger, ignore");	//api sometimes shows a player as ingame after game has ended
					} else {
						p.setLCT(cmct);									//save the creationTime of the match to avoid bugged reminder triggers
						p.setIngame(true);							
						System.out.println("ingame - set true");
					}
				}
			}
			
			s = null;
			
			boolean empty = App.cleanReminders(p, removeR);
			if (empty) removeP.add(p);
			
		}
		
		App.cleanPlayers(removeP);
		App.serializeData();	//save changes onto disk
	}
}
