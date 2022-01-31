package com.sipios.refactoring.model;

public class ShoppingCart {

    private ShoppingItem[] shoppingItems;
    private String         userType;

    public ShoppingCart(ShoppingItem[] is, String t) {
        this.shoppingItems = is;
        this.userType      = t;
    }

    public ShoppingCart() {
    }

    public ShoppingItem[] getItems() {
        return shoppingItems;
    }

    public void setItems(ShoppingItem[] shoppingItems) {
        this.shoppingItems = shoppingItems;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
