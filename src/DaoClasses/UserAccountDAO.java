package DAOclasses;

import ContainerClasses.UserAccount;
import java.util.ArrayList;

/**
 * This class lists all methods in the UserAccountDAOimpl class
 * CIS 234A Dougherty
 * Date created: 5/19/2016.
 *  @author Zack
 *  @version 2016.5.19
 */
public interface UserAccountDAO {
    public boolean readAllUserAccounts(ArrayList<UserAccount> userAccounts);
    public boolean readUsersHavingResults(ArrayList<UserAccount> userAccounts);
    public boolean insertUserAccount(UserAccount account);
}
