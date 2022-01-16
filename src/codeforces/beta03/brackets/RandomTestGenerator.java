package codeforces.beta03.brackets;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RandomTestGenerator {

	public static void main(String[] args) throws IOException {
		if (args.length != 3) {
			System.out.println("Arguments: INPUT-PATH OUTPUT-PATH N");
		}

		var inputPath = Path.of(args[0]);
		var outputPath = Path.of(args[1]);
		var n = Integer.parseInt(args[2]);
		var generator = new CorrectBracketingGenerator(n);
		generator.generate();
		var answer = generator.toString();
		var input = new StringBuilder(answer);
		var random = ThreadLocalRandom.current();
		var openCost = new ArrayList<Integer>();
		var closeCost = new ArrayList<Integer>();
		long totalMinCost = 0L;
		
		for (int i = 0; i < n; ++i) {
			if (random.nextFloat() > 0.5F) {
				int baseCost = random.nextInt(999_999);
				
				if (input.charAt(i) == '(') {
					openCost.add(baseCost);
					closeCost.add(baseCost + 1);
				} else {
					openCost.add(baseCost + 1);
					closeCost.add(baseCost);
				}
				
				input.setCharAt(i, '?');				
				totalMinCost += baseCost;
			}
		}

		try (var printWriter = new PrintWriter(Files.newBufferedWriter(inputPath))) {
			printWriter.println(input);

			for (int i = 0; i < openCost.size(); ++i) {
				printWriter.println(openCost.get(i) + " " + closeCost.get(i));
			}
		}

		try (var printWriter = new PrintWriter(Files.newBufferedWriter(outputPath))) {
			printWriter.println(totalMinCost);
			printWriter.println(answer);
		}
	}

	private static class CorrectBracketingGenerator {
		private StringBuilder buffer;
		private Random random;

		public CorrectBracketingGenerator(int n) {
			if (n % 2 != 0) {
				throw new IllegalArgumentException("n % 2 != 0");
			}
			
			buffer = new StringBuilder();
			random = ThreadLocalRandom.current();
			
			for (int i = 0; i < n; ++i) {
				buffer.append('?');
			}
		}
		
		@Override
		public String toString() {
			return buffer.toString();
		}

		public void generate() {
			putBrackets(0, buffer.length() / 2);
		}
		
		/**
		 * Put brackets in the given range.
		 * 
		 * @param i the starting index
		 * @param m how many bracket pairs to put right after the starting index
		 */
		public void putBrackets(int i, int m) {
			if (m == 1) {
				buffer.setCharAt(i, '(');
				buffer.setCharAt(i + 1, ')');
			} else if (m > 0) {
				int split = random.nextInt(m);
				
				if (split == 0) {
					buffer.setCharAt(i, '(');
					buffer.setCharAt(i + 2 * m - 1, ')');
					putBrackets(i + 1, m - 1);
				} else {
					putBrackets(i, split);
					putBrackets(i + 2 * split, m - split);
				}
			}
		}
	}
}
