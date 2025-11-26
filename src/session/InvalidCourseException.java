package session;

/**
 * Exception thrown when an invalid course is created or accessed.
 * Typically used to signal that a department ir course number is not recognized.
 */
public class InvalidCourseException extends IllegalArgumentException{
	/**
	 * Constructs an invalidCourseException for an invalid department.
	 * 
	 * @param dept 		the invalid deptartment code
	 */
	public InvalidCourseException(String dept) {super("invalid dept in new Course: " + dept);} 
	/**
	 * Constructs an InvalidCourseException for an invalid department and number.
	 * 
	 * @param dept 		the invalid department code
	 * @param number 	the invalid course number
	 */
	public InvalidCourseException(String dept, int number){
		super("Invalid course number in new Course: " + number);
	}
}