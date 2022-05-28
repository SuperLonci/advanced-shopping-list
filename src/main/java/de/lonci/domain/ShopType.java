package de.lonci.domain;

public class ShopType {
    final String id;
    String name;

    public ShopType(ShopTypeBuilder builder) {
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
        return "ShopType: " + this.id + ", " + this.name;
    }
}
