import java.util.ArrayList;


public class FreqIter {
	
	public String word;
	public int frequency;
	public ArrayList<FreqIter> wordListF = new ArrayList<FreqIter>();
	
	
	public FreqIter(){
		
	}
	//creates FreqIter objects to add to the list
	public FreqIter (String w){
		word = w;
		frequency = 0;
	}
	
	public ArrayList<FreqIter> IterationCount(ArrayList<String> wl){
		//Calls constructor to add objects to iteration lists
		for (int i=0; i<wl.size(); i++){
			wordListF.add(new FreqIter(wl.get(i)));
		}
		//Goes one by one through the list to count the frequency of each word
		for (int index = 0; index<wordListF.size(); index++){
			for (int i=0; i<Driver.aWL.size(); i++){
				if (Driver.aWL.get(i).toLowerCase().equals(wordListF.get(index).word)){
					wordListF.get(index).frequency = wordListF.get(index).frequency + 1;
				}
			}
			//Will print the items in the list
			/*for (int i=0; i<wordListF.size(); i++){
				System.out.println(wordListF.get(i));
			}*/
		}return wordListF;
	}
	//For Array printing
	public String toString(){
		return (word + "   " + frequency);
	}
}
