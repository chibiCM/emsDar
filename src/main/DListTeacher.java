package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class DListTeacher extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private static JTextField txtTeacherID;
	private static JTextField txtTLastName;
	static JTextField txtTFirstName;
	static JLabel lblNumRec;
	static JComboBox<String> cbxCourseHandle;
	String courseitem;
	
	
	//static String txtCourseHandle;
	
	static DefaultTableModel model;
	static JScrollPane scrollPane;
	private static JTable tableTeacher;
	static int cols;
	static int rowCount;
	
	static boolean isForUpdate = false;
	
	static String tableTeacherID;
	static int inttableTeacherID;
	static String tableTFirstName;
	static String tableTLastName;
	static String tableTCH;
	
	
	
	 boolean updateIconIsClicked = false;
	 static JLabel lblYouAreEditing;
	 DashBoard dbpm = new DashBoard();
	/**
	 * Create the panel.
	 */
	public DListTeacher() {
		setBackground(new Color(255, 255, 255));
        setBounds(291, 51, 680, 600);
        setLayout(null);
        
        JLabel lblNewLabel = new JLabel("List of Teachers");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblNewLabel.setBounds(10, 37, 660, 14);
        add(lblNewLabel);
        
        JLabel lbltxt1 = new JLabel("Last Name:");
        lbltxt1.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lbltxt1.setBounds(10, 75, 210, 14);
        add(lbltxt1);
        
        JLabel lbltxt2 = new JLabel("First Name: ");
        lbltxt2.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lbltxt2.setBounds(10, 100, 120, 20);
        add(lbltxt2);
        
        JLabel lbltxt3 = new JLabel("Course Handle: ");
        lbltxt3.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lbltxt3.setBounds(10, 127, 120, 20);
        add(lbltxt3);
        
        
        //hidden teacherID for comparison
        txtTeacherID = new JTextField();
        txtTeacherID.setVisible(false);
        txtTeacherID.setEditable(false);
        txtTeacherID.setEnabled(false);
        txtTeacherID.setColumns(10);
        txtTeacherID.setBounds(115, 42, 86, 22);
        add(txtTeacherID);
       

        txtTFirstName = new JTextField();
        txtTFirstName.setEnabled(false);
        txtTFirstName.setBounds(115, 100, 228, 22);
        add(txtTFirstName);
        txtTFirstName.setColumns(10);
        
        txtTLastName = new JTextField();
        txtTLastName.setEnabled(false);
        txtTLastName.setColumns(10);
        txtTLastName.setBounds(115, 73, 228, 22);
        add(txtTLastName);
        
        cbxCourseHandle = new JComboBox<String>();
       // populateCBXTeacherCourseHandle();
        cbxCourseHandle.setEnabled(false);
        cbxCourseHandle.setEditable(true);
        cbxCourseHandle.setBackground(new Color(255, 255, 255));
        cbxCourseHandle.setBounds(115, 128, 228, 22);
        add(cbxCourseHandle);
        cbxCourseHandle.removeAllItems();//para di patong patong yung lama ng combo box, remove item muna before populate
        populateCBXTeacherCourseHandle();
        
        
        
        JLabel lblNewLabel_1 = new JLabel("Total Records(s) in database are: ");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblNewLabel_1.setBounds(10, 562, 238, 27);
        add(lblNewLabel_1);
        
        //to show database number of records
        lblNumRec = new JLabel("");
        lblNumRec.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblNumRec.setBounds(231, 562, 112, 27);
        add(lblNumRec);
        
        
        //para sa icon label method from dashboard frame
        DashBoard dashB = new DashBoard();
        
        
        JLabel lblDelIcon = new JLabel("");
        lblDelIcon.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		deleteTeacher();
        	}
        });
        lblDelIcon.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblDelIcon.setBounds(630, 66, 40, 40);
        add(lblDelIcon);
        dashB.iconsDash("/deleteIcon.png", lblDelIcon);
        
        
        JLabel lblUpdateIcon = new JLabel("");
        lblUpdateIcon.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblUpdateIcon.setBounds(580, 66, 40, 40);
        add(lblUpdateIcon);
        dashB.iconsDash("/updateIcon.png", lblUpdateIcon);
        lblUpdateIcon.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		
        		
				
				 updateIconIsClicked = true;
				 
				 if(updateIconIsClicked && !txtTLastName.isEnabled() && !txtTFirstName.isEnabled() && !cbxCourseHandle.isEnabled()) {
					 dbpm.pm("/warningIcon.png", "<html><center>You are now updating records. Click + icon to enable textboxes</center></html>");
					 
					 lblYouAreEditing.setText("You are now updating records. Click + icon to enable textboxes.");
				 }
        		
        		 if(tableTeacher.getSelectedRowCount()==1) {
        			
        			tableTeacherID = tableTeacher.getValueAt(tableTeacher.getSelectedRow(), 0).toString();
        			tableTLastName = tableTeacher.getValueAt(tableTeacher.getSelectedRow(), 1).toString();
        			tableTFirstName = tableTeacher.getValueAt(tableTeacher.getSelectedRow(), 2).toString();
        			tableTCH = tableTeacher.getValueAt(tableTeacher.getSelectedRow(), 3).toString();
        			
        			txtTeacherID.setText(tableTeacherID);
        			txtTLastName.setText(tableTLastName);
        			txtTFirstName.setText(tableTFirstName);
        			cbxCourseHandle.setSelectedItem(tableTCH);
        			
        			lblYouAreEditing.setText("Selected record with ID:" + tableTeacherID);
        	 		
        	 		inttableTeacherID = Integer.parseInt(tableTeacherID);

        	 		isExisting(); //if existing magiging true is isForUpdate na boolean;
        	 		//need ma-identity if existing/forupdate yung record para malaman yung 
        	 		//procedure ng pagsave ng data if insert into or update set
             		
        		 }else {
        			 if(tableTeacher.getSelectedRowCount()==0) {
     					 dbpm.pm("/warningIcon.png", "<html><center>Empty Selection</center></html>");
        			 }else {
     					 dbpm.pm("/warningIcon.png", "<html><center>Select 1 Only</center></html>");
        			 }
        		 }
        	}
        });
        
        JLabel lblSaveIcon = new JLabel("");
        lblSaveIcon.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblSaveIcon.setBounds(530, 66, 40, 40);
        add(lblSaveIcon);
        dashB.iconsDash("/saveIcon.png", lblSaveIcon);
        lblSaveIcon.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		
        		if(txtTeacherID.getText().isEmpty() && txtTFirstName.getText().isEmpty() && txtTLastName.getText().isEmpty() && cbxCourseHandle.getSelectedItem().toString().isEmpty() || !txtTFirstName.isEnabled()) {
            		
       				 dbpm.pm("/warningIcon.png", "<html><center>Please add or update info before saving</center></html>");
            	}
        		else {
        			isExisting();//dapat nakalagay din to dito, kasi hindi naman lagi magclick si user sa updateIcon, para malaman kung for update
            		
            		String currentCBX = cbxCourseHandle.getSelectedItem().toString();
            		
            		if(isForUpdate && !txtTeacherID.getText().isEmpty()) {
            			updateTeacher();
            		}
            		
            		if(txtTeacherID.getText().isEmpty() && !isForUpdate && txtTFirstName.isEnabled() && txtTLastName.isEnabled() && cbxCourseHandle.isEnabled()) {
            			addTeacher();
            			isForUpdate = false;
            		}
        		}
        		
        		txtTeacherID.setText("");
        	}
        });
        
        
        JLabel lblNewIcon = new JLabel("");
        lblNewIcon.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblNewIcon.setBounds(480, 66, 40, 40);
        add(lblNewIcon);
        dashB.iconsDash("/newIcon.png", lblNewIcon);
        lblNewIcon.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {

        		if(!txtTeacherID.getText().isEmpty() && txtTFirstName.isEnabled() && txtTLastName.isEnabled() && cbxCourseHandle.isEnabled() && !isForUpdate){
        			
        			
					dbpm.pm("/checkIcon.png", "<html><center>You are now adding new record</center></html>");
					
					txtTeacherID.setText("");
        			txtTLastName.setText("");
        			txtTFirstName.setText("");
        			cbxCourseHandle.removeAllItems();
        			//////populateCBXTeacherCourseHandle();
        		
        			lblYouAreEditing.setText("You are adding record. Please fill up the details.");
        			
        			isForUpdate = false;
        		}else if(txtTeacherID.getText().isEmpty() && !isForUpdate){
        			
        			
					dbpm.pm("/checkIcon.png", "<html><center>You are now adding new record</center></html>");
					txtTFirstName.setEnabled(true);
	        		txtTLastName.setEnabled(true);
	        		cbxCourseHandle.setEnabled(true);
	        		
	        		txtTeacherID.setText("");
        			txtTLastName.setText("");
        			txtTFirstName.setText("");
        			//////populateCBXTeacherCourseHandle();
        			
        			lblYouAreEditing.setText("You are adding record. Please fill up the details.");
        			
        			isForUpdate = false;
        		}else if(!txtTeacherID.getText().isEmpty() && isForUpdate){
        			if(isForUpdate) {
        				
    					dbpm.pm("/checkIcon.png", "<html><center>You are now updating records. Click + icon again to add new record</center></html>");
    					isForUpdate = false;
    					lblYouAreEditing.setText("You are now updating records. Click + icon again to add new record.");
    					
        			}
					txtTFirstName.setEnabled(true);
	        		txtTLastName.setEnabled(true);
	        		cbxCourseHandle.setEnabled(true);
	        		//////populateCBXTeacherCourseHandle();
	        		
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
        
        
        scrollPane = new JScrollPane();
        
        scrollPane.addHierarchyListener(new HierarchyListener() {
        	public void hierarchyChanged(HierarchyEvent e) {
        		populateTable();
        	}
        });
        scrollPane.setBounds(10, 208, 660, 343);
        add(scrollPane);
        
        lblYouAreEditing = new JLabel("");
        lblYouAreEditing.setBackground(new Color(48, 104, 120));
        lblYouAreEditing.setForeground(new Color(48, 104, 120));
        lblYouAreEditing.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblYouAreEditing.setBounds(10, 170, 660, 27);
        add(lblYouAreEditing);
        
        
        
	}
	
	
	//methods
	
	
	public static void populateTable() {
		try {
			TheConnection dbcon = new TheConnection();
			dbcon.connect_db();
			
			tableTeacher = new JTable();
		    tableTeacher.setBackground(Color.WHITE);
		    tableTeacher.setFont(new Font("Tahoma", Font.PLAIN, 13));

		    scrollPane.setViewportView(tableTeacher);

			Statement st = dbcon.conn.createStatement();
			
			String populateCourses = "SELECT ID, TeacherLastName, TeacherFirstName, TeacherCourseHandle FROM dbjnc6.tblteacher";
				
			ResultSet rs = st.executeQuery(populateCourses);
			
			ResultSetMetaData rsmd = rs.getMetaData();
			
			model = new DefaultTableModel();
			tableTeacher.setModel(model);
			
			cols = rsmd.getColumnCount();
			
			String[] colName = new String[cols];
			
			for(int i = 0; i < cols; i++) {
				colName[i] = rsmd.getColumnName(i+1);
				model.setColumnIdentifiers(colName);
			}
			
			tableTeacher.getColumnModel().getColumn(0).setHeaderValue("ID");
			tableTeacher.getColumnModel().getColumn(1).setHeaderValue("Last Name");
			tableTeacher.getColumnModel().getColumn(2).setHeaderValue("First Name");
			tableTeacher.getColumnModel().getColumn(3).setHeaderValue("Course Handle");
			
			
			//disable ID row
			model.setRowCount(0); // para di nagpapatong-patong/nag-aadd kada show ng table
			
			//hide row, need value pero di need makita sa table
			TableColumn colTeacher = tableTeacher.getColumnModel().getColumn(3);
			//must be in order, kasi pag hindi nakikita pa rin sa jtable min-max-wid
			colTeacher.setMinWidth(0);
			colTeacher.setMaxWidth(0);
			colTeacher.setWidth(0);

			
			String teacherID, teacherLastName, teacherFirstName, teacherCourse;
			while(rs.next()) {
				teacherID = rs.getString(1);
				teacherLastName = rs.getString(2);
				teacherFirstName = rs.getString(3);
				teacherCourse = rs.getString(4);
				String row[] = {teacherID, teacherLastName, teacherFirstName, teacherCourse};
				model.addRow(row);
			}
			
			rowCount = tableTeacher.getRowCount();
			lblNumRec.setText(String.valueOf(rowCount));
			
		}catch(Exception e) {
			
		}
	}
	

	public void populateCBXTeacherCourseHandle() {
		
		
		try {

			TheConnection dbcon = new TheConnection();
			dbcon.connect_db();
			
			if(!isForUpdate) {
				Statement st = dbcon.conn.createStatement();
				
				String selectQuery = "SELECT CourseName FROM dbjnc6.tblcourse";
					
				ResultSet rs = st.executeQuery(selectQuery);
				
				
				cbxCourseHandle.insertItemAt("", 0);
				
				while(rs.next()) {
					courseitem = rs.getString("CourseName");
					cbxCourseHandle.addItem(courseitem);
				}
				
				cbxCourseHandle.setSelectedIndex(0);
			}
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	
	
	public void isExisting() {
		
 		
 		try {
			TheConnection dbcon = new TheConnection();
			dbcon.connect_db();
			
			Statement st = dbcon.conn.createStatement();
			
			tableTeacherID = tableTeacher.getValueAt(tableTeacher.getSelectedRow(), 0).toString();
			txtTeacherID.setText(tableTeacherID);
	 		inttableTeacherID = Integer.parseInt(tableTeacherID);
			
			String selectQuery = "SELECT * FROM dbjnc6.tblteacher WHERE ID = '"+ inttableTeacherID +"'";
	
			ResultSet rs = st.executeQuery(selectQuery);
			
			if(rs.next()) {
				isForUpdate = true;
			}
		}catch(Exception ex) {
			
		}
	}
	
	
	private void updateTeacher() {
		// TODO Auto-generated method stub

		if(!txtTeacherID.getText().isEmpty() && isForUpdate) {
			try {
				TheConnection dbcon = new TheConnection();
				dbcon.connect_db();
				
				tableTeacherID = txtTeacherID.getText().toLowerCase().trim();
				tableTLastName = txtTLastName.getText().toLowerCase().trim();
				tableTFirstName = txtTFirstName.getText().toLowerCase().trim();
				tableTCH = (String) cbxCourseHandle.getSelectedItem();
				String fullname = tableTFirstName+" "+tableTLastName;
				
				if (!txtTLastName.isEnabled() && !txtTFirstName.isEnabled()) {
						dbpm.pm("/warningIcon.png", "<html><center>Enable textboxes before editing then saving.</center></html>");
				}else if(tableTLastName.length() == 0 || tableTFirstName.length() == 0) {
					dbpm.pm("/warningIcon.png", "<html><center>Select first a record to edit before hitting save.</center></html>");
				}
				else {

					if(!txtTeacherID.getText().isEmpty() &&(txtTLastName.getText().isEmpty() || txtTFirstName.getText().isEmpty() || "".equals(tableTCH))) {
						
						dbpm.pm("/warningIcon.png", "<html><center>Please fill details</center></html>");
					}else {
						
						
						
						//update teacher table
						String updateQuery1 = "UPDATE dbjnc6.tblteacher SET TeacherLastName = ?, TeacherFirstName = ?, TeacherCourseHandle = ?, TeacherFullName = ? WHERE ID = ?";
						PreparedStatement pst1 = dbcon.conn.prepareStatement(updateQuery1);
							
						pst1.setString(1, tableTLastName);
						pst1.setString(2, tableTFirstName);
						pst1.setString(3, tableTCH.toLowerCase());
						pst1.setString(4, fullname);
						pst1.setInt(5, inttableTeacherID);
						
						int rowsInserted1 = pst1.executeUpdate();
						

						//update student table
						String updateQuery2 = "UPDATE dbjnc6.tblstudent SET TeacherFullName = ? WHERE ID = ?";
						PreparedStatement pst2 = dbcon.conn.prepareStatement(updateQuery2);
							
						pst2.setString(1, fullname);
						pst2.setInt(2, inttableTeacherID);
						
						int rowsInserted2 = pst2.executeUpdate();
						
						
						
						
						
						if(rowsInserted1<=0 && rowsInserted2<=0) {
							
							dbpm.pm("/warningIcon.png", "<html><center>No Record Updated</center></html>");
						}

						if(rowsInserted1>0 && rowsInserted2>0) {
							
							dbpm.pm("/checkIcon.png", "<html><center>Record Updated</center></html>");
							txtTeacherID.setText("");
						}
						
						populateTable();
						isForUpdate = false;
					}
					
				}	
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
	private void addTeacher() {
		// TODO Auto-generated method stub
		
		tableTLastName = txtTLastName.getText().toLowerCase().trim();
		tableTFirstName = txtTFirstName.getText().toLowerCase().trim();
		tableTCH = (String) cbxCourseHandle.getSelectedItem();
		
		try {
			TheConnection dbcon = new TheConnection();
			dbcon.connect_db();
		
			
			if (!txtTLastName.getText().isEmpty() && !txtTFirstName.getText().isEmpty()) {
				
				String insertQuery = "INSERT INTO dbjnc6.tblteacher(TeacherLastName, TeacherFirstName, TeacherCourseHandle, FKCourseID, TeacherFullName) VALUES(?, ?, ?,(SELECT CourseID FROM dbjnc6.tblcourse WHERE CourseName = ?),CONCAT(?, ' ', ?) )";
				PreparedStatement st = dbcon.conn.prepareStatement(insertQuery);
				
				st.setString(1, tableTLastName);
				st.setString(2, tableTFirstName);
				st.setString(3, tableTCH.toLowerCase());
				st.setString(4, tableTCH);
				st.setString(5, tableTFirstName);
				st.setString(6, tableTLastName);
				
				int rowsInserted = st.executeUpdate();
				if (rowsInserted > 0) {
					
					dbpm.pm("/checkIcon.png", "<html><center>New Record Added</center></html>");
					
					populateTable();

				} else {
					
					dbpm.pm("/warningIcon.png", "<html><center>No Record Added</center></html>");
				} 
			}else {
				
				dbpm.pm("/warningIcon.png", "<html><center>Ensure all details are filled</center></html>");
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}	
	}
	
	public void deleteTeacher() {
		tableTeacherID = tableTeacher.getValueAt(tableTeacher.getSelectedRow(), 0).toString();
		txtTeacherID.setText(tableTeacherID);
		inttableTeacherID = Integer.parseInt(tableTeacherID);
 		
		 try {
			TheConnection dbcon = new TheConnection();
			dbcon.connect_db();

			//delete first student dahil dependent sa teacher
			String deleteQuery1 = "DELETE FROM dbjnc6.tblstudent WHERE TeacherIDStud = ?";
			PreparedStatement pst1 = dbcon.conn.prepareStatement(deleteQuery1);
			pst1.setString(1, tableTeacherID);
			int rowsAffected = pst1.executeUpdate();
			
			
			//delete teacher
			String deleteQuery2 = "DELETE FROM dbjnc6.tblteacher WHERE ID = ?";
			PreparedStatement pst2 = dbcon.conn.prepareStatement(deleteQuery2);
			pst2.setString(1, tableTeacherID);
			int rowsAffected2 = pst2.executeUpdate();
			
			
			if(rowsAffected>0 || rowsAffected2 > 0) {
				dbpm.pm("/checkIcon.png", "<html><center>Record Deleted</center></html>");
			}else {
				dbpm.pm("/warningIcon.png", "<html><center>No Record Deleted</center></html>");
			}	
				
			populateTable();
			txtTeacherID.setText("");
		 }catch(Exception e) {
			e.printStackTrace();
		}
	}
}
