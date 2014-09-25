import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class Login {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Asks for user input user name
		Scanner scan = new Scanner(System.in);
		System.out.print("Enter Your Username: ");
		String username = scan.next();
		
		try {
			//Finds username file
			Scanner filescan = new Scanner(new File(username));
			//Stores password from username file
			String password = filescan.nextLine();
			//Asks user for password
			Scanner scan1 = new Scanner (System.in);
			System.out.print("Enter Your Password: ");
			String line = scan1.next();
			//Condition statement to compare user input password to stored password
			if (line.equals(password)) {
				System.out.println("Welcome " + username);
			}
			//If invalid password program outputs invalid password and asks for password again
			else {
				System.out.println("Invalid Password");	
				Scanner scan2 = new Scanner (System.in);
				System.out.print("Enter Your Password: ");
				String line2 = scan2.next();
				if (line2.equals(password)) {
					System.out.println("Welcome " + username);
				}
				//Allows for a third attempt to input correct password
				else {
					System.out.println("Invalid Password Second Failed Attempt");	
					Scanner scan3 = new Scanner (System.in);
					System.out.print("Enter Your Password: ");
					String line3 = scan3.next();
					if (line3.equals(password)) {
						System.out.println("Welcome " + username);
					}
					//Program terminates after invalid third attempt
					else {
						System.out.println("Invalid Password Third Attempt");
				}
				}
			}
		}
		
		//Catches invalid username (no file named 'username')			
		 catch (FileNotFoundException e) {
			System.out.println("No Username Exists.");

	}
	}
}
