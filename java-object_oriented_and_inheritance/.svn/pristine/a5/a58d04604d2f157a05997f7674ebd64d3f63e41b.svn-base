package slotinterface;
import java.util.Scanner;

public class Driver {

	public static void main(String[] args) {
		SlotMachine[] machines = new SlotMachine[3];

		machines[0] = new FruitMachine();
		machines[1] = new VideoPoker();
		machines[2] = new BlackJack();
		Scanner scan = new Scanner(System.in);

		int machine = -1;
		while (machine < 0) {
			System.out
					.print("Which machine would you like to use, 1, 2, or 3? ");
			machine = scan.nextInt();
			if (machine < 1 || machine > 3) {
				System.out.println("Machine " + machine + " is not available.");
				machine = -1;
			} else {
				machine--;
			}
		}

		int choice = 0;
		while (choice != 4) {
			System.out
					.println("What would you like to do? Choose from the following:");
			System.out.println("1: add tokens");
			System.out.println("2: pull the lever");
			System.out.println("3: see the total");
			System.out.println("4: end the game");
			System.out.print("Your choice? ");
			choice = scan.nextInt();

			switch (choice) {
			case 1:
				System.out.print("How many tokens would you like to add? ");
				int result = machines[machine].addToken(scan.nextInt());
				System.out.println("You have "+result+" token(s) now.");
				break;
			case 2:
				if (machines[machine].addToken(0) == 0) {
					System.out.println("You don't have enough tokens.");
				} else {
					System.out.println("You've won " + machines[machine].pull() + " tokens!");
				}
				break;
			case 3:
				System.out.println("You have "+machines[machine].addToken(0)+" token(s) now.");
				break;
			case 4:
				System.out.println("Thanks for playing! Your payout is "
						+ machines[machine].endTheGame()+" token(s).");
				break;
			default:
				System.out.println("Invalid choice");
			}
		}
	}
}
