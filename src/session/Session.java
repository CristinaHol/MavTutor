package session;

import people.Tutor;
import people.Student;
import rating.Rateable;
import rating.Rating;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.PrintStream;

/**
 * Represents a tutoring session for a specific course with a tutor.
 */
public class Session implements Rateable{

	/**
	 *  Constructs a Session for a given course and tutor
	 *  @param course the Course being tutored
	 *  @param tutor the Tutor leading the session
	 */
	public Session(Course course, Tutor tutor){
		this.course = course;
		this.tutor = tutor;
		this.students = new ArrayList<Student>();
		this.ratings = new ArrayList<Rating>();

	}

	public Session(Scanner in){
		this.course = new Course(in);
		this.dates = new DateRange(in);
		this.tutor = new Tutor(in);
		int size = Integer.parseInt(in.nextLine());
		this.students = new ArrayList<>();
		for(int i = 0; i < size; ++i){
			Student s = new Student(in);
			this.students.add(s);
		}
	}

	private Course course;
	private DateRange dates;
	private Tutor tutor;
	private List<Student> students;
	private List<Rating> ratings;

	/**
	 *  Sets the schedule of the session using a start time and duration.
	 *  @param date 		the date of the session
	 *  @param startTime 	the start time in HH:MM
	 *  @param duration		the duration in minutes
	 * 
	 */
	public void setSchedule(String date, String startTime, long duration){
		this.dates = new DateRange(date, startTime, duration);
	}

	/**
	 * Adds a student to the session
	 * @param student 	the student to add
	 */
	public void addStudent(Student student){
		students.add(student);
	}

	public void addRating(Rating rating){
		ratings.add(rating);
	}

	public double getAverageRating(){
		if(ratings.isEmpty()){
			return Double.NaN;
		}
		double total = 0.0;
		for(Rating r : ratings){
			total += r.getStars();
		}
		return total / ratings.size();
	}

	public Rating[] getRatings(){
		return ratings.toArray(new Rating[0]);
	}

	/**
	 * Returns a string representation of this session, including the course, schedule, and enrolled students.
	 * 
	 * Example:
	 * <pre>
	 * Session course at YYYY:MM:DD
	 * Tutor: jane doe
	 * Students: Student 1, Student 2
	 * </pre>
	 * 
	 * @return a formatted string representing this session
	 */

	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();

		sb.append("Session ")
		  .append(course)
		  .append(" at ")
		  .append(dates)
		  .append("\n");

		sb.append("Tutor: ")
		  .append(tutor)
		  .append("\n");

		sb.append("Students: ");
		if(students.isEmpty()){
			sb.append("None");
		} else{
			for(int i = 0; i < students.size(); ++i){
				sb.append(students.get(i));
				if(i < students.size() - 1) sb.append(", ");
			}
		}

		return sb.toString();
	}

	public void save(PrintStream out){
		course.save(out);
		dates.save(out);
		tutor.save(out);
		out.println("" +students.size());
		for(Student s : students){
			s.save(out);
		}
	}

}