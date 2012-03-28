package net.sf.oereader;

import java.io.*;
import java.util.Arrays;

public class OEReader {
	public OEFileHeader header;
	public OEFileInfo info;
	
	private byte data[];
	private Boolean open = false;
	
	public OEReader() {}
	
	public boolean open(String file) {
		if (file != null) {
			File f = new File(file);
			if (f != null) {
				try {
					FileInputStream fin = new FileInputStream(f);
					data = new byte[(int)f.length()];
					fin.read(data);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			else return false;
		}
		else return false;
		
		if (data == null) return false;
		
		header = new OEFileHeader(data);
		if (!header.checkMagic(data)) return false;
		info = new OEFileInfo(header.type,data,0x24bc);
		open = true;
		return true;
	}
	
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

	
	private OEMessageInfo[] tree_Messages(byte[] data,OTree t) {
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
	
	public OEMessageInfo[] getMessages() {
		if (open == false) return null;
		
		if (header.rootnodeEntries < 1) return null;
		
		OETree tree = new OETree(data,header.rootnode);
		OEMessageInfo[] messages = tree_Messages(data,tree);
		
		return messages;
	}
}
