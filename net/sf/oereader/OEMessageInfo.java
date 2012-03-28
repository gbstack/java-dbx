package net.sf.oereader;

public class OEMessageInfo extends OEIndexedInfo {
	public int index, flags, messageBodyLines,messagep,priority,messageTextLength;
	public OEMessage message;
	public int[] createtime, savetime, receivetime;
	public String messageId,origSubject,subject,senderan,answeredTo,serverlist,server,sendername,
		senderaddr,receivername,receiveraddr,accountname;
	
	public OEMessageInfo(byte[] data, int m) {
		super(data,m);
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
	}
}
