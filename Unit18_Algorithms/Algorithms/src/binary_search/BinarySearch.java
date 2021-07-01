package binary_search;

import java.util.ArrayList;

public class BinarySearch
{
    private ArrayList<String> list;

    public BinarySearch(ArrayList<String> list)
    {
        this.list = list;
        list.sort(String::compareTo);
    }

    public int search(String query)
    {
        return search(query, 0, list.size() - 1);
    }

    private int search(String query, int from, int to)
    {
        int low = from;
        int high = to;

        while (low <= high) {
            int mid = (low + high) >>> 1;
            String midValue = list.get(mid);
            int cmp = midValue.compareTo(query);

            if (cmp < 0)
                low = mid + 1;
            else if (cmp > 0)
                high = mid - 1;
            else
                return mid;
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] array = new int[] {10, 20, 55, 120, 5, 55, 15, 200, 12, 88, 100, 119};

        ArrayList<String> words = new ArrayList<>();
        for (int num : array){
            words.add(Integer.toString(num));
        }
        String valueForSearch = "100";
        BinarySearch binarySearch = new BinarySearch(words);
        int pos = binarySearch.search(valueForSearch);
        if (pos > -1) {
            System.out.printf("String %s found in sorted set at position %d%n", valueForSearch, pos + 1);
        } else {
            System.out.println("String not found");
        }
        for (String word : words) {
            System.out.printf("%s ", word);
        }
    }
}
