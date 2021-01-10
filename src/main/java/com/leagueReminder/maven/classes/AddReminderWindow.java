package com.leagueReminder.maven.classes;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;


public class AddReminderWindow extends JComponent {//change this into a subclass of Window? so the other window (editing/removing reminders) can inherit 
	private String summonerName;
	private String reminderText;
	private int numGames;
	private JTextField stext;
	private JTextField rtext;
	private JTextField ntext;	
	
	public AddReminderWindow(JDialog d) {
		//Creating the frame
		d.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		d.setSize(450,180);
		d.setResizable(false);
	    
		//TextFields
	    stext = new JTextField(15);
		rtext = new JTextField(25);
		ntext = new JTextField(2);
		
		//Labels
		JLabel slabel = new JLabel("Summoner name:");
		JLabel rlabel = new JLabel("Reminder text:");
		JLabel nlabel1 = new JLabel("Repeat for");
		JLabel nlabel2 = new JLabel("games (0 for unlimited)");
		
	    //Panels
	    JPanel panel = new JPanel();
	    JPanel summonerNamePanel = new JPanel();
	    JPanel reminderPanel = new JPanel();
	    JPanel numGamesPanel = new JPanel();
	    
	    summonerNamePanel.add(slabel);
	    summonerNamePanel.add(stext);
	    reminderPanel.add(rlabel);
	    reminderPanel.add(rtext);
	    numGamesPanel.add(nlabel1);
	    numGamesPanel.add(ntext);
	    numGamesPanel.add(nlabel2);
	    
		//Button
	    JButton button = new JButton("Set");
	    button.addActionListener(new ActionListener() {
	    	@Override
	    	public void actionPerformed(ActionEvent e) { //when 'set' is pressed
	    		try {
	    			numGames = Integer.parseInt(ntext.getText());
		    		summonerName = stext.getText();
		    		reminderText = rtext.getText();
		    		System.out.println(summonerName);
		    		System.out.println(reminderText);
		    		System.out.println(numGames);
		    		App.setReminder(summonerName, reminderText, numGames);
		    		d.dispose();
	    		} catch (NumberFormatException x) {
	    			// popup window notifying user of invalid input
	    			JOptionPane.showMessageDialog(d,
	    				    "needs to be a number",
	    				    "Invalid input",
	    				    JOptionPane.WARNING_MESSAGE);
	    		}
	    	}
	    });
	    
	    
	    //Adding components to center panel
	    panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
	    panel.add(summonerNamePanel);
	    panel.add(reminderPanel);
	    panel.add(numGamesPanel);
	    
	    //Adding components to frame
	    d.getContentPane().add(BorderLayout.CENTER, panel);
	    d.getContentPane().add(BorderLayout.PAGE_END, button);
	    d.setVisible(true);
	}

	
}
