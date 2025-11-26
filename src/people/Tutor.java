package people;
import session.Course;

import java.util.Scanner;
import java.io.PrintStream;

public class Tutor extends Person{

	public Tutor(String name, String email, int ssn, String bio, Course course){
		super(name, email);
		if(ssn < 001_01_0001 || ssn > 999_99_9999) throw new IllegalArgumentException("Invalid SSN: " + ssn);
		this.ssn = ssn;
		this.bio = bio;
		this.course = course; 

	}

	public Tutor(Scanner in){
		super(in);
		this.ssn = Integer.parseInt(in.nextLine());//in.nextInt(); in.nextLine();
		this.bio = in.nextLine();
		this.course = new Course(in);
	}

	private String bio = "";
	private int ssn;
	private Course course;

	public int getSSN(){
		return ssn;
	}
	public String getBio(){
		return bio;
	}
	public Course getCourse(){
		return course;
	}

	public void save(PrintStream out){
		super.save(out);
		out.println("" + ssn);
		out.println("" + bio);
		course.save(out);

	}

}