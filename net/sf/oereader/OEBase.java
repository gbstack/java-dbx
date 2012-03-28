package net.sf.oereader;

public class OEBase {
	protected int toInt(byte[] data, int i) {
		return (((int)data[i]&0xff)<<24)|(((int)data[i+1]&0xff)<<16)|(((int)data[i+2]&0xff)<<8)|(data[i+3]&0xff);
	}
	
	protected int toInt2(byte[] data, int i) {
		return (((int)data[i+1]&0xff)<<8)|(data[i]&0xff);
	}
	
	protected int toInt3(byte[] data, int i) {
		return (((int)data[i+2]&0xff)<<16)|(((int)data[i+1]&0xff)<<8)|(data[i]&0xff);
	}
	
	protected int toInt4(byte[] data, int i) {
		return (data[i]&0xff)|(((int)data[i+1]&0xff)<<8)|(((int)data[i+2]&0xff)<<16)|(((int)data[i+3]&0xff)<<24);
	}
	
	protected int[] toInt8(byte[] data, int i) {
		int[] ret = new int[2];
		ret[0] = toInt4(data,i);
		ret[1] = toInt4(data,i+4);
		return ret;
	}
	
	protected String toString(byte[] data, int i) {
		String ret = "";
		for (int x = i; data[x] != 0; x++) {
			ret += (char)data[x];
		}
		return ret;
	}
}
