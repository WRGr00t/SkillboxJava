package double_linked_list;

public class ListItem
{
    private String data;
    private ListItem prev;
    private ListItem next;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public ListItem getNext() {
        return next;
    }

    public void setNext(ListItem next) {
        this.next = next;
    }

    public ListItem getPrev() {
        return prev;
    }

    public void setPrev(ListItem prev) {
        this.prev = prev;
    }

    public void printItemData() {
        System.out.println("[" + data + "]");
    }

    public ListItem(String data) {
        this.data = data;
        this.prev = null;
        this.next = null;
    }
}

