package DaoClasses;

import ContainerClasses.TestResult;

import java.util.ArrayList;

/**
 * This class lists all methods in the TestResultDAOimpl class
 * CIS 234A Dougherty
 * Date created: 5/19/2016.
 *  @author Zack
 *  @version 2016.5.31
 *
 *  Modifications:
 *  2016.5.31
 *      refactored insertResult to take a TestResult param, instead of several ints
 */
public interface TestResultDAO {
    public boolean readAllTestResults(ArrayList<TestResult> testResults);
    public boolean insertResult(TestResult result);
}
