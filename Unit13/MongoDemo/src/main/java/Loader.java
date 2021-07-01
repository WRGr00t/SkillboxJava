
import com.mongodb.client.*;
import com.mongodb.client.MongoClient;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.Arrays;
import java.util.Map;

public class Loader {
    public static void main(String[] args) {

        try (MongoClient mongoClient = MongoClients.create()) {
            MongoDatabase db = mongoClient.getDatabase("test");
            MongoCollection<Document> booksCollection = db.getCollection("books");
            booksCollection.drop();
            addAllBook(booksCollection);
            AggregateIterable<Document> docs = booksCollection.aggregate(Arrays.asList(
                    new Document("$group",
                            new Document("_id", "$" + "bookName").append("MinYear", new Document("$min", "$" + "year")))));
            for (Document document : docs) {
                System.out.println(document);
            }

        }
    }

        /*try (MongoClient mongoClient = MongoClients.create()) {
            var database = mongoClient.getDatabase("test");

            DBCollection booksCollection = database.getCollection("books");

            addAllBook(booksCollection);

            DBCursor cur = booksCollection.find();

            while(cur.hasNext()) {
                System.out.println(cur.next().get("name"));
            }

            BasicDBObject query = new BasicDBObject();

            query.put("year", new BasicDBObject("$bt", 25));

// получение объекта DBCursor на основе запроса
            FindIterable<Document> cur = booksCollection.find(query);



            booksCollection.find().sort({year:-1})
                    .forEach((Consumer<Document>) System.out::println);

        }
    }*/

    public static void addBook(MongoCollection<Document> booksCollection, String bookName, String author, String year) {
        var bookDocument = new Document(Map.of("_id", new ObjectId(),
                "bookName", bookName, "author", author, "year", year));
        booksCollection.insertOne(bookDocument);
    }

    public static void addAllBook(MongoCollection<Document> booksCollection) {
        addBook(booksCollection, "Алмазная колесница", "Б.Акунин", "2002");
        addBook(booksCollection, "А зори здесь тихие", "Б.Васильев", "1969");
        addBook(booksCollection, "12 стульев", "Е.Петров и И.Ильф", "1927");
        addBook(booksCollection, "Золотой теленок", "Е.Петров и И.Ильф", "1931");
        addBook(booksCollection, "Завтра была война", "Б.Васильев", "1984");
    }
}
