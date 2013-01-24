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
