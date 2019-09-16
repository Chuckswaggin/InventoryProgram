/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oa_software_1;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Product;

/**
 *
 * @author Nicholas
 */

public class OA_Software_1 extends Application {
    
    public static int partIdCounter = 1;
    public static int productIdCounter = 1;
            
    @Override
    public void start(Stage primaryStage) throws IOException {
        
        Parent scene = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
        
        primaryStage.setScene(new Scene(scene));
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        //int partIdCounter = 1;
        //int productIdCounter = 1;
        
        InHouse ih1 = new InHouse(partIdCounter++, "Pinion", 34.99, 100, 50, 150, 6);
        InHouse ih2 = new InHouse(partIdCounter++, "Worm Shaft", 49.99, 50, 20, 100, 2);
        Outsourced os1 = new Outsourced(partIdCounter++, "Bearing", 19.99, 1500, 750, 2000, "WSH");
        Outsourced os2 = new Outsourced(partIdCounter++, "Ingot", 25.00, 3000, 500, 4000, "Timkin");
        
        Product prod1 = new Product(productIdCounter++, "Rack Bar", 150.00, 100, 50, 200);
        prod1.addAssociatedPart(ih1);
        prod1.addAssociatedPart(os1);        
        Product prod2 = new Product(productIdCounter++, "Assembly", 200.00, 125, 100, 300);
        prod2.addAssociatedPart(ih2);
        prod2.addAssociatedPart(os2);
        
        Inventory.addPart(ih1);
        Inventory.addPart(ih2);
        Inventory.addPart(os1);
        Inventory.addPart(os2);
        Inventory.addProduct(prod1);
        Inventory.addProduct(prod2);
        
        
        launch(args);
    }
    
}
