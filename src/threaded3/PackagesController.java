/**
 * This is the controller for the main packages scene.
 * 
 */
package threaded3;


import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
//import day7exercise.Agent;
//import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Labeled;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class PackagesController implements Initializable {

	//declare list of Packages
	ObservableList<Packages> packagesList;
	//declare list of suppliers available
	ObservableList<ProductSupplier> psAvailableList;
	//declare list included products and suppliers
	ObservableList<ProductSupplier> psOwnedList;

	//ComboBox
	
	@FXML ComboBox<String> cbbProducts = new ComboBox<String>();
	
	//labels
	@FXML Labeled lblPackages; 
	@FXML Labeled lblPackageName;
	@FXML Labeled lblStartDate;
	@FXML Labeled lblEndDate;
	@FXML Labeled lblBasePrice;
	@FXML Labeled lblAgencyCommision;
	@FXML Labeled lblDescription;
	
	//text field
	@FXML TextField txtPackages; 
	@FXML TextField txtStartDate;
	@FXML TextField txtEndDate;
	@FXML TextField txtBasePrice;
	@FXML TextField txtAgencyCommision;
	@FXML TextField txtDescription;
	
	//date time picker
	@FXML DatePicker dpStartDate = new DatePicker();
	@FXML DatePicker dpEndDate = new DatePicker();
	
	//button
	@FXML Button btnTabSupplier;
	@FXML Button btnTabProducts;
	@FXML Button btnNew;
	@FXML Button btnAdd;
	@FXML Button btnUpdate;
	@FXML Button btnDelete;
	@FXML Button btnCancel;
	@FXML Button btnChngeleft;
	@FXML Button btnChngeRight;
	@FXML Button btnAddPackage;
	@FXML Button btnDeletePackage;
	@FXML Button btnUpdatePackage;
	
	//package change 
	String prodSelected = null;
	Packages Packselected = null;
	ProductSupplier psSelected = null;
	ProductSupplier psOwnSelected = null;
	
	//table and table columns
    @FXML
    private TableView<Packages> tvPackages = new TableView<Packages>();
    @FXML
    private TableColumn<Packages, Integer> packagesID = new TableColumn();
    @FXML
    private TableColumn<Packages, String> packagesName = new TableColumn();
    @FXML
    private TableColumn<Packages, String> packagesStartDate = new TableColumn();
    @FXML
    private TableColumn<Packages, String> packagesEndDate = new TableColumn();
    @FXML
    private TableColumn<Packages, Double> packagesBasePrice = new TableColumn();
    @FXML
    private TableColumn<Packages, Double> packagesAgencyCommission = new TableColumn();
    @FXML
    private TableColumn<Packages, String> packagesDescription = new TableColumn();
    
    //table for offered products
    @FXML private TableView<ProductSupplier> tvPSAvailable = new TableView<ProductSupplier>();
    @FXML private TableColumn<ProductSupplier, String> supplierName = new TableColumn();
    
    //table for products owned in the package
    @FXML private TableView<ProductSupplier> tvPSOwned = new TableView<ProductSupplier>();
    @FXML private TableColumn<ProductSupplier, String> productOwned = new TableColumn();
    @FXML private TableColumn<ProductSupplier, String> supplierOwned = new TableColumn();
    
    /*
     * Selects start and end dates - Kevin.
     */
    @FXML private DatePicker dp_start = new DatePicker();
    @FXML private DatePicker dp_end = new DatePicker();
    
    /*
     * changePackList - listener for table
     * addSupplier - when supplier is added to package
     * remove - listener when supplier is removed from package
     */
    private ChangeListener<Object> changePackList;
    private ChangeListener<Object> addSupplier;
    private ChangeListener<Object> removeSupplier;
    
    ObservableList<Product> psProducts;
    
    
    //selescts ONLY the names from the observable list
public static ObservableList<String> ccbReturn(ObservableList<Product> list){
	
		ObservableList<String> namesOnly = FXCollections.observableArrayList();	
		for(Product prod : list) {
			
			namesOnly.add(prod.getProdName());
		}
		return namesOnly;
	}

public static ObservableList<Integer> ccbValueReturn(ObservableList<Product> list){
	
	ObservableList<Integer> numbersOnly = FXCollections.observableArrayList();	
	for(Product prod : list) {
		
		numbersOnly.add(prod.getProdId());
	}
	return numbersOnly;
	
}
 //access addPackage
