package de.lonci.plugins.repository;

import de.lonci.application.DataProvider;
import de.lonci.application.ShoppingListRepository;
import de.lonci.domain.ShoppingList;
import de.lonci.domain.ShoppingListStore;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class BinaryStreamShoppingListRepository implements ShoppingListRepository {

    String shoppingListPath;
    DataProvider dataProvider;

    public BinaryStreamShoppingListRepository(String shoppingListPath, DataProvider dataProvider){
        this.shoppingListPath = shoppingListPath;
        this.dataProvider = dataProvider;

        // Create folder if it doesn't exist
        Path path = new File(shoppingListPath).toPath();
        if (!Files.exists(path)) {
            try {
                Files.createDirectory(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void delete(String id) {
        try {
            Files.delete(Path.of(shoppingListPath + id + ".bin"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(ShoppingList shoppingList) {
        FileOutputStream fileOutputStream;
        ObjectOutputStream objectOutputStream;

        String filePath = (shoppingListPath + shoppingList.getId() + ".bin");
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
        return loadFromPath(shoppingListPath + id + ".bin");
    }

    @Override
    public List<ShoppingList> getAll() {
        // todo: hashmap mit allen vorhandenen ShoppingLists erstellen und speichern

        List<ShoppingList> shoppingLists = new ArrayList<>();

        File[] listOfFiles = Path.of(shoppingListPath).toFile().listFiles();
        for (int i = 0; i < (listOfFiles != null ? listOfFiles.length : 0); i++) {
            if (listOfFiles[i].isFile()) {
                var shoppingList = loadFromPath(listOfFiles[i].getPath());
                shoppingLists.add(shoppingList);
            }
        }
        return shoppingLists;
    }

    public ShoppingList loadFromPath(String path){
        try {
            FileInputStream fileInputStream = new FileInputStream(path);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            ShoppingList shoppingList = (ShoppingList) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();

            for (ShoppingListStore shoppingListStore : shoppingList.getShoppingListStores()) {
                shoppingListStore.loadReferencedData(dataProvider);
            }

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
}