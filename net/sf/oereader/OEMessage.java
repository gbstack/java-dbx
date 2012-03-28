package net.sf.oereader;

public class OEMessage extends OEBase {
	public int marker, bodylength, seglength, next;
	public String text;
	
	public OEMessage(byte[] data, int i) {
		marker = toInt4(data,i);
		bodylength = toInt4(data,i+4);
		seglength = toInt4(data,i+8);
		next = toInt4(data,i+12);
		text = new String(data,i+16,seglength);
		if (next != 0) {
			OEMessage n = new OEMessage(data,next);
			text += n.text;
		}
	}
}
