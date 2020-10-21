

import java.util.ArrayList;
import java.util.Comparator;
import net.datastructures.CompleteBinaryTree;
import net.datastructures.DefaultComparator;
import net.datastructures.EmptyPriorityQueueException;
import net.datastructures.Entry;
import net.datastructures.InvalidEntryException;
import net.datastructures.InvalidKeyException;
import net.datastructures.Position;
import net.datastructures.AdaptablePriorityQueue;
import support.heap.HeapWrapper;

/**
 * An implementation of an adaptable priority queue by 
 * means of a heap. Be certain that your running times 
 * match those specified in the program documentation, 
 * and remember that the running time of a "called" 
 * method sets the minimum running time of the "calling" 
 * method. Feel free to add additional comments. 
 */

public class MyHeap<K,V> implements HeapWrapper<K,V>, AdaptablePriorityQueue<K,V> {
	
	// This the underlying data structure of your heap
	private MyLinkedHeapTree<MyHeapEntry<K,V>> _tree;
	private Comparator<K> _compare;
	private int node_num=0;
	/** 
	 * Creates an empty heap with the given comparator. 
	 * 
	 * @param the comparator to be used for heap keys
	 */
	public MyHeap(Comparator<K> comparator) {
		_tree= new MyLinkedHeapTree<MyHeapEntry<K,V>>();
		this._compare=comparator;
	}

	/**
	 * Sets the comparator used for comparing items in the heap to the
	 * comparator passed in.
	 * 
	 * @param comparator, the comparator to be used for heap keys
	 * @throws IllegalStateException if priority queue is not empty
	 * @throws IllegalArgumentException if null comparator is passed in
	 */
	public void setComparator(Comparator<K> comparator)
			throws IllegalStateException, IllegalArgumentException {
		if(comparator==null) {
			throw new IllegalArgumentException("Comparator cannot be null.");
		}
		if(!(_tree.size()==0)) {
			throw new IllegalStateException("A tree is already in place!");
		}
		this._compare=comparator;
		throw new IllegalStateException();
	}

	/**
	 * Returns a CompleteBinaryTree that will allow the visualizer 
	 * access to private members, shattering encapsulation, but 
	 * allowing visualization of the heap. This is the only method 
	 * needed to satisfy HeapWrapper interface implementation.
	 *
	 * Do not modify or call this method. It is solely
	 * necessary for the visualizer to work properly.
	 * 
	 * @return the underlying binary tree on which the heap is based
	 */
	public CompleteBinaryTree<MyHeapEntry<K,V>> getTree() {
		return _tree;
	}
	
	/** 
	 * Returns the size of the heap.
	 * This method must run in O(1) time.
	 *
	 * @return an int representing the number of entries stored
	 */
	public int size() {
		return node_num;
	}

	/** 
	 * Returns whether the heap is empty.
	 * This method must run in O(1) time.
	 * 
	 * @return true if the heap is empty; false otherwise
	 */
	public boolean isEmpty() {
		if(size()==0) {
			return true;
		}
		return false;
	}

	/** 
	 * Returns but does not remove the entry with minimum key.
	 * This method must run in O(1) time.
	 * 
	 * @return the entry with the minimum key in the heap
	 * @throws EmptyPriorityQueueException if the heap is empty
	 */
	public Entry<K,V> min() throws EmptyPriorityQueueException {
		if(_tree.size()==0) {
			throw new EmptyPriorityQueueException("The heap is empty!");
		}
		return _tree.return_min();
	}

