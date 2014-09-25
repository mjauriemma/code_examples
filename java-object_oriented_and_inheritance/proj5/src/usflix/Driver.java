package usflix;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Driver class manages user interaction: input and output between the program
 * and users
 * 
 * @author EJ Jung
 * @version 2.0 March 3, 2013.
 */
public class Driver {

	/**
	 * There will be only one movie database and one user database in the system,
	 * so they are declared as static.
	 */
	private static MovieDatabase movieDB;
	private static UserDatabase userDB;

	/**
	 * The first program argument (args[0]) may contain the movie information
	 * file name, and the second program argument (args[1]) may contain the user
	 * information file name.
	 * 
	 * @param args
	 */

	public static void main(String[] args) {

		Scanner scan = new Scanner(System.in);

		userDB = new UserDatabase();

		if (args.length > 1) {
			movieDB = new MovieDatabase(args[0]);
			try {
				loadUsers(args[1]);
			} catch (FileNotFoundException e) {
				System.out.println("User file not found");
				e.printStackTrace();
			}
		} else if (args.length > 0) {
			movieDB = new MovieDatabase(args[0]);
		} else {
			System.out.print("Please enter the movie database file name: ");
			movieDB = new MovieDatabase(scan.nextLine());
		}

		int choice = -1;
		while (choice != 0) {
			try {
				System.out
						.println("Welcome to USFlix! Select an option from the menu.");
				System.out
						.println("1 to load users and their ratings from a file");
				System.out.println("2 to login");
				System.out.println("3 to create a new account");
				System.out.println("0 to quit");
				System.out.print("Enter your choice: ");
				choice = Integer.parseInt(scan.nextLine());

				switch (choice) {
				case 0:
					System.out.println("Bye!");
					break;
				case 1:
					System.out.print("Enter the file name: ");
					loadUsers(scan.nextLine());
					break;
				case 2:
					System.out.print("Enter the username: ");
					String username = scan.nextLine();
					System.out.print("Enter the password: ");
					String password = scan.nextLine();
					User u = userDB.login(username, password);
					if (u == null)
						System.out.println("Login error");
					else {
						userMenu(u);
					}
					break;
				case 3:
					System.out.print("Enter your first name: ");
					String firstName = scan.nextLine();
					System.out.print("Enter your last name: ");
					String lastName = scan.nextLine();
					System.out.print("Enter a username: ");
					username = scan.nextLine();
					//Creates account from user information and prints an error message if it fails
					System.out.println("Enter your password: ");
					if (userDB.createAccount(firstName, lastName, username, scan.nextLine()) == null){
						System.out.println("Account Creation Error!");
					}else{
						System.out.println("Account Created! Please Login.");
					}
					break;
				}

			} catch (InputMismatchException e) {
				System.out.println("Invalid choice");
			} catch (FileNotFoundException e) {
				System.out.println("User file is not found");
			}
		}
	}



	/**
	 * loadUsers() method loads the user information from a file. If the username
	 * is available, then the new account is created and his or her rating information 
	 * gets added to the movie database and also to the user object. If the username
	 * is not available, then this method skips to the next user information (until it
	 * sees "done"). 
	 * 
	 * @param filename the user information file name
	 * @throws FileNotFoundException if the user information file is not found, 
	 * then the main method catches the exception and asks user for another file name.
	 */
	private static void loadUsers(String filename) throws FileNotFoundException {
		Scanner scan = new Scanner(new File(filename));
		//creates accounts and adds movies and ratings to seenMovies list
		while (scan.hasNextLine()) {
			String fn = scan.nextLine();
			String ln = scan.nextLine();
			String un = scan.nextLine();
			String pw = scan.nextLine();
			
			User u;
			u = userDB.createAccount(fn, ln, un, pw);

			
			String title = scan.nextLine();
			if (u != null) {
				while (!title.equals("done")) {
					Movie m = movieDB.getMovieByTitle(title);
					if (m == null) {
						m = new Movie(title, 0, null);
						movieDB.addMovie(m);
					}
					String tmp = scan.nextLine();
					u.addRating(m, Float.parseFloat(tmp));
					title = scan.nextLine();
				}
			} else {
				while (!title.equals("done")) {
					title = scan.nextLine();
				}
			}
		}

	}



	/**
	 * userMenu handles menu options that are available after logging in,
	 * such as search by title (and director if you have it) and the
	 * list of movies the user has seen before. 
	 * 
	 * @param u 
	 */
	private static void userMenu(User u) {
		Scanner scan = new Scanner(System.in);
		int choice = -1;
		while (choice != 0) {
			System.out.println("Welcome, " + u.getFirstName()
					+ "! Select an option from the menu.");
			System.out.println("1 to search movies by title");
			System.out
					.println("2 to see the list of movies you have seen before");
			System.out.println("3 to have a movie recconmended for you");
			System.out.println("0 to logout");
			System.out.print("Enter your choice: ");
			choice = Integer.parseInt(scan.nextLine());
			switch (choice) {
			case 1:
				System.out.print("Enter keywords: ");
				String[] keywords = scan.nextLine().split(" ");

				ArrayList<Movie> searchResults = movieDB
						.searchByTitle(keywords);

				listMenu(u, searchResults);
				break;
			case 2:
				listMenu(u, u.getSeenMovies());
				break;
			case 3:
				listMenu2(u, userDB.UCompare(u));
			case 0:
				return;
			}
		}
	}
	//Method takes the MovieRating list for the Recommended movies and prints them out.
	private static void listMenu2(User u, ArrayList<MovieRating> list) {
		Scanner scan = new Scanner(System.in);
		for (int i = 0; i < list.size(); i++) {
			Movie m = list.get(i).getMovie();
			System.out.println((i + 1) + ". " + m + "\n(" + list.get(i).getRating()+") Expected");
		}
		int choice = -1;
		while (choice != 0) {
			System.out.println("Select the movie number to rate or watch");
			System.out.println("0 to go back to previous menu");
			System.out.print("Enter your choice: ");
			choice = Integer.parseInt(scan.nextLine());
			if (choice == 0) {
				userMenu(u);
			} else if (choice - 1 >= 0 && choice <= list.size()) {
				Movie m = list.get(choice - 1).getMovie();
				System.out.println("How did you like " + m.title
						+ "? (0.5~5 stars)");
				u.addRating(m, Float.parseFloat(scan.nextLine()));
			}
		}
		
	}



	/**
	 * listMenu handles printing the list of Movies with the appropriate
	 * rating (user's own if available, average otherwise), letting user
	 * rate any of the Movies in the list.  
	 * 
	 * @param u
	 * @param list search result or the list of movies user has seen
	 */
	private static void listMenu(User u, ArrayList<Movie> list) {
		Scanner scan = new Scanner(System.in);
		for (int i = 0; i < list.size(); i++) {
			Movie m = list.get(i);
			System.out.println((i + 1) + ". " + m + "\n" + u.getRating(m));
		}
		int choice = -1;
		while (choice != 0) {
			System.out.println("Select the movie number to rate or watch");
			System.out.println("0 to go back to previous menu");
			System.out.print("Enter your choice: ");
			choice = Integer.parseInt(scan.nextLine());
			if (choice == 0) {
				userMenu(u);
			} else if (choice - 1 >= 0 && choice <= list.size()) {
				Movie m = list.get(choice - 1);
				System.out.println("How did you like " + m.title
						+ "? (0.5~5 stars)");
				u.addRating(m, Float.parseFloat(scan.nextLine()));
			}
		}
	}
}