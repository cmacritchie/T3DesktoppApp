package threaded3;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

//import application.Agent;

public class TravelXDB {


	static String url = "jdbc:mysql://localhost:3306/travelexperts"; //connection
	static String username = "root"; //user name default
	static String password = ""; //password, default



//vectors 396 & 397 in Java book Drop down combo box
	//This method gets all the Packages
	public static ObservableList<Packages> GetAllPackages() //List<Packages> GetALLPackages()
	{
		System.out.println("connected");
		//List<Packages> packages = new ArrayList<String>();
		ObservableList<Packages> packages = FXCollections.observableArrayList();
		
		Packages pkg = null;
		
		try
		{
			java.sql.Connection connection = DriverManager.getConnection(url, username, password);
			
			//selects string set and executed in the query
			String selectAllPackages = "SELECT * FROM Packages";
			Statement selectStatement = connection.createStatement();
			ResultSet result = selectStatement.executeQuery(selectAllPackages);
			
			//while there is agents in the select statement
			while(result.next())
			{
				pkg = new Packages(result.getInt(1), result.getString(2), result.getString(3),
						result.getString(4), result.getString(5), result.getDouble(6), result.getDouble(7));
				
				packages.add(pkg);
			}
			System.out.println("tester");
			System.out.println(packages.size());
		} 
		catch (SQLException e)
		{
			//help locate errors
			System.out.println(e.getErrorCode()); e.printStackTrace();    
		}
		return packages;
	}
	
	//this method gets the agent based off their id
		public static Packages GetPackageID(int packageid)
		{
			Packages selectedPackage = new Packages();
			try(Connection connection = DriverManager.getConnection(url, username, password))
			{
				//uses agent id for the ? in select agent query
				String selectPackage = "SELECT * FROM Packages where PackageId=?";
				PreparedStatement selectState = connection.prepareStatement(selectPackage);
				selectState.setInt(1, packageid);
				
				ResultSet resReader = selectState.executeQuery();
				System.out.println(resReader);
				
				//goes through single agent (since there should only be one agent id per agent) and gets agent info
				while(resReader.next())
				{
					//somehow it's getting all the relevant info  clean up later
					selectedPackage.setPackgeId(resReader.getInt("PackagedId"));
					selectedPackage.setPkgName(resReader.getString("PkgName"));
				//	selectedPackage.setPkgStartDate(resReader.getString("AgtLastName"));
				//	selectedPackage.setPkgEndDate(resReader.getString("AgtEmail"));
					selectedPackage.setPkgBasePrice(resReader.getDouble("AgtBusPhone"));
					selectedPackage.setPkgAgencyCommision(resReader.getDouble("AgtPosition"));
				}
			}
			catch (SQLException e)
			{
				//help locate errors
				System.out.println(e.getErrorCode()); e.printStackTrace();
			}
			return selectedPackage;
		}
		
		
		
		
		
		//Suppliers This portion handles all the method for the suppliers scene
		
		public static ObservableList<Supplier> GetAllSuppliers() 
		{
			System.out.println("supplier loading begun");
			
			ObservableList<Supplier> suppliers = FXCollections.observableArrayList();
			
			Supplier sup = null;
			
			try
			{
				java.sql.Connection connection = DriverManager.getConnection(url, username, password);
				
				//selectString gets all the suppliers
				String selectAllSuppliers = "SELECT * FROM Suppliers";
				Statement stmAllSuppliers = connection.createStatement();
				ResultSet allSuppResult = stmAllSuppliers.executeQuery(selectAllSuppliers);
				
				//while there are suppliers in the list, keep adding them
				while(allSuppResult.next())
				{
					sup = new Supplier(allSuppResult.getInt(1), allSuppResult.getString(2));
					
					suppliers.add(sup);
				}
				System.out.println("supplierAdded");
				
								
			
			}
			catch (SQLException e)
			{
				System.out.println(e.getErrorCode());
				e.printStackTrace();
			}
			return suppliers;
		}
		
		public static Supplier GetSupplierID(int supplierid)
		{
			Supplier selectedSupplier = new Supplier();
			try(Connection connection = DriverManager.getConnection(url, username, password))
			{
				//uses supplier id for the ? in the select supplier form query
				String selectSupplier = "Select * FROM Suppliers where SupplierId =?";
				PreparedStatement selectSupp = connection.prepareStatement(selectSupplier);
				selectSupp.setInt(1, supplierid);
				
				ResultSet resReader = selectSupp.executeQuery();
				System.out.println(resReader);
				
				while(resReader.next())
				{
					selectedSupplier.setSuppId(resReader.getInt("SupplierId"));
					selectedSupplier.setSuppName(resReader.getString("SupName"));
				}
				
			}
				catch (SQLException e)
				{
					System.out.println(e.getErrorCode());
					e.printStackTrace();
				}
				return  selectedSupplier;
			}
		
