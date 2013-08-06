/*
	Zheng algorithm #4: nodeInsertion

	Inserting a node to an outlier tree.

Input:
	Root: a root of an outlier tree;
	Singleton: a node to be inserted

Output:
	true/false: whether or not the node insertion is
	successful

														*/


package wcc.big_data.udf;

import wcc.big_data.datatypes.*;

import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDF;

@Description(
				name = "Node_insertion",
				value = "_FUNC_(root,singleton)",
				extended=""
			)

public class Node_insertion extends UDF {

	public Boolean evaluate(
							node root,
							node singleton
							)
	{
		return insert_node(root,singleton);
	}

	public Boolean insert_node(
								node root,
								node singleton
								)
	{
		/* 1 */
		if 	(
			root.region().equals(
				singleton.region()
								)
			&& 
			!root.contains(singleton)
			)
		{

			/* 2 */
			root.add(
				singleton
					);

			/* 3 */
			return true;
		}

		/* 4 */
		else

			/* 5 */
			if (!root.has_subnodes())

				/* 6 */
				return false;

			/* 7 */
			else

				/* 8 */
				for ( int i=0;
						i<root.subnodes_count();
							++i ) 
				{
					/* 9 */
					if 	( insert_node(
							root.subnode(i),
									singleton
									)
						)

						/* 10 */
						return true;

						/* 11 */ // end if

				/* 12 */ // end for
				}

			/* 13 */ // end if

		/* 14 */ // end if

		/* 15 */
		return false;
	}

}