	/** 
	 * Inserts a key-value pair and returns the entry created.
	 * This method must run in O(log n) time.
	 *
	 * @param key to be used as the key the heap is sorting with
	 * @param value stored with the associated key in the heap
	 * @return the entry created using the key/value parameters
	 * @throws InvalidKeyException if the key is not suitable for this heap
	 */
	public Entry<K,V> insert(K key, V value) throws InvalidKeyException {
		try {
			this._compare.compare(key,key);
		}
		catch(NullPointerException|ClassCastException e){
			throw new InvalidKeyException("The key is invalid.");
		}
		MyHeapEntry<K,V> newEnt= new MyHeapEntry<K,V>(key,value);
		Position<MyHeapEntry<K,V>> pos=_tree.add(newEnt);
		pos.element().setPos(pos);
		node_num++;
		if(_tree.size()>1) {
		upHeap(pos,key);
		}
		return newEnt; 
	}

	/** 
	 * Removes and returns the entry with the minimum key.
	 * This method must run in O(log n) time.
	 * 
	 * @return the entry with the with the minimum key, now removed 
	 * @throws EmptyPriorityQueueException if the heap is empty
	 */
	public Entry<K,V> removeMin() throws EmptyPriorityQueueException {
		if(_tree.size()==0) {
			throw new EmptyPriorityQueueException("Heap is empty.");
		}
		Position<MyHeapEntry<K,V>> swapPos= _tree.root();
		_tree.swap(swapPos, _tree.return_newest());
		MyHeapEntry<K,V> save=_tree.remove();
		//checkedEntry.setPos(save.getPos());
		node_num--;
		if(node_num!=0) {
			Position<MyHeapEntry<K,V>> pos=_tree.root();
			if(_tree.size()>1) {
				downHeap(pos);
			}
		}
		return save;
	}

	/** 
	 * Removes and returns the given entry from the heap.
	 * This method must run in O(log n) time.
	 *
	 * @param entry to be removed from the heap
	 * @return the entry specified for removal by the parameter, now removed
	 * @throws InvalidEntryException if the entry cannot be removed from this heap
	 */
	public Entry<K,V> remove(Entry<K,V> entry) throws InvalidEntryException {
		MyHeapEntry<K,V> checkedEntry = this.checkAndConvertEntry(entry);
		if(_tree.size()==0) {
			throw new EmptyPriorityQueueException("Heap is empty.");
		}
		Position<MyHeapEntry<K,V>> swapPos= checkedEntry.getPos();
		_tree.swap(swapPos, _tree.return_newest());
		MyHeapEntry<K,V> save=_tree.remove();
		checkedEntry.setPos(save.getPos());
		node_num--;
		Position<MyHeapEntry<K,V>> pos=checkedEntry.getPos();
		if(_tree.size()>1) {
			downHeap(pos);
		}
		return save;
	}

	/** 
	 * Replaces the key of the given entry.
	 * This method must run in O(log n) time.
	 *
	 * @param entry within which the key will be replaced
	 * @param key to replace the existing key in the entry
	 * @return the old key formerly associated with the entry
	 * @throws InvalidEntryException if the entry is invalid
	 * @throws InvalidKeyException if the key is invalid
	 */
	public K replaceKey(Entry<K,V> entry, K key) throws InvalidEntryException, InvalidKeyException {
		try {
			this._compare.compare(key,key);
		}
		catch(ClassCastException e){
			throw new InvalidKeyException("The key is invalid.");
		}
		if(entry==null) {
			throw new InvalidEntryException("Entry is invalid.");
		}
		MyHeapEntry<K,V> checkedEntry = this.checkAndConvertEntry(entry);
		K save=checkedEntry.getKey();
		checkedEntry.setKey(key);
		if(this._compare.compare(key,save)==0) {
			return save;
		}
		else if(this._compare.compare(key,save)>0) {
			downHeap(checkedEntry.getPos());
		}
		else {
			upHeap(checkedEntry.getPos(),key);
		}
		return save;
	}

