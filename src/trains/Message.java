package trains;

public class Message {

	private MessageHeader messageHeader;
	private String payload;

	private Message(MessageHeader msgHdr, String payload){
		this.messageHeader = msgHdr;
		this.payload = payload;
	}

	public static Message createMessage(MessageHeader messageHeader, String payload){
		return new Message(messageHeader, payload);
	}
	
	public static Message messageFromPayloadAndType(String payload, int type){
		MessageHeader msgHdr = new MessageHeader(payload.length(), type);
		return new Message(msgHdr, payload);		
	}
	
	public static Message messageFromPayload(String payload){
		int type = MessageType.AM_BROADCAST.ordinal();
		MessageHeader msgHdr = MessageHeader.createMessageHeader(payload.length(), type);
		return new Message(msgHdr, payload);		
	}

	public void setMessageHeader(MessageHeader msgHdr){
		this.messageHeader = msgHdr;
	}

	public void setPayload(String payload){
		this.payload = payload;
	}
}
