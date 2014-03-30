package net.sf.oereader;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Stores tree information from the .dbx file.
 *
 * @author Alex Franchuk
 * @version 1.1
 */
public class OETree<T extends OEIndexedInfo> extends OEBase {
	private byte[] data;
	private OEIndexedInfo factory;
	private int marker;
	/**
	 * Pointer to the child node
	 */
	public int child;
	/**
	 * Pointer to the parent node
	 */
	public int parent;
	/**
	 * Number of stored entries in the child tree of this node
	 */
	public int childvalues;
	/**
	 * Values in the body of this node
	 */
	public int[] value;
	/**
	 * Pointers to child nodes of this node
	 */
	public int[] childp;
	/**
	 * Number of stored entries in the {@link #childp children} of this tree
	 */
	public int[] childvaluesp;
	/**
	 * Node id of this node
	 */
	public int nodeId;
	/**
	 * Number of entries in the body of this node
	 */
	public int bodyentries;
	/**
	 * {@link net.sf.oereader.OETree OETree} of the child of this node
	 */
	public OETree<T> dChild;
	/**
	 * {@link net.sf.oereader.OETree OETree}'s of the children of this node
	 */
	public OETree<T>[] bChildren;

	/**
	 * Reads a tree of data recursively from the root node of the file.
	 *
	 * Reads and reconstructs the tree of data for perusal later.
	 *
	 * @param data data to be read
	 * @param i index to start from
	 */
	@SuppressWarnings("unchecked")
	public OETree(byte[] data, int i, OEIndexedInfo factory) {
		this.data = data;
		this.factory = factory;
		marker = toInt4(data,i);
		child = toInt4(data,i+8);
		parent = toInt4(data,i+12);
		nodeId = (byte)data[i+16]&0xff;
		bodyentries = (byte)data[i+17]&0xff;
		childvalues = toInt4(data,i+20);
		
		if (child != 0) {
			dChild = new OETree<T>(data,child,factory);
		}
		
		value = new int[bodyentries];
		childp = new int[bodyentries];
		childvaluesp = new int[bodyentries];
		bChildren = (OETree<T>[]) new OETree[bodyentries];
		
		for (int k = 0; k < bodyentries; k++) {
			value[k] = toInt4(data,24+12*k+i);
			childp[k] = toInt4(data,28+12*k+i);
			if (childp[k] != 0) bChildren[k] = new OETree(data,childp[k],factory);
			childvaluesp[k] = toInt4(data,32+12*k+i);
		}
	}

	/**
	 * Converts the OETree's contents to a list
	 *
	 * @return a list of the OETree's contained {@link net.sf.oereader.OEIndexedInfo OEIndexedInfo} objects
	 */
	@SuppressWarnings("unchecked")
	public List<T> toList() {
		ArrayList<T> ret = new ArrayList<T>(this.bodyentries);
		for (int i = 0; i < this.bodyentries; i++) {
			ret.add(i,(T)factory.create(data,this.value[i]));
		}
		if (this.dChild != null)
			ret.addAll(this.dChild.toList());
		for (int i = 0; i < this.bodyentries; i++) {
			if (this.bChildren[i] != null)
				ret.addAll(this.bChildren[i].toList());
		}
		return ret;
	}
}
