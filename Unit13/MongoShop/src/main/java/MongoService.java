import au.com.bytecode.opencsv.CSVReader;
import com.mongodb.*;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Consumer;

import static java.lang.Integer.*;


public class MongoService {
    private static MongoStorage storage = new MongoStorage();

    static String itemsColName = "items";
    static String shopsColName = "shops";

    public MongoService(MongoStorage storage) {
        MongoService.storage = storage;
        createCollection(itemsColName);
        createCollection(shopsColName);
    }

    public void createCollection(String collectionName) {
        DBCollection collection = storage.getDb().getCollection(collectionName);
        collection.drop();
        System.out.printf("Коллекция %s очищена и готова к заполнению\n", collectionName);
    }

    public void addItem(Item item) {
        DBCollection itemsCollection = storage.getDb().getCollection(itemsColName);
        BasicDBObject document = new BasicDBObject();
        document.put("_id", new ObjectId());
        document.put("name", item.getName());
        document.put("price", item.getPrice());
        document.put("description", item.getDescription());
        itemsCollection.insert(document);
    }

    public Item getItemFromName(String name) {
        DBCollection itemsCollection = storage.getDb().getCollection(itemsColName);
        Item item = new Item();
        BasicDBObject query = new BasicDBObject();
        query.put("name", name);
        DBObject object = itemsCollection.findOne(query);
        if (object != null) {
            item.setId(String.valueOf(object.get("_id")));
            item.setName(name);
            item.setPrice((int) object.get("price"));
            item.setDescription((String) object.get("description"));
        }
        return item;
    }

    public Shop getShopFromName(String name) {
        DBCollection shopsCollection = storage.getDb().getCollection(shopsColName);
        Shop shop = new Shop();
        BasicDBObject query = new BasicDBObject();
        query.put("name", name);
        DBObject object = shopsCollection.findOne(query);
        if (object != null) {
            shop.setId(String.valueOf(object.get("_id")));
            shop.setName(name);
            shop.setItems((String) object.get("items"));
        } else {
            System.out.println("Магазин не найден");
        }

        return shop;
    }

    public void addItemToShop(String itemName, String shopName) {

        DBCollection shopsCollection = storage.getDb().getCollection(shopsColName);
        BasicDBObject query = new BasicDBObject();
        BasicDBObject edit = new BasicDBObject();
        query.put("name", shopName);
        DBObject object = shopsCollection.findOne(query);
        if (object != null) {
            edit.put("name", shopName);
            if (object.get("items").equals("none")) {
                edit.put("items", itemName);
            } else {
                edit.put("items", object.get("items") + ", " + itemName);
            }
            shopsCollection.update(query, edit);
        }
    }

    public void setPriceToItem(String name, int price) {
        DBCollection itemsCollection = storage.getDb().getCollection(itemsColName);
        BasicDBObject query = new BasicDBObject();
        BasicDBObject edit = new BasicDBObject();
        query.put("name", name);
        DBObject object = itemsCollection.findOne(query);
        if (object != null) {
            edit.put("name", name);
            edit.put("price", price);
            edit.put("description", object.get("description"));
            itemsCollection.update(query, edit);
        }
    }

    public void editNameItem(String oldName, String newName) {
        DBCollection itemsCollection = storage.getDb().getCollection(itemsColName);
        BasicDBObject query = new BasicDBObject();
        BasicDBObject edit = new BasicDBObject();
        query.put("name", oldName);
        DBObject object = itemsCollection.findOne(query);
        if (object != null) {
            edit.put("name", newName);
            edit.put("price", object.get("price"));
            edit.put("description", object.get("description"));
            itemsCollection.update(query, edit);
        }
    }

    public void addShop(Shop shop) {
        DBCollection shopsCollection = storage.getDb().getCollection(shopsColName);
        BasicDBObject document = new BasicDBObject();
        document.put("_id", new ObjectId());
        document.put("name", shop.getName());
        document.put("items", shop.getItems());
        shopsCollection.insert(document);
    }

