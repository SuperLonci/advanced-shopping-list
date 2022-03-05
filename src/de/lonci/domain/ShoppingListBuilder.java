package de.lonci.domain;

import java.util.List;

public class ShoppingListBuilder {
    final String id;
    String name;
    List<ShoppingListItem> shoppingListItems;

    public ShoppingListBuilder(String id) {
        this.id = id;
    }

    public ShoppingListBuilder name(String name) {
        this.name = name;
        return this;
    }

    public ShoppingListBuilder shoppingListItems(List<ShoppingListItem> shoppingListItems) {
        this.shoppingListItems = shoppingListItems;
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
