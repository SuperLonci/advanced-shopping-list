package de.lonci.domain;

import java.io.Serializable;
import java.util.List;

public class ShoppingList implements Serializable, Displayable{
    String id;
    String name;
    List<ShoppingListStore> shoppingListStores;

    public ShoppingList(ShoppingListBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.shoppingListStores = builder.shoppingListStores;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<ShoppingListStore> getShoppingListStores() {
        return shoppingListStores;
    }

    public void addShoppingListStore(ShoppingListStore shoppingListStore){
        shoppingListStores.add(shoppingListStore);
    }

    public void addProductToShoppingListStore(Product product, Integer shoppingListItemNumber){
        shoppingListStores.get (shoppingListItemNumber).addProduct(product);
    }

    public String toString(){
        String items = "";
        for (ShoppingListStore shoppingListStore : shoppingListStores) {
            items = items.concat(shoppingListStore.toString() + "\n");
        }
        return "Shop: " + this.id + " \n" + this.name + " \n" + items;
    }

    @Override
    public String getDisplayName() {
        return name;
    }
}
