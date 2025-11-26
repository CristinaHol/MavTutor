package people;
import rating.Rateable;
import rating.Rating;

import java.util.Objects;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.PrintStream;

public class Person implements Rateable{
	public Person(String name, String email){
		if(name == null || name.isEmpty()) throw new IllegalArgumentException("name is not filled out correctly");
		if(email == null || email.isEmpty()) throw new IllegalArgumentException("email is not filled out correctly");

		this.name = name;
		this.email = email;
	}

	public Person(Scanner in){
		this.name = in.nextLine();
		this.email = in.nextLine();
		//this.ratings = new ArrayList<>();
	}
	private String name;
	private String email;
	private ArrayList<Rating> ratings = new ArrayList<>();

	public String getName(){
		return name;
	}

	@Override
	public boolean equals(Object o){
		if(o == this) return true;
		if(o == null || o.getClass() != getClass()) return false;
		Person p = (Person) o;
		return p.name.equals(name) && p.email.equals(email);
	}

	@Override
	public int hashCode(){
		return Objects.hash(name, email);
	}

	@Override
	public String toString(){
		return name + " (" + email +")";
	}

	@Override
	public void addRating(Rating rating){
		ratings.add(rating);
	}

	@Override
	public double getAverageRating(){
		if(ratings.isEmpty()) return 0.0;

		double sum = 0.0;
		for(Rating rating : ratings){
			sum += rating.getStars();
		}

		return sum / ratings.size();
	}

	@Override
	public Rating[] getRatings(){
		return ratings.toArray(new Rating[0]);
	}

	public void save(PrintStream out){
		out.println("" + name);
		out.println("" + email);
		//out.println("" + ratings.size());

	}

}