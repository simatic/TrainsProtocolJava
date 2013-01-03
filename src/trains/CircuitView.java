package trains;

import java.util.HashMap;

public class CircuitView {

	/* Singleton with static factory */
	private static final CircuitView CIRCUITVIEW = new CircuitView(0, 0, 0);
	
	private int nmemb; 				/* Number of members */
	private HashMap<Integer,Integer> members;	/* List of members */
	private int joined; 			/* New member */
	private int departed; 			/* Departed member*/
	
	private CircuitView(int memb, int joined, int departed){
		this.nmemb = memb;
		this.members = new HashMap<Integer,Integer>(/* default initial capacity at 16 */);
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
		this.members.put(rank, addr);
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
		return this.members.get(rank).intValue();
	}

	public int getJoined(){
		return this.joined;
	}

	public int getDeparted(){
		return this.departed;
	}
}
