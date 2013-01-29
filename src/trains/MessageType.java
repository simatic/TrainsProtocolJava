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
 * 
 * Defines types for the messages of the application
 * 
 * @author Stephanie Ouillon
 */

public enum MessageType {
	
	/**
	 * Broadcast requested by application
	 */
	AM_BROADCAST,
	
	/**
	 * Arrival of a process
	 */
	AM_ARRIVAL,
	
	/**
	 * Departure of a process
	 */
	AM_DEPARTURE,
	
	/**
	 * Type of message used internally by utoDeliveries to exit its main loop
	 */
	AM_TERMINATE,	
	
	/**
	 * Type of message used for latency test
	 */
	AM_PING,
	
	/**
	 * Type of message used for latency test
	 */
	AM_PONG
}
