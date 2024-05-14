package main;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Font;
import java.awt.Color;
import javax.swing.border.LineBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ErrorMsg extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	static JLabel lblError;
	static JButton btnOkayClose;
	public static ErrorMsg errorMsg;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			errorMsg = new ErrorMsg();
			errorMsg.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			
			errorMsg.setVisible(true);

			
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public static void funcErrorMsg(String text) {
		lblError.setText(text);
	}
	
	public ErrorMsg() {
		setResizable(false);
		setUndecorated(true);
		setBounds(100, 100, 311, 244);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.WHITE);
		contentPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		setLocationRelativeTo(null);
		contentPanel.setLayout(null);
		
		
		lblError = new JLabel("");
		lblError.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblError.setBounds(29, 94, 255, 77);
		lblError.setHorizontalAlignment(SwingConstants.CENTER);
		contentPanel.add(lblError);
		
		JLabel lblErrorIcon = new JLabel("");
		lblErrorIcon.setHorizontalAlignment(SwingConstants.CENTER);
		lblErrorIcon.setBounds(120, 22, 68, 68);
		contentPanel.add(lblErrorIcon);
		ImageIcon errorIcon = new ImageIcon(this.getClass().getResource("/warningIcon.png"));
		lblErrorIcon.setIcon(errorIcon);
		
		btnOkayClose = new JButton("OK");
		btnOkayClose.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnOkayClose.setBackground(new Color(239, 198, 67));
		btnOkayClose.setBounds(101, 182, 108, 34);
		btnOkayClose.addActionListener(e->this.dispose()); //
		//btnClose.addActionListener(e -> System.exit(0));

		contentPanel.add(btnOkayClose);
	}
	
	
}
