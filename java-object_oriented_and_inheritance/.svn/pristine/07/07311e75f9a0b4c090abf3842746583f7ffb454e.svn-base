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
		movies.add(nm);
		if (movies.contains(nm)){
			return true;
		}else{
			return false;
		}
	}
	//Searches for movies with titles that match the keywords given
	public ArrayList<Movie> searchByTitle(String[] keywords){
		ArrayList<Movie> results = new ArrayList<Movie>();
		if (keywords.length == 1){
			for (int i=0; i<movies.size(); i++){
				if (movies.get(i).title.contains(keywords[0])){
					results.add(movies.get(i));
				}
			}return results;
		}else{
			for (int i=0; i<movies.size(); i++){
				if (movies.get(i).title.contains(keywords[0])){
					results.add(movies.get(i));
				}
			}for (int i=1; i<keywords.length; i++){
				for(int j=0; j<results.size(); j++){
					if (!results.get(j).title.contains(keywords[i])){
						results.remove(j);
					}
				}
			}return results; 
		}
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
