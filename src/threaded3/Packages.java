/*
 * Package class
 */

package threaded3;

import java.text.SimpleDateFormat;
//import java.sql.Date;
import java.util.Date;
import java.util.Calendar;

public class Packages {

	//fields
	private int PackgeId;
	private String PkgName;
	private String PkgStartDate;
	private String PkgEndDate;
	private String PkgDesc;
	private double PkgBasePrice;
	private double PkgAgencyCommision;
	
	//constructor
	public Packages(int packgeId, String pkgName, String pkgStartDate, String pkgEndDate, String pkgDesc,
			double pkgBasePrice, double pkgAgencyCommision) {
		super();
		PackgeId = packgeId;
		PkgName = pkgName;
		PkgStartDate = pkgStartDate;
		PkgEndDate = pkgEndDate;
		PkgDesc = pkgDesc;
		PkgBasePrice = pkgBasePrice;
		PkgAgencyCommision = pkgAgencyCommision;
	} 	
	
	//default constructor
	public Packages() {
		//super();
	}



	//getter & Setters
	public int getPackgeId() {
		return PackgeId;
	}

	public void setPackgeId(int packgeId) {
		PackgeId = packgeId;
	}

	public String getPkgName() {
		return PkgName;
	}

	public void setPkgName(String pkgName) {
		PkgName = pkgName;
	}

	public String getPkgStartDate() {
		return PkgStartDate;
	}

	public void setPkgStartDate(String pkgStartDate) {
		PkgStartDate = pkgStartDate;
	}

	public String getPkgEndDate() {
		return PkgEndDate;
	}

	public void setPkgEndDate(String pkgEndDate) {
		PkgEndDate = pkgEndDate;
	}

	public String getPkgDesc() {
		return PkgDesc;
	}

	public void setPkgDesc(String pkgDesc) {
		PkgDesc = pkgDesc;
	}

	public double getPkgBasePrice() {
		return PkgBasePrice;
	}

	public void setPkgBasePrice(double pkgBasePrice) {
		PkgBasePrice = pkgBasePrice;
	}

	public double getPkgAgencyCommision() {
		return PkgAgencyCommision;
	}

	public void setPkgAgencyCommision(double pkgAgencyCommision) {
		PkgAgencyCommision = pkgAgencyCommision;
	}

	
	//toString method
	@Override
	public String toString() {
		return "Packages [PackgeId=" + PackgeId + ", PkgName=" + PkgName + ", PkgStartDate=" + PkgStartDate
				+ ", PkgEndDate=" + PkgEndDate + ", PkgDesc=" + PkgDesc + ", PkgBasePrice=" + PkgBasePrice
				+ ", PkgAgencyCommision=" + PkgAgencyCommision + "]";
	}
	
	
	
	
	
}
