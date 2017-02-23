package se.kth.sb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

public class Query4 {

	public static void main(String[] args) throws SQLException {
		Connection con = DriverManager.getConnection("jdbc:hsqldb:file:" + Conf.connectionstr);

		PreparedStatement stmt = con.prepareStatement(""
	    		+ "SELECT name, weight*100/height as rat "
	    		+ "FROM Students "
	    		+ "WHERE nationality = ?");
	    stmt.setString(1, "Swedish");
	    
	    ResultSet rs = stmt.executeQuery();
	    
	    while (rs.next()) {
	        String name = rs.getString("name");
	        System.out.print(" name " + name);
	        
	        int ratio = rs.getInt("rat");
	        System.out.print(" ratio " + ratio);

	        System.out.println("");
	    }
	    stmt.close();
	    con.close();
	}

}
