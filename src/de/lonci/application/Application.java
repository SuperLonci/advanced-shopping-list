package de.lonci.application;

import de.lonci.domain.*;

import java.util.Arrays;
import java.util.List;
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
        return Arrays.stream(dataProvider.getProducts()).filter(product -> product.getType().toLowerCase().contains(userInput.toLowerCase())).collect(Collectors.toList());
    }

    public boolean tryAddProductToShoppingList(Product product) {
        for (ShoppingListStore item : activeShoppingList.getShoppingListStores()) {
            for (Product chainProduct : item.getShop().getChain().getProducts()) {
                if (product.getId().equals(chainProduct.getId())) {
                    activeShoppingList.addProductToShoppingListStore(product, activeShoppingList.getShoppingListStores().indexOf(item));
                    System.out.printf("Product %s found in %s%n", product.getName(), item.getShop().getName());
                    return true;
                }
            }
        }
        return false;
    }

    public List<Chain> getChainsOfferingProduct(Product product){
        return Arrays.stream(dataProvider.getChains()).filter(chain -> Arrays.stream(chain.getProducts()).anyMatch(p -> p == product)).collect(Collectors.toList());
    }

    public void addShoppingListStore(Shop shop){
        activeShoppingList.addShoppingListStore(new ShoppingListStoreBuilder().shop(shop).build());
    }

    public List<Shop> getShopsFromChain(Chain chain){
        return Arrays.stream(dataProvider.getShops()).filter(shop -> shop.getChain() == chain).collect(Collectors.toList());
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
