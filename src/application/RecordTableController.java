package application;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.imageio.ImageIO;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

public class RecordTableController extends Database{

    @FXML
    private TableColumn<Data, String> col_date;

    @FXML
    private TableColumn<Data, String> col_temperature;

	@FXML
    private TableColumn<Data,Image> col_photo;
	
    //TABLE VIEW AND DATA
    private ObservableList <Data> list = FXCollections.observableArrayList();
 
    @FXML
    private TableView<Data> table_view;

    @FXML
    private TableColumn<Data, String> col_attendance;
    
    @FXML
    private Button search;
    
    @FXML
    private Button view;
    
    @FXML
    private TextField filterField;
    
    public void populateTableView() {

    	try {
    		Connection connection = DriverManager.getConnection(DATABASE_URL,DATABASE_USERNAME,DATABASE_PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(LOAD_QUERY);
            ResultSet rs = preparedStatement.executeQuery();
        	
            while(rs.next()) {
            	Blob blob = rs.getBlob(4);
            	byte[] b = blob.getBytes(1, (int)blob.length());
            	Image image = new Image(new ByteArrayInputStream(b));
            	Data data = new Data(rs.getString(1),rs.getString(2),rs.getString(3),image);
            	list.add(data);
            }
            
            col_date.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
        	col_temperature.setCellValueFactory(cellData -> cellData.getValue().temperatureProperty());
        	col_attendance.setCellValueFactory(cellData -> cellData.getValue().codeProperty());
        	col_photo.setCellFactory(col-> new TableCell<Data,Image>(){
        		private final ImageView imageView = new ImageView();
        		{
        			// set size of ImageView
        	        imageView.setFitHeight(50);
        	        imageView.setFitWidth(80);
        	        imageView.setPreserveRatio(true);

        	        // display ImageView in cell
        	        setGraphic(imageView);
        		}
        		
        		 @Override
        		    protected void updateItem(Image item, boolean empty) {
        		        super.updateItem(item, empty);
        		        imageView.setImage(item);
        		    }
        	});
        	col_photo.setCellValueFactory(cellData -> cellData.getValue().imageProperty());
        	table_view.setItems(list);
            
           
    	}catch(SQLException e) {
    		System.err.println("Error" + e);
    	}
    	 
    }
    

	public void filterTableView() {
    	FilteredList<Data>filteredData = new FilteredList<>(list,p->true);
    	
    	filterField.textProperty().addListener((observable,oldValue,newValue)->{
    		filteredData.setPredicate(data->{
    			if(newValue==null||newValue.isEmpty()) {
    				return true;
    			}
    			
    			String filter = String.valueOf(newValue);
    			if(data.getDate().contains(filter)) {
    				return true;
    			}
    			else if (data.getTemperature().contains(filter)) {
    				return true;
    			}
    			else if(data.getCode().contains(filter)) {
    				return true;
    			}
    			return false;
    		});
    	});
    	
    	SortedList<Data> sortedData = new SortedList<>(filteredData);
    	sortedData.comparatorProperty().bind(table_view.comparatorProperty());
    	table_view.setItems(sortedData);
    }
    
    @FXML
    public void onViewClicked(ActionEvent event) throws Exception{
    	try {
    		populateTableView();
    	}catch(Exception e) {
    		System.out.println(e.getMessage());
    	}
    }

    @FXML
    public void onSearchClicked(ActionEvent event) throws Exception {
    	try {
    		filterTableView();
    	}catch(Exception e) {
    		System.out.println(e.getMessage());
    	}
    }


}
