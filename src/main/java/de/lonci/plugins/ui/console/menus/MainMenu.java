package de.lonci.plugins.ui.console.menus;

import de.lonci.application.Application;
import de.lonci.domain.Shop;
import de.lonci.domain.ShoppingList;
import de.lonci.plugins.ui.console.ConsoleUserInterface;
import de.lonci.plugins.ui.console.MenuBase;
import de.lonci.plugins.ui.console.MenuItem;

public class MainMenu extends MenuBase {

    public MainMenu(Application application) {
        super(application);
        updateItems();
    }

    private void updateItems(){
        clearItems();

        setMenuHeader("Please select an option: ");
        int menuItemNumber = 0;
        addItem(new MenuItem(Integer.toString(menuItemNumber++), "Exit", this::exit));
        addItem(new MenuItem(Integer.toString(menuItemNumber++), "Select a Shopping List", this::setActiveShoppingList));
        if (application.getActiveShoppingList() != null) {
            addItem(new MenuItem(Integer.toString(menuItemNumber++), "Manage Shopping List (" + application.getActiveShoppingList().getName() + ")", this::manageShoppingList));
        }
        addItem(new MenuItem(Integer.toString(menuItemNumber++), "Create a new Shopping List", this::createShoppingList));
        addItem(new MenuItem(Integer.toString(menuItemNumber++), "Rename a Shopping List", this::renameShoppingList));
        addItem(new MenuItem(Integer.toString(menuItemNumber++), "Delete a Shopping List", this::deleteShoppingList));
        addItem(new MenuItem(Integer.toString(menuItemNumber++), "List all shops", this::showShops));
    }

    private void exit(){
        running = false;
    }

    private ShoppingList selectShoppingList(){
        var menu = new ObjectSelectionMenu<ShoppingList>(application, application.getShoppingListRepository().getAll());
        menu.run();
        if (menu.selection == null){
            return null;
        }
        return menu.selection;
    }
    private void setActiveShoppingList(){
        var selectedShoppingList = selectShoppingList();
        if (selectedShoppingList != null){
            application.setActiveShoppingList(selectedShoppingList);
            System.out.println(application.getActiveShoppingList().getName() + " selected");
        }
        updateItems();
    }

    private void manageShoppingList(){
        var menu = new ManageShoppingListMenu(application);
        while (menu.isRunning()){
            menu.run();
            updateItems();
        }
    }

    private void createShoppingList(){
        System.out.println("Enter a name:");
        String shoppingListName = receiveInput();
        application.createNewShoppingList(shoppingListName);
        System.out.println("New Shopping List created");
    }

    private void renameShoppingList(){
        var selectedShoppingList = selectShoppingList();
        if (selectedShoppingList == null){
            return;
        }
        System.out.println("Enter a new name: ");
        selectedShoppingList.setName(receiveInput());
        application.getShoppingListRepository().save(selectedShoppingList);
        if (selectedShoppingList.getId().equals(application.getActiveShoppingList().getId())){
            application.setActiveShoppingList(selectedShoppingList);
        }
        updateItems();
    }

    private void deleteShoppingList(){
        var selectedShoppingList = selectShoppingList();
        if (selectedShoppingList != null) {
            if (application.getActiveShoppingList() != null && application.getActiveShoppingList().getId().equals(selectedShoppingList.getId())){
                application.setActiveShoppingList(null);
            }
            application.deleteShoppingList(selectedShoppingList.getId());
            updateItems();
        }
    }

    private void showShops(){
        for (Shop shop : application.getDataProvider().getShops()) {
            System.out.println(shop.getName());
        }
    }
}
