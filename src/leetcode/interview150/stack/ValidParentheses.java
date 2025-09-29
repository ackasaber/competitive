package leetcode.stack;

// Task 20. Valid Parentheses

import static java.nio.charset.StandardCharsets.US_ASCII;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// A classic application of the stack data structure.
public class ValidParentheses {

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

	// Checks the bracketing in the parentheses string.
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
	
	private static final class BracketStack {
		private char[] stack;
		private int top;
		
		public BracketStack(int capacity) {
			stack = new char[capacity];
			top = 0;
		}
		
		public boolean isEmpty() {
			return top == 0;
		}
		
		public void push(char c) {
			stack[top++] = c;
		}
		
		public char pop() {
			return stack[--top];
		}
	}
}
