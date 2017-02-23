package se.kth.sb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

public class Update1 {

	public static void main(String[] args) throws SQLException {
		Connection con = DriverManager.getConnection("jdbc:hsqldb:file:" + Conf.connectionstr);

		PreparedStatement stmt = con.prepareStatement("UPDATE students SET surname=? where personnumber=?");
		stmt.setString(1,  "Jordan");
		stmt.setInt(2, 1011);
		stmt.executeUpdate();
		stmt.close();
		con.close();
	}

}
