import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

//Name: Benjamin Lerner
//UNI: bll2121
//Programming Assignment 1

public class TwoParamObj<AnyType extends Comparable<? super AnyType>>
{

	/**
	 * Construct the tree.
	 */
	public TwoParamObj( )
	{
		root = null;
	}

	/**
	 * Insert into the tree; duplicates are ignored.
	 * @param x the item to insert.
	 */
	public void insert( AnyType x, int line)
	{
		root = insert( x, line, root );
	}

	/**
	 * Remove from the tree. Nothing is done if x is not found.
	 * @param x the item to remove.
	 */

	/**
	 * Internal method to insert into a subtree.
	 * @param x the item to insert.
	 * @param t the node that roots the subtree.
	 * @return the new root of the subtree.
	 */
	
	//note how it takes a "line" int as well
	private MyNode<AnyType> insert( AnyType x, int line, MyNode<AnyType> t )
	{
		if( t == null ) {
			MyNode<AnyType> aNode = new MyNode<> (x, null, null);
			addLine(line, aNode);
			return aNode;
		}

		int compareResult = x.compareTo( t.element );

		if( compareResult < 0 )
			t.left = insert( x, line, t.left );
		else if( compareResult > 0 )
			t.right = insert( x, line, t.right );
		else {
			addLine(line, t);
		}
		return balance( t );

	}
	
	//adds a line int a node's linkedlist
	public void addLine(int line, MyNode<AnyType> t) {
		t.lineList.add(line);
	}

	public void remove( AnyType x )
	{
		root = remove( x, root );
	}


	/**
	 * Internal method to remove from a subtree.
	 * @param x the item to remove.
	 * @param t the node that roots the subtree.
	 * @return the new root of the subtree.
	 */
	private MyNode<AnyType> remove( AnyType x, MyNode<AnyType> t )
	{
		if( t == null )
			return t;   // Item not found; do nothing

		int compareResult = x.compareTo( t.element );

		if( compareResult < 0 )
			t.left = remove( x, t.left );
		else if( compareResult > 0 )
			t.right = remove( x, t.right );
		else if( t.left != null && t.right != null ) // Two children
		{
			t.element = findMin( t.right ).element;
			t.right = remove( t.element, t.right );
		}
		else
			t = ( t.left != null ) ? t.left : t.right;
		return balance( t );
	}

	/**
	 * Find the smallest item in the tree.
	 * @return smallest item or null if empty.
	 */
	public AnyType findMin( ) throws Exception
	{
		if( isEmpty( ) )
			throw new Exception( );
		return findMin( root ).element;
	}

	/**
	 * Find the largest item in the tree.
	 * @return the largest item of null if empty.
	 */
	public AnyType findMax( ) throws Exception
	{
		if( isEmpty( ) )
			throw new Exception( );
		return findMax( root ).element;
	}

	/**
	 * Find an item in the tree.
	 * @param x the item to search for.
	 * @return true if x is found.
	 */
	public boolean contains( AnyType x )
	{
		return contains( x, root );
	}

	/**
	 * Make the tree logically empty.
	 */
	public void makeEmpty( )
	{
		root = null;
	}

	/**
	 * Test if the tree is logically empty.
	 * @return true if empty, false otherwise.
	 */
	public boolean isEmpty( )
	{
		return root == null;
	}

	/**
	 * Print the tree contents in sorted order.
	 */
	public void printTree( )
	{
		if( isEmpty( ) )
			System.out.println( "Empty tree" );
		else
			printTree( root );
	}

	private static final int ALLOWED_IMBALANCE = 1;

	// Assume t is either balanced or within one of being balanced
	private MyNode<AnyType> balance( MyNode<AnyType> t )
	{
		if( t == null )
			return t;

		if( height( t.left ) - height( t.right ) > ALLOWED_IMBALANCE )
			if( height( t.left.left ) >= height( t.left.right ) )
				t = rotateWithLeftChild( t );
			else
				t = doubleWithLeftChild( t );
		else
			if( height( t.right ) - height( t.left ) > ALLOWED_IMBALANCE )
				if( height( t.right.right ) >= height( t.right.left ) )
					t = rotateWithRightChild( t );
				else
					t = doubleWithRightChild( t );

		t.height = Math.max( height( t.left ), height( t.right ) ) + 1;
		return t;
	}

	public void checkBalance( )
	{
		checkBalance( root );
	}

	private int checkBalance( MyNode<AnyType> t )
	{
		if( t == null )
			return -1;

		if( t != null )
		{
			int hl = checkBalance( t.left );
			int hr = checkBalance( t.right );
			if( Math.abs( height( t.left ) - height( t.right ) ) > 1 ||
					height( t.left ) != hl || height( t.right ) != hr )
				System.out.println( "OOPS!!" );
		}

		return height( t );
	}


	/**
	 * Internal method to find the smallest item in a subtree.
	 * @param t the node that roots the tree.
	 * @return node containing the smallest item.
	 */
	private MyNode<AnyType> findMin( MyNode<AnyType> t )
	{
		if( t == null )
			return t;

		while( t.left != null )
			t = t.left;
		return t;
	}

