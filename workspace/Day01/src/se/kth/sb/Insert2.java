package se.kth.sb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Insert2 {

	public static void main(String[] args) throws SQLException, ParseException {
		Connection con = DriverManager.getConnection("jdbc:hsqldb:file:" + Conf.connectionstr);

		PreparedStatement stmt = con.prepareStatement("INSERT INTO students VALUES (?, ?, ?, ?, ?, ?, ?)");
		stmt.setInt(1, 1011);
		stmt.setString(2, "Angela");
		stmt.setString(3, "Merkel");
		stmt.setDate(4,  new java.sql.Date(new SimpleDateFormat("yyyy-mm-dd").parse("1965-08-11").getTime()));
		stmt.setInt(5, 160);
		stmt.setInt(6, 65);
		stmt.setString(7, "German");
		
		stmt.executeUpdate();
		stmt.close();
		con.close();
	}

}
