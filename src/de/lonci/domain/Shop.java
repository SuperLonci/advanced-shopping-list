package de.lonci.domain;

public class Shop {
    final String id;
    String name;
    Chain chain;
    String address;
    Integer priority;

    public Shop(ShopBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.chain = builder.chain;
        this.address = builder.address;
        this.priority = builder.priority;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Chain getChain() {
        return chain;
    }

    public String getAddress() {
        return address;
    }

    public Integer getPriority() {
        return priority;
    }

    public String toString(){
        return "Shop: " + this.id + ", " + this.name + ", " + this.chain + ", " + this.address + ", " + this.priority;
    }
}
