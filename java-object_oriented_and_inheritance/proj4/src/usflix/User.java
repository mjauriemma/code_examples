package usflix;

import java.util.ArrayList;

public class User {
	private String firstName;
	private String lastName;
	private String username;
	private String password;
	private ArrayList<MovieRating> seenMovies;
	
	//creates user objects
	public User(String f, String l, String u, String p){
		firstName = f;
		lastName = l;
		username = u;
		password = p;
		seenMovies = new ArrayList<MovieRating>();
	}
	//Checks password is good for a Username
	public boolean login(String pw){
		if (pw.equals(password)){
			return true;
		}else{
			return false;
		}
	}
	//Adds ratings to movies and seenMovies
	public void addRating (Movie m, float r){
		if (seenMovies.contains(m)){
			for (int i=0; i<seenMovies.size(); i++){
				if (seenMovies.get(i).equals(m)){
					seenMovies.get(i).setRating(r);
				}
			}m.addRating(r);
		}else{
			seenMovies.add(new MovieRating(m,r));
			m.addRating(r);
		}
	}
	//gets rating or average rating for a movie
	public String getRating(Movie m){
		if (seenMovies.contains(m)){
			return (getRating(m));
		}else{
			return (m.getAverageRating()+ " (average)");
		}
	}
	//first name getter
	public String getFirstName() {
		return firstName;
	}
	//last name getter
	public String getLastName() {
		return lastName;
	}
	//creates a list of Movie objects from seenMovies list
	public ArrayList<Movie> getSeenMovies(){
		ArrayList<Movie> movs = new ArrayList<Movie>();
		for (int i=0; i<seenMovies.size(); i++){
			movs.add(seenMovies.get(i).getMovie());
		}return movs;
	}

	
}
