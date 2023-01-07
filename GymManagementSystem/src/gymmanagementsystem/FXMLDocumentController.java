/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gymmanagementsystem;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 *
 * @author MarcoMan
 * Subscribe our YouTube Channel: https://www.youtube.com/@marcomanchannel
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private AnchorPane main_form;

    @FXML
    private AnchorPane sub_form;

    @FXML
    private Button sub_signupBtn;

    @FXML
    private Button sub_loginBtn;

    @FXML
    private Label edit_label;

    @FXML
    private AnchorPane signup_form;

    @FXML
    private TextField su_email;

    @FXML
    private TextField su_username;

    @FXML
    private PasswordField su_password;

    @FXML
    private Button su_signupBtn;

    @FXML
    private AnchorPane login_form;

    @FXML
    private TextField si_username;

    @FXML
    private PasswordField si_password;

    @FXML
    private Button si_loginBtn;

    @FXML
    private Button close;

    @FXML
    private FontAwesomeIcon close_icon;

//   DATABASE TOOLS 
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    public void login() {

        String sql = "SELECT * FROM admin WHERE username = ? and password = ?";

        connect = database.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, si_username.getText());
            prepare.setString(2, si_password.getText());
            result = prepare.executeQuery();

            Alert alert;

            if (si_username.getText().isEmpty() || si_password.getText().isEmpty()) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {
                if (result.next()) {

                    data.username = si_username.getText();

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Login!");
                    alert.showAndWait();

                    si_loginBtn.getScene().getWindow().hide();

                    // LINK YOUR DASHBOARD FORM 
                    Parent root = FXMLLoader.load(getClass().getResource("dashboard.fxml"));

                    Stage stage = new Stage();
                    Scene scene = new Scene(root);

                    stage.initStyle(StageStyle.TRANSPARENT);

                    stage.setScene(scene);
                    stage.show();

                } else {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Wrong Username/Password");
                    alert.showAndWait();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void signup() {

        String sql = "INSERT INTO admin (email, username, password) VALUES(?,?,?)";

        connect = database.connectDb();

        try {
            Alert alert;

            if (su_email.getText().isEmpty() || su_username.getText().isEmpty()
                    || su_password.getText().isEmpty()) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {
                if (su_password.getText().length() < 8) {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Invalid password :3");
                    alert.showAndWait();
                } else {
                    prepare = connect.prepareStatement(sql);
                    prepare.setString(1, su_email.getText());
                    prepare.setString(2, su_username.getText());
                    prepare.setString(3, su_password.getText());

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully create new account!");
                    alert.showAndWait();

                    prepare.executeUpdate();

                    su_email.setText("");
                    su_username.setText("");
                    su_password.setText("");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void signupSlider() {

        TranslateTransition slider1 = new TranslateTransition();
        slider1.setNode(sub_form);
        slider1.setToX(300);
        slider1.setDuration(Duration.seconds(.5));
        slider1.play();

        slider1.setOnFinished((ActionEvent event) -> {
            edit_label.setText("Login Account");

            sub_signupBtn.setVisible(false);
            sub_loginBtn.setVisible(true);

            close_icon.setFill(Color.valueOf("#fff"));
        });

    }

    public void loginSlider() {

        TranslateTransition slider1 = new TranslateTransition();
        slider1.setNode(sub_form);
        slider1.setToX(0);
        slider1.setDuration(Duration.seconds(.5));
        slider1.play();

        slider1.setOnFinished((ActionEvent event) -> {
            edit_label.setText("Create Account");

            sub_signupBtn.setVisible(true);
            sub_loginBtn.setVisible(false);
            close_icon.setFill(Color.valueOf("#000"));
        });

    }

    public void close() {
        javafx.application.Platform.exit();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
