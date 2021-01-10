package com.leagueReminder.maven.classes;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Window {
	private JFrame frame;
	private AddReminderWindow add;
	private JList list;
	
	public Window() {
		//Frame setup
		frame = new JFrame("League Reminder App");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(450,200);
		frame.setResizable(false);
		
		//Button setup
		JPanel buttons = new JPanel();
		JButton addButton = new JButton("Add");
		JButton removeButton = new JButton("Remove");
		
	    //List setup
		list = new JList(App.reminders);
		list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		list.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (e.getValueIsAdjusting() == false) {
					if (list.getSelectedIndex() != -1) {
						removeButton.setEnabled(true);
					} else {
						removeButton.setEnabled(false);
					}
				}
			}
		});
		JScrollPane listScroller = new JScrollPane(list);
		listScroller.setPreferredSize(new Dimension(250, 150));
		
		//Button setup v2
	    addButton.addActionListener(new ActionListener() {
	    	@Override
	    	public void actionPerformed(ActionEvent e) {
	    		JDialog dialog = new JDialog(frame, "Add Reminder", true);
	    		add = new AddReminderWindow(dialog);
	    	}
	    });
	    removeButton.addActionListener(new ActionListener() {
	    	@Override
	    	public void actionPerformed(ActionEvent e) {
	    		List<Reminder> toRemove = list.getSelectedValuesList();
	    		for (Reminder r: toRemove) {
	    			App.deleteReminder(r);
	    		}
	    	}
	    });
	    addButton.setAlignmentX(Component.CENTER_ALIGNMENT);
	    removeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
	    removeButton.setEnabled(false);
	    buttons.add(addButton);
	    buttons.add(removeButton);
	    buttons.setLayout(new GridLayout(2,0));
	    
	    //Finalisation
	    frame.setLayout(new FlowLayout());
		frame.getContentPane().add(listScroller);
		frame.getContentPane().add(buttons); // instead of just 1 button, create horizontal panel which has add/remove/exit buttons. remove should be greyed out unless item selected
		frame.setVisible(true);
	}
	
//	public void refresh(DefaultListModel<Reminder> rs) {
//		reminders = rs;
//	}
}
