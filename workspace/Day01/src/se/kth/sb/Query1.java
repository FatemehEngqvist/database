package se.kth.sb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

public class Query1 {

	public static void main(String[] args) throws SQLException {
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

	        /*
	        String nationality1 = rs.getString("nationality1");
	        System.out.print(" nationality " + nationality1);
	        */
	     
	        System.out.print(" weight " + rs.getInt("weight"));
	        //System.out.print(" other " + rs.getInt("name"));
	        System.out.print(" birthday " + rs.getDate("birthday"));
	        
	        Calendar c = Calendar.getInstance();
	        //c.setTime(rs.getString("birthday"));
	        c.setTime(rs.getDate("birthday"));
	        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
	        System.out.print(" born on " + dayOfWeek);
	        
	        System.out.println("");
	    }
	    stmt.close();
	    con.close();
	}

}
