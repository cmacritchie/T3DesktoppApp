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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Labeled;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * 
 * @author Craig MacRitchie and Kevin Yan
 * 
 * CRUD operations on the Products table - add, update, delete.
 *
 */
public class ProductController implements Initializable {

	//declare list of Products
	ObservableList<Product> productList;
	
	//labels
	@FXML Labeled lblProduct;
	
	//textfield
	@FXML TextField txtProduct;
	//button
	@FXML Button btnAddProduct;
	@FXML Button btnDeleteProduct;
	@FXML Button btnUpdateProduct;
	
	//table and columns
	@FXML
	private TableView<Product> tvProducts = new TableView<Product>();
	@FXML 
	private TableColumn<Product, Integer> productID = new TableColumn();
	@FXML
	private TableColumn<Product, String> productName = new TableColumn();
	
		
	private ChangeListener<Object> productItemSelected;
		
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
		System.out.println("products Initialized");
		
		productList = TravelXDB.GetAllProducts();
		
		productID.setCellValueFactory(new PropertyValueFactory<Product, Integer>("ProdId"));
		productName.setCellValueFactory(new PropertyValueFactory<Product, String>("ProdName"));
		
		tvProducts.setItems(productList);
		//tvProducts.getSelectionModel().selectFirst();
		
		productItemSelected = new ChangeListener<Object>() {

			@Override
			public void changed(ObservableValue<?> observableValue, Object oldValue, Object newValue) {
				// TODO Auto-generated method stub
				//System.out.println("obVal: " + observableValue);
				//System.out.println("new" + newValue);
				
				//Product selected = tvProducts.getSelectionModel().getSelectedItem();
				//System.out.println("prod:" + selected);
				
				txtProduct.setText(((Product)newValue).getProdName());
				
			}
			
		};
		tvProducts.getSelectionModel().selectedItemProperty().addListener(productItemSelected);
			
		tvProducts.getSelectionModel().selectFirst();
	}

	@FXML void addNewProduct(ActionEvent event)
	{
		int clone = 0;
		String newProd = txtProduct.getText();
		for (Product prod : productList) {
		    if(prod.getProdName().equals(newProd))
		    {	
		    	clone++;
		    }   
		}
	
		//Product selected = tvProducts.getSelectionModel().getSelectedItem();
		if(validator() && clone ==0)
		{
		
		TravelXDB.AddProduct(newProd);
		
		updateTVProducts();
		
		tvProducts.getSelectionModel().selectLast();
		tvProducts.scrollTo(tvProducts.getItems().size()-1);
		}
		else {AlertNotify();}
	}
	
	@FXML void deleteProduct(ActionEvent event)
	{
		Product selected = tvProducts.getSelectionModel().getSelectedItem();
		
		TravelXDB.DeleteProduct(selected.getProdId());
		//getting an error here
		updateTVProducts();
		
		tvProducts.getSelectionModel().selectFirst();
		tvProducts.scrollTo(0);
	}
	@FXML void updateProduct(ActionEvent event)
	{
		int clone = 0;
		String updateProd = txtProduct.getText();
		for (Product prod : productList) {
		    if(prod.getProdName().equals(updateProd))
		    {
		    	clone++;
		    }    	
		}
				
		if(validator() && (clone == 1 || clone == 0))
		{
		
		Product selected = tvProducts.getSelectionModel().getSelectedItem();
		
		int index = tvProducts.getSelectionModel().getFocusedIndex();
		
		TravelXDB.UpdateProduct(selected.getProdId(), updateProd);
		
		updateTVProducts();
		
		
		tvProducts.getSelectionModel().select(index);
		tvProducts.scrollTo(index);
		}
		else
		{
			AlertNotify();
		}
	}
	
	private void AlertNotify()
	{
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText("Product Name");
		alert.setContentText("The product name cannot be left blank and must be unique!");

		alert.showAndWait();
	}
	
	//checks to make sure that the product name was not left blank
	private boolean validator()
	{
		String read = txtProduct.getText();
		
		if (read.length() > 0)
			return true;
		else
			return false;
			
		
	}
	
	private void updateTVProducts()
	{
		tvProducts.getSelectionModel().selectedItemProperty().removeListener(productItemSelected);
		tvProducts.setItems(TravelXDB.GetAllProducts());
		tvProducts.getSelectionModel().selectedItemProperty().addListener(productItemSelected);
	}
}
