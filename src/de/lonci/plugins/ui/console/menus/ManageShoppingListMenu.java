package de.lonci.plugins.ui.console.menus;

import de.lonci.application.Application;
import de.lonci.domain.Chain;
import de.lonci.domain.Product;
import de.lonci.domain.Shop;
import de.lonci.plugins.ui.console.MenuBase;
import de.lonci.plugins.ui.console.MenuItem;

public class ManageShoppingListMenu extends MenuBase {
    public ManageShoppingListMenu(Application application) {
        super(application);

        setMenuHeader("Please select an option: ");
        addItem(new MenuItem("0", "Exit", this::exit));
        addItem(new MenuItem("1", "Show List", this::showList));
        addItem(new MenuItem("2", "Add a product", this::addProduct));
        addItem(new MenuItem("3", "Remove a product", this::exit));
        addItem(new MenuItem("4", "Change store for product", this::exit));
        addItem(new MenuItem("5", "List all products", this::exit));
    }

    private void exit(){
        running = false;
    }

    private void showList(){
        System.out.println("List: " + application.getActiveShoppingList().getName());
        System.out.println(application.getActiveShoppingList());
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

        var chains = application.getChainsOfferingProduct(productMenu.selection);
        if (chains.isEmpty()){
            System.out.println("No stores offering this product found.");
            return;
        }

        var chainMenu = new ObjectSelectionMenu<Chain>(application, chains);
        chainMenu.run();
        var shops = application.getShopsFromChain(chainMenu.selection);
        if (shops.isEmpty()){
            System.out.println("No store found.");
            return;
        }
        if (shops.size() == 1){
            application.addShoppingListStore(shops.get(0));
            return;
        }
        var storeMenu = new ObjectSelectionMenu<Shop>(application, shops);
        storeMenu.run();
        application.addShoppingListStore(storeMenu.selection);
        System.out.println(productMenu.selection.getName() + " added");
    }
}

