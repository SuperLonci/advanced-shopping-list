package de.lonci.domain;

import java.io.Serializable;
import java.util.List;

public class ShoppingList implements Serializable {
    String id;
    String name;
    List<ShoppingListItem> shoppingListItems;

    public ShoppingList(ShoppingListBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.shoppingListItems = builder.shoppingListItems;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<ShoppingListItem> getShoppingListItems() {
        return shoppingListItems;
    }

    public void addShoppingListItem(ShoppingListItem shoppingListItem){
        shoppingListItems.add(shoppingListItem);
    }

    public void addProductToShoppingListItem(Product product, Integer shoppingListItemNumber){
        shoppingListItems.get (shoppingListItemNumber).addProduct(product);
    }

    public String toString(){
        String items = "";
        for (ShoppingListItem shoppingListItem:shoppingListItems) {
            items = items.concat(shoppingListItem.toString() + "\n");
        }
        return "Shop: " + this.id + " \n" + this.name + " \n" + items;
    }

}
