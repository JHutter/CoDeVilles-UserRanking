package SharedFunctions;

import ContainerClasses.*;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * This class handles accessing a database
 * CIS 234A Dougherty
 * Creation Date: 4/22/2016.
 *
 *  @author Zack and Jinsook and JoAnne
 *  @version 2016.5.03
 *
 *  2016.4.29:
 *      Refactored SQL statements to not use SELECT * FROM
 *      Removed pop-ups from function call failures
 *      Added function to get user accounts that have results associated with them. (e.g. users that have answered test questions)
 *  2016.5.3:
 *      Added getTestItems and getSessionID methods
 */
public class DatabaseManager {
    //begin database information strings
    private static final String DB_NAME = "234a_CoDeVilles";
    private static final String DB_URL = "jdbc:jtds:sqlserver://cisdbss.pcc.edu/" + DB_NAME;
    private static final String USERNAME = "234a_CoDeVilles";
    private static final String PASSWORD = "selliVeDoC_a432#";


    //being sql strings
    private static final String GET_ALL_USER_ACCOUNTS_SQL = "SELECT UserID, Email, Name, Pass FROM USERACCOUNTS";
    private static final String GET_ALL_TESTS_SQL = "SELECT TestID, TestName FROM TESTS";
    private static final String GET_ALL_TEST_SESSIONS_SQL = "SELECT SessionID, UserID, TestID, isActive FROM TESTSESSIONS";
    private static final String GET_ALL_TEST_ITEMS_SQL = "SELECT ItemID, ItemText, TestID FROM TESTITEMS";
    private static final String GET_ALL_TEST_RESULTS_SQL = "SELECT ResultID, QuestionNumber, ItemID, SessionID, Result FROM TESTRESULTS";
    private static final String GET_USERS_HAVING_RESULTS_SQL = "SELECT UserID, Email, Name, Pass FROM USERACCOUNTS " +
                                                                "WHERE EXISTS (SELECT UserID FROM TESTSESSIONS " +
                                                                "WHERE USERACCOUNTS.UserID = TESTSESSIONS.UserID " +
                                                                "AND EXISTS (SELECT SessionID FROM TESTRESULTS " +
                                                                "WHERE TESTSESSIONS.SessionID = TESTRESULTS.SessionID))";
    private static final String INSERT_NEW_USER_ACCOUNT_SQL = "INSERT INTO USERACCOUNTS (Email, Name, Pass) VALUES (?,?,?)";
    private static final String INSERT_NEW_TEST_ITEM_SQL = "INSERT INTO TESTITEMS (ItemText, TestID) VALUES (?,?)";
    private static final String DELETE_TEST_ITEM_SQL = "DELETE FROM TESTITEMS WHERE ItemText = ? and TestID= ?";
    private static final String GET_SESSION_ID_SQL = "SELECT SESSIONID FROM TESTSESSIONS WHERE UserID = ? and TestID = ?";
    private static final String GET_TEST_ITEMS_SQL = "SELECT ItemID, ItemText FROM TESTITEMS WHERE TestID = ?";
    private static final String SET_IS_ACTIVE_SQL = "UPDATE TESTSESSIONS SET isActive = 1 WHERE SessionID = ?" +
                                                                "and UserID = ? and  TestID = ?";
    private static final String INSERT_RESULT_SQL = "INSERT INTO TESTRESULTS (QuestionNumber, ItemID, SessionID, Result)" +
                                                                " values (?, ?, ?, ?)";


