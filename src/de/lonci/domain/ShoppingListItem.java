package de.lonci.domain;

import java.io.Serializable;
import java.util.List;

public class ShoppingListItem implements Serializable {

    String id;
    Shop shop;
    List<Product> products;

    public ShoppingListItem(ShoppingListItemBuilder builder) {
        this.id = builder.id;
        this.shop = builder.shop;
        this.products = builder.products;
    }

    public String getId() {
        return id;
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
        return "Shop: " + this.id + " \n" + this.shop + " \n" + items;
    }
}
