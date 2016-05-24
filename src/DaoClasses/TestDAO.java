package DaoClasses;

import ContainerClasses.Test;

import java.util.ArrayList;

/**
 * This class lists all methods in the TestResultDAOimpl class
 * CIS 234A Dougherty
 * Date created: 5/24/2016.
 *  @author JoAnne Hutter
 *  @version 2016.5.24
 */
public interface TestDAO {
    public boolean readAllTests(ArrayList<Test> tests);
}
