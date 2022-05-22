package de.lonci;

import de.lonci.application.Application;
import de.lonci.plugins.data.XMLDataProvider;
import de.lonci.plugins.repository.BinaryStreamShoppingListRepository;
import de.lonci.plugins.ui.console.ConsoleUserInterface;

public class Main {

    public static void main(String[] args) {
        var xmlDataProvider = new XMLDataProvider("./data/");
        var consoleUserInterface = new ConsoleUserInterface();
        var shoppingListRepository = new BinaryStreamShoppingListRepository("./data/shoppinglists/", xmlDataProvider);
        Application application = new Application(xmlDataProvider,consoleUserInterface, shoppingListRepository);
        application.start();

    }
}
