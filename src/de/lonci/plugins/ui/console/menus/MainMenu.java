package de.lonci.plugins.ui.console.menus;

import de.lonci.application.Application;
import de.lonci.plugins.ui.console.MenuBase;
import de.lonci.plugins.ui.console.MenuItem;

public class MainMenu extends MenuBase {

    private boolean running = true;

    public MainMenu(Application application) {
        super(application);

        setMenuHeader("Please select an option: ");
        addItem(new MenuItem("0", "Exit", this::exit));
        addItem(new MenuItem("1", "Select a Shopping List", this::selectShoppingList));
        addItem(new MenuItem("2", "Show selected Shopping List", this::exit));
        addItem(new MenuItem("3", "Manage Shopping List", this::manageShoppingList));
        addItem(new MenuItem("4", "Create a new Shopping List", this::exit));
        addItem(new MenuItem("5", "List all shops", this::exit));

    }

    public boolean isRunning(){
        return running;
    }

    private void exit(){
        running = false;
    }

    private void selectShoppingList(){
        var menu = new ShoppingListSelectionMenu(application);
        menu.run();
    }

    private void manageShoppingList(){
        var menu = new ManageShoppingListMenu(application);
        menu.run();
    }
}