    //begin database functions
    /**
     * Reads all user accounts from the database and informs caller of success with return boolean
     * @param userAccounts An ArrayList of UserAccount to populate with data from the database
     * @return true/false Whether the connection and read failed or succeeded.
     */
    public boolean readAllUserAccounts(ArrayList<UserAccount> userAccounts){
        try ( //try to create a database connection
            Connection connection =  DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            PreparedStatement stmt = connection.prepareStatement(GET_ALL_USER_ACCOUNTS_SQL);
            ResultSet rs = stmt.executeQuery()
            ){
            while (rs.next()){ //if the connection was successful, read the result set and put into array list
                userAccounts.add(
                        new UserAccount(
                        rs.getInt("UserID"),
                        rs.getString("Email"),
                        rs.getString("Name"),
                        rs.getString("Pass")
                ));
            }

        } catch (SQLException e) { //if the connection fails, show error
            System.err.println("ERROR: " + e.getMessage());
            e.printStackTrace();
            return false; //return false due to connection failure
        }

        return true; //return true for success
    }
    /**
     * Reads all tests from the database and informs caller of success with return boolean
     * @param tests An ArrayList of Test to populate with data from the database
     * @return true/false Whether the connection and read failed or succeeded.
     */
    public boolean readAllTests(ArrayList<Test> tests){
        try ( //try to create a database connection
              Connection connection =  DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
              PreparedStatement stmt = connection.prepareStatement(GET_ALL_TESTS_SQL);
              ResultSet rs = stmt.executeQuery()
        ){
            while (rs.next()){ //if the connection was successful, read the result set and put into array list
                tests.add(
                        new Test(
                                rs.getInt("TestID"),
                                rs.getString("TestName")
                        ));
            }

        } catch (SQLException e) { //if the connection fails, show error
            System.err.println("ERROR: " + e.getMessage());
            e.printStackTrace();
            return false; //return false due to connection failure
        }

        return true; //return true for success
    }
    /**
     * Reads all test sessions from the database and informs caller of success with return boolean
     * @param testSessions An ArrayList of TestSession to populate with data from the database
     * @return true/false Whether the connection and read failed or succeeded.
     */
    public boolean readAllTestSessions(ArrayList<TestSession> testSessions){
        boolean isSessionActive;
        try ( //try to create a database connection
              Connection connection =  DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
              PreparedStatement stmt = connection.prepareStatement(GET_ALL_TEST_SESSIONS_SQL);
              ResultSet rs = stmt.executeQuery()
        ){
            while (rs.next()){ //if the connection was successful, read the result set and put into array list
                if(rs.getInt("isActive") == 1){
                    isSessionActive = true;
                } else {
                    isSessionActive = false;
                }
                testSessions.add(
                        new TestSession(
                                rs.getInt("SessionID"),
                                rs.getInt("TestID"),
                                rs.getInt("UserID"),
                                isSessionActive
                        ));
            }

        } catch (SQLException e) { //if the connection fails, show error
            System.err.println("ERROR: " + e.getMessage());
            e.printStackTrace();
            return false; //return false due to connection failure
        }

        return true; //return true for success
    }
    /**
     * Reads all test items from the database and informs caller of success with return boolean
     * @param testItems An ArrayList of TestItem to populate with data from the database
     * @return true/false Whether the connection and read failed or succeeded.
     */
    public boolean readAllTestItems(ArrayList<TestItem> testItems){
        try ( //try to create a database connection
              Connection connection =  DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
              PreparedStatement stmt = connection.prepareStatement(GET_ALL_TEST_ITEMS_SQL);
              ResultSet rs = stmt.executeQuery()
        ){
            while (rs.next()){ //if the connection was successful, read the result set and put into array list
                testItems.add(
                        new TestItem(
                                rs.getInt("ItemID"),
                                rs.getInt("TestID"),
                                rs.getString("itemText")
                        ));

            }

        } catch (SQLException e) { //if the connection fails, show error
            System.err.println("ERROR: " + e.getMessage());
            e.printStackTrace();
            return false; //return false due to connection failure
        }

        return true; //return true for success
    }
    /**
     * Reads all test results from the database and informs caller of success with return boolean
     * @param testResults An ArrayList of TestResult to populate with data from the database
     * @return true/false Whether the connection and read failed or succeeded.
     */
    public boolean readAllTestResults(ArrayList<TestResult> testResults){
        try ( //try to create a database connection
              Connection connection =  DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
              PreparedStatement stmt = connection.prepareStatement(GET_ALL_TEST_RESULTS_SQL);
              ResultSet rs = stmt.executeQuery()
        ){
            while (rs.next()){ //if the connection was successful, read the result set and put into array list
                testResults.add(
                        new TestResult(
                                rs.getInt("ResultID"),
                                rs.getInt("QuestionNumber"),
                                rs.getInt("ItemID"),
                                rs.getInt("SessionID"),
                                rs.getInt("Result")
                        ));
            }

        } catch (SQLException e) { //if the connection fails, show error
            System.err.println("ERROR: " + e.getMessage());
            e.printStackTrace();
            return false; //return false due to connection failure
        }

        return true; //return true for success
    }
    /**
     * Reads user accounts from the database for users associated with a test session having associated test results
     * e.g. does not read a user that has not answered a test question
     * and informs caller of success with return boolean
     * @param userAccounts An ArrayList of UserAccount to populate with data from the database
     * @return true/false Whether the connection and read failed or succeeded.
     */
    public boolean readUsersHavingResults(ArrayList<UserAccount> userAccounts){
        try ( //try to create a database connection
              Connection connection =  DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
              PreparedStatement stmt = connection.prepareStatement(GET_USERS_HAVING_RESULTS_SQL);
              ResultSet rs = stmt.executeQuery()
        ){
            while (rs.next()){ //if the connection was successful, read the result set and put into array list
                userAccounts.add(
                        new UserAccount(
                                rs.getInt("UserID"),
                                rs.getString("Email"),
                                rs.getString("Name"),
                                rs.getString("Pass")
                        ));
            }

        } catch (SQLException e) { //if the connection fails, show error
            System.err.println("ERROR: " + e.getMessage());
            e.printStackTrace();
            return false; //return false due to connection failure
        }

        return true; //return true for success
    }
    /**
     * Adds the supplied user account to the database and informs caller of success with return boolean
     * @param account A user account that will be added to the database
     * @return true/false Whether the connection and write failed or succeeded.
     */
    public boolean insertUserAccount(UserAccount account){
        try (//try to create a database connection
            Connection connection =  DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            PreparedStatement stmt = connection.prepareStatement(INSERT_NEW_USER_ACCOUNT_SQL)
        ){

            stmt.setString(1, account.getEmail());
            stmt.setString(2, account.getName());
            stmt.setString(3, account.getPassword());
            stmt.executeUpdate();

        } catch (SQLException e) { //if the connection fails, show error
            System.err.println("ERROR: " + e.getMessage());
            e.printStackTrace();
            return false; //return false due to connection failure
        }

        return true; //return true for success
    }

