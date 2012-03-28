package net.sf.oereader;

public class OETree extends OEBase {
	public int marker,child,parent,childvalues;
	public int[] value,childp,childvaluesp;
	public int nodeId,bodyentries;
	public OETree dChild;
	public OETree[] bChildren;
	
	public OETree(byte[] data, int i) {
		marker = toInt4(data,i);
		child = toInt4(data,i+8);
		parent = toInt4(data,i+12);
		nodeId = (byte)data[i+16]&0xff;
		bodyentries = (byte)data[i+17]&0xff;
		childvalues = toInt4(data,i+20);
		
		if (child != 0) {
			dChild = new OETree(data,child);
		}
		
		value = new int[bodyentries];
		childp = new int[bodyentries];
		childvaluesp = new int[bodyentries];
		bChildren = new OETree[bodyentries];
		
		for (int k = 0; k < bodyentries; k++) {
			value[k] = toInt4(data,24+12*k+i);
			childp[k] = toInt4(data,28+12*k+i);
			if (childp[k] != 0) bChildren[k] = new OETree(data,childp[k]);
			childvaluesp[k] = toInt4(data,32+12*k+i);
		}
	}
}
