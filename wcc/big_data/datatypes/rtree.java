


/*


This is probably all completely irrelevant. Don't
assume you should use any of this code if you are
building on the Zheng algorithms.


*/



import java.util.arraylist;




public class rtree {
	private root_set _root_set;
}





class root_set
{
	private ArrayList<node> _root;

	public int count()
	{
		return _root.size();
	}
}


class node
{
	// Identifier convention "R<_id>,"
	// i.e. "R12."
	private int _id;

	// Custom identifier.
	private String _key;

	// Upper-left coordinate.
	private double vertex [2];

	// Expands right and down.
	private double range[2];
	
	// Can have any number of subnodes.
	private ArrayList<node> _subnode;

	// Pointer to parent node. This
	// may be redundant.
	private node _parent;


	public Boolean equals(node that)
	{
		return
		( this._id == that._id );
	}

	public Boolean has_subnodes()
	{
		return !(_subnode.size() == 0);
	}

	public int subnodes_count()
	{
		return _subnode.size();
	}

	public node subnode(int index)
	{
		return _subnode.get(index);
	}

	public Boolean contains(node that)
	{
		Boolean contained =
			this.equals(that);
		for (int i=0;
			 i<this._subnode.size();
		 	  ++i)
		{
			if(contained) break;
			contained = _subnode[i].contains(that);
		}
		return contained;
	}

	// Return true if rtree under this
	// node contains node identified by
	// "key".
	public Boolean contains(String key)
	{
		Boolean contained =
			this._key.equals(key);
		for (int i=0;
			 i<this._subnode.size();
		 	  ++i)
		{
			if(contained) break;
			contained = _subnode[i].contains(that);
		}
		return contained;	
	}


	public node_region region()
	{
		// Bounds for "this."
		double b[2][2];

		b[0][0] =
			that.vertex[0];
		b[0][1] =
			that.vertex[0]+that.range[0];
		b[1][0] =
			that.vertex[1];
		b[1][1] =
			that.vertex[1]+that.range[1];

		if(b[0][0] < b[0][1])
		{
			double temp=b[0][0];
			b[0][0]=b[0][1];
			b[0][1]=temp;
		}
		if(b[1][0] < b[1][1])
		{
			double temp=b[1][0];
			b[1][0]=b[1][1];
			b[1][1]=temp;
		}

		return node_region( b[0][0],
							b[0][1],
							b[1][0],
							b[1][1]	);
	}


	public Boolean bound_by(node that)
	{
		return
			this.region().bound_by(that.region());
	}


	public Boolean add(node that)
	{
		return _subnode.add(that);
	}



	public node(double vertex_x0,
				double vertex_x1,
				double range_x0,
				double range_x1,
				String key
				)
	{
		vertex[0] = vertex_x0;
		vertex[1] = vertex_x1;
		range[0]  =	range_x0;
		range[1]  = range_x1;
		if(key.equals(""))
			_key="NO_KEY";
		else
			_key=key;
	}



} // end class node


class node_region
{
	private double bound[2][2];

	public Boolean equals(node_region that)
	{
		return 
			this.bound[0][0] == that.bound[0][0]
				&&
			this.bound[0][1] == that.bound[0][1]
				&&
			this.bound[1][0] == that.bound[1][0]
				&&
			this.bound[1][1] == that.bound[1][1]
				;
	}

	public Boolean bound_by(node_region that)
	{
		return (
			this.bound[0][0] >= that.bound[0][0]
				&&
			this.bound[0][1] <= that.bound[0][1]
				&&
			this.bound[1][0] >= that.bound[1][0]
				&&
			this.bound[1][1] <= that.bound[1][1]
				);
	}

	public node_region( double b00,
						double b01,
						double b10,
						double b11 )
	{
		bound[0][0] = b00;
		bound[0][1] = b01;
		bound[1][0] = b10;
		bound[1][1] = b11;
	}
}









/*

public node findNode(node root, int position) {
    ArrayList<node> a = new ArrayList<node>();
    populateNodes(root, a);
    return a.get(position);
}

private void populateNodes(node singleton, ArrayList<node> a) {
    if (singleton == null) return;
    populateNodes(singleton.left, a);
    a.add(singleton);
    populateNodes(singleton.right, a);
}


*/
