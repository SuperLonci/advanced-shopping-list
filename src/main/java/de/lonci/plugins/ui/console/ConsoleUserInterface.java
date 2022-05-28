package de.lonci.plugins.ui.console;

import de.lonci.application.Application;
import de.lonci.application.UserInterface;
import de.lonci.domain.Shop;
import de.lonci.domain.ShoppingList;
import de.lonci.plugins.ui.console.menus.MainMenu;

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

    private void menu() {
        var menu = new MainMenu(application);
        while (menu.isRunning()) {
            menu.run();
        }
    }
}
