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

package examples;

import trains.*;

import java.util.concurrent.Semaphore;

/**
 * 
 * Example running the trains protocol.
 * The program sends messages with a rank number as a payload and print them at the reception.
 * 
 * @author Stephanie Ouillon
 *
 */

public class Example {

	/* Set to true if the user wants the process to utoBrodcast messages. */
	static boolean sender;
	
	/* Minimum number of members in the protocol before starting to utoBroadcast */
	static int nbMemberMin;
	
	/* Minimum delay in microseconds between 2 utoBroadcasts by the same process */ 
	static int delayBetweenTwoUtoBroadcast;
	
	/* Minimum numer of messages to be received before process stops */
	static int nbRecMsgBeforeStop;
	
	static boolean terminate;
	
	/* Counting the number of received messages */
	static int nbRecMsg = 0;


	/* Semaphore used to wait that enough members (nbMemberMin) are on the circuit */
	static Semaphore semWaitEnoughMembers;
	
	/* Semaphore used to know when to terminate the protocol */
	static Semaphore semWaitToDie;

	
	/* User's CallbackCircuitChange */
	public static class myCallbackCircuitChange implements CallbackCircuitChange{

		/* Singleton */
		private static final myCallbackCircuitChange CIRCUITCHANGE = new myCallbackCircuitChange();
		
		/* Additional attribute*/
		private int id = 0;
		
		private myCallbackCircuitChange(){
			//Nothing to do
		} 
		
		/* Mandatory static factory getInstance()*/
		public static myCallbackCircuitChange getInstance(){
			return CIRCUITCHANGE;
		}
		
		public void setId(int id){
			this.id = id;
		}

		/* The method called from the native code */
		@Override
		public void run(CircuitView cv){
			int rank;
			
			System.out.println("Callback id: " + this.id);
			
			/* Printing the circuit modification */
			System.out.println("!!! ******** callbackCircuitChange called with " +  cv.getMemb() 
					+ " members (process ");

			/* Printing the new/departed participant if any */
			if(cv.getJoined() != 0){
				System.out.println(Integer.toString(cv.getJoined()) + " has arrived.)");
			} else if (cv.getDeparted() != 0){
				System.out.println(Integer.toString(cv.getDeparted()) + " is gone.)");
			}

			/* Checking if there is enough members to start sending messages */
			System.out.println(cv.getMemb() + " // " + nbMemberMin);
			if (cv.getMemb() >= nbMemberMin){
				System.out.println("!!! ******** enough members to start utoBroadcasting\n");
				semWaitEnoughMembers.release();
			}
			
			/* Printing the circuit view data (the members list) */
			for(rank=0; rank < nbMemberMin; rank++){
				System.out.println("address for rank " + rank + ": " + cv.getMembersAddress(rank));
			}
		}
	}

	
	/* User's CallbackUtoDeliver */
	public static class myCallbackUtoDeliver implements CallbackUtoDeliver{

		/* Singleton */
		private static final myCallbackUtoDeliver UTODELIVER = new myCallbackUtoDeliver();
	
		public myCallbackUtoDeliver(){
			//Nothing to do
		} 
		
		/* Mandatory static factory getInstance()*/
		public static myCallbackUtoDeliver getInstance(){
			return UTODELIVER;
		}

		/* The method called from the native code */
		@Override
		public void run(int sender, Message msg){
      
			/* Receiving a fixed number of messages */
			nbRecMsg++;
			if (nbRecMsg >= nbRecMsgBeforeStop) {
				terminate = true;
				semWaitToDie.release();
			}
			
			/* Getting and printing the content of the message */
			String content = new String(msg.getPayload());
			System.out.println("!!! " + nbRecMsg + "-ieme message (recu de " + sender + " / contenu = " + content + ")");
		}
	}


	public static void main(String args[]) {	

		/* trInit parameters set to 0 to use default values of the protocol */
		int trainsNumber = 0;
		int wagonLength = 0;
		int waitNb = 0;
		int waitTime = 0;

		/* To build the messages */
		byte[] payload = null;
		int rankMessage = 0;
		Message msg = null;
		
		int exitcode;
		
		/* Max concurrent requests for the semaphores */
		int maxConcurrentRequests = 1;

		/* Test parameters */
		sender = true;
		nbMemberMin = 3;
		delayBetweenTwoUtoBroadcast = 1000;
		nbRecMsgBeforeStop = 10;
		terminate = false;

		/* Semaphores initialization */
		semWaitEnoughMembers = new Semaphore(maxConcurrentRequests, true);
		semWaitToDie = new Semaphore(maxConcurrentRequests, true);

		/* Callbacks */
		myCallbackCircuitChange mycallbackCC = myCallbackCircuitChange.getInstance();
		mycallbackCC.setId(1);
		myCallbackUtoDeliver mycallbackUto = myCallbackUtoDeliver.getInstance();

		
		try {
			semWaitEnoughMembers.acquire();

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

		System.out.println("my address is: " + trin.JgetMyAddress());
		
		/* Waiting for other members to join */
		try {
			semWaitEnoughMembers.acquire();
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/* If this process is a sender, then begin to send messages */
		if (sender){
			while (!terminate) {

				/* Creating and filling a message */
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

				trin.Jnewmsg(msg.getPayload().length, msg.getPayload());
				
				rankMessage++;

				/* Broadcasting the message */
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
			
		} else {
			try {
				semWaitToDie.acquire();

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
	}
}
