import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class Driver {
	//RecursFreq and FreqIter class objects
	public static RecursFreq rf = new RecursFreq();
	public static FreqIter fi = new FreqIter();
	//Word List and list of All the words
	static ArrayList<String> wordlist = new ArrayList<String>();
	public static ArrayList<String> aWL=new ArrayList<String>();
	
	//filescan for scanning file
	public static Scanner filescan;
	
	public static void main(String[] args) {
		
		//opens file if program arg is given
			if (args.length > 0){
				try {
					filescan = new Scanner(new File(args[0]));
				} catch (FileNotFoundException e) {
					System.out.println("Program args contains invalid Filename.");
				}
			}//Asks for user input and continues to ask until file opens
				else{
					Scanner scan=new Scanner(System.in);
					while (true){
						try {
							System.out.println("Please enter filename: ");
							String fname = scan.next();
							filescan = new Scanner(new File(fname));
							break;
						}catch (FileNotFoundException e) {
							System.out.println("Invalid File Name.");
						}
					}
				}
			//reads file storing words in wordList and aWL
			while (filescan.hasNextLine()){
					String[] nl = filescan.next().split("[^a-zA-Z]");
					//adds every word to awl
					for (int j=0; j<nl.length; j++){
						aWL.add(nl[j]);
					}
					//ensures that only one of each word gets added to list
					for(int i=0; i<nl.length; i++){
						if(wordlist.contains(nl[i].toLowerCase())==false && nl[i]!=" "){
								wordlist.add(nl[i].toLowerCase());
						}
					}
					
				}
			
			//calls both versions of the frequency counter
			fi.IterationCount(wordlist);
			rf.RecursHelp(wordlist);

			//Checks the size of the lists
			if (wordlist.size()==fi.wordListF.size()&&wordlist.size()==rf.wordListR.size()){
				System.out.println("Same Size");
			}
			
			//Compares every single item of the two frequency lists to make sure they have 
			//same result
			for (int i=0; i<wordlist.size(); i++){
				if (fi.wordListF.get(i).word.equals(rf.wordListR.get(i).word)&& fi.wordListF.get(i).frequency==rf.wordListR.get(i).frequency){
					System.out.println("True");
				}else{
					System.out.println("False");
				}
			}
		}
}
