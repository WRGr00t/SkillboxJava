import com.mongodb.DBCursor;

public class Loader {
    public static void main(String[] args) {
        MongoService service = MongoService.get().connectDb("test");
        service.createCollection("books");


        DBCursor result = service.findByKeys("books", "author", "Б.Васильев",
                (value) -> (value));
        while (result.hasNext()) {
            System.out.println(result.next());
        }

        result = service.showExtreme("books", "year", 1);
        while (result.hasNext()) {
            System.out.println(result.next());
        }
    }
}
