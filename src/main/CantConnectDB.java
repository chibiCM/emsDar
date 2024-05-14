package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.JLabel;

public class CantConnectDB extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	JLabel lblCantError;
	JLabel lblCantErrorIcon;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			CantConnectDB dialog = new CantConnectDB();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public CantConnectDB() {
		setResizable(false);
		setUndecorated(true);
		setBounds(100, 100, 311, 244);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		contentPanel.setBackground(Color.WHITE);
		setLocationRelativeTo(null);
		
		
		
		lblCantError = new JLabel("");
		lblCantError.setBounds(29, 94, 255, 77);
		lblCantError.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCantError.setHorizontalAlignment(SwingConstants.CENTER);
		contentPanel.add(lblCantError);
		

		
		lblCantErrorIcon = new JLabel("");
		lblCantErrorIcon.setBounds(120, 22, 68, 68);
		lblCantErrorIcon.setHorizontalAlignment(SwingConstants.CENTER);
		ImageIcon errorIcon = new ImageIcon(this.getClass().getResource("/warningIcon.png"));
		lblCantErrorIcon.setIcon(errorIcon);
		contentPanel.add(lblCantErrorIcon);
		
		
		JButton btnCantOkayClose = new JButton("CLOSE");
		btnCantOkayClose.setBounds(101, 182, 108, 34);
		btnCantOkayClose.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnCantOkayClose.setBackground(new Color(239, 198, 67));
		btnCantOkayClose.addActionListener(e -> System.exit(0));
		contentPanel.add(btnCantOkayClose);

	}
}
