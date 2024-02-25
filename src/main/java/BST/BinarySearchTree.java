package BST;

import Queue.MyQueue;

public class BinarySearchTree implements BSTInterface {

	BSTNode root = null;
	public static void main(String[] args)
	{
		BinarySearchTree tree = new BinarySearchTree();

		// Optional code for extra points
		boolean Optional = false;

		// Testing for inserting words into the tree
		// Prints inorder traversal of tree
		tree.put("dog");
		tree.put("cat");
		tree.put("alligator");
		tree.put("giraffe");
		tree.put("elephant");
		tree.put("hippo");
		tree.put("zebra");
		tree.printTreeStructure();

		System.out.println("Contains dog: " + tree.contains("dog"));
		System.out.println("Contains alligator: " + tree.contains("alligator"));
		System.out.println("Contains horse: " + tree.contains("horse"));

		System.out.println();
		tree.delete("dog");
		tree.printTreeStructure();

		System.out.println();
		tree.delete("elephant");
		tree.printTreeStructure();

		System.out.println();
		tree.delete("cat");
		tree.printTreeStructure();

		tree.put("penguin");
		tree.put("aardvark");
		tree.put("bear");
		tree.printTreeStructure();

		// Optional
		if (Optional) {

			MyQueue inOrder = tree.inOrder();
			MyQueue preOrder = tree.preOrder();
			MyQueue postOrder = tree.postOrder();

			System.out.println("In order: " + inOrder);
			System.out.println("Preorder: " + preOrder);
			System.out.println("Postorder: " + postOrder);
		}

		// Testing if clearing the tree works
		System.out.println();
		tree.makeEmpty();
		System.out.println(tree.isEmpty());
	}

	public boolean isEmpty(){
		return root == null;
	}// returns true if the BST is empty, false otherwise

	public void makeEmpty(){
		root = null;
	} // Empties the BST

	public boolean contains(String s){
		return recursiveSearch(root, s);
	} // Returns true if the BST contains the String, false otherwise

	public void put(String s){
		root = recursiveInsert(root, s);
	} // Adds a String to the BST. If the String is already in the BST, does nothing.

	public void delete(String s){
		root = recursiveRemove(root, s);
	} // Removes a String from the BST. If the String isn't in the BST, does nothing.

	public MyQueue inOrder(){
		MyQueue queue = new MyQueue();
		inOrderRecursive(root, queue);
		return queue;
	} // OPTIONAL, Returns a queue with the elements in order

	public MyQueue preOrder(){
		MyQueue queue = new MyQueue();
		preOrderRecursive(root,queue);
		return queue;
	} // OPTIONAL, Returns a queue with the elements pre order

	public MyQueue postOrder(){
		MyQueue queue = new MyQueue();
		postOrderRecursive(root, queue);
		return queue;
	} // OPTIONAL, Returns a queue with the elements post order

	// TODO: Fill this in and call it from contains()
	protected boolean recursiveSearch(BSTNode node, String s) {
		if (node == null) {
			return false; // Base case: String not found
		} 
		else if (node.item.equals(s)) {
			return true; // Base case: String found
		} 
		else if (s.compareTo(node.item) < 0) {
			return recursiveSearch(node.left, s); // Search left subtree
		} 
		else {
			return recursiveSearch(node.right, s); // Search right subtree
		}
	}

	// TODO: Fill this in and call it from put()
	protected BSTNode recursiveInsert(BSTNode node, String s) {
		if (node == null) {
			return new BSTNode(s);
		}
		// Compare the String argument to the item in the current node
		if (s.compareTo(node.item) < 0) {
			// If the String is alphabetically before node.item, recursively insert into the left subtree
			node.left = recursiveInsert(node.left, s);
		} 
		else if (s.compareTo(node.item) > 0) {
			// If the String is alphabetically after node.item, recursively insert into the right subtree
			node.right = recursiveInsert(node.right, s);
		}
		// If the String is equal to node.item, no need to insert as it's already in the BST
		return node; // Return the modified node
	}

	// TODO: Fill this in and call it from delete()
	protected BSTNode recursiveRemove(BSTNode node, String s) {
		if (node == null) {
			return null; // If the subtree is empty, return null
		}

		// Compare the String argument to the item in the current node
		if (s.compareTo(node.item) < 0) {
			// If the String is alphabetically before node.item, recursively remove from the left subtree
			node.left = recursiveRemove(node.left, s);
		} else if (s.compareTo(node.item) > 0) {
			// If the String is alphabetically after node.item, recursively remove from the right subtree
			node.right = recursiveRemove(node.right, s);
		} else {
			// If the current node contains the String to be removed
			// Call deleteNode to handle different cases of removing a node
			node = deleteNode(node);
		}
		return node;
	}

	// TODO: Fill this in and call it from recursiveRemove()
	protected BSTNode deleteNode(BSTNode node) {
		// Case 1: If the node is a leaf node
		if (node.left == null && node.right == null) {
			return null;
		}
		// Case 2: If the node has only one child
		if (node.left == null) {
			return node.right;
		}
		if (node.right == null) {
			return node.left;
		}
		// Case 3: If the node has two children
		// Find the smallest node in the right subtree
		String smallestValue = getSmallest(node.right);
		// Replace the node's value with the smallest value
		node.item = smallestValue;
		// Recursively delete the smallest node in the right subtree
		node.right = recursiveRemove(node.right, smallestValue);
		return node;
	}

	// TODO: Fill this in and call it from deleteNode()
	protected String getSmallest(BSTNode node) {
		// Start with the current node's item as the smallest
		String smallest = node.item;

		// Keep following left branches until we can't go left anymore
		while (node.left != null) {
			smallest = node.left.item;
			node = node.left;
		}

		// Return the smallest value found
		return smallest;
	}
	// Extra Credit

	// TODO: Fill this in and call it from inOrder()
	protected void inOrderRecursive(BSTNode node, MyQueue queue) {
		if (node != null) {
			inOrderRecursive(node.left, queue);
			queue.enqueue(node.item);
			inOrderRecursive(node.right, queue);
		}
	}

	// TODO: Fill this in and call it from preOrder()
	protected void preOrderRecursive(BSTNode node, MyQueue queue) {
		if (node != null) {
			queue.enqueue(node.item);
			preOrderRecursive(node.left, queue);
			preOrderRecursive(node.right, queue);
		}
	}

	// TODO: Fill this in and call it from postOrder()
	protected void postOrderRecursive(BSTNode node, MyQueue queue) {
		if (node != null) {
			postOrderRecursive(node.left, queue);
			postOrderRecursive(node.right, queue);
			queue.enqueue(node.item);
		}
	}

	// Prints out the tree structure, using indenting to show the different levels
	// of the tree
	public void printTreeStructure() {
		printTree(0, root);
	}

	// Recursive helper for printTreeStructure()
	protected void printTree(int depth, BSTNode node) {
		if(depth == 0) System.out.println();
		indent(depth);
		if (node != null) {
			System.out.println(node.item);
			printTree(depth + 1, node.left);
			printTree(depth + 1, node.right);
		} else {
			System.out.println("null");
		}
	}

	// Indents with spaces
	protected void indent(int depth) {
		for (int i = 0; i < depth; i++)
			System.out.print("  "); // Indents two spaces for each unit of depth
	}


}
