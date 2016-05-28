package DaoClasses;

/**
 * Create DAOs for the various tables in the 234a_CoDeVilles database
 * Create Date 05/22/2016
 *
 * @author   Jinsook Lee
 * @version  05/22/2016
 */
public class DAOFactory {

    public static TestItemDAO getTestItemDAO(){
        TestItemDAO tItemDAO = new TestItemDAOimpl();
        return tItemDAO;
    }

    public static TestSessionDAO getTestSessionDAO(){
        TestSessionDAO tSessionDAO = new TestSessionDAOimpl();
        return tSessionDAO;
    }

    public static TestDAO getTestDAO(){
        TestDAO testDAO = new TestDAOimpl();
        return testDAO;
    }


    public static TestResultDAO getTestResultDAO(){
        TestResultDAO tResultDAO = new TestResultDAOimpl();
        return tResultDAO;
    }

    public static UserAccountDAO getUserAccountDAO(){
        UserAccountDAO uAccountDAO = new UserAccountDAOimpl();
        return uAccountDAO;
    }
}
