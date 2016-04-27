package UserTest;

/**
 * Created by JHutter on 4/26/2016.
 */
public class ItemPair {
    /* fields */
    private String item1;
    private String item2;


    /* constructor */
    public ItemPair (String anItem, String anotherItem) {
        int randomNumber = 57;
        if ((randomNumber % 2) == 1) {
            item1 = anItem;
            item2 = anotherItem;
        }
        else {
            item1 = anotherItem;
            item2 = anItem;
        }
    }


    /* methods */
    public String getItem1() {
        return item1;
    }
    public String getItem2() {
        return item2;
    }


}
