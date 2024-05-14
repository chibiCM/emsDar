package main;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.Window;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JButton;
import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;

public class DashBoard {

	public JFrame frameDashboard;
	int frameDashboardWidth;
	AbstractButton lblofIcon;
	JPanel myPanelOutput;
	static String pmIconURL;
	static String pmMessage;
	
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DashBoard dashboardwindow = new DashBoard();
					dashboardwindow.frameDashboard.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		SwingUtilities.invokeLater(() -> new DashBoard());
	}

	/**
	 * Create the application.
	 */
	public DashBoard() {
		
		initialize();
		
		centreWindow(frameDashboard);
		frameDashboard.getContentPane().setLayout(null);
		
		
		
		Bar myBar = new Bar();
		myBar.setBounds(10, 0, 965, 40);
		myBar.setBackground(new Color(48, 104, 120));
		frameDashboard.getContentPane().add(myBar);
		
		
		int ninetypercent = (int) (frameDashboardWidth * 0.9);
		myBar.fbtnMinimize(ninetypercent-20, frameDashboard);//
		myBar.fbtnClose(ninetypercent+30);
		myBar.setVisible(true);
		
		JPanel panelNav = new JPanel();
		panelNav.setBackground(Color.WHITE);
		panelNav.setBounds(15, 51, 260, 600);
		frameDashboard.getContentPane().add(panelNav);
		panelNav.setLayout(null);
		
		JLabel lblDashboard = new JLabel("  Dashboard");
		lblDashboard.setBackground(new Color(239, 198, 67));
		lblDashboard.addMouseListener(new MouseAdapter()  
		{  
		    public void mouseClicked(MouseEvent e)  
		    {  
		    	//lblDashboard.setText("testhaha");
		    	
		    	DListEnrolled showAdminListStud = new DListEnrolled();
		        myPanelOutput.removeAll(); 
		        myPanelOutput.add(showAdminListStud); 
		        myPanelOutput.revalidate(); 
		        myPanelOutput.repaint();
		    };
		}); 
		lblDashboard.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblDashboard.setBounds(53, 190, 150, 40);
		panelNav.add(lblDashboard);
		iconsDash("/dashboardIcon.png", lblDashboard);
		
		
		JLabel lblCourse = new JLabel("  Course");
		lblCourse.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				DListCourse showDlistCourse = new DListCourse();
				myPanelOutput.removeAll();
				myPanelOutput.add(showDlistCourse); 
		        myPanelOutput.revalidate(); 
		        myPanelOutput.repaint();
		        //deletetempenrolled();
			}
		});
		lblCourse.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblCourse.setBounds(53, 245, 150, 40);
		panelNav.add(lblCourse);
		iconsDash("/courseIcon.png", lblCourse);
		
		
		JLabel lblTeacher = new JLabel("  Teacher");
		lblTeacher.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				DListTeacher showDListTeacher = new DListTeacher();
				myPanelOutput.removeAll();
				myPanelOutput.add(showDListTeacher); 
		        myPanelOutput.revalidate(); 
		        myPanelOutput.repaint();
		       // deletetempenrolled();
			}
		});
		lblTeacher.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblTeacher.setBounds(53, 300, 150, 40);
		panelNav.add(lblTeacher);
		iconsDash("/teacherIcon.png", lblTeacher);
		
		
		JLabel lblStudent = new JLabel("  Student");
		lblStudent.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				DListStud showDListStud = new DListStud();
				myPanelOutput.removeAll();
				myPanelOutput.add(showDListStud); 
		        myPanelOutput.revalidate(); 
		        myPanelOutput.repaint();
		       // deletetempenrolled();
			}
		});
		lblStudent.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStudent.setBounds(53, 355, 150, 40);
		panelNav.add(lblStudent);
		iconsDash("/studIcon.png", lblStudent);
		
		
		JLabel lblExit = new JLabel("Quit");
		lblExit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//deletetempenrolled();
				System.exit(0);
			}
		});
		lblExit.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblExit.setBounds(53, 410, 150, 40);
		panelNav.add(lblExit);
		iconsDash("/exitIcon.png", lblExit);
		
		JLabel lblAccount = new JLabel("  Account");
		lblAccount.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblAccount.setBounds(53, 500, 150, 40);
		panelNav.add(lblAccount);
		iconsDash("/acountimg.png", lblAccount);
		
		JLabel lblLogo = new JLabel("");
		lblLogo.setBounds(55, 11, 150, 150);
		panelNav.add(lblLogo);
		iconsDash("/jn3b6150.png", lblLogo);
		
		
		
		myPanelOutput = new JPanel(new BorderLayout());//borderlayout para dito magappear yung ibang panel
		myPanelOutput.setBackground(Color.CYAN);
		myPanelOutput.setBounds(291, 51, 680, 600);
		frameDashboard.getContentPane().add(myPanelOutput);
		
		//DListCourse showDlistCourse = new DListCourse();
		//DListStud showAdminListStud = new DListStud();
       // frameDashboard.getContentPane().add(showDlistCourse);
        //myPanelOutput.revalidate(); 
       // myPanelOutput.repaint();
        
        
		
				
		 
		
	}

	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frameDashboard = new JFrame();
		frameDashboard.getContentPane().setBackground(new Color(48, 104, 120));
		frameDashboard.setBounds(100, 100, 985, 665);
		frameDashboard.setResizable(false);
		frameDashboard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameDashboardWidth = frameDashboard.getWidth();
		frameDashboard.setUndecorated(true);
	}
	
	
	
	//para center dashboard
	public static void centreWindow(Window framewind) {
	    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) ((dimension.getWidth() - framewind.getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - framewind.getHeight()) / 2);
	    framewind.setLocation(x, y);
	}
	
	public void iconsDash(String iconURL, JLabel lblDashboard) {
		ImageIcon dashIcon = new ImageIcon(this.getClass().getResource(iconURL));
		lblDashboard.setIcon(dashIcon);
	}
	
	//function for custom prompt
	public void pm(String pmIconURL, String pmMessage) {
		 PromptM pmdialog = new PromptM(pmIconURL, pmMessage);
		 pmdialog.setVisible(true);
	}
	

	
	
}
