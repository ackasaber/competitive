package spoj.graph.mintoposort;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class GraphGenerator {

	public static void main(String[] args) throws IOException {
		int n = Integer.parseInt(args[0]);
		int m = Integer.parseInt(args[1]);
		String filename = args[2];
		
		if (m > n * (n - 1) / 2) {
			throw new IllegalArgumentException("Too many arcs requested");
		}
		
		try (var writer = new FileWriter(filename);
		     var bufferedWriter = new BufferedWriter(writer);
		     var printer = new PrintWriter(bufferedWriter)) {
			printer.printf("%d %d%n", n, m);
			
			int arcCount = 0;
			
			for (int i = 0; i < n - 1 && arcCount < m; i++) {
				for (int j = i + 1; j < n && arcCount < m; j++) {
					printer.printf("%d %d%n", i + 1, j + 1);
					arcCount++;
				}
			}
		}
	}
}
