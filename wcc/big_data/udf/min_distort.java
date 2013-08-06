/*
	Zheng algorithm #1: minDistort

	Calculating minimum distort of time 
	sequences.

Input:
	Lnk[i]: a link;
	tf[j]: a time frame;
	t: number of adjacent weeks to check

Output:
	minDistort[i][j]: the degree of distort
	for link Lnk[i] in time frame tf[j]

										*/


// I'm not clear on how timeframes are
// defined or accessed. Once that becomes
// clear, this algorithm should be
// complete.

package wcc.big_data.udf;

import wcc.big_data.datatypes.*;

import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDF;

import java.lang.Double;

@Description
	(	

	name ="min_distort",

	value="_FUNC_"
		 +"(link, tf_j, t)"
		 +" - "
		 +"minimum distortion"
		 +" of time sequence",

	extended="Example:\n"
			+"> SELECT"
			+" _FUNC_"
			+"(link1,tf2,3)"
			+" FROM dummy;"

	)



public class min_distort extends UDF
{

	// 1 week in milliseconds.
	double ms_in_week
		= 7 * 24 * 60 * 60 * 1000;

	// 1 week in seconds.
	double secs_in_week
		= ms_in_week/1000;

	// Return minimum distortion 
	public double evaluate
		(
			Link link,
			Timeframe tf_j,
			int t
		)
	{
			
	/* 1 */ 
		// The minimum distortion
		// starts at positive infinity.
		Double min_dist
			= Double.POSITIVE_INFINITY;
	
	/* 2 */
		// The Timeframe_set constructor
		// will create the array of
		// timeframes for comparison.
		Timeframe_set T
			= new Timeframe_set
				(
					tf_j,
					-1*t,
					t
				);

//		for (int i=-1*t; i<=t; ++i)
//		{
//			Timeframe Next = Timeframe.Extract_relatable_timeframe(tf_j,i);
//			T.add(Next);
//		}
	

	/* 3 */
		// Compare each timeframe in
		// the set to the original
		// timeframe.
		for (int i=0; i<T.size(); i++)
		{

		/* 4 */
			// Skip if timeframes overlap.
			if(T.get(i).overlaps(tf_j))
				/* 5 */
				continue;
			/* 6 */ // End If

	/* 7 */
		// Find 'current distance.' It's
		// not clear to me what this means.
		// This is just a dummy function
		// in the mean time.
		double current_dist
			= Distance(tf_j,T.get(i),link);
	
	/* 8 */
		// Set minimum distortion to
		// current distance if it is
		// less. This mismatchof words
		// is in the original lambda
		// publication.
		if(current_dist<min_dist)
		/* 9 */
			min_dist = current_dist;
		/* 10 */ // end if
	
	/* 11 */ // end for
		}
	
/* 12 */
	return min_dist;	
	}
	
	}
	