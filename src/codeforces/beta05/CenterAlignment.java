package codeforces.beta05;

// B. Center Alignment

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UncheckedIOException;
import java.util.List;

import static java.nio.charset.StandardCharsets.US_ASCII;
import static java.util.stream.Collectors.toList;

public class CenterAlignment {
    public static void main(String[] args) {
        var reader = new BufferedReader(new InputStreamReader(System.in, US_ASCII));
        var lines = reader.lines().collect(toList());
        
        var writer = new BufferedWriter(new OutputStreamWriter(System.out, US_ASCII));
        printCentered(writer, lines);
    }
    
    private static void printCentered(BufferedWriter writer, List<String> lines) {
        try {
            int maxLength = getMaxLength(lines);
            writeRepeat(writer, '*', maxLength + 2);
            writer.newLine();
            int adjustment = 0;
            
            for (var line: lines) {
                writer.append('*');
                int padding = maxLength - line.length();
                int leftPadding = padding / 2;
                
                if (padding % 2 == 1) {
                    leftPadding += adjustment;
                    adjustment = 1 - adjustment;
                }
                
                int rightPadding = padding - leftPadding;
                writeRepeat(writer, ' ', leftPadding);
                writer.append(line);
                writeRepeat(writer, ' ', rightPadding);
                writer.append('*');
                writer.newLine();
            }
            
            writeRepeat(writer, '*', maxLength + 2);
            writer.flush();
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }
    
    private static int getMaxLength(List<String> lines) {
        int max = 0;
        
        for (var line: lines) {
            if (line.length() > max)
                max = line.length();
        }
        
        return max;
    }
    
    private static void writeRepeat(BufferedWriter writer, char c, int repeat) throws IOException {
        for (int i = 0; i < repeat; i++)
            writer.append(c);
    }
}
