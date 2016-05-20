package DaoClasses;

import ContainerClasses.TestResult;

import java.util.ArrayList;

/**
 * This class lists all methods in the TestResultDAOimpl class
 * CIS 234A Dougherty
 * Date created: 5/19/2016.
 *  @author Zack
 *  @version 2016.5.19
 */
public interface TestResultDAO {
    public boolean readAllTestResults(ArrayList<TestResult> testResults);
    public boolean insertResult(int questionNumber, int itemID, int sessionID, int result);
}
