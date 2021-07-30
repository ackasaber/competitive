package aveleshko.codeforces.commentators;

/**
 * Location data for three stadiums.
 */
public final class StadiumsData {
	private final Stadium[] stadiums = new Stadium[3];

	/**
	 * Returns the location data for a particular stadium.
	 *
	 * @param i the stadium number (1, 2 or 3)
	 * @return the stadium location data
	 */
	public Stadium stadium(int i) {
		return stadiums[i - 1];
	}

	/**
	 * Bundles the location data for all three stadiums.
	 *
	 * @param s1 the first stadium location data
	 * @param s2 the second stadium location data
	 * @param s3 the third stadium location data
	 */
	public StadiumsData(Stadium s1, Stadium s2, Stadium s3) {
		stadiums[0] = s1;
		stadiums[1] = s2;
		stadiums[2] = s3;
	}
}
