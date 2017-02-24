package se.kth.db;


import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

public class Concurrency02 {
	public static void main(String[] args) throws SQLException {
		Thread t1 = new Thread(){
			public void run() {
				try {
					Connection con = DriverManager.getConnection("jdbc:hsqldb:file:" + Conf.connectionstr);
					con.setAutoCommit(false);
					try {
						Statement stmt = con.createStatement();
						System.out.println("Adding frist student");
						stmt.executeUpdate("UPDATE STUDENTS SET name='Erik3' WHERE personnumber=1013");
						stmt = con.createStatement();
						System.out.println("Adding second student");
						stmt.executeUpdate("UPDATE AGENTS SET agent_name='Erik2' WHERE agent_code='A001'");
						con.commit();
					} catch (Exception e ) {
						System.out.println("Error");
						con.rollback();
					}
					con.close();
				} catch (SQLException e) {
				}
			};
		};
		
		Thread t2 = new Thread(){
			public void run() {
				try {
					Connection con = DriverManager.getConnection("jdbc:hsqldb:file:" + Conf.connectionstr);
					con.setAutoCommit(false);
					try {
						Statement stmt = con.createStatement();
						System.out.println("Adding frist student");
						stmt.executeUpdate("UPDATE AGENTS SET agent_name='Erik2' WHERE agent_code='A001'");
						stmt = con.createStatement();
						System.out.println("Adding second student");
						stmt.executeUpdate("UPDATE STUDENTS SET name='Erik3' WHERE personnumber=1013");
						con.commit();
					} catch (Exception e ) {
						System.out.println("Error");
						con.rollback();
					}
					con.close();
				} catch (SQLException e) {
				}
			};
		};
		
		t1.start();
		t2.start();
		
	}

}
