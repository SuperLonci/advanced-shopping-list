package de.lonci.domain;

import java.util.ArrayList;
import java.util.List;

public class Chain implements Displayable {
    final String id;
    String name;
    Product[] products;

    public Chain(ChainBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.products = builder.products;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Product[] getProducts() {
        return products;
    }


    public String toString(){
        List<String> productList = new ArrayList<>();
        for (Product product: products) {
            productList.add(product.getName());
        }
        return "Chain: " + this.id + ", " + this.name + ", Products: " + String.join(", ", productList);
    }

    @Override
    public String getDisplayName() {
        return name;
    }
}
