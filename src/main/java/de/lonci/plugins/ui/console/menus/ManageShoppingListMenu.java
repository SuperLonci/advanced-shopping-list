package de.lonci.plugins.ui.console.menus;

import de.lonci.application.Application;
import de.lonci.domain.Chain;
import de.lonci.domain.Product;
import de.lonci.domain.Shop;
import de.lonci.domain.ShoppingListStore;
import de.lonci.plugins.ui.console.MenuBase;
import de.lonci.plugins.ui.console.MenuItem;

import java.util.ArrayList;

public class ManageShoppingListMenu extends MenuBase {
    public ManageShoppingListMenu(Application application) {
        super(application);

        setMenuHeader("Please select an option: ");
        addItem(new MenuItem("0", "Exit", this::exit));
        addItem(new MenuItem("1", "Show List", this::showList));
        addItem(new MenuItem("2", "Add a product", this::addProduct));
        addItem(new MenuItem("3", "Remove a product", this::removeProduct));
        addItem(new MenuItem("4", "Change store for product", this::changeStoreForProduct));
    }

    private void exit(){
        running = false;
    }

    private void showList(){
        System.out.println("List: " + application.getActiveShoppingList().getName());
        for (ShoppingListStore shoppingListStore: application.getActiveShoppingList().getShoppingListStores()) {
            System.out.println("Store: " + shoppingListStore.getShop().getDisplayName());
            for (Product product: shoppingListStore.getProducts()) {
                System.out.println("    - " + product.getDisplayName());
            }
        }
    }

    private void addProduct(){
        System.out.println("Enter a product: ");
        var products = application.matchProduct(receiveInput());
        if (products == null){
            System.out.println("No product found");
            return;
        }
        var productMenu = new ObjectSelectionMenu<Product>(application, products);
        productMenu.run();

        if (productMenu.selection == null) {
            System.out.println("No product selected");
            return;
        }

        if (application.tryAddProductToShoppingList(productMenu.selection)) {
            System.out.println(productMenu.selection.getName() + " added");
            return;
        }

        ShoppingListStore store = selectStoreForProduct(productMenu.selection);

        application.getActiveShoppingList().addProductToShoppingListStore(productMenu.selection, application.getActiveShoppingList().getShoppingListStores().indexOf(store));
        application.saveShoppingList(application.getActiveShoppingList());

        System.out.println(productMenu.selection.getDisplayName() + " added");
    }

    private Product selectProduct(){
        var products = new ArrayList<Product>();
        for (ShoppingListStore shoppingListStore: application.getActiveShoppingList().getShoppingListStores()) {
            products.addAll(shoppingListStore.getProducts());
        }

        if (products.isEmpty()){
            System.out.println("No product found");
            return null;
        }

        var menu = new ObjectSelectionMenu<Product>(application, products);
        menu.run();

        if (menu.selection == null) {
            System.out.println("No product selected");
            return null;
        }
        return menu.selection;
    }

    private ShoppingListStore selectStoreForProduct(Product product){
        var chains = application.getChainsOfferingProduct(product);
        if (chains.isEmpty()){
            System.out.println("No stores offering this product found.");
            return null;
        }

        var chainMenu = new ObjectSelectionMenu<Chain>(application, chains);
        chainMenu.run();
        var shops = application.getShopsFromChain(chainMenu.selection);
        if (shops.isEmpty()){
            System.out.println("No store found.");
            return null;
        }

        if (shops.size() == 1){
            return application.getOrAddShoppingListStore(shops.get(0));
        } else {
            var storeMenu = new ObjectSelectionMenu<Shop>(application, shops);
            storeMenu.run();
            return application.getOrAddShoppingListStore(storeMenu.selection);
        }
    }



    private void removeProduct(){
        System.out.println("Remove product:");

        var selectedProduct = selectProduct();
        if (selectedProduct == null){
            return;
        }

        if (application.removeProductFromShoppingList(selectedProduct)) {
            System.out.println(selectedProduct.getName() + " removed");
            application.removeEmptyShoppingListStores();
        }
    }

    private void changeStoreForProduct(){
        System.out.println("Change Store:");

        var selectedProduct = selectProduct();
        if (selectedProduct == null){
            return;
        }

        if (!application.removeProductFromShoppingList(selectedProduct)) {
            return;
        }

        ShoppingListStore store = selectStoreForProduct(selectedProduct);

        application.getActiveShoppingList().addProductToShoppingListStore(selectedProduct, application.getActiveShoppingList().getShoppingListStores().indexOf(store));
        application.saveShoppingList(application.getActiveShoppingList());
        System.out.println(selectedProduct.getDisplayName() + " changed to " + store.getShop().getDisplayName());
        application.removeEmptyShoppingListStores();
    }
}

