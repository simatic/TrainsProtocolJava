package trains;

import java.util.Vector;

public class CircuitView {

	/* Singleton with static factory */
	private static final CircuitView CIRCUITVIEW = new CircuitView(0, 0, 0);
	
	// address type in C is unsigned short -> we deal with it in java with the char type
	
	private int nmemb; 	/* Number of members */
	//The first process is at the last referenced position
	//the last process to connect is at the first position
	private Vector<Integer> members;	        /* List of members */
	private int joined; 			/* New member */
	private int departed; 			/* Departed member*/
	
	private static final int MAX_MEMB = Interface.getMax_Memb();
	
	private CircuitView(int memb, int joined, int departed){
		this.nmemb = memb;
		this.members = new Vector<Integer>(MAX_MEMB);
		this.joined = joined;
		this.departed = departed;
	}
	
	public static CircuitView getInstance(){
		return CIRCUITVIEW;
	}
	
	public void setMemb(int memb){
		this.nmemb = memb;
	}

	public void setMembersAddress(int rank, int addr){
		this.members.add(rank, addr);
	}

	public void setJoined(int joined){
		this.joined = joined;
	}

	public void setDeparted(int departed){
		this.departed = departed;
	}
	
	public int getMemb(){
		return this.nmemb;
	}

	public int getMembersAddress(int rank){
		return this.members.elementAt(rank);
	}

	public int getJoined(){
		return this.joined;
	}

	public int getDeparted(){
		return this.departed;
	}
}
