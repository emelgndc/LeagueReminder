package com.leagueReminder.maven.classes;

import java.io.*;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.merakianalytics.orianna.Orianna;
import com.merakianalytics.orianna.types.core.summoner.Summoner;

public class App 
{
	static ArrayList<Player> players = new ArrayList<Player>();
	private static ArrayList<Reminder> reminders = new ArrayList<Reminder>();
	
    @SuppressWarnings("unchecked")
	public static void main( String[] args )
    {
        Orianna.loadConfiguration("config.json");
        Orianna.setRiotAPIKey("API KEY HERE");
        
        Window window = new Window();

        //Deserialize players and reminders list
        try {
        	FileInputStream playersIn = new FileInputStream("data/players.ser");
        	FileInputStream remindersIn = new FileInputStream("data/reminders.ser");
        	ObjectInputStream in1 = new ObjectInputStream(playersIn);
        	ObjectInputStream in2 = new ObjectInputStream(remindersIn);
        	
        	players = (ArrayList<Player>)in1.readObject();
        	reminders = (ArrayList<Reminder>)in2.readObject();
        	
        	in1.close();
        	in2.close();
        	playersIn.close();
        	remindersIn.close();
        } catch (EOFException e)  {			//file is empty - nothing stored, this is fine!
        	return;
        } catch (FileNotFoundException f) {	//files do not exist. probably due to first launch. also fine!
        	try {
        		FileOutputStream playersOut = new FileOutputStream("data/players.ser");
                FileOutputStream remindersOut = new FileOutputStream("data/reminders.ser");
                playersOut.close();
                remindersOut.close();
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
        }
        
        for (Reminder reminder: reminders) {
        	System.out.println(reminder);
        }
    }
    
    public static void setReminder(String summonerName, String reminderText, int numGames) {
    	boolean uniqueP = true;
    	Player existing = null;
    	for (Player p: players) {				//check if player is in players list already
    		if (p.getName() == summonerName) {
    			uniqueP = false;
    			existing = p;
    			break;
    		}
    	}
    	
    	if (uniqueP) {
    		Player player = new Player(summonerName, numGames);
    		players.add(player);
    	} else {
    		existing.setGamesLeft(Math.max(numGames, existing.getGamesLeft()));	//if player already exists, set gamesLeft to highest value
    	}
    	
    	Reminder reminder = new Reminder(summonerName, reminderText, numGames);
    	reminders.add(reminder);
    	
        try {
            FileOutputStream playersOut = new FileOutputStream("data/players.ser");
            FileOutputStream remindersOut = new FileOutputStream("data/reminders.ser");
            ObjectOutputStream out1 = new ObjectOutputStream(playersOut);
            ObjectOutputStream out2 = new ObjectOutputStream(remindersOut);
            out1.writeObject(players);
            out2.writeObject(reminders);
            out1.close();
            out2.close();
            playersOut.close();
            remindersOut.close();
         } catch (IOException i) {
            i.printStackTrace();
         }
    }
    
    public static void activateReminder(Reminder r) {
    	JOptionPane o = new JOptionPane(r.getText());
    	JDialog d = o.createDialog(null, "LeagueReminder");
    	d.setAlwaysOnTop(true);
    	d.setVisible(true);
    }
    
    public void gameStatusChecker() {
    	//Timer t = new Timer
    }
}
