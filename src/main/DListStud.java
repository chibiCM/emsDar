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
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import com.toedter.calendar.IDateEditor;
import com.toedter.calendar.JDateChooser;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JButton;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DListStud extends JPanel {

	private static final long serialVersionUID = 1L;
	 
	 //Components
	 JTextField txtFirstName;
	 JTextField txtLastName;
	 JTextField txtSID;
	 JDateChooser dCBirthday;
	 JComboBox<String> cbxStudCourse;
	 JComboBox<String> cbxStudTeacher;
	 
	 //label icon
	 static JComponent lblNewIcon;
	 
	 
	 //Selected row
	 static String tableStudID;
	 
	 static String tableLastName;
	 static String tableFirstName;
	 static String  tableBirthday;
	 static String  tableStudTeacherID;
	 static String  tableStudCourseID;
	 static String  tableStudTeacher;
	 static String  tableStudCourse;
	 
	 
	 static String tableBirthdaySTR;
	 
	 static Date  datetableBirthday;
	 
	 //converted txtSID to int to compare sa tbl
	 static int inttxtSID;
	 
	 static int inttableStudID;
	 
	 
	 static boolean isForUpdate = false;
	 
	 
	 static String availableTeacherForCourse;
	 
	 
	 
	 //for the table
	 static JScrollPane scrollPane;
	 static JTable tableStudList;
	 static DefaultTableModel model;
	 static int cols;
	 static int rowCount;
	 static JLabel lblNumRec;
	 
	 static String popuTeacher;
	 
	 String courseitem;
	 String teacheritem;
	 
	 static JLabel lblYouAreEditing;
	 DashBoard dbpm = new DashBoard();
	 
	 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	 private JTextField textField;


	 static String getCourseForTeacherCBX;
	 
	 static String hdSTRcourse;
	 private JTextField hiddenCourse;
	/**
	 * Create the panel.
	 */
	public DListStud() {
		
		setBackground(new Color(255, 255, 255));
        setBounds(291, 51, 680, 600);
        setLayout(null);
        
        
        
        JLabel lblNewLabel = new JLabel("List of Student");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblNewLabel.setBounds(10, 37, 660, 14);
        add(lblNewLabel);
        
        JLabel lblFirstName = new JLabel("First Name:");
        lblFirstName.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblFirstName.setBounds(10, 75, 141, 14);
        add(lblFirstName);
        
        JLabel lblLastName = new JLabel("Last Name: ");
        lblLastName.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblLastName.setBounds(10, 100, 120, 20);
        add(lblLastName);
        
        JLabel lblDOB = new JLabel("Date of Birth: ");
        lblDOB.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblDOB.setBounds(10, 130, 120, 20);
        add(lblDOB);
        
        JLabel lblCourse = new JLabel("Course: ");
        lblCourse.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblCourse.setBounds(10, 160, 120, 20);
        add(lblCourse);
        
        JLabel lblTeacher = new JLabel("Teacher: ");
        lblTeacher.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblTeacher.setBounds(10, 190, 120, 20);
        add(lblTeacher);
        
        
        txtSID = new JTextField();
        txtSID.setEnabled(false);
        txtSID.setBounds(10, 42, 78, 22);
        add(txtSID);
        txtSID.setColumns(10);
        txtSID.setVisible(false);
        
        txtFirstName = new JTextField();
        txtFirstName.setEnabled(false);
        txtFirstName.setBounds(115, 73, 228, 22);
        add(txtFirstName);
        txtFirstName.setColumns(10);
        
        txtLastName = new JTextField();
        txtLastName.setEnabled(false);
        txtLastName.setBounds(115, 100, 228, 22);
        add(txtLastName);
        txtLastName.setColumns(10);
        
        dCBirthday =   new JDateChooser();
        dCBirthday.setDateFormatString("yyyy-MM-dd");
        dCBirthday.setBounds(115, 131, 228, 22);
        dCBirthday.setEnabled(false);
        add(dCBirthday);
        
        
        cbxStudCourse = new JComboBox();
        cbxStudCourse.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		
        		if(!cbxStudCourse.getSelectedItem().toString().isEmpty()) {
        			hiddenCourse.setText(cbxStudCourse.getSelectedItem().toString()); 
        			cbxStudTeacher.removeAllItems();
        			populateCBXStudTeacherSELECTED();
        		}
        		
        	}
        });
        cbxStudCourse.setEditable(true);
        cbxStudCourse.setEnabled(false);
        cbxStudCourse.setBounds(115, 161, 228, 22);
        add(cbxStudCourse);
        populateCBXStudCourseALL();
        
        
        
        cbxStudTeacher = new JComboBox();
        cbxStudTeacher.setEditable(true);
        cbxStudTeacher.setEnabled(false);
        cbxStudTeacher.setBounds(115, 191, 228, 22);
        add(cbxStudTeacher);
        
        
        

        JLabel lblNewLabel_1 = new JLabel("Total Records(s) in database are: ");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblNewLabel_1.setBounds(10, 562, 238, 27);
        add(lblNewLabel_1);
        
        lblNumRec = new JLabel("");
        lblNumRec.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblNumRec.setBounds(231, 562, 112, 27);
        add(lblNumRec);
        
        
        DashBoard dashB = new DashBoard();
      
		
        JLabel lblNewIcon = new JLabel("");
        lblNewIcon.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        			if(!txtSID.getText().isEmpty() && txtFirstName.isEnabled() && txtLastName.isEnabled() && cbxStudCourse.isEnabled() && !isForUpdate){

					dbpm.pm("/checkIcon.png", "<html><center>You are now adding new record</center></html>");
					
					txtSID.setText("");
        			txtLastName.setText("");
        			txtFirstName.setText("");
        			dCBirthday.setCalendar(null);
        		 	
        			

        			lblYouAreEditing.setText("You are adding record. Please fill up the details.");
        			
        			isForUpdate = false;
        		}else if(txtSID.getText().isEmpty() && !isForUpdate){
        			
        			
					dbpm.pm("/checkIcon.png", "<html><center>You are now adding new record</center></html>");
					txtFirstName.setEnabled(true);
	        		txtLastName.setEnabled(true);
	        		dCBirthday.setEnabled(true);
	        		cbxStudCourse.setEnabled(true);
	        		cbxStudTeacher.setEnabled(true);
	        		
	        		
	        		txtSID.setText("");
        			txtLastName.setText("");
        			txtFirstName.setText("");
        			dCBirthday.setCalendar(null);
        			


        			lblYouAreEditing.setText("You are adding record. Please fill up the details.");
        			
        			isForUpdate = false;
        		}else if(!txtSID.getText().isEmpty() && isForUpdate){
        			if(isForUpdate) {
        				
    					dbpm.pm("/checkIcon.png", "<html><center>You are now updating records. Click + icon again to add new record</center></html>");
    					isForUpdate = false;
    					lblYouAreEditing.setText("You are now updating records. Click + icon again to add new record.");
        			}
        			txtFirstName.setEnabled(true);
	        		txtLastName.setEnabled(true);
	        		dCBirthday.setEnabled(true);
	        		cbxStudCourse.setEnabled(true);
	        		cbxStudTeacher.setEnabled(true);
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
        lblNewIcon.setBounds(480, 110, 40, 40);
        add(lblNewIcon);
        dashB.iconsDash("/newIcon.png", lblNewIcon);
       
        
        
        JLabel lblSaveIcon = new JLabel("");
        lblSaveIcon.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblSaveIcon.setBounds(530, 110, 40, 40);
        add(lblSaveIcon);
        dashB.iconsDash("/saveIcon.png", lblSaveIcon);
        lblSaveIcon.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		
	        		if(txtSID.getText().isEmpty() && txtFirstName.getText().isEmpty() && txtLastName.getText().isEmpty() && cbxStudCourse.getSelectedItem().toString().isEmpty() && dCBirthday.getDate().toString().isEmpty() && cbxStudTeacher.getSelectedItem().toString().isEmpty() || !txtFirstName.isEnabled()) {
	            		
	      				 dbpm.pm("/warningIcon.png", "<html><center>Please add or update info before saving</center></html>");
	           	}
	       		else {
	       			isExisting();

	           		
	           		String currentCBX = cbxStudCourse.getSelectedItem().toString();
	           		
	           		if(isForUpdate && !txtSID.getText().isEmpty()) {
	           			updateStud();
	           		}
	           		
	           		if(txtSID.getText().isEmpty() && !isForUpdate && txtFirstName.isEnabled() && txtLastName.isEnabled() && cbxStudCourse.isEnabled()) {
	           			addStud();
	           			isForUpdate = false;
	           		}
	       		}
        		
        	}
        });
        
        
        
        JLabel lblUpdateIcon = new JLabel("");
        lblUpdateIcon.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblUpdateIcon.setBounds(580, 110, 40, 40);
        add(lblUpdateIcon);
        dashB.iconsDash("/updateIcon.png", lblUpdateIcon);
        lblUpdateIcon.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		
        		 if(tableStudList.getSelectedRowCount()==1) {
        			
        			 tableStudID = tableStudList.getValueAt(tableStudList.getSelectedRow(), 0).toString();
        			 tableLastName = tableStudList.getValueAt(tableStudList.getSelectedRow(), 1).toString();
        			 tableFirstName = tableStudList.getValueAt(tableStudList.getSelectedRow(), 2).toString();
        			 tableBirthday = (String) tableStudList.getValueAt(tableStudList.getSelectedRow(), 3);
        			 //datetableBirthday = (Date) tableStudList.getValueAt(tableStudList.getSelectedRow(), 3);
        			 tableStudCourseID = tableStudList.getValueAt(tableStudList.getSelectedRow(), 4).toString();
        			 tableStudTeacherID = tableStudList.getValueAt(tableStudList.getSelectedRow(), 5).toString();
        			 tableStudTeacher = tableStudList.getValueAt(tableStudList.getSelectedRow(), 6).toString();
        			 tableStudCourse = tableStudList.getValueAt(tableStudList.getSelectedRow(), 7).toString();

             		
        			 txtSID.setText(tableStudID);
        			 txtFirstName.setText(tableFirstName);
        			 txtLastName.setText(tableLastName);
        			 
        			 ((JTextField)dCBirthday.getDateEditor().getUiComponent()).setText(tableBirthday);
        			 
        			 
        			 cbxStudCourse.setSelectedItem(tableStudCourse);
        			 hiddenCourse.setText(cbxStudCourse.getSelectedItem().toString());
        			 
        			// cbxStudTeacher.setSelectedItem(tableStudTeacher);
        	 		
        			 inttxtSID = Integer.parseInt(tableStudID);
        	 		
        	 		isExisting();
             		
        		 }else {
        			 if(tableStudList.getSelectedRowCount()==0) {
        				 DashBoard dbpm = new DashBoard();
     					 dbpm.pm("/warningIcon.png", "Empty Selection");
        			 }else {
        				 DashBoard dbpm = new DashBoard();
     					 dbpm.pm("/warningIcon.png", "Select 1 Only");
        			 }
        		 }
        	}
        });
        
        
        
        JLabel lblDelIcon = new JLabel("");
        lblDelIcon.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblDelIcon.setBounds(630, 110, 40, 40);
        add(lblDelIcon);
        dashB.iconsDash("/deleteIcon.png", lblDelIcon);
        lblDelIcon.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		deleteTeacher();
        	}
        });
        
        
        scrollPane = new JScrollPane();
        scrollPane.addHierarchyListener(new HierarchyListener() {
        	public void hierarchyChanged(HierarchyEvent e) {
        		populateTable();
        	}
        });
        scrollPane.setBounds(10, 275, 660, 270);
        add(scrollPane);
        
        
        lblYouAreEditing = new JLabel("");
        lblYouAreEditing.setBackground(new Color(48, 104, 120));
        lblYouAreEditing.setForeground(new Color(48, 104, 120));
        lblYouAreEditing.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblYouAreEditing.setBounds(10, 237, 660, 27);
        add(lblYouAreEditing);
        
        hiddenCourse = new JTextField();
        hiddenCourse.setBounds(380, 162, 86, 20);
        add(hiddenCourse);
        hiddenCourse.setColumns(10);
        hiddenCourse.setVisible(false);
        
        
        
       
	}
	
	
	public void populateCBXStudCourseALL() {
		
		try {

			TheConnection dbcon = new TheConnection();
			dbcon.connect_db();
			
			if(!isForUpdate) {
				Statement st = dbcon.conn.createStatement();
				
				String selectQuery = "SELECT CourseName FROM dbjnc6.tblcourse";
					
				ResultSet rs = st.executeQuery(selectQuery);
				
				
				cbxStudCourse.insertItemAt("", 0);
				
				while(rs.next()) {
					courseitem = rs.getString("CourseName");
					cbxStudCourse.addItem(courseitem);
				}
				
				cbxStudCourse.setSelectedIndex(0);
			}
			//getCourseForTeacherCBX = cbxStudCourse.getSelectedItem().toString().toString();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	

	

		public void populateCBXStudTeacherSELECTED() {
			
			try {
				
				String courseSelected = cbxStudCourse.getSelectedItem().toString();

				TheConnection dbcon = new TheConnection();
				dbcon.connect_db();
				
				if(!isForUpdate) {
					Statement st = dbcon.conn.createStatement();
					
					
					//String selectQuery = "SELECT TeacherFullName FROM dbjnc6.tblteacher";
					///JOptionPane.showMessageDialog(null, "4"+availableTeacherForCourse);
					///availableTeacherForCourse = cbxStudCourse.getSelectedItem().toString();
					
					//JOptionPane.showMessageDialog(null, "4"+courseSelected);
					String selectQuery = "SELECT TeacherFullName FROM dbjnc6.tblteacher WHERE TeacherCourseHandle = (SELECT CourseName FROM dbjnc6.tblcourse WHERE CourseName = '"+courseSelected+"')";

					
					ResultSet rs = st.executeQuery(selectQuery);
					
					
					cbxStudTeacher.insertItemAt("", 0);
					
					while(rs.next()) {
						teacheritem = rs.getString("TeacherFullName");
						cbxStudTeacher.addItem(teacheritem);
					}
					
					cbxStudTeacher.setSelectedIndex(0);
				}
				
			}catch(Exception ex) {
				ex.printStackTrace();
			}
		}



	
	
	public static void populateTable() {
		try {
			TheConnection dbcon = new TheConnection();
			dbcon.connect_db();
			
			tableStudList = new JTable();
		    tableStudList.setBackground(Color.WHITE);
		    tableStudList.setFont(new Font("Tahoma", Font.PLAIN, 13));

		    scrollPane.setViewportView(tableStudList);

			Statement st = dbcon.conn.createStatement();
			
			//String populateCourses = "SELECT * FROM dbjnc6.tblstudent";
			
			
			////String populateCourses0 = "CREATE TEMPORARY TABLE dbjnc6.tblTempStud as SELECT dbjnc6.tblstudent.ID, dbjnc6.tblstudent.StudentLastName, dbjnc6.tblstudent.StudentFirstName, dbjnc6.tblstudent.StudentDOB, dbjnc6.tblcourse.CourseName, dbjnc6.tblteacher.TeacherLastName FROM dbjnc6.tblstudent JOIN dbjnc6.tblcourse ON dbjnc6.tblstudent.CourseIDStud = dbjnc6.tblcourse.CourseID JOIN dbjnc6.tblteacher ON dbjnc6.tblstudent.TeacherIDStud = dbjnc6.tblteacher.ID";
			
			////st.executeUpdate(populateCourses0);
			
			////String populateCourses ="SELECT * FROM tblTempStud";
			
			//String populateCourses = "SELECT * FROM dbjnc6.tblstudwithtc";
			
			String populateCourses = "SELECT * FROM dbjnc6.tblstudent";

			ResultSet rs = st.executeQuery(populateCourses);
			
			ResultSetMetaData rsmd = rs.getMetaData();
			
			model = new DefaultTableModel();
			tableStudList.setModel(model);
			
			cols = rsmd.getColumnCount();
			
			String[] colName = new String[cols];
			
			for(int i = 0; i < cols; i++) {
				colName[i] = rsmd.getColumnName(i+1);
				model.setColumnIdentifiers(colName);
			}
			
			tableStudList.getColumnModel().getColumn(0).setHeaderValue("ID");
			tableStudList.getColumnModel().getColumn(1).setHeaderValue("StudentLastName");
			tableStudList.getColumnModel().getColumn(2).setHeaderValue("StudentFirstName");
			tableStudList.getColumnModel().getColumn(3).setHeaderValue("StudentDOB");
			tableStudList.getColumnModel().getColumn(4).setHeaderValue("CourseIDStud");
			tableStudList.getColumnModel().getColumn(5).setHeaderValue("TeacherIDStud");
			tableStudList.getColumnModel().getColumn(6).setHeaderValue("TeacherFullName");
			tableStudList.getColumnModel().getColumn(7).setHeaderValue("StudCourse");
			//hide course and teacher, need value pero di need makita sa table
			//TableColumn colCourse = tableStudList.getColumnModel().getColumn(4);
			//TableColumn colTeacher = tableStudList.getColumnModel().getColumn(5);
			
			//must be in order, kasi pag hindi nakikita pa rin sa jtable min-max-wid
			//colCourse.setMinWidth(0);
			//colCourse.setMaxWidth(0);
			//colCourse.setWidth(0);
			
			//colTeacher.setMinWidth(0);
			//colTeacher.setMaxWidth(0);
			//colTeacher.setWidth(0);
			
			model.setRowCount(0); // para di nagpapatong-patong/nag-aadd kada show ng table
			
			String studentID, studentLastName, studentFirstName, studentBirthday, studCourseID, studTeacherID, studentTeacher, studentCourse;
			while(rs.next()) {
				studentID = rs.getString(1);
				studentLastName = rs.getString(2);
				studentFirstName = rs.getString(3);
				studentBirthday = rs.getString(4);
				studCourseID = rs.getString(5);
				studTeacherID = rs.getString(6);
				studentTeacher = rs.getString(7);
				studentCourse = rs.getString(8);
				String row[] = {studentID, studentLastName, studentFirstName, studentBirthday, studCourseID, studTeacherID, studentTeacher, studentCourse};
				model.addRow(row);
			}
			
			
		//	st.close();
			//dbcon.conn.close();
			
			rowCount = tableStudList.getRowCount();
			lblNumRec.setText(String.valueOf(rowCount));

		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void isExisting() {
		//JOptionPane.showMessageDialog(null, "Before existing "+txtSID.getText()+" "+txtFirstName.getText()+" "+txtLastName.getText()+" "+cbxStudCourse.getSelectedItem().toString()+" "+dCBirthday.getDate().toString()+" "+cbxStudTeacher.getSelectedItem().toString());
		
		
 		
 		try {
			TheConnection dbcon = new TheConnection();
			dbcon.connect_db();
			
			Statement st = dbcon.conn.createStatement();
			
			tableStudID = tableStudList.getValueAt(tableStudList.getSelectedRow(), 0).toString();
			txtSID.setText(tableStudID);
	 		inttableStudID = Integer.parseInt(tableStudID);
			
			//String selectQuery = "SELECT * FROM dbjnc6.tblteacher WHERE CourseID = '"+ inttableTeacherID +"'";
			String selectQuery = "SELECT * FROM dbjnc6.tblstudent WHERE ID = '"+ inttableStudID +"'";
	
			ResultSet rs = st.executeQuery(selectQuery);
			
			if(rs.next()) {
				isForUpdate = true;
			}
		}catch(Exception ex) {
			
		}
	}
	
	








	
	
	private void updateStud() {
		if(!txtSID.getText().isEmpty() && isForUpdate) {
			try {
				TheConnection dbcon = new TheConnection();
				dbcon.connect_db();
				
				tableStudID = txtSID.getText().toLowerCase().trim();
				tableLastName = txtLastName.getText().toLowerCase().trim();
				tableFirstName = txtFirstName.getText().toLowerCase().trim();
				//String tableBirthdayGetDataForDB = ((JTextField)dCBirthday.getDateEditor()).getText();
				//tableBirthday = ((JTextField)dCBirthday.getDateEditor()).getText();
				tableBirthdaySTR = ((JTextField)dCBirthday.getDateEditor()).getText().trim();
				tableStudCourse = (String) cbxStudCourse.getSelectedItem();
				tableStudTeacher = (String) cbxStudTeacher.getSelectedItem();
				

				if (!txtLastName.isEnabled() && !txtFirstName.isEnabled()) {
						dbpm.pm("/warningIcon.png", "<html><center>Enable textboxes before editing then saving.</center></html>");
				}else if(tableLastName.length() == 0 || tableFirstName.length() == 0 || tableBirthday.isEmpty() || tableStudTeacher.isEmpty() || tableBirthday.length() ==0) {
					dbpm.pm("/warningIcon.png", "<html><center>Select first a record to edit before hitting save.</center></html>");
				}
				else {

					if(!txtSID.getText().isEmpty() &&(txtLastName.getText().isEmpty() || txtFirstName.getText().isEmpty() || "".equals(tableStudCourse) || "".equals(tableStudTeacher) || dCBirthday.getDate().toString().isEmpty())) {
						
						dbpm.pm("/warningIcon.png", "<html><center>Please fill details</center></html>");
					}else {
						//String updateQuery = "UPDATE dbjnc6.tblstudent SET StudentLastName = ?, StudentFirstName = ?, StudentDOB = ?, TeacherFullName = ?, StudCourse =? WHERE ID = ?";
						
						String updateQuery = "UPDATE dbjnc6.tblstudent SET StudentLastName = ?, StudentFirstName = ?, StudentDOB = ?, TeacherFullName = ?, StudCourse = ?, TeacherIDStud = (SELECT ID FROM dbjnc6.tblteacher WHERE TeacherFullName =?), CourseIDStud = (SELECT CourseID FROM dbjnc6.tblcourse WHERE CourseName=?) WHERE ID = ?";
						
						//JOptionPane.showMessageDialog(null, tableLastName + tableFirstName + tableBirthdaySTR + tableStudTeacher + tableStudCourse);
						
						PreparedStatement pst = dbcon.conn.prepareStatement(updateQuery);
							
						pst.setString(1, tableLastName);
						pst.setString(2, tableFirstName);
						pst.setString(3, tableBirthdaySTR);
						pst.setString(4, tableStudTeacher);
						pst.setString(5, tableStudCourse);
						pst.setString(6, tableStudTeacher);
						pst.setString(7, tableStudCourse);
						pst.setString(8, tableStudID);
						//pst.setInt(6, inttxtSID);
						
						int rowsInserted = pst.executeUpdate();
						
						
						if(rowsInserted<=0 ) {
							
							dbpm.pm("/warningIcon.png", "<html><center>No Record Updated</center></html>");
						}

						if(rowsInserted>0) {
							
							dbpm.pm("/checkIcon.png", "<html><center>Record Updated</center></html>");
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
	
	
	
	public void addStud() {
		
		//tableStudID 
		
		
		try {
			TheConnection dbcon = new TheConnection();
			dbcon.connect_db();
			
			tableLastName = txtLastName.getText().toLowerCase().trim();
			tableFirstName = txtFirstName.getText().toLowerCase().trim();
			String tableBirthdayGetDataForDB = ((JTextField)dCBirthday.getDateEditor()).getText().trim();
			tableStudCourse = (String) cbxStudCourse.getSelectedItem();
			tableStudTeacher = (String) cbxStudTeacher.getSelectedItem();
			
			if (!txtLastName.getText().isEmpty() && !txtFirstName.getText().isEmpty()) {
				String insertQuery = "INSERT INTO dbjnc6.tblstudent(StudentLastName, StudentFirstName, StudentDOB, TeacherFullName, StudCourse, TeacherIDStud, CourseIDStud) VALUES(?, ?, ?, ?, ?, (SELECT ID FROM dbjnc6.tblteacher WHERE TeacherFullName = ?), (SELECT CourseID FROM dbjnc6.tblcourse WHERE CourseName = ?))";
				
				
				
				PreparedStatement pst = dbcon.conn.prepareStatement(insertQuery);

				
				pst.setString(1, tableLastName);
				pst.setString(2, tableFirstName);
				pst.setString(3, tableBirthdayGetDataForDB);
				pst.setString(4, tableStudTeacher);
				pst.setString(5, tableStudCourse);
				pst.setString(6, tableStudTeacher);
				pst.setString(7, tableStudCourse);
			
				
				int rowsInserted = pst.executeUpdate();
				
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
		tableStudID = tableStudList.getValueAt(tableStudList.getSelectedRow(), 0).toString();
		txtSID.setText(tableStudID);
		inttxtSID = Integer.parseInt(tableStudID);
 		
		 try {
			TheConnection dbcon = new TheConnection();
			dbcon.connect_db();
				
			String deleteQuery = "DELETE FROM dbjnc6.tblstudent WHERE ID = ? ";
			
			PreparedStatement pst = dbcon.conn.prepareStatement(deleteQuery);
			
			pst.setString(1, tableStudID);
			
			int rowsAffected = pst.executeUpdate();
			
			if(rowsAffected>0) {
				dbpm.pm("/warningIcon.png", "<html><center>Record Deleted</center></html>");
			}else {
				dbpm.pm("/warningIcon.png", "<html><center>No Record Deleted</center></html>");
			}	
			
			populateTable();
			txtSID.setText("");
		 }catch(Exception e) {
			e.printStackTrace();
		}
	}
}