    /**
     * Add an item to TESTITEMS table
     * @param item a test item that will be added to the database
     * @return true/false Whether the connection and read failed or succeeded.
     */
    public boolean insertTestItem(TestItem item){
        try (//try to create a database connection
             Connection connection =  DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
             PreparedStatement stmt = connection.prepareStatement(INSERT_NEW_TEST_ITEM_SQL)
        ){
            stmt.setString(1, item.getItemText());
            stmt.setInt(2, item.getTestID());
            stmt.executeUpdate();
        }
        catch (SQLException e) { //if the connection fails, show error
            System.err.println("ERROR: " + e.getMessage());
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * Delete an item from TESTITEMS table
     * @param  item a test item that will be deleted from the database
     * @return true/false Whether the connection and read failed or succeeded.
     */
    public boolean deleteTestItem(TestItem item){
        try ( //try to create a database connection
              Connection connection =  DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
              PreparedStatement stmt = connection.prepareStatement(DELETE_TEST_ITEM_SQL)
        ){
            stmt.setString(1, item.getItemText());
            stmt.setInt(2, item.getTestID());
            stmt.executeUpdate();
        }
        catch (SQLException e) { //if the connection fails, show error
            System.err.println("ERROR: " + e.getMessage());
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /*public int getSessionID(UserAccount user, Test test) {
        try ( //try to create a database connection
              Connection connection =  DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
              PreparedStatement stmt = connection.prepareStatement(GET_SESSION_ID_SQL);
        ){
            stmt.setInt(1, user.getUserID());
            stmt.setInt(2, test.getTestID());
            //stmt.();
            return 1;
        }
        catch (SQLException e) { //if the connection fails, show error
            System.err.println("ERROR: " + e.getMessage());
            e.printStackTrace();
            return 0;
        }
    }*/


    public ArrayList<TestItem> getTestItems(int testID) {
        try ( //try t create a database connection
              Connection connection =  DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
              PreparedStatement stmt = connection.prepareStatement(GET_TEST_ITEMS_SQL);
        ){
            stmt.setInt(1, testID);
            ResultSet rs = stmt.executeQuery();
            ArrayList<TestItem> items = new ArrayList<>();
            while (rs.next()) {
                String itemText = rs.getString("ItemText");
                int itemID = rs.getInt("ItemID");
                items.add(new TestItem(itemID, testID, itemText));
            }
            return items;
        }
        catch (SQLException e) { //if the connection fails, show error
            System.err.println("ERROR: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Set isActive to true when a test is being taken by the user
     * @param sessionID int, the ID for the session
     * @param userID int, the ID for the user
     * @param testID int, the ID for the test
     */
    public void setIsActive(int sessionID, int userID, int testID) {
        try ( //try t create a database connection
              Connection connection =  DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
              PreparedStatement stmt = connection.prepareStatement(SET_IS_ACTIVE_SQL);
        ){
            stmt.setInt(1, sessionID);
            stmt.setInt(2, userID);
            stmt.setInt(3, testID);
            stmt.executeUpdate();
            return;
        }
        catch (SQLException e) { //if the connection fails, show error
            System.err.println("ERROR: " + e.getMessage());
            e.printStackTrace();
            return;
        }
    }

    /**
     * Add test results to database
     * @param questionNumber int, the ID for the item
     * @param itemID int, the ID for the matched item
     * @param sessionID int, the ID for the session
     * @param result int, the result of the matchup (-1,0,1)
     */
    public void insertResult(int questionNumber, int itemID, int sessionID, int result) {
        try ( //try t create a database connection
              Connection connection =  DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
              PreparedStatement stmt = connection.prepareStatement(INSERT_RESULT_SQL);
        ){
            stmt.setInt(1, questionNumber);
            stmt.setInt(2, itemID);
            stmt.setInt(3, sessionID);
            stmt.setInt(4, result);
            stmt.executeUpdate();
            return;
        }
        catch (SQLException e) { //if the connection fails, show error
            System.err.println("ERROR: " + e.getMessage());
            e.printStackTrace();
            return;
        }
    }
}