    public ArrayList<Shop> loadShopsFromFile() {
        ArrayList<Shop> shops = new ArrayList<>();
        String pathToShopCSV = "src/main/resources/shops.csv";
        int POSITION_NAME = 0;
        int POSITION_ITEMS = 1;
        try (FileReader fileReader = new FileReader(pathToShopCSV);
             CSVReader reader = new CSVReader(fileReader, ';', '"', 0)) {
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                try {
                    String name = nextLine[POSITION_NAME];
                    String items = nextLine[POSITION_ITEMS];
                    shops.add(new Shop(name, items));
                } catch (IndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return shops;
    }

    public void addAllShops() {
        DBCollection shopsCollection = storage.getDb().getCollection(shopsColName);
        long countBefore = shopsCollection.count();
        ArrayList<Shop> shops = loadShopsFromFile();
        shops.forEach(shop -> addShop(shop));
        System.out.printf("Успешно добавлено %d записей\n", shopsCollection.count() - countBefore);
    }

    public DBCursor getAllItems() {
        DBCollection itemsCollection = storage.getDb().getCollection(itemsColName);
        if (itemsCollection.getCount() > 0) {
            System.out.println("Коллекция " + itemsColName);
            System.out.println("search Query :: getAll");
        } else {
            System.out.println("Пустая коллекция " + itemsColName);
        }
        return itemsCollection.find();
    }

    public DBCursor getAllShops() {
        DBCollection collection = storage.getDb().getCollection(shopsColName);
        if (collection.getCount() > 0) {
            System.out.println("Коллекция " + shopsColName);
            System.out.println("search Query :: getAll");
        } else {
            System.out.println("Пустая коллекция " + shopsColName);
        }
        return collection.find();
    }

    public void printFromCursorAll(String string) {
        if (string.equals("items")) {
            DBCursor cursor = getAllItems();
            printFromCursor(cursor);
        } else if (string.equals("shops")) {
            DBCursor cursor = getAllShops();
            printFromCursor(cursor);
        }
    }

    public void printFromCursor(DBCursor cursor) {
        while (cursor.hasNext()) {
            System.out.println(cursor.next().toString());
        }
    }

    public ArrayList<Item> loadItemsFromFile(String pathToCSV) {
        ArrayList<Item> items = new ArrayList<>();
        int POSITION_NAME = 0;
        int POSITION_PRICE = 1;
        int POSITION_DESCR = 2;
        try (FileReader fileReader = new FileReader(pathToCSV);
             CSVReader reader = new CSVReader(fileReader, ';', '"', 0)) {
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                try {
                    String name = nextLine[POSITION_NAME];
                    int price = parseInt(nextLine[POSITION_PRICE]);
                    String description = nextLine[POSITION_DESCR];
                    items.add(new Item(name, price, description));
                } catch (IndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return items;
    }

    public void addAllItems() {
        String pathToItemsCSV = "src/main/resources/items.csv";
        DBCollection itemsCollection = storage.getDb().getCollection(itemsColName);
        long countBefore = itemsCollection.count();
        ArrayList<Item> items = loadItemsFromFile(pathToItemsCSV);
        items.forEach(item -> addItem(item));
        System.out.printf("Успешно добавлено %d записей\n", itemsCollection.count() - countBefore);
    }

    public void addItem(MongoStorage storage, String itemName, int price) {
        DBCollection collection = storage.getCollection(itemsColName);
        long countBefore = collection.getCount();
        addItem(new Item(itemName, price, "none"));
        System.out.printf("Успешно добавлено %d записей\n", collection.count() - countBefore);
    }

    public void addShop(MongoStorage storage, String shopName) {
        DBCollection collection = storage.getDb().getCollection(shopsColName);
        Shop shop = new Shop(shopName);
        BasicDBObject document = new BasicDBObject();
        document.put("_id", new ObjectId());
        document.put("name", shop.getName());
        document.put("items", shop.getItems());
        collection.insert(document);
        if (collection.find(document).count() > 0) {
            System.out.println("Магазин успешно добавлен...");
        } else {
            System.out.println("Ошибка при добавлении магазина...");
        }
    }

    public void printStat() {
        MongoStorage storage = MongoStorage.getStorage().connectToMongoDatabase("test");
        MongoCollection<Document> collection = storage.getDatabase().getCollection(itemsColName);
        System.out.printf("Общее число товаров в коллекции товаров = %d\n", collection.countDocuments());

        //db.items.aggregate( [ {$group :{ _id : "Items", avg_price: { $avg : "$price" }}} ] )

        AggregateIterable<Document> output = collection.aggregate(Arrays.asList(
                new Document("$group", new Document("_id", "items").append("avg_price", new Document("$avg", "$price")))));

        for (Document dbObject : output) {
            System.out.printf("Средняя цена товара в коллекции - %-10.2f\n", dbObject.get("avg_price"));
        }

        output = collection.aggregate(Arrays.asList(
                new Document("$group", new Document("_id", "items").append("max_price", new Document("$max", "$price")))));

        for (Document dbObject : output) {
            System.out.printf("Максимальная цена товара в коллекции - %d\nТовары с такой ценой:\n", dbObject.get("max_price"));
            printItemByPrice(Integer.parseInt(dbObject.get("max_price").toString()));

            /*DBCursor cursor = findItems("price", Integer.parseInt(dbObject.get("max_price").toString()));
            if (cursor.count() == 0) {
                System.out.println("Ничего не найдено");
            } else {
                printFromCursor(cursor);
            }*/
        }

        output = collection.aggregate(Arrays.asList(
                new Document("$group", new Document("_id", "items").append("min_price", new Document("$min", "$price")))));

        for (Document dbObject : output) {
            System.out.printf("Минимальная цена товара в коллекции - %d\nТовары с такой ценой:\n", dbObject.get("min_price"));
            printItemByPrice(Integer.parseInt(dbObject.get("min_price").toString()));
        }

        System.out.println("Список товаров с ценой более 100:");
        printItemByCondition(100);
    }

    public DBCursor findItems(String key, int value) {
        DBCollection itemsCollection = storage.getDb().getCollection(itemsColName);
        BasicDBObject query = new BasicDBObject();
        query.put(key, value);
        DBCursor cursor = itemsCollection.find(query);
        return cursor;
    }

    public void printItemByPrice(int price) {
        MongoCollection<Document> collection = storage.getDatabase().getCollection(itemsColName);
        BsonDocument query = BsonDocument.parse("{price: {$eq: " + price + "}}");
        collection.find(query).forEach((Consumer<Document>) System.out::println);
    }

    public void printItemByCondition(int condition) {
        MongoCollection<Document> collection = storage.getDatabase().getCollection(itemsColName);
        BsonDocument query = BsonDocument.parse("{price: {$gte: " + condition + "}}");
        collection.find(query).forEach((Consumer<Document>) System.out::println);
    }
}
