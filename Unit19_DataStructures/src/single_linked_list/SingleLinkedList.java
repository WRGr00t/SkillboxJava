package single_linked_list;

public class SingleLinkedList
{
    private ListItem top;

    public void push(ListItem item)
    {
        if(top != null) {
            item.setNext(top);
        }
        top = item;
    }

    public ListItem pop()
    {
        ListItem item = top;
        if(top != null)
        {
            top = top.getNext();
            item.setNext(null);
        }
        return item;
    }

    public void removeTop()
    {
        if(top != null) {
            top = top.getNext();
        }
    }

    public void removeLast() {
        if (top != null) {
            ListItem tmp = top;
            while (tmp.getNext() != null) {
                tmp = tmp.getNext();
                if (tmp.getNext().getNext() == null) {
                    tmp.setNext(null);
                }
            }
        }
    }

    public static void printList(SingleLinkedList list)
    {
        ListItem currNode = list.top;
        System.out.printf("Top item with data = %s%n", list.top.getData());
        System.out.print("LinkedList: ");
        while (currNode != null) {
            System.out.print(currNode.getData() + " ");
            currNode = currNode.getNext();
        }
    }
    public static void main(String[] args)
    {
        SingleLinkedList list = new SingleLinkedList();

        for (int i = 1; i < 10; i++) {
            list.push(new ListItem(String.valueOf(i)));
        }

        printList(list);
        System.out.println("\nRemove top");
        list.removeTop();
        printList(list);
        System.out.println("\nRemove last");
        list.removeLast();
        printList(list);
        System.out.println("\nRemove last");
        list.removeLast();
        printList(list);
        System.out.println("\nRemove last");
        list.removeLast();
        printList(list);
        System.out.println("\nRemove top");
        list.removeTop();
        printList(list);
    }
}