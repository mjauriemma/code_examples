package slotinheritance;

import java.util.ArrayList;

public class FruitMachine extends SlotMachine {
	private ArrayList<String> symbols;
	private int r1, r2, r3;
	
	public FruitMachine (){
		 symbols = new ArrayList<String>();
		 symbols.add("Cherry");symbols.add("Bar");symbols.add("Lemon");
		 symbols.add("Orange");symbols.add("Strawberrry");symbols.add("Pear");
		 symbols.add("Grape");symbols.add("Bell");symbols.add("Watermelon");
		 symbols.add("7");
	}
	
	public int pull() {
		tokens--;
		r1 = (int) (Math.random()*10);
		r2 = (int) (Math.random()*10);
		r3 = (int) (Math.random()*10);
		System.out.println(symbols.get(r1)+ "|"+symbols.get(r2)+ 
				"|"+symbols.get(r3));
		if(r1==r2&&r2==r3){
			if(r1==0){
				addToken(5);
				return 5;
			}
			if (r1==1){
				addToken(80);
				return 80;
			}
			if (r1==2){
				addToken(9);
				return 9;
			}
			if (r1==3){
				addToken(12);
				return 12;
			}
			if (r1==4){
				addToken(18);
				return 18;
			}
			if (r1==5){
				addToken(25);
				return 25;
			}
			if (r1==6){
				addToken(65);
				return 65;
			}
			if (r1==7){
				addToken(100);
				return 100;
			}
			if (r1==8){
				addToken(200);
				return 200;
			}
			if (r1==9){
				addToken(500);
				return 500;
			}
		}
		if (r1==r2&&r3==1){
			if (r2==0){
				addToken(4);
				return 4; 
			}
			if (r2==2){
				addToken(8);
				return 8; 
			}
			if (r2==3){
				addToken(10);
				return 10; 
			}
			if (r2==4){
				addToken(15);
				return 15; 
			}
			if (r2==5){
				addToken(20);
				return 20; 
			}
			if (r2==6){
				addToken(50);
				return 50; 
			}
			if (r2==7){
				addToken(90);
				return 90; 
			}
			if (r2==8){
				addToken(150);
				return 150; 
			}
		}
		if (r1==9 && r2==r1){
			addToken(500);
			return 500;
		}
		if (r1==9){
			addToken(2);
			return 2;
		}else{
			return 0;
		}
	}

}
