package de.lonci.application;

import de.lonci.domain.*;
import de.lonci.plugins.repository.BinaryStreamShoppingListRepository;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

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

    public ShoppingList getShoppingList(){
        // todo: Aufteilung und Berechnung um in einem neuen Datenmodell eine "advanced" strukturierte Liste zu erhalten
        return null;
    }

    public ShoppingList getActiveShoppingList(){
        return activeShoppingList;
    }

    public List<Product> matchProduct(String userInput){
        return Arrays.stream(dataProvider.getProducts()).filter(product -> product.getName().toLowerCase().contains(userInput.toLowerCase())).collect(Collectors.toList());
    }

    public void addProductToShoppingList(Product product){
        for (ShoppingListItem item: activeShoppingList.getShoppingListItems()) {
            for (Product chainProduct: item.getShop().getChain().getProducts()) {
                if (product.getId().equals(chainProduct.getId())){
                    activeShoppingList.addProductToShoppingListItem(product, activeShoppingList.getShoppingListItems().indexOf(item));
                }
            }
        }
    }

    public void setActiveShoppingList(ShoppingList activeShoppingList) {
        this.activeShoppingList = activeShoppingList;
    }

    public void createNewShoppingList(String name){
        ShoppingList shoppingList = new ShoppingListBuilder(generateId(name)).name(name).build();
        shoppingListRepository.save(shoppingList);
        setActiveShoppingList(shoppingList);
    }

    private String generateId(String name){
        // todo do some magic
        return name;
    }

}
