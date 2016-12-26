package se.kth.sb.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Vector;

import se.kth.sb.model.Student;

public class StudentDAO {
	private Connection openConnection() throws SQLException {
		return DriverManager.getConnection("jdbc:hsqldb:file:~/Sources/ref/hello1");
	}
	
	public void create(Student student) throws SQLException {
		Connection con = openConnection();
		PreparedStatement stmt = null;
		try {
			stmt = con.prepareStatement("INSERT INTO students VALUES (?, ?, ?, ?, ?, ?, ?)");
			stmt.setInt(1, student.personnumber);
			stmt.setString(2, student.name);
			stmt.setString(3, student.surname);
			stmt.setDate(4, student.birthday);
			stmt.setInt(5, student.height);
			stmt.setInt(6, student.weight);
			stmt.setString(7, student.nationality);
			
			
			stmt.executeUpdate();
		} catch (SQLException e) {
		} finally {
			if (stmt != null)
				stmt.close();
			con.close();
		}
	}
	
	public Student read(int personnumber) throws SQLException {
		Connection con = openConnection();
		PreparedStatement stmt = null;
		Student res = null;
		try {
			stmt = con.prepareStatement("SELECT * FROM Students WHERE personnumber=?");
			stmt.setInt(1,  personnumber);
			ResultSet rs = stmt.executeQuery();
		    
			if (!rs.next()) {
				System.out.println("Student not found");
			    stmt.close();
			    rs.close();
				return null;
			}				
			
			res = new Student(
					personnumber,
					rs.getString("name"),
					rs.getString("surname"),
					rs.getString("nationality"),
					rs.getDate("birthday"),
					rs.getInt("weight"),
					rs.getInt("height")
			);
		} catch (SQLException e) {    
		} finally {
			if (stmt != null)
				stmt.close();
			con.close();
		}
		return res;
	}
	public void update(Student student) throws SQLException {
		Connection con = openConnection();
		PreparedStatement stmt = null;
		try {		
			stmt = con.prepareStatement("UPDATE students SET name=?, surname=?, nationality=?, weight=?, height=? WHERE personnumber=?");
			stmt.setString(1, student.name);
			stmt.setString(2, student.surname);
			stmt.setString(3, student.nationality);
			stmt.setInt(4, student.weight);
			stmt.setInt(5, student.height);
			stmt.setInt(6, student.personnumber);
			
			stmt.executeUpdate();
		} catch (SQLException e) {    
		} finally {
			if (stmt != null)
				stmt.close();
			con.close();
		}
	}
	public void delete(int personnumber) throws SQLException {		
		Connection con = openConnection();
		PreparedStatement stmt = null;
		try {		
			stmt = con.prepareStatement("DELETE FROM students WHERE personnumber=?");
			stmt.setInt(1, personnumber);
			stmt.executeUpdate();
		} catch (SQLException e) {    
		} finally {
			if (stmt != null)
				stmt.close();
			con.close();
		}
	}
	public List<Student> list() throws SQLException {
		List<Student> res = new Vector<Student>();
		Connection con = openConnection();
		Statement stmt = null;
		try {
			stmt = con.createStatement();
		    ResultSet rs = stmt.executeQuery("SELECT personnumber, name, surname, nationality FROM PUBLIC.Students");
		    
		    while (rs.next()) {
		    	Student student = new Student(
		    			rs.getInt("personnumber"),
		    			rs.getString("name"),
		    			rs.getString("surname"),
		    			rs.getString("nationality")
		    	);
		    	res.add(student);
		    }
		} catch (SQLException e) {
		} finally {
			if (stmt != null)
				stmt.close();
			con.close();
		}
		return res;
	}
}
