package binary_tree;

import java.util.ArrayList;
import java.util.List;

public class BinaryTree
{
    private Node root;

    public BinaryTree(Node root) {
        this.root = root;
    }

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }

    public void addNode(String data)
    {
        insert(root, data);
    }

    public Node insert(Node root, String s) {
        if (root == null) {
            return new Node(s);
        } else if (s.compareTo(root.getData()) > 0) {
            root.setRight(insert(root.getRight(), s));
        } else {
            root.setLeft(insert(root.getLeft(), s));
        }
        return root;
    }

    public static Node search(String str, Node node) {
        if (node == null || node.getData().equals(str)) {
            return node;
        } else if (node.getData().compareTo(str) > 0) {
            return search(str, node.getLeft());
        } else {
            return search(str, node.getRight());
        }
    }

    public List<Node> searchNodes(String data)
    {
        List<Node> resultList = new ArrayList<>();
        Node searchNode = search(data, getRoot());
        if (searchNode == null) {
            return null;
        } else {
            resultList.add(searchNode);
            while ((searchNode.getLeft() != null) && (search(data, searchNode.getLeft()) != null)) {
                if (searchNode.getLeft().getData().equals(data)){
                    resultList.add(searchNode.getLeft());
                    searchNode = searchNode.getLeft();
                }
            }
        }
        return resultList;
    }

    public static void inorder(Node root) {
        if (root != null) {
            inorder(root.getLeft());
            String str = String.format("[%s]%n", root.getData());
            System.out.print(str);
            inorder(root.getRight());
        }
    }

    public static void main(String[] args) {

        Node root = new Node("Китежин");
        BinaryTree bt = new BinaryTree(root);
        bt.addNode("Петров");
        bt.addNode("Петров");
        bt.addNode("Аганесян");
        bt.addNode("Сидорова");
        bt.addNode("Подопригора");
        bt.addNode("Симоченко");
        bt.addNode("Ягода");
        bt.addNode("Кастро");
        bt.addNode("Иванов");
        bt.addNode("Бубликов");
        bt.addNode("Петров");
        bt.addNode("Исаченко");
        bt.addNode("Петрищенко");
        System.out.println("List tree in order:");
        inorder(root);
        String search = "Петров";
        if (bt.searchNodes(search) == null){
            System.out.printf("Node with data = \"%s\" not found", search);
        } else {
            System.out.printf("%d nodes with data = \"%s\" were found in binary tree",
                    bt.searchNodes(search).size(), search);
        }
    }
}