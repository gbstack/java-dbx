package net.sf.oereader;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Main class used to read data from a .dbx Outlook Express file.
 *
 * This class takes care of loading the data from the .dbx file, parsing the {@link net.sf.oereader.OEFileHeader header},
 * and loading a list of {@link net.sf.oereader.OEMessage messages} to be used by the application, or a list of
 * {@link net.sf.oereader.OEFolderInfo folderinfos} to be used by the application.
 *
 * @author Alex Franchuk
 * @version 1.1
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
	
	/**
	 * Gets a list of all {@link net.sf.oereader.OEMessageInfo OEMessageInfo} objects from the opened file.
	 *
	 *@return a list of {@link net.sf.oereader.OEMessageInfo OEMessageInfo} objects
	 */
	@SuppressWarnings("unchecked")
	public List<OEMessageInfo> getMessages() throws Exception {
		if (open == false) return null;

		if (header.type != "MessageDB") {
			throw new Exception("File type is not MessageDB, instead is: "+header.type);
		}
		
		if (header.rootnodeEntries < 1) return null;
		
		OETree tree = new OETree<OEMessageInfo>(data,header.rootnode, new OEMessageInfo());
		if (tree == null) return new ArrayList<OEMessageInfo>(0);
		else return tree.toList();
	}

	/**
	 * Gets a list of all {@link net.sf.oereader.OEFolderInfo OEFolderInfo} objects from the opened file.
	 *
	 * @return a list of {@link net.sf.oereader.OEFolderInfo OEFolderInfo} objects
	 */
	@SuppressWarnings("unchecked")
	public List<OEFolderInfo> getFolders() throws Exception {
		if (open == false) return null;

		if (header.type != "FolderDB") {
			throw new Exception("File type is not FolderDB, instead is: "+header.type);
		}

		if (header.rootnodeEntries < 1) return null;

		OETree tree = new OETree<OEFolderInfo>(data,header.rootnode, new OEFolderInfo());
		if (tree == null) return new ArrayList<OEFolderInfo>(0);
		else return tree.toList();
	}
}
