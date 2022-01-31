package com.sipios.refactoring.model;

public class ShoppingCart {

    private ShoppingItem[] shoppingItems;
    private String         customerType;

    public ShoppingCart(ShoppingItem[] shoppingItemsArray, String customerType) {
        this.shoppingItems = shoppingItemsArray;
        this.customerType  = customerType;
    }

    public ShoppingCart() {
    }

    public ShoppingItem[] getItems() {
        return shoppingItems;
    }

    public void setItems(ShoppingItem[] shoppingItems) {
        this.shoppingItems = shoppingItems;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }
}
