package de.lonci.application;

import de.lonci.domain.Chain;
import de.lonci.domain.Product;
import de.lonci.domain.Shop;
import de.lonci.domain.ShopType;

public interface DataProvider {
    Shop[] getShops();
    ShopType[] getShopTypes();
    Chain[] getChains();
    Product[] getProducts();
}
