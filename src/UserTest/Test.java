package UserTest;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by JHutter on 4/26/2016.
 */
public class Test {
    /* fields */
    private int turnCount;
    private int itemCount;
    private int testID;
    private ArrayList<String> items;
    private ArrayList<ItemPair> pairs;


    /* constructor */
    public Test() {
        items = getDBitems(testID);
        pairs = generateItemPairs(items);
        itemCount = items.size();
        turnCount = pairs.size();
    }

    private ArrayList<ItemPair> generateItemPairs(ArrayList<String> items) {
        return new ArrayList<ItemPair>();
    }


    /* methods */
    private ArrayList<String> getDBitems(int testID) {
        return new ArrayList<>(Arrays.asList("cat", "dog", "apple", "existential dread", "sunflower"));
    }

}
