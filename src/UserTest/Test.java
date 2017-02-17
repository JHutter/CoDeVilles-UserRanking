package UserTest;

import ContainerClasses.TestItem;
import ContainerClasses.TestResult;
import DaoClasses.DAOFactory;
import DaoClasses.TestResultDAO;

import javax.swing.*;
import java.lang.reflect.Array;
import java.util.*;

/**
 * This class models single user account.
 * CIS 234A Dougherty
 * Creation Date: 4/26/2016
 *
 *  @author JHutter
 *  @version 2016.5.31
 *
 *  Modifications:
 *  2016.5.24:
 *      Fixed bug in constructor
 *      Added recentItems and checkRecentItems for sprint2 (item ordering story)
 * 2016.5.31:
 *      Changed generatePairs
 *      Added addRecent
 *      Added methods for progress bar (calculate progress, get progress, etc)
 *      Added progress field for progress bar
 *      Refactored to use DAO
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
        recentItems = new ArrayList<>();
        setTestID(testID);
        items = new ArrayList<>();
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
        recentItems = new ArrayList<>();
        setTestID(testID);
        setTestName(testName);
        items = new ArrayList<>();
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
        ArrayList<ItemPair> generatedPairs = new ArrayList<>();
        ItemPair currentPair;
        Boolean recentMatch;
        int count = 0;

        for (int outer = 0; outer < itemTotal-1; outer++) {
            TestItem outerItem = items.get(outer);
            for (int inner = outer+1; inner < itemTotal; inner++) {
                TestItem innerItem = items.get(inner);
                generatedPairs.add(new ItemPair(outerItem, innerItem));
            }
        }
        Collections.shuffle(generatedPairs);
        for (int i=0; i < generatedPairs.size(); i++){
            if (i == 0){
                addRecent(generatedPairs.get(i));
            }
            else {
                count = 0;
                currentPair = generatedPairs.get(i);
                recentMatch = (recentItems.contains(currentPair.getItem1()) || recentItems.contains(currentPair.getItem2()));
                if (recentMatch) {
                    for (int j = i + 1; j < generatedPairs.size(); j++) {
                        recentMatch = (recentItems.contains(generatedPairs.get(j).getItem1()) || recentItems.contains(generatedPairs.get(j).getItem2()));
                        if (!recentMatch) {
                            Collections.swap(generatedPairs, i, j);
                            break;
                        }
                    }
                }


                /*while (recentMatch) {
                    if (count > 100){ // too many iterations...
                        break;
                    }
                    Collections.shuffle(generatedPairs.subList(i, generatedPairs.size()));
                    currentPair = generatedPairs.get(i);
                    recentMatch = (recentItems.contains(currentPair.getItem1()) || recentItems.contains(currentPair.getItem2()));
                    count++;
                }*/
                addRecent(generatedPairs.get(i));
            }
        }
        return generatedPairs;
    }

    private void addRecent(ItemPair pair){
        if (items.size() <= 3){ // if it's a short list of items, not applicable
            return;
        }
        if (recentItems.size() == 0) {
            recentItems.add(pair.getItem1());
            recentItems.add(pair.getItem2());
        }
        else {
            if (!recentItems.contains(pair.getItem1())){
                recentItems.add(0,pair.getItem1());
            }
            if (!recentItems.contains(pair.getItem2())){
                recentItems.add(0,pair.getItem2());
            }
            if (recentItems.size() >= items.size()-1){
                recentItems.remove(0);
                recentItems.remove(0);
            }
        }
    }

    /**
     * getItems
     * returns the list of TestItems
     * @return list of TestItems
     */
    public ArrayList<TestItem> getItems() {
        return items;
    }

    /**
     * setBackupItems
     * sets a list of dummy testItems
     * for testing, not production use
     */
    private void setBackupItems() {
        ArrayList<String> stringItems = new ArrayList<>(Arrays.asList("A", "B", "C", "D", "E", "F", "G"));
        //ArrayList<String> stringItems = new ArrayList<String>(Arrays.asList("Tablet", "PC - Windows", "PC - Mac", "Smartphone"));
        int testID = 0;
        int itemID = 0;
        for (String stringItem : stringItems) {
            items.add(new TestItem(itemID, testID, stringItem));
        }
        itemTotal = items.size();
        pairs = generateItemPairs(items);
        turnTotal = pairs.size();
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

    public ArrayList<TestItem> getRecentItems() { return recentItems; }

    /**
     * getItemByText
     * getter for item, given a particular text and return the item with that text
     * @param text, the text of the item
     * @return return an item based on the text of the item
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
     * @return success
     */
    public Boolean recordResult(int newQuestionNumber, int newItemID, int newSessionID, int newResult) {
        int oldResultCount = getResults().size();
        results.add(new TestResult(newQuestionNumber, newItemID, newSessionID, newResult));
        int newResultCount = getResults().size();

        if (oldResultCount == newResultCount){
            return false;
        }
        else { return true;}

    }

    /**
     * writeResults
     * write the list of TestResults to the database after the test
     * @return success
     */
    public Boolean writeResults() {
        //TestResultDAO resultsManager = DAOFactory.getTestResultDAO();
        for (TestResult result : results) {
            if (!DAOFactory.getTestResultDAO().insertResult(result)){
                return false;
            }
        }
        return true;
    }

    /**
     * getResults
     * getter for list of TestResults
     * @return list of TestResults
     */
    public ArrayList<TestResult> getResults() {
        return results;
    }

    public float getProgress(){
        calculateProgress();
        return progress;
    }
    public void calculateProgress(){
        progress = ((float)getCurrentTurn()/(float)getTotalTurn()) * 100;
    }
}
