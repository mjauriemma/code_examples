import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class Driver {
	
	public static Scanner filescan;
	public static int numUsers, items;
	
	public static double[][] ratingDB,commonDB; 
	
	public static void main(String[] args) {
		
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
		numUsers = filescan.nextInt();
		items = filescan.nextInt();
		ratingDB = new double[numUsers][items];
		int i = 0;
		while (i<numUsers){
			int j=0;
			while (j<items){
				ratingDB[i][j]=filescan.nextDouble();
				j++;
			}i++;
		}commonDB = new double[numUsers][numUsers];
		for (int j=0; j< numUsers; j++){
			for (int k=0; k<numUsers; k++){
				commonDB[j][j]=0;
				commonDB[j][k]=0;
				if (k!=j){
					for (int h=0; h< items; h++){
						if ((0<=ratingDB[j][h]&&ratingDB[j][h]<=5) && (0<=ratingDB[k][h]&&ratingDB[k][h]<=5)){
							commonDB[j][j]++;
							if (ratingDB[j][h]<ratingDB[k][h]){
								commonDB[j][k] = commonDB[j][k] + (ratingDB[k][h]-ratingDB[j][h]);
							}else{
								commonDB[j][k] = commonDB[j][k] + (ratingDB[j][h]-ratingDB[k][h]);
							}
						
							
							
						}if (h==items-1){
						commonDB[j][k] = (commonDB[j][k]/commonDB[j][j]);
						}
				}
			}
			
			}
		}
		for (i=0; i<numUsers; i++){
			System.out.print("\n");
			for (int j=0; j<numUsers; j++){
				System.out.print(commonDB[i][j]+", ");
			}
		}
		
		int u1, u2, min;
		for (u1=0; u1<numUsers; u1++){
			System.out.print("\nUser"+u1+ " is most like:");
			if (u1 == 0){
				min =1;
			}else{
				min =0;
			}
			for (u2=0; u2<numUsers; u2++){
				if(u1!=u2){
					if (commonDB[u1][u2]<commonDB[u1][min]){
						min = u2;
					}
				}
			}for (int z = 0; z<numUsers; z++){
				if (z!=u1&&commonDB[u1][z]==commonDB[u1][min]){
					System.out.print(" User"+z);
				}
			}
		}

	}
	
	

}
