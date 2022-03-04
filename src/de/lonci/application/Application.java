package de.lonci.application;

import de.lonci.domain.Shop;
import de.lonci.domain.ShoppingList;
import de.lonci.domain.ShoppingListItem;
import de.lonci.plugins.repository.BinaryStreamShoppingListRepository;

import javax.xml.crypto.Data;
import java.util.List;

public class Application {
    DataProvider dataProvider;
    UserInterface userInterface;
    ShoppingListRepository shoppingListRepository;
    ShoppingList activeShoppingList;

    public Application(DataProvider dataProvider, UserInterface userInterface, ShoppingListRepository shoppingListRepository){
        this.dataProvider = dataProvider;
        this.userInterface = userInterface;
        this.shoppingListRepository = shoppingListRepository;
    }

    public void start(){
        dataProvider.initialize();
        userInterface.create(this);
    }

    public DataProvider getDataProvider(){
        return dataProvider;
    }

    public ShoppingListRepository getShoppingListRepository(){
        return shoppingListRepository;
    }

    public ShoppingList getAdvancedShoppingList(){
        // todo: Aufteilung und Berechnung um in einem neuen Datenmodell eine "advanced" strukturierte Liste zu erhalten
        return null;
    }

    public ShoppingList getActiveShoppingList(){
        return activeShoppingList;
    }

    public void setActiveShoppingList(ShoppingList activeShoppingList) {
        this.activeShoppingList = activeShoppingList;
    }

    public void createNewShoppingList(String name, List<ShoppingListItem> shoppingListItems){
        ShoppingList shoppingList = new ShoppingList(generateId(name), name, shoppingListItems);
        shoppingListRepository.save(shoppingList);
    }

    private String generateId(String name){
        // todo do some magic
        return name;
    }

}
