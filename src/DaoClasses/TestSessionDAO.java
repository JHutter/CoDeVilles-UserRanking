package DaoClasses;

import ContainerClasses.TestSession;

import java.util.ArrayList;

/**
 * This class lists all methods in the TestResultDAOimpl class
 * CIS 234A Dougherty
 * Date created: 5/24/2016.
 *  @author JoAnne Hutter
 *  @version 2016.5.24
 */
public interface TestSessionDAO {
    public void insertSession(int userID, int testID);
    public boolean readAllTestSessions(ArrayList<TestSession> testSessions);
    public int getSessionID(int userID, int testID);

}
