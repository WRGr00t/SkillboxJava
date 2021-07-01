import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

import java.net.UnknownHostException;

public class MongoStorage {
    private MongoClient client;
    private static DB db;
    private static MongoDatabase database;
    private static MongoStorage storage = new MongoStorage();

    public MongoClient getClient() {
        return client;
    }

    public void setClient(MongoClient client) {
        this.client = client;
    }

    public static DB getDb() {
        return db;
    }

    public void setDb(DB db) {
        this.db = db;
    }

    public static MongoDatabase getDatabase() {
        return database;
    }

    public static void setDatabase(MongoDatabase database) {
        MongoStorage.database = database;
    }

    public static MongoStorage getStorage() {
        return storage;
    }

    public static void setStorage(MongoStorage storage) {
        MongoStorage.storage = storage;
    }

    public MongoStorage() {
        try {
            init();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void init() throws UnknownHostException {
        this.client = new MongoClient();
    }

    public MongoStorage connectDb(String dbname) {
        if (db != null) {
            throw new RuntimeException("Already connected to " + db.getName() + "can't connect " + dbname);
        }
        db = client.getDB(dbname);
        System.out.println("DB Details :: " + db.getName());
        return storage;
    }

    public MongoStorage connectToMongoDatabase (String databaseName){
        if (database != null){
            throw new RuntimeException("Already connected to " + database.getName() + "can't connect " + databaseName);
        }
        database = client.getDatabase(databaseName);
        System.out.println("DB Details :: " + db.getName());
        return storage;
    }

    public DBCollection getCollection(String collectionName){
        return db.getCollection(collectionName);
    }

}



