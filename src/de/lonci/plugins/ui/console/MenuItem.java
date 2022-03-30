package de.lonci.plugins.ui.console;

public class MenuItem {

    private String selector;
    private String displayName;
    private Runnable action;

    public MenuItem(String selector, String displayName, Runnable action) {
        this.selector = selector;
        this.displayName = displayName;
        this.action = action;
    }

    public String getSelector() {
        return selector;
    }

    public void print(){
        System.out.println(String.format("%s: %s", selector, displayName));
    }

    public void run(){
        action.run();
    }
}
