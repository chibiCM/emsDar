package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import java.awt.Color;

public class Bar extends JPanel {

	private static final long serialVersionUID = 1L;
	protected static final String ICONIFIED = "";
	JFrame myJframe;
	//JPanel panelbtnHolder;
	/**
	 * Create the panel.
	 */
	public Bar() {
		setBackground(new Color(48, 104, 120));
		setLayout(null);
		
		//DateTimeFormatter theDay = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		//LocalTime whatTime = LocalTime.now(null);
		
		ZoneId phZoneID = ZoneId.of("Asia/Manila");
		
		
		LocalTime whatTime = LocalTime.now(phZoneID);
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
		String timeFormatted = whatTime.format(timeFormatter);
		JLabel lblTime = new JLabel(timeFormatted);
		lblTime.setForeground(new Color(255, 255, 255));
		lblTime.setBounds(113, 11, 131, 18);
		add(lblTime);
		
		
		LocalDate whatDay = LocalDate.now(phZoneID);
		JLabel lblDate = new JLabel(whatDay.toString());
		lblDate.setForeground(Color.WHITE);
		lblDate.setBounds(10, 11, 84, 18);
		add(lblDate);
		
	}
	
	public void funcDay() {
		
		
		
	}
	
	//if frame
	public void fbtnMinimize(int xAxisEnd, JFrame myJframe) {
	JButton btnMinimize = new JButton("");
    btnMinimize.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
        	myJframe.setExtendedState(JFrame.ICONIFIED);
        }
    });
        btnMinimize.setBounds(xAxisEnd, 0, 46, 40);
        ImageIcon minimize = new ImageIcon(this.getClass().getResource("/min-icon.png"));
        btnMinimize.setIcon(minimize);
        add(btnMinimize);
    }
	
	
	public void fbtnClose(int xAxisEnd) {
        JButton btnClose = new JButton("");
        btnClose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        btnClose.setBounds(xAxisEnd, 0, 46, 40);
        ImageIcon closeX = new ImageIcon(this.getClass().getResource("/closemin.png"));
        btnClose.setIcon(closeX);
        add(btnClose);
    }
	
	//if dashord
	public void fbtnMinimize(int xAxisEnd, DashBoard myDashBoard) {
		JButton btnMinimize = new JButton("");
	    btnMinimize.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	        	myJframe.setExtendedState(JFrame.ICONIFIED);
	        }
	    });
	        btnMinimize.setBounds(xAxisEnd, 0, 46, 40);
	        ImageIcon minimize = new ImageIcon(this.getClass().getResource("/min-icon.png"));
	        btnMinimize.setIcon(minimize);
	        add(btnMinimize);
	    }
		
		


}
