package com.leagueReminder.maven.classes;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class Window {
	private JFrame frame;
	private AddReminderWindow add;
	private JList list;
	
	public Window() {
		frame = new JFrame("League Reminder App");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(450,130);
		frame.setResizable(false);
		
		list = new JList(App.reminders);
		JScrollPane listScroller = new JScrollPane(list);
		listScroller.setPreferredSize(new Dimension(250, 80));
		
		JPanel buttons = new JPanel();
		JButton addButton = new JButton("Add");
		JButton removeButton = new JButton("Remove");
	    addButton.addActionListener(new ActionListener() {
	    	@Override
	    	public void actionPerformed(ActionEvent e) {
	    		JDialog dialog = new JDialog(frame, "Add Reminder", true);
	    		add = new AddReminderWindow(dialog);
	    	}
	    });
	    
	    
	    addButton.setAlignmentX(Component.CENTER_ALIGNMENT);
	    removeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
	    buttons.add(addButton);
	    buttons.add(removeButton);
	    //buttons.setLayout(new BoxLayout(buttons, BoxLayout.PAGE_AXIS));
	    buttons.setLayout(new GridLayout(2,0));
	    
	    
	    frame.setLayout(new FlowLayout());
		frame.getContentPane().add(listScroller);
		frame.getContentPane().add(buttons); // instead of just 1 button, create horizontal panel which has add/remove/exit buttons. remove should be greyed out unless item selected
		frame.setVisible(true);
	}
	
//	public void refresh(DefaultListModel<Reminder> rs) {
//		reminders = rs;
//	}
}
