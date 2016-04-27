package UserTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by JHutter on 4/26/2016.
 */
public class Test {
    /* fields */
    private int turnTotal;
    private int currentTurn;
    private int itemTotal;
    private int testID;
    private ArrayList<String> items;
    private ArrayList<ItemPair> pairs;


    /* constructor */
    public Test() {
        currentTurn = 0;
        items = getDBitems(testID);
        itemTotal = items.size();
        pairs = generateItemPairs(items);
        turnTotal = pairs.size();
    }

    private ArrayList<ItemPair> generateItemPairs(ArrayList<String> items) {
        ArrayList<ItemPair> generatedPairs = new ArrayList<ItemPair>();
        for (int outer = 0; outer < itemTotal-1; outer++) {
            String outerItem = items.get(outer);
            for (int inner = outer+1; inner < itemTotal; inner++) {
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

    public void incrementTurn() {
        currentTurn++;
    }

    public int getCurrentTurn() {
        return currentTurn;
    }
    public int getTotalTurn() {
        return turnTotal;
    }
}
