package de.lonci.domain;

import java.util.List;

public class ShoppingListItemBuilder {
    final String id;
    Shop shop;
    List<Product> products;

    public ShoppingListItemBuilder(String id) {
        this.id = id;
    }

    public ShoppingListItemBuilder shop(Shop shop) {
        this.shop = shop;
        return this;
    }

    public ShoppingListItemBuilder products(List<Product> products) {
        this.products = products;
        return this;
    }

    public ShoppingListItem build() {
        ShoppingListItem shoppingListItem = new ShoppingListItem(this);
        validateShoppingListItemObject(shoppingListItem);
        return shoppingListItem;
    }

    private void validateShoppingListItemObject(ShoppingListItem shoppingListItem) {
        //Do some basic validations to check
    }
}
