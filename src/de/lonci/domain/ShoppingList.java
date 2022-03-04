package de.lonci.domain;

import java.io.Serializable;
import java.util.List;

public class ShoppingList implements Serializable {
    String id;
    String name;
    private List<ShoppingListItem> items;

    public ShoppingList(String id, String name, List<ShoppingListItem> items) {
        this.id = id;
        this.name = name;
        this.items = items;
    }

    public void append(ShoppingListItem item){
        items.add(item);
    }

    public List<ShoppingListItem> getItems(){
        return items;
    }

    public String getId(){
        return id;
    }

    public String getName(){
        return name;
    }

}
