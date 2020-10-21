

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import net.datastructures.EmptyPriorityQueueException;
import net.datastructures.Entry;
import net.datastructures.InvalidEntryException;
import net.datastructures.InvalidKeyException;
import net.datastructures.Position;

/**
 * This class can be used to test the functionality of your MyHeap implementation.
 * You will find a few examples to guide you through the syntax of writing test cases.
 * Each test case uses its own heap instance to ensure that the test cases are independent 
 * of each other. All of the given examples should pass once you've implemented your heap.
 * 
 *
 * The annotation @Test before each test case is JUnit syntax. It basically lets the compiler know
 * that this is a unit test method. Use this annotation for *every* test method. This class is 
 * also like any other java class, so should you need to add private helper methods to use in your 
 * tests, you can do so, simply without the @Test annotation.
 * The general framework of a test case is:
 * 		- Name the test method descriptively, mentioning what is being tested (it is ok to have 
 * 		  slightly verbose method names here)
 * 		- Set-up the program state (ex: instantiate a heap and insert K,V pairs into it)
 * 		- Use assertions to validate that the progam is in the state you expect it to be
 * 
 * We've given you four example of test cases below that should help you understand syntax and the
 * general structure of tests.
 */

public class MyHeapTest {
	
	/**
	 * A simple test to ensure that insert() works.
	 */
	@Test
	public void testInsertOneElement() {
		// set-up
		MyHeap<Integer, String> heap = new MyHeap<Integer, String>(new IntegerComparator());
		heap.insert(1, "A");
		
		// Assert that your data structure is consistent using 
		// assertThat(actual, is(expected))
		assertThat(heap.size(), is(1));
		assertThat(heap.min().getKey(), is(1));
	}

	/**
	 * This is an example to check that the order of the heap is sorted as per the keys
	 * by comparing a list of the actual and expected keys.
	 */
	@Test
	public void testRemoveMinHeapOrderUsingList() {
	MyHeap<Integer, String> heap = new MyHeap<Integer, String>(new IntegerComparator());
		heap.insert(11, "A");
		heap.insert(13, "B");
		heap.insert(64, "C");
		heap.insert(16, "D");
		heap.insert(44, "E");
		
		// the expected ordering that keys come in
		List<Integer> expectedKeys = Arrays.asList(11, 13, 16, 44, 64);
		
		// the actual ordering of keys in the heap
		List<Integer> actualKeys = new ArrayList<Integer>();
		while(!heap.isEmpty()) {
			actualKeys.add(heap.removeMin().getKey());
		}
		
		// check that the actual ordering matches the expected ordering by using one assert
		// Note that assertThat(actual, is(expected)), when used on lists/ arrays, also checks that the
		// ordering is the same.
		assertThat(actualKeys, is(expectedKeys));
	}
	
	/**
	 * This is an example of testing heap ordering by ensuring that the min key is always at the root
	 * by checking it explicitly each time, using multiple asserts rather than a list.
	 */
	@Test
	public void testRemoveMinHeapOrder() {
		MyHeap<Integer, String> heap = new MyHeap<Integer, String>(new IntegerComparator());
		heap.insert(11, "A");
		heap.insert(13, "B");
		heap.insert(64, "C");
		heap.insert(16, "D");
		heap.insert(44, "E");
		
		
		// test the heap ordering by asserting on all elements
		assertThat(heap.removeMin().getKey(), is(11));
		assertThat(heap.removeMin().getKey(), is(13));
		assertThat(heap.removeMin().getKey(), is(16));
		assertThat(heap.removeMin().getKey(), is(44));
		assertThat(heap.removeMin().getKey(), is(64));
	}
	

	/**
	 * This is an example of how to test whether an exception you expect to be thrown on a certain line of code
	 * is actually thrown. As shown, you'd simply add the expected exception right after the @Test annotation.
	 * This test will pass if the exception expected is thrown by the test and fail otherwise.
	 * 
	 * Here, we're checking to see if an IllegalStateException is being correctly thrown after we try to
	 * call setComparator on a non-empty heap.
	 */
	@Test(expected=IllegalStateException.class)
	public void testSetComparatorThrowsIllegalStateException() {
		MyHeap<Integer, String> heap = new MyHeap<Integer, String>(new IntegerComparator());
		heap.insert(1, "A");
		heap.setComparator(new IntegerComparator());
	}
	
	
	/**
	 * TODO: add your tests below!
	 * Think of edge cases and testing for exceptions (if applicable) for insert, remove, min, removeMin, size and
	 * your helper methods (if applicable).
	 */
	
