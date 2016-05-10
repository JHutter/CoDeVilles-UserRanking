package ResultReporting;

import ContainerClasses.TestItem;

/**
 * This class models single test item at the end of a test.
 * CIS 234A Dougherty
 * Creation Date: 5/9/2016.
 *
 *  @author Zack
 *  @version 2016.4.22
 */
public class RankedItem extends TestItem {

    private int win, loss, tie;

    //Begin Get functions
    /**
     * @return win The number of 'wins' an item has item
     */
    public int getWin(){return win;}
    /**
     * @return loss The number of 'losses' an item has item
     */
    public int getLoss(){return loss;}
    /**
     * @return tie The number of 'ties' an item has item
     */
    public int getTie(){return tie;}

    //begin Set functions
    /**
     * @param newWin The new 'wins' for the item
     */
    public void setWin (int newWin) {
        win = newWin;
    }
    /**
     * @param newLoss The new 'losses' for the item
     */
    public void setLoss (int newLoss) {
        loss = newLoss;
    }
    /**
     * @param newTie The new 'wins' for the item
     */
    public void setTie (int newTie) {
        tie = newTie;
    }

    //begin constructors
    /**
     * default constructor
     */
    public RankedItem(){
        super();
        win = 0;
        loss = 0;
        tie = 0;
    }
    /**
     * constructor with full parameters
     * @param anItem The item to be ranked
     * @param newWin The 'wins' of the new ranked item
     * @param newLoss The 'losses' of the new ranked item
     * @param newTie The 'ties' of the new item
     */
    public RankedItem(TestItem anItem, int newWin, int newLoss, int newTie){
        super();
        itemID = anItem.getItemID();
        testID = anItem.getTestID();
        itemText = anItem.getItemText();
        win = newWin;
        loss = newLoss;
        tie = newTie;
    }
}
