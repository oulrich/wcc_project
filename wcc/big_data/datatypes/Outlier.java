/*
	Outlier Classes

	Classes defining outliers and
	their rtree relationships.

	Intended to support Zheng transit
	algorithms.

				(c) 2013 WCC

									*/


public class Outlier extends Node
{
	// The outlier and node classes
	// and sets should probably be merged.
}

class Node {

	public void clear_subnodes()
	{
		// Clear sub nodes.
	}

	public Boolean contains(Node that)
	{
		// Return true if this node
		// contain that node.
		return false;
	}

	public Region region()
	{
		// Return the node's region.
	}

	// A subclass defining
	// a node's region.
	class Region
	{

	}

	public Boolean insert_node(Node that)
	{
		// Insert node function called by
		// Find_all_children.
		// It's not clear that this is
		// the same as node_insertion,
		// but it may be.
	}

}


// Node_set probably also defines
// an entire rtree.
class Node_set
{
	public int count()
	{
		// Return number of nodes
		// in array.
		return 0;
	}

	public int count_roots()
	{
		// Return number of roots
		// in the tree.
	}

	public Node get(int index)
	{
		// Return node[index]
	}

	public Node get_root(int index)
	{
		// Return root[index]
	}
}

class Outlier_set
{
	public int timeframes()
	{
		// Return number of associated
		// timeframes.
		return 0;
	}

	public int outliers(int index)
	{
		// Return number of outliers
		// associated with timeframe
		// at index.
		return 0;
	}

	public Timeframe get(int i)
	{
		// Return timeframe(i).
	}

	public Node get(int i, int j)
	{
		// Return outlier(j)
		// under timeframe(i).
	}
}

class Root_set extends Outlier_set
{
	public Root_set(null)
	{
		// Create a Root_set with
		// no entries.
		return;
	}

	public Boolean add(Node new_root)
	{
		// Add a new root to the set.
	}

	public Boolean contains(Node that)
	{
		// Return if this set contains
		// that.

		// Should this return true if
		// the node is a root, or if
		// the node is a root or one
		// of the roots' subnodes?
	}

	public Node Find_all_children
		(
		Outlier_set STOutliers,
		int i,
		int j
		)
	{
	    /* 9 */
	    	// Return if i is beyond
	    	// the number of
	    	// timeframes.
	    	int timeframes 
	    		= STOutliers
	    			.timeframes();

	        if (i >= timeframes)
	            /* 10 */
	                return 
	                	STOutliers
                			.get(i,j);
	    /* 11 */ // end if
	
	    /* 12 */
	        STOutliers
	        	.get(i,j)
	        		.clear_subnodes();
	
	/* 13 */
		int outliers = 
			STOutliers
				.get(i)
					.num_outliers();

		for(int u=0; u<outliers; ++u)
		/* 14 */
			if(this
				.contains(
					STOutliers
						.get(i+1,j)
						)
			/* 15 */
				continue;
		/* 16 */ // end if
		/* 17 */
			if(STOutliers.get(i,j)
				.region().equals(
					STOutliers
						.get(i+1,j)
							.region()
								)
				)
			/* 18 */
				STOutliers.get(i,j)
					.insert_node(
						this.Find_all_children(
							STOutliers,i+1,j
												)
								);
		/* 19 */ // end if
	/* 20 */ // end for
		
	/* 21 */
		return STOutliers.get(i,j);
	}
}