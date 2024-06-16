package coderun.graph;

/**
 * An integer stack implementation.
 */
final class IntStack {
	private int[] array;
	private int length;
	
	/**
	 * Creates a stack with the given capacity.
	 * 
	 * @param capacity maximum number of elements in the created stack
	 */
	public IntStack(int capacity) {
		array = new int[capacity];
		length = 0;
	}
	
	/**
	 * Puts the element on top of the stack.
	 * 
	 * @param x the element to add
	 */
	public void push(int x) {
		array[length++] = x;
	}
	
	/**
	 * Examines the stack top.
	 * 
	 * @return the top element on the stack
	 */
	public int peek() {
		return array[length - 1];
	}
	
	/**
	 * Removes the top element from the stack.
	 */
	public int pop() {
		length--;
		return array[length];
	}
	
	/**
	 * Checks the stack for elements.
	 * 
	 * @return <code>true</code> when the stack is empty, <code>false</code>
	 * otherwise
	 */
	public boolean isEmpty() {
		return length == 0;
	}
}