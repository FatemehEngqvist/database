package se.kth.sb;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

public class CRUD1 {

	private static Scanner scanner;

	public static void main(String[] args) throws SQLException, ParseException {
		scanner = new Scanner(System.in);
		
		while (true) {
			printList();
			printHelp();
		
			String command = scanner.nextLine();
			if ("c".equals(command))
				create();
			else if ("r".equals(command))
				read();
			else if ("u".equals(command))
				update();
			else if ("d".equals(command))
				delete();
		}
	}

	private static void delete() throws SQLException {
		System.out.print("Enter personnumer to delete: ");
		int personnumber = scanner.nextInt();
		
		Connection con = openConnection();
		PreparedStatement stmt = con.prepareStatement("DELETE FROM students WHERE personnumber=?");
		stmt.setInt(1, personnumber);
		stmt.executeUpdate();
		stmt.close();
		con.close();
	}

	private static Connection openConnection() throws SQLException {
		return DriverManager.getConnection("jdbc:hsqldb:file:" + Conf.connectionstr);
	}

	private static void update() throws SQLException {
		System.out.print("Enter personnumer to update: ");
		int personnumber = scanner.nextInt();
		scanner.nextLine();
		System.out.print("Enter name: ");
		String name = scanner.nextLine();
		System.out.print("Enter surname: ");
		String surname = scanner.nextLine();
		System.out.print("Enter nationality: ");
		String nationality = scanner.nextLine();
		System.out.print("Enter weight: ");
		int weight = scanner.nextInt();
		System.out.print("Enter height: ");
		int height = scanner.nextInt();
		
		Connection con = openConnection();
		
		
		PreparedStatement stmt = con.prepareStatement("UPDATE students SET name=?, surname=?, nationality=?, weight=?, height=? WHERE personnumber=?");
		stmt.setString(1, name);
		stmt.setString(2, surname);
		stmt.setString(3, nationality);
		stmt.setInt(4, weight);
		stmt.setInt(5, height);
		stmt.setInt(6, personnumber);
		
		stmt.executeUpdate();
		stmt.close();
		con.close();
	}

	private static void read() throws SQLException {
		System.out.print("Enter personnumer to read: ");
		int personnumber = scanner.nextInt();
		scanner.nextLine();
		
		Connection con = openConnection();
		PreparedStatement stmt = con.prepareStatement("SELECT * FROM Students WHERE personnumber=?");
		stmt.setInt(1,  personnumber);
		ResultSet rs = stmt.executeQuery();
	    
		System.out.println("name: " + rs.getString("name"));
		System.out.println("surname: " + rs.getString("surname"));
		System.out.println("nationality: " + rs.getString("nationality"));
		System.out.println("birthday: " + rs.getDate("birthday"));
		System.out.println("weight: " + rs.getInt("weight"));
		System.out.println("height: " + rs.getInt("height"));
	    
	    stmt.close();
	    rs.close();
	}

	private static void create() throws SQLException, ParseException {
		System.out.print("Enter personnumer to crate: ");
		int personnumber = scanner.nextInt();
		scanner.nextLine();
		System.out.print("Enter name: ");
		String name = scanner.nextLine();
		System.out.print("Enter surname: ");
		String surname = scanner.nextLine();
		System.out.print("Enter nationality: ");
		String nationality = scanner.nextLine();
		System.out.print("Enter birthdate: ");
		String birthString = scanner.nextLine();
		Date birthday = new java.sql.Date(new SimpleDateFormat("yyyy-mm-dd").parse(birthString).getTime());
		System.out.print("Enter weight: ");
		int weight = scanner.nextInt();
		System.out.print("Enter height: ");
		int height = scanner.nextInt();
		
		Connection con = openConnection();
		
		
		PreparedStatement stmt = con.prepareStatement("INSERT INTO students VALUES (?, ?, ?, ?, ?, ?, ?)");
		stmt.setInt(1, personnumber);
		stmt.setString(2, name);
		stmt.setString(3, surname);
		stmt.setDate(4, birthday);
		stmt.setInt(5, height);
		stmt.setInt(6, weight);
		stmt.setString(7, nationality);
		
		stmt.executeUpdate();
		stmt.close();
		con.close();
	}

	private static void printList() throws SQLException {
		Connection con = openConnection();
		Statement stmt = con.createStatement();
	    ResultSet rs = stmt.executeQuery("SELECT personnumber, name, surname, nationality FROM PUBLIC.Students");
	    
	    while (rs.next()) {
	        int pn = rs.getInt("personnumber");
	        System.out.print(pn);
	        
	        String name = rs.getString("name");
	        System.out.print(" | " + name);
	        
	        String surname = rs.getString("surname");
	        System.out.print(" | " + surname);

	        String nationality = rs.getString("nationality");
	        System.out.print(" | " + nationality);

	        System.out.println("");
	    }
	    
	    stmt.close();
	    rs.close();
	}

	private static void printHelp() {
		System.out.println("Help");
		System.out.println("c - Create a new student");
		System.out.println("r - Read a student");
		System.out.println("u - Update a student");
		System.out.println("d - Delete a student");
	}

}
