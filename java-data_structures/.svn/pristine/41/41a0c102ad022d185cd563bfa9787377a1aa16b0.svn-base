public class MyMultiKeyList implements MultiKeyList {

	protected MyListElem head;
	protected MyListElem tail;

	protected int numKeys, numofElements, keyIndex;

	public MyMultiKeyList(int numKeys) {
		this.numKeys = numKeys;
		head = new MyListElem(numKeys);
		tail = new MyListElem(numKeys);
		for (int j = 0; j < numKeys; j++) {
			head.myNext[j] = tail;
			tail.myPrev[j] = head;
		}

	}

	@Override
	public MyMultiKeyListIterator iterator(int index) {
		if (index >= numKeys) {
			throw new IllegalArgumentException();
		}
		return new MyMultiKeyListIterator(index, head, tail);
	}

	/**
	 * Helper Method for Add: Handles the base case for adding to empty list
	 * 
	 * @param newElement
	 *            Element to be added
	 * @param keyIndex
	 *            keyList to add addElement to
	 */
	public void addToEmpty(MyListElem newElement, int keyIndex) {
		newElement.myPrev[keyIndex] = head;
		head.myNext[keyIndex] = newElement;

		newElement.myNext[keyIndex] = tail;
		tail.myPrev[keyIndex] = newElement;
	}

	/**
	 * Helper method for add: Handles adding to list that has at least one node
	 * @param newElement element to be added
	 * @param it iterator
	 * @param keyIndex keyList to add addElement too
	 */
	public void addAtIndex(MyListElem newElement, MyMultiKeyListIterator it,
			int keyIndex) {
		boolean isAdd = false;
		while (it.hasNext()) {
			MyListElem iterElement = it.next();

			if (newElement.myKeys[keyIndex].compareTo(iterElement.myKeys[keyIndex]) < 0) {

				iterElement.myPrev[keyIndex].myNext[keyIndex] = newElement;
				newElement.myPrev[keyIndex] = iterElement.myPrev[keyIndex];

				iterElement.myPrev[keyIndex] = newElement;
				newElement.myNext[keyIndex] = iterElement;
				isAdd = true;
				break;
			}
		}
		//For adding as the last element to the list before tail 'dummy'
		if (!isAdd) {
			MyListElem iterElement = it.currElem;

			newElement.myNext[keyIndex] = iterElement.myNext[keyIndex];
			iterElement.myNext[keyIndex].myPrev[keyIndex] = newElement;
			iterElement.myNext[keyIndex] = newElement;
			newElement.myPrev[keyIndex] = iterElement;

		}

	}

	@Override
	public void add(Comparable[] keys, Object data) {
		Comparable[] tempArray = new Comparable[keys.length];
		for (int i = 0; i < keys.length; i++) {
			tempArray[i] = keys[i];
		}
		MyListElem addElement = new MyListElem(tempArray, data, numKeys);
		if (keys.length != numKeys) {
			throw new IllegalArgumentException();
		} else {
			for (int i = 0; i < numKeys; i++) {
				MyMultiKeyListIterator it = iterator(i);
				if (!it.hasNext()) {
					addToEmpty(addElement, i);
				} else {
					addAtIndex(addElement, it, i);
				}
			}

		}

	}

	@Override
	public MyListElem get(int index, int keyIndex) {
		int count = -1;
		MyMultiKeyListIterator it = iterator(keyIndex);
		while (it.hasNext()) {
			if (count == index) {
				break;
			}
			count++;
			it.next();
		}
		if (count == index) {
			return it.currElem;
		} else {
			throw new IllegalArgumentException();
		}

	}

	@Override
	public void removeIndex(int index, int keyIndex) {
		int count = -1;
		MyMultiKeyListIterator it = iterator(keyIndex);
		while (it.hasNext()) {
			if (count == index) {
				break;
			}
			count++;
			it.next();
		}
		if (count == index) {
			it.remove();
		} else {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public void remove(Comparable[] keys) {
		if (keys.length != this.numKeys) {

			throw new IllegalArgumentException();

		} else {

			boolean found = false;
			MyMultiKeyListIterator it = iterator(0);
			while (it.hasNext()) {
				it.next();
				int i = 0;
				while (it.currElem.myKeys[i].compareTo(keys[i]) == 0) {
					i++;
					if (i == keys.length - 1) {
						found = true;
						break;
					}
				}

			}
			if (found) {
				it.remove();
			}
		}
	}

	@Override
	public void remove(Comparable key, int keyIndex) {
		MyMultiKeyListIterator it = iterator(keyIndex);
		while (it.hasNext()) {
			it.next();
			if (it.currElem.myKeys[keyIndex].compareTo(key) == 0) {
				it.remove();
			}

		}

	}

}