package de.lonci.domain;

public class ShopTypeBuilder {
    final String id;
    String name;

    public ShopTypeBuilder(String id) {
        this.id = id;
    }

    public ShopTypeBuilder name(String name) {
        this.name = name;
        return this;
    }

    public ShopType build() {
        ShopType shopType = new ShopType(this);
        validateShopTypeObject(shopType);
        return shopType;
    }

    private void validateShopTypeObject(ShopType shopType) {
        //Do some basic validations to check
    }

}
