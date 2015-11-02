package assignment5;

public class BTNode<E>
{
	private E data;
	private BTNode<E> left, right;
	
	public BTNode(E initialData, BTNode<E> initialLeft, BTNode<E> initialRight) {
	   data = initialData;
	   left = initialLeft;
	   right = initialRight;
	} 
	
	public E getData() {
	   return data;
	}
	
	public BTNode<E> getLeft() {
	   return left;                                               
	} 
	
	public BTNode<E> getRight() {
	   return right;                                               
	} 
	
	public void setLeft(BTNode<E> lNode) {
		left = lNode;
	}
	
	public void setRight(BTNode<E> rNode) {
		right = rNode;
	}
	
	public boolean isLeaf() {
		if ((left == null) && (right == null)) {
			return true;
		} else {
			return false;
		}
	}
	
	public void print(int depth) {
		int i;
		for (i = 1; i <= depth; i++) {
			System.out.print("   ");
		}
		System.out.println(data);
		if (left != null) {
			left.print(depth+1);
		} else if (right != null) {
			for (i = 1; i <= depth+1; i++) {
				System.out.print("   ");
			}
			System.out.println("--");
		}
		if (right != null) {
			right.print(depth+1);
		} else if (left != null) {
			for (i = 1; i <= depth+1; i++) {
				System.out.print("    ");
			}
			System.out.println("--");
		}
	}
}
