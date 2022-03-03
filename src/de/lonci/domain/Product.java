package de.lonci.domain;

public class Product {
    final String id;
    String name;

    public Product(ProductBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    public String toString(){

        return "Product: " + this.id + ", " + this.name;
    }
}
