package DaoClasses;

import ContainerClasses.UserAccount;
import DaoClasses.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;

/**
 * This class implements the methods in the UserAccountDAO class
 * CIS 234A Dougherty
 * Date created: 5/19/2016.
 *  @author Zack, JoAnne
 *  @version 2016.5.24
 *
 *  Modifications
 *  2016.5.24
 *      Added getUserID
 */
public class UserAccountDAOimpl implements UserAccountDAO {
    //fields
    DatabaseConnection databaseConnection;

    //string constants
    private static final String GET_ALL_USER_ACCOUNTS_SQL = "SELECT UserID, Email, Name, Pass FROM USERACCOUNTS";
    private static final String GET_USERS_HAVING_RESULTS_SQL = "SELECT UserID, Email, Name, Pass FROM USERACCOUNTS " +
            "WHERE EXISTS (SELECT UserID FROM TESTSESSIONS " +
            "WHERE USERACCOUNTS.UserID = TESTSESSIONS.UserID " +
            "AND EXISTS (SELECT SessionID FROM TESTRESULTS " +
            "WHERE TESTSESSIONS.SessionID = TESTRESULTS.SessionID)) " +
            "ORDER BY Email";
    private static final String INSERT_NEW_USER_ACCOUNT_SQL = "INSERT INTO USERACCOUNTS (Email, Name, Pass) VALUES (?,?,?)";
    private static final String GET_USER_ID_SQL = "SELECT UserID from USERACCOUNTS WHERE Email = ? and Pass = ?";

    //methods
    /**
     * Reads all user accounts from the database and informs caller of success with return boolean
     * @param userAccounts An ArrayList of UserAccount to populate with data from the database
     * @return true/false Whether the connection and read failed or succeeded.
     */
    @Override
    public boolean readAllUserAccounts(ArrayList<UserAccount> userAccounts) {
        if(databaseConnection.getStatus()){
            try ( //try to create a database connection
                  Connection connection =  databaseConnection.getConnection();
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
        }else{return false;}
    }

    /**
     * Reads user accounts from the database for users associated with a test session having associated test results
     * e.g. does not read a user that has not answered a test question
     * and informs caller of success with return boolean
     * @param userAccounts An ArrayList of UserAccount to populate with data from the database
     * @return true/false Whether the connection and read failed or succeeded.
     */
    @Override
    public boolean readUsersHavingResults(ArrayList<UserAccount> userAccounts) {
        if(databaseConnection.getStatus()){
            try ( //try to create a database connection
                  Connection connection =  databaseConnection.getConnection();
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
        }else{return false;}
    }

    /**
     * Adds the supplied user account to the database and informs caller of success with return boolean
     * @param account A user account that will be added to the database
     * @return true/false Whether the connection and write failed or succeeded.
     */
    @Override
    public boolean insertUserAccount(UserAccount account) {
        if(databaseConnection.getStatus()){
            try (//try to create a database connection
                 Connection connection =  databaseConnection.getConnection();
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
        }else{return false;}
    }

    /**
     * Add test results to database
     * @param email String, the email of the user
     * @param password String, the password the user entered
     * @return userID int, the userID that matches the email provided
     */
    public int getUserID(String email, String password) {
        try ( //try t create a database connection
              Connection connection =  databaseConnection.getConnection();
              PreparedStatement stmt = connection.prepareStatement(GET_USER_ID_SQL);
        ){
            int userID = -1;
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                userID = rs.getInt("UserID");
            }
            return userID;
        }
        catch (SQLException e) { //if the connection fails, show error
            System.err.println("ERROR: " + e.getMessage());
            e.printStackTrace();
            return -1;
        }
    }

    public UserAccountDAOimpl(){
        databaseConnection = new DatabaseConnection();
    }
}
