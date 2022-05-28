import de.lonci.application.Application;
import de.lonci.application.UserInterface;
import de.lonci.domain.ShoppingList;
import de.lonci.domain.ShoppingListBuilder;
import mocks.MockDataProvider;
import mocks.MockRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApplicationTests {

        private static MockRepository repository;
        private static MockDataProvider dataProvider;
        private static Application application;

        @BeforeAll
        public static void setup() {
                // Create test application
                var dummyUi = new UserInterface() {
                        @Override
                        public void create(Application application) {
                                // Do nothing
                        }
                };
                dataProvider = new MockDataProvider();
                repository = new MockRepository();
                application = new Application(dataProvider, dummyUi, repository);
                application.start();
        }

        @Test
        public void testCreateShoppingList() {
                // Arrange
                repository.clear();
                var name = "Test Shopping List";

                // Act
                application.createNewShoppingList(name);

                // Assert
                assertEquals(1, repository.getAll().size());
                assertEquals(name, repository.getAll().get(0).getName());
        }

        @Test
        public void testDeleteShoppingList() {
                // Arrange
                repository.clear();
                repository.save(new ShoppingListBuilder("test").build());

                // Act
                application.deleteShoppingList("test");

                // Assert
                assertEquals(0, repository.getAll().size());
        }

        @Test
        public void testFindProductWithQuery() {
                // Arrange
                var query = "toff";

                // Act
                var result = application.matchProduct(query);

                // Assert
                assertEquals(1, result.size());
        }

        @Test
        public void testAddProductWithoutStore() {
                // Arrange
                var product = dataProvider.getProducts()[0];
                var shoppingList = application.createNewShoppingList("TestEmpty");

                // Act
                var result = application.tryAddProductToShoppingList(product);

                // Assert
                assertEquals(false, result);
        }

        @Test
        public void testAddProductWithStore() {
                // Arrange
                var product = dataProvider.getProducts()[0];
                var shoppingList = application.createNewShoppingList("TestStore");
                var store = application.getOrAddShoppingListStore(dataProvider.getShops()[0]);

                // Act
                var result = application.tryAddProductToShoppingList(product);

                // Assert
                assertEquals(true, result);
                assertEquals(1, store.getProducts().size());
        }

        @Test
        public void testRemoveEmptyStores() {
                // Arrange
                var shoppingList = application.createNewShoppingList("TestEmptyStore");
                var store = application.getOrAddShoppingListStore(dataProvider.getShops()[0]);

                // Act
                application.removeEmptyShoppingListStores();

                // Assert
                assertEquals(0, shoppingList.getShoppingListStores().size());
        }

        @Test
        public void testGetOrAddStore() {
                // Arrange
                var shoppingList = application.createNewShoppingList("TestGetAddStore");

                // Act
                assertEquals(0, shoppingList.getShoppingListStores().size());

                var store = application.getOrAddShoppingListStore(dataProvider.getShops()[0]);

                assertEquals(1, shoppingList.getShoppingListStores().size());
                assertEquals(store.getShop(), dataProvider.getShops()[0]);
        }

        @Test
        public void testShopsFromChain() {
                // Arrange
                var chain = dataProvider.getChains()[0];

                // Act
                var shops = application.getShopsFromChain(chain);

                // Assert
                assertEquals(1, shops.size());
                assertEquals(dataProvider.getShops()[0], shops.get(0));
        }

        @Test
        public void testRemoveProduct() {
                // Arrange
                var product = dataProvider.getProducts()[0];
                var shoppingList = application.createNewShoppingList("TestRemoveProduct");
                var store = application.getOrAddShoppingListStore(dataProvider.getShops()[0]);
                store.addProduct(product);

                // Act
                application.removeProductFromShoppingList(product);

                // Assert
                assertEquals(0, store.getProducts().size());
        }

        @Test
        public void testChainsOfferingProduct() {
                // Arrange
                var product = dataProvider.getProducts()[0];

                // Act
                var chains = application.getChainsOfferingProduct(product);

                // Assert
                assertEquals(1, chains.size());
                assertEquals(dataProvider.getChains()[0], chains.get(0));
        }
}
