package mdi;
import people.*;
import rating.*;
import session.*;
import menu.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import java.io.PrintStream;
import java.io.File;


public class MavTutor{

	private Menu menu;
	private List view;

	private ArrayList<Course> courses;
	private ArrayList<Student> students;
	private ArrayList<Tutor> tutors;
	private ArrayList<Session> sessions;
	private File file;
	



	public MavTutor(String[] args){
		if (args.length == 0 || !args[0].equalsIgnoreCase("skip")) {
            showSplashScreen();
        }
		courses = new ArrayList<>();
		students = new ArrayList<>();
		tutors = new ArrayList<>();
		sessions = new ArrayList<>();
		view = new ArrayList<>();


		buildMenu();
		menu.run();

	}
	public MavTutor(){
		this(new String[0]);
	}

	private void showSplashScreen(){
		String splash = """
       .--.                   .---.
   .---|__|           .-.     |~~~|
.--|===|--|_          |_|     |~~~|--.
|  |===|  |'\\     .---!~|  .--|   |--|
|%%|   |  |.'\\    |===| |--|%%|   |  |
|%%|   |  |\\.'\\   |   | |__|  |   |  |
|  |   |  | \\  \\  |===| |==|  |   |  |
|  |   |__|  \\.'\\ |   |_|__|  |~~~|__|
|  |===|--|   \\.'\\|===|~|--|%%|~~~|--|
^--^---'--^    `-'`---^-^--^--^---'--' hjw
	Welcome to MavTutor!!!
		""";
		System.out.println(splash);
		try{
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	private void buildMenu(){
		menu = new Menu("MavTutor Main Menu", new Object[] {"\nSelection? "});
		menu.addMenuItem(new MenuItem("Add Tutor", this::newTutor));
		menu.addMenuItem(new MenuItem("Add Student", this::newStudent));
		menu.addMenuItem(new MenuItem("Add Course", this::newCourse));
		menu.addMenuItem(new MenuItem("Add Session", this::newSession));


		menu.addMenuItem(new MenuItem("View Courses", () -> System.out.println(selectView(courses))));
		menu.addMenuItem(new MenuItem("View Tutors", () -> System.out.println(selectView(tutors))));
		menu.addMenuItem(new MenuItem("View Students", () -> System.out.println(selectView(students))));
		menu.addMenuItem(new MenuItem("View Sessions", () -> System.out.println(selectView(sessions))));

		menu.addMenuItem(new MenuItem("Review a Student", () -> review(students)));
		menu.addMenuItem(new MenuItem("Review a Tutor", () -> review(tutors)));
		menu.addMenuItem(new MenuItem("Review a Session", () -> review(sessions)));

		menu.addMenuItem(new MenuItem("New...", this::newz ));
		menu.addMenuItem(new MenuItem("Open", this::open));
		menu.addMenuItem(new MenuItem("Save", this::save));
		menu.addMenuItem(new MenuItem("SaveAs", this::saveAs));
		
		menu.addMenuItem(new MenuItem("quit", this::quit));
	}
	

	private void quit(){
		menu.result = null;
	}

	private String selectView(List<?> list){
		String header;
		if(list == courses) header = "Courses";
		else if(list == students) header = "Students";
		else if(list == tutors) header = "Tutors";
		else if(list == sessions) header = "Sessions";
		else header = "Unknown List";

		StringBuilder sb = new StringBuilder();

		sb.append("\n").append(header).append(":\n");
		if(list == null || list.isEmpty()){
			sb.append("No data to display.\n");
		} else {
			for(Object item : list){
				sb.append("* ").append(item.toString()).append("\n");
			}
		}

		return sb.toString();

	}
	private void newCourse(){
		String dept = Menu.getString("\nEnter department: ");
		int number = Menu.getInt("Enter course number: ");

		Course course = new Course(dept, number);
		if(courses.contains(course)){
			System.out.println("Course already exists.");
		} else {
			courses.add(course);
			System.out.println("Course added: " + course + "\n");
		}
	}
	private void newTutor(){
		if (courses.isEmpty()) {
        System.out.println("No courses available. Create a course first.");
        return;
    	}

    
    	String name = Menu.getString("\nEnter tutor name: ");
     	String email = Menu.getString("Enter tutor email: ");
    	int ssn = Menu.getInt("Enter tutor ssn: ");
    	String bio = Menu.getString("Enter bio: "); 

    
    	Course course;
    	if (courses.size() == 1) {
    		course = courses.get(0);
    		System.out.println("Only one course available. Automatically selected: " + course);
		} else {
			course = null;
	    	while (course == null) {
	    	    Integer index = Menu.selectItemFromList("Select a course for the tutor: ", courses);

	    	    if (index == null) { 
	    	        System.out.println("Selection cancelled. Please select a course."); 
	        	    continue;
	    	    }

	    	    if (index < 0 || index >= courses.size()) {
	    	        System.out.println("Invalid course. Try again.");
	        	    continue;
	       	 	}

	        	course = courses.get(index);
	    	}
    	}
    
    	Tutor tutor = new Tutor(name, email, ssn, bio, course);
    	tutors.add(tutor);

    	System.out.println("Tutor added: " + tutor + "\n");
	}

	private void newStudent(){
		if(courses.isEmpty()){
			System.out.println("No courses available. Create a course first.");
			return;
		}

		String name = Menu.getString("\nEnter student name: ");
		String email = Menu.getString("Enter student email: ");

		Student student = new Student(name, email);

		boolean addingCourses = true;

		while(addingCourses){
			if (courses.size() == 1) {
		        Course course = courses.get(0);
		        student.addCourse(course);
		        System.out.println("Only one course available. Automatically added: " + course);
		        break;
    		}
			Integer index = Menu.selectItemFromList("Select a course fror the student (Cancel to finish): ", courses);
			if(index == null){
				addingCourses = false;
			} else {
				Course course = courses.get(index);
				student.addCourse(course);
				System.out.println("Added course: " + course);
			}
		}

		students.add(student);
		System.out.println("Student added: " + student + "\n");
	}

	private void newSession() {
	    if(courses.isEmpty()){
	        System.out.println("No courses available.");
	        return;
	    }
	    if(tutors.isEmpty()){
	        System.out.println("No tutors available.");
	        return;
	    }
	    if(students.isEmpty()){
	        System.out.println("No students available.");
	        return;
	    }

	    // Select course
	    Course course = null;
	    while(course == null){
	        Integer courseIndex = Menu.selectItemFromList("Select a course for the session: ", courses);
	        if(courseIndex == null || courseIndex < 0 || courseIndex >= courses.size()){
	            System.out.println("Invalid selection, try again.");
	            continue;
	        }
	        course = courses.get(courseIndex);
	    }

	    // Select tutor
	    Tutor tutor = null;
	    while(tutor == null){
	        Integer tutorIndex = Menu.selectItemFromList("Select a tutor for the session: ", tutors);
	        if(tutorIndex == null || tutorIndex < 0 || tutorIndex >= tutors.size()){
	            System.out.println("Invalid selection, try again.");
	            continue;
	        }
	        tutor = tutors.get(tutorIndex);
	    }

	    Session session = new Session(course, tutor);

	    // Date/time/duration
	    String date = Menu.getString("Enter session date (YYYY-MM-DD): ");
	    String time = Menu.getString("Enter session time (HH:MM): ");
	    int duration = Menu.getInt("Enter session duration in minutes: ");
	    session.setSchedule(date, time, duration);

	    // Add students
	    
	    List<Student> addedStudents = new ArrayList<>();

	    while(true){
	    	Integer index = Menu.selectItemFromList("Select a student to add to the session(Cancel to finish): ", students);
	    	if(index == null || index < 0 || index >= students.size()){
	    		System.out.println("Finished adding students");
	    		break;
	    	}
	    	Student student = students.get(index);

	    	if(addedStudents.contains(student)){
	    		System.out.println("Student already added: " + student);
	    		continue;
	    	}
	    	session.addStudent(student);
	    	addedStudents.add(student);
	    	System.out.println("Added student: " + student);
	    }

	    sessions.add(session);
	    System.out.println("Session created successfully: " + session);
	}

	private void newz(){
		if(courses != null) courses.clear();
		if(students != null) students.clear();
		if(tutors != null) tutors.clear();
		if(sessions != null) sessions.clear();
		file = null;
	}
	private void save(){
		if(file == null){
			file = Menu.selectFile("Select choice to save file: ",null,null);
		}
		try(PrintStream out = new PrintStream(file)){
			out.println(courses.size());
			for(Course c : courses){
				c.save(out);
			}

			out.println(students.size());
			for(Student s: students){
				s.save(out);
			}

			out.println(tutors.size());
			for(Tutor t : tutors){
				t.save(out);
			}

			out.println(sessions.size());
			for(Session ses : sessions){
				ses.save(out);
			}

			menu.result.append("Saved successfully.\n");
		}

		catch(Exception e){
			menu.result.append("Error saving " + e.getMessage() + "\n");
		}

	}
	private void saveAs(){
		file = null;
		save();
	}
	private void open(){
		File chosen = Menu.selectFile("Select a file to open: ", null, null);

		try(Scanner in = new Scanner(chosen)){
			this.file = chosen;

			int courseSize = Integer.parseInt(in.nextLine());
			courses.clear();
			for(int i = 0; i < courseSize; ++i){
				courses.add(new Course(in));
			}

			int studentSize = Integer.parseInt(in.nextLine());
			students.clear();
			for(int i = 0; i < studentSize; ++i){
				students.add(new Student(in));
			}

			int tutorSize = Integer.parseInt(in.nextLine());
			tutors.clear();
			for(int i = 0; i < tutorSize; ++i){
				tutors.add(new Tutor(in));
			}

			int sessionSize = Integer.parseInt(in.nextLine());
			sessions.clear();
			for(int i = 0; i < sessionSize; ++i){
				sessions.add(new Session(in));
			}
			menu.result.append("File opened and data loaded successfully!\n");
		}
		catch(Exception e){
			menu.result.append("Error loading file: " + e + "\n");
			newz();
		}

	}

	private void review(List<? extends Rateable> items){
		if(items.isEmpty()){
			System.out.println("No items available to review right now.");
			return;
		}
		Integer index = Menu.selectItemFromList("Select an item to review: ", items);
		if(index == null) return;
		Rateable selected = items.get(index);

		double avg = selected.getAverageRating();
		System.out.println("Average rating: " + (Double.isNaN(avg) ? "N/A" :  avg));
	
		Person reviewer = login();
		String add = Menu.getString("Would you like to add a rating? (y/n): ");
		if(add.equalsIgnoreCase("y")){
			int stars = Menu.getInt("Enter stars (1-5): ");
			String text = Menu.getString("Enter you comment: ");

			Comment comment = new Comment(text, reviewer, null);
			selected.addRating(new Rating(stars, comment));

			System.out.println("Thank you! Rating added.");
		}
		Rating[] ratings = selected.getRatings();
		if(ratings.length == 0){
			System.out.println("No ratings to display");
			return;
		}

		System.out.println("All Ratings: \n");
		for(int i = 0; i < ratings.length; ++i){
			System.out.println((i+1) + ")");
			printIndented(ratings[i].getReview().toString(), 1);
		}

		Integer choice = Menu.getInt("Select a rating to view its comments: ");
		Rating chosen = ratings[choice -1];

		System.out.println("\nComments for this rating:\n");
		navigateComments(chosen.getReview(), reviewer);
	}

	private static void printIndented(String multiline, int level) {
        String[] strings = multiline.split("\n");
        for(String s : strings) 
            System.out.println("  ".repeat(level) + s);
    }
    private static void printExpandedComments(Comment c, int level) {
        printIndented(c.toString(), level);
        System.out.println("\n");
        for(int i=0; i<c.numReplies(); ++i)
            printExpandedComments(c.getReply(i), level+1);
    }


	private void navigateComments(Comment comment, Person user) {
    while (true) {
        // Show current comment and its subtree
        System.out.println("\n--- Viewing Comment ---");
        printExpandedComments(comment, 0);

        // Show options
        System.out.println("\nOptions:");
        System.out.println("1) Reply to THIS comment");
        System.out.println("2) Reply to a SUB-comment (choose which one)");
        System.out.println("3) View a reply (drill down)");
        System.out.println("4) Reply to PARENT comment (if any)");
        System.out.println("5) Go back");

        int opt = Menu.getInt("Select an option: ");

        switch (opt) {
            case 1 -> {
                // Reply to the current comment
                if (user == null) {
                    String doLogin = Menu.getString("You must be logged in to reply. Log in now? (y/n): ");
                    if (doLogin.equalsIgnoreCase("y")) {
                        user = login();
                        if (user == null) {
                            System.out.println("Login cancelled. Cannot reply.");
                            break;
                        }
                    } else {
                        System.out.println("Reply cancelled.");
                        break;
                    }
                }
                String replyText = Menu.getString("Enter your reply: ");
                comment.addReply(replyText, user); // adds reply as child of 'comment'
                System.out.println("Reply added to this comment.");
            }

            case 2 -> {
                // Reply to a specific sub-comment directly
                int numReplies = comment.numReplies();
                if (numReplies == 0) {
                    System.out.println("No sub-comments to reply to.");
                    break;
                }
                System.out.println("\nSub-comments:");
                for (int i = 0; i < numReplies; ++i)
                    System.out.println((i + 1) + ") " + comment.getReply(i).toString());

                int which = Menu.getInt("Select a sub-comment to reply to (0 to cancel): ");
                if (which <= 0 || which > numReplies) {
                    System.out.println("Cancelled or invalid selection.");
                    break;
                }
                Comment target = comment.getReply(which - 1);

                if (user == null) {
                    String doLogin = Menu.getString("You must be logged in to reply. Log in now? (y/n): ");
                    if (doLogin.equalsIgnoreCase("y")) {
                        user = login();
                        if (user == null) {
                            System.out.println("Login cancelled. Cannot reply.");
                            break;
                        }
                    } else {
                        System.out.println("Reply cancelled.");
                        break;
                    }
                }
                String replyText2 = Menu.getString("Enter your reply: ");
                // add reply to the selected sub-comment (not to the original)
                target.addReply(replyText2, user);
                System.out.println("Reply added to the selected sub-comment.");
            }

            case 3 -> {
                // View (drill down into) a reply - recursive navigation
                int num = comment.numReplies();
                if (num == 0) {
                    System.out.println("No replies to view.");
                    break;
                }
                System.out.println("\nReplies:");
                for (int i = 0; i < num; ++i)
                    System.out.println((i + 1) + ") " + comment.getReply(i).toString());

                int sel = Menu.getInt("Select a reply to view (0 to cancel): ");
                if (sel <= 0 || sel > num) {
                    System.out.println("Cancelled or invalid selection.");
                    break;
                }
                // Recurse into that reply so user can reply further down
                navigateComments(comment.getReply(sel - 1), user);
            }

            case 4 -> {
                // Reply to parent comment (move up and reply)
                Comment parent = comment.getInReplyTo();
                if (parent == null) {
                    System.out.println("No parent comment (this is the top-level review).");
                    break;
                }

                // Print parent briefly so user knows context
                System.out.println("\nParent comment:");
                printExpandedComments(parent, 0);

                if (user == null) {
                    String doLogin = Menu.getString("You must be logged in to reply. Log in now? (y/n): ");
                    if (doLogin.equalsIgnoreCase("y")) {
                        user = login();
                        if (user == null) {
                            System.out.println("Login cancelled. Cannot reply.");
                            break;
                        }
                    } else {
                        System.out.println("Reply cancelled.");
                        break;
                    }
                }
                String replyText3 = Menu.getString("Enter your reply to the parent: ");
                parent.addReply(replyText3, user);
                System.out.println("Reply added to parent comment.");
            }

            case 5 -> {
                // Go back up one level (return to caller)
                return;
            }

            default -> System.out.println("Invalid option. Try again.");
        }
    }
}

	
	private Person login(){
		try{
			System.out.println("\n=== Login Menu ===");
			System.out.println("1) Login as Student");
			System.out.println("2) Login as Tutor");
			System.out.println("3) Continue without login");

			int choice = Menu.getInt("Please select an option: ");
		
			switch(choice){
				case 1 ->{
					if(students.isEmpty()){
						System.out.println("No students available to login.");
						return null;
					}
					Integer sIndex = Menu.selectItemFromList("Select a student to login as: ", students);
					if(sIndex == null){
						System.out.println("Login cancelled.");
						return null;
					}
					Student selectedStudent = students.get(sIndex);
					System.out.println("Logged in as: " + selectedStudent.getName());
					return selectedStudent;
				}
				case 2 -> {
					if(tutors.isEmpty()){
						System.out.println("No tutors availoable to login.");
						return null;
					}
					 Integer tIndex = Menu.selectItemFromList("Select a tutuor to login as: ", tutors);
					if(tIndex == null){
						System.out.println("Login cancelled");
						return null;
					}
					Tutor selectedTutor = tutors.get(tIndex);
					System.out.println("Logged in as: " + selectedTutor.getName());
					return selectedTutor;
				}
				case 3->{
					System.out.println("Continuing without login...");
					return null;
				}
				default ->{
					System.out.println("Invalid selection. Returning to main menu.");
					return null;
				}
			}

		} catch(Exception e){
			System.out.println("Error during login. Please try again.");
			e.printStackTrace();
			return null;
		}
	}

	public static void main(String[] args){
		new MavTutor();
	}

}