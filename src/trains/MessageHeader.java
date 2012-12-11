package trains;

public class MessageHeader {

	//length of the whole message
	private int len;
	//type of message -> enum AM_BROADCAST, AM_ARRIVAL or A_DEPARTURE
	private String type;
	
	public MessageHeader(int len, String type){
		this.len = len;
		this.type = type;
	}
	
	/*public static MessageHeader createMessageHeader(int len, String type){
		return new MessageHeader(len, type);
	}*/

	public void setLen(int len){
		this.len = len;
	}

	public void setType(String type){
		this.type = type;
	}
}
