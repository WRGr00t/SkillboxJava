import au.com.bytecode.opencsv.CSVReader;
import com.mongodb.*;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.BasicQuery;

import java.io.*;
import java.net.UnknownHostException;
import java.util.ArrayList;
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
        this.client = new MongoClient();
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

    public DBCursor searchByCondition(String collectionName, String key, String condition, Function<String, String> convertDataType){
        DBCollection collection = db.getCollection(collectionName);
        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put( key, convertDataType.apply(condition));
        System.out.println("search Query ::" + searchQuery);
        DBCursor cursor = collection.find(searchQuery);

        return cursor;
    }

    public DBCursor showExtreme(String collectionName, String key, Direction dir) {
        String result = "";
        int direction = 0;
        if (dir == Direction.DIRECT_DOWN) {
            result = "Min = ";
            direction = 1;
        } else if (dir == Direction.DIRECT_UP) {
            result = "Max = ";
            direction = -1;
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

    public void createCollection(String collectionName, String path) throws IOException {
        DBCollection collection = db.getCollection(collectionName);
        collection.drop();
        addAllStudents(collection, path);
    }

    public static void addStudent(DBCollection booksCollection, Student student) {
        BasicDBObject document = new BasicDBObject();
        document.put("_id", new ObjectId());
        document.put("name", student.getName());
        document.put("age", student.getAge());
        document.put("courses", student.getCourses());
        booksCollection.insert(document);
    }

    public static void addAllStudents(DBCollection collection, String path) throws IOException {
        ArrayList<Student> students = readStudentsFromFile(path);
        students.forEach(student -> addStudent(collection, student));
    }

    public static ArrayList<Student> readStudentsFromFile(String path) throws IOException {
        ArrayList<Student> students = new ArrayList<>();
        int POSNAME = 0;
        int POSAGE = 1;
        int POSCOURSES = 2;
        try {
            FileReader fileReader = new FileReader(path);
            CSVReader reader = new CSVReader(fileReader, ',', '"', 0);
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                try {
                    String name = nextLine[POSNAME];
                    int age = Integer.parseInt(nextLine[POSAGE]);
                    String courses = nextLine[POSCOURSES];
                    students.add(new Student(name, age, courses));
                } catch (IndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return students;
    }

    public long getCount(String collectionName) {
        DBCollection collection = db.getCollection(collectionName);
        return collection.getCount();
    }

    public DBCursor printCollection(String collectionName) {
        DBCollection collection = db.getCollection(collectionName);
        BasicDBObject searchQuery = new BasicDBObject();
        System.out.println("search Query :: getAll");
        DBCursor cursor = collection.find();

        return cursor;
    }

    public DBCursor filterByAgeGT(String collectionName, int age){
        DBCollection collection = db.getCollection(collectionName);

        BasicDBObject gtQuery = new BasicDBObject();
        gtQuery.put("age", new BasicDBObject("$gt", age));

        BasicQuery queryDoc = new BasicQuery(gtQuery);
        System.out.println(queryDoc.toString());
        DBCursor cursor = collection.find(queryDoc.getQueryObject());
        return cursor;
    }

    public ArrayList<Student> getStudentsFromCursor(DBCursor cursor) {
        ArrayList<String> strings = new ArrayList<>();
        ArrayList<Student> students = new ArrayList<>();
        int POSNAMEINFRAGMENT = 3;
        int POSSUBFR = 1;
        int POSAGEINFRAGMENT = 4;
        int POSAGE = 0;
        int POSCORSINFRAGMENT = 5;

        while (cursor.hasNext()) {
            strings.add(cursor.next().toString());
        }
        String name = "";
        int age = 0;
        String courses = "";
        for (String string: strings){
            String[] split = string.split(":");
            String[] fragName = split[POSNAMEINFRAGMENT].split("\"");
            name = fragName[POSSUBFR].trim();
            String[] fragAge = split[POSAGEINFRAGMENT].split(",");
            age = Integer.parseInt(fragAge[POSAGE].trim());
            String[] fragCours = split[POSCORSINFRAGMENT].split("\"");
            courses = fragCours[POSSUBFR].trim();
            students.add(new Student(name, age, courses));
        }
        return students;
    }
}
