package se.kth.sb;

import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import se.kth.sb.controller.StudentDAO;
import se.kth.sb.model.Student;

public class CRUD3 {

	private static Scanner scanner;
	private static StudentDAO studentDAO;

	public static void main(String[] args) throws SQLException {
		scanner = new Scanner(System.in);
		studentDAO = new StudentDAO();
		
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
		studentDAO.delete(personnumber);
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
		System.out.print("Enter birthday: ");
		String birthString = scanner.nextLine();
		Date birthday;
		try {
			birthday = new java.sql.Date(new SimpleDateFormat("yyyy-mm-dd").parse(birthString).getTime());
		} catch (ParseException e1) {
			return;
		}
		System.out.print("Enter weight: ");
		int weight = scanner.nextInt();
		System.out.print("Enter height: ");
		int height = scanner.nextInt();
		
		Student student = new Student(personnumber, name, surname, nationality, birthday, weight, height);
		studentDAO.update(student);
	}

	private static void read() throws SQLException {
		System.out.print("Enter personnumer to read: ");
		int personnumber = scanner.nextInt();
		scanner.nextLine();
		
		Student student = studentDAO.read(personnumber);

		System.out.println("name: " + student.name);
		System.out.println("surname: " + student.surname);
		System.out.println("nationality: " + student.nationality);
		System.out.println("birthday: " + student.birthday);
		System.out.println("weight: " + student.weight);
		System.out.println("height: " + student.height);
	}

	private static void create() throws SQLException {
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
		Date birthday;
		try {
			birthday = new java.sql.Date(new SimpleDateFormat("yyyy-mm-dd").parse(birthString).getTime());
		} catch (ParseException e1) {
			return;
		}
		System.out.print("Enter weight: ");
		int weight = scanner.nextInt();
		System.out.print("Enter height: ");
		int height = scanner.nextInt();
		
		Student student = new Student(personnumber, name, surname, nationality, birthday, weight, height);
		studentDAO.create(student);
	}

	private static void printList() throws SQLException {
		List<Student> students = studentDAO.list();
		Iterator<Student> it = students.iterator();
		while (it.hasNext()) {
			Student student = it.next();
	        System.out.print(student.personnumber);
	        System.out.print(" | " + student.name);
	        System.out.print(" | " + student.surname);
	        System.out.print(" | " + student.nationality);

	        System.out.println("");
		}
	}

	private static void printHelp() {
		System.out.println("Help");
		System.out.println("c - Create a new student");
		System.out.println("r - Read a student");
		System.out.println("u - Update a student");
		System.out.println("d - Delete a student");
	}
}
