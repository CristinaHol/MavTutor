package rating;
public class Rating{
	public Rating(int stars, Comment review){
		if(stars < 1 || stars > 5) throw new IllegalArgumentException("Stars are not between 1 - 5");
		this.stars = stars;
		this.review = review;
	}

	private int stars;
	private Comment review;

	public int getStars(){
		return stars;
	}

	public Comment getReview(){
		return review;
	}

	@Override
	public String toString(){
		int left = 5 - getStars();
		String s = "";
		for(int i = 0; i < getStars(); ++i){
			s += '\u2605';

		}
		for(int i = 0; i < left; ++i){
			s += '\u2606';
		}
		return s;
	}
}