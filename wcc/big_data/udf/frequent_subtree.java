/*
	Zheng algorithm #3: frequentSubtree

	Discovering frequent subtrees from STOutlier trees.

Input:
	STO_trees: a list of roots of spatial-temporal trees;
	E: a support threshold for frequency substructure selection

Output:
	frequent_subtrees: a list of roots of frequent spatial-temporal
	 subtrees

																*/

package wcc.big_data.udf;

import wcc.big_data.datatypes.*;

import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDF;

@Description(
				name = "frequent_subtree",
				value = "_FUNC_(STO_trees,threshold)",
				extended=""
			)

public class frequent_subtree extends UDF {

	public Node_set evaluate(
							Root_set STO_trees,
							double threshold
							)
	{
	
	/* 1 */
		// Form a list of frequent nodes (i.e. 
		// frequent trees of size 1).
	
	/* 2 */
		// The number of roots in STO_trees is
		// also the number of total trees.
		int num_trees = STO_trees.count();
	
	/* 3 */
		// frequent_nodes should be given
		// any unique node appearing at
		// least num_trees * threshold
		// times in STO_trees.

		int frequency=num_trees*threshold;

		Node_set frequent_nodes;

		/* for() loop to assign frequent
			nodes to the set */
	
	/* 4 */
		// Assign frequent nodes to
		// merge target. This can
		// probably be reduced.
		Node_set merge_target = frequent_nodes;
	
	/* 5 */
		// Create an empty Root_set.
		Root_set frequent_subtrees
			= new Root_set(null);
	
	/* 6 */
		while(merge_target.count() > 0)
		{
		/* 7 */
			// Form candidates of frequent
			// subtrees.
	
		/* 8 */
			Node_set subtree_candidates
				= new Node_set(null);
	
		/* 9 */
			for(int i=0;
				i<merge_target.count();
				++i)
			{
			/* 10 */
				for(int j=0;
					j<merge_target
						.count_roots();
					++j)
				{
				/* 11 */
					// Calling algorithm #4
					// This should be double
					// checked with the
					// lambda.
					If(Node_insertion.
						evaluate(
							merge_target
								.get_root(j),
							merge_target
								.get(i)
								)
												.merge_target.get(i))
					/* 12 */
						// Add the node
						// to the
						// candidates
						// if node was
						// inserted
						// successfully.
						subtree_candidates
							.add(
								merge_target
									.get_root(j)
								);
				/* 13 */ // end if
			/* 14 */ // end for
				}
		/* 15 */ // end for
			}
	
		/* 16 */
			//  Filter subtree candidates
			// by threshold of support
			// 'threshold'.
			
		/* 17 */
			merge_target
				= new Node_set(null);
	
		/* 18 */
			for(int i=0;
				i<subtree_candidates
					.count();
				++i)
			{
			/* 19 */
				int count = 0;
				
			/* 20 */
				for(int j=0;
					j<merge_target
						.count_roots();
					++j)
				{
				/* 21 */
					if(merge_target.get_root(j)
						.contains(
							subtree_candidates(i)
			 					)
						)
					/* 22 */
						count++;
					
				/* 23 */ // end if
			/* 24 */ // end for
				}
			/* 25 */
				if(count>frequency)
				{
				/* 26 */
					frequent_subtrees
						.add(
							subtree_candidates
								.get(i)
							);
				/* 27 */
					merge_target
						.add(
							subtree_candidates
								.get(i)
							);
					
			/* 28 */ // end if
				}
		/* 29 */ // end for
			}
	/* 30 */ //end while
		}
		
	/* 31 */
		return frequent_subtrees;
	}

}


