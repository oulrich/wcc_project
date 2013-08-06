/*
	Zheng algorithm #2: STOTree

	Constructing all outlier trees.

Input:
	STOutlier: a set of spatial-
	temporal outliers of size t x k,
	where t is the number of time 
	frames, and k is the number of
	outliers to examine in a time 
	frame

Output:
	STOTrees: a list of roots of 
	spatial-temporal trees


	The subroutine FindAllChildren
	has been internalized by the
	Root_set class.

									*/




// Spatial-Temporal Outlier Tree
// Returns a set of R-tree roots
// corresponding to Spatial-Temporal
// Outliers.

package wcc.big_data.udf;

import wcc.big_data.datatypes.*;

import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDF;


@Description(
				name  	= "STO_tree",

				value 	="_FUNC_"
						+ "(STOutliers)",

				extended="Spatial"
						+"-"
						+"temporal"
			)

public class STO_tree extends UDF {

	public Root_set evaluate
		(
		Outlier_set STOutliers
		)
	{
	/* 1 */
		Root_set STOTrees
			= new Root_set(null);
	
	/* 2 */
		// timeframes() will return
		// number of timeframes
		int timeframes
			= STOutliers.timeframes();

		for(int i=0;
			i<timeframes;
			++i)
		{
		/* 3 */
			// outliers(i) returns number
			// of outliers under
			// timeframe[i].
			int outliers
				= STOutliers
					.outliers(i);

			for(int j=0;
				j<outliers;
				++j)
			{
			/* 4 */
				// Find_all_children()
				// is a subroutine
				// defined by Zheng.
				Node STORoot_ij
					= STOTrees
						.Find_all_children(
							STOutliers,
							i,j
											);
			/* 5 */
				STOTrees
					.add(STORoot_ij);
	
		/* 6 */ // end for
			}
	/* 7 */ // end for
		}
	
	/* 8 */
		return STOTrees;
	
	}
	
}
