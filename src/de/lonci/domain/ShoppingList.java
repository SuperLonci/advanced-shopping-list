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
        shoppingListStores.get(shoppingListItemNumber).addProduct(product);
    }

    public boolean removeProductFromList(Product product){
        boolean removed = false;
        for (ShoppingListStore shoppingListStore: shoppingListStores) {
            removed |= shoppingListStore.removeProduct(product);
        }
        return removed;
    }

    public boolean removeShoppingListStore(ShoppingListStore shoppingListStore){
        return shoppingListStores.remove(shoppingListStore);
    }

    public String toString(){
        return "ShoppingList: " + this.name;
    }

    @Override
    public String getDisplayName() {
        return name;
    }
}
