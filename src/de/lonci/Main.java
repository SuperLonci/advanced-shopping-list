package de.lonci;

import de.lonci.domain.Chain;
import de.lonci.domain.Product;
import de.lonci.domain.Shop;
import de.lonci.domain.ShopType;
import de.lonci.plugins.XMLDataProvider;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("Enter Number 1-4 for functions: ");

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        XMLDataProvider data = new XMLDataProvider("");

        Shop[] shops = data.getShops();
        ShopType[] shopTypes = data.getShopTypes();
        Chain[] chains = data.getChains();
        Product[] products = data.getProducts();

        if (Integer.parseInt(input) == 1){
            for (Shop shop : shops) {
                System.out.println(shop.toString());
            }
        }else if (Integer.parseInt(input) == 2){
            for (ShopType shopType : shopTypes) {
                System.out.println(shopType.toString());
            }
        }else if (Integer.parseInt(input) == 3){
            for (Chain chain : chains) {
                System.out.println(chain.toString());
            }
        }else if (Integer.parseInt(input) == 4){
            for (Product product : products) {
                System.out.println(product.toString());
            }
        }
    }
}
