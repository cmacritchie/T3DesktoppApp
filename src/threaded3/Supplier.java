package threaded3;

public class Supplier {

	//fields
	private int SuppId;
	private String SuppName;
	
	//default constructor
	public Supplier(){
		
	}
	
	public Supplier(int suppId, String suppName) {
		super();
		SuppId = suppId;
		SuppName = suppName;
	}

	//getters and setters
	public int getSuppId() {
		return SuppId;
	}

	public void setSuppId(int suppId) {
		SuppId = suppId;
	}

	public String getSuppName() {
		return SuppName;
	}

	public void setSuppName(String suppName) {
		SuppName = suppName;
	}

	@Override
	public String toString() {
		return "Supplier [SuppId=" + SuppId + ", SuppName=" + SuppName + "]";
	}
	
}
