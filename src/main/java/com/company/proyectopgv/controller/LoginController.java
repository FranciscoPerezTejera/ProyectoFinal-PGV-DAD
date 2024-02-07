package com.company.proyectopgv.controller;

import com.company.proyectopgv.App;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;



public class LoginController implements Initializable {

    @FXML
    private PasswordField passwordField;

    private boolean isTextVisible = false;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        
    }

    @FXML
    private void goToPrimary(ActionEvent event) {

        try {
            App.setRoot("primary");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
