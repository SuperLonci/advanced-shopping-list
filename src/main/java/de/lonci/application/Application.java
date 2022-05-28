package de.lonci.application;

import de.lonci.domain.*;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
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

    // if product is found in Stores in Shopping List the product is added and true is returned
    public boolean tryAddProductToShoppingList(Product product) {
        for (ShoppingListStore item : activeShoppingList.getShoppingListStores()) {
            for (Product chainProduct : item.getShop().getChain().getProducts()) {
                if (product.getId().equals(chainProduct.getId())) {
                    activeShoppingList.addProductToShoppingListStore(product, activeShoppingList.getShoppingListStores().indexOf(item));
                    System.out.printf("Product %s found in %s%n", product.getName(), item.getShop().getName());
                    saveShoppingList(activeShoppingList);
                    return true;
                }
            }
        }
        return false;
    }

    public List<Chain> getChainsOfferingProduct(Product product){
        return Arrays.stream(dataProvider.getChains()).filter(chain -> Arrays.stream(chain.getProducts()).anyMatch(p -> p == product)).collect(Collectors.toList());
    }

    public boolean removeProductFromShoppingList(Product product){
        boolean removed = activeShoppingList.removeProductFromList(product);
        if (removed){
            saveShoppingList(activeShoppingList);
        }
        return removed;
    }

    public ShoppingListStore getOrAddShoppingListStore(Shop shop){
        var existingStore = activeShoppingList.getShoppingListStores().stream().filter(store -> store.getShop().equals(shop)).findAny();
        if (existingStore.isPresent()){
            return existingStore.get();
        }

        var store = new ShoppingListStoreBuilder().shop(shop).build();
        activeShoppingList.addShoppingListStore(store);
        saveShoppingList(activeShoppingList);
        return store;
    }

    public List<Shop> getShopsFromChain(Chain chain){
        return Arrays.stream(dataProvider.getShops()).filter(shop -> shop.getChain() == chain).collect(Collectors.toList());
    }

    public void setActiveShoppingList(ShoppingList activeShoppingList) {
        this.activeShoppingList = activeShoppingList;
    }

    public void createNewShoppingList(String name){
        ShoppingList shoppingList = new ShoppingListBuilder(generateId()).name(name).build();
        saveShoppingList(shoppingList);
        setActiveShoppingList(shoppingList);
    }

    public void removeEmptyShoppingListStores(){
        for (int i = activeShoppingList.getShoppingListStores().size() - 1; i >= 0; i--) {
            var shoppingListStore = activeShoppingList.getShoppingListStores().get(i);
            if (shoppingListStore.getProducts().isEmpty()){
                activeShoppingList.removeShoppingListStore(shoppingListStore);
            }
        }
        saveShoppingList(activeShoppingList);
    }

    public void saveShoppingList(ShoppingList shoppingList){
        shoppingListRepository.save(shoppingList);
    }

    private String generateId(){
        return UUID.randomUUID().toString();
    }
}
