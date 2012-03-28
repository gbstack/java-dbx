package net.sf.oereader;
	
public class OEIndexedInfo extends OEBase {
	protected class IndexValue {
		public int index;
		public int value;
		public Boolean direct;
		public IndexValue(int i, int v, boolean d) {
			index = i;
			value = v;
			direct = d;
		}
	}

	public int marker, bodylength, objectlength,entries,changes;
	public int datapos;
	protected IndexValue[] indices;
	public OEIndexedInfo(byte[] data, int i) {
		marker = toInt4(data,i);
		bodylength = toInt4(data,i+4);
		objectlength = toInt2(data,i+8);
		entries = data[i+10];
		changes = data[i+11];
		indices = new IndexValue[entries];
		for (int n = 0; n < entries; n++) {
			int k = data[i+12+n*4];
			int v = toInt3(data,i+13+n*4);
			indices[n] = new IndexValue(k&127,v,((k>>7)&1) == 1);
		}
		datapos = i+12+entries*4;
	}
}
