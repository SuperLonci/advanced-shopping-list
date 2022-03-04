package de.lonci.application;

import de.lonci.domain.ShoppingList;

public interface ShoppingListRepository {
    void delete(String id);
    void save(ShoppingList shoppingList);
    ShoppingList getById(String id);
    ShoppingList[] getAll();
}
