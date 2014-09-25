import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Driver2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scan = new Scanner(System.in);
		System.out.println("Welcome to USFlix!\n" +
				"Please Enter Keyword(s): ");
		String kword = scan.next();
		
		String filename = "movies";
		String results = "";
		Scanner filescan;
		try {
			filescan = new Scanner (new File(filename));
			for (int i = 0; i<148; i++){
				String line = filescan.nextLine();
				String [] titles = line.split(" ");
				System.out.println(titles);
			}
				
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
