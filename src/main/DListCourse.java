package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.BevelBorder;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JEditorPane;
import javax.swing.event.AncestorListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.AncestorEvent;
import java.awt.event.HierarchyListener;
import java.awt.event.HierarchyEvent;

public class DListCourse extends JPanel {

	private static final long serialVersionUID = 1L;
	
	
	private static JTextField textCourse;
	static JLabel lblNewLabel;
	private JTable table;
	private static JTable tableCourse;
	
	static DefaultTableModel model;
	static JScrollPane scrollPane;
	String row[];
	static JTextField txtYearDuration;
	
	static int selected;
	static int rowCount;
	static JLabel lblNumRec;
	static int cols;
	private static JTextField txtCourseID;
	
	
	static String tableCourseID;
	static int inttableCourseID;
	static String tableCourseYD;
	static String tableCourseN;
	
	static boolean isForUpdate = false;
	static boolean isInsertProcess = true;
	
	static DashBoard dbpm = new DashBoard();
	boolean updateIconIsClicked = false;
	 static JLabel lblYouAreEditing;
	
	/**
	 * Create the panel.
	 */
	public DListCourse() {
		
		setBackground(new Color(255, 255, 255));
        setBounds(291, 51, 680, 600);
        setLayout(null);

        //JLabel 
        lblNewLabel = new JLabel("List of Courses Offered");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblNewLabel.setBounds(10, 37, 660, 14);
        add(lblNewLabel);
        
        JLabel lblListOfEnrolled = new JLabel("Course Name:");
        lblListOfEnrolled.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblListOfEnrolled.setBounds(10, 75, 210, 14);
        add(lblListOfEnrolled);
        
        JLabel lblSearch = new JLabel("Year Duration: ");
        lblSearch.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblSearch.setBounds(10, 100, 120, 20);
        add(lblSearch);
        
        txtYearDuration = new JTextField();
        txtYearDuration.setEnabled(false);
        txtYearDuration.setBounds(115, 100, 228, 22);
        add(txtYearDuration);
        txtYearDuration.setColumns(10);
        
        
        
        textCourse = new JTextField();
        textCourse.setEnabled(false);
        textCourse.setColumns(10);
        textCourse.setBounds(115, 74, 228, 22);
        add(textCourse);
        
        JLabel lblNewLabel_1 = new JLabel("Total Records(s) in database are: ");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblNewLabel_1.setBounds(10, 562, 238, 27);
        add(lblNewLabel_1);
        
        lblNumRec = new JLabel("");
        lblNumRec.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblNumRec.setBounds(231, 562, 112, 27);
        add(lblNumRec);
        
        
        //forda icon label
        DashBoard dashB = new DashBoard();
        
        
        JLabel lblDelIcon = new JLabel("");
        lblDelIcon.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		deleteCourse();
        	}
        });
        lblDelIcon.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblDelIcon.setBounds(630, 66, 40, 40);
        add(lblDelIcon);
        dashB.iconsDash("/deleteIcon.png", lblDelIcon);
        
        JLabel lblUpdateIcon = new JLabel("");
        lblUpdateIcon.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		
        		updateIconIsClicked = true;
				 
				 if(updateIconIsClicked && !textCourse.isEnabled() && !txtYearDuration.isEnabled() && tableCourse.getSelectedRowCount()==1) {
					 dbpm.pm("/infoIcon.png", "<html><center>You are now updating records. Click + icon to enable textboxes</center></html>");
					 
					 lblYouAreEditing.setText("You are now updating records. Click + icon to enable textboxes.");
				 }
		  			 
				 if(textCourse.isEnabled() && updateIconIsClicked && txtCourseID.getText().length()==0 && tableCourse.getSelectedRowCount()==1) {
					 dbpm.pm("/infoIcon.png", "<html><center>You are now updating records.</center></html>");
						 lblYouAreEditing.setText("You are now updating records.");
				 }
				

       		
       		 if(tableCourse.getSelectedRowCount()==1) {
       			
       			tableCourseID = tableCourse.getValueAt(tableCourse.getSelectedRow(), 0).toString();
       			tableCourseN = tableCourse.getValueAt(tableCourse.getSelectedRow(), 1).toString();
       			tableCourseYD = tableCourse.getValueAt(tableCourse.getSelectedRow(), 2).toString();
       			
       			
       			txtCourseID.setText(tableCourseID);
       			textCourse.setText(tableCourseN);
       			txtYearDuration.setText(tableCourseYD);
       			
       			
       			lblYouAreEditing.setText("Selected record with ID:" + tableCourseID);
       	 		
       	 		inttableCourseID = Integer.parseInt(tableCourseID);

       	 		isExisting(); //if existing magiging true is isForUpdate na boolean;
       	 		//need ma-identity if existing/forupdate yung record para malaman yung 
       	 		//procedure ng pagsave ng data if insert into or update set
            		
       		 }else {

       			 if(tableCourse.getSelectedRowCount()==0) {
    					 dbpm.pm("/warningIcon.png", "<html><center>Empty Selection</center></html>");
       			 }else {
    					 dbpm.pm("/warningIcon.png", "<html><center>Select 1 Only</center></html>");
       			 }
       		 }
        	}
        });
        lblUpdateIcon.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblUpdateIcon.setBounds(580, 66, 40, 40);
        add(lblUpdateIcon);
        dashB.iconsDash("/updateIcon.png", lblUpdateIcon);

        
        
        
   
        
        JLabel lblNewIcon = new JLabel("");
        lblNewIcon.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {

        		
        		if(!txtCourseID.getText().isEmpty() && textCourse.isEnabled() && txtYearDuration.isEnabled() && !isForUpdate){
        			
        			
					dbpm.pm("/infoIcon.png", "<html><center>You are now adding new record</center></html>");
					
					txtCourseID.setText("");
					textCourse.setText("");
					txtYearDuration.setText("");
        			
        			
        			lblYouAreEditing.setText("You are adding record. Please fill up the details.");
        			
        			isForUpdate = false;
        		}else if(txtCourseID.getText().isEmpty() && !isForUpdate){
        			
        			
					dbpm.pm("/infoIcon.png", "<html><center>You are now adding new record</center></html>");
					textCourse.setEnabled(true);
					txtYearDuration.setEnabled(true);
	        		
					txtCourseID.setText("");
					textCourse.setText("");
					txtYearDuration.setText("");
        			
        			lblYouAreEditing.setText("You are adding record. Please fill up the details.");
        			
        			isForUpdate = false;
        		}else if(!txtCourseID.getText().isEmpty() && isForUpdate){
        			if(isForUpdate) {
        				
    					dbpm.pm("/infoIcon.png", "<html><center>You are now updating records. Click + icon again to add new record</center></html>");
    					isForUpdate = false;
    					lblYouAreEditing.setText("You are now updating records. Click + icon again to add new record.");
        			}
        			textCourse.setEnabled(true);
        			txtYearDuration.setEnabled(true);
	        		
        		}else {
        			/*do nothing, i don't have else statement, gusto ko lang ma-ensure na
        			 * isa lang yung pipilin sa scenario. nagsusunod lumabas kasi yung
        			 * you are adding record and updating record after iclick yung plus sign
        			 * pero okay na to, isa-isa na silang nalabas lang
        			 */
        		}

        		isForUpdate = false;
        		
        	}
        });
       
        lblNewIcon.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblNewIcon.setBounds(480, 66, 40, 40);
        add(lblNewIcon);
        dashB.iconsDash("/newIcon.png", lblNewIcon);
        
        
        JLabel lblSaveIcon = new JLabel("");
        lblSaveIcon.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		
        		
        		
        		if(txtCourseID.getText().isEmpty() && textCourse.getText().isEmpty() || txtYearDuration.getText().isEmpty() || !textCourse.isEnabled()) {
            		
     				 dbpm.pm("/warningIcon.png", "<html><center>Please add or update valid info before saving</center></html>");
	           	}
	       		else {
	       			
	       			isExisting();
	       			
	           		if(isForUpdate && !txtCourseID.getText().isEmpty()) {
		           		if(!txtYearDuration.getText().matches("[0-9]+")) {
		           			dbpm.pm("/warningIcon.png", "<html><center>Please enter valid number for the year duration</center></html>");
		           			
		           		}else {
		           			if(textCourse.getText().isEmpty() && txtYearDuration.getText().isEmpty()) {
		           				dbpm.pm("/warningIcon.png", "<html><center>Please select record to edit</center></html>");
		           			}
		           			else {
		           				updateCourse();
		           			}
		           		}
	           			
	           		}
	           		
	           		if(txtCourseID.getText().isEmpty() && !isForUpdate && textCourse.isEnabled() && txtYearDuration.isEnabled()) {
	           			
	           			if(!txtYearDuration.getText().matches("[0-9]+")) {
	           				
		           			dbpm.pm("/warningIcon.png", "<html><center>Please enter valid number for the year duration</center></html>");
		           			
		           		}else {
		           			addCourse();
		           			isForUpdate = false;
		           			txtCourseID.setText("");
		           			
		           		}
	           		}
	       		}
       		
       		
        		
        		
        		
        	}
        });
        lblSaveIcon.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblSaveIcon.setBounds(530, 66, 40, 40);
        add(lblSaveIcon);
        dashB.iconsDash("/saveIcon.png", lblSaveIcon);
        
        
        
        scrollPane = new JScrollPane();
        scrollPane.addHierarchyListener(new HierarchyListener() {
        	public void hierarchyChanged(HierarchyEvent e) {
        		populateTable();
        	}
        });
        scrollPane.setBounds(10, 188, 660, 363);
        add(scrollPane);

        
        txtCourseID = new JTextField();
        txtCourseID.setVisible(false);
        txtCourseID.setEditable(false);
        txtCourseID.setEnabled(false);
        txtCourseID.setColumns(10);
        txtCourseID.setBounds(115, 42, 86, 22);
        add(txtCourseID);
        
        
        lblYouAreEditing = new JLabel("");
        lblYouAreEditing.setBackground(new Color(48, 104, 120));
        lblYouAreEditing.setForeground(new Color(48, 104, 120));
        lblYouAreEditing.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblYouAreEditing.setBounds(10, 143, 660, 27);
        add(lblYouAreEditing);
        
	}
	
	
	public static void populateTable() {
		try {
			TheConnection dbcon = new TheConnection();
			dbcon.connect_db();
			
			tableCourse = new JTable();
		    tableCourse.setBackground(Color.WHITE);
		    tableCourse.setFont(new Font("Tahoma", Font.PLAIN, 13));

		    scrollPane.setViewportView(tableCourse);

			Statement st = dbcon.conn.createStatement();
			
			String populateCourses = "SELECT * FROM dbjnc6.tblcourse";
				
			ResultSet rs = st.executeQuery(populateCourses);
			
			ResultSetMetaData rsmd = rs.getMetaData();
			
			model = new DefaultTableModel();
			tableCourse.setModel(model);
			
			cols = rsmd.getColumnCount();
			
			String[] colName = new String[cols];
			
			for(int i = 0; i < cols; i++) {
				colName[i] = rsmd.getColumnName(i+1);
				model.setColumnIdentifiers(colName);
			}
			
			tableCourse.getColumnModel().getColumn(0).setHeaderValue("ID");
			tableCourse.getColumnModel().getColumn(1).setHeaderValue("Course Name");
			tableCourse.getColumnModel().getColumn(2).setHeaderValue("Year Duration");
			
			
			model.setRowCount(0); // para di nagpapatong-patong/nag-aadd kada show ng table
			
			String CourseID, CourseName, YearDuration;
			while(rs.next()) {
				CourseID = rs.getString(1);
				CourseName = rs.getString(2);
				YearDuration = rs.getString(3);
				String row[] = {CourseID, CourseName, YearDuration};
				model.addRow(row);
			}
			
			
			
			
			rowCount = tableCourse.getRowCount();
			lblNumRec.setText(String.valueOf(rowCount));
			st.close();
			dbcon.conn.close();

		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	
	public void isExisting() {
		
 		
 		try {
			TheConnection dbcon = new TheConnection();
			dbcon.connect_db();
			
			Statement st = dbcon.conn.createStatement();
			
			tableCourseID = tableCourse.getValueAt(tableCourse.getSelectedRow(), 0).toString();
			txtCourseID.setText(tableCourseID);
	 		inttableCourseID = Integer.parseInt(tableCourseID);
	 		
			
			String selectQuery = "SELECT * FROM dbjnc6.tblcourse WHERE CourseID = '"+ inttableCourseID +"'";
	
			ResultSet rs = st.executeQuery(selectQuery);
			
			if(rs.next()) {
				isForUpdate = true;
			}
			st.close();
			dbcon.conn.close();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	
	private static void updateCourse() {
		
		if(!txtCourseID.getText().isEmpty() && isForUpdate) {
			try {
				TheConnection dbcon = new TheConnection();
				dbcon.connect_db();
				
				
				tableCourseID = txtCourseID.getText().toLowerCase().trim();
				tableCourseN = textCourse.getText().toLowerCase().trim();
				tableCourseYD = txtYearDuration.getText().toLowerCase().trim();
				
				Statement st = dbcon.conn.createStatement();
				String checkExistingCourse = "SELECT CourseName FROM dbjnc6.tblcourse WHERE CourseName = '"+tableCourseN+"'";
				ResultSet rs = st.executeQuery(checkExistingCourse);
				
					if(rs.next()) {
						dbpm.pm("/warningIcon.png", "Course is existing");
					}else {
				
					//Update Course Table
					String updateQuery1 = "UPDATE dbjnc6.tblcourse SET CourseName = ?, YearDuration = ? WHERE CourseID = ?";
					
					PreparedStatement pst1 = dbcon.conn.prepareStatement(updateQuery1);
						
					pst1.setString(1, tableCourseN);
					pst1.setString(2, tableCourseYD);
					pst1.setInt(3, inttableCourseID);
					
					int rowsInserted1 = pst1.executeUpdate();
					
					
					
					//Update Teacher Table 
					String updateQuery2 = "UPDATE dbjnc6.tblteacher SET TeacherCourseHandle = ? WHERE FKCourseID = ?";
					PreparedStatement pst2 = dbcon.conn.prepareStatement(updateQuery2);
						
					pst2.setString(1, tableCourseN);
					pst2.setInt(2, inttableCourseID);
					
					int rowsInserted2 = pst2.executeUpdate();
					
					
					
					//Update Student Table 
					String updateQuery3 = "UPDATE dbjnc6.tblstudent SET StudCourse = ? WHERE CourseIDStud = ?";
					PreparedStatement pst3 = dbcon.conn.prepareStatement(updateQuery3);
						
					pst3.setString(1, tableCourseN);
					pst3.setInt(2, inttableCourseID);
					
					int rowsInserted3 = pst3.executeUpdate();
					
					if(rowsInserted1<=0 && rowsInserted2<=0 && rowsInserted3<=0) {
						
						dbpm.pm("/warningIcon.png", "No Record Updated");
					}else {
	
						dbpm.pm("/checkIcon.png", "Record Updated");
						txtCourseID.setText(""); //set ulit sa walang laman yung hidden txtbox for id
					}
					
					populateTable();
	
					pst1.close();
					pst2.close();
					pst3.close();
					dbcon.conn.close();
				
					}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		

		
	}
	
	private static void addCourse() {

		tableCourseN = textCourse.getText().toLowerCase().trim();
		tableCourseYD = txtYearDuration.getText().toLowerCase().trim();
		
		 try {
			TheConnection dbcon = new TheConnection();
			dbcon.connect_db();
			
			if (!textCourse.getText().isEmpty() && !txtYearDuration.getText().isEmpty()) {
				
				
				///////////////////////////
				
				Statement st = dbcon.conn.createStatement();
				String checkExistingCourse = "SELECT CourseName FROM dbjnc6.tblcourse WHERE CourseName = '"+tableCourseN+"'";
				ResultSet rs = st.executeQuery(checkExistingCourse);
				
					if(rs.next()) {
						dbpm.pm("/warningIcon.png", "Course is existing");
					}else {
						String insertQuery = "INSERT INTO dbjnc6.tblcourse(CourseName, YearDuration) VALUES(?, ?)";
						PreparedStatement pst = dbcon.conn.prepareStatement(insertQuery);
						
						pst.setString(1, tableCourseN);
						pst.setString(2, tableCourseYD);
						int rowsInserted = pst.executeUpdate();
						if (rowsInserted > 0) {
							
							dbpm.pm("/checkIcon.png", "New Record Added");
							
							populateTable();

							st.close();
							pst.close();
							dbcon.conn.close();	
						} else {
							
							dbpm.pm("/warningIcon.png", "No Record Added");
						} 
					}
					
					///////////////////////////				
		
			}else {
				
				dbpm.pm("/warningIcon.png", "Ensure all details are filled");
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}		
		 
	}
	
	
	
	private static void deleteCourse() {
		tableCourseID = tableCourse.getValueAt(tableCourse.getSelectedRow(), 0).toString();
		txtCourseID.setText(tableCourseID);
 		inttableCourseID = Integer.parseInt(tableCourseID);
 		
 		tableCourseN = textCourse.getText();
 		
		 try {
			TheConnection dbcon = new TheConnection();
			dbcon.connect_db();

			//delete from tblstudent first
			String deleteQuery1 = "DELETE FROM dbjnc6.tblstudent WHERE CourseIDStud = ?";
			PreparedStatement pst1 = dbcon.conn.prepareStatement(deleteQuery1);
			pst1.setString(1, tableCourseID);
			int rowsAffected1 = pst1.executeUpdate();

			//delete from tblteacher
			String deleteQuery2 = "DELETE FROM dbjnc6.tblteacher WHERE FKCourseID = ?";
			PreparedStatement pst2 = dbcon.conn.prepareStatement(deleteQuery2);
			pst2.setString(1, tableCourseID);
			int rowsAffected2 = pst2.executeUpdate();
			
			
			//delete here sa course
			String deleteQuery3 = "DELETE FROM dbjnc6.tblcourse WHERE CourseID = ?";
			PreparedStatement pst3 = dbcon.conn.prepareStatement(deleteQuery3);
			pst3.setString(1, tableCourseID);
			int rowsAffected3 = pst3.executeUpdate();
			
			
			if(rowsAffected1>0 || rowsAffected1>0 || rowsAffected3>0) {
				dbpm.pm("/checkIcon.png", "Record Deleted");
			}else {
				dbpm.pm("/warningIcon.png", "No Record Deleted");
			}	
				
			populateTable();
			
			txtCourseID.setText(""); //set ulit sa walang laman yung hidden txtbox for id
			
			pst1.close();
			pst2.close();
			pst3.close();

			dbcon.conn.close();
		 }catch(Exception e) {
			e.printStackTrace();
		}	
		
	}
}












