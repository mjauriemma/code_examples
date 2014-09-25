package usflix;

import java.util.ArrayList;


public class Movie {

	protected final String title;
	protected final int year;
	protected final String director;
	private ArrayList<Float> ratings;
	
	//Movie constructor
	public Movie(String tit, int yr, String dir){
		title = tit;
		year = yr;
		director = dir;
		ratings = new ArrayList<Float>();
		}
	//calculates average rating for a movie
	public float getAverageRating(){
		int i;
		float a=0;
		for (i=0; i<ratings.size(); i++){
			a = a+ratings.get(i);
		}a = a/(i+1);
		return a;
	}
	//adds a rating for a movie to the list
	public void addRating (float r){
		ratings.add(r);
	}
	//finds an equivalent rating on the list and removes it
	public void removeRating(float r){
		for (int i=0; i<ratings.size(); i++){
			if (ratings.get(i)==r){
				ratings.remove(i);
				break;
			}
		}
	}
	
	//Overrides toString and prints Movie Objects
	public String toString(){
		return (title + " (" + year + ")\n" + 
				director + "\n" + getAverageRating());
		}
	}