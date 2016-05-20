package example;

import java.util.List;

import net.sf.oereader.OEFolderInfo;
import net.sf.oereader.OEMessageInfo;
import net.sf.oereader.OEReader;

public class Example {
	public static void main(String args[]) throws Exception{
		OEReader reader = new OEReader();
		reader.open("E:\\Evidence\\Mail\\Outlook Express\\ ’º˛œ‰.dbx");
		List<OEFolderInfo> folders = null;
		try{
			folders = reader.getFolders();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		if(reader.header.type == "MessageDB"){
			List<OEMessageInfo> msgs = null;
			msgs = reader.getMessages();
			for(OEMessageInfo msg:msgs){
				System.out.println(msg.receiveraddr);
			}
		}
		
		
	}
}
