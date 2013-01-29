package perf;

import trains.*;

import java.util.concurrent.Semaphore;

import examples.Example.myCallbackCircuitChange;
import examples.Example.myCallbackUtoDeliver;


public class Perf {

	//static boolean sender;
	static int broadcasters;
	static int delayBetweenTwoUtoBroadcast = 1; //millis
	//static int nbRecMsgBeforeStop;
	static int nbRecMsg = 0;
	static boolean measurementDone;

	static Semaphore semWaitEnoughMembers;

	public static class myCallbackCircuitChange implements CallbackCircuitChange{

		private static final myCallbackCircuitChange CIRCUITCHANGE = new myCallbackCircuitChange();
		private int id = 0;

		private myCallbackCircuitChange(){
			//Nothing to do
		} 

		public static myCallbackCircuitChange getInstance(){
			return CIRCUITCHANGE;
		}

		public void setId(int id){
			this.id = id;
		}

		@Override
		public void run(CircuitView cv){
			//Printing the circuit modification
			System.out.println("!!! ******** callbackCircuitChange called with " +  cv.getMemb() 
					+ " members (process ");

			//Printing the new/departed participant
			if(cv.getJoined() != 0){
				System.out.println(Integer.toString(cv.getJoined()) + " has arrived.)");
			} else {
				System.out.println(Integer.toString(cv.getDeparted()) + " is gone.)");
				if (!measurementDone) {
					System.out.println("!!! ******** Experience has failed ******** !!!\n");
					return;
				}
			}

			System.out.println(cv.getMemb() + " // " + broadcasters);
			if (cv.getMemb() >= broadcasters){
				System.out.println("!!! ******** enough members to start utoBroadcasting\n");
				semWaitEnoughMembers.release();
			}
		}
	}

	public static class myCallbackUtoDeliver implements CallbackUtoDeliver{

		private static final myCallbackUtoDeliver UTODELIVER = new myCallbackUtoDeliver();

		public myCallbackUtoDeliver(){
			//Nothing to do
		} 

		public static myCallbackUtoDeliver getInstance(){
			return UTODELIVER;
		}

		@Override
		public void run(int sender, Message msg){

			nbRecMsg++;

			/*String content = new String(msg.getPayload());
			System.out.println("!!! " + nbRecMsg + "-ieme message (recu de " + sender + " / contenu = " + content + ")");
		    */
		}
	}

	public static void main(String args[]) {	

		//trInit parameters: values by default
		int trainsNumber = 0;
		int wagonLength = 0;
		int waitNb = 0;
		int waitTime = 0;

		byte[] payload = null;
		int rankMessage = 0;
		Message msg = null;
		int exitcode;
		int i = 0;
		int maxConcurrentRequests = 1;
		
		int rank = 0;
		
		// Test parameters
		broadcasters = 2;
		int cooldown = 1; /* Default value = 10 seconds */
		int measurement = 10; /* Default value = 600 seconds */
		int number = 2;
		int size = 10;
		int warmup = 1; /* Default value = 300 seconds */
		measurementDone = false;
		
		//Semaphores initialization
		semWaitEnoughMembers = new Semaphore(maxConcurrentRequests, true);
		
		//Callback
		myCallbackCircuitChange mycallbackCC = myCallbackCircuitChange.getInstance();
		mycallbackCC.setId(1);
		myCallbackUtoDeliver mycallbackUto = myCallbackUtoDeliver.getInstance();


		try {
			semWaitEnoughMembers.acquire();			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		//timeKeeper.setTimeLoadInterfaceBegins(System.nanoTime());	
		Interface trin = Interface.trainsInterface();
		//timeKeeper.setTimeLoadInterfaceEnds(System.nanoTime());

		TimeKeeper timeKeeper = new TimeKeeper.Builder(trin, broadcasters, number, size).warmup(warmup).measurement(measurement).cooldown(cooldown).build();
	     
		timeKeeper.setTimeJtrInitBegins(System.nanoTime());
		
		exitcode = trin.JtrInit(trainsNumber, wagonLength, waitNb, waitTime,
				myCallbackCircuitChange.class.getName(), 
				myCallbackUtoDeliver.class.getName());
		
		timeKeeper.setTimeJtrInitEnds(System.nanoTime());


		if (exitcode < 0){
			System.out.println("JtrInit failed.");
			return;
		}

		try {
			semWaitEnoughMembers.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		new Thread(timeKeeper).start();

		if (rank < broadcasters){
			while (!measurementDone) {

				payload = Message.IntToByteArray(rankMessage);
				if (payload == null){
					System.out.println("Converting payload to byte array failed.");
					return;
				}

				msg = Message.messageFromPayload(payload);

				if (msg == null){
					System.out.println("Creating a message failed.");
					return;
				}

				trin.Jnewmsg(msg.getPayload().length, msg.getPayload());

				rankMessage++;

				exitcode = trin.JutoBroadcast(msg);
				if (exitcode < 0){
					System.out.println("JutoBroadcast failed.");
					return;
				}


				try {
					Thread.sleep(delayBetweenTwoUtoBroadcast);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} 
		System.out.println("rankMessage: " + rankMessage);
		System.out.println("** JtrTerminate");
		exitcode = trin.JtrTerminate();
		if (exitcode < 0){
			System.out.println("JtrTerminate failed.");
			return;
		}
		
		System.out.println("\n*********************\n");
		//System.exit(0);
	}
}
