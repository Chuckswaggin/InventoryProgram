/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import oa_software_1.OA_Software_1;

/**
 * FXML Controller class
 *
 * @author Nicholas
 */
public class AddPartController implements Initializable {
    
    Stage stage;
    Parent scene;

    @FXML
    private RadioButton inHouseRadioBtn;
    
    @FXML
    private ToggleGroup IHOSToggle;
    
    @FXML
    private RadioButton outsourcedRadioBtn;
    
    @FXML
    private Text compMachLabel;
    
    @FXML
    private TextField idTextField;
    
    @FXML
    private TextField nameTextField;
    
    @FXML
    private TextField invTextField;
    
    @FXML
    private TextField priceTextField;
    
    @FXML
    private TextField compMachTextField;
    
    @FXML
    private TextField maxTextField;
    
    @FXML
    private TextField minTextField;
    
    @FXML
    private Button addPartSaveBtn;
    
    @FXML
    private Button cancelBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void setCompNameLabel(ActionEvent event) {
        compMachLabel.setText("Company Name");
        compMachTextField.setPromptText("Comp Nm");
    }

    @FXML
    private void setMachIdLabel(ActionEvent event) {
        compMachLabel.setText("Machine ID");
        compMachTextField.setPromptText("Mach ID");
    }

    @FXML
    private void saveAddPartChanges(ActionEvent event) throws IOException {
        String name = nameTextField.getText();
        int inv = Integer.parseInt(invTextField.getText());
        double price = Double.parseDouble(priceTextField.getText());
        int max = Integer.parseInt(maxTextField.getText());
        int min = Integer.parseInt(minTextField.getText());
        if(max < min){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Max must be greater than min");
            alert.showAndWait();
        }
        else {
            if(inHouseRadioBtn.isSelected())
            {
                int machId = Integer.parseInt(compMachTextField.getText());
                Inventory.addPart(new InHouse(OA_Software_1.partIdCounter++, name, price, inv, min, max, machId));
            }
            else
            {
                String compName = compMachTextField.getText();
                Inventory.addPart(new Outsourced(OA_Software_1.partIdCounter++, name, price, inv, min, max, compName));  
            }

            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    @FXML
    private void switchToMain(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Any information entered will not be saved. Continue?");
        Optional<ButtonType> result = alert.showAndWait();
        
        if(result.isPresent() && result.get() == ButtonType.OK){ 
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }
    
}
