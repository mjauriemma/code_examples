package usflix;

public class MovieRating {
	
	private Movie movie;
	private float rating;
	private static final float minRating = (float) 0.5;
	private static final float maxRating = (float) 5.0;
	
	//Creates movie rating object
	MovieRating(Movie m, float r){
		movie = m;
		rating = r;
	}
	//getter for movie
	public Movie getMovie(){
		return movie;
	}
	//getter for rating
	public float getRating(){
		return rating;
	}
	//sets rating for a movie
	public void setRating (float r){
		if (minRating<rating && rating<maxRating){
			rating = r;
		}
	}
	//toString to print out rating for movie object
	public String toString(){
		if (rating>0){
			return ("Your Rating: " + rating);
		}else{
			return ("Average Rating: "+ movie.getAverageRating());
		}
	}
	
	
}
