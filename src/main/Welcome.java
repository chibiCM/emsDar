package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import javax.swing.ImageIcon;
import javax.swing.JProgressBar;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.UIManager;

import main.TheConnection;
import javax.swing.border.LineBorder;

public class Welcome extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	public static JProgressBar progressBar;
	public static int loadingCounter = 0;
	static JLabel lblLoadingText;
	
	//for db
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) 
	{
		try 
		{
			
			Welcome dialog = new Welcome();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			
			while(loadingCounter <= 100)
			{
						//progress bar
				Thread.sleep(10);
				progressBar.setValue(loadingCounter);
						
				if(loadingCounter <= 20) lblLoadingText.setText("Loading your future. Stay patient!");
				if(loadingCounter>20 && loadingCounter <= 40) lblLoadingText.setText("Patience is the key to progress.");
				if(loadingCounter>40 && loadingCounter <= 60) lblLoadingText.setText("Great things take time. Keep going!");
			    if(loadingCounter>60 && loadingCounter <= 80) lblLoadingText.setText("You’ve got what it takes to succeed!");
				if(loadingCounter>80 && loadingCounter <= 99) lblLoadingText.setText("Believe it!");
				//lblLoadingText.setText("Let's do it!");
				loadingCounter++;
			}
			
			dialog.setVisible(false);
			
			TheConnection dbcon = new TheConnection();
			dbcon.connect_db();
			
			if(dbcon.conn != null) {
				new Login().setVisible(true);
			}else {
				/*
				ErrorMsg errorMsg = new ErrorMsg();
				errorMsg.setVisible(true);
				ErrorMsg.lblError.setText("<html><center>The system could not connect. Please contact admin.</center></html>");
				*/
				CantConnectDB errorClose = new CantConnectDB();
				errorClose.setVisible(true);
				errorClose.lblCantError.setText("<html><center>The system could not connect. Please contact admin.</center></html>\"");
			}
		}catch(Exception ex) 
		{
			ex.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Welcome() {
		setUndecorated(true);
		setBounds(100, 100, 450, 320);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		setLocationRelativeTo(null);
		
		progressBar = new JProgressBar();
		progressBar.setForeground(UIManager.getColor("Button.foreground"));
		progressBar.setStringPainted(true);
        progressBar.setBounds(0, 300, 450, 20);
        contentPanel.add(progressBar);
        
        lblLoadingText = new JLabel("");
        lblLoadingText.setHorizontalAlignment(SwingConstants.CENTER);
        lblLoadingText.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblLoadingText.setBounds(0, 269, 450, 20);
        lblLoadingText.setForeground(new Color(255, 255, 255));
        contentPanel.add(lblLoadingText);
		
		JLabel lblBackGImg = new JLabel();
		lblBackGImg.setHorizontalAlignment(SwingConstants.CENTER);
		lblBackGImg.setBounds(0, 0, 450, 320);
		contentPanel.add(lblBackGImg);
		ImageIcon BackGImg = new ImageIcon(this.getClass().getResource("/studying1.gif")); //add image/bg
        lblBackGImg.setIcon(BackGImg); //call the set added/set
        
	}
	
	public void connection_db() {
		
	}
	
	
}
