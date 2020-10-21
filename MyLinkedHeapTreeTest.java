

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.Test;

import net.datastructures.EmptyTreeException;


/**
 * This class should be used to test the functionality of your MyLinkedHeapTree implementation.
 * You will find a few examples to guide you through the syntax of writing test cases.
 * Each test case uses its own tree instance to ensure that the test cases are independent 
 * of each other. All of the given examples should pass once you've implemented your tree methods.
 * 
 *
 * The annotation @Test before each test case is JUnit syntax, it basically lets the compiler know
 * that this is a unit test method. Use this annotation for every test method. This class is also like
 * any other java class, so should you need to add private helper methods to use in your tests, 
 * you can do so, simply without the annotations as you usually would write a method.
 * The general framework of a test case is:
 * 		- Name the test method descriptively, mentioning what is being tested (it is ok to have slightly verbose method names here)
 * 		- Set-up the program state (ex: instantiate a heap and insert K,V pairs into it)
 * 		- Use assertions to validate that the progam is in the state you expect it to be
 */
public class MyLinkedHeapTreeTest {
	
	/**
	 * A simple example of checking that the add() method adds the first element to the tree.
	 */
	@Test
	public void testAddOneElement() {
		MyLinkedHeapTree<Integer> tree = new MyLinkedHeapTree<Integer>();
		tree.add(1);
		
		/* These are two ways of asserting the same thing
		 * Use whichever you find more convenient out of
		 * assertThat(actual, is(expected)) and
		 * assertTrue(boolean)
		 * Take a look at the JUnit docs for more assertions you might want to use.
		 */
		assertThat(tree.size(), is(1));
		assertTrue(tree.size() == 1);
	}
	
	/**
	 * This is an example of how to test whether an exception you expect to be thrown on a certain line of code
	 * is actually thrown. As shown, you'd simply add the expected exception right after the @Test annotation.
	 * This test will pass if the exception expected is thrown by the test and fail otherwise.
	 */
	@Test(expected = EmptyTreeException.class)
	public void testRemoveThrowsEmptyTreeException() {
		MyLinkedHeapTree<Integer> tree = new MyLinkedHeapTree<Integer>();
		tree.remove();
	}
	
	/**
	 * TODO: Write your own tests below!
	 * Think of edge cases for add/remove and try to test your helper methods (if applicable).
	 */
	/**
	 * this test makes sure an empty tree exception is raised in the event you try and
	 * call remove on an empty tree
	 */
	@Test(expected = EmptyTreeException.class)
	public void testRemoveThrowsEmptyTreeExceptionPart2() {
		MyLinkedHeapTree<Integer> tree = new MyLinkedHeapTree<Integer>();
		tree.add(1);
		tree.remove();
		tree.remove();
	}
	
	/**
	 * This test adds one element to the tree then removes it
	 */
	@Test
	public void removeOneElement() {
		MyLinkedHeapTree<Integer> tree = new MyLinkedHeapTree<Integer>();
		tree.add(1);
		assertThat(tree.size(), is(1));
		assertTrue(tree.size() == 1);
		Integer save = tree.remove();
		assertThat(tree.size(), is(0));
		assertTrue(tree.size() == 0);
		assertThat(save, is(1));
	}
	/**
	 * This test tests remove (and add) on multiple entires
	 */
	@Test
	public void removeMultipleElements() {
		MyLinkedHeapTree<Integer> tree = new MyLinkedHeapTree<Integer>();
		tree.add(1);
		tree.add(2);
		tree.add(3);
		tree.add(4);
		tree.add(5);
		tree.add(6);
		tree.add(7);
		assertThat(tree.size(), is(7));
		assertTrue(tree.size() == 7);
		Integer save = tree.remove();
		assertThat(tree.size(), is(6));
		assertTrue(tree.size() == 6);
		assertThat(save, is(7));
		Integer save2 = tree.remove();
		assertThat(tree.size(), is(5));
		assertTrue(tree.size() == 5);
		assertThat(save2, is(6));
		Integer save3 = tree.remove();
		assertThat(tree.size(), is(4));
		assertTrue(tree.size() == 4);
		assertThat(save3, is(5));
		Integer save4 = tree.remove();
		assertThat(tree.size(), is(3));
		assertTrue(tree.size() == 3);
		assertThat(save4, is(4));
		Integer save5 = tree.remove();
		assertThat(tree.size(), is(2));
		assertTrue(tree.size() == 2);
		assertThat(save5, is(3));
		Integer save6 = tree.remove();
		assertThat(tree.size(), is(1));
		assertTrue(tree.size() == 1);
		assertThat(save6, is(2));
		Integer save7 = tree.remove();
		assertThat(tree.size(), is(0));
		assertTrue(tree.size() == 0);
		assertThat(save7, is(1));
	}
}
