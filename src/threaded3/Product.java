package threaded3;

/**
 * 
 * @author Craig MacRitchie and Kevin Yan
 * 
 * Represents Product table on in the database
 *
 */
public class Product
{

	//fields
	private int ProdId;
	private String ProdName;
	
	//default constructor
	public Product() {
		
	}

	//constructor
	public Product(int prodId, String prodName) {
		super();
		ProdId = prodId;
		ProdName = prodName;
	}

	//getter and setters
	public int getProdId() {
		return ProdId;
	}

	public void setProdId(int prodId) {
		ProdId = prodId;
	}

	public String getProdName() {
		return ProdName;
	}

	public void setProdName(String prodName) {
		ProdName = prodName;
	}

	@Override
	public String toString() {
		return "Product [ProdId=" + ProdId + ", ProdName=" + ProdName + "]";
	}
	
	
}
