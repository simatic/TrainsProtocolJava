package trains;

public class MessageHeader {

	//length of the whole message
	private int len;
	private int type;
	
	public MessageHeader(int len, int type){
		this.len = len;
		this.type = type;
	}
	
	/*public static MessageHeader createMessageHeader(int len, String type){
		return new MessageHeader(len, type);
	}*/

	public void setLen(int len){
		this.len = len;
	}

	public void setType(int type){
		this.type = type;
	}
}
