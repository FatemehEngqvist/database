package se.kth.sb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

public class Insert1 {

	public static void main(String[] args) throws SQLException {
		Connection con = DriverManager.getConnection("jdbc:hsqldb:file:~/Sources/ref/hello1");

		Statement stmt = con.createStatement();
				
		stmt.executeUpdate("INSERT INTO students VALUES (1010, 'Erik', 'Doe', '1985-11-01', 192, 55, 'USA')");
		stmt.close();
		con.close();
	}

}
