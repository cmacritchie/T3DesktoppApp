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
	
		
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
		System.out.println("products Initialized");
		
		productList = TravelXDB.GetAllProducts();
		
		productID.setCellValueFactory(new PropertyValueFactory<Product, Integer>("ProdId"));
		productName.setCellValueFactory(new PropertyValueFactory<Product, String>("ProdName"));
		
		tvProducts.setItems(productList);
		//tvProducts.getSelectionModel().selectFirst();
		
		tvProducts.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {

			@Override
			public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
				// TODO Auto-generated method stub
				Product selected = tvProducts.getSelectionModel().getSelectedItem();
				System.out.println(selected);
				
				txtProduct.setText(selected.getProdName());
				
				
			}
			
		});
			
		
	}

	@FXML void addNewProduct(ActionEvent event)
	{
		//Product selected = tvProducts.getSelectionModel().getSelectedItem();
		
		String newProd = txtProduct.getText();
		TravelXDB.AddProduct(newProd);
		
		
		tvProducts.setItems(TravelXDB.GetAllProducts());
		tvProducts.getSelectionModel().selectFirst();
		
	}
	
	@FXML void deleteProduct(ActionEvent event)
	{
		Product selected = tvProducts.getSelectionModel().getSelectedItem();
		
		TravelXDB.DeleteProduct(selected.getProdId());
		//getting an error here
		tvProducts.setItems(TravelXDB.GetAllProducts());
		
	}
	@FXML void updateProduct(ActionEvent event)
	{
		String updateProd = txtProduct.getText();
		Product selected = tvProducts.getSelectionModel().getSelectedItem();
		
		TravelXDB.UpdateProduct(selected.getProdId(), updateProd);
		tvProducts.setItems(TravelXDB.GetAllProducts());
		
	}
}
