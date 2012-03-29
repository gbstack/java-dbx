package net.sf.oereader;

import java.io.*;
import java.util.Arrays;

/**
 * Main class used to read data from a .dbx Outlook Express file.
 *
 * This class takes care of loading the data from the .dbx file, parsing the {@link net.sf.oereader.OEFileHeader header},
 * and loading an array of {@link net.sf.oereader.OEMessage messages} to be used by the application.
 *
 * @author Alex Franchuk
 * @version 1.0
 */
public class OEReader {
	/**
	 * {@link net.sf.oereader.OEFileHeader FileHeader} of the file
	 */
	public OEFileHeader header;
	/**
	 * {@link net.sf.oereader.OEFileInfo FileInfo} of the file
	 */
	public OEFileInfo info;
	
	private byte data[];
	private Boolean open = false;
	
	public OEReader() {}
	
	/**
	 * Opens a file, given the name, and reads the header information.
	 *
	 * @param file name of the .dbx file
	 * @return whether the file was read successfully
	 */
	public boolean open(String file) {
		if (file != null) {
			File f = new File(file);
			return open(f);
		}
		else return false;
		
	}

	/**
	 * Opens a file, given the {@link java.io.File File}, and reads the header information.
	 *
	 * @param file {@link java.io.File File} of the .dbx file
	 * @return whether the file was read successfully
	 */
	public boolean open(File file) {
		if (file != null) {
			try {
				FileInputStream fin = new FileInputStream(file);
				data = new byte[(int)file.length()];
				fin.read(data);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else return false;

		if (data == null) return false;
		
		header = new OEFileHeader(data);
		if (!header.checkMagic(data)) return false;
		info = new OEFileInfo(header.type,data,0x24bc);
		open = true;
		return true;
	}
	
	/**
	 * Closes a previously opened file.
	 */
	public void close() {
		header = null;
		info = null;
		data = null;
		open = false;
	}
	
	private OEMessageInfo[] concat(OEMessageInfo[] A, OEMessageInfo[] B) {
		OEMessageInfo[] C= new OEMessageInfo[A.length+B.length];
		System.arraycopy(A, 0, C, 0, A.length);
		System.arraycopy(B, 0, C, A.length, B.length);
		return C;
	}

	
	private OEMessageInfo[] tree_Messages(byte[] data,OETree t) {
		if (t == null) return new OEMessageInfo[0];
		OEMessageInfo[] ret = new OEMessageInfo[t.bodyentries];
		for (int i = 0; i < t.bodyentries; i++) {
			ret[i] = new OEMessageInfo(data,t.value[i]);
		}
		concat(ret,tree_Messages(data,t.dChild));
		for (int i = 0; i < t.bodyentries; i++) {
			concat(ret,tree_Messages(data,t.bChildren[i]));
		}
		return ret;
	}	
	
	/**
	 * Gets an array of all {@link net.sf.oereader.OEMessageInfo OEMessageInfo} objects from the opened file.
	 *
	 *@return an array of {@link net.sf.oereader.OEMessageInfo OEMessageInfo} objects
	 */
	public OEMessageInfo[] getMessages() {
		if (open == false) return null;
		
		if (header.rootnodeEntries < 1) return null;
		
		OETree tree = new OETree(data,header.rootnode);
		OEMessageInfo[] messages = tree_Messages(data,tree);
		
		return messages;
	}
}
