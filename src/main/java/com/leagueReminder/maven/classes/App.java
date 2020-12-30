package com.leagueReminder.maven.classes;

import java.io.*;
import java.util.ArrayList;
import java.util.Timer;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.merakianalytics.orianna.Orianna;
import com.merakianalytics.orianna.types.core.summoner.Summoner;

public class App 
{
	static ArrayList<Player> players = new ArrayList<Player>();
	
    @SuppressWarnings("unchecked")
	public static void main( String[] args )
    {
        Orianna.loadConfiguration("config.json");
        Orianna.setRiotAPIKey("API KEY HERE");
        
        Window window = new Window();

        //Deserialize players and reminders list
        try {
        	FileInputStream playersIn = new FileInputStream("data/players.ser");
        	ObjectInputStream objin = new ObjectInputStream(playersIn);
        	players = (ArrayList<Player>)objin.readObject();
        	objin.close();
        	playersIn.close();

        } catch (EOFException e)  {			//file is empty - nothing stored, this is fine!
        	return;
        } catch (FileNotFoundException f) {	//files do not exist. probably due to first launch. also fine!
        	try {
        		FileOutputStream playersOut = new FileOutputStream("data/players.ser");
                //FileOutputStream remindersOut = new FileOutputStream("data/reminders.ser");
                playersOut.close();
                //remindersOut.close();
        	} catch (IOException j) {
        		j.printStackTrace();
        		return;
        	}
            return;
        } catch (IOException i) {
        	i.printStackTrace();
        	return;
        } catch (ClassNotFoundException c) {
        	System.out.println("class not found");
        	c.printStackTrace();
        	return;
        }
        
        for (Player player: players) {
        	System.out.println(player);
        	for (Reminder r: player.getReminders()) {
        		System.out.println(r);
        	}
        }
        
    	Timer t = new Timer();
    	t.schedule(new CheckerTask(), 0, 10000);
    }
    
    public static void serializeData() {
        try {
            FileOutputStream playersOut = new FileOutputStream("data/players.ser");
            ObjectOutputStream objout = new ObjectOutputStream(playersOut);
            objout.writeObject(players);
            objout.close();
            playersOut.close();
         } catch (IOException i) {
            i.printStackTrace();
         }
    }
    
    public static void setReminder(String summonerName, String reminderText, int numGames) {
    	boolean uniqueP = true;
    	Player existing = null;
    	//System.out.println(players);
    	for (Player p: players) {				//check if player is in players list already
    		if (p.getName().equals(summonerName)) {
    			uniqueP = false;
    			existing = p;
    			break;
    		}
    	}
    	
    	Reminder r = new Reminder(summonerName, reminderText, numGames);
    	
    	if (uniqueP) {	//if player is unique (i.e. not already in list), create a new Player
    		Player player = new Player(summonerName, numGames);
    		players.add(player);
    		player.addReminder(r);
    		//System.out.println("WAS UNIQUE");
    	} else {		//otherwise, set gamesLeft to highest value
    		existing.setGamesLeft(Math.max(numGames, existing.getGamesLeft()));
    		existing.addReminder(r);
    		//System.out.println("WAS NOT UNIQUE");
    	}
    	
        serializeData();
    }
    
    public static void activateReminder(Reminder r) {
    	JOptionPane o = new JOptionPane(r.getText());
    	JDialog d = o.createDialog(null, "LeagueReminder");
    	d.setAlwaysOnTop(true);
    	d.setVisible(true);
    }
    
//    public void gameStatusChecker() {
//    	Timer t = new Timer();
//    	t.schedule(new CheckerTask(), 0, 10000);
//    }
}
