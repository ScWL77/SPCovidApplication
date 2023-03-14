package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File;
import java.net.MalformedURLException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

import java.io.IOException;
import java.sql.SQLException;
import javafx.scene.control.Alert.AlertType;

public class DetailFormController extends Database {

//	@FXML
//	private TextField date;

	@FXML
	private TextField code;

	@FXML
	private TextField temperature;

	@FXML
	private ImageView img_view;

	@FXML
	private TextField text_path;

	@FXML
	private Button Upload;

	@FXML
	private Button Choose;

	@FXML
	public void onuploadClicked(ActionEvent event) throws SQLException {

		Window owner = Upload.getScene().getWindow();

//		System.out.println(date.getText());
		System.out.println(temperature.getText());
		System.out.println(code.getText());

//		if (date.getText().isEmpty()) {
//			showAlert(Alert.AlertType.ERROR, owner, "Form Error!", "Please enter the date");
//			return;
//		}
		if (temperature.getText().isEmpty()) {
			showAlert(Alert.AlertType.ERROR, owner, "Form Error!", "Please enter your temperature");
			return;
		}

		if (code.getText().isEmpty()) {
			showAlert(Alert.AlertType.ERROR, owner, "Form Error!", "Please enter your attendance code");
			return;
		}

		if (text_path.getText().isEmpty()) {
			showAlert(Alert.AlertType.ERROR, owner, "Form Error!", "Please upload your seating photo");
		}

//		String Date = date.getText();
		String Temperature = temperature.getText();
		String Code = code.getText();
		String Path = text_path.getText();

		CheckCode db1 = new CheckCode();
		boolean flag = db1.checkcode(Code);
		if (!flag) {
			showAlert(Alert.AlertType.ERROR, owner, "Form Error!", "Please enter a valid attendance code!");
		} else {
			InsertInfo db2 = new InsertInfo();
			db2.insert(Temperature, Code, Path);

			try {
				AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("Homepage.fxml"));
				Scene scene5 = new Scene(root, 1200, 600);
				scene5.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				Stage Window5 = new Stage();
				Window5.initModality(Modality.APPLICATION_MODAL);
				Window5.setTitle("By Chang Wan Ling Samantha (1923377)");
				Window5.setScene(scene5);
				Window5.show();
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	@FXML
	public void onChooseClicked(ActionEvent event) throws MalformedURLException {
		JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		int returnValue = chooser.showOpenDialog(null);

		if (returnValue == JFileChooser.APPROVE_OPTION) {
			text_path.setText(chooser.getSelectedFile().getAbsolutePath());
			File file = new File(chooser.getSelectedFile().getAbsolutePath());
			String localURL = file.toURI().toURL().toString();
			img_view.setImage(new Image(localURL));
		}
	}

	public static void infoBox(String infoMessage, String headerText, String title) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setContentText(infoMessage);
		alert.setTitle(title);
		alert.setHeaderText(headerText);
		alert.showAndWait();
	}

	private static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.initOwner(owner);
		alert.show();
	}
}