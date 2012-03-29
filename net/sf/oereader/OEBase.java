package net.sf.oereader;

/**
 * Base class which all other classes inherit; contains a number of functions
 * to parse data from binary.
 *
 * @author Alex Franchuk
 * @version 1.0
 */
public class OEBase {
	/**
	 * Converts 4 bytes directly to an int (big endian).
	 *
	 * @param data data array to read
	 * @param i index to start from
	 * @return integer composed of the 4 read bytes
	 */
	protected int toInt(byte[] data, int i) {
		return (((int)data[i]&0xff)<<24)|(((int)data[i+1]&0xff)<<16)|(((int)data[i+2]&0xff)<<8)|(data[i+3]&0xff);
	}
	
	/**
	 * Converts 2 bytes to an int (technically a short in an int).
	 *
	 * @param data data array to read
	 * @param i index to start from
	 * @return integer composed of the 2 bytes, in proper order
	 */
	protected int toInt2(byte[] data, int i) {
		return (((int)data[i+1]&0xff)<<8)|(data[i]&0xff);
	}
	
	/**
	 * Converts 3 bytes to an int.
	 *
	 * @param data data array to read
	 * @param i index to start from
	 * @return integer composed of the 3 bytes, in proper order
	 */
	protected int toInt3(byte[] data, int i) {
		return (((int)data[i+2]&0xff)<<16)|(((int)data[i+1]&0xff)<<8)|(data[i]&0xff);
	}
	
	/**
	 * Converts 4 bytes to an int (little endian).
	 *
	 * @param data data array to read
	 * @param i index to start from
	 * @return integer composed of the 4 bytes, in proper order
	 */
	protected int toInt4(byte[] data, int i) {
		return (data[i]&0xff)|(((int)data[i+1]&0xff)<<8)|(((int)data[i+2]&0xff)<<16)|(((int)data[i+3]&0xff)<<24);
	}
	
	/**
	 * Converts 8 bytes to an array of 2 ints.
	 *
	 * @param data data array to read
	 * @param i index to start from
	 * @return integer array of length 2, with 2 read integers
	 */
	protected int[] toInt8(byte[] data, int i) {
		int[] ret = new int[2];
		ret[0] = toInt4(data,i);
		ret[1] = toInt4(data,i+4);
		return ret;
	}
	
	/**
	 * Reads a string from data, starting at index i.
	 *
	 * @param data data array to read
	 * @param i index to start from
	 * @return {@link java.lang.String String} containing the read data string
	 */
	protected String toString(byte[] data, int i) {
		String ret = "";
		for (int x = i; data[x] != 0; x++) {
			ret += (char)data[x];
		}
		return ret;
	}
}
