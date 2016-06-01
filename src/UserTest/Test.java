package UserTest;

import ContainerClasses.TestItem;
import ContainerClasses.TestResult;
import DaoClasses.DAOFactory;
import SharedFunctions.DatabaseManager;

import java.util.*;

/**
 * This class models single user account.
 * CIS 234A Dougherty
 * Creation Date: 4/26/2016
 *
 *  @author JoAnne
 *  @version 2016.5.24
 *
 *  Modifications:
 *  2016.5.24:
 *      Fixed bug in constructor
 *      Added recentItems and checkRecentItems for sprint2 (item ordering story)
 */
public class Test extends ContainerClasses.Test{
    /* fields */
    private int turnTotal;
    private int currentTurn;
    private int itemTotal;
    private int testID;
    private float progress;
    private ArrayList<TestItem> items;
    private ArrayList<ItemPair> pairs;
    private ArrayList<TestResult> results;
    private ArrayList<TestItem> recentItems;


    /* constructors */

    /**
     * Constructor with one parameter testID
     * @param testID the ID of the new test
     */
    public Test(int testID) {
        currentTurn = 0;
        progress = 0;
        setTestID(testID);
        items = DAOFactory.getTestItemDAO().getTestItems(testID);
        if (items.size() == 0){
            setBackupItems();
        }
        else {
            setDBItems(items);
        }
        results = new ArrayList<>();
    }

    /**
     * Constructor with two parameters, testID and testName
     * @param testID the ID of the test
     * @param testName the name of the test
     */
    public Test(int testID, String testName) {
        currentTurn = 0;
        progress = 0;
        setTestID(testID);
        setTestName(testName);
        items = DAOFactory.getTestItemDAO().getTestItems(testID);
        if (items.size() == 0){
            setBackupItems();
        }
        else {
            setDBItems(items);
        }
        results = new ArrayList<>();
    }

    /* methods */
    /**
     * generateItemPairs
     * method that given a list of items, shuffles them to create a list
     * of item pairs that matches every item against every other item
     * @param items the list of TestITems
     * @return a list of shuffled ItemPairs
     */
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

    /**
     * getItems
     * returns the list of TestItems
     * @return list of TestItems
     */
    public ArrayList<TestItem> getItems() {
        //return database.getTestItems(testID);
        return items;
    }

    /**
     * setBackupItems
     * sets a list of dummy testItems
     * for testing, not production use
     */
    public void setBackupItems() {
        //ArrayList<String> stringItems = new ArrayList<String>(Arrays.asList("Tablet", "PC - Windows", "PC - Mac", "Smartphone", "Brick Phone", "Wristwatch", "VR Set"));
        ArrayList<String> stringItems = new ArrayList<String>(Arrays.asList("Tablet", "PC - Windows", "PC - Mac", "Smartphone"));
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


    /**
     * setDBItems
     * given a list of testItems from the DB, set those items and generate pairs
     * @param testItems
     */
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

    /**
     * incrementTurn
     * increases the current turn
     */
    public void incrementTurn() {
        if (currentTurn < turnTotal) {
            currentTurn++;
        }
    }

    /**
     * getCurrentTurn
     * getter for currentTurn
     * @return currentTurn
     */
    public int getCurrentTurn() {
        return currentTurn;
    }

    /**
     * getTotalTurn
     * getter for turnTotal
     * @return turnTotal, the total number of turns
     */
    public int getTotalTurn() {
        return turnTotal;
    }
    public ItemPair getNextPair(int turn){
        if (turn <= turnTotal) {
            return pairs.get(turn);
        }
        return pairs.get(turnTotal);
    }

    /**
     * getPairs
     * getter for list of ItemPairs
     * @return pairs, list of ItemPairs
     */
    public ArrayList<ItemPair> getPairs() {
        return pairs;
    }

    /**
     * getItemByText
     * getter for item, given a particular text and return the item with that text
     * @param text, the text of the item
     * @return
     */
    public TestItem getItemByText(String text) {
        for (TestItem item : items) {
            if (text.equals(item.getItemText())) {
                return item;
            }
        }
        return null;
    }

    /**
     * recordResult
     * given the result information, add that result to the list of TestResults
     * @param newQuestionNumber, first itemID
     * @param newItemID, second itemID, which newQuestionNumber was matched against
     * @param newSessionID, the ID of the test session
     * @param newResult, the result of the matchup
     */
    public void recordResult(int newQuestionNumber, int newItemID, int newSessionID, int newResult) {
        results.add(new TestResult(newQuestionNumber, newItemID, newSessionID, newResult));

    }

    /**
     * writeResults
     * write the list of TestResults to the database after the test
     */
    public void writeResults() {
        //database = new DatabaseManager();
        for (TestResult result : results) {
            //database.insertResult(result.getQuestionNumber(), result.getItemID(), result.getSessionID(), result.getResult());
        }
    }

    /**
     * getResults
     * getter for list of TestResults
     * @return list of TestResults
     */
    public ArrayList<TestResult> getResults() {
        return results;
    }

    /**
     * setRecentItems
     * clears the recent items list
     * adds the 2 items from the last turn the recent items list
     * @param itemPair
     */
    public void setRecentItems(ItemPair itemPair) {
        recentItems.clear();
        recentItems.add(itemPair.getItem1());
        recentItems.add(itemPair.getItem2());
    }

    /**
     * checkRecentItems
     * using the current turn and recent items,
     * checks the item pair at the index of current turn
     * if the current turn == total turns or first turn, do nothing
     * else check the
     */
    public void checkRecentItems(){
        if (currentTurn == 0 || currentTurn == turnTotal){
            return;
        }
        else {
            // check the itemPair at index currentTurn
            int shuffleCount = 0;
            while (recentItems.contains(pairs.get(currentTurn).getItem1()) ||
                    recentItems.contains(pairs.get(currentTurn).getItem2())){
                shuffleSubList(currentTurn, turnTotal);
                shuffleCount++;
                if (shuffleCount == 5) { // if we haven't gotten a fresh pair yet, don't keep shuffling
                    break;
                }

            }
        }
    }

    private void shuffleSubList(int start, int stop){
        Collections.shuffle(pairs.subList(start, start));
    }

    public void calculateProgress(){
        progress = ((float)getCurrentTurn()/(float)getTotalTurn()) * 100;
        return;
    }

    public float getProgress(){
        calculateProgress();
        return progress;
    }



}
