package usflix;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class MovieDatabase {
	//Lists of movies and results 
	private static ArrayList<Movie> movies = new ArrayList<Movie>();
	protected static ArrayList<Movie> result = new ArrayList<Movie>();
	
	public MovieDatabase (String fname) {
		// try and catch, opens file with name fname
		Scanner filescan = null;
		try {
			filescan = new Scanner (new File (fname));
		} catch (FileNotFoundException e) {
			System.out.println("Movie Database File Not Found.");
		}
		//reads file all the way through and creates movie objects
		while (filescan.hasNextLine()) {
			String title = filescan.nextLine();
			int  year = filescan.nextInt();
			filescan.nextLine();
			String director = filescan.nextLine();
			addMovie (title, year, director);
		}
	}
	//Search method for title
	public static ArrayList<Movie> searchByTitle (String kword){
		int i = 0;
		for (i=0; i<(movies.size()); i++) {
			if (movies.get(i).title.toLowerCase().contains(kword.toLowerCase())){
				result.add(movies.get(i));
			}
		}
		return result;
	}
	// search method for director
	public static ArrayList<Movie> searchByDirector (String kword){
		int i = 0;
		for (i=0; i<(movies.size()); i++) {
			if (movies.get(i).director.toLowerCase().contains(kword.toLowerCase())){
				result.add(movies.get(i));
			}
		}
		return result;
	}
	//add movie method which calls movie constructor
	public static boolean addMovie(String title, int year, String director) {
		Movie newmov = new Movie(title, year, director);
		movies.add(newmov);
		if (movies.contains(newmov)){
			return true;
		}
		else {
			return false;
		}
		
	}
	
}
