package trains;

public interface CallbackCircuitChange {
	//Important: need to implement a static factory getInstance()
	
	public void run(CircuitView cv);
}
