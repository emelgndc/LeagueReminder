package com.leagueReminder.maven.classes;
import javax.swing.*;

public class Window {
	
	public Window() {
		JFrame frame = new JFrame("League Reminder App");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800,400);
	    //JButton button = new JButton("Press");
	    //frame.getContentPane().add(button); // Adds Button to content pane of frame
	    frame.setVisible(true);
	}

	
}
