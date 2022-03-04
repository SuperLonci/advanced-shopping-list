package de.lonci.domain;

import java.io.Serializable;

public class ShoppingListItem implements Serializable {

    String name;

    public ShoppingListItem(String name){
        this.name = name;
    }

}
