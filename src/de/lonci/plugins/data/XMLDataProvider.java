package de.lonci.plugins.data;

import de.lonci.application.DataProvider;
import de.lonci.domain.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.xml.parsers.*;

public class XMLDataProvider implements DataProvider {

    String folderPath;

    Shop[] shops;
    ShopType[] shopTypes;
    Chain[] chains;
    Product[] products;

    HashMap<String,Shop> shopHashMap = new HashMap<>();
    HashMap<String,ShopType> shopTypeHashMap = new HashMap<>();
    HashMap<String,Chain> chainHashMap = new HashMap<>();
    HashMap<String,Product> productHashMap = new HashMap<>();

    public XMLDataProvider(String folderPath){
        this.folderPath = folderPath;
    }


    private NodeList parseFile(String filePath, String elementName){
        try {
            File inputFile = new File(filePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            return doc.getElementsByTagName(elementName);
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void readProductsFromXML(String filePath){
        NodeList nList = parseFile(filePath, "product");
        products = new Product[nList.getLength()];
        for (int i = 0; i < nList.getLength(); i++) {
            Node nNode = nList.item(i);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                String id = eElement
                        .getElementsByTagName("id")
                        .item(0)
                        .getTextContent();

                String name = eElement
                        .getElementsByTagName("name")
                        .item(0)
                        .getTextContent();

                products[i] = new ProductBuilder(id).name(name).build();
                productHashMap.put(id,products[i]);
            }
        }
    }

    private void readShopTypesFromXML(String filePath){
        NodeList nList = parseFile(filePath, "shopType");
        shopTypes = new ShopType[nList.getLength()];
        for (int i = 0; i < nList.getLength(); i++) {
            Node nNode = nList.item(i);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                String id = eElement
                        .getElementsByTagName("id")
                        .item(0)
                        .getTextContent();

                String name = eElement
                        .getElementsByTagName("name")
                        .item(0)
                        .getTextContent();

                shopTypes[i] = new ShopTypeBuilder(id).name(name).build();
                shopTypeHashMap.put(id,shopTypes[i]);
            }
        }
    }

    private void readChainsFromXML(String filePath){
        NodeList nList = parseFile(filePath, "chain");
        chains = new Chain[nList.getLength()];

        for (int i = 0; i < nList.getLength(); i++) {
            Node nNode = nList.item(i);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                String id = eElement
                        .getElementsByTagName("id")
                        .item(0)
                        .getTextContent();

                String name = eElement
                        .getElementsByTagName("name")
                        .item(0)
                        .getTextContent();

                String shopTypeId = eElement
                        .getElementsByTagName("shopTypeId")
                        .item(0)
                        .getTextContent();

                NodeList childList = eElement
                        .getElementsByTagName("products")
                        .item(0)
                        .getChildNodes();

                List<Product> productList = new ArrayList<>();

                for (int k = 0; k < childList.getLength(); k++) {
                    Node childNode = childList.item(k);
                    if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                        Product chainProduct = productHashMap.get(childNode.getTextContent().trim());
                        if (chainProduct != null){
                            productList.add(chainProduct);
                        } else {
                            System.out.println("Product not found with id " + childNode.getTextContent().trim());
                        }
                    }
                }

                Product[] chainProducts = new Product[productList.size()];
                productList.toArray(chainProducts);

                chains[i] = new ChainBuilder(id).name(name).shopTypeId(shopTypeId).products(chainProducts).build();
                chainHashMap.put(id,chains[i]);
            }
        }
    }

    private void readShopsFromXML(String filePath){
        NodeList nList = parseFile(filePath, "shop");
        shops = new Shop[nList.getLength()];
        for (int i = 0; i < nList.getLength(); i++) {
            Node nNode = nList.item(i);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                String id = eElement
                        .getElementsByTagName("id")
                        .item(0)
                        .getTextContent();

                String name = eElement
                        .getElementsByTagName("name")
                        .item(0)
                        .getTextContent();

                String chainId = eElement
                        .getElementsByTagName("chainId")
                        .item(0)
                        .getTextContent();

                String address = eElement
                        .getElementsByTagName("address")
                        .item(0)
                        .getTextContent();

                String priority = eElement
                        .getElementsByTagName("priority")
                        .item(0)
                        .getTextContent();

                Chain shopChain = chainHashMap.get(chainId);
                if (shopChain == null){
                    System.out.println("Chain not found with id " + chainId);
                }

                shops[i] = new ShopBuilder(id).name(name).chain(shopChain).address(address).priority(Integer.parseInt(priority)).build();
                shopHashMap.put(id,shops[i]);
            }
        }
    }

    @Override
    public void initialize() {
        readProductsFromXML(folderPath + "products.xml");
        readShopTypesFromXML(folderPath + "shopTypes.xml");
        readChainsFromXML(folderPath + "chains.xml");
        readShopsFromXML(folderPath + "shops.xml");
    }

    @Override
    public Shop[] getShops() {
        return shops;
    }

    @Override
    public ShopType[] getShopTypes() {
        return shopTypes;
    }

    @Override
    public Chain[] getChains() {
        return chains;
    }

    @Override
    public Product[] getProducts() {
        return products;
    }
}
