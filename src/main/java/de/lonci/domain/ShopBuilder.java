package de.lonci.domain;

public class ShopBuilder {
    final String id;
    String name;
    Chain chain;
    String address;
    Integer priority;

    public ShopBuilder(String id) {
        this.id = id;
    }

    public ShopBuilder name(String name) {
        this.name = name;
        return this;
    }

    public ShopBuilder chain(Chain chain) {
        this.chain = chain;
        return this;
    }

    public ShopBuilder address(String address) {
        this.address = address;
        return this;
    }

    public ShopBuilder priority(Integer priority) {
        this.priority = priority;
        return this;
    }

    public Shop build() {
        Shop shop = new Shop(this);
        validateShopObject(shop);
        return shop;
    }

    private void validateShopObject(Shop shop) {
        //Do some basic validations to check
    }

}
