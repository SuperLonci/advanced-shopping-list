package de.lonci.domain;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListBuilder {
    final String id;
    String name;
    List<ShoppingListStore> shoppingListStores = new ArrayList<>();

    public ShoppingListBuilder(String id) {
        this.id = id;
    }

    public ShoppingListBuilder name(String name) {
        this.name = name;
        return this;
    }

    public ShoppingListBuilder shoppingListItems(List<ShoppingListStore> shoppingListStores) {
        this.shoppingListStores = shoppingListStores;
        return this;
    }

    public ShoppingList build() {
        ShoppingList shoppingList = new ShoppingList(this);
        validateShoppingListObject(shoppingList);
        return shoppingList;
    }

    private void validateShoppingListObject(ShoppingList shoppingList) {
        //Do some basic validations to check
    }
}
