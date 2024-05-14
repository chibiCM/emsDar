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
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class DListEnrolled extends JPanel {

	private static final long serialVersionUID = 1L;
	private static JTextField txtSearch;
	
	
	private static JTable tableEnrolled;
	static DefaultTableModel model;
	static int cols;
	static int rowCount;
	static JLabel lblNumRec;
	
	static JScrollPane scrollPane;
	
	
	 static DashBoard dbpm = new DashBoard();
	 
	 
	// static String stxtSearch;

	/**
	 * Create the panel.
	 */
	public DListEnrolled() {
		setBackground(Color.WHITE);
        setBounds(291, 51, 680, 600);
        setLayout(null);
        
        JLabel lblNewLabel = new JLabel("Dashboard");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblNewLabel.setBounds(10, 11, 100, 14);
        add(lblNewLabel);
        
        JLabel lblListOfEnrolled = new JLabel("List of Enrolled Students:");
        lblListOfEnrolled.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblListOfEnrolled.setBounds(10, 49, 210, 14);
        add(lblListOfEnrolled);
        
        JLabel lblSearch = new JLabel("Search:");
        lblSearch.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblSearch.setBounds(10, 74, 70, 20);
        add(lblSearch);
        
        txtSearch = new JTextField();
        txtSearch.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		processSearch();
        	}
        });
        txtSearch.setBounds(67, 74, 276, 22);
        add(txtSearch);
        txtSearch.setColumns(10);
        
        JLabel lblListOfEnrolled_1 = new JLabel("");
        lblListOfEnrolled_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblListOfEnrolled_1.setBounds(230, 49, 210, 14);
        add(lblListOfEnrolled_1);
        
        JLabel lblNewLabel_1 = new JLabel("Total Records(s) in database are: ");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblNewLabel_1.setBounds(10, 562, 238, 27);
        add(lblNewLabel_1);
        
        lblNumRec = new JLabel();
        lblNumRec.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblNumRec.setBounds(231, 562, 112, 27);
        add(lblNumRec);
        

        tableEnrolled = new JTable();
        tableEnrolled.setBounds(10, 127, 660, 424);
        add(tableEnrolled);
        
        scrollPane = new JScrollPane();
        scrollPane.addHierarchyListener(new HierarchyListener() {
        	public void hierarchyChanged(HierarchyEvent e) {
        		populateTable();
        	}
        });
       // scrollPane.setBounds(10, 188, 660, 363);
        scrollPane.setBounds(10, 127, 660, 424);
        add(scrollPane);
        
        
        
        
        
	}
	
	
