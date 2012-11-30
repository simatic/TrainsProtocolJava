package trains;

/* defined in include/applicationMessage.h */

public class Message {

	private MessageHeader messageHeader;
	private String payload;
	
	private Message(){
		//do something
	}
	
	public static void Message(){
		//do something
	}

        public void setMessageHeader(MessageHeader msgHdr){
        	this.messageHeader = msgHdr;
        }

        public void setPayload(String payload){
  		this.payload = payload;
        }
}
