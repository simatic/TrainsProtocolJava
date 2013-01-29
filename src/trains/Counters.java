package trains;

public class Counters {
	//Dump of the structure "counters"
	//We don't need to access the fields, we're just storing the bytes 
	//and passing them to native code
	private byte[] data;

	public Counters(){
		this.data = new byte[4096];
	}
	
	public byte[] getData(){
		return this.data;
	}
}