	/**
	 * This test tests that the min function is operating correctly
	 */
	@Test
	public void testMinFunction() {
		MyHeap<Integer, String> heap = new MyHeap<Integer, String>(new IntegerComparator());
		heap.insert(11, "A");
		heap.insert(13, "B");
		heap.insert(64, "C");
		heap.insert(16, "D");
		heap.insert(44, "E");
		
		
		// test the heap ordering by asserting on all elements
		assertThat(heap.min().getKey(), is(11));
	}
	/**
	 * This test tests the edge case for when you're looking for a min in an empty tree
	 */
	@Test(expected=EmptyPriorityQueueException.class)
	public void testMinThrowsEmptyPriority() {
		MyHeap<Integer, String> heap = new MyHeap<Integer, String>(new IntegerComparator());
		heap.min();
	}
	/**
	 * This test makes sure that if you have an invalid key, it will throw the right exception
	 */
	@Test(expected=InvalidKeyException.class)
	public void testInsertInvalidKey() {
		MyHeap<Integer, String> heap = new MyHeap<Integer, String>(new IntegerComparator());
		heap.insert(null,"A");
	}
	/**
	 * This test simultaneously checks that the remove, insert and size functions are working as expected.
	 * I wanted to make sure size would get intermediate values, not just the max and 0
	 */
	@Test
	public void testRemoveAndInsertAndSize() {
		MyHeap<Integer, String> heap = new MyHeap<Integer, String>(new IntegerComparator());
		Entry<Integer,String> one=heap.insert(0, "A");
		Entry<Integer,String> two=heap.insert(13, "B");
		Entry<Integer,String> three=heap.insert(-14, "C");
		Entry<Integer,String> four=heap.insert(84, "D");
		Entry<Integer,String> five=heap.insert(30, "E");
		heap.insert(48,"F");
		heap.insert(66,"G");
		heap.insert(68,"H");
		
		assertThat(heap.size(), is(8));
		assertThat(heap.remove(one).getValue(), is("A"));
		assertThat(heap.remove(two).getKey(), is(13));
		assertThat(heap.remove(three).getValue(), is("C"));
		assertThat(heap.remove(four).getKey(), is(84));
		assertThat(heap.remove(five).getValue(), is("E"));

		assertThat(heap.size(), is(3));
	}
	/**
	 * This test checks that the remove function throws the correct error when you try to remove from 
	 * an empty tree
	 */
	@Test(expected=EmptyPriorityQueueException.class)
	public void RemovingFromEmptyHeap() {
		MyHeap<Integer, String> heap = new MyHeap<Integer, String>(new IntegerComparator());
		Entry<Integer,String> one=heap.insert(0, "A");
		heap.remove(one);
		heap.remove(one);
	}
	/**
	 * This test makes sure the replace key function identifies
	 * the edge case when there is an invalid key
	 */
	@Test(expected=InvalidEntryException.class)
	public void InvalidEntryExceptionReplaceKey() {
		MyHeap<Integer, String> heap = new MyHeap<Integer, String>(new IntegerComparator());
		Entry<Integer,String> one=heap.insert(0, "A");
		heap.replaceKey(null, 10);
	}
	/**
	 * this test tests the basic function of replace key
	 */
	@Test
	public void replaceKeyTest() {
		MyHeap<Integer, String> heap = new MyHeap<Integer, String>(new IntegerComparator());
		Entry<Integer,String> one=heap.insert(0, "A");
		heap.replaceKey(one,9);
		assertThat(heap.remove(one).getKey(), is(9));
	}
	/**
	 * This test makes sure that an invalid entry is raised in the case one is passed into replace key
	 */
	@Test(expected=InvalidEntryException.class)
	public void InvalidEntryExceptionReplaceValue() {
		MyHeap<Integer, String> heap = new MyHeap<Integer, String>(new IntegerComparator());
		Entry<Integer,String> one=heap.insert(0, "A");
		heap.replaceKey(null, 10);
	}
	
	/**
	 * This tests the basic function of replace value
	 */
	@Test
	public void replaceValueTest() {
		MyHeap<Integer, String> heap = new MyHeap<Integer, String>(new IntegerComparator());
		Entry<Integer,String> one=heap.insert(0, "A");
		heap.replaceValue(one,"B");
		assertThat(heap.remove(one).getValue(), is("B"));
	}
}
