package codeforces.beta02;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/* Task 2A. Winner */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * The task solution.
 */
public final class Winner {

	/**
	 * Reads the score history, finds the winner and writes its name.
	 *
	 * @param args commmand line arguments (unused)
	 */
	public static void main(String[] args) {
		var reader = new InputStreamReader(System.in, StandardCharsets.US_ASCII);
		var bufferedReader = new BufferedReader(reader);
		@SuppressWarnings("resource")
		var scanner = new Scanner(bufferedReader);

		// Read the score change history.
		List<ScoreRecord> scoreHistory = new ArrayList<>();

		int n = scanner.nextInt();
		for (int i = 0; i < n; ++i) {
			String playerName = scanner.next();
			int scoreChange = scanner.nextInt();
			scoreHistory.add(new ScoreRecord(playerName, scoreChange));
		}

		// Compute the final scores.
		Map<String, PlayerEntry> scores = new HashMap<>();
		for (var scoreRecord : scoreHistory) {
			scores.compute(scoreRecord.playerName, (String name, PlayerEntry entry) -> {
				if (entry == null) {
					entry = new PlayerEntry();
				}

				entry.finalScore += scoreRecord.scoreChange;
				return entry;
			});
		}

		// Find the maximum final score.
		var maxEntry = scores.entrySet().stream().max((u, v) -> {
			return Integer.compare(u.getValue().finalScore, v.getValue().finalScore);
		});

		// Find the first player who reached the maximum score during the game.
		maxEntry.ifPresent(u -> {
			int maxScore = u.getValue().finalScore;

			for (var scoreRecord : scoreHistory) {
				var scoreEntry = scores.get(scoreRecord.playerName);
				scoreEntry.currentScore += scoreRecord.scoreChange;

				if (scoreEntry.currentScore >= maxScore && scoreEntry.finalScore == maxScore) {
					System.out.println(scoreRecord.playerName);
					break;
				}
			}
		});
	}

	/**
	 * A map entry with information about the running scores.
	 */
	private static final class PlayerEntry {
		/**
		 * The final score, computed on the first run through the score history.
		 */
		public int finalScore;
		/**
		 * The current score, computed on the second run through the score history to
		 * resolve possible ties.
		 */
		public int currentScore;
	}

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
		 * @param playerName  player name
		 * @param scoreChange score change
		 */
		public ScoreRecord(String playerName, int scoreChange) {
			this.playerName = playerName;
			this.scoreChange = scoreChange;
		}
	}
}
