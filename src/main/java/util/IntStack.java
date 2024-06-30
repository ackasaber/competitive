package util;

public final class IntStack {
	private int[] array;
	private int length;
	
	public IntStack(int capacity) {
		array = new int[capacity];
		length = 0;
	}
	
	public void push(int x) {
		array[length++] = x;
	}
	
	public int peek() {
		return array[length - 1];
	}
	
	public int pop() {
		return array[--length];
	}
	
	public boolean isEmpty() {
		return length == 0;
	}
}