package main;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JInternalFrame;
import javax.swing.JTabbedPane;
import javax.swing.JDesktopPane;
import javax.swing.JToolBar;
import java.awt.Color;

public class Main extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
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
	public Main() {
		setResizable(false);
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1085, 670);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(48, 104, 120));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		
		
		
		//this is the time and minimize&close button
		Bar myBar = new Bar();
		myBar.setBackground(new Color(48, 104, 120));
		getContentPane().add(myBar);
		setVisible(true);
		myBar.setBounds(10, 10, 1068, 40);
		myBar.fbtnMinimize(970, this);
		myBar.fbtnClose(1017);
		
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 60, 280, 600);
		contentPane.add(panel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(300, 60, 773, 600);
		contentPane.add(panel_1);
		setUndecorated(true);
		
		
	}
}
