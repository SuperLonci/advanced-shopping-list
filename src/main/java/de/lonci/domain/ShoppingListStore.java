package de.lonci.domain;

import de.lonci.application.DataProvider;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ShoppingListStore implements Serializable {

    transient Shop shop;
    transient List<Product> products;

    // to be able to save references in binary;
    String shopId;
    List<String> productIds;

    public ShoppingListStore(ShoppingListStoreBuilder builder) {
        this.shop = builder.shop;
        this.products = builder.products;
        this.shopId = this.shop.getId();
        this.productIds = this.products.stream().map(Product::getId).collect(Collectors.toList());
    }

    public Shop getShop() {
        return shop;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void addProduct(Product product){
        products.add(product);
        productIds.add(product.getId());
    }

    public boolean removeProduct(Product product){
        productIds.remove(product.getId());
        return products.remove(product);
    }

    public String toString(){
        String items = "";
        for (Product product:products) {
            items = items.concat(product.toString() + "\n");
        }
        return "Items: " + " \n" + this.shop + " \n" + items;
    }

    public void loadReferencedData(DataProvider dataProvider) {
        this.shop = Arrays.stream(dataProvider.getShops()).filter(s -> s.getId().equals(this.shopId)).findAny().orElse(null);
        this.products = this.productIds.stream().map(p -> Arrays.stream(dataProvider.getProducts()).filter(s -> s.getId().equals(p)).findAny().orElse(null)).collect(Collectors.toList());
    }
}
