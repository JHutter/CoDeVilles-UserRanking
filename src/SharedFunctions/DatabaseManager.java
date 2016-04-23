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
    public boolean readAllUserAccounts(ArrayList<UserAccount> userAccounts){
        //ArrayList<UserAccount> userAccounts = new ArrayList<UserAccount>();

        try (
            Connection connection =  DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            PreparedStatement stmt = connection.prepareStatement(GET_ALL_USER_ACCOUNTS_SQL);
            ResultSet rs = stmt.executeQuery();
            ){
            while (rs.next()){
                userAccounts.add(
                        new UserAccount(
                        rs.getString("Email"),
                        rs.getString("Name"),
                        rs.getString("Pass")
                ));
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Unable to connect to database");
            System.err.println("ERROR: " + e.getMessage());
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
