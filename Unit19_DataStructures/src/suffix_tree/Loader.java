package suffix_tree;

import java.util.List;

public class Loader {
    public static void main(String[] args) {
        SuffixTree suffixTree = new SuffixTree("mississippi");
        List<String> matches = suffixTree.search("i");
        matches.stream().forEach(m -> System.out.println(m));
    }
}
