package de.lonci.domain;

import java.util.List;

public class ShoppingList {
    private List<ShoppingListItem> items;

    public void append(ShoppingListItem item){
        items.add(item);
    }

    public List<ShoppingListItem> getItems(){
        return items;
    }
}
