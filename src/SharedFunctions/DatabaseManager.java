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
 *  @author Zack
 *  @version 2016.4.22
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
            ResultSet rs = stmt.executeQuery();
            ){
            while (rs.next()){ //if the connection was successful, read the result set and put into array list
                userAccounts.add(
                        new UserAccount(
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
}
