package com.leagueReminder.maven.classes;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class App 
{
	static ArrayList<Player> players = new ArrayList<Player>();
	static DefaultListModel<Reminder> reminders = new DefaultListModel<Reminder>();
	static Timer timer = new Timer();
	private static boolean timerActive = false;
	private static Window window;
	
    @SuppressWarnings("unchecked")
	public static void main( String[] args )
    {
        window = new Window();

        //Deserialize players and reminders list
        try {
        	FileInputStream playersIn = new FileInputStream("data/players.ser");
        	ObjectInputStream objin = new ObjectInputStream(playersIn);
        	players = (ArrayList<Player>)objin.readObject();
        	reminderSetup();
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
        System.out.println(App.players);
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
    	Reminder r = new Reminder(summonerName, reminderText, numGames);
    	
    	for (int i=0; i<players.size(); i++) {	//check if player is in players list already
    		Player p = players.get(i);
    		if (p.getName().equals(summonerName)) {
    			uniqueP = false;
    			existing = p;
    			break;
    		}
    	}
    	
    	if (uniqueP) {	//if player is unique (i.e. not already in list), create a new Player
    		Player player = new Player(summonerName, numGames);
    		players.add(player);
    		player.addReminder(r);
    		System.out.println("WAS UNIQUE");
    	} else {
    		existing.addReminder(r);
    		System.out.println("WAS NOT UNIQUE");
    	}
    	
    	reminders.addElement(r);

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
    
    public static void deleteReminder(Reminder r) {
    	reminders.removeElement(r);
    	r.getPlayer().removeReminder(r);
    	System.out.println("reminder " + r + " removed.");
    }
    
    public static boolean cleanReminders(Player p, ArrayList<Reminder> rs) {
    	for (Reminder r: rs) {
    		deleteReminder(r);
    	}
    	
    	if (p.size() == 0) {
    		return true;
    	}
    	return false;
    	
    }
    
    public static void cleanPlayers(ArrayList<Player> ps) {
    	for (Player p: ps) {
    		players.remove(p);
    		System.out.println("player " + p.getName() + " removed.");
    	}
    }
    
    public static void startChecker() {
    	timer.schedule(new CheckerTask(), 0, 10000);
    	timerActive = true;
    }
    
//    public static void stopChecker() {
//    	timer.cancel();
//    }
    
    private static void reminderSetup() {
    	for (Player p: players) {
    		for (Reminder r: p.getReminders()) {
    			reminders.addElement(r);
    		}
    	}
    }
}
