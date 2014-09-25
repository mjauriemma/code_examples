import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Driver {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Instantiates Scanner and sends prompt to user
		Scanner scan = new Scanner(System.in);
		System.out.println("Welcome to USFlix!\n" +
				"Please Enter Keyword(s): ");
		//Receives user input
		String kword = scan.next();
		//Instantiates the list array
		List<String> titles = new ArrayList<String>();
		//Hard wire file name
		String filename = "movies";
		//Instantiates an empty string for the search results
		String results = "";
		Scanner filescan;
		try {
			filescan = new Scanner (new File(filename));
			//For loop which iterates through all lines of the movies.txt file
			//and adds to List titles
			for (int i = 0; i<148; i++){
				String line = filescan.nextLine();
				titles.add(line);
			}
			//For loop which  goes through titles list and compares to user given keyword
			int j=1;
			for (j = 0; j<148; j++) {

				if (titles.get(j).toLowerCase().contains(kword.toLowerCase())|| 
						titles.get(j).toLowerCase().equals(kword.toLowerCase())){
							results=results +titles.get(j)+ "\n";
				}
			}
			//Print results list
			System.out.println(results);
			
		//Catch exception for file not found 
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("File Not Found");
		}

		
	
	}

}
