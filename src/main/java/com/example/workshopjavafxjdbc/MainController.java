package com.example.workshopjavafxjdbc;

import com.example.workshopjavafxjdbc.utils.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
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
        loadView("DepartmentList.fxml");
    }

    @FXML
    public void onMenuItemAboutAction(){
        loadView("About.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    private synchronized void loadView(String absoluteName){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
            VBox newVBox = loader.load();

            Scene mainScene = Main.getMainScene();
            VBox mainVBOX = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();

            Node mainMenu = mainVBOX.getChildren().get(0);
            mainVBOX.getChildren().clear();
            mainVBOX.getChildren().add(mainMenu);
            mainVBOX.getChildren().addAll(newVBox.getChildren());

        } catch (IOException e){
            Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), Alert.AlertType.ERROR);
        }
    }
}