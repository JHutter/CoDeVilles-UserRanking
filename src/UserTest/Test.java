package UserTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

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
        ArrayList<ItemPair> generatedPairs = new ArrayList<ItemPair>();
        for (int outer = 0; outer < itemCount-1; outer++) {
            String outerItem = items.get(outer);
            for (int inner = outer+1; inner < itemCount; inner++) {
                String innerItem = items.get(inner);
                ItemPair currentPair = new ItemPair(outerItem, innerItem);
                generatedPairs.add(currentPair);
            }
        }
        Collections.shuffle(generatedPairs);
        return generatedPairs;
    }


    /* methods */
    private ArrayList<String> getDBitems(int testID) {
        return new ArrayList<>(Arrays.asList("cat", "dog", "apple", "existential dread", "sunflower"));
    }

}
