package slotinheritance;

import java.util.ArrayList;

public class BlackJack extends SlotMachine {
	private ArrayList<String> cards,suites;
	private int d1,d2,p1,p2;
	private int s1,s2,s3,s4;
	
	public BlackJack(){
		suites  = new ArrayList<String>();
		suites.add("Diamonds"); suites.add("Hearts");suites.add("Spades");suites.add("Clubs");
		cards = new ArrayList<String>();
		cards.add("2");cards.add("3");cards.add("4");cards.add("5");cards.add("6");cards.add("7");cards.add("8");
		cards.add("9");cards.add("10");cards.add("Jack");cards.add("Queen");cards.add("King");cards.add("Ace");
	}
	@Override
	public int pull() {
		tokens--;
		d1 = (int) (Math.random()*13);
		d2 = (int) (Math.random()*13);
		p1 = (int) (Math.random()*13);
		p2 = (int) (Math.random()*13);
		s1 = (int) (Math.random()*4);
		s2 = (int) (Math.random()*4);
		s3 = (int) (Math.random()*4);
		s4 = (int) (Math.random()*4);

		//Makes sure that if 2 cards are equal, their suites are not
		if (d1==d2){
			if (d2==p1){
				if (p1==p2){
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
		}if (d2==p1){
			if (p1==p2){
				while (s2==s3||s3==s4||s4==s2){
					s2 = (int) (Math.random()*4);
					s3 = (int) (Math.random()*4);
					s4 = (int) (Math.random()*4);
				}
			}while (s3==s2){
				s2 = (int) (Math.random()*4);
				s3 = (int) (Math.random()*4);
			}
		}if (p1==p2){
			while (s3==s4){
				s3 = (int) (Math.random()*4);
				s4 = (int) (Math.random()*4);
			}
		}
		
		System.out.println("Dealer has: " +cards.get(d1) + " of " + suites.get(s1) + " | "+ cards.get(d2) + " of " + suites.get(s2));
		System.out.println("Player has: "+cards.get(p1) + " of " + suites.get(s3) + " | "+ cards.get(p2) + " of " + suites.get(s4));
		
		
		//If double Aces, Makes one worth one
		if (p1==12&&p2==12){
			p1 = -1;
		}if (d1==12&&d2==12){
			d1=-1;
		}
		if ((d1 == 9&& 1<s1<<4)||(d2==9&& 1<s2<<4)){
			if ((d1==12||d2==12)&& (7<d1<<12||7<d2<<12)){
			System.out.println("Dealer has BlackJack");
			return 0;
			}
		}if ((p1 == 9&& 1<s3<<4)||(p2==9&& 1<s3<<4)){
			if ((p1==12||p2==12)&& (7<p1<<12||7<p2<<12)){
			System.out.println("BlackJack");
			addToken(20);
			return 20;
			}
		}if ((d1==12||d2==12)&& (7<d1<<11||7<d2<<11)){
			System.out.println("Dealer has 21");
			return 0;
		}if ((p1==12||p2==12)&& (7<p1<<11||7<p2<<11)){
			System.out.println("21");
			addToken(15);
			return 15;
		}

		//Makes 10's through Kings worth the same numerical value and Aces worth one more
		if (d1>8){
			if (d1==9){
				d1--;
			}if (d1==10){
				d1= d1-2;
			}if (d1==11){
				d1=d1-3;
			}if (d1==12){
				d1=d1-3;
			}
		}if (d2>8){
			if (d2==9){
				d2--;
			}if (d2==10){
				d2= d2-2;
			}if (d2==11){
				d2=d2-3;
			}if (d2==12){
				d2=d2-3;
			}
		}if (p1>8){
			if (p1==9){
				p1--;
			}if (p1==10){
				p1= p1-2;
			}if (p1==11){
				p1=p1-3;
			}if (p1==12){
				p1=p1-3;
			}
		}if (p2>8){
			if (p2==9){
				p2--;
			}if (p2==10){
				p2= p2-2;
			}if (p2==11){
				p2=p2-3;
			}if (p2==12){
				p2=p2-3;
			}
		}
		
		
		if (d1+d2<p1+p2){
			System.out.println("Player Wins");
			return 0;
		}else{
			System.out.println("Dealer Wins!");
			addToken(5);
			return 5;
		}
	}

}
