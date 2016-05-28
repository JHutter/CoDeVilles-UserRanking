package DaoClasses;

import ContainerClasses.TestSession;

import java.util.ArrayList;

/**
 * This class lists all methods in the TestResultDAOimpl class
 * CIS 234A Dougherty
 * Date created: 5/24/2016.
 *  @author JoAnne Hutter, Zack Salzwedel, Jinsook Lee
 *  @version 2016.5.28
 *
 *  2016.5.27
 *      added function to get inactive test sessions
 *
 *  2016.5.28
 *      added function to get count of session for specific test ID
 */
public interface TestSessionDAO {
    public void insertSession(int userID, int testID);
    public boolean readAllTestSessions(ArrayList<TestSession> testSessions);
    public boolean readInactiveTestSessions(ArrayList<TestSession> testSessions);
    public int getSessionID(int userID, int testID);
    public int countSession(int testID);
}
