package net.sf.oereader;

/**
 * Contains the information for a Folder object.
 *
 * This object is only used in the {@link net.sf.oereader.OEFileHeader#type FolderDB} .dbx files.
 * The pointers to the FolderInfo objects are stored in the {@link net.sf.oereader.OETree Tree}.
 *
 * @author Alex Franchuk
 * @version 1.1
 */
public class OEFolderInfo extends OEIndexedInfo {
	/**
	 * Static enum class of Flag bits
	 */
	public static class Flags {
		public static int ACTIVE = 1 << 0;
		public static int FOLDER_IN_OE = 1 << 3;
		public static int HAS_SUBFOLDER = 1 << 4;
	}

	/**
	 * Index
	 */
	public int index;
	/**
	 * Parent folder's index
	 */
	public int parentIndex;
	/**
	 * Folder name
	 */
	public String folderName;
	/**
	 * .dbx Filename
	 */
	public String filename;
	/**
	 * Registry of the account
	 */
	public String registryKey;
	/**
	 * Flags, as described <a href="http://oedbx.aroh.de/doc/OE_Dbx_FolderInfo.html">here</a>,
	 * and in the {@link net.sf.oereader.OEFolderInfo.Flags Flags} enum class
	 */
	public int flags;
	/**
	 * Number of messages in the folder
	 */
	public int messageCount;
	/**
	 * Number of unread messages in the folder
	 */
	public int unreadMessageCount;
	/**
	 * Unique index, used if this folder is a subfolder of 'local folders'
	 */
	public int localFolderIndex;
	/**
	 * Maximum message index on the server
	 */
	public int messageIndexServerMax;
	/**
	 * Minimum message index on the server
	 */
	public int messageIndexServerMin;
	/**
	 * Maximum message index locally
	 */
	public int messageIndexLocalMax;
	/**
	 * Minimum message index locally
	 */
	public int messageIndexLocalMin;
	/**
	 * Number of messages to download
	 */
	public int toDownloadCount;
	/**
	 * Watched messages
	 */
	public int watchedMessages;

	/** 
	 * Construct for FolderInfo factory object.
	 */
	public OEFolderInfo() {
		super();
	}

	/**
	 * Constructor for the FolderInfo object.
	 *
	 * Starts by loading information in the {@link net.sf.oereader.OEIndexedInfo IndexedInfo} parent object.
	 *
	 * @param data data to be read
	 * @param m index to start from
	 */
	public OEFolderInfo(byte[] data, int m) {
		super(data,m);
		for (int i = 0; i < indices.length; i++) {
			IndexValue iv = indices[i];
			switch(iv.index) {
				case 0:
					index = (iv.direct?iv.value:toInt4(data,datapos+iv.value));
					break;
				case 1:
					parentIndex = (iv.direct?iv.value:toInt4(data,datapos+iv.value));
					break;
				case 2:
					folderName = toString(data,datapos+iv.value);
					break;
				case 3:
					filename = toString(data,datapos+iv.value);
					break;
				case 5:
					registryKey = toString(data,datapos+iv.value);
					break;
				case 6:
					flags = (iv.direct?iv.value:toInt4(data,datapos+iv.value));
					break;
				case 7:
					messageCount = (iv.direct?iv.value:toInt4(data,datapos+iv.value));
					break;
				case 8:
					unreadMessageCount = (iv.direct?iv.value:toInt4(data,datapos+iv.value));
					break;
				case 9:
					localFolderIndex = (iv.direct?iv.value:toInt4(data,datapos+iv.value));
					break;
				case 13:
					messageIndexServerMax = (iv.direct?iv.value:toInt4(data,datapos+iv.value));
					break;
				case 14:
					messageIndexServerMin = (iv.direct?iv.value:toInt4(data,datapos+iv.value));
					break;
				case 16:
					messageIndexLocalMax = (iv.direct?iv.value:toInt4(data,datapos+iv.value));
					break;
				case 17:
					messageIndexLocalMin = (iv.direct?iv.value:toInt4(data,datapos+iv.value));
					break;
				case 18:
					toDownloadCount = (iv.direct?iv.value:toInt4(data,datapos+iv.value));
					break;
				case 28:
					watchedMessages = (iv.direct?iv.value:toInt4(data,datapos+iv.value));
					break;
				default:
					break;
			}
		}
	}

	/**
	 * Factory method to create a new OEFolderInfo object.
	 */
	public OEFolderInfo create(byte[] data, int i) {
		return new OEFolderInfo(data,i);
	}
}
