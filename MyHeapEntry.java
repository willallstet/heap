

import net.datastructures.Entry;
import net.datastructures.Position;

/**
 * Represents a key/value pair to be stored in a data 
 * structure, such as a heap. Entry<K,V> is a very 
 * limited accessing interface, so you may wish to add 
 * additional methods. In particular, think about the 
 * relationship of the Entry<K,V> to its location in 
 * the heap's binary tree. All methods must run in O(1)
 * time.
 *
 * Feel free to add additional comments. 
 */

public class MyHeapEntry<K,V> implements Entry<K,V> {
	private K key;
	private V val;
	private Position<MyHeapEntry<K,V>> pos;

	/** 
	 * Default constructor. You may wish to modify the parameters.
	 */
	public MyHeapEntry(K setKey, V setVal) {
		key=setKey;
		val=setVal;
	}
	
	/**
	 * @return the key stored in this entry 
	 */
	public K getKey() {
		return key;
	}

	/** 
	 * @return the value stored in this entry 
	 */
	public V getValue() {
		return val;
	}
	
	public void setValue(V setVal) {
		val=setVal;
	}
	
	public void setKey(K setKey) {
		key=setKey;
	}
	
	public void setPos(Position<MyHeapEntry<K,V>> pos_) {
		pos=pos_;
	}
	
	public Position<MyHeapEntry<K,V>> getPos() {
		return pos;
	}
	/* Add any additional methods here */

}
