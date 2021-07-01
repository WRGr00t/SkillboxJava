package suffix_tree;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SuffixTree {
    private static final String WORD_TERMINATION = "$";
    private static final int POSITION_UNDEFINED = -1;

    private String text;
    private Node root;

    public SuffixTree(String text) {
        root = new Node("", POSITION_UNDEFINED);
        for (int i = 0; i < text.length(); i++) {
            addSuffix(text.substring(i) + WORD_TERMINATION, i);
        }
        this.text = text;
    }

    private void addChildNode(Node parentNode, String text, int index) {
        parentNode.getNextNodes().add(new Node(text, index));
    }

    private String getLongestCommonPrefix(String str1, String str2) {
        int compareLength = Math.min(str1.length(), str2.length());
        for (int i = 0; i < compareLength; i++) {
            if (str1.charAt(i) != str2.charAt(i)) {
                return str1.substring(0, i);
            }
        }
        return str1.substring(0, compareLength);
    }

    private void splitNodeToParentAndChild(Node parentNode, String parentNewText, String childNewText) {
        Node childNode = new Node(childNewText, parentNode.getPosition());

        if (parentNode.getNextNodes().size() > 0) {
            while (parentNode.getNextNodes().size() > 0) {
                childNode.getNextNodes()
                        .add(parentNode.getNextNodes().remove(0));
            }
        }

        parentNode.getNextNodes().add(childNode);
        parentNode.setFragment(parentNewText);
        parentNode.setPosition(POSITION_UNDEFINED);
    }

    private List<Node> getAllNodesInTraversePath(String pattern, Node startNode, boolean isAllowPartialMatch) {
        List<Node> nodes = new ArrayList<>();
        for (int i = 0; i < startNode.getNextNodes().size(); i++) {
            Node currentNode = startNode.getNextNodes().get(i);
            String nodeText = currentNode.getFragment();
            if (pattern.charAt(0) == nodeText.charAt(0)) {
                if (isAllowPartialMatch && pattern.length() <= nodeText.length()) {
                    nodes.add(currentNode);
                    return nodes;
                }

                int compareLength = Math.min(nodeText.length(), pattern.length());
                for (int j = 1; j < compareLength; j++) {
                    if (pattern.charAt(j) != nodeText.charAt(j)) {
                        if (isAllowPartialMatch) {
                            nodes.add(currentNode);
                        }
                        return nodes;
                    }
                }

                nodes.add(currentNode);
                if (pattern.length() > compareLength) {
                    List<Node> nodes2 = getAllNodesInTraversePath(pattern.substring(compareLength),
                            currentNode, isAllowPartialMatch);
                    if (nodes2.size() > 0) {
                        nodes.addAll(nodes2);
                    } else if (!isAllowPartialMatch) {
                        nodes.add(null);
                    }
                }
                return nodes;
            }
        }
        return nodes;
    }

    private void build() {
        //TODO
    }
    private void extendNode(Node node, String newText, int position) {
        String currentText = node.getFragment();
        String commonPrefix = getLongestCommonPrefix(currentText, newText);

        if (commonPrefix != currentText) {
            String parentText = currentText.substring(0, commonPrefix.length());
            String childText = currentText.substring(commonPrefix.length());
            splitNodeToParentAndChild(node, parentText, childText);
        }

        String remainingText = newText.substring(commonPrefix.length());
        addChildNode(node, remainingText, position);
    }

    private void addSuffix(String suffix, int position) {
        List<Node> nodes = getAllNodesInTraversePath(suffix, root, true);
        if (nodes.size() == 0) {
            addChildNode(root, suffix, position);
        } else {
            Node lastNode = nodes.remove(nodes.size() - 1);
            String newText = suffix;
            if (nodes.size() > 0) {
                String existingSuffixUptoLastNode = nodes.stream()
                        .map(a -> a.getFragment())
                        .reduce("", String::concat);
                newText = newText.substring(existingSuffixUptoLastNode.length());
            }
            extendNode(lastNode, newText, position);
        }
    }

    private List<Integer> getPositions(Node node) {
        List<Integer> positions = new ArrayList<>();
        if (node.getFragment().endsWith(WORD_TERMINATION)) {
            positions.add(node.getPosition());
        }
        for (int i = 0; i < node.getNextNodes().size(); i++) {
            positions.addAll(getPositions(node.getNextNodes().get(i)));
        }
        return positions;
    }

    private String markPatternInText(Integer startPosition, String pattern) {
        String matchingTextLHS = text.substring(0, startPosition);
        String matchingText = text.substring(startPosition, startPosition + pattern.length());
        String matchingTextRHS = text.substring(startPosition + pattern.length());
        return matchingTextLHS + "[" + matchingText + "]" + matchingTextRHS;
    }

    public List<String> search(String query) {
        ArrayList<String> positions = new ArrayList<>();
        List<Node> nodes = getAllNodesInTraversePath(query, root, false);

        if (nodes.size() > 0) {
            Node lastNode = nodes.get(nodes.size() - 1);
            if (lastNode != null) {
                List<Integer> pos = getPositions(lastNode);
                pos = pos.stream()
                        .sorted()
                        .collect(Collectors.toList());
                pos.forEach(m -> positions.add((markPatternInText(m, query))));
            }
        }
        return positions;
    }
}