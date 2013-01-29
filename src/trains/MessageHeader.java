/**
 Trains Protocol: Middleware for Uniform and Totally Ordered Broadcasts
 Copyright: Copyright (C) 2010-2012
 Contact: michel.simatic@telecom-sudparis.eu

 This library is free software; you can redistribute it and/or
 modify it under the terms of the GNU Lesser General Public
 License as published by the Free Software Foundation; either
 version 3 of the License, or any later version.

 This library is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 Lesser General Public License for more details.

 You should have received a copy of the GNU Lesser General Public
 License along with this library; if not, write to the Free Software
 Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307
 USA

 Developer(s): Stephanie Ouillon
 */

package trains;


/**  
 * Stores the header of the message carried by trains protocol for the application using the middleware.
 * 
 * @author Stephanie Ouillon
 */

public class MessageHeader {

	/**
	 * Length of the whole message
	 */
	private int len;
	
	
	/**
	 * Type of message (contains AM_BROADCAST, AM_ARRIVAL or AM_DEPARTURE), 
	 * see {@link trains.MessageType MessageType}
	 */
	private char type;
	
	
	/**
	 * 
	 * @param len {@link #len lenght} of the whole message (header + {@link Message.payload payload})
	 * @param type {@link #type type} of the message
	 */
	public MessageHeader(int len, int type){
		this.len = len;
		this.type = (char) type;
	}
	
	
	/**
	 * Sets the length of the whole message (header +{@link Message.payload payload})
	 * @param len {@link #len lenght}
	 */
	public void setLen(int len){
		this.len = len;
	}

	
	/**
	 * Sets the  {@link trains.MessageType type} of the message.
	 * @param type {@link #type type}
	 */
	public void setType(int type){
		this.type = (char) type;
	}
	
	
	/**
	 * Returns the length of the whole message
	 * @return {@link #len lenght}
	 */
	public int getLen(){
		return this.len;
	}

	
	/**
	 * Returns the type of the message
	 * @return the {@link #type type} of the message
	 */
	public int getType(){
		return (int)this.type;
	}
}
