package se.kth.db;


import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

public class Concurrency01 {

	static void printStudents() throws SQLException {
		Connection con = DriverManager.getConnection("jdbc:hsqldb:file:" + Conf.connectionstr);

		Statement stmt = con.createStatement();
	    ResultSet rs = stmt.executeQuery("SELECT * FROM Students");
	    
	    while (rs.next()) {
	        int pn = rs.getInt("personnumber");
	        System.out.print("personnumber " + pn);
	        
	        String name = rs.getString("name");
	        System.out.print(" name " + name);
	        name = rs.getString(2);
	        System.out.print(" name " + name);
	        
	        String surname = rs.getString("surname");
	        System.out.print(" surname " + surname);

	        String nationality = rs.getString("nationality");
	        System.out.print(" nationality " + nationality);

	        System.out.print(" weight " + rs.getInt("weight"));
	        System.out.print(" birthday " + rs.getDate("birthday"));
	        
	        Calendar c = Calendar.getInstance();
	        c.setTime(rs.getDate("birthday"));
	        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
	        System.out.print(" born on " + dayOfWeek);
	        
	        System.out.println("");
	    }
	    stmt.close();
	    con.close();
	}
	
	static void update() throws SQLException {
		Connection con = DriverManager.getConnection("jdbc:hsqldb:file:" + Conf.connectionstr);
		con.setAutoCommit(false);
		try {
			Statement stmt = con.createStatement();
			System.out.println("Adding frist student");
			stmt.executeUpdate("UPDATE STUDENTS SET name='Erik2' WHERE personnumber=1012");
			stmt = con.createStatement();
			System.out.println("Adding second student");
			stmt.executeUpdate("UPDATE STUDENTS SET name='Erik3' WHERE personnumber=1013");
			con.commit();
		} catch (Exception e ) {
			System.out.println("Error");
			con.rollback();
		}
		con.close();
	}
	
	public static void main(String[] args) throws SQLException {
		printStudents();
		System.out.println("---------------------------------------");
		
		Thread t1 = new Thread(){
			public void run() {
				try {
					update();
					printStudents();
				} catch (SQLException e) {
				}
			};
		};
		
		Thread t2 = new Thread(){
			public void run() {
				try {
					update();
					printStudents();
				} catch (SQLException e) {
				}
			};
		};
		
		t1.start();
		t2.start();
		
	}

}
