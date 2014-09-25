 
package slotinheritance;

public abstract class SlotMachine {

	protected int tokens;
	protected int payout;
	
	public SlotMachine() {
		tokens=0;
		payout=0;
	}
	/*
	 * @param the number of tokens to add
	 * @return the total number of tokens
	 * 
	 * Player adds tokens to the machine
	 */
	public int addToken(int numTokens) {
		tokens += numTokens;
		return tokens;
	}
	
	/*
	 * 
	 * @return the payout
	 * 
	 * This method checks if there is any token left, and if there is, 
	 * consume one token (decrease the number of tokens by one) and 
	 * turn the reels randomly. Then it prints out what would show on 
	 * screen and returns the payout for this combination of reels. 
	 * For example, Cherry Cherry Cherry could return the maximum value of
	 * fruit machine payout.
	 */
	public abstract int pull();
	
	/*
	 * @return the payout so far + the token leftover
	 * 
	 * This method returns the sum of payout so far and the leftover tokens.  
	 * Then it resets the number of tokens and payout to 0 in this machine.
	 */
	public int endTheGame() {
		int sum = tokens+payout;
		tokens=0;
		payout=0;
		return sum;
	}

}
