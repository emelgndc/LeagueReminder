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
	static Timer timer = new Timer();
	private static boolean timerActive = false;
	
    @SuppressWarnings("unchecked")
	public static void main( String[] args )
    {
        Orianna.loadConfiguration("config.json");
        Orianna.setRiotAPIKey("RGAPI-c12552ef-9307-4026-919c-96c91c71b5da");
        
        Window window = new Window();

        //Deserialize players and reminders list
        try {
        	FileInputStream playersIn = new FileInputStream("data/players.ser");
        	ObjectInputStream objin = new ObjectInputStream(playersIn);
        	players = (ArrayList<Player>)objin.readObject();
        	objin.close();
        	playersIn.close();

        } catch (EOFException e)  {			//file is empty - nothing stored, this is fine!
        	System.out.println("file is empty");
        } catch (FileNotFoundException f) {	//files do not exist. probably due to first launch. also fine!
        	try {
        		FileOutputStream playersOut = new FileOutputStream("data/players.ser");
                playersOut.close();
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
        
        //Testing that players/reminders have been serialized properly (comment out later)
        for (Player player: players) {
        	System.out.println(player);
        	for (Reminder r: player.getReminders()) {
        		System.out.println(r);
        	}
        }
    	startChecker();	//this will only run if file exists and was not empty
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
        if (timerActive == false) {
        	startChecker();
        }
    }
    
    public static void activateReminder(Reminder r) {
    	JOptionPane o = new JOptionPane(r.getText());
    	JDialog d = o.createDialog(null, "LeagueReminder");
    	d.setAlwaysOnTop(true);
    	d.setVisible(true);
    }
    
    public static void startChecker() {
    	timer.schedule(new CheckerTask(), 0, 5000);
    	timerActive = true;
    }
    
//    public static void stopChecker() {
//    	timer.cancel();
//    }
}
