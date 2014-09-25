package usfzoo;

public class Elephant extends Mammals {
	public static final String scientificName = "Elephantidae";
	public final String nickName;
	private float height;
	private float weight;
	
	Elephant (String nName, float h, float w){
		 nickName = nName;
		 setHeight(h);
		 setWeight(w);
		 Mammals.totalMammals++;
		 USFZoo.totalAnimals++;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float w) {
		weight = w;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float h) {
		height = h;
	}
	public String toString() {
		return ("Name: "+ nickName + "\n" + "Scientific Name: " + 
	scientificName + "\n" +"Height: " + height + " Feet" + "\n" + 
				"Weight: " + weight + " Pounds");
	}
}
