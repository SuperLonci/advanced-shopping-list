package de.lonci.plugins.ui.console.menus;

import de.lonci.application.Application;
import de.lonci.domain.Product;
import de.lonci.domain.ShoppingList;
import de.lonci.plugins.ui.console.MenuBase;
import de.lonci.plugins.ui.console.MenuItem;

import java.util.List;

public class ProductSelectionMenu extends MenuBase {

    public Product selection;

    public ProductSelectionMenu(Application application, List<Product> products) {
        super(application);

        setMenuHeader("Select a Product: ");
        addItem(new MenuItem("0", "Exit", this::exit));
        for (int i = 0; i < (products.size()); i++) {
            var currentIndex = i;
            addItem(new MenuItem(Integer.toString(currentIndex + 1), products.get(currentIndex).getName(), ()->selectProduct(products.get(currentIndex))));
        }
    }

    private void selectProduct(Product product){
        selection = product;
    }

    private void exit(){
    }
}
