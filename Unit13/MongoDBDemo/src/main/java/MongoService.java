import com.mongodb.*;
import org.bson.types.ObjectId;

import java.net.UnknownHostException;
import java.util.function.Function;

public class MongoService {
    private MongoClient client;
    private DB db;
    private static MongoService service = new MongoService();

    public MongoService() {
        try {
            init();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static MongoService get() {
        return service;
    }

    private void init() throws UnknownHostException {
        this.client = new MongoClient("localhost", 27017);
    }

    public MongoService connectDb(String dbname) {
        if (db != null) {
            throw new RuntimeException("Already connected to " + db.getName() + "can't connect " + dbname);
        }
        this.db = client.getDB(dbname);
        System.out.println("DB Details :: " + db.getName());
        return service;
    }

    public <T,X> DBCursor findByKey(String collectionName, String key, T value, Function<T,X> convertDataType){
        DBCollection collection = db.getCollection(collectionName);
        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put(key, convertDataType.apply(value));
        System.out.println("search Query ::" + searchQuery);
        DBCursor cursor = collection.find(searchQuery);

        return cursor;
    }

    public DBCursor findByKeys(String collectionName, String key, String value, Function<String, String> convertDataType){
        DBCollection collection = db.getCollection(collectionName);
        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put( key, convertDataType.apply(value));
        System.out.println("search Query ::" + searchQuery);
        DBCursor cursor = collection.find(searchQuery);

        return cursor;
    }

    public DBCursor showExtreme(String collectionName, String key, int direction) {
        String result = "";
        if (direction == 1) {
            result = "Min = ";
        } else if (direction == -1) {
            result = "Max = ";
        } else {
            System.out.println("Wrong parameter");
        }
        DBCollection collection = db.getCollection(collectionName);
        BasicDBObject query = new BasicDBObject();
        query.append(key, direction);
        System.out.println("search Query ::" + result);
        DBCursor cursor = collection.find().sort(query).limit(1);
        return cursor;
    }

    public void createCollection(String collectionName){
        DBCollection collection = db.getCollection(collectionName);
        collection.drop();
        addAllBook(collection);
    }

    public static void addBook(DBCollection booksCollection, String bookName, String author, String year) {
        BasicDBObject document = new BasicDBObject();
        document.put("_id", new ObjectId());
        document.put("bookName", bookName);
        document.put("author", author);
        document.put("year", year);
        booksCollection.insert(document);
    }

    public static void addAllBook(DBCollection booksCollection) {
        addBook(booksCollection, "Алмазная колесница", "Б.Акунин", "2002");
        addBook(booksCollection, "А зори здесь тихие", "Б.Васильев", "1969");
        addBook(booksCollection, "12 стульев", "Е.Петров и И.Ильф", "1927");
        addBook(booksCollection, "Золотой теленок", "Е.Петров и И.Ильф", "1931");
        addBook(booksCollection, "Завтра была война", "Б.Васильев", "1984");
    }
}

