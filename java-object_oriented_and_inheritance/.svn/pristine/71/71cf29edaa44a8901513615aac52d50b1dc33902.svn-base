package usfzoo;

public class Alligator extends Reptiles {
	public static final String scientificName = "Alligator";
	public final String nickName;
	private float height;
	private float weight;
	
	Alligator (String nName, float h, float w){
		 nickName = nName;
		 setHeight(h);
		 setWeight(w);
		 Reptiles.totalReptiles++;
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
