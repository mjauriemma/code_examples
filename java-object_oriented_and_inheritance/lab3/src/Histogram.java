import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class Histogram {
	//Class variables used
	static boolean loop = false;
	static Scanner scan = new Scanner(System.in);
	static int count,zero,one,two,three,four = 0;
	static Scanner filescan;
	
	public static void main(String[] args) {
		//First try with args filename
		try {
			filescan = new Scanner (new File(args[0]));
		} catch (FileNotFoundException e) {
			//Catch starts loop asking user for filename
			System.out.println("Enter a filename: ");
			//loop will run until file is found
			while (loop == false){
			try {
					
				String filename = scan.next();
				filescan = new Scanner (new File (filename));
				loop = true;
			} catch (FileNotFoundException e1) {
				System.out.println("File not Found. \n " +
						"Please Enter Another Filename: ");
				// ensures loop will continue to run
				loop = false;
			
			}
			}
		}
		//reads file as long as it has another double
		//ignores numbers less than zero or greater than five
		while (filescan.hasNextDouble()) {
			double temp = filescan.nextDouble();
			//Keeps track of number of entries
			count++;
			//if statements classify where each rating falls
			if (temp<=1 && temp >= 0){
				zero++;	
			}
			if (temp<=2 && temp >1){
				one++;	
			}
			if (temp<=3 && temp >2){
				two++;	
			}
			if (temp<=4 && temp >3){
				three++;	
			}
			if (temp<=5 && temp >4){
				four++;	
			}
		}
		//for loops create asterisks for each entry
		String z = "";
		for (int i = 0; i<zero; i++){
			z+="*";
		}
		String o = "";
		for (int i = 0; i<one; i++){
			o+="*";
		}
		String t = "";
		for (int i = 0; i<two; i++){
			t+="*";
		}
		String th = "";
		for (int i = 0; i<three; i++){
			th+="*";
		}
		String f = "";
		for (int i = 0; i<four; i++){
			f+="*";
		}
		//Print statement, prints number of reviews,
		//range, asterisks, and number of ratings
		System.out.println(count+"reviews \n 4-5 star: "
				+ f+ " " +'(' + four + ") \n 3-4 star: " 
				+ th+ " " +'(' + three + ") \n 2-3 star: "
				+ t+ " " +'(' + two + ") \n 1-2 star: "
				+ o+ " " +'(' + one + ") \n 0-1 star: "
				+ z+ " " +'(' + zero + ")");
		
		

                
	}

}
