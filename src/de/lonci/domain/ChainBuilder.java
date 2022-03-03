package de.lonci.domain;

public class ChainBuilder {
    final String id;
    String name;
    String shopTypeId;
    Product[] products;

    public ChainBuilder(String id) {
        this.id = id;
    }

    public ChainBuilder name(String name) {
        this.name = name;
        return this;
    }

    public ChainBuilder shopTypeId(String shopTypeId) {
        this.shopTypeId = shopTypeId;
        return this;
    }

    public ChainBuilder products(Product[] products) {
        this.products = products;
        return this;
    }

    public Chain build() {
        Chain chain = new Chain(this);
        validateChainObject(chain);
        return chain;
    }

    private void validateChainObject(Chain chain) {
        //Do some basic validations to check
    }

}
