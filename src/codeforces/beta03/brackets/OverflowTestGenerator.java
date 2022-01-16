package codeforces.beta03.brackets;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class OverflowTestGenerator {

	public static void main(String[] args) throws IOException {
		if (args.length != 3) {
			System.out.println("Arguments: INPUT-PATH OUTPUT-PATH N");
		}
		
		var inputPath = Path.of(args[0]);
		var outputPath = Path.of(args[1]);
		var n = Integer.parseInt(args[2]);

		try (var inputWriter = Files.newBufferedWriter(inputPath)) {
			for (int i = 0; i < n; ++i) {
				inputWriter.append('?');
			}
			
			inputWriter.newLine();
			
			for (int i = 0; i < n; ++i) {
				inputWriter.append("1000000 1000000");
				inputWriter.newLine();
			}
		}
		
		try (var outputWriter = Files.newBufferedWriter(outputPath)) {
			outputWriter.append(n + "000000");
			outputWriter.newLine();
			
			for (int i = 0; i < n / 2; ++i) {
				outputWriter.append("()");
			}
			
			outputWriter.newLine();
		}
	}

}
