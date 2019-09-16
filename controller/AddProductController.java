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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;
import oa_software_1.OA_Software_1;

/**
 * FXML Controller class
 *
 * @author Nicholas
 */
public class AddProductController implements Initializable {
    
    Stage stage;
    Parent scene;
    ObservableList<Part> associated = FXCollections.observableArrayList();

    @FXML
    private TextField idTextField;
    
    @FXML
    private TextField nameTextField;
    
    @FXML
    private TextField invTextField;
    
    @FXML
    private TextField priceTextField;
    
    @FXML
    private TextField maxTextField;
    
    @FXML
    private TextField minTextField;
    
    @FXML
    private Button searchBtn;
    
    @FXML
    private TextField searchTextField;
    
    @FXML
    private TableView<Part> allPartsTableView;
    
    @FXML
    private TableColumn<Part, Integer> allPartsIdCol;
    
    @FXML
    private TableColumn<Part, String> allPartsNameCol;
    
    @FXML
    private TableColumn<Part, Integer> allPartsInvCol;
    
    @FXML
    private TableColumn<Part, Double> allPartsPriceCol;
    
    @FXML
    private Button addPartBtn;
    
    @FXML
    private TableView<Part> associatedPartsTableView;
    
    @FXML
    private TableColumn<Part, Integer> associatedPartsIdCol;
    
    @FXML
    private TableColumn<Part, String> associatedPartsNameCol;
    
    @FXML
    private TableColumn<Part, Integer> associatedPartsInvCol;
    
    @FXML
    private TableColumn<Part, Double> associatedPartsPriceCol;
    
    @FXML
    private Button deleteBtn;
    
    @FXML
    private Button saveBtn;
    
    @FXML
    private Button cancelBtn;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        allPartsTableView.setItems(Inventory.getAllParts());
        allPartsIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        allPartsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        allPartsInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        allPartsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        associatedPartsTableView.setItems(associated);
        associatedPartsIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartsInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
    
    @FXML
    void searchParts(ActionEvent event) {
        String text = searchTextField.getText();
        try {
            int id = Integer.parseInt(text);
            ObservableList<Part> foundPart = FXCollections.observableArrayList();
            foundPart.add(Inventory.lookupPart(id));
            allPartsTableView.setItems(foundPart);
        }
        catch(NumberFormatException e) {
            allPartsTableView.setItems(Inventory.lookupPart(text));
        }
        catch(NullPointerException n){
            allPartsTableView.setItems(Inventory.getAllParts());
        }
    }
    
     @FXML
    void onActionAddAssociatedPart(ActionEvent event) {
        associated.add(allPartsTableView.getSelectionModel().getSelectedItem());
        associatedPartsTableView.setItems(associated);
    }

    @FXML
    void onActionDeleteAssociatedPart(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Are you sure you want to delete this part?");
        Optional<ButtonType> result = alert.showAndWait();
        
        if(result.isPresent() && result.get() == ButtonType.OK){
            associated.remove(associatedPartsTableView.getSelectionModel().getSelectedItem());
        }
    }

    @FXML
    void saveChanges(ActionEvent event) throws IOException {
        String name = nameTextField.getText();
        double price = Double.parseDouble(priceTextField.getText());
        int inv = Integer.parseInt(invTextField.getText());
        int max = Integer.parseInt(maxTextField.getText());
        int min = Integer.parseInt(minTextField.getText());
        if(max < min){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Max must be greater than min");
            alert.showAndWait();
        }
        else {
            Product result = new Product(OA_Software_1.productIdCounter++, name, price, inv, min, max);
            for(Part part : associated) {
                result.addAssociatedPart(part);
            }
            Inventory.addProduct(result);

            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }
    
    @FXML
    void returnToMain(ActionEvent event) throws IOException {        
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
