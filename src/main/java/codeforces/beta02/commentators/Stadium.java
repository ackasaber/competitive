package codeforces.beta02.commentators;

/**
 * Stadium location data.
 */
public final class Stadium {
	private final double x;
	private final double y;
	private final double r;

	/**
	 * Creates a new location object for a stadium.
	 *
	 * @param x the stadium center x coordinate
	 * @param y the stadium center y coordinate
	 * @param r the stadium radius
	 */
	public Stadium(double x, double y, double r) {
		this.x = x;
		this.y = y;
		this.r = r;
	}

	/**
	 * Returns the stadium center x coordinate.
	 *
	 * @return the stadium center x coordinate
	 */
	public double x() {
		return x;
	}

	/**
	 * Returns the stadium center y coordinate.
	 *
	 * @return the stadium center y coordinate
	 */
	public double y() {
		return y;
	}

	/**
	 * Returns the stadium radius.
	 *
	 * @return the stadium radius
	 */
	public double r() {
		return r;
	}
}
