import org.bson.types.ObjectId;

public class Shop {
    String id;
    String name;
    String items;

    public Shop() {
    }

    public Shop(String name, String items) {
        this.name = name;
        this.items = items;
    }

    public Shop(String shopName) {
        this.id = new ObjectId().toString();
        this.name = shopName;
        this.items = "none";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }
}
