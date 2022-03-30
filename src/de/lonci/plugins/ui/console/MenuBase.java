package de.lonci.plugins.ui.console;

import de.lonci.application.Application;

import java.util.*;

public abstract class MenuBase {

    protected Application application;
    private Map<String, MenuItem> menuItems = new HashMap<>();
    private List<MenuItem> menuItemsOrdered = new ArrayList<>();
    private String menuHeader;


    public MenuBase(Application application) {
        this.application = application;
    }

    protected void addItem(MenuItem menuItem){
        menuItems.put(menuItem.getSelector(), menuItem);
        menuItemsOrdered.add(menuItem);
    }

    protected void setMenuHeader(String menuHeader) {
        this.menuHeader = menuHeader;
    }

    private void print(){
        System.out.println(menuHeader);
        for (MenuItem menuItem : menuItemsOrdered){
            menuItem.print();
        }
    }

    private String receiveInput(){
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public void run(){
        print();
        boolean inputFound = false;
        do{
            String input = receiveInput();
            inputFound = runItem(input);
        }while (!inputFound);
    }

    private boolean runItem(String itemChar){
        var menuItem = menuItems.get(itemChar);
        if (menuItem != null){
            menuItem.run();
            return true;
        } else {
            System.out.println("Menuitem not found.");
            return false;
        }
    }
}