	/** 
	 * Replaces the value of the given entry.
	 * This method must run in O(1) time.
	 *
	 * @param entry within which the value will be replaced
	 * @param value to replace the existing value in the entry
	 * @return the old value formerly associated with the entry
	 * @throws InvalidEntryException if the entry cannot have its value replaced
	 */
	public V replaceValue(Entry<K,V> entry, V value) throws InvalidEntryException {		
		MyHeapEntry<K,V> checkedEntry = this.checkAndConvertEntry(entry);
		if(entry==null) {
			throw new InvalidEntryException("Entry is invalid.");
		}
		V save=checkedEntry.getValue();
		checkedEntry.setValue(value);
		return save;
	}
	

	/**
	 * Determines whether a given entry is valid and converts it to a
	 * MyHeapEntry. Don't change this method.
	 *
	 * @param entry to be checked for validity with respect to the heap
	 * @return the entry cast as a MyHeapEntry if considered valid 
	 *
	 * @throws InvalidEntryException if the entry is not of the proper class
	 */
	public MyHeapEntry<K,V> checkAndConvertEntry(Entry<K,V> entry)
			throws InvalidEntryException {
		if (entry == null || !(entry instanceof MyHeapEntry)) {
			throw new InvalidEntryException("Invalid entry");
		}
		return (MyHeapEntry<K, V>) entry;
	}
	
	private void downHeap(Position<MyHeapEntry<K,V>> pos_){
		Position<MyHeapEntry<K,V>> pos=pos_;
		if(_tree.isExternal(pos)) {
			return;
		}
		if(_tree.hasLeft(pos) && _tree.hasRight(pos)) {
			if(this._compare.compare(_tree.left(pos).element().getKey(),_tree.right(pos).element().getKey())<=0) {
				if(this._compare.compare(_tree.left(pos).element().getKey(),pos.element().getKey())<0) {
					//switch their positions
					_tree.left(pos).element().setPos(pos);
					pos.element().setPos(_tree.left(pos));
					//swap elements in the tree
					_tree.downheap(pos,-1);
					pos=_tree.left(pos);
					downHeap(pos);
				}
			}
			if(_tree.hasRight(pos)&&this._compare.compare(_tree.right(pos).element().getKey(),_tree.left(pos).element().getKey())<=0) {
				if(this._compare.compare(_tree.right(pos).element().getKey(),pos.element().getKey())<0) {
					//switch their positions
					_tree.right(pos).element().setPos(pos);
					pos.element().setPos(_tree.right(pos));
					//swap elements in the tree
					_tree.downheap(pos,1);
					pos=_tree.right(pos);
					downHeap(pos);
				}
			}
			return;
		}
		if(_tree.hasLeft(pos) && !(_tree.hasRight(pos))){
			if(this._compare.compare(_tree.left(pos).element().getKey(),pos.element().getKey())<0) {
				//switch their positions
				_tree.left(pos).element().setPos(pos);
				pos.element().setPos(_tree.left(pos));
				//swap elements in the tree
				_tree.downheap(pos,-1);
				pos=_tree.left(pos);
				downHeap(pos);
			}
			return;
		}
		if(_tree.hasRight(pos) && !(_tree.hasLeft(pos))){
			if(this._compare.compare(_tree.right(pos).element().getKey(),pos.element().getKey())<0) {
				//switch their positions
				_tree.right(pos).element().setPos(pos);
				pos.element().setPos(_tree.right(pos));
				//swap elements in the tree
				_tree.downheap(pos,1);
				pos=_tree.right(pos);
				downHeap(pos);
			}
			return;
		}
	}
	
	private void upHeap(Position<MyHeapEntry<K,V>> pos_, K key_){
		Position<MyHeapEntry<K,V>> pos=pos_;
		K key=key_;
		while((pos!=_tree.root())&&this._compare.compare(key,_tree.parent(pos).element().getKey())<0) {
			//updating position data
			pos.element().setPos(_tree.parent(pos));
			_tree.parent(pos).element().setPos(pos);
			//swapping in tree
			_tree.swap(pos,_tree.parent(pos));
			//updating for the while loop
			pos =_tree.parent(pos);
		}
	}
}
