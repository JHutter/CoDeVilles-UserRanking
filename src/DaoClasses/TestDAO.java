package DaoClasses;

import ContainerClasses.Test;

import java.util.ArrayList;

/**
 * This class lists all methods in the TestResultDAOimpl class
 * CIS 234A Dougherty
 * Date created: 5/24/2016.
 *  @author JoAnne Hutter and Jinsook Lee
 *  @version 2016.5.25
 *
 *  Modification
 *      Add insertGetTest method
 */
public interface TestDAO {
    boolean readAllTests(ArrayList<Test> tests);
    int insertGetTest(String testName);
}
