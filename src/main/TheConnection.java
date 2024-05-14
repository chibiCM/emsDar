package main;

import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.ResultSet;


public class TheConnection {
	//static String isconnected = "";

	                
	public String dbURL = "jdbc:mysql://localhost:3306/dbjnc6";
	public String userName = "root";
	public String passWord = "Javaisheart@2023";
	public Connection conn;

	public void connect_db() {
		try {
			conn = DriverManager.getConnection(dbURL,userName,passWord);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/*public void connection_db() {
		try {
			Connection conn = DriverManager.getConnection(dbURL,userName,passWord);
			if(conn != null) {
				System.out.println("db connected");
				isconnected = "Yes";
			}else {
				System.out.println("dn not connected");
				isconnected = "No";
			}
		}catch(Exception ex) {
			isconnected = "isconnected";
			//ex.printStackTrace();
		}
	}
	
	
	
	public void method(){
		System.out.print(isconnected);
	}*/
	
	
	
}