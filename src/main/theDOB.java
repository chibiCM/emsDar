package main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


//import com.toedter.calendar.JDateChooser;
//import com.toedter.calendar.JDateChooser;


import javax.swing.*;
import java.awt.*;
import java.util.Calendar;
import java.util.Date;
import com.toedter.calendar.JDateChooser;

public class theDOB extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					theDOB frame = new theDOB();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public theDOB() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JDateChooser dateChooser = new JDateChooser();
		getContentPane().add(dateChooser, BorderLayout.CENTER);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		
		//com.toedter.calendar.JDateChooser dateChooser = new com.toedter.calendar.JDateChooser();.

		//Date date =  dateChooser.getDate();
		//DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		//someJLabel.setText(dateFormat.format(date));
		
	}
}