	/**
	 * Internal method to find the largest item in a subtree.
	 * @param t the node that roots the tree.
	 * @return node containing the largest item.
	 */
	private MyNode<AnyType> findMax( MyNode<AnyType> t )
	{
		if( t == null )
			return t;

		while( t.right != null )
			t = t.right;
		return t;
	}

	/**
	 * Internal method to find an item in a subtree.
	 * @param x is item to search for.
	 * @param t the node that roots the tree.
	 * @return true if x is found in subtree.
	 */
	private boolean contains( AnyType x, MyNode<AnyType> t )
	{
		while( t != null )
		{
			int compareResult = x.compareTo( t.element );

			if( compareResult < 0 )
				t = t.left;
			else if( compareResult > 0 )
				t = t.right;
			else
				return true;    // Match
		}

		return false;   // No match
	}

	/**
	 * Internal method to print a subtree in sorted order.
	 * @param t the node that roots the tree.
	 */
	private void printTree( MyNode<AnyType> t )
	{
		if( t != null )
		{
			printTree( t.left );
			System.out.println("The word '" + t.element + "' appears on lines: ");
			printLines(t);
			printTree( t.right );
		}
	}

	private void printLines(MyNode<AnyType> t) {
		for (int i = 0; i < t.lineList.size(); ++i) {
			System.out.println(t.lineList.get(i));
		}
	}

	/**
	 * Return the height of node t, or -1, if null.
	 */
	private int height( MyNode<AnyType> t )
	{
		return t == null ? -1 : t.height;
	}

	/**
	 * Rotate binary tree node with left child.
	 * For AVL trees, this is a single rotation for case 1.
	 * Update heights, then return new root.
	 */
	private MyNode<AnyType> rotateWithLeftChild( MyNode<AnyType> k2 )
	{
		MyNode<AnyType> k1 = k2.left;
		k2.left = k1.right;
		k1.right = k2;
		k2.height = Math.max( height( k2.left ), height( k2.right ) ) + 1;
		k1.height = Math.max( height( k1.left ), k2.height ) + 1;
		return k1;
	}

	/**
	 * Rotate binary tree node with right child.
	 * For AVL trees, this is a single rotation for case 4.
	 * Update heights, then return new root.
	 */
	private MyNode<AnyType> rotateWithRightChild( MyNode<AnyType> k1 )
	{
		MyNode<AnyType> k2 = k1.right;
		k1.right = k2.left;
		k2.left = k1;
		k1.height = Math.max( height( k1.left ), height( k1.right ) ) + 1;
		k2.height = Math.max( height( k2.right ), k1.height ) + 1;
		return k2;
	}

	/**
	 * Double rotate binary tree node: first left child
	 * with its right child; then node k3 with new left child.
	 * For AVL trees, this is a double rotation for case 2.
	 * Update heights, then return new root.
	 */
	private MyNode<AnyType> doubleWithLeftChild( MyNode<AnyType> k3 )
	{
		k3.left = rotateWithRightChild( k3.left );
		return rotateWithLeftChild( k3 );
	}

	/**
	 * Double rotate binary tree node: first right child
	 * with its left child; then node k1 with new right child.
	 * For AVL trees, this is a double rotation for case 3.
	 * Update heights, then return new root.
	 */
	private MyNode<AnyType> doubleWithRightChild( MyNode<AnyType> k1 )
	{
		k1.right = rotateWithLeftChild( k1.right );
		return rotateWithRightChild( k1 );
	}

	private static class MyNode<AnyType>
	{
		// Constructors
		MyNode(AnyType theElement)
		{
			this(theElement, null, null );
		}

		MyNode( AnyType theElement, MyNode<AnyType> lt, MyNode<AnyType> rt )
		{
			element  = theElement;
			left     = lt;
			right    = rt;
			height   = 0;
		}

		AnyType           element; 								// The data in the node
		MyNode<AnyType>  left;     							    // Left child
		MyNode<AnyType>  right;    							    // Right child
		int               height;       						// Height
		LinkedList<Integer> lineList = new LinkedList<>();		// list of the lines in which the word shows up
	}

	private MyNode<AnyType> root;

	public static void main( String [ ] args )
	{
		TwoParamObj<String> s = new TwoParamObj();
		//takes in a file of words on different lines as its arguments
		File file = new File(args[0]);
		Scanner scanner = null;
		try {
			scanner = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		String line = scanner.nextLine();
		int lineCount = 1;
		while (true) {
			//splits the line up into its constituent words
			String[] words = line.toLowerCase().split(" ");
			//for every word in a line
			for (int i = 0; i < words.length; ++i) {
				if (!words[i].replaceAll(("[^(a-zA-Z||')]" ),  "").equals("")) {
					//insert it in lower case without punctuation into the AVL tree along with its linecount
					s.insert(words[i].replaceAll(("[^(a-zA-Z||')]" ),  ""), lineCount);
				}
			}
			if (!scanner.hasNext()) {
				break;
			}
			//get the next line
			line = scanner.nextLine();
			//increase the line count with every loop
			lineCount++;
		}
		s.printTree();
	}
}
