package de.lonci.plugins.ui.console.menus;

import de.lonci.application.Application;
import de.lonci.domain.Product;
import de.lonci.plugins.ui.console.ConsoleUserInterface;
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
        application.getActiveShoppingList().toString();
    }

    private void addProduct(){
        System.out.println("Enter a product: ");
        var menu = new ProductSelectionMenu(application, application.matchProduct(receiveInput()));
        menu.run();

        if (menu.selection != null){
            application.addProductToShoppingList(menu.selection);
            System.out.println(menu.selection.getName() + " selected");
        } else {
            System.out.println("No product found");
        }

    }
}

