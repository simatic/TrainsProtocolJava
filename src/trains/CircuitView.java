package trains;

public class CircuitView {

	/* Singleton with static factory */
	private static final CircuitView CIRCUITVIEW = new CircuitView(0, 0, 0);
	
	private int nmemb; 				/* Number of members */
	//XXX: should use an Array here
	private int members_address;	/* List of members */
	private int joined; 			/* New member */
	private int departed; 			/* Departed member*/
	
	private CircuitView(int memb, int joined, int departed){
		this.nmemb = memb;
		this.members_address = members_address;
		this.joined = joined;
		this.departed = departed;
	}
	
	public static CircuitView getInstance(){
		return CIRCUITVIEW;
	}

	public void setMemb(int memb){
		this.nmemb = memb;
	}

	public void setMembersAddress(int addr){
		this.members_address = addr;
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

	public int getMembersAddress(){
		return this.members_address;
	}

	public int getJoined(){
		return this.joined;
	}

	public int getDeparted(){
		return this.departed;
	}
}
