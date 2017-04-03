package threaded3;

public class ProductSupplier {

	//fields
	private int ProdSupplier; //product supplier number
	private int ProdId; // product supplier
	private int SuppId; // supplier id
	private String ProdName;
	private String SuppName;
	
	
	
	public ProductSupplier(int prodSupplier, int prodId, int suppId, String prodName, String suppName) {
		super();
		ProdSupplier = prodSupplier;
		ProdId = prodId;
		SuppId = suppId;
		ProdName = prodName;
		SuppName = suppName;
	}


	//default constructor
	public ProductSupplier() {
		
	}

	
	//getters and setters
	public int getProdSupplier() {
		return ProdSupplier;
	}

	public void setProdSupplier(int prodSupplier) {
		ProdSupplier = prodSupplier;
	}

	public int getProdId() {
		return ProdId;
	}

	public void setProdId(int prodId) {
		ProdId = prodId;
	}

	public int getSuppId() {
		return SuppId;
	}

	public void setSuppId(int suppId) {
		SuppId = suppId;
	}

	public String getProdName() {
		return ProdName;
	}

	public void setProdName(String prodName) {
		ProdName = prodName;
	}

	public String getSuppName() {
		return SuppName;
	}

	public void setSuppName(String suppName) {
		SuppName = suppName;
	}


	//toString method... just in case
	@Override
	public String toString() {
		return "ProductSupplier [ProdSupplier=" + ProdSupplier + ", ProdId=" + ProdId + ", SuppId=" + SuppId
				+ ", ProdName=" + ProdName + ", SuppName=" + SuppName + "]";
	}
	
	
	
	
	
}
