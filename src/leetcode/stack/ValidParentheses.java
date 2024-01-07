package leetcode.stack;

/* Task 20. Valid Parentheses */

import static java.nio.charset.StandardCharsets.US_ASCII;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * The task solution.
 * 
 * <p>A classic application of the stack data structure.</p>
 */
public class ValidParentheses {

	/**
	 * Reads the parentheses string from the standard input and reports
	 * whether the bracketing is valid to the standard output.
	 * 
	 * @param args command line arguments (unused)
	 * @throws IOException on input errors
	 */
	public static void main(String[] args) throws IOException {
		var reader = new InputStreamReader(System.in, US_ASCII);
		var bufferedReader = new BufferedReader(reader);
		String line = bufferedReader.readLine();
		
		if (isValid(line)) {
			System.out.println("VALID");
		} else {
			System.out.println("INVALID");
		}
	}

	/**
	 * Checks the bracketing in the parentheses string.
	 * 
	 * @param s a string consisting of round, square and curly parentheses
	 * @return <code>true</code> if the bracketing is valid,
	 * <code>false</code> otherwise
	 */
	public static boolean isValid(String s) {
		int n = s.length();
		var stack = new BracketStack(n);
		
		for (int i = 0; i < n; i++) {
			char c = s.charAt(i);
			
			switch (c) {
			case '(': case '[': case '{':
				stack.push(c);
				break;
				
			case ')':
				if (stack.isEmpty() || stack.pop() != '(') {
					return false;
				}
				break;
				
			case ']':
				if (stack.isEmpty() || stack.pop() != '[') {
					return false;
				}
				break;
				
			case '}':
				if (stack.isEmpty() || stack.pop() != '{') {
					return false;
				}
			}
		}
		
		return stack.isEmpty();
	}
	
	/**
	 * A character stack implementation.
	 */
	private static final class BracketStack {
		private char[] stack;
		private int top;
		
		/**
		 * Creates an empty stack.
		 * 
		 * @param capacity the maximum capacity of the stack
		 */
		public BracketStack(int capacity) {
			stack = new char[capacity];
			top = 0;
		}
		
		/**
		 * Checks if the stack is empty.
		 * 
		 * @return <code>true</code> if the stack is empty,
		 * <code>false</code> otherwise
		 */
		public boolean isEmpty() {
			return top == 0;
		}
		
		/**
		 * Places a character on top of the stack.
		 * 
		 * @param c the character to place
		 */
		public void push(char c) {
			stack[top++] = c;
		}
		
		/**
		 * Removes the character from the top of the stack.
		 * 
		 * @return the removed character
		 */
		public char pop() {
			return stack[--top];
		}
	}
}
