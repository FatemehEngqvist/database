package se.kth.sb.model;

import java.sql.Date;

public class Student {
	public int personnumber;
	public String name;
	public String surname;
	public String nationality;
	public int weight;
	public int height;
	public Date birthday;
	
	public Student(int personnumber, String name, String surname, String nationality, Date birthday, int weight, int height) {
		super();
		this.personnumber = personnumber;
		this.name = name;
		this.surname = surname;
		this.nationality = nationality;
		this.birthday = birthday;
		this.weight = weight;
		this.height = height;
	}

	public Student(int personnumber, String name, String surname, String nationality) {
		super();
		this.personnumber = personnumber;
		this.name = name;
		this.surname = surname;
		this.nationality = nationality;
	}
}
