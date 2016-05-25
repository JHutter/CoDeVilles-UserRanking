package DaoClasses;

import ContainerClasses.UserAccount;
import java.util.ArrayList;

/**
 * This class lists all methods in the UserAccountDAOimpl class
 * CIS 234A Dougherty
 * Date created: 5/19/2016.
 *  @author Zack, Joanne
 *  @version 2016.5.24
 *
 *  Modifications
 *  2016.5.24
 *      Added getUserID
 */
public interface UserAccountDAO {
    public boolean readAllUserAccounts(ArrayList<UserAccount> userAccounts);
    public boolean readUsersHavingResults(ArrayList<UserAccount> userAccounts);
    public boolean insertUserAccount(UserAccount account);
    public int getUserID(String email, String password);
}
