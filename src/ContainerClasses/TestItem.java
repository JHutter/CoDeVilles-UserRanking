package ContainerClasses;

/**
 * This class models single test item.
 * CIS 234A Dougherty
 * Creation Date: 4/22/2016.
 *
 *  @author Zack
 *  @version 2016.4.22
 */
public class TestItem {

    protected int itemID, testID;
    protected String itemText;

    //Begin Get functions
    /**
     * @return itemID The ID number of the item
     */
    public int getItemID (){
        return itemID;
    }
    /**
     * @return testID The ID number of the test that the item belongs to
     */
    public int getTestID (){
        return testID;
    }
    /**
     * @return itemText The text associated with the test item
     */
    public String getItemText (){
        return itemText;
    }

    //Begin Set functions
    /**
     * @param newItemID The new ID number for the item
     */
    public void setItemID (int newItemID) {
        itemID = newItemID;
    }
    /**
     * @param newTestID The new ID number for the test that the item belongs to
     */
    public void setTestID (int newTestID) {
        testID = newTestID;
    }
    /**
     * @param newItemText The new text to associate with the item
     */
    public void setItemText (String newItemText) {itemText = newItemText;}

    //Begin Constructors
    /**
     * Default Constructor. Assigns default values to all fields
     */
    public TestItem () {
        itemID = 0;
        testID = 0;
        itemText = "noText";
    }
    /**
     * Constructor with all parameters
     * @param newItemID The ID number for the new item. Normally assigned by the database
     * @param newTestID The ID number of the test that the new item belongs to
     * @param newItemText The text to associate witht he new item
     */
    public TestItem (int newItemID, int newTestID, String newItemText) {
        itemID = newItemID;
        testID = newTestID;
        itemText = newItemText;
    }
    /**
     * Constructor with 2 parameters
     * The ID number for the new item is unneeded because it is normally assigned by the database
     * @param newTestID The ID number of the test that the new item belongs to
     * @param newItemText The text to associate witht he new item
     */
    public TestItem (int newTestID, String newItemText) {
        itemID = 0;
        testID = newTestID;
        itemText = newItemText;
    }
}
