package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;

public class HomepageController extends RecordTableController{

	@FXML
	private Button btnBack;

	@FXML
	private Button upload;

	@FXML
	private Button SearchandView;

	@FXML
	void onBackClicked(ActionEvent event) {
		Stage Window2 = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Window2.show();
	}
	
	@FXML
	void onUploadClicked(ActionEvent event) {
		try {
			AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("DetailForm.fxml"));
			Scene scene3 = new Scene(root, 1200, 600);
			scene3.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Stage Window3 = new Stage();
			Window3.setTitle("By Chang Wan Ling Samantha (1923377)");
			Window3.initModality(Modality.APPLICATION_MODAL);
			Window3.setScene(scene3);
			Window3.show();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	@FXML
	void onSearchandViewClicked(ActionEvent event) {
		try {
			AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("RecordTable.fxml"));
			Scene scene4 = new Scene(root, 1200, 600);
			scene4.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Stage Window4 = new Stage();
			Window4.setTitle("By Chang Wan Ling Samantha (1923377)");
			Window4.initModality(Modality.APPLICATION_MODAL);
			Window4.setScene(scene4);
			Window4.show();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

}

