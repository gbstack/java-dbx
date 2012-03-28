package net.sf.oereader;

public class OEFileInfo extends OEBase {
	public String regKey,folderName;
	public int sourceType;
	public int[] filetime;
	public OEFileInfo(String type, byte[] data, int i) {
		if (type == "MessageDB") {
			if (toInt4(data,i) != 1) return;
			sourceType = data[i+4];
			regKey = toString(data,i+5);
			folderName =  toString(data,i+0x105);
		}
		else if (type == "FolderDB") {
			filetime = toInt8(data,i);
			if (toInt4(data,i+8) != 1) return;
		}
	}
}
