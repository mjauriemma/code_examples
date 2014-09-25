package usfzoo;

import java.util.ArrayList;

public class Driver {
	private static ArrayList<Mammals> mammal = new ArrayList<Mammals>();
	private static ArrayList<Reptiles> reptile = new ArrayList<Reptiles>();

	public static void main(String[] args) {

		mammal.add(new Elephant("Dumbo", 3, 500));
		reptile.add(new Alligator("Snappy", 1, 300));
		reptile.add(new Crocodile ("Smiley", 1, 200));
		mammal.add(new Lion ("Mufasa", 4, 100));

		System.out.println("Number of Reptiles: " +Reptiles.totalReptiles);

		for (int i =0; i<reptile.size(); i++) {
			System.out.println(reptile.get(i));
		}
		
		System.out.println("Number of Mammals: " + Mammals.totalMammals);
		
		for (int i= 0; i<mammal.size(); i++) {
			System.out.println(mammal.get(i));
		}
		System.out.println("Total Number of Animals:" + USFZoo.totalAnimals);

	}

}
