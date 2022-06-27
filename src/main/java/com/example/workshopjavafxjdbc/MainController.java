package com.example.workshopjavafxjdbc;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private MenuItem menuItemSeller;

    @FXML
    private MenuItem menuItemDepartment;

    @FXML
    private MenuItem menuItemAbout;

    @FXML
    public void onMenuItemSellerAction(){
        System.out.println("action");
    }

    @FXML
    public void onMenuItemDepartmentAction(){
        System.out.println("action");
    }

    @FXML
    public void onMenuItemAboutAction(){
        System.out.println("action");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}