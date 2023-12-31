package codeforces.beta02;

/* Task 2A. Winner */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * The task solution using a radix tree.
 */
public final class WinnerTree {

	/**
	 * A record about score change.
	 */
	private static final class ScoreRecord {
		/**
		 * The player name.
		 */
		public String playerName;
		/**
		 * The change of the score after a single game move.
		 */
		public int scoreChange;

		/**
		 * Creates the record about one game move.
		 *
		 * @param playerName  the player name
		 * @param scoreChange the score change
		 */
		public ScoreRecord(String playerName, int scoreChange) {
			this.playerName = playerName;
			this.scoreChange = scoreChange;
		}
	}
	
	/**
	 * Reads the score history, finds the winner and writes its name.
	 *
	 * @param args the commmand line arguments (unused)
	 */
	public static void main(String[] args) {
		var reader = new InputStreamReader(System.in, StandardCharsets.US_ASCII);
		var bufferedReader = new BufferedReader(reader);
		@SuppressWarnings("resource")
		var scanner = new Scanner(bufferedReader);

		// Read the score change history.
		int n = scanner.nextInt();
		var scoreHistory = new ScoreRecord[n];

		for (int i = 0; i < n; ++i) {
			String playerName = scanner.next();
			int scoreChange = scanner.nextInt();
			scoreHistory[i] = new ScoreRecord(playerName, scoreChange);
		}

		// Compute the final scores.
		RadixTree scores = new RadixTree();
		for (var scoreRecord : scoreHistory) {
			var entry = scores.get(scoreRecord.playerName);
			entry.finalScore += scoreRecord.scoreChange;
		}

		// Find the maximum final score.
		int maxScore = scores.maxScore();

		// Find the first player who reached the maximum score during the game.
		for (var scoreRecord : scoreHistory) {
			var scoreEntry = scores.get(scoreRecord.playerName);
			scoreEntry.currentScore += scoreRecord.scoreChange;

			if (scoreEntry.currentScore >= maxScore && scoreEntry.finalScore == maxScore) {
				System.out.println(scoreRecord.playerName);
				break;
			}
		}
	}

	/**
	 * A radix tree node with information about the running scores.
	 */
	private static final class LetterNode {
		/**
		 * The last letter in the path from the root.
		 */
		private char letter;
		/**
		 * Links the sibling nodes in a list.
		 */
		private LetterNode nextSibling;
		/**
		 * The first child node.
		 */
		private LetterNode firstChild;
		/**
		 * True if the tree contains a player with the name corresponding
		 * to the letter sequence in the path from the root to this node.
		 * False for intermediate nodes.
		 */
		private boolean taken;
		/**
		 * The final score, computed on the first run through the score history.
		 */
		public int finalScore;
		/**
		 * The current score, computed on the second run through the score history to
		 * resolve possible ties.
		 */
		public int currentScore;
		
		/**
		 * Creates a new radix tree node.
		 * 
		 * @param letter the node (last) letter
		 * @param nextSibling the list of node siblings
		 */
		public LetterNode(char letter, LetterNode nextSibling) {
			this.letter = letter;
			this.nextSibling = nextSibling;
		}
		
	}

	/**
	 * The radix tree storing player records. The player record is identified
	 * by the player name.
	 */
	private static final class RadixTree {
		private LetterNode first;
		
		/**
		 * Searches for the letter node in the sibling list.
		 * 
		 * @param c a letter to search for
		 * @param entry the sibling list to search in
		 * @return a node with the given letter or <code>null</code> if not found
		 */
		private static LetterNode searchLetter(char c, LetterNode entry) {
			while (entry != null && entry.letter != c) {
				entry = entry.nextSibling;
			}
			
			return entry;
		}
		
		/**
		 * Searches for the player record. A new record is created if
		 * the player is new.
		 * 
		 * @param playerName the player name
		 * @return the corresponding player record
		 */
		public LetterNode get(String playerName) {
			int m = playerName.length();
			char c = playerName.charAt(0);
			var current = searchLetter(c, first);
			
			if (current == null) {
				first = new LetterNode(c, first);
				current = first;
			}
			
			for (int i = 1; i < m; i++) {
				c = playerName.charAt(i);
				var next = searchLetter(c, current.firstChild);
				
				if (next == null) {
					current.firstChild = new LetterNode(c, current.firstChild);
					next = current.firstChild;
				}
				
				current = next;
			}
			
			current.taken = true;
			return current;
		}
		
		/**
		 * Finds the maximum final score value among the players.
		 * 
		 * @return the maximum final score value
		 */
		public int maxScore() {
			return maxScore(first);
		}
		
		/**
		 * Find the maximum final score for all players in the subtree rooted
		 * in the given node and the subtrees rooted in the right siblings
		 * of that node.
		 * 
		 * @param entry the starting node
		 * @return the maximum final score
		 */
		private static int maxScore(LetterNode entry) {
			int max = 0;
			
			while (entry != null) {
				if (entry.taken && entry.finalScore > max) {
					max = entry.finalScore;
				}
				
				int subtreeMax = maxScore(entry.firstChild);
				
				if (subtreeMax > max) {
					max = subtreeMax;
				}
				
				entry = entry.nextSibling;
			}
			
			return max;
		}
	}
}
