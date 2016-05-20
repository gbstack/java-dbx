package net.sf.oereader;

/**
 * Class representing a message in a .dbx file.
 *
 * Loaded by the {@link net.sf.oereader.OEMessageInfo MessageInfo} constructor.
 *
 * @author Alex Franchuk
 * @version 1.0
 */
public class OEMessage extends OEBase {
	private int marker;
	/**
	 * Length of the body of the message
	 */
	public int bodylength;
	/**
	 * Length of the text segment in the body
	 */
	public int seglength;
	/**
	 * Pointer to the next {@link net.sf.oereader.OEMessage Message} object
	 */
	public int next;
	/**
	 * Text in this segment of the message
	 */
	public String text;
	
	/**
	 * Loads the OEMessage object.
	 *
	 * Traverses the linked list of message body text segments and adds them together
	 *
	 * @param data data to be read
	 * @param i index to start from
	 */
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
