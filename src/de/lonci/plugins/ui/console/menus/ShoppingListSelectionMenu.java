package de.lonci.plugins.ui.console.menus;

import de.lonci.application.Application;
import de.lonci.domain.ShoppingList;
import de.lonci.plugins.ui.console.MenuBase;
import de.lonci.plugins.ui.console.MenuItem;

import java.util.List;

public class ShoppingListSelectionMenu extends MenuBase {

    public ShoppingListSelectionMenu(Application application) {
        super(application);

        setMenuHeader("Select a Shopping List: ");

        addItem(new MenuItem("0", "Exit", this::exit));

        List<ShoppingList> list = application.getShoppingListRepository().getAll();
        for (int i = 0; i < (list.size()); i++) {
            var currentIndex = i;
            addItem(new MenuItem(Integer.toString(currentIndex + 1), list.get(currentIndex).getName(), ()->selectShoppingList(list.get(currentIndex))));
        }
    }

    private void selectShoppingList(ShoppingList shoppingList){
        application.setActiveShoppingList(shoppingList);
        System.out.println(application.getActiveShoppingList().getName() + " selected");
    }

    private void exit(){

    }
}
