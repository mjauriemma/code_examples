import java.lang.reflect.Array;
import java.util.ArrayList;

class BinarySearchTree<T extends Comparable<T>> {

	private class BSTNode {
		public T data;
		public BSTNode left;
		public BSTNode right;

		BSTNode(T newdata) {
			data = newdata;
		}
	}

	private BSTNode root;

	public void Insert(T elem) {
		root = Insert(root, elem);
	}

	public boolean Find(T elem) {
		return Find(root, elem);
	}

	public void Delete(T elem) {
		root = Delete(root, elem);
	}

	public T ElementAt(int i) {
		BSTNode node = root;
		int count = i;
		int sizeOfLeftSubtree = 0;
		while (node != null) {
			sizeOfLeftSubtree = NumElems(node.left);
			if (sizeOfLeftSubtree + 1 == count)
				return node.data;
			else if (sizeOfLeftSubtree < count) {
				node = node.right;
				count -= sizeOfLeftSubtree + 1;
			} else {
				node = node.left;
			}
		}
		return null;
	}

	public int NumElems(BSTNode tree) {
		int count = 0;
		if (tree != null) {
			if (tree.left != null) {
				count += NumElems(tree.left);
			}
			if (tree.right != null) {
				count += NumElems(tree.right);
			}
			count++;
		}
		return count;
	}
	
	
	public int Find(BSTNode tree, T data, int count) {
		if (tree == null) {
			return count;
		}
		if (data.compareTo(tree.data) == 0) {
			count++;
			return count;
		}
		if (data.compareTo(tree.data) < 0) {
			count++;
			return Find(tree.left, data, count);
		} else {
			count ++;
			return Find(tree.right, data,count);
		}
	}

	

	public int NumBetween(T low, T high) {
		int countHigh = 0;
		int countLow = 0;
		int count;
		boolean highLeft = true;
		boolean lowLeft = true;
		
		if (root.data.compareTo(low)<0){
			lowLeft = false;
		}
		if (root.data.compareTo(high)<0){
			highLeft = false;
		}
		
		countHigh = Find(root, high, 0);
		countLow = Find(root, low, 0);

		if (highLeft == lowLeft){
			count = (countHigh+countLow);
		}else{
			count = Math.abs(countHigh-countLow);
		}
		
		count = Math.abs(countHigh-countLow);
		return count;
	}

	public void Balance() {
		
		int size = NumElems(root);
		BSTNode[] nodes = toSortedArray();

		root = ToTree(nodes,0,nodes.length-1);
	}
		
	public BSTNode ToTree(BSTNode[] nodes, int start, int end) {
	  if (start > end) {
		  return null;
	  }
	  int mid = start + (end - start) / 2;
	  BSTNode node = nodes[mid];
	  
	  node.left = ToTree(nodes, start, mid-1);
	  node.right = ToTree(nodes, mid+1, end);
	  return node;
	}
	 
	

	public BSTNode[] toSortedArray() {
		int size = NumElems(root);
		BSTNode[] results = new BinarySearchTree.BSTNode[size];
		NodeArray(root, results, size - 1);

		return results;

	}

	private int NodeArray(BSTNode n, BSTNode[] results, int index) {
		if (n.left != null) {
			index = NodeArray(n.left, results, index);
		}
		if (n.right != null) {
			index = NodeArray(n.right, results, index);
		}
		results[index] = n;
		return index - 1;
	}

	public void Print() {
		Print(root);
	}

	public int Height() {
		return Height(root);
	}

	private int Height(BSTNode tree) {
		if (tree == null) {
			return 0;
		}
		return 1 + Math.max(Height(tree.left), Height(tree.right));
	}

	private boolean Find(BSTNode tree, T elem) {
		if (tree == null) {
			return false;
		}
		if (elem.compareTo(tree.data) == 0) {
			return true;
		}
		if (elem.compareTo(tree.data) < 0) {
			return Find(tree.left, elem);
		} else {
			return Find(tree.right, elem);
		}
	}

	private T Minimum(BSTNode tree) {
		if (tree == null) {
			return null;
		}
		if (tree.left == null) {
			return tree.data;
		} else {
			return Minimum(tree.left);
		}
	}

	private void Print(BSTNode tree) {
		if (tree != null) {
			Print(tree.left);
			System.out.println(tree.data);
			Print(tree.right);
		}
	}

	private BSTNode Insert(BSTNode tree, T elem) {
		if (tree == null) {
			return new BSTNode(elem);
		}
		if (elem.compareTo(tree.data) < 0) {
			tree.left = Insert(tree.left, elem);
			return tree;
		} else {
			tree.right = Insert(tree.right, elem);
			return tree;
		}
	}

	private BSTNode Delete(BSTNode tree, T elem) {
		if (tree == null) {
			return null;
		}
		if (tree.data.compareTo(elem) == 0) {
			if (tree.left == null) {
				return tree.right;
			} else if (tree.right == null) {
				return tree.left;
			} else {
				if (tree.right.left == null) {
					tree.data = tree.right.data;
					tree.right = tree.right.right;
					return tree;
				} else {
					tree.data = RemoveSmallest(tree.right);
					return tree;
				}
			}
		} else if (elem.compareTo(tree.data) < 0) {
			tree.left = Delete(tree.left, elem);
			return tree;
		} else {
			tree.right = Delete(tree.right, elem);
			return tree;
		}
	}

	T RemoveSmallest(BSTNode tree) {
		if (tree.left.left == null) {
			T smallest = tree.left.data;
			tree.left = tree.left.right;
			return smallest;
		} else {
			return RemoveSmallest(tree.left);
		}
	}
	/*
	 * Input: A general tree, and an offset (offset initially 0) Output: The
	 * contents of the tree, printed to standard out
	 */
	public void print(BSTNode tree, int offset) {
		if (tree == null) {
			
		} else {
			for (int i = 0; i < offset; i++) {
				System.out.print("\t");
			}
			System.out.print(tree.data + "\n");
			if (tree.left != null) {
				print(tree.left, offset + 1);
			}
			if (tree.right != null) {
				print(tree.right, offset + 1);
			}
		}
	}

	public static void main(String args[])

	{
		BinarySearchTree<Integer> t = new BinarySearchTree<Integer>();
		for (int x = 0; x < 31; x++)
			t.Insert(new Integer(x));
		
		System.out.println(t.ElementAt(new Integer(5)));
		System.out.println(t.NumBetween(new Integer(10), new Integer(15)));
		System.out.println(t.Height());
		t.Balance();
		System.out.println(t.ElementAt(new Integer(5)));
		//System.out.println(t.NumBetween(new Integer(10), new Integer(15)));
		System.out.println(t.Height());
	}

}