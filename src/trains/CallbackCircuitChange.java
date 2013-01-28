package trains;


/**
 * Interface for the callback that is called when a change occurs on the circuit.
 * 
 * IMPORTANT: when implementing this interface, define a static factory getInstance().
 * This factory is called in the native code to make sure only one callback exists.
 * ALSO: the name of the class should be of less than 128 characters.
 * @author Stephanie Ouillon
 *
 */
public interface CallbackCircuitChange {
	//Important: need to implement a static factory getInstance()
	
	/**
	 * Method implemented by the Java user and called from the native code.
	 * @param cv View of the circuit (see {@link trains.CircuitView CircuitView})
	 */
	public void run(CircuitView cv);
}
