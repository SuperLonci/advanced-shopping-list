package de.lonci.domain;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListStoreBuilder {
    Shop shop;
    List<Product> products = new ArrayList<>();

    public ShoppingListStoreBuilder() {
    }

    public ShoppingListStoreBuilder shop(Shop shop) {
        this.shop = shop;
        return this;
    }

    public ShoppingListStoreBuilder products(List<Product> products) {
        this.products = products;
        return this;
    }

    public ShoppingListStore build() {
        ShoppingListStore shoppingListStore = new ShoppingListStore(this);
        validateShoppingListItemObject(shoppingListStore);
        return shoppingListStore;
    }

    private void validateShoppingListItemObject(ShoppingListStore shoppingListStore) {
        //Do some basic validations to check
    }
}
