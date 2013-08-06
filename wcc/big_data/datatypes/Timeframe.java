/*
	Timeframe Classes

	Classes describing bus timeframes
	and their relationships.

	Intended to support Zheng transit
	algorithms.

				(c) 2013 WCC
									*/


public class Timeframe {
	// private long int t



	public Boolean overlaps(Timeframe)
	{
		// Return whether timeframes
		// overlap.
		return false;
	}

	public int num_outliers()
	{
		// Return number of outliers
		// associated with this
		// timeframe.

		return 0;
	}
}

class Timeframe_set {
	Timeframe_set
		(
			Timeframe tf,
			int previous_weeks,
			int post_weeks
		)
	{
		// This constructor needs to
		// find values for this timeframe
		// from previous and later weeks.
		// It will then construct an array
		// of timeframes out of those
		// values.

	}

	public int size()
	{
		// return size of timeframe array.
	}
}

// This will probably go to Rtree.java or
// something else.
class Link {


}