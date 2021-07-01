package rabin_karp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

public class RabinKarp
{
    public final static int d = 256;
    private String text;
    private TreeMap<Integer, Integer> number2position;

    public RabinKarp(String text)
    {
        this.text = text;
        createIndex();
    }

    public List<Integer> search(String query)
    {
        int M = query.length();
        int N = text.length();
        int i, j;
        int patternHash = 0;
        int textHash = 0;
        int h = 1;
        int q = 2 ^ 64 - 1; // Простое число
        ArrayList<Integer> indices = new ArrayList<>();
        for (i = 0; i < M - 1; i++) {
            h = (h * d) % q;
        }
        for (i = 0; i < M; i++) {
            patternHash = (d * patternHash + query.charAt(i)) % q;
            textHash = (d * textHash + text.charAt(i)) % q;
        }

        for (i = 0; i <= N - M; i++) {
            if (patternHash == textHash) {
                for (j = 0; j < M; j++) {
                    if (text.charAt(i + j) != query.charAt(j)) {
                        break;
                    }
                }
                if (j == M) {
                    indices.add(i);
                }
            }
            if (i < N - M) {
                textHash = (d * (textHash - text.charAt(i) * h) + text.charAt(i + M)) % q;
                if (textHash < 0) {
                    textHash = (textHash + q);
                }
            }
        }
        return indices;
    }

    private void createIndex()
    {
        //TODO: implement text indexing
    }

    public static void main(String[] args) {
        String txt = " example for examplexample";
        String pattern = "example";
        RabinKarp searchRK = new RabinKarp(txt);
        ArrayList<Integer> result = (ArrayList<Integer>) searchRK.search(pattern);
        System.out.printf("Find pattern \'%s\' in text \'%s\'%n", pattern, txt);
        for (Integer integer : result) {
            System.out.printf("Pattern found at index %d%n", integer);
        }
    }
}