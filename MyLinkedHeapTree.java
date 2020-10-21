

import java.util.*;

import net.datastructures.CompleteBinaryTree;
import net.datastructures.EmptyTreeException;
import net.datastructures.LinkedBinaryTree;
import net.datastructures.Position;
import net.datastructures.NodeDeque;

/**
 * An implementation of a complete binary tree by means 
 * of a linked structure (LinkedBinaryTree). The LinkedBinaryTree class 
 * takes care of most of the mechanics of modifying 
 * the tree (you should read through the NDS4 documentation 
 * in order to fully understand how this class works. There's a link on
 * the website), but you will need 
 * to think about how to implement a CompleteBinaryTree such that
 * additions and removals operate *only* on the last node (hint: think
 * about other useful data structures). You must also ensure that you do not
 * violate the assignment runtime requirements when deciding how you will
 * track nodes within the tree.
 *  
 */

public class MyLinkedHeapTree<E> extends LinkedBinaryTree<E> 
		implements CompleteBinaryTree<E> {
	
	/**
	 * Default constructor. The tree begins empty.
	 */
	NodeDeque<Position<E>> line = new NodeDeque<Position<E>>();
	LinkedBinaryTree<E> tree_;
	int node_num;
	public MyLinkedHeapTree() {
		node_num=0;
		tree_=new LinkedBinaryTree<E>();
	}

	/**
	 * Adds an element to the tree just after the last node. Returns the newly
	 * created position for the element.
	 *
	 * Note: You don't need to instantiate a new Position<E> as a local variable.
	 * Look at the NDS4 documentation for LinkedBinaryTree for how to add a
	 * new Position<E> to the tree.
	 * 
	 * This method must run in constant O(1) worst-case time.
	 * 
	 * @param element to be added to the tree as the new last node
	 * @return the Position of the newly inserted element
	 */
	@Override
	public Position<E> add(E element) {
		if(node_num==0){
			tree_.addRoot(element);
			line.addLast(tree_.root());
			node_num++;
			return tree_.root();
		}
		else if(!(tree_.hasLeft(line.getFirst()))) {
			Position<E> save=tree_.insertLeft(line.getFirst(),element);
			line.addLast(save);
			node_num++;
			return save;
		}
		else {
			Position<E> save=tree_.insertRight(line.getFirst(),element);
 			line.addLast(save);
 			line.removeFirst();
			node_num++;
			updateList();
			return save;
		}
	}

	/**
	 * Removes and returns the element stored in the last node of the tree.
	 * 
	 * This method must run in constant O(1) worst-case time.
	 * 
	 * @return the element formerly stored in the last node (prior to its removal)
	 * @throws EmptyTreeException if the tree is empty and no last node exists
	 */
	@Override
	public E remove() throws EmptyTreeException {
		if(node_num==0) {
			throw new EmptyTreeException("You cannot remove from an empty tree."); 
		}
		if(node_num==1) {
			E save1=tree_.remove(line.getFirst());
			tree_= new LinkedBinaryTree<E>();
			line = new NodeDeque<Position<E>>();
			node_num=0;
			return save1;
		}
		//adding its parent to the front of the Deque
		Position<E> toRemove=line.removeLast();
		Position<E> parent=tree_.parent(toRemove);
		if(tree_.hasRight(parent)) {
			node_num--;
			return tree_.remove(tree_.right(parent));
		}
		else {
			node_num--;
			line.addFirst(parent);
			
			return tree_.remove(tree_.left(parent));
		}
	}
	
	//most of my "helper" methods are getters accessed in MyHeap so they're public
	
	public int size(){
		return node_num;
	}
	
	public void swap(Position<E> one, Position<E> two) {
		tree_.swapElements(one,two);
	}
	
	public E return_min(){
		return tree_.root().element();
	}
	
	public Position<E> return_newest(){
		return line.getLast();
	}
	
	public Position<E> root(){
		return tree_.root();
	}
	
	public Position<E> right(Position<E> i){
		if(tree_.hasRight(i)) {
			return tree_.right(i);
		}
		else {
			return null;
		}
	}
	
	public Position<E> left(Position<E> i){
		return tree_.left(i);
	}
	
	//removes unnesscary elements from deque
	private void updateList() {
		if(tree_.hasLeft(line.getFirst())&&tree_.hasRight(line.getFirst())) {
			line.removeFirst();
		}
	}
	
	public void downheap(Position<E> i, int j) {
		if(j<0) {
			swap(i,tree_.left(i));
		}
		else {
			swap(i,tree_.right(i));
		}
	}
	
}
