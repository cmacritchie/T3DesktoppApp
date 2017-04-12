package threaded3;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JOptionPane;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

/**
 * 
 * @author  Craig MacRitchie and Kevin Yan
 * 
 * New Scene for adding a new Package.
 * 
 * closing will refresh all the tables in the PackagesController scene.
 *
 */
public class AddPackage implements Initializable 
{

	//text input fields
	@FXML TextField txtPackage;
	@FXML TextArea txtDescription;
	@FXML TextField txtBasePrice;
	@FXML TextField txtCommission;
	
	//buttons
	@FXML Button btnAdd;
	
	//date time picker
	@FXML DatePicker dpStartDate;
	@FXML DatePicker dpEndDate;
	
	@FXML Label success = new Label();
	
	Timer timer = new Timer();
	TimerTask start_task = new TimerTask()
	{
	        public void run()
	        {
	            success.setVisible(false);      
	        }

	};
	
		
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		dpStartDate.setValue(LocalDate.now());
		dpEndDate.setValue(LocalDate.now().plusDays(7));

		LocalDate minDate = LocalDate.now();
        LocalDate maxDate = minDate.plusYears(2);
        
        dpStartDate.setDayCellFactory((p) -> new DateCell() {
            public void updateItem(LocalDate ld, boolean bln) {
                super.updateItem(ld, bln);
                setDisable(ld.isBefore(minDate) || ld.isAfter(maxDate));
            }
        });
		
		dpEndDate.setDayCellFactory((p) -> new DateCell() {
            public void updateItem(LocalDate ld, boolean bln) {
                super.updateItem(ld, bln);
                setDisable(ld.isBefore(minDate) || ld.isAfter(maxDate));
            }
        });
		
		dpStartDate.valueProperty().addListener(new ChangeListener<LocalDate>() {


			@Override
			public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue,LocalDate newValue) 
			{
				dpEndDate.setDayCellFactory((p) -> new DateCell() {
		            public void updateItem(LocalDate ld, boolean bln) {
		                super.updateItem(ld, bln);
		                setDisable(ld.isBefore(dpStartDate.getValue()) || ld.isAfter(maxDate));
		            }
		        });
			}
		});
	}
	private String validator()
	{
		String errorwarning ="";
		if(txtPackage.getText().length() < 1)
		{
			errorwarning += "Package needs a name";
		}
		if(txtDescription.getText().length() < 1);
		{
			errorwarning += "Package needs a description";
		}
		//if(txtBasePrice.getText())
		return "Hello Motto";
	}
	
	@FXML void addPackage(ActionEvent event)
	{
		
		try
		{
			
		String packName = txtPackage.getText();
		String description = txtDescription.getText();
		LocalDate startdate = dpStartDate.getValue();
		LocalDate enddate = dpEndDate.getValue();
		Date start = Date.valueOf(startdate);
		Date end = Date.valueOf(enddate);
		double basePrice = Double.parseDouble(txtBasePrice.getText());
		double commission = Double.parseDouble(txtCommission.getText());
		
		if(packName.length() > 0 && description.length() > 0 )
		{
			if(!enddate.isBefore(startdate))
			{
			TravelXDB.AddPackage(packName, start, end, description, basePrice, commission);
			System.out.println("successfully added Package");
			txtPackage.setText("");
			txtDescription.setText("");
			txtBasePrice.setText("");
			txtCommission.setText("");
			dpStartDate.setValue(LocalDate.now());
			dpEndDate.setValue(LocalDate.now().plusDays(7));
			
			timer.schedule(start_task, 6000);
			}
			else
			{
			JOptionPane.showConfirmDialog(null, "Package start and end date are conflicting","Error", JOptionPane.CANCEL_OPTION);
			}
			
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
		catch (SQLException e)
		{
			JOptionPane.showConfirmDialog(null, "SQL error","Error", JOptionPane.CANCEL_OPTION);
		}
		
		
		
			
	}
}
