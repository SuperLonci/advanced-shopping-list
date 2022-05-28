package mocks;

import de.lonci.application.ShoppingListRepository;
import de.lonci.domain.ShoppingList;

import java.util.ArrayList;
import java.util.List;

public class MockRepository implements ShoppingListRepository {

    private List<ShoppingList> shoppingLists = new ArrayList<>();

    @Override
    public void delete(String id) {
        shoppingLists.removeIf(shoppingList -> shoppingList.getId().equals(id));
    }

    @Override
    public void save(ShoppingList shoppingList) {
        if (!shoppingLists.contains(shoppingList)) {
            shoppingLists.add(shoppingList);
        }
    }

    @Override
    public ShoppingList getById(String id) {
        return shoppingLists.stream()
                .filter(shoppingList -> shoppingList.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<ShoppingList> getAll() {
        return shoppingLists;
    }

    public void clear() {
        shoppingLists.clear();
    }
}
