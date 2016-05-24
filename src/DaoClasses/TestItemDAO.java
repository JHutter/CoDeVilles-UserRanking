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
/*    public boolean readAllUserAccounts(ArrayList<UserAccount> userAccounts);
    public boolean readUsersHavingResults(ArrayList<UserAccount> userAccounts);
    public boolean insertUserAccount(UserAccount account);*/
    boolean readAllTestItems(ArrayList<TestItem> testItems);
   // boolean readAllTestItemsID(ArrayList<TestItem> testItems, int testID);
    boolean insertTestItem(TestItem item);
    boolean deleteTestItem(TestItem item);
    ArrayList<TestItem> getTestItems(int testID);
}
