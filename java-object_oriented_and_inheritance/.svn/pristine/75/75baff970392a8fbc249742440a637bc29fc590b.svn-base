package usflix;

import java.util.Scanner;

public class Driver {
	static boolean input = false;
	static String filename;
	static Scanner scan = new Scanner (System.in);

	//Uses program arg if available
	public static void main(String[] args) {
	if (args.length > 0) {
		filename=args[0];
	}else {
		System.out.println("Enter Movie Database Name: ");
		filename = scan.next();
	}
	//calls constructor to read file and construct movies Array List
	new MovieDatabase (filename);
	System.out.println("Welcome to USFlix!"); 
	
	//while loop to choose search by Title or search by Director
	//Calls search method and toString 
	while (input == false){
			System.out.println("Enter 1 to Search by Title \n" +
			"-or- \n Enter 2 to Search by Director: ");
			int in = scan.nextInt();
			if (in == 1){
				input = true;
				System.out.println("Enter Keyword for Search: ");
				String keyword = scan.next();
				MovieDatabase.searchByTitle(keyword);
				Movie.toString (MovieDatabase.result);
			}
			if (in == 2){
				input = true;
				System.out.println("Enter Keyword for Search: ");
				String keyword = scan.next();
				MovieDatabase.searchByDirector(keyword);
				Movie.toString (MovieDatabase.result);
			}
			if (in != 1 && in != 2){
				System.out.println("Invalid input");
			}
	}

	}

}
