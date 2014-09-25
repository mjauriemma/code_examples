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

	//Takes the user and the Database and compares the two, and returns the seen movies
	//Of the user with the most in common minus the seen movies of the user u.
	public ArrayList<MovieRating> MCompare(User u, User[] uDB){
		//If user has not seen any movies, takes the first user and gives his movie
		//with the highest rating
		if (seenMovies.size()==0){
			int mr = 0;
			for (int i=0; i<uDB[0].seenMovies.size(); i++){
				if (uDB[0].seenMovies.get(i).getRating()>uDB[0].seenMovies.get(mr).getRating()){
					mr = i;
				}
			}return uDB[0].seenMovies;
		}else{
			int numUsers = uDB.length-1;
			int items = seenMovies.size();
			//Stores the int of the user's location in uDB
			int[] comp = new int[uDB.length];
			int z=1;
			double[][] ratingDB = new double[uDB.length][items];
			double[][] commonDB = new double[uDB.length][uDB.length];
			//Stores the ratings from the users seen movies in the 0th part of the 
			//first half of the double array
			for (int k=0; k<items; k++){
				ratingDB[0][k] = seenMovies.get(k).getRating();
			}
			int i = 0;
			while (i<numUsers){
				//To store the location of the User for later
				if (username.equals(uDB[i].username)){
					comp[0] = i;
					i++;
				}else{
					comp[z]=i;
					z++;
					int j=0;
					while (j<items){
						//Finds if the given movie is on the User's list, and if so,
						//adds the ratings to the double array in the corresponding spot
						if(seenMovies.contains(uDB[i].seenMovies.get(j))){
							for (int k=0; k<uDB[i].seenMovies.size()-1; k++){
								if (seenMovies.get(j).getMovie().equals(seenMovies.get(k).getMovie())){
									ratingDB[i][j]=uDB[i].seenMovies.get(k).getRating();
								}
							}
						}
						j++;
					}i++;
				}
			}
			
			//Compares the the ratings and divides by the total number they share in common.
			for (int j=0; j< numUsers; j++){
				for (int k=0; k<numUsers; k++){
					commonDB[j][j]=0;
					commonDB[j][k]=0;
					if (k!=j){
						for (int h=0; h< items; h++){
							if ((0<=ratingDB[j][h]&&ratingDB[j][h]<=5) && (0<=ratingDB[k][h]&&ratingDB[k][h]<=5)){
								commonDB[j][j]++;
								if (ratingDB[j][h]<ratingDB[k][h]){
									commonDB[j][k] = commonDB[j][k] + (ratingDB[k][h]-ratingDB[j][h]);
								}else{
									commonDB[j][k] = commonDB[j][k] + (ratingDB[j][h]-ratingDB[k][h]);
								}
							}if (h==items-1){
								commonDB[j][k] = (commonDB[j][k]/commonDB[j][j]);
							}
						}
					}

				}
			//Finds the min common difference from the 0th user
			}
			int[]common = new int[uDB.length];
			int c=0;
			int min = 0;
			for (int h=0; h<numUsers; h++){
				if (commonDB[0][h]==commonDB[0][min]){
					min = h;
					common[c]=h;
					c++;
					if(commonDB[0][h]<commonDB[0][min]&&commonDB[0][h]>0){
						common=new int[numUsers];
						c=0;
					}
				}
			}ArrayList<MovieRating> sm=uDB[(comp[common[c]])].seenMovies;
			//Finds the max rating of all the movies not seen already and returns 
			//the one with the highest value
			
			for (i=0; i<sm.size(); i++){
				if(u.getSeenMovies().contains(sm.get(i).getMovie())){
					sm.remove(i);
				}
			}
			return sm;
		}


	}


}
