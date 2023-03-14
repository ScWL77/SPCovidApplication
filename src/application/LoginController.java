package application;

import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public class LoginController{

	@FXML
	private TextField txtUser;

	@FXML
	private Button submitButton;

	@FXML
	private PasswordField txtPwd;

	@FXML
	public void onLoginClicked(ActionEvent event) throws SQLException {

		Window owner = submitButton.getScene().getWindow();

        System.out.println(txtUser.getText());
        System.out.println(txtPwd.getText());

        if (txtUser.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                "Please enter your username");
            return;
        }
        if (txtPwd.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                "Please enter a password");
            return;
        }

        String username = txtUser.getText();
        String password = txtPwd.getText();

        Validate jdbcDao = new Validate();
        boolean flag = jdbcDao.validate(username, password);

        if (!flag) {
            infoBox("Please enter correct username or password", null, "Failed");
        } else {
        	try {
				AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("Homepage.fxml"));
				Scene scene2 = new Scene(root, 1200, 600);
				scene2.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				Stage Window2 = new Stage();
				Window2.initModality(Modality.APPLICATION_MODAL);
				Window2.setTitle("By Chang Wan Ling Samantha (1923377)");
				Window2.setScene(scene2);
				Window2.show();
				
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
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