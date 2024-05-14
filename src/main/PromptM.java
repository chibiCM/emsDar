package main;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JDialog;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class PromptM extends JDialog {

	private static final long serialVersionUID = 1L;
	static PromptM dialog;
	JLabel lblPMIcon;
	JLabel lblPMMsg;
	JButton btnPMOkay;
	static String pmIconURL;
	static String pmMessage;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					dialog = new PromptM(pmIconURL, pmMessage);
					dialog = new PromptM("/acountimg.png", "bogkai");
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);

					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the dialog.
	 */
	public PromptM(String pmIconURL, String pmMessage) {
		setResizable(false);
		getContentPane().setBackground(new Color(255, 255, 255));
		setUndecorated(true);
		setBounds(100, 100, 311, 244);
		getContentPane().setLayout(null);
		setLocationRelativeTo(null);
		
		
		
		lblPMIcon = new JLabel("");
		lblPMIcon.setHorizontalAlignment(SwingConstants.CENTER);
		lblPMIcon.setBounds(120, 22, 68, 68);
		getContentPane().add(lblPMIcon);
		ImageIcon pmIcon = new ImageIcon(this.getClass().getResource(pmIconURL));
		lblPMIcon.setIcon(pmIcon);
		
		
		lblPMMsg = new JLabel(pmMessage);
		lblPMMsg.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPMMsg.setBounds(29, 94, 255, 77);
		lblPMMsg.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(lblPMMsg);
		
		
		btnPMOkay = new JButton("OK");
		btnPMOkay.setBounds(101, 182, 108, 34);
		btnPMOkay.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnPMOkay.setBackground(new Color(239, 198, 67));
		btnPMOkay.addActionListener(e->this.dispose());
		getContentPane().add(btnPMOkay);
		
		

	}
}
