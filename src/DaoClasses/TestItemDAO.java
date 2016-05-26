package DaoClasses;

import ContainerClasses.TestItem;
import java.util.ArrayList;

/**
 * This class lists all methods in the TestItemDAOimpl class
 *
 * Date created: 05/20/2016
 *  @author Jinsook Lee
 *  @version 05/20/2016
 */
public interface TestItemDAO {
    boolean readAllTestItems(ArrayList<TestItem> testItems);
    boolean insertTestItem(TestItem item);
    boolean deleteTestItem(TestItem item);
    ArrayList<TestItem> getTestItems(int testID);
}
