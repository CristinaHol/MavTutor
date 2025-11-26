package session;
import java.util.Objects;
import java.util.Scanner;
import java.io.PrintStream;

/**
 * Represents a college course with a department and course number.
 * Courses are identified by a combination of the department and number.
 */

public class Course{

	/**
	 * Constructs a new Course with the given department and number.
	 * 
	 * @param dept  	the department code, ex. "CSE"
	 * @param number  	the course number, ex. "1325"
	 */

	public Course(String dept, int number){
		if(dept == null || (dept.length() != 3 && dept.length() != 4)) throw new InvalidCourseException(dept);
		if(number < 1000 || number > 9999) throw new InvalidCourseException(dept, number);

		this.dept = dept;
		this.number = number;
	}

	public Course(Scanner in){
		this.dept = in.nextLine();
		this.number = Integer.parseInt(in.nextLine());//in.nextInt(); in.nextLine();
	}

	private String dept;
	private int number;

	/**
	 * Returns a string representation of this course in the format "DEPT COURSE".
	 * Example: "CSE 1325".
	 * 
	 * @return a formated string representing this course
	 */

	@Override
	public String toString(){
		return dept + number;
	}

	/**
	 * Compares this course to another object for equality.
	 * Two courses are considered equal if they have the same department and course number.
	 *  
	 * @param o 		the object to compare with
	 * @return true 	if obj is a COurse with the same dept and number, false otherwise
	 */

	@Override
	public boolean equals(Object o){
		if(this == o) return true;
		if(o == null || o.getClass() != getClass() ) return false;

		Course c = (Course) o;
		return dept.equals(c.dept) && c.number == number;
	}

	/**
	 * Returns a hashcode value for this course.
	 * The hash code is base on the department and course number;
	 * 
	 * @return an integer hash code for this course
	 */

	@Override
	public int hashCode(){
		return Objects.hash(dept, number);
	}

	public void save(PrintStream out){
		out.println(dept);
		out.println("" + number);
	}
}