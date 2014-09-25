package slotinheritance;

import java.util.ArrayList;

public class VideoPoker extends SlotMachine {
	
	private ArrayList<String> cards,suites;
	private int c1,c2,c3,c4,c5;
	private int s1,s2,s3,s4,s5;
	
	public VideoPoker(){
		suites  = new ArrayList<String>();
		suites.add("Diamonds"); suites.add("Hearts");suites.add("Spades");suites.add("Clubs");
		cards = new ArrayList<String>();
		cards.add("2");cards.add("3");cards.add("4");cards.add("5");cards.add("6");cards.add("7");cards.add("8");
		cards.add("9");cards.add("10");cards.add("Jack");cards.add("Queen");cards.add("King");cards.add("Ace");
	}

	@Override
	public int pull() {
		tokens--;
		c1 = (int) (Math.random()*13);
		c2 = (int) (Math.random()*13);
		c3 = (int) (Math.random()*13);
		c4 = (int) (Math.random()*13);
		c5 = (int) (Math.random()*13);
		s1 = (int) (Math.random()*4);
		s2 = (int) (Math.random()*4);
		s3 = (int) (Math.random()*4);
		s4 = (int) (Math.random()*4);
		s5 = (int) (Math.random()*4);

		//Disallows having 5 of the same card type
		
		if ((c1==c2&&c2==c3)&&(c3==c4&&c4==c5)){
			while (c4==c5){
				c5 = (int) (Math.random()*13);
			}
		}
		
		//Sort the cards with Lowest as c1 and highest as c5
		
		if (c5<c4){
			int t1 = c5;
			c5 = c4;
			c4 = t1;
		}
		if (c4<c3){
			int t1 = c4;
			c4 = c3;
			c3 = t1;
		}
		if (c3<c2){
			int t1 = c3;
			c3 = c2;
			c2 = t1;
		}
		if(c2<c1){
			int t1 = c2;
			c2 = c1;
			c1 = t1;
		}
		if (c5<c4){
			int t1 = c5;
			c5 = c4;
			c4 = t1;
		}
		if (c4<c3){
			int t1 = c4;
			c4 = c3;
			c3 = t1;
		}
		if (c3<c2){
			int t1 = c3;
			c3 = c2;
			c2 = t1;
		}
		if(c2<c1){
			int t1 = c2;
			c2 = c1;
			c1 = t1;
		}
		if (c5<c4){
			int t1 = c5;
			c5 = c4;
			c4 = t1;
		}
		if (c4<c3){
			int t1 = c4;
			c4 = c3;
			c3 = t1;
		}
		if (c3<c2){
			int t1 = c3;
			c3 = c2;
			c2 = t1;
		}
		if(c2<c1){
			int t1 = c2;
			c2 = c1;
			c1 = t1;
		}
		if (c5<c4){
			int t1 = c5;
			c5 = c4;
			c4 = t1;
		}
		if (c4<c3){
			int t1 = c4;
			c4 = c3;
			c3 = t1;
		}
		if (c3<c2){
			int t1 = c3;
			c3 = c2;
			c2 = t1;
		}
		if(c2<c1){
			int t1 = c2;
			c2 = c1;
			c1 = t1;
		}
		//Makes sure that if 2 cards are equal, their suites are not
		if (c1==c2){
			if (c2==c3){
				if (c3==c4){
					s1 = 0; s2 = 1; s3 = 2; s4 = 3;
				}while (s1==s2||s2==s3||s3==s1){
					s1 = (int) (Math.random()*4);
					s2 = (int) (Math.random()*4);
					s3 = (int) (Math.random()*4);
				}
			}while (s1==s2){
				s1 = (int) (Math.random()*4);
				s2 = (int) (Math.random()*4);
			}
		}if (c2==c3){
			if (c3==c4){
				if (c4==c5){
					s2 = 0; s3 = 1; s4 = 2; s5 = 3;
				}while (s2==s3||s3==s4||s4==s2){
					s2 = (int) (Math.random()*4);
					s3 = (int) (Math.random()*4);
					s4 = (int) (Math.random()*4);
				}
			}while (s3==s2){
				s2 = (int) (Math.random()*4);
				s3 = (int) (Math.random()*4);
			}
		}if (c3==c4){
			if (c4==c5){
				while (s3==s4||s4==s5||s5==s3){
					s3 = (int) (Math.random()*4);
					s4 = (int) (Math.random()*4);
					s5 = (int) (Math.random()*4);
				}
			}while (s3==s4){
				s3 = (int) (Math.random()*4);
				s4 = (int) (Math.random()*4);
			}
		}
		
		
		
		System.out.println(cards.get(c1)+" of "+suites.get(s1)+ " | " +cards.get(c2)+" of "+suites.get(s2)+ " | "+
				cards.get(c3)+" of "+suites.get(s3)+ " | "+cards.get(c4)+" of "+suites.get(s4)+ " | "+
				cards.get(c5)+" of "+suites.get(s5));
		
		//Hands and Payouts
		
		//Straights
		if ((c1+1 == c2 && c2+1==c3)&&(c3+1==c4&& c4+1==c5)){
			if ((s1==s2 && s2==s3)&&(s3==s4&&s4==s5)){
				if (c1==8){
					System.out.println("Royal Flush!");
					addToken(50);
					return 50;
				}
				System.out.println("Straight Flush");
				addToken(25);
				return 25;
			}
			System.out.println("Straight");
			addToken(15);
			return 15;
		}
		//Flush
		if ((s1==s2 && s2==s3)&&(s3==s4&&s4==s5)){
			System.out.println("Flush");
			addToken(12);
			return 12;
		}
		//Triples, Pairs, Houses, 4 of a Kind
		if ((c2==c3&&c3==c4)){
			if ((c1==c2)||(c4==c5)){
				System.out.println("4 of a Kind");
				addToken(20);
				return 20;
			}System.out.println("3 of a Kind");
			addToken(10);
			return 10;
		}
		if(c3==c4&&c4==c5){
			if (c1==c2){
				System.out.println("Full House");
				addToken(15);
				return 15;
			}System.out.println("3 of a Kind");
			addToken(10);
			return 10;
		}
		if (c1==c2&&c2==c3){
			if (c4==c5){
				System.out.println("Full House");
				addToken(15);
				return 15;
			}System.out.println("3 of a Kind");
			addToken(10);
			return 10;
		}
		if ((c1==c2||c2==c3)||(c3==c4||c4==c5)){
			System.out.println("2 of a Kind");
			addToken(4);
			return 4;
		}else{
			return 0;
		}
	}

}
