package double_linked_list;

import java.util.NoSuchElementException;

public class DoubleLinkedList
{
    private ListItem head;
    private ListItem tail;

    public ListItem getHeadElement()
    {
        return head;
    }

    public ListItem getTailElement()
    {
        return tail;
    }

    public boolean isEmpty() {
        return (head == null);
    }

    public ListItem popHeadElement()
    {
        final ListItem f = head;
        if (f == null) {
            throw new NoSuchElementException();
        }
        final String element = f.getData();
        final ListItem next = f.getNext();
        head = next;
        if (next == null) {
            tail = null;
        } else {
            next.setPrev(null);
        }
        return new ListItem(element);
    }

    public ListItem popTailElement()
    {
        final ListItem last = tail;
        if (last == null) {
            throw new NoSuchElementException();
        }
        final String element = last.getData();
        final ListItem prev = last.getPrev();
        tail = prev;
        if (prev == null) {
            tail = null;
        } else {
            prev.setNext(null);
        }
        return new ListItem(element);
        /*if (tail != null) {
            ListItem temp = tail;
            tail = tail.getPrev();
            tail.setNext(null);
            return temp;
        } else {
            return null;
        }*/

    }

    public void removeHeadElement()
    {
        popHeadElement();
    }

    public void removeTailElement()
    {
        popTailElement();
    }

    public void addToHead(ListItem item)
    {
        item.setNext(head);
        item.setPrev(null);
        if (head != null)
            head.setPrev(item);
        head = item;
        if (tail == null)
            tail = item;
    }

    public void addToTail(ListItem item)
    {
        item.setNext(null);
        item.setPrev(tail);
        if (tail != null)
            tail.setNext(item);
        tail = item;
        if (head == null) {
            head = item;
        }
    }

    public void printLinkedListForward() {
        if (head == null && tail == null) {
            System.out.println("List empty");
        } else {
            System.out.println("Printing Double LinkedList (head >>> tail) ");
            ListItem current = head;
            while (current != null) {
                current.printItemData();
                current = current.getNext();
            }
            System.out.println();
        }
    }

    public void printLinkedListBackward() {
        if (head == null && tail == null) {
            System.out.println("List empty");
        } else {
            System.out.println("Printing Double LinkedList (tail >>> head) ");
            ListItem current = tail;
            while (current != null) {
                current.printItemData();
                current = current.getPrev();
            }
            System.out.println();
        }
    }

    private static final ListItem A = new ListItem("a");
    private static final ListItem B = new ListItem("b");

    public static void main(String[] args) {
        DoubleLinkedList newList = new DoubleLinkedList();

        newList.addToHead(new ListItem("Петров"));
        newList.addToHead(new ListItem("Петрищенко"));
        newList.addToHead(new ListItem("Исаченко"));
        newList.addToTail(new ListItem("Васин"));
        newList.addToTail(new ListItem("Васин"));

        newList.printLinkedListForward();
        newList.printLinkedListBackward();
        System.out.println("Delete head item...");
        newList.popHeadElement();
        newList.printLinkedListForward();
        newList.printLinkedListBackward();

        System.out.println("Delete tail item...");
        newList.popTailElement();
        newList.printLinkedListForward();
        newList.printLinkedListBackward();

        DoubleLinkedList l = new DoubleLinkedList();

        l.addToHead(B);
        l.addToHead(A);
        l.removeHeadElement();
        l.popHeadElement();
        l.printLinkedListForward();
    }
}