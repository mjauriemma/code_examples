import java.util.NoSuchElementException;

public class MyMultiKeyListIterator implements MultiKeyListIterator {

	/** 
	 * Global Variables
	 * List Element pointers for manipulating the list
	 */
	public MyListElem dummyHead;
	public MyListElem dummyTail;
	public MyListElem currElem;
	public MyListElem prevElem;
	/**
	 * Flags to handle special cases
	 * nextFlag - true means previous had just been called
	 * prevFlag - true means next had just been called
	 * doubleRemove - true means remove had been completed and no previous or next had been called yet
	 * move - true means the iterator moved off the 'dummy' head
	 */
	public boolean nextFlag, prevFlag, doubleRemove, move;

	/**
	 * Index of the 'keyList' we are working on
	 */
	int keyIndex;

	public MyMultiKeyListIterator(int keyIndex, MyListElem head, MyListElem tail) {
		this.dummyHead = head;
		this.dummyTail = tail;
		this.currElem = head;
		this.keyIndex = keyIndex;
		this.doubleRemove = false;
		this.move = false;
		this.nextFlag = true;
		this.prevFlag = false;

	}

	@Override
	public boolean hasNext() {
		//Make sure the next element is not the tail
		return currElem.myNext[keyIndex] != this.dummyTail;
	}

	@Override
	public MyListElem next() {
		//Sets values saying next has just been called
		doubleRemove = false;
		move = true;
		if (prevFlag){
			prevFlag = false;
			nextFlag = true;
			return currElem;
		}

		if (hasNext()) {
			prevElem = currElem;
			currElem = currElem.myNext[keyIndex];

			return currElem;
		} else {
			throw new NoSuchElementException();
		}

	}

	@Override
	public boolean hasPrevious() {
		//Make sure the previous element is not the head
		return currElem.myPrev[keyIndex] != this.dummyHead;

	}

	@Override
	public MyListElem previous() {
		//Sets values saying previous has just been called
		doubleRemove = false;
		move = true;
		if (nextFlag){
			nextFlag = false;
			prevFlag = true;
			return currElem;
		}

		if (hasPrevious()) {
			
			currElem = prevElem;
			prevElem = prevElem.myPrev[keyIndex];


			return currElem;
		} else {
			throw new NoSuchElementException();
		}

	}

	@Override
	public void remove() {
		if (!this.move) {
			throw new IllegalStateException();
		}
		if (this.doubleRemove){
			throw new IllegalStateException();
		}
		this.doubleRemove = true;
		MyListElem delElem = currElem;
		
		for (int i=0; i< delElem.numKeys(); i++) {
			delElem.myPrev[i].myNext[i] = delElem.myNext[i];
			delElem.myNext[i].myPrev[i] = delElem.myPrev[i];
		}
		
		// Sets flags to values saying remove has just been called
		this.nextFlag = false;
		this.prevFlag = false;
		this.move = false;

	}

}