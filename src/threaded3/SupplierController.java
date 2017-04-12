package threaded3;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Labeled;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * 
 * @author  Craig MacRitchie and Kevin Yan
 * 
 * CRUD operations on the Supplier table for Supplier Scene
 *
 */
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
	private ChangeListener<Object> supplierProducts;
	
	
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
						tvProdOffer.getSelectionModel().selectFirst();
						tvProdAvailable.getSelectionModel().selectFirst();
					}
					
				};
				tvSuppliers.getSelectionModel().selectedItemProperty().addListener(supplierSelected);
				
				
				//listener to Available Products
				supplierProducts = new ChangeListener() {

					@Override
					public void changed(ObservableValue arg0, Object arg1, Object arg2) {
						// TODO Auto-generated method stub
						Product prodSelected = tvProdAvailable.getSelectionModel().getSelectedItem();
						
						Supplier suppselected  = tvSuppliers.getSelectionModel().getSelectedItem(); //double instantiated!!!
						//TravelXDB.AddAvailProducts(prodSelected.getProdId(), suppselected.getSuppId());
					}
						
				}; 
				
				tvProdAvailable.getSelectionModel().selectedItemProperty().addListener(supplierProducts);
			
				
			
				tvSuppliers.getSelectionModel().selectFirst();
				tvProdOffer.getSelectionModel().selectFirst();
				tvProdAvailable.getSelectionModel().selectFirst();
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
		
		
		//Turning off change listener when a supplier changes products available
		offeredAvailableTableUpdate (suppselected);
		
		addRemoveSupplierButtons();
		tvProdOffer.getSelectionModel().selectFirst();
		tvProdAvailable.getSelectionModel().selectFirst();
	}
	
	@FXML void removeOffered(ActionEvent event)
	{
		ProductSupplier prodSelected = tvProdOffer.getSelectionModel().getSelectedItem();
		
		Supplier suppselected  = tvSuppliers.getSelectionModel().getSelectedItem();
		
		TravelXDB.RemoveOfferProducts(prodSelected.getProdId(), suppselected.getSuppId());
		
		//Turning off change listener when a supplier changes products available
		offeredAvailableTableUpdate (suppselected);
		
		addRemoveSupplierButtons();
		tvProdOffer.getSelectionModel().selectFirst();
		tvProdAvailable.getSelectionModel().selectFirst();
	}

	
	@FXML void addSupplier(ActionEvent event)
	{
		int clone = 0;
		String newSupplier = txtSuppliers.getText();
		for (Supplier sup : supplierList) {
		    if(sup.getSuppName().equals(newSupplier))
		    {	
		    	clone++;
		    }   
		}
		
		if(validator() && clone == 0)
		{
		
		TravelXDB.AddSupplier(newSupplier);
		
		updateTVSuppliers ();
		txtSuppliers.setText("");
		
		tvSuppliers.getSelectionModel().selectLast();
		tvSuppliers.scrollTo(tvSuppliers.getItems().size()-1);
		
		//needs to be here because new suppliers have no products to begin with
		addRemoveSupplierButtons();
		tvProdOffer.getSelectionModel().selectFirst();
		tvProdAvailable.getSelectionModel().selectFirst();
		}
		else
		{
			AlertNotify();
		}
	}
	
	@FXML void deleteSupplier(ActionEvent event)
	{
		Supplier selected = tvSuppliers.getSelectionModel().getSelectedItem();
		TravelXDB.DeleteProductsSupplier(selected.getSuppId());
		TravelXDB.DeleteSupplier(selected.getSuppId());
		
		
		//Turning off change listener when a supplier changes products available
		offeredAvailableTableUpdate (selected);
		
		updateTVSuppliers ();
		txtSuppliers.setText("");
		
		tvSuppliers.getSelectionModel().selectFirst();
		tvSuppliers.scrollTo(0);
		tvProdOffer.getSelectionModel().selectFirst();
		tvProdAvailable.getSelectionModel().selectFirst();
	}
	
	@FXML void updateSupplier(ActionEvent event)
	{
		int clone = 0;
		String newSupplier = txtSuppliers.getText();
		for (Supplier sup : supplierList) {
		    if(sup.getSuppName().equals(newSupplier))
		    {	
		    	clone++;
		    }   
		}
		
		if(validator() && clone <= 1)
		{
		Supplier selected = tvSuppliers.getSelectionModel().getSelectedItem();
		String updateName = txtSuppliers.getText();
		
		int index = tvSuppliers.getSelectionModel().getFocusedIndex();
		
		TravelXDB.UpdateSupplier(selected.getSuppId(), updateName);
		
		updateTVSuppliers ();
		txtSuppliers.setText("");
		
		tvSuppliers.getSelectionModel().select(index);
		tvSuppliers.scrollTo(index);
		tvProdOffer.getSelectionModel().selectFirst();
		tvProdAvailable.getSelectionModel().selectFirst();
		}
		else
		{
			AlertNotify();
		}
	}
	
	/*
	 * Turns off supplierSelected change listener temporarily for tvSuppliers to update.
	 */
	private void updateTVSuppliers ()
	{
		tvSuppliers.getSelectionModel().selectedItemProperty().removeListener(supplierSelected);
		tvSuppliers.setItems(TravelXDB.GetAllSuppliers());
		tvSuppliers.getSelectionModel().selectedItemProperty().addListener(supplierSelected);
	}
	
	private void AlertNotify()
	{
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText("Supplier Name");
		alert.setContentText("The Supplier name cannot be left blank and must be unique!");

		alert.showAndWait();
	}
	
	//checks to make sure that the product name was not left blank
	private boolean validator()
	{
		String read = txtSuppliers.getText();
		
		if (read.length() > 0)
			return true;
		else
			return false;
			
		
	}

	/*
	 * Turns off supplierProduct change listener temporarily for tvProdAvailable and tvProdOffer to update.
	 */
	private void offeredAvailableTableUpdate (Supplier sup)
	{
		tvProdAvailable.getSelectionModel().selectedItemProperty().removeListener(supplierProducts);
		tvProdAvailable.setItems(TravelXDB.GetAvailableProducts(sup.getSuppId()));
		tvProdAvailable.getSelectionModel().selectedItemProperty().addListener(supplierProducts);
		
		tvProdOffer.getSelectionModel().selectedItemProperty().removeListener(supplierProducts);
		tvProdOffer.setItems(TravelXDB.GetOfferedProducts(sup.getSuppId()));
		tvProdOffer.getSelectionModel().selectedItemProperty().addListener(supplierProducts);
	}
	
	/*
	 * Disables the add and remove buttons on the window so that empty available or empty offered tables can't add to the other
	 */
	private void addRemoveSupplierButtons()
	{
		if (tvProdOffer.getItems().size() <= 0 && tvProdAvailable.getItems().size() > 0)
		{
			btnProdRemove.setDisable(true);
			btnProdAdd.setDisable(false);
		}
		else if (tvProdOffer.getItems().size() > 0 && tvProdAvailable.getItems().size() <= 0)
		{
			btnProdRemove.setDisable(false);
			btnProdAdd.setDisable(true);
		}
		else if (tvProdOffer.getItems().size() <= 0 && tvProdAvailable.getItems().size() <= 0)
		{
			btnProdRemove.setDisable(true);
			btnProdAdd.setDisable(true);
		}
		else 
		{
			btnProdRemove.setDisable(false);
			btnProdAdd.setDisable(false);
		}
	}
}
