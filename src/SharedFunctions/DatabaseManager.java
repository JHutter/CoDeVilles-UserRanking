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
 *  @author Zack and Jinsook Lee
 *  @version 2016.4.25
 */
public class DatabaseManager {
    //begin database information strings
    private static final String DB_NAME = "234a_CoDeVilles";
    private static final String DB_URL = "jdbc:jtds:sqlserver://cisdbss.pcc.edu/" + DB_NAME;
    private static final String USERNAME = "234a_CoDeVilles";
    private static final String PASSWORD = "selliVeDoC_a432#";


    //being sql strings
    private static final String GET_ALL_USER_ACCOUNTS_SQL = "SELECT * FROM USERACCOUNTS";
    private static final String GET_ALL_TESTS_SQL = "SELECT * FROM TESTS";
    private static final String GET_ALL_TEST_SESSIONS_SQL = "SELECT * FROM TESTSESSIONS";
    private static final String GET_ALL_TEST_ITEMS_SQL = "SELECT * FROM TESTITEMS";
    private static final String GET_ALL_TEST_RESULTS_SQL = "SELECT * FROM TESTRESULTS";
    private static final String INSERT_NEW_USER_ACCOUNT_SQL = "INSERT INTO USERACCOUNTS (Email, Name, Pass) VALUES (?,?,?)";
    private static final String INSERT_NEW_TEST_ITEM_SQL = "INSERT INTO TESTITEMS (ItemText, TestID) VALUES (?,?)";
    private static final String DELETE_TEST_ITEM_SQL = "DELETE FROM TESTITEMS WHERE ItemText = ? and TestID= ?";


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
            JOptionPane.showMessageDialog(null, "Unable to connect to database"); //generates pop-up box
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
            JOptionPane.showMessageDialog(null, "Unable to connect to database"); //generates pop-up box
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
            JOptionPane.showMessageDialog(null, "Unable to connect to database"); //generates pop-up box
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
            JOptionPane.showMessageDialog(null, "Unable to connect to database"); //generates pop-up box
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
            JOptionPane.showMessageDialog(null, "Unable to connect to database"); //generates pop-up box
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
            JOptionPane.showMessageDialog(null, "Unable to add account to database"); //generates pop-up box
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
            JOptionPane.showMessageDialog(null, "Unable to connect to database"); //generates pop-up box
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
            JOptionPane.showMessageDialog(null, "Unable to connect to database"); //generates pop-up box
            System.err.println("ERROR: " + e.getMessage());
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
