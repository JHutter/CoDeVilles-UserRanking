package UserTest;

import ContainerClasses.TestItem;
import ContainerClasses.TestResult;
import SharedFunctions.DatabaseManager;

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
    private DatabaseManager database;
    private ArrayList<TestResult> results;


    /* constructor */
    public Test(int testID) {
        currentTurn = 0;
        this.testID = testID;
        items = new ArrayList<TestItem>();
        DatabaseManager database = new DatabaseManager();
        setDBItems(database.getTestItems(testID));
        if (itemTotal == 0) {
            setBackupItems();
        }

        results = new ArrayList<>();
        /* TODO add field (collection of some sort) for recording scores */
    }

    private ArrayList<ItemPair> generateItemPairs(ArrayList<TestItem> items) {
        ArrayList<ItemPair> generatedPairs = new ArrayList<ItemPair>();
        for (int outer = 0; outer < itemTotal-1; outer++) {
            TestItem outerItem = items.get(outer);
            for (int inner = outer+1; inner < itemTotal; inner++) {
                TestItem innerItem = items.get(inner);
                generatedPairs.add(new ItemPair(outerItem, innerItem));
            }
        }
        Collections.shuffle(generatedPairs);
        return generatedPairs;
    }


    /* methods */
    public ArrayList<TestItem> getItems() {
        //return database.getTestItems(testID);
        return items;
    }

    public void setBackupItems() {
        ArrayList<String> stringItems = new ArrayList<String>(Arrays.asList("Tablet", "PC - Windows", "PC - Mac", "Smartphone", "Raspberry Pi"));
        int testID = 0;
        int itemID = 0;
        for (String stringItem : stringItems) {
            items.add(new TestItem(itemID, testID, stringItem));
        }
        itemTotal = items.size();
        pairs = generateItemPairs(items);
        turnTotal = pairs.size();
        //return database.getTestItems(testID);
        return;
    }


    public void setDBItems(ArrayList<TestItem> testItems){
        /*for (TestItem item : items = testItems) {
            items.add(item);
        }*/
        items = null;
        items = testItems;
        generateItemPairs(items);
        itemTotal = items.size();
        pairs = generateItemPairs(items);
        turnTotal = pairs.size();
        //items.addAll(testItems);
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

    public TestItem getItemByText(String text) {
        for (TestItem item : items) {
            if (text.equals(item.getItemText())) {
                return item;
            }
        }
        return null;
    }

    public void recordResult(int newQuestionNumber, int newItemID, int newSessionID, int newResult) {
        results.add(new TestResult(newQuestionNumber, newItemID, newSessionID, newResult));

    }

    public void writeResults() {
        database = new DatabaseManager();
        for (TestResult result : results) {
            database.insertResult(result.getQuestionNumber(), result.getItemID(), result.getSessionID(), result.getResult());
        }
    }
    public ArrayList<TestResult> getResults() {
        return results;
    }


}
