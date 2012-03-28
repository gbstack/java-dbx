package net.sf.oereader;

public class OEFileHeader extends OEBase {
	public String type = "";
	public int fileInfoLength,lastvseg,vseglength,lastvsegspace,lasttseg,tseglength,lasttsegspace,
	  lastmseg,mseglength,lastmsegspace,rootp_deletedm,rootp_deletedt,middle_use_space,
	  middle_reuse_space,lasttentry,firstflnode,lastflnode,fm_use_space,mconditions,fconditions,
	  rootnodeEntries,variantEntries,rootnode,rootnodeVariant;
	
	public OEFileHeader(byte[] data) {
		if (toInt(data,0) != 0xcfad12fe) {
			return;
		}
		switch(toInt(data,4)) {
			case 0xc5fd746f:
				type = "MessageDB";
				break;
			case 0xc6fd746f:
				type = "FolderDB";
				break;
			case 0x309dfe26:
				type = "OfflineDB";
				break;
			default:
				return;
		}
		if (toInt(data,8) != 0x66e3d111 || toInt(data,12) != 0x9a4e00c0 || toInt(data,16) != 0x4fa309d4 || toInt(data,20) != 0x05000000 || toInt(data,24) != 0x05000000) return;
		fileInfoLength = toInt4(data,28);
		lastvseg = toInt4(data,36);
		vseglength = toInt4(data,40);
		lastvsegspace = toInt4(data,44);
		lasttseg = toInt4(data,48);
		tseglength = toInt4(data,52);
		lasttsegspace = toInt4(data,56);
		lastmseg = toInt4(data,60);
		mseglength = toInt4(data,64);
		lastmsegspace = toInt4(data,68);
		rootp_deletedm = toInt4(data,72);
		rootp_deletedt = toInt4(data,76);
		middle_use_space = toInt4(data,84);
		middle_reuse_space = toInt4(data,88);
		lasttentry = toInt4(data,92);
		if (toInt4(data,100) != 1) return;
		if (type == "FolderDB" && toInt4(data,104) != 1) return;
		firstflnode = toInt4(data,108);
		lastflnode = toInt4(data,112);
		if (type == "FolderDB" && toInt4(data,116) != 3 && toInt4(data,120) != 2) return;
		fm_use_space = toInt4(data,124);
		if (type == "MessageDB" && toInt4(data,128) != 2) return;
		if (type == "FolderDB" && toInt4(data,128) != 3) return;
		mconditions = toInt4(data,136);
		fconditions = toInt4(data,140);
		rootnodeEntries = toInt4(data,196);
		variantEntries = toInt4(data,200);
		rootnode = toInt4(data,228);
		rootnodeVariant = toInt4(data,232);
		//Ends at 0x24bc
	}
	
	public boolean checkMagic(byte[] data) {
		if (toInt(data,0) != 0xcfad12fe) {
			return false;
		}
		switch(toInt(data,4)) {
			case 0xc5fd746f:
			case 0xc6fd746f:
			case 0x309dfe26:
				break;
			default:
				return false;
		}
		if (toInt(data,8) != 0x66e3d111 || toInt(data,12) != 0x9a4e00c0 || toInt(data,16) != 0x4fa309d4 || toInt(data,20) != 0x05000000 || toInt(data,24) != 0x05000000) return false;
		return true;
	}
}