		//Adds new supplier
		public static void AddSupplier(String SupplierName)
		{
			try(Connection connection = DriverManager.getConnection(url, username, password))
			{
				String addSupplier = "insert into Suppliers(SupName) Values(?)";
				PreparedStatement PrepState = connection.prepareStatement(addSupplier);
				PrepState.setString(1, SupplierName);
				
				PrepState.executeUpdate();
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
		
		//delete Supplier
		public static void DeleteSupplier(int supplierid)
		{
			try(Connection connection = DriverManager.getConnection(url, username, password))
			{
				String deleteSupplier = "DELETE FROM Products_Suppliers WHERE SupplierId =? ";
						               
				
				PreparedStatement deleteState = connection.prepareStatement(deleteSupplier);
				deleteState.setInt(1, supplierid);
				deleteState.executeUpdate();
				System.out.println("check 4");
			}
			catch (SQLException e)
			{
				e.printStackTrace();
				
			}
		}
		
		//delete Products_supplier
		public static void DeleteProductsSupplier(int supplierid)
		{
			try(Connection connection = DriverManager.getConnection(url, username, password))
			{
				String deletePS = "DELETE FROM Suppliers WHERE SupplierId=? ";
				
				PreparedStatement deleteState = connection.prepareStatement(deletePS);
				deleteState.setInt(1, supplierid);
				deleteState.executeUpdate();
				System.out.println("check 8");
			}
			catch (SQLException e)
			{
				e.printStackTrace();
				
			}
		}
		
		public static void UpdateSupplier(int supplierid, String supplierName)
		{
			try(Connection connection = DriverManager.getConnection(url, username, password))
			{
				String updateSupplier = "UPDATE Suppliers SET SupName = ? WHERE SupplierId = ?";
				PreparedStatement updateState = connection.prepareStatement(updateSupplier);
				updateState.setString(1, supplierName);
				updateState.setInt(2, supplierid);
				
				updateState.executeUpdate();
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
		
		
		
		
		
		
		
		//Get All Products
		public static ObservableList<Product> GetAllProducts() 
		{
			System.out.println("products connection begun");
			
			ObservableList<Product> products = FXCollections.observableArrayList();
			
			Product  prd = null;
			
			try (java.sql.Connection connection = DriverManager.getConnection(url, username, password))
			{
				String selectProduct = "SELECT * FROM Products";
				Statement selectStatement = connection.createStatement();
				ResultSet result = selectStatement.executeQuery(selectProduct);
				
				while(result.next())
				{
					prd = new Product(result.getInt(1), result.getString(2));
					
					products.add(prd);
				}
				System.out.println("prod added");
				System.out.println(products.size());
				
			}
			catch (SQLException e)
			{
				System.out.println(e.getErrorCode());
				e.printStackTrace();
			}
			return products;
			//finsihed here
		}
	
		//Add a Product
		public static void DeleteProduct(int productid)
		{
			try(Connection connection = DriverManager.getConnection(url, username, password))
			{
				String deleteProd = "DELETE FROM Products WHERE ProductId=?";
				PreparedStatement deleteState = connection.prepareStatement(deleteProd);
				deleteState.setInt(1, productid);
				
				deleteState.executeUpdate();
				System.out.println("deletion complete");
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
		
		//Add Product
		public static void AddProduct(String ProdName)
		{
			try(Connection connection = DriverManager.getConnection(url, username, password))
			{
				String addProduct = "insert into Products (ProdName) Values(?)";
				PreparedStatement addState = connection.prepareStatement(addProduct);
				addState.setString(1, ProdName);
				
				addState.executeUpdate();
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
		
		public static void UpdateProduct(int productid, String updateName)
		{
			try(Connection connection = DriverManager.getConnection(url, username, password))
			{
				String updateProduct = "UPDATE Products " +
									   "SET ProdName = ? " +
									   "WHERE ProductId = ?";
				PreparedStatement updateState = connection.prepareStatement(updateProduct);
				updateState.setString(1, updateName);
				updateState.setInt(2, productid);
				
				updateState.executeUpdate();
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
		
		
	
		//gets all products that are offered
		public static ObservableList<ProductSupplier> GetOfferedProducts(int supplierid){
			
			ObservableList<ProductSupplier> offeredProducts = FXCollections.observableArrayList();
			
			ProductSupplier prdsp = null;
			
			try
			{
				java.sql.Connection connection = DriverManager.getConnection(url, username, password);
				String selectOffPack = "select ps.ProductSupplierId, ps.ProductId, ps.SupplierId, p.ProdName, s.SupName " +
										"from Products_Suppliers ps " +
										"inner join Products p " +
										"On p.ProductId = ps.ProductId " +
										"inner join Suppliers s " + 
										"on s.SupplierId = ps.SupplierId " +
										"where s.SupplierId = ?";
				PreparedStatement selectStatement =connection.prepareStatement(selectOffPack);
				selectStatement.setInt(1, supplierid);
				ResultSet result = selectStatement.executeQuery();
				
				while(result.next())
				{
					prdsp = new ProductSupplier(result.getInt(1), result.getInt(2), result.getInt(3), result.getString(4), result.getString(5));
					
					
					
					
					offeredProducts.add(prdsp);
				}
				System.out.println("product Suppliers added");
			}
			catch (SQLException e)
			{
				System.out.println(e.getErrorCode());
				e.printStackTrace();
			}
			return offeredProducts;
			
		}
		//gets all available products that are not provided by supplier
public static ObservableList<Product> GetAvailableProducts(int supplierid){
			
			ObservableList<Product> availableProducts = FXCollections.observableArrayList();
			
			//Product prdsp = new Product();
			Product prdsp = null;
			System.out.println("foo");
			try
			{
				java.sql.Connection connection = DriverManager.getConnection(url, username, password);
				String selectAvailPack = 
						
						"SELECT DISTINCT p.ProductId, p.ProdName " + 
						"FROM Products p " +
						"WHERE p.ProductId NOT IN " +
						"(select ProductId " + 
						"from Products_Suppliers " + 
						"Where SupplierId = ?)";
						
						
				System.out.println("bar");								
				PreparedStatement selectStatement =connection.prepareStatement(selectAvailPack);
				selectStatement.setInt(1, supplierid);
				ResultSet result = selectStatement.executeQuery();
				
				
				while(result.next())
				{
					prdsp = new Product(result.getInt(1), result.getString(2));
					//prdsp.setProdSupplier(result.getInt("ProductSupplierId"));
					//prdsp.setProdId(result.getInt("p.ProductId"));
					//prdsp.setSuppId(result.getInt("SupplierId"));
					//prdsp.setSuppName(result.getString("SupName"));
					//prdsp.setProdName(result.getString("p.ProdName"));
				
					availableProducts.add(prdsp);
				}
				System.out.println("available " +availableProducts);
			}
			catch (SQLException e)
			{
				System.out.println(e.getErrorCode());
				e.printStackTrace();
			}
			return availableProducts;
			
		}

//this method adds products from available to Offered
public static void AddAvailProducts(int productid, int supplierid)
{
	try(Connection connection = DriverManager.getConnection(url, username, password))
	{
		System.out.println("part one");
		String addOffProd = "INSERT INTO Products_Suppliers (ProductId, SupplierId) VALUES(?, ?)";
		PreparedStatement addState = connection.prepareStatement(addOffProd);
		addState.setInt(1, productid);
		addState.setInt(2,  supplierid);
		System.out.println("part 2");
		
		addState.executeUpdate();
		System.out.println("insert completed");
	}
	catch(SQLException e)
	{
		System.out.println(e.getErrorCode());
		e.printStackTrace();
	}
	//return true;
}

//this method moves Offered Products back to available
public static void RemoveOfferProducts(int productid, int supplierid)
{
	try(Connection connection = DriverManager.getConnection(url, username, password))
	{
		
		String RemOffProd = "Delete From Products_Suppliers Where SupplierId=? AND ProductID =?";
		PreparedStatement remState = connection.prepareStatement(RemOffProd);
		remState.setInt(1, supplierid);
		remState.setInt(2,  productid);
		
		
		remState.executeUpdate();
		System.out.println("remove completed");
	}
	catch(SQLException e)
	{
		System.out.println(e.getErrorCode());
		e.printStackTrace();
	}
	//return true;
}


	
}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		