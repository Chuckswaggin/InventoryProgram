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

/**
 * FXML Controller class
 *
 * @author Nicholas
 */
public class MainController implements Initializable {
    
    Stage stage;
    Parent scene;

    @FXML
    private Button partsSearchBtn;
    
    @FXML
    private TextField partsSearchTxt;
    
    @FXML
    private Button addPartBtn;
    
    @FXML
    private Button modPartBtn;
    
    @FXML
    private Button delPartBtn;
    
    @FXML
    private TableView<Part> partsTblVw;
    
    @FXML
    private TableColumn<Part, Integer> partIdCol;
    
    @FXML
    private TableColumn<Part, String> partNameCol;
    
    @FXML
    private TableColumn<Part, Integer> partInvCol;
    
    @FXML
    private TableColumn<Part, Double> partPriceCol;
    
    @FXML
    private Button productsSearchBtn;
    
    @FXML
    private TextField productsSearchTxt;
    
    @FXML
    private Button addProductBtn;
    
    @FXML
    private Button modProductBtn;
    
    @FXML
    private Button delProductBtn;
    
    @FXML
    private TableView<Product> productsTblVw;
    
    @FXML
    private TableColumn<Product, Integer> productIdCol;
    
    @FXML
    private TableColumn<Product, String> productNameCol;
    
    @FXML
    private TableColumn<Product, Integer> productInvCol;
    
    @FXML
    private TableColumn<Product, Double> productPriceCol;
    
    @FXML
    private Button exitBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        partsTblVw.setItems(Inventory.getAllParts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        productsTblVw.setItems(Inventory.getAllProducts());
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }    

    @FXML
    private void searchParts(ActionEvent event) {
        String text = partsSearchTxt.getText();
        try {
            int id = Integer.parseInt(text);
            ObservableList<Part> foundPart = FXCollections.observableArrayList();
            foundPart.add(Inventory.lookupPart(id));
            partsTblVw.setItems(foundPart);
        }
        catch(NumberFormatException e) {
            partsTblVw.setItems(Inventory.lookupPart(text));
        }
        catch(NullPointerException n){
            partsTblVw.setItems(Inventory.getAllParts());
        }
    }

    @FXML
    private void switchToAddPart(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddPart.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    private void switchToModifyPart(ActionEvent event) throws IOException {
        
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/ModifyPart.fxml"));
        loader.load();
        
        ModifyPartController mpController = loader.getController();
        mpController.setPart(partsTblVw.getSelectionModel().getSelectedItem());
        
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    private void deletePartFromTableView(ActionEvent event) {
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Are you sure you want to delete this part?");
        Optional<ButtonType> result = alert.showAndWait();
        
        if(result.isPresent() && result.get() == ButtonType.OK){
            Inventory.deletePart(partsTblVw.getSelectionModel().getSelectedItem());
        }
    }

    @FXML
    private void searchProducts(ActionEvent event) {
        String text = productsSearchTxt.getText();
        try {
            int id = Integer.parseInt(text);
            ObservableList<Product> foundProduct = FXCollections.observableArrayList();
            foundProduct.add(Inventory.lookupProduct(id));
            productsTblVw.setItems(foundProduct);
        }
        catch(NumberFormatException e) {
            productsTblVw.setItems(Inventory.lookupProduct(text));
        }
        catch(NullPointerException n){
            productsTblVw.setItems(Inventory.getAllProducts());
        }
    }

    @FXML
    private void switchToAddProduct(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddProduct.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    private void switchToModifyProduct(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/ModifyProduct.fxml"));
        loader.load();
        
        ModifyProductController mpController = loader.getController();
        mpController.setProduct(productsTblVw.getSelectionModel().getSelectedItem());
        
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    private void deleteProductFromTableView(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Are you sure you want to delete this product?");
        Optional<ButtonType> result = alert.showAndWait();
        
        if(result.isPresent() && result.get() == ButtonType.OK){
            Inventory.deleteProduct(productsTblVw.getSelectionModel().getSelectedItem());
        }
    }
    
    @FXML
    void exitProgram(ActionEvent event) {
        System.exit(0);
    }
}
