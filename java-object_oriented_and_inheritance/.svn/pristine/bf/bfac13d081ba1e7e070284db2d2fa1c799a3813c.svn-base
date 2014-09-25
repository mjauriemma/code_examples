import java.util.ArrayList;


public class RecursFreq {

	public String word;
	public int frequency;
	public ArrayList<RecursFreq> wordListR = new ArrayList<RecursFreq>();
	
	public RecursFreq(){
		
	}
	//Constructor
	public RecursFreq (String w){
		word = w;
		frequency = 0;
	}
	
	public ArrayList<RecursFreq> RecursHelp (ArrayList<String> wl){
		//Creates objects for recursive list
		for (int i=0; i<wl.size(); i++){
			wordListR.add(new RecursFreq(wl.get(i)));
		}
		int index = wordListR.size();
		//Calls recursive method
		RecursionCount(index-1);
		
		//Will print items in the array
		/*for (int i=0; i<wordListR.size(); i++){
			System.out.println(wordListR.get(i));
		}*/
		return wordListR;
	}
	
	private RecursFreq RecursionCount (int index){
		//Goes through the list one by one increasing frequency when word appears
		for (int i=0; i<Driver.aWL.size(); i++){
			if (Driver.aWL.get(i).toLowerCase().equals(wordListR.get(index).word)){
				wordListR.get(index).frequency = wordListR.get(index).frequency + 1;
			}
		}
		if (index>0){
			//Recursive call towards base case
			return RecursionCount(index-1);
		}else{
			//Base Case
			return null;
		}
	}
	//for printing array
	public String toString(){
		return (word + "   " + frequency);
	}
}
