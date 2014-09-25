/* * Fill in the missing code */
public class LinkedList {
	private LinkElem head = null;
	private LinkElem tail = null; 
	
	/* * Adds an element to the linked list */
	public void add(int elem) {
		LinkElem newNode = new LinkElem(elem);
		if (head == null) {
			head = newNode;
			tail = head;
		} else {
			if (tail != null) {
				tail.setNext(newNode);
				newNode.setNext(null);
				tail = newNode;
			}
		}
	} 
	
	/* * Prints all the elements of the list */
	public void printList() {
		LinkElem curr = head;
		while (curr != null) {
			System.out.print(curr.elem() + " ");
			curr = curr.next();
		}
		System.out.println();
	} 
	
	/* * Finds the middle element in the linked list that starts with link */
	private static LinkElem findMiddle(LinkElem link) {
		LinkElem mid, end;
		mid = link;
		end = link.next();
		while ((end != null) && end.next() != null) {
			mid = mid.next();
			end = end.next().next();
		}
		return mid;
	} 
	
	/*
	 *  * Merges two linked lists: one with head1 and the other with head2
	 * Returns * the head of the merged list
	 */
	public static LinkElem mergeLists(LinkElem elem1, LinkElem elem2) {
		LinkElem curr = null;
		LinkElem cHead = null;
		while (elem1 != null && elem2 != null) {
			if (elem1.elem() < elem2.elem()) {
				if (curr == null) {
					cHead = elem1;
					curr = elem1;
				} else {
					curr.setNext(elem1);
					curr = curr.next();
				}
				elem1 = elem1.next();
			} else {
				if (curr == null) {
					cHead = elem2;
					curr = elem2;
				} else {
					curr.setNext(elem2);
					curr = curr.next();
				}
				elem2 = elem2.next();
			}
		}
		curr.setNext(elem1 != null ? elem1 : elem2);
		return cHead;
	} 
	
	/*
	 *  * Recursively divides the LinkedList into two sublists: one that goes
	 * from * the element "begLink" to the middle element of the list; and
	 * another one * that goes from the element after the middle element and
	 * till the * "endLink". Merges the lists using mergeLists method and
	 * returns the new * head.
	 */
	private LinkElem divideList(LinkElem begLink, LinkElem endLink) {
		if (begLink == endLink) {
			return begLink;
		}
		LinkElem midLink = findMiddle(begLink);
		LinkElem secondBegLink = midLink.next();
		midLink.setNext(null);
		LinkElem leftList = divideList(begLink, midLink);
		LinkElem rightList = divideList(secondBegLink, endLink);
		return mergeLists(leftList, rightList);
	} 
	
	/*
	 *  * Sort this linked list using merge sort This method should call
	 * divideList
	 */
	public void mergeSort() {
		head = divideList(head, tail);
	}
}