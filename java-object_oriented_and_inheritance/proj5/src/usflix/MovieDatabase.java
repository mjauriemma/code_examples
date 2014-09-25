package usflix;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class MovieDatabase {
	//Lists of movies
	private ArrayList<Movie> movies;
	private Scanner filescan;
	
	public MovieDatabase(){
		
	}
	//Reads movie file and creates movie objects
	//to add to movies list
	public MovieDatabase(String filename){
		movies = new ArrayList<Movie>();
		try {
			filescan = new Scanner (new File(filename));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}while (filescan.hasNextInt()){
			String title = filescan.nextLine();
			int year = filescan.nextInt();
			filescan.nextLine();
			addMovie(new Movie (title, year, filescan.nextLine()));
			
		}
	}
	//adds Movie objects to movies list
	public boolean addMovie (Movie nm){
		return movies.add(nm);
	}
	
	
	//Searches for movies with titles that match the keywords given
	public ArrayList<Movie> searchByTitle(String[] keywords){
		ArrayList<Movie> results = new ArrayList<Movie>();
		for (int i=0; i<movies.size(); i++){
			for (int j=1; j<keywords.length; j++){
				if (movies.get(i).title.contains(keywords[j])){
					results.add(movies.get(i));
				}
			}
		}return results;

	}
	//Matches the parameter title to a title in 
	//the movies database
	public Movie getMovieByTitle(String t){
		for (int i=0; i<movies.size(); i++){
			if (movies.get(i).title.equals(t)){
				return movies.get(i);
			}
		}return null;
	}
}
