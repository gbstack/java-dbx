package net.sf.oereader;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

/**
 * Contains the information for a Message object.
 *
 * This object is only used in the {@link net.sf.oereader.OEFileHeader#type MessageDB} .dbx files.
 * The pointers to the MessageInfo objects are stored in the {@link net.sf.oereader.OETree Tree}.
 *
 * @author Alex Franchuk
 * @version 1.0
 */
public class OEMessageInfo extends OEIndexedInfo {
	/**
	 * Static enum class of Flag bits
	 */
	public static class Flags {
		public static int MESSAGE_BODY = 1 << 0;
		public static int IN_WORK = 1 << 3;
		public static int MARKED = 1 << 5;
		public static int READ = 1 << 7;
		public static int MARKED_FOR_DOWNLOAD = 1 << 8;
		public static int NEWS_POSTING = 1 << 11;
		public static int DIGITAL_SIGNED = 1 << 12;
		public static int WITH_ATTACHMENT = 1 << 14;
		public static int ANSWERED = 1 << 19;
		public static int THREAD_WATCHED = 1 << 22;
		public static int THREAD_IGNORED = 1 << 23;
	}

	/**
	 * Index
	 */
	public int index;
	/**
	 * Flags, as described <a href="http://oedbx.aroh.de/doc/OE_Dbx_MessageInfo.html#flags">here</a>,
	 * and in the {@link net.sf.oereader.OEMessageInfo.Flags Flags} enum class
	 */
	public int flags;
	/**
	 * Number of lines in the {@link net.sf.oereader.OEMessage Message} body
	 */
	public int messageBodyLines;
	/**
	 * Pointer to the corresponding {@link net.sf.oereader.OEMessage Message}
	 */
	public int messagep;
	/**
	 * Priority of the email (1 = low, 3 = normal, 5 = high)
	 */
	public int priority;
	/**
	 * Length of the message header and body (can be incorrect)
	 */
	public int messageTextLength;
	/**
	 * {@link net.sf.oereader.OEMessage Message} object of this message
	 */
	public OEMessage message;
	/**
	 * Time of creation of this message
	 */
	public int[] createtime;
	/**
	 * Time of saving of this message
	 */
	public int[] savetime;
	/**
	 * Time of reception of this message
	 */
	public int[] receivetime;
	/**
	 * Id of this message
	 */
	public String messageId;
	/**
	 * Original subject of this message (without "re:" etc.)
	 */
	public String origSubject;
	/**
	 * Subject of this message
	 */
	public String subject;
	/**
	 * Sender address and name
	 */
	public String senderan;
	/**
	 * Answered to {@link #messageId messageId}
	 */
	public String answeredTo;
	/**
	 * Server/Newsgroup/Message number (list)
	 */
	public String serverlist;
	/**
	 * Server the message was taken from
	 */
	public String server;
	/**
	 * Name of the sender ("From")
	 */
	public String sendername;
	/**
	 * Address of the sender ("From")
	 */
	public String senderaddr;
	/**
	 * Name of the recipient ("To")
	 */
	public String receivername;
	/**
	 * Address of the recipient ("To")
	 */
	public String receiveraddr;
	/**
	 * Mail or newsgroup account name
	 */
	public String accountname;
	
	private String body;
	

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	/**
	 * Constructor for MessageInfo factory object.
	 */
	public OEMessageInfo() {
		super();
	}
	
	/**
	 * Constructor for the MessageInfo object.
	 *
	 * Starts by loading the information in the {@link net.sf.oereader.OEIndexedInfo IndexedInfo} parent object.
	 *
	 * @param data data to be read
	 * @param m index to start from
	 */
	public OEMessageInfo(byte[] data, int m) {
		super(data,m);
		
		Session session = Session.getDefaultInstance(System.getProperties());
		
		for (int i = 0; i < indices.length; i++) {
			IndexValue iv = indices[i];
			switch(iv.index) {
				case 0:
					index = (iv.direct?iv.value:toInt4(data,datapos+iv.value));
					break;
				case 1:
					flags = (iv.direct?iv.value:toInt4(data,datapos+iv.value));
					break;
				case 2:
					createtime = toInt8(data,datapos+iv.value);
					break;
				case 3:
					messageBodyLines = (iv.direct?iv.value:toInt4(data,datapos+iv.value));
					break;
				case 4:
					messagep = (iv.direct?iv.value:toInt4(data,datapos+iv.value));
					break;
				case 5:
					origSubject = toString(data,datapos+iv.value);
					break;
				case 6:
					savetime = toInt8(data,datapos+iv.value);
					break;
				case 7:
					messageId = toString(data,datapos+iv.value);
					break;
				case 8:
					subject = toString(data,datapos+iv.value);
					break;
				case 9:
					senderan = toString(data,datapos+iv.value);
					break;
				case 10:
					answeredTo = toString(data,datapos+iv.value);
					break;
				case 11:
					serverlist = toString(data,datapos+iv.value);
					break;
				case 12:
					server = toString(data,datapos+iv.value);
					break;
				case 13:
					sendername = toString(data,datapos+iv.value);
					break;
				case 14:
					senderaddr = toString(data,datapos+iv.value);
					break;
				case 16:
					priority = (iv.direct?iv.value:toInt4(data,datapos+iv.value));
					break;
				case 17:
					messageTextLength = (iv.direct?iv.value:toInt4(data,datapos+iv.value));
					break;
				case 18:
					receivetime = toInt8(data,datapos+iv.value);
					break;
				case 19:
					receivername = toString(data,datapos+iv.value);
					break;
				case 20:
					receiveraddr = toString(data,datapos+iv.value);
					break;
				case 26:
					accountname = toString(data,datapos+iv.value);
					break;
				default:
					break;
			}
		}
		message = new OEMessage(data,messagep);
		ByteArrayInputStream stream = new ByteArrayInputStream(message.text.getBytes());
		try {
			MimeMessage mime_msg = new MimeMessage(session, stream);
			try {
				this.setBody(mime_msg.getContent().toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Factory method to create a new OEMessageInfo object
	 */
	public OEMessageInfo create(byte[] data, int i) {
		return new OEMessageInfo(data,i);
	}
}
