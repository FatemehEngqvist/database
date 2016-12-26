package se.kth.sb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

public class Delete1 {

	public static void main(String[] args) throws SQLException {
		Connection con = DriverManager.getConnection("jdbc:hsqldb:file:~/Sources/ref/hello1");

		PreparedStatement stmt = con.prepareStatement("DELETE FROM students WHERE personnumber=?");
		stmt.setInt(1, 101);
		stmt.executeUpdate();
		stmt.close();
		con.close();
	}

}
