package de.lonci.plugins.ui.console.menus;

import de.lonci.application.Application;
import de.lonci.domain.Displayable;
import de.lonci.domain.Product;
import de.lonci.plugins.ui.console.MenuBase;
import de.lonci.plugins.ui.console.MenuItem;

import java.util.List;

public class ObjectSelectionMenu<T extends Displayable> extends MenuBase {

    public T selection;

    public ObjectSelectionMenu(Application application, List<T> objects) {
        super(application);

        setMenuHeader("Select an entry: ");
        addItem(new MenuItem("0", "Exit", this::exit));
        for (int i = 0; i < (objects.size()); i++) {
            var currentIndex = i;
            addItem(new MenuItem(Integer.toString(currentIndex + 1), objects.get(currentIndex).getDisplayName(), ()->selectObject(objects.get(currentIndex))));
        }
    }

    private void selectObject(T object){
        selection = object;
    }

    private void exit(){
    }

}
