package se.kth.db;


import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

public class Injection02 {

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
	
	public static void main(String[] args) {
		try {
			printStudents();
			
			String nationality = "USA'); DELETE FROM students; --";
			
			Connection con = DriverManager.getConnection("jdbc:hsqldb:file:" + Conf.connectionstr);
			Statement stmt = con.createStatement();
			System.out.println("Adding frist student");
			stmt.executeUpdate("INSERT INTO students VALUES (1, 'Erik', 'Doe', '1985-11-01', 192, 55, '"+nationality+"')");
			stmt.close();
			con.close();
			
			printStudents();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
