package de.lonci.application;

import de.lonci.domain.ShoppingList;

import java.util.List;

public interface ShoppingListRepository {
    void delete(String id);
    void save(ShoppingList shoppingList);
    ShoppingList getById(String id);
    List<ShoppingList> getAll();
}
