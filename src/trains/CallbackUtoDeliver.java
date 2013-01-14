package trains;

public interface CallbackUtoDeliver {
	//Important: need to implement a static factory getInstance()

	public void run(int sender, Message msg);
}
