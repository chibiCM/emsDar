package main;

import main.Bar;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import main.TheConnection;
import java.awt.EventQueue;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.SystemColor;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class Login extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final JFrame frameLogin = new JFrame();
	private JPanel contentPaneLogin;
	JLabel lblLogo;
	private JTextField textUser;
	private JPasswordField passwordField;
	JLabel lblPassword;
	JButton btnLogin;
	//static String jPassword;
	
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		//SwingUtilities.invokeLater(() -> new Login());
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frameLogin = new Login();
					frameLogin.setVisible(true);
					
					new Welcome().setVisible(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		
	}

	/**
	 * Create the frame.
	 * @return 
	 */
	
	
	
	public Login() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		contentPaneLogin = new JPanel();
		contentPaneLogin.setBackground(new Color(48,104,120));
		contentPaneLogin.setBorder(new LineBorder(new Color(48,104,120)));
		setLocationRelativeTo(null);
		setContentPane(contentPaneLogin);
		contentPaneLogin.setLayout(null);
		
		
		JPanel panelLoginWhi = new JPanel();
		panelLoginWhi.setBackground(Color.WHITE);
		panelLoginWhi.setBounds(10, 40, 580, 350);
		contentPaneLogin.add(panelLoginWhi);
		panelLoginWhi.setLayout(null);
		setUndecorated(true);
		

		//this is the time and minimize&close button
		Bar myBar = new Bar();
		myBar.setBackground(new Color(48, 104, 120));
		getContentPane().add(myBar);
		setVisible(true);
		myBar.setBounds(10, 0, 580, 40);
		
		JFrame frameLogin = this; //para sabihin na ito yung iminimize
		//this is the minimize&close button, was placed inside function
				//to have a dynamic x-axis and makapili ng imiminimize
		myBar.fbtnMinimize(487, frameLogin);
		myBar.fbtnClose(535);
		
		
		JLabel lblWelcomeLogoHolder = new JLabel("");
		lblWelcomeLogoHolder.setHorizontalAlignment(SwingConstants.CENTER);
		lblWelcomeLogoHolder.setFont(new Font("Tahoma", Font.BOLD, 19));
		lblWelcomeLogoHolder.setBounds(10, 47, 250, 250);
		panelLoginWhi.add(lblWelcomeLogoHolder);
		ImageIcon iconLogo = new ImageIcon(this.getClass().getResource("/jn3b6.png"));
		lblWelcomeLogoHolder.setIcon(iconLogo);
		
		JLabel lblLogin = new JLabel("");
		lblLogin.setForeground(new Color(48, 104, 120));
		lblLogin.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblLogin.setHorizontalAlignment(SwingConstants.CENTER);
		lblLogin.setBounds(283, 36, 264, 57);
		panelLoginWhi.add(lblLogin);
		ImageIcon personImg = new ImageIcon(this.getClass().getResource("/personImg.png"));
		lblLogin.setIcon(personImg);
		
		
		JLabel lblNewLabel = new JLabel("Username");
		lblNewLabel.setBounds(285, 104, 61, 14);
		panelLoginWhi.add(lblNewLabel);	
		revalidate();
		repaint();

		textUser = new JTextField();
		textUser.setBounds(283, 129, 276, 35);
		panelLoginWhi.add(textUser);
		textUser.setColumns(10);
		revalidate();
		repaint();
		
		lblPassword = new JLabel("Password");
		lblPassword.setBounds(283, 175, 75, 14);
		panelLoginWhi.add(lblPassword);
		revalidate();
		repaint();
		
		passwordField = new JPasswordField();
		passwordField.setBounds(283, 198, 276, 35);
		panelLoginWhi.add(passwordField);
		revalidate();
		repaint();

		
		btnLogin = new JButton("Login");
		btnLogin.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//login process
				processLogin();
			}
		});
		revalidate();
		repaint();
		btnLogin.setBackground(new Color(239, 198, 67));
		btnLogin.setForeground(new Color(0,0,0));
		btnLogin.setBounds(349, 256, 145, 35);
		panelLoginWhi.add(btnLogin);
		

		//to make enter button login as well 
		InputMap inputMap = contentPaneLogin.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = contentPaneLogin.getActionMap();

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "login");
        actionMap.put("login", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	btnLogin.doClick();
            }
        });
		

	}
	
	public void processLogin() {
		
		String jUsername = textUser.getText();

		char[] jPass = passwordField.getPassword();
		String jPassword = String.copyValueOf(jPass);
		
		if (!jUsername.isEmpty()&&!jPassword.isEmpty()) {
			try {
				TheConnection dbcon = new TheConnection();
				dbcon.connect_db();

				String loginQuery = "SELECT * FROM dbjnc6.tbluser WHERE Username=? AND Password=?";

				PreparedStatement pt = dbcon.conn.prepareStatement(loginQuery);
				pt.setString(1, jUsername.trim());
				pt.setString(2, jPassword.trim());

				ResultSet rs = pt.executeQuery();

				if (rs.next()) {
					dispose();
					//Main frame = new Main();
					//frame.setVisible(true);
					
					DashBoard dashboardwindow = new DashBoard();
					dashboardwindow.frameDashboard.setVisible(true);
					
					DListEnrolled showAdminListStud = new DListEnrolled();
					dashboardwindow.myPanelOutput.removeAll(); 
					dashboardwindow.myPanelOutput.add(showAdminListStud); 
					dashboardwindow.myPanelOutput.revalidate(); 
					dashboardwindow.myPanelOutput.repaint();
				} else {
					ErrorMsg dialog = new ErrorMsg();
					dialog.setVisible(true);
					ErrorMsg.lblError.setText("<html><center>Incorrect Username or Password</center></html>");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} 
		}else {
			ErrorMsg dialog = new ErrorMsg();
			dialog.setVisible(true);
			ErrorMsg.lblError.setText("<html><center>Please ensure all details are filled</center></html>");
		}

	}
}
