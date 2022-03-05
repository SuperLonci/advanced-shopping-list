package de.lonci.plugins.repository;

import de.lonci.application.ShoppingListRepository;
import de.lonci.domain.Product;
import de.lonci.domain.ShoppingList;
import de.lonci.domain.ShoppingListItem;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class BinaryStreamShoppingListRepository implements ShoppingListRepository {

    String shoppingListPath;

    public BinaryStreamShoppingListRepository(String shoppingListPath){
        this.shoppingListPath = shoppingListPath;
    }

    @Override
    public void delete(String id) {
        try {
            Files.delete(Path.of(shoppingListPath + "shoppingList" + id + ".txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(ShoppingList shoppingList) {
        FileOutputStream fileOutputStream;
        ObjectOutputStream objectOutputStream;

        String filePath = (shoppingListPath + "shoppingList" + shoppingList.getId() + ".txt");
        if (Files.notExists(Path.of(filePath))){
            File file = new File(filePath);
            try {
                FileWriter fw = new FileWriter(file.getAbsoluteFile());
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write("");
                bw.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        try {
            fileOutputStream = new FileOutputStream(filePath);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(shoppingList);
            objectOutputStream.flush();
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ShoppingList getById(String id) {
        try {
            FileInputStream fileInputStream = new FileInputStream(shoppingListPath + "shoppingList" + id + ".txt");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            ShoppingList shoppingList = (ShoppingList) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
            return shoppingList;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<ShoppingList> getAll() {
        // todo: hashmap mit allen vorhandenen ShoppingLists erstellen und speichern

        List<ShoppingList> shoppingLists = new ArrayList<>();

        try {
            File[] listOfFiles = Path.of(shoppingListPath).toFile().listFiles();
            for (int i = 0; i < (listOfFiles != null ? listOfFiles.length : 0); i++) {
                if (listOfFiles[i].isFile()) {
                    FileInputStream fileInputStream = new FileInputStream(listOfFiles[i].getPath());
                    ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                    ShoppingList shoppingList = (ShoppingList) objectInputStream.readObject();
                    objectInputStream.close();
                    fileInputStream.close();
                    shoppingLists.add(shoppingList);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return shoppingLists;
    }
}