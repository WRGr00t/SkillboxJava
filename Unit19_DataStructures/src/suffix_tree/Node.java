package suffix_tree;

import java.util.ArrayList;
import java.util.List;

public class Node
{
    private String fragment;
    private ArrayList<Node> nextNodes;
    private int position;

    public Node(String fragment, int position)
    {
        this.fragment = fragment;
        this.nextNodes = new ArrayList<>();
        this.position = position;
    }

    public String getFragment()
    {
        return fragment;
    }

    public int getPosition()
    {
        return position;
    }

    public List<Node> getNextNodes()
    {
        return nextNodes;
    }

    public void setFragment(String fragment) {
        this.fragment = fragment;
    }

    public void setNextNodes(ArrayList<Node> nextNodes) {
        this.nextNodes = nextNodes;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}