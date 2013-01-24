package trains;

import java.io.UnsupportedEncodingException;


/**  
 * Stores the message carried by the trains protocol for the application using the middleware.
 * 
 * @author Stephanie Ouillon
 */

public class Message {

	
	/**
	 * Header of the application message. Is an instance of {@link trains.MessageHeader MessageHeader}.
	 */
	private MessageHeader messageHeader;
	
	
	/**
	 * Content of the message
	 */
	private byte[] payload;

	
	/**
	 * @param msgHdr message header of type {@link trains.MessageHeader MessageHeader}
	 * @param payload content of the message
	 */
	public Message(MessageHeader msgHdr, byte[] payload){
		this.messageHeader = msgHdr;
		this.payload = payload;
	}
	
	
	/**
	 * Returns a message given the payload and the type.
	 * 
	 * @param payload content of the message
	 * @param type type of the message (see {@link trains.MessageType MessageType})
	 * @return the created message
	 */
	public static Message messageFromPayloadAndType(byte[] payload, int type){
		MessageHeader msgHdr = new MessageHeader(payload.length, type);
		return new Message(msgHdr, payload);		
	}
	
	
	/**
	 * Returns a message of type AM_BROADCAST (see {@link trains.MessageType MessageType}) given the payload.
	 * 
	 * @param payload content of the message
	 * @return the created message
	 */
	public static Message messageFromPayload(byte[] payload){
		int type = MessageType.AM_BROADCAST.ordinal();
		MessageHeader msgHdr = new MessageHeader(payload.length, type);
		return new Message(msgHdr, payload);		
	}

	
	/**
	 * Sets the message header (see {@link trains.MessageHeader MessageHeader}) of a message.
	 * @param msgHdr - message header
	 */
	public void setMessageHeader(MessageHeader msgHdr){
		this.messageHeader = msgHdr;
	}

	
	/**
	 * Sets the content of the message.
	 * @param payload
	 */
	public void setPayload(byte[] payload){
		this.payload = payload;
	}
	
	
	/**
	 * Returns the message header of type {@link trains.MessageHeader MessageHader}.
	 * @return the {@link #messageHeader message header} 
	 */
	public MessageHeader getMessageHeader(){
		return this.messageHeader;
	}

	
	/**
	 * Returns the content of a message
	 * @return the {@link #payload payload}
	 */
	public byte[] getPayload(){
		return this.payload;
	}
	
	
	/**
	 * Returns a byte Array given an integer. Uses UTF-16 with little endianness.
	 *  
	 * @param payload
	 * @return a byte Array
	 */
	public static byte[] IntToByteArray(int payload){

		try {
			 byte[] byteArray =  String.valueOf(payload).getBytes("UTF-16LE");
			 return byteArray;
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}
	
	
	/**
	 * Returns a byte Array given a String. Uses UTF-16 with little endianness.
	 * 
	 * @param payload
	 * @return a byte Array
	 */
	public static byte[] StringToByteArray(String payload){
		
		try {
			 byte[] byteArray =  payload.getBytes("UTF-16LE");
			 return byteArray;
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}
	
}
