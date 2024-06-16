package codeforces.beta02.commentators;

/**
 * Visualization options.
 */
final class Options {
	private final double xmin;
	private final double xmax;
	private final double ymin;
	private final double ymax;

	/**
	 * Minimum x coordinate.
	 *
	 * @return minimum x coordinate
	 */
	public double xmin() {
		return xmin;
	}

	/**
	 * Maximum x coordinate.
	 *
	 * @return maximum x coordinate
	 */
	public double xmax() {
		return xmax;
	}

	/**
	 * Minimum y coordinate.
	 *
	 * @return minimum y coordinate
	 */
	public double ymin() {
		return ymin;
	}

	/**
	 * Maximum y coordinate.
	 *
	 * @return maximum y coordinate
	 */
	public double ymax() {
		return ymax;
	}

	/**
	 * Builder for options.
	 */
	public static class Builder {
		private double xmin = 0.0;
		private double xmax = 1.0;
		private double ymin = 0.0;
		private double ymax = 1.0;

		/**
		 * Create the mutable options with default values.
		 */
		public Builder() {
		}

		/**
		 * Sets the minimum x limit.
		 *
		 * @param xmin the minimum x coordinate
		 * @return the current builder
		 */
		public Builder withXmin(double xmin) {
			this.xmin = xmin;
			return this;
		}

		/**
		 * Sets the maximum x limit.
		 *
		 * @param xmax the maximum x coordinate
		 * @return the current builder
		 */
		public Builder withXmax(double xmax) {
			this.xmax = xmax;
			return this;
		}

		/**
		 * Sets the minimum y limit.
		 *
		 * @param ymin the minimum y coordinate
		 * @return the current builder
		 */
		public Builder withYmin(double ymin) {
			this.ymin = ymin;
			return this;
		}

		/**
		 * Sets the maximum y limit.
		 *
		 * @param ymax the maximum y coordinate
		 * @return the current builder
		 */
		public Builder withYmax(double ymax) {
			this.ymax = ymax;
			return this;
		}

		/**
		 * Produces an immutable options object for the current options values.
		 *
		 * @return the options object
		 */
		public Options build() {
			return new Options(this);
		}
	}

	private Options(Builder builder) {
		xmin = builder.xmin;
		xmax = builder.xmax;
		ymin = builder.ymin;
		ymax = builder.ymax;
	}
}
