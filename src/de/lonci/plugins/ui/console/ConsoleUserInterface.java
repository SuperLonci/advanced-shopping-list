package de.lonci.plugins.ui.console;

import de.lonci.application.Application;
import de.lonci.application.ShoppingListRepository;
import de.lonci.application.UserInterface;
import de.lonci.domain.Shop;
import de.lonci.domain.ShoppingList;

import java.io.IOException;
import java.util.Scanner;

public class ConsoleUserInterface implements UserInterface {

    Application application;

    @Override
    public void create(Application application) {
        this.application = application;
        start();
        menu();
    }

    void start(){
        System.out.println("Herzlich Willkommen bei deiner Einkaufsliste!");
    }

    private int input(){
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e){
            System.out.println("Please enter a valid number");
        }
        return 0;
    }

    private void menu(){
        while(true) {
            System.out.println("Enter a number to select one of the following functions: ");
            System.out.println("0: Exit");
            System.out.println("1: Select a Shopping List");
            System.out.println("2: Manage entries in Shopping List");
            System.out.println("3: Show advanced Shopping List");
            System.out.println("4: List all shops");
            System.out.println("5: Create a new Shopping List");

            int input = input();
            if (input == 0) {
                break;
            } else if (input == 1) {
                selectShoppingList();
            } else if (input == 2) {
                manageShoppingList();
            } else if (input == 3) {
                System.out.println(application.getAdvancedShoppingList().toString());
            } else if (input == 4) {
                for (Shop shop : application.getDataProvider().getShops()) {
                    System.out.println(shop.getName());
                }
            } else if (input == 5) {
                application.createNewShoppingList("name", null);
            }

            System.out.println("-----------------------");
            System.out.println("Press enter to continue");
            try {
                System.in.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void selectShoppingList(){
        System.out.println("Select a Shopping List: ");
        ShoppingList[] list = application.getShoppingListRepository().getAll();
        for (int i = 0; i < (list.length); i++) {
            System.out.println(i + ": " + list[i].getName());
        }
        application.setActiveShoppingList(application.getShoppingListRepository().getById(list[input()].getId()));
        System.out.println(application.getActiveShoppingList().getName() + " selected");
    }

    private void manageShoppingList(){
        if (application.getActiveShoppingList() != null){
            // todo crud for products
        } else {
            System.out.println("Please select a Shopping List first");
        }
    }
}
