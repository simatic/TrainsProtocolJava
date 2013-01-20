package examples;

import trains.*;

import java.util.concurrent.Semaphore;

public class Example {

	static boolean sender;
	static int nbMemberMin;
	static int delayBetweenTwoUtoBroadcast;
	static int nbRecMsgBeforeStop;
	static boolean terminate;
	static int nbRecMsg = 0;


	static Semaphore semWaitEnoughMembers;
	static Semaphore semWaitToDie;

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
			System.out.println(this.id);
			//Printing the circuit modification
			System.out.println("!!! ******** callbackCircuitChange called with " +  cv.getMemb() 
					+ " members (process ");

			//Printing the new/departed participant
			if(cv.getJoined() != 0){
				System.out.println(Integer.toString(cv.getJoined()) + " has arrived.)");
			} else {
				System.out.println(Integer.toString(cv.getDeparted()) + " is gone.)");
			}

			System.out.println(cv.getMemb() + " // " + nbMemberMin);
			if (cv.getMemb() >= nbMemberMin){
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
			//System.out.println(nbRecMsg + " " + nbRecMsgBeforeStop);
			if (nbRecMsg >= nbRecMsgBeforeStop) {
				terminate = true;
				semWaitToDie.release();
				//System.out.println("semWaitToDie released in UtoDeliver");
			}
      String content = new String(msg.getPayload());
			System.out.println("!!! " + nbRecMsg + "-ieme message (recu de " + sender + " / contenu = " + content + ")");
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

		// Test parameters
		sender = true;
		nbMemberMin = 3;
		delayBetweenTwoUtoBroadcast = 1000;
		nbRecMsgBeforeStop = 10;
		terminate = false;

		//Semaphores initialization
		semWaitEnoughMembers = new Semaphore(maxConcurrentRequests, true);
		semWaitToDie = new Semaphore(maxConcurrentRequests, true);

		//Callback
		myCallbackCircuitChange mycallbackCC = myCallbackCircuitChange.getInstance();
		mycallbackCC.setId(1);
		myCallbackUtoDeliver mycallbackUto = myCallbackUtoDeliver.getInstance();

		
		try {
			semWaitEnoughMembers.acquire();
			//System.out.println("semWaitEnoughMembers acquired in main");

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("** Load interface");
		Interface trin = Interface.trainsInterface();

		System.out.println("** trInit");
		exitcode = trin.JtrInit(trainsNumber, wagonLength, waitNb, waitTime,
				myCallbackCircuitChange.class.getName(), 
				myCallbackUtoDeliver.class.getName());

		if (exitcode < 0){
			System.out.println("JtrInit failed.");
			return;
		}

		try {
			semWaitEnoughMembers.acquire();
			//System.out.println("semWaitEnoughMembers acquired in main");

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (sender){
			while (!terminate) {
				//Filling the message
				//System.out.println("** Filling a message");

				payload = Message.StringToByteArray(String.valueOf(rankMessage) + " % hello");
				if (payload == null){
					System.out.println("Converting payload to byte array failed.");
					return;
				}
				
				msg = Message.messageFromPayload(payload);

				if (msg == null){
					System.out.println("Creating a message failed.");
					return;
				}

				//System.out.println("Payload: " + msg.getPayload());

				//Needed to keep count of the messages
				//System.out.println("** JnewMsg");
				trin.Jnewmsg(msg.getPayload().length, msg.getPayload());

				rankMessage++;

				//Sending the message
				//System.out.println("** JutoBroadcast");
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
			terminate = true;
			semWaitToDie.release();
			//System.out.println("semWaitToDie released in UtoDeliver");
		} else {
			try {
				semWaitToDie.acquire();
				//System.out.println("semWaitToDie acquired in main");

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		System.out.println("System received enough messages? game over !");

		System.out.println("** JtrTerminate");
		exitcode = trin.JtrTerminate();
		if (exitcode < 0){
			System.out.println("JtrTerminate failed.");
			return;
		}
		System.out.println("\n*********************\n");

		System.exit(0);
		//return;
	}
}
