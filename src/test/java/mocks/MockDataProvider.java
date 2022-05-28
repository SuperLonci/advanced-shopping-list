package mocks;

import de.lonci.application.DataProvider;
import de.lonci.domain.*;

import java.util.ArrayList;
import java.util.List;

public class MockDataProvider implements DataProvider {

    private ShopType[] shopTypes;
    private Product[] products;
    private Chain[] chains;
    private Shop[] shops;

    @Override
    public void initialize() {
        shopTypes = new ShopType[] {
          new ShopTypeBuilder("1").name("Supermarkt").build(),
          new ShopTypeBuilder("2").name("Baumarkt").build(),
        };

        products = new Product[] {
                new ProductBuilder("1").name("Milch").type("Milch").build(),
                new ProductBuilder("2").name("Kartoffeln").type("Kartoffeln").build(),
                new ProductBuilder("3").name("Fleisch").type("Fleisch").build(),
        };

        chains = new Chain[] {
                new ChainBuilder("1").name("Aldi").products(new Product[]{products[0]}).build(),
                new ChainBuilder("2").name("Rewe").products(new Product[]{products[1]}).build(),
                new ChainBuilder("3").name("Lidl").products(new Product[]{products[2]}).build(),
        };

        shops = new Shop[] {
                new ShopBuilder("1")
                        .name("Aldi")
                        .chain(chains[0])
                        .priority(1)
                        .build(),
                new ShopBuilder("2")
                        .name("Rewe")
                        .chain(chains[1])
                        .priority(2)
                        .build(),
                new ShopBuilder("3")
                        .name("Lidl")
                        .chain(chains[2])
                        .priority(3)
                        .build(),
        };
    }

    @Override
    public Shop[] getShops() {
        return shops;
    }

    @Override
    public ShopType[] getShopTypes() {
        return shopTypes;
    }

    @Override
    public Chain[] getChains() {
        return chains;
    }

    @Override
    public Product[] getProducts() {
        return products;
    }
}