@FXML private void TabAddPack(ActionEvent event) {
	
	Parent root;
	try {
		root = FXMLLoader.load(getClass().getResource("AddPackage.fxml"));
		Stage stage = new Stage();
		stage.setTitle("Add Package");
		stage.setScene(new Scene(root));
		
		Window existingWindow = ((Node) event.getSource()).getScene().getWindow();
		
		stage.initModality(Modality.APPLICATION_MODAL);
		
		stage.initOwner(existingWindow);
		
		stage.setOnCloseRequest(new EventHandler<WindowEvent>()
		{

			public void handle(WindowEvent event) {
				// TODO Auto-generated method stub
				windowClose();
			}
			
		});
		
		stage.show();
	}
	catch (IOException e) {
		e.printStackTrace();
	}
}

    
    
    //access Supplier Scene
    @FXML 
    private void TabSupplierAction(ActionEvent event) {
    	
    	System.out.println("button pressed");
    	
    	Parent root;
    	try {
    		root = FXMLLoader.load(getClass().getResource("SupplierPage.fxml"));
    		Stage stage = new Stage();
    		stage.setTitle("Suppliers");
    		stage.setScene(new Scene(root));
    		stage.getIcons().add(new Image(getClass().getResource("icon.png").toExternalForm()));
    		
    		Window existingWindow = ((Node) event.getSource()).getScene().getWindow();
    		
    		//make it modal
    		stage.initModality(Modality.APPLICATION_MODAL);
    		//make its owner the existing window
    		stage.initOwner(existingWindow);
    		
    		stage.setOnCloseRequest(new EventHandler<WindowEvent>()
    		{

    			public void handle(WindowEvent event) {
    				// TODO Auto-generated method stub
    				windowClose();
    			}
    			
    		});
    		
    		stage.show();
    		
    	}
    	catch (IOException e) {
    		e.printStackTrace();
    	}
    } 
    
    //clears entry boxes
    @FXML private void Newbutton(ActionEvent event)
    {
    txtPackages.setText("");
    txtStartDate.setText("");
    txtEndDate.setText("");
    txtBasePrice.setText("");
    txtAgencyCommision.setText("");
    txtDescription.setText("");
    btnNew.setText("Cancel");
    
    
    }
    
    //for the add new
    @FXML private void AddButton(ActionEvent event)
    {
    	
    }
    
    //products products tab
    @FXML 
    private void TabProducts(ActionEvent event) {
    	
    	System.out.println("Products button pressed");
    	
    	Parent root;
    	
    	try {
    		root = FXMLLoader.load(getClass().getResource("ProductsPage.fxml"));
    		Stage stage = new Stage();
    		stage.setTitle("Products");
    		stage.setScene(new Scene(root));
    		stage.getIcons().add(new Image(getClass().getResource("icon.png").toExternalForm()));
    		
    		Window existingWindow = ((Node) event.getSource()).getScene().getWindow();
    		
    		//make it modal
    		stage.initModality(Modality.APPLICATION_MODAL);
    		//make its owner the existing window
    		stage.initOwner(existingWindow);
    		
    		stage.setOnCloseRequest(new EventHandler<WindowEvent>()
    		{

    			public void handle(WindowEvent event) {
    				windowClose();
    			}
    			
    		});
    		
    		stage.show();
    		
    	}
    	catch (IOException e) {
    		e.printStackTrace();
    	}
    }
  
	
	//Initializes program 
	@Override
	public void initialize(URL url, ResourceBundle rb) {
			
		//gets list of all packages
		packagesList = TravelXDB.GetAllPackages();
		
		//sets packaged column id to Packages property names
		packagesID.setCellValueFactory(new PropertyValueFactory<Packages, Integer>("PackageId"));
        packagesName.setCellValueFactory(new PropertyValueFactory<Packages, String>("PkgName"));
        packagesStartDate.setCellValueFactory(new PropertyValueFactory<Packages, String>("PkgStartDate"));
        packagesEndDate.setCellValueFactory(new PropertyValueFactory<Packages, String>("PkgEndDate"));
        packagesBasePrice.setCellValueFactory(new PropertyValueFactory<Packages, Double>("PkgBasePrice"));
        packagesAgencyCommission.setCellValueFactory(new PropertyValueFactory<Packages, Double>("PkgAgencyCommision"));
        packagesDescription.setCellValueFactory(new PropertyValueFactory<Packages, String>("PkgDesc"));
        
        //owned products and suppliers
        productOwned.setCellValueFactory(new PropertyValueFactory<ProductSupplier, String>("ProdName"));
		supplierOwned.setCellValueFactory(new PropertyValueFactory<ProductSupplier, String>("SuppName"));
		
		//available Suppliers based on Products
		supplierName.setCellValueFactory(new PropertyValueFactory<ProductSupplier, String>("SuppName"));
        
        //sets packages table to the packages list
        tvPackages.setItems(packagesList);
        
        //sets comboBox
      //List for combobox Products
    	psProducts = TravelXDB.GetAllProducts();
    	cbbProducts.setItems(ccbReturn(psProducts));

		System.out.println(Packselected);  
        
		//Listens to table selection change to update the text fields based on the package seleected 
	  changePackList = new ChangeListener() {
		    
		  	@Override
			public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {

		  		DecimalFormat df = new DecimalFormat("#.00");
				df.setMaximumFractionDigits(2);
		  		
		  		//sets package object to selected row
				 Packselected = tvPackages.getSelectionModel().getSelectedItem();
				
				 //sets selectable packages to 
				 txtPackages.setText(Packselected.getPkgName());
				 txtBasePrice.setText(String.valueOf(df.format(Packselected.getPkgBasePrice())));
				 txtAgencyCommision.setText(String.valueOf(df.format(Packselected.getPkgAgencyCommision())));
				 txtDescription.setText(Packselected.getPkgDesc());
				 
				 DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				 
				 dpStartDate.setValue(LocalDate.parse(Packselected.getPkgStartDate(), format));
				 dpEndDate.setValue(LocalDate.parse(Packselected.getPkgEndDate(), format));
				 
				 psOwnedList = TravelXDB.getPSNamed(Packselected.getPackgeId());
				 tvPSOwned.setItems(psOwnedList);
				 
				 
				// Packages packSelected = tvPackages.getSelectionModel().getSelectedItem();
			 
				/*
				 * DatePicker for start and end date
				 */
				String start = Packselected.getPkgStartDate();
				dp_start.setValue(LocalDate.of(Integer.parseInt(start.substring(0, 4)), Integer.parseInt(start.substring(5, 7)), Integer.parseInt(start.substring(8, 10))));
				
				String end = Packselected.getPkgEndDate();
				dp_end.setValue(LocalDate.of(Integer.parseInt(end.substring(0, 4)), Integer.parseInt(end.substring(5, 7)), Integer.parseInt(end.substring(8, 10))));
				
				tvPSAvailable.getSelectionModel().selectFirst();
		    	tvPSOwned.getSelectionModel().selectFirst();
		    	System.out.println(Packselected.getPackgeId());
		    	
		    	psOwnedList = TravelXDB.getPSNamed(Packselected.getPackgeId());
				  tvPSOwned.setItems(psOwnedList);
				  
				  psAvailableList = TravelXDB.getOffProdSupply(cbbProducts.getSelectionModel().getSelectedItem(), Packselected.getPackgeId());
				  tvPSAvailable.setItems(psAvailableList);
		  	}
	     };
	     tvPackages.getSelectionModel().selectedItemProperty().addListener(changePackList);
	  
	  //products and suppliers assigned to the packages
	  addSupplier = new ChangeListener() {

		@Override
		public void changed(ObservableValue arg0, Object arg1, Object arg2) {
		
			psOwnSelected = tvPSOwned.getSelectionModel().getSelectedItem();
			
			
		}
			
		};
		tvPSOwned.getSelectionModel().selectedItemProperty().addListener(addSupplier);
		
	  //products and suppliers available to the packages
		removeSupplier = new ChangeListener() {

		@Override
		public void changed(ObservableValue arg0, Object arg1, Object arg2) {
			// TODO Auto-generated method stub
			
			 psSelected = tvPSAvailable.getSelectionModel().getSelectedItem();
			
			//psAvailableList = TravelXDB.getOffProdSupply(ProdName, Packageid)
			
		}
			
		};
		tvPSAvailable.getSelectionModel().selectedItemProperty().addListener(removeSupplier);
	  
	  
	  /*
	   * Set focus to the first item in the package table
	   */
	  tvPackages.requestFocus();
	  tvPackages.getSelectionModel().select(0);
	  tvPackages.getFocusModel().focus(0);
	  
	  /*
	   * This repeats code from Product_change function below but quickest way to initialize and setup combobox and PSAvailable tableview.
	   */
		  cbbProducts.getSelectionModel().selectFirst();
		  psAvailableList = TravelXDB.getOffProdSupply(cbbProducts.getSelectionModel().getSelectedItem(), Packselected.getPackgeId());
	  	tvPSAvailable.setItems(psAvailableList);
	  
	  	tvPSAvailable.getSelectionModel().selectFirst();
  		tvPSOwned.getSelectionModel().selectFirst();
	  

	}
	//change on the ComboBox
	  @FXML void Product_change(ActionEvent event)
	  {
		String prodSelected = cbbProducts.getSelectionModel().getSelectedItem();
		
		psAvailableList = TravelXDB.getOffProdSupply(prodSelected, Packselected.getPackgeId());
		//System.out.println(prodSelected + ' ' + Packselected.getPackgeId() + ' ' + psSelected.getProdSupplier() );
		tvPSAvailable.setItems(psAvailableList);
		tvPSAvailable.getSelectionModel().selectFirst();
	//	psOwnedList.getSelectionModel().selectFirst();
	//	psAvailableList.getSelectionModel().selectFirst();
		
	  }
	  
	  //moves available products to the the Package
	  @FXML void move_left(ActionEvent event)
	  {
		  TravelXDB.Addps(psSelected.getProdSupplier(), Packselected.getPackgeId());
		  
		  psOwnedList = TravelXDB.getPSNamed(Packselected.getPackgeId());
		  tvPSOwned.setItems(psOwnedList);
		  
		  psAvailableList = TravelXDB.getOffProdSupply(cbbProducts.getSelectionModel().getSelectedItem(), Packselected.getPackgeId());
		  tvPSAvailable.setItems(psAvailableList);
	  }
	  
	  @FXML void move_right(ActionEvent event)
	  {
		  TravelXDB.Deleteps(psOwnSelected.getProdSupplier(), Packselected.getPackgeId());
		//  psOwnedList.getSelectionModel().selectFirst();
		//  psAvailableList.getSelectionModel().selectFirst();
		  
		  psOwnedList = TravelXDB.getPSNamed(Packselected.getPackgeId());
		  tvPSOwned.setItems(psOwnedList);
		  
		  psAvailableList = TravelXDB.getOffProdSupply(cbbProducts.getSelectionModel().getSelectedItem(), Packselected.getPackgeId());
		  tvPSAvailable.setItems(psAvailableList);
	  }
	  
	  
	 
	  
	  //deletes Package
	  @FXML void btn_delete(ActionEvent event)
	  {
		 
		  TravelXDB.DeletePackage(Packselected.getPackgeId());
		  System.out.println("packaged deleted");
		  
		  updatingPackages();
	  }
	  
	  //updates Package
	  @FXML void btn_update(ActionEvent event)
	  {
		 try{
		  	String packName = txtPackages.getText();
			LocalDate startdate = dpStartDate.getValue();
			LocalDate enddate = dpEndDate.getValue();
			Date start = Date.valueOf(startdate);
			Date end = Date.valueOf(enddate);
			int packageid = Packselected.getPackgeId();
			String description = txtDescription.getText();
			double basePrice = Double.parseDouble(txtBasePrice.getText());
			double commission = Double.parseDouble(txtAgencyCommision.getText());
			
			if(packName.length() > 0 && description.length() > 0 )
			{
			TravelXDB.UpdatePackage(Packselected.getPackgeId(), packName, start, end, description, basePrice, commission);
			System.out.println("successfully updated Package");
			}
			else
			{
				JOptionPane.showConfirmDialog(null, "You need a Package name and description","Error", JOptionPane.CANCEL_OPTION);
			}
			}
					
			catch (NumberFormatException e)
			{
				JOptionPane.showConfirmDialog(null, "Please enter numbers for Base Price and Commission","Error", JOptionPane.CANCEL_OPTION);
			}
			
			
			
			updatingPackages();
	  }
	  
	  /*
	   * for when TableView Packages needs an update.
	   */
	  private void updatingPackages ()
	  {
		  tvPackages.getSelectionModel().selectedItemProperty().removeListener(changePackList);
		  packagesList = TravelXDB.GetAllPackages();
		  tvPackages.setItems(packagesList);
		  tvPackages.getSelectionModel().selectedItemProperty().addListener(changePackList);
		  
		  tvPackages.getSelectionModel().selectFirst();
	  }
	  
	  /*
	   * updating all tables on PackagesController when any of the windows are closed
	   */
	  private void windowClose()
	  {
		  	updatingPackages();
			tvPackages.getSelectionModel().selectFirst();
			
			psProducts = TravelXDB.GetAllProducts();
	    	cbbProducts.setItems(ccbReturn(psProducts));
			
			psOwnedList = TravelXDB.getPSNamed(Packselected.getPackgeId());
			tvPSOwned.setItems(psOwnedList);
			tvPSOwned.getSelectionModel().selectFirst();
			
			psAvailableList = TravelXDB.getOffProdSupply(cbbProducts.getSelectionModel().getSelectedItem(), Packselected.getPackgeId());
			tvPSAvailable.setItems(psAvailableList);
			tvPSOwned.getSelectionModel().selectFirst();
	  }
	  
	  
}

 