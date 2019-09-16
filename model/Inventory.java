package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    public static void addPart(Part part) {
        allParts.add(part);
    }

    public static void addProduct(Product product) {
        allProducts.add(product);
    }

    public static Part lookupPart(int id) {
        Part result = null;
        for(Part part : allParts) {
            if(part.getId() == id){
                result = part;
            }
        }
        return result;
    }

    public static Product lookupProduct(int id) {
        Product result = null;
        for(Product product : allProducts){
            if(product.getId() == id) {
                result = product;
            }
        }
        return result;
    }

    public static ObservableList<Part> lookupPart(String name) {
        ObservableList<Part> result = FXCollections.observableArrayList();
        for(Part part : allParts) {
            if(part.getName().contains(name)) {
                result.add(part);
            }
        }
        return result;
    }

    public static ObservableList<Product> lookupProduct(String name) {
        ObservableList<Product> result = FXCollections.observableArrayList();
        for(Product product : allProducts) {
            if(product.getName().contains(name)) {
                result.add(product);
            }
        }
        return result;
    }

    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    public static void updateProduct(int index, Product selectedProduct) {
        allProducts.set(index, selectedProduct);
    }

    public static void deletePart(Part selectedPart) {
        allParts.remove(selectedPart);
    }

    public static void deleteProduct(Product selectedProduct) {
        allProducts.remove(selectedProduct);
    }

    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}