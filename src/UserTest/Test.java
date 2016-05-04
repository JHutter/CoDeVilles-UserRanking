package UserTest;

import ContainerClasses.TestItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by JHutter on 4/26/2016.
 */
public class Test extends ContainerClasses.Test{
    /* fields */
    private int turnTotal;
    private int currentTurn;
    private int itemTotal;
    private int testID;
    private ArrayList<TestItem> items;
    private ArrayList<ItemPair> pairs;


    /* constructor */
    public Test() {
        currentTurn = 0;
        items = getDBitems();
        itemTotal = items.size();
        pairs = generateItemPairs(items);
        turnTotal = pairs.size();
        /* TODO add field (collection of some sort) for recording scores */
    }

    private ArrayList<ItemPair> generateItemPairs(ArrayList<TestItem> items) {
        ArrayList<ItemPair> generatedPairs = new ArrayList<ItemPair>();
        for (int outer = 0; outer < itemTotal-1; outer++) {
            TestItem outerItem = items.get(outer);
            for (int inner = outer+1; inner < itemTotal; inner++) {
                TestItem innerItem = items.get(inner);
                ItemPair currentPair = new ItemPair(outerItem, innerItem);
                generatedPairs.add(currentPair);
            }
        }
        Collections.shuffle(generatedPairs);
        return generatedPairs;
    }


    /* methods */
    public ArrayList<TestItem> getDBitems() {
        //return new ArrayList<>(Arrays.asList("cat", "dog", "apple", "existential dread", "sunflower"));
        // TODO remove
        TestItem itema = new TestItem(1, "cat");
        TestItem itemb = new TestItem(2, "dog");
        ArrayList<TestItem> items = new ArrayList<>();
        items.add(itema);
        items.add(itemb);
        return items;
    }

    public void setDBItems(ArrayList<TestItem> testItems){
        for (TestItem item : items = testItems) {
            items.add(item);
        }
    }

    public void incrementTurn() {
        if (currentTurn < turnTotal) {
            currentTurn++;
        }
    }

    public int getCurrentTurn() {
        return currentTurn;
    }
    public int getTotalTurn() {
        return turnTotal;
    }
    public ItemPair getNextPair(int turn){
        if (turn <= turnTotal) {
            return pairs.get(turn);
        }
        return pairs.get(turnTotal);
    }

    public ArrayList<ItemPair> getPairs() {
        return pairs;
    }
}
