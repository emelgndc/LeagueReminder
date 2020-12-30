package com.leagueReminder.maven.classes;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


public class Window {//change this into a subclass of Window? so the other window (editing/removing reminders) can inherit 
	private String summonerName;
	private String reminderText;
	private int numGames;
	private JFrame frame;	
	private JTextField stext;
	private JTextField rtext;
	private JTextField ntext;	
	
	public Window() {
		//Creating the frame
		frame = new JFrame("League Reminder App");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(450,200);
		frame.setResizable(false);
		

	    //Menu bar
	    JMenuBar mb = new JMenuBar();
	    JMenu file = new JMenu("File");
	    JMenuItem rem = new JMenuItem("Active Reminders");
	    JMenuItem exit = new JMenuItem("Exit");
	    file.add(rem);
	    file.add(exit);
	    mb.add(file);
	    
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
		    		System.out.println(numGames);
		    		System.out.println(summonerName);
		    		System.out.println(reminderText);		// execute setreminder
		    		App.setReminder(summonerName, reminderText, numGames);
	    		} catch (NumberFormatException x) {
	    			//System.out.println("needs to be a number"); 
	    			// popup window notifying user of invalid input
	    			JOptionPane.showMessageDialog(frame,
	    				    "needs to be a number",
	    				    "Invalid input",
	    				    JOptionPane.WARNING_MESSAGE);
	    		}
	    		//System.out.println(summonerName, numGames, reminderText);
	    	}
	    });
	    
	    
	    //Adding components to center panel
	    panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
	    panel.add(summonerNamePanel);
	    panel.add(reminderPanel);
	    panel.add(numGamesPanel);
	    
	    //Adding components to frame
	    frame.getContentPane().add(BorderLayout.PAGE_START, mb);
	    frame.getContentPane().add(BorderLayout.CENTER, panel);
	    frame.getContentPane().add(BorderLayout.PAGE_END, button);
	    frame.setVisible(true);

	}

	
}
