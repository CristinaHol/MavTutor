package rating;
import people.Person;
import java.util.ArrayList;

public class Comment{
	public Comment(String text, Person author, Comment inReplyTo){
		if(text == null || text.isEmpty()) throw new IllegalArgumentException("Text is not filled out correctly");
		if( author == null) throw new IllegalArgumentException("Author can't be null.");
		this.text = text;
		this.author = author;
		this.inReplyTo = inReplyTo;
		replies = new ArrayList<>();
	}

	private String text;
	private Person author;
	private Comment inReplyTo;
	private ArrayList<Comment> replies;

	public void addReply(String text, Person author){
		if(text == null ||  text.isEmpty()) throw new IllegalArgumentException("Text is incorrect");
		if(author == null) throw new IllegalArgumentException("Author can't be null.");
		Comment c = new Comment(text, author, this);
		replies.add(c);
	}

	public int numReplies(){
		return replies.size();
	}

	public Comment getReply(int index){
		return replies.get(index);
	}

	public Comment getInReplyTo(){
		return this.inReplyTo;
	}

	@Override
	public String toString(){
		StringBuilder s = new StringBuilder("Comment by " + author);

		if(inReplyTo != null) s.append(" in reply to " + inReplyTo.author);
		
		if(numReplies() != 0){
			s.append("\nReplies: ");
			for(int i = 0; i < numReplies(); ++i){
				s.append(" (").append(i).append(") ").append(replies.get(i).author.getName());
			}
			
		}
		s.append("\n").append(text);
		return s.toString();
	}

}