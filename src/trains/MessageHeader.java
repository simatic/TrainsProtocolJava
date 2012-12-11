package trains;

public class MessageHeader {

	//length of the whole message
	private int len;
	
	//casting type from int to char is okay since enum values are small
	//int (32 bits) to char (16 bits)
	private char type;
	
	public MessageHeader(int len, int type){
		this.len = len;
		this.type = (char) type;
	}
	
	/*public static MessageHeader createMessageHeader(int len, String type){
		return new MessageHeader(len, type);
	}*/

	public void setLen(int len){
		this.len = len;
	}

	public void setType(int type){
		this.type = (char) type;
	}
}
