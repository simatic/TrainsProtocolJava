package trains;

public class Message {

	private MessageHeader messageHeader;
	private String payload;

	/* Is there a single Message object in the JVM -> no: 
	 * 1 created/sent/used by the user
	 * the 1 that's inside the process (utoDeliveries) 
	 *  */
	
	public Message(MessageHeader msgHdr, String payload){
		this.messageHeader = msgHdr;
		this.payload = payload;
	}

	public /*static*/ Message createMessage(MessageHeader messageHeader, String payload){
		return new Message(messageHeader, payload);
	}
	
	public /*static*/ Message messageFromPayloadAndType(String payload, int type){
		MessageHeader msgHdr = new MessageHeader(payload.length(), type);
		return new Message(msgHdr, payload);		
	}
	
	public static Message messageFromPayload(String payload){
		int type = MessageType.AM_BROADCAST.ordinal();
		MessageHeader msgHdr = new MessageHeader(payload.length(), type);
		return new Message(msgHdr, payload);		
	}

	public void setMessageHeader(MessageHeader msgHdr){
		this.messageHeader = msgHdr;
	}

	public void setPayload(String payload){
		this.payload = payload;
	}
	
	public MessageHeader getMessageHeader(){
		return this.messageHeader;
	}

	public String getPayload(){
		return this.payload;
	}
}
