package de.lonci.domain;

public class ProductBuilder {
    final String id;
    String name;
    String type;

    public ProductBuilder(String id) {
        this.id = id;
    }

    public ProductBuilder name(String name) {
        this.name = name;
        return this;
    }

    public ProductBuilder type(String type) {
        this.type = type;
        return this;
    }

    public Product build() {
        Product product = new Product(this);
        validateProductObject(product);
        return product;
    }

    private void validateProductObject(Product product) {
        //Do some basic validations to check
    }

}
