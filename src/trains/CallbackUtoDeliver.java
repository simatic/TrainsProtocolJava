package trains;

/**
 * Interface for the callback that is called when a message is delivered (received).
 * 
 * IMPORTANT: when implementing this interface, define a static factory getInstance().
 * This factory is called in the native code to make sure only one callback exists.
 * 
 * @author Stephanie Ouillon
 *
 */

public interface CallbackUtoDeliver {
	//Important: need to implement a static factory getInstance()

	/**
	 * Method implemented by the Java user and called from the native code.
	 * 
	 * @param sender sender of the message
	 * @param msg application message (see {@link trains.Message Message}
	 */
	public void run(int sender, Message msg);
}
