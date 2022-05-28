package de.lonci.domain;

public class Product implements Displayable {
    final String id;
    String name;
    String type;

    public Product(ProductBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.type = builder.type;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String toString(){

        return "Product: " + this.id + ", " + this.name + ", " + this.type;
    }

    @Override
    public String getDisplayName() {
        return name;
    }
}
