package threaded3;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Labeled;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class SupplierController implements Initializable {
	
	//declare list of suppliers
	ObservableList<Supplier> supplierList;
	//declare list of offered products
	ObservableList<ProductSupplier> offeredProductsList;
	//declare list of available products
	ObservableList<Product> availableProductsList;
	//here
	
	//declare labels
	@FXML Labeled lblSuppliers;
	
	//declare text fields
	@FXML TextField txtSuppliers;
	
	
	//declare buttons
	@FXML Button btnAddSupplier;
	@FXML Button btnDeleteSupplier;
	@FXML Button btnUpdateSupplier;
	@FXML Button btnProdAdd;
	@FXML Button btnProdRemove;
	
	
	//declare table view all Suppliers
	@FXML
	private TableView<Supplier> tvSuppliers = new TableView<Supplier>();
	@FXML
	private TableColumn<Supplier, Integer> supplierId = new TableColumn();
	@FXML
	private TableColumn<Supplier, String> supplierName = new TableColumn();
	
	//declare table view Proucts offered
	@FXML
	private TableView<ProductSupplier> tvProdOffer = new TableView<ProductSupplier>();
	@FXML 
	private TableColumn<ProductSupplier, Integer> productOfferId = new TableColumn();
	@FXML
	private TableColumn<ProductSupplier, String> prodOfferNameName = new TableColumn();
	
	//declare table view Products Available
	@FXML
	private TableView<Product> tvProdAvailable = new TableView<Product>();
	@FXML
	private TableColumn<Product, Integer> prodAvailable = new TableColumn();
	@FXML
	private TableColumn<Product, String> prodAvailableName = new TableColumn();
	
	private ChangeListener<Object> supplierSelected;
	
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO Auto-generated method stub
		
		//ensures suppliers is loaded
				System.out.print("Suppliers Loaded");
				
				supplierList = TravelXDB.GetAllSuppliers();
				
				//Supplier List
				supplierId.setCellValueFactory(new PropertyValueFactory<Supplier, Integer>("SuppId"));
				supplierName.setCellValueFactory(new PropertyValueFactory<Supplier, String>("SuppName"));
				
				//Products Offered List
				prodOfferNameName.setCellValueFactory(new PropertyValueFactory<ProductSupplier, String>("ProdName"));
				
				//Products Available List
				prodAvailableName.setCellValueFactory(new PropertyValueFactory<Product, String>("ProdName"));
				
				System.out.println(supplierList.size());
				tvSuppliers.setItems(supplierList);
				
				Supplier SupplierSelected = tvSuppliers.getSelectionModel().getSelectedItem();
				System.out.println(SupplierSelected);
				
				//listeners made global
				//Supplier suppselected  = new Supplier(); //tvSuppliers.getSelectionModel().getSelectedItem();
				//Product prodSelected = null; //tvProdAvailable.getSelectionModel().getSelectedItem();
				
				
				//listener for table change  BEANs value?
				supplierSelected = new ChangeListener() {

					@Override
					public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
						// TODO Auto-generated method stub
						Supplier suppselected  = tvSuppliers.getSelectionModel().getSelectedItem();
						System.out.println(suppselected);
						
						//get offered products list and reload						
						offeredProductsList = TravelXDB.GetOfferedProducts(suppselected.getSuppId());
						tvProdOffer.setItems(offeredProductsList);
						
						//get available products list and reload
						availableProductsList = TravelXDB.GetAvailableProducts(suppselected.getSuppId());
						//System.out.print(availableProductsList);
						tvProdAvailable.setItems(availableProductsList);
						
						
						
						
						
						txtSuppliers.setText(suppselected.getSuppName());
					}
					
				};
				tvSuppliers.getSelectionModel().selectedItemProperty().addListener(supplierSelected);
				
				//listener to Available Products
				tvProdAvailable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {

					@Override
					public void changed(ObservableValue arg0, Object arg1, Object arg2) {
						// TODO Auto-generated method stub
						Product prodSelected = tvProdAvailable.getSelectionModel().getSelectedItem();
						
						Supplier suppselected  = tvSuppliers.getSelectionModel().getSelectedItem(); //double instantiated!!!
						//TravelXDB.AddAvailProducts(prodSelected.getProdId(), suppselected.getSuppId());
					}
						
				}); 
				
				
			
				
			
		
	}
	
	//Product prodSelected = tvProdAvailable.getSelectionModel().getSelectedItem();
	
	//Supplier suppselected  = tvSuppliers.getSelectionModel().getSelectedItem(); //double instantiated!!!
	
	
	//button add Available works but needs to add update.
	@FXML void addAvailable(ActionEvent event)
	{
		Product prodSelected = tvProdAvailable.getSelectionModel().getSelectedItem();
		
		Supplier suppselected  = tvSuppliers.getSelectionModel().getSelectedItem(); //double instantiated!!!
		System.out.println("this works");
		TravelXDB.AddAvailProducts(prodSelected.getProdId(), suppselected.getSuppId());
		System.out.println("This one does too");
		TravelXDB.GetAvailableProducts(suppselected.getSuppId());
		TravelXDB.GetOfferedProducts(suppselected.getSuppId());
		
		
	}
	
	@FXML void removeOffered(ActionEvent event)
	{
		ProductSupplier prodSelected = tvProdOffer.getSelectionModel().getSelectedItem();
		
		Supplier suppselected  = tvSuppliers.getSelectionModel().getSelectedItem();
		
		TravelXDB.RemoveOfferProducts(prodSelected.getProdId(), suppselected.getSuppId());
		
	}

	
	@FXML void addSupplier(ActionEvent event)
	{
		String newSupplier = txtSuppliers.getText();
		
		TravelXDB.AddSupplier(newSupplier);
		
		updateTVSuppliers ();
		txtSuppliers.setText("");
	}
	
	@FXML void deleteSupplier(ActionEvent event)
	{
		Supplier selected = tvSuppliers.getSelectionModel().getSelectedItem();
		TravelXDB.DeleteProductsSupplier(selected.getSuppId());
		TravelXDB.DeleteSupplier(selected.getSuppId());
		
		updateTVSuppliers ();
		txtSuppliers.setText("");
	}
	
	@FXML void updateSupplier(ActionEvent event)
	{
		Supplier selected = tvSuppliers.getSelectionModel().getSelectedItem();
		String updateName = txtSuppliers.getText();
		TravelXDB.UpdateSupplier(selected.getSuppId(), updateName);
		
		updateTVSuppliers ();
		txtSuppliers.setText("");
	}
	
	private void updateTVSuppliers ()
	{
		tvSuppliers.getSelectionModel().selectedItemProperty().removeListener(supplierSelected);
		tvSuppliers.setItems(TravelXDB.GetAllSuppliers());
		tvSuppliers.getSelectionModel().selectedItemProperty().addListener(supplierSelected);
	}

}