/*
	public static void populateTable() {
		try {
			TheConnection dbcon = new TheConnection();
			dbcon.connect_db();
			
		    scrollPane.setViewportView(tableEnrolled);

			Statement st = dbcon.conn.createStatement();
			
			String populateCourses = "SELECT CONCAT(StudentFirstName, ' ',StudentLastName), StudCourse, TeacherFullName FROM dbjnc6.tblstudent;";
				
			ResultSet rs = st.executeQuery(populateCourses);
			
			ResultSetMetaData rsmd = rs.getMetaData();
			
			model = new DefaultTableModel();
			tableEnrolled.setModel(model);
			
			cols = rsmd.getColumnCount();
			
			String[] colName = new String[cols];
			
			for(int i = 0; i < cols; i++) {
				colName[i] = rsmd.getColumnName(i+1);
				model.setColumnIdentifiers(colName);
			}
			
			tableEnrolled.getColumnModel().getColumn(0).setHeaderValue("Student Fullname");
			tableEnrolled.getColumnModel().getColumn(1).setHeaderValue("Course Name");
			tableEnrolled.getColumnModel().getColumn(2).setHeaderValue("Advisor");
			
		
			model.setRowCount(0); 
			
			String CourseID, CourseName, YearDuration;
			while(rs.next()) {
				CourseID = rs.getString(1);
				CourseName = rs.getString(2);
				YearDuration = rs.getString(3);
				String row[] = {CourseID, CourseName, YearDuration};
				model.addRow(row);
			}
			
			//add line
			st.close();
			dbcon.conn.close();
			
			rowCount = tableEnrolled.getRowCount();
			lblNumRec.setText(String.valueOf(rowCount));
			
		}catch(Exception e) {
			
		}
	}	
	*/
	

	
	
	
	public void processSearch() {
		try {
			TheConnection dbcon = new TheConnection();
			dbcon.connect_db();

		    scrollPane.setViewportView(tableEnrolled);
		    
		   // Statement st = dbcon.conn.createStatement();
		    
		    String strtxtSearch = "%"+txtSearch.getText()+"%";
		   
		    
		    String createTempSearchTable  = "CREATE TEMPORARY TABLE tempsearch AS SELECT * FROM dbjnc6.tempEnrolled WHERE StudFullName LIKE ?";
		    
			PreparedStatement pst = dbcon.conn.prepareStatement(createTempSearchTable );
				
			pst.setString(1, strtxtSearch);
			
			//int rowsInserted = st.executeUpdate();
			
			int rowsInserted = pst.executeUpdate();
			
			if(rowsInserted<=0) {

				JLabel noRecordsLabel = new JLabel("No records found");
		        noRecordsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		        JScrollPane noRecordsScrollPane = new JScrollPane(noRecordsLabel);
		        scrollPane.setViewportView(noRecordsScrollPane);
			}else {
				
				Statement st = dbcon.conn.createStatement();

		    	String populatetempsearch = "SELECT * FROM tempsearch";
				ResultSet rstempsearch = st.executeQuery(populatetempsearch);
				
				ResultSetMetaData rsmdtempsearch = rstempsearch.getMetaData();
				
				model = new DefaultTableModel();
				tableEnrolled.setModel(model);
				
				cols = rsmdtempsearch.getColumnCount();
				
				String[] colName = new String[cols];
				
				for(int i = 0; i < cols; i++) {
					colName[i] = rsmdtempsearch.getColumnName(i+1);
					model.setColumnIdentifiers(colName);
				}
				
				tableEnrolled.getColumnModel().getColumn(0).setHeaderValue("ID");
				tableEnrolled.getColumnModel().getColumn(1).setHeaderValue("Student Fullname");
				tableEnrolled.getColumnModel().getColumn(2).setHeaderValue("Course Name");
				tableEnrolled.getColumnModel().getColumn(3).setHeaderValue("Advisor");
				
			
				model.setRowCount(0); 
				
				String sid, fname, course, teacher;
				while(rstempsearch.next()) {
					sid = rstempsearch.getString(1);
					fname = rstempsearch.getString(2);
					course = rstempsearch.getString(3);
					teacher = rstempsearch.getString(4);
					String row[] = {sid, fname, course, teacher};
					model.addRow(row);
				}
				
				int rowc = model.getRowCount();
				String strrowc = String.valueOf(rowc);
				
				lblNumRec.setText(strrowc);
				
				st.close();
				dbcon.conn.close();

			}

		}catch(Exception e) {
			e.printStackTrace();		
		}    
	}
	

	public static void populateTable() {
		try {
			TheConnection dbcon = new TheConnection();
			dbcon.connect_db();
			
			//dbpm.pm("/checkIcon.png", "Data Loaded");
			
		    scrollPane.setViewportView(tableEnrolled);
		    
		    //delete table if meron
		    Statement stmtdropTbl = dbcon.conn.createStatement();
		    String dropTbl = "DROP TABLE IF EXISTS tempEnrolled";
		    stmtdropTbl.executeUpdate(dropTbl);
		    
		    
		    
		    //create table
		    String createTempTable = "CREATE TABLE tempEnrolled AS SELECT tblstudent.ID, CONCAT(tblstudent.StudentFirstName, ' ', tblstudent.StudentLastName) AS StudFullName, tblstudent.StudCourse, tblstudent.TeacherFullName FROM dbjnc6.tblstudent";
            
		    Statement stcreateTempTable = dbcon.conn.createStatement();

            int result = stcreateTempTable.executeUpdate(createTempTable);
            
            
		    if(result > 0) {
		    	//select from the created table
		    	Statement st = dbcon.conn.createStatement();

		    	String populateCourses = "SELECT * FROM tempEnrolled";
				ResultSet rs = st.executeQuery(populateCourses);
				
				ResultSetMetaData rsmd = rs.getMetaData();
				
				model = new DefaultTableModel();
				tableEnrolled.setModel(model);
				
				cols = rsmd.getColumnCount();
				
				String[] colName = new String[cols];
				
				for(int i = 0; i < cols; i++) {
					colName[i] = rsmd.getColumnName(i+1);
					model.setColumnIdentifiers(colName);
				}
				
				tableEnrolled.getColumnModel().getColumn(0).setHeaderValue("ID");
				tableEnrolled.getColumnModel().getColumn(1).setHeaderValue("Student Fullname");
				tableEnrolled.getColumnModel().getColumn(2).setHeaderValue("Course Name");
				tableEnrolled.getColumnModel().getColumn(3).setHeaderValue("Advisor");
				
			
				model.setRowCount(0); 
				
				String sid, fname, course, teacher;
				while(rs.next()) {
					sid = rs.getString(1);
					fname = rs.getString(2);
					course = rs.getString(3);
					teacher = rs.getString(4);
					String row[] = {sid, fname, course, teacher};
					model.addRow(row);
				}
				
				//add line
				st.close();
				dbcon.conn.close();
				
				int rowc = model.getRowCount();
				String strrowc = String.valueOf(rowc);
				
				lblNumRec.setText(strrowc);
		    	
		    }

			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}		
	
	
	
/*
	public static void populateTable() {
	    try {
	        TheConnection dbcon = new TheConnection();
	        dbcon.connect_db();

	        try (Statement st = dbcon.conn.createStatement()) {

	            String createTempTable = "CREATE TEMPORARY TABLE tempEnrolled AS SELECT ID, CONCAT(tblstudent.StudentFirstName, ' ', tblstudent.StudentLastName) AS StudFullName, tblstudent.StudCourse, tblstudent.TeacherFullName FROM dbjnc6.tblstudent";
	            
	            int result = st.executeUpdate(createTempTable);

	            if (result > 0) {
	                String populateCourses = "SELECT * FROM dbjnc6.tempEnrolled";
	                try (ResultSet rs = st.executeQuery(populateCourses)) {
	                    ResultSetMetaData rsmd = rs.getMetaData();

	                    model = new DefaultTableModel();
	                    tableEnrolled.setModel(model);

	                    int cols = rsmd.getColumnCount();

	                    String[] colName = new String[cols];

	                    for (int i = 0; i < cols; i++) {
	                        colName[i] = rsmd.getColumnName(i + 1);
	                        model.setColumnIdentifiers(colName);
	                    }

	                    tableEnrolled.getColumnModel().getColumn(0).setHeaderValue("ID");
	                    tableEnrolled.getColumnModel().getColumn(1).setHeaderValue("Student Fullname");
	                    tableEnrolled.getColumnModel().getColumn(2).setHeaderValue("Course Taken");
	                    tableEnrolled.getColumnModel().getColumn(3).setHeaderValue("Advisor");

	                    model.setRowCount(0);

	                    while (rs.next()) {
	                        String id = rs.getString(1);
	                        String fullname = rs.getString(2);
	                        String course = rs.getString(3);
	                        String teacher = rs.getString(4);
	                        String[] row = {id, fullname, course, teacher};
	                        model.addRow(row);
	                    }
	                }
	            } else {
	                System.out.println("Failed to create temporary table.");
	            }
	        } // Automatically closes the Statement
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}*/


	
	
	
	
	
}
