package leetcode.simple;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TextJustification {

	public static void main(String[] args) throws IOException {
		var lines1 = fullJustify(new String[] {
				"This", "is", "an", "example", "of", "text", "justification."
		}, 16);
		show(lines1);
		var lines2 = fullJustify(new String[] {
				"What", "must", "be", "acknowledgment", "shall", "be"	
		}, 16);
		show(lines2);
		var lines3 = fullJustify(new String[] {
				"Science", "is", "what", "we", "understand", "well", "enough",
				"to", "explain", "to", "a", "computer.", "Art", "is",
				"everything", "else", "we", "do"
		}, 20);
		show(lines3);
	}
	
	private static void show(List<String> lines) {
		int width = 0;
		for (var line: lines) {
			System.out.print('|');
			System.out.print(line);
			System.out.println('|');
			width = line.length();
		}
		for (int i = 0; i < width + 2; i++)
			System.out.print('-');
		System.out.println();
	}

	public static List<String> fullJustify(String[] words, int maxWidth) {
		assert words.length != 0;
        var lines = new ArrayList<String>();
        var line = new LineWindow(words);

        for (String word: words) {
        	assert !word.isEmpty();
        	assert word.length() <= maxWidth;
        	if (line.affords(word, maxWidth))
        		line.addNext();
        	else {
        		lines.add(line.justifyFull(maxWidth));
        		line.addNext();
        	}
        }
        
        lines.add(line.justifyLeft(maxWidth));
        return lines;
	}
	
	// I had to merge this class into the parent class for the submission
	// since the class loading kills the run time.
	private static class LineWindow {
		private final String[] words;
		private int start;
		private int end;
		private int minLength;
		
		public LineWindow(String[] words) {
			this.words = words;
			start = 0;
			end = 0;
			minLength = 0;
		}
		
		public boolean affords(String word, int maxWidth) {
			if (minLength == 0)
				return word.length() <= maxWidth;
			
			return minLength + 1 + word.length() <= maxWidth;
		}
		
		public void addNext() {
			String word = words[end++];
			
			if (minLength == 0)
				minLength = word.length();
			else
				minLength += 1 + word.length();
		}
		
		public String justifyFull(int maxWidth) {
			assert start < end;
			var buffer = new StringBuilder(maxWidth);
			var firstWord = words[start++];
			buffer.append(firstWord);
			
			if (start == end)
				padRight(buffer, maxWidth);
			else {
				int extraSpaces = maxWidth - minLength;
				int slotCount = end - start;
				int eachSlotPad = extraSpaces / slotCount;
				int extraPadCount = extraSpaces % slotCount;
				// First extraPadCount slots get eachSlotPad + 2 spaces,
				// and the rest gets eachSlotPad + 1 spaces.
				
				for (int i = 0; i < extraPadCount; i++) {
					addSpaces(buffer, eachSlotPad + 2);
					buffer.append(words[start++]);
				}
				
				while (start < end) {
					addSpaces(buffer, eachSlotPad + 1);
					buffer.append(words[start++]);
				}
			}
			
			minLength = 0;
			return buffer.toString();
		}
		
		public String justifyLeft(int maxWidth) {
			var buffer = new StringBuilder(maxWidth);
			assert start < end;
			buffer.append(words[start++]);
			
			while (start < end) {
				buffer.append(' ');
				buffer.append(words[start++]);
			}
			
			padRight(buffer, maxWidth);
			return buffer.toString();
		}
		
		private static void padRight(StringBuilder buffer, int length) {
			while (buffer.length() < length)
				buffer.append(' ');
		}
		
		private static void addSpaces(StringBuilder buffer, int pad) {
			for (int i = 0; i < pad; i++)
				buffer.append(' ');
		}
	}
}
