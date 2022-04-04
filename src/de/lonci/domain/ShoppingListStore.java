package de.lonci.domain;

import java.io.Serializable;
import java.util.List;

public class ShoppingListStore implements Serializable {

    Shop shop;
    List<Product> products;

    public ShoppingListStore(ShoppingListStoreBuilder builder) {
        this.shop = builder.shop;
        this.products = builder.products;
    }

    public Shop getShop() {
        return shop;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void addProduct(Product product){
        products.add(product);
    }

    public String toString(){
        String items = "";
        for (Product product:products) {
            items = items.concat(product.toString() + "\n");
        }
        return "Items: " + " \n" + this.shop + " \n" + items;
    }
}
