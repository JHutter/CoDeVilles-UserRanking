package UserTest;

import ContainerClasses.TestItem;
import java.util.Random;

/**
 * Created by JHutter on 4/26/2016.
 */
public class ItemPair {
    /* fields */
    private TestItem item1;
    private TestItem item2;


    /* constructor */
    public ItemPair (TestItem anItem, TestItem anotherItem) {
        Random randomGenerator = new Random();
        int randomNumber = randomGenerator.nextInt(100);
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
    public TestItem getItem1() {
        return item1;
    }
    public TestItem getItem2() {
        return item2;
    }

    public TestItem getItemByText(String text) {
        if (text.equals(item1.getItemText())) {
            return item1;
        }
        else if (text.equals((item2.getItemText()))) {
            return item2;
        }
        else {
            return null;
        }
    }


}
