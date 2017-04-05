package threaded3;


import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
//import day7exercise.Agent;
//import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Labeled;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class PackagesController implements Initializable {

	//declare list of Packages
	ObservableList<Packages> packagesList;
	
	//tabs
	@FXML TabPane tabPackages;
	@FXML TabPane tabSupplier;
	@FXML TabPane tabProducts;
	
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
	
	//button
	@FXML Button btnTabSupplier;
	@FXML Button btnTabProducts;
	
	
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
    		
    		Window existingWindow = ((Node) event.getSource()).getScene().getWindow();
    		
    		//make it modal
    		stage.initModality(Modality.APPLICATION_MODAL);
    		//make its owner the existing window
    		stage.initOwner(existingWindow);
    		
    		stage.show();
    		
    	}
    	catch (IOException e) {
    		e.printStackTrace();
    	}
    }
    
    @FXML 
    private void TabProducts(ActionEvent event) {
    	
    	System.out.println("Products button pressed");
    	
    	Parent root;
    	
    	try {
    		root = FXMLLoader.load(getClass().getResource("ProductsPage.fxml"));
    		Stage stage = new Stage();
    		stage.setTitle("Suppliers");
    		stage.setScene(new Scene(root));
    		
    		Window existingWindow = ((Node) event.getSource()).getScene().getWindow();
    		
    		//make it modal
    		stage.initModality(Modality.APPLICATION_MODAL);
    		//make its owner the existing window
    		stage.initOwner(existingWindow);
    		
    		stage.show();
    		
    	}
    	catch (IOException e) {
    		e.printStackTrace();
    	}
    }
  
	
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO Auto-generated method stub
		System.out.println("Instance Initialized");
		
		packagesList = TravelXDB.GetAllPackages();
		
		
	
		packagesID.setCellValueFactory(new PropertyValueFactory<Packages, Integer>("PackageId"));
        packagesName.setCellValueFactory(new PropertyValueFactory<Packages, String>("PkgName"));
        packagesStartDate.setCellValueFactory(new PropertyValueFactory<Packages, String>("PkgStartDate"));
        packagesEndDate.setCellValueFactory(new PropertyValueFactory<Packages, String>("PkgEndDate"));
        packagesBasePrice.setCellValueFactory(new PropertyValueFactory<Packages, Double>("PkgBasePrice"));
        packagesAgencyCommission.setCellValueFactory(new PropertyValueFactory<Packages, Double>("PkgAgencyCommision"));
        packagesDescription.setCellValueFactory(new PropertyValueFactory<Packages, String>("PkgDesc"));
        
        System.out.println(packagesList);
        tvPackages.setItems(packagesList);
        
        Packages selected = tvPackages.getSelectionModel().getSelectedItem();
		System.out.println(selected);
		
		//tab change listener
	/*	tabSupplier.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
		    @Override
		    public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) {
		    	System.out.println("Supplier Tab Selected");
		    }
		}); */
		
		
		
		
        
        
		//listener selection change
	  tvPackages.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
		    
			@Override
			public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
				// TODO Auto-generated method stub
				Packages selected = tvPackages.getSelectionModel().getSelectedItem();
				System.out.println(selected);
				
				DecimalFormat df = new DecimalFormat("#.00");
				df.setMaximumFractionDigits(2);
				
				 txtPackages.setText(selected.getPkgName());
				 txtStartDate.setText(selected.getPkgStartDate());
				 txtEndDate.setText(selected.getPkgEndDate());
				 txtBasePrice.setText(String.valueOf(df.format(selected.getPkgBasePrice())));
				 txtAgencyCommision.setText(String.valueOf(df.format(selected.getPkgAgencyCommision())));
				 txtDescription.setText(selected.getPkgDesc());
				
				
			}
	     });
		
	// SupplierController.sudo_initialize();
	
	}
}

 