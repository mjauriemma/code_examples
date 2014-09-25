package usfzoo;

public class Crocodile extends Reptiles {
	public static final String scientificName = "Crocodylidae";
	public final String nickName;
	private float height;
	private float weight;
	
	Crocodile (String nName, float h, float w){
		 nickName = nName;
		 height = h;
		 weight = w;
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
