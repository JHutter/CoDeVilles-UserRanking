package DaoClasses;

import ContainerClasses.TestSession;
import DaoClasses.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * This class implements the methods in the TestSessionDAO class
 *
 * Date created: 05/24/2016
 *  @author JoAnne Hutter, Zack Salzwedel, Jinsook Lee
 *  @version 06/06/2016
 *
 *  2016.5.27
 *      added function to get inactive test sessions
 *
 *  2016.5.28
 *      added COUNT_SESSIONS_ID_SQL statement
 *      added countSession method to check test is taken or not in Admin Setup
 *
 * 2016.5.31
 *      changed setIsActive to have a TestSession obj as parameter instead of individual ints
 *
 * 2016.6.6
 *      changed setIsActive to change session IsActive based on session.getActive()
 *      changed insertNewSession
 *
 */
public class TestSessionDAOimpl implements TestSessionDAO {
    //fields
    DatabaseConnection databaseConnection;
    private static final String GET_SESSION_SQL = "SELECT SessionID FROM TESTSESSIONS WHERE UserID = ? and TestID = ?";
    private static final String INSERT_NEW_SESSION_SQL = "INSERT INTO TESTSESSIONS (UserID, TestID, isActive) values (?, ?, 1)";
    private static final String GET_ALL_TEST_SESSIONS_SQL = "SELECT SessionID, UserID, TestID, isActive FROM TESTSESSIONS";
    private static final String GET_INACTIVE_TEST_SESSIONS_SQL = "SELECT SessionID, UserID, TestID, isActive FROM TESTSESSIONS " +
                                                                 "WHERE isActive = 0";
    private static final String SET_IS_ACTIVE_SQL = "UPDATE TESTSESSIONS SET isActive = ? WHERE SessionID = ?" +
            "and UserID = ? and  TestID = ?";
    private static final String COUNT_SESSIONS_ID_SQL = "SELECT COUNT(*) AS total FROM TESTSESSIONS WHERE TestID = ?";

    //constructor
    public TestSessionDAOimpl(){
        databaseConnection = new DatabaseConnection();
    }

    //methods
    /**
     *  Insert a new test session in the database
     * @param  userID the ID of the user taking the test
     * @param  testID the ID of the test being taken
     */
    public void insertSession(int userID, int testID){
        try ( //try to create a database connection
              Connection connection =  databaseConnection.getConnection();
              PreparedStatement stmt = connection.prepareStatement(INSERT_NEW_SESSION_SQL);
        ){
            stmt.setInt(1, userID);
            stmt.setInt(2, testID);
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
     * Reads all test sessions from the database and informs caller of success with return boolean
     * @param testSessions An ArrayList of TestSession to populate with data from the database
     * @return true/false Whether the connection and read failed or succeeded.
     */
    public boolean readAllTestSessions(ArrayList<TestSession> testSessions) {
        boolean isSessionActive;
        try ( //try to create a database connection
              Connection connection = databaseConnection.getConnection();
              PreparedStatement stmt = connection.prepareStatement(GET_ALL_TEST_SESSIONS_SQL);
              ResultSet rs = stmt.executeQuery()
        ) {
            while (rs.next()) { //if the connection was successful, read the result set and put into array list
                if (rs.getInt("isActive") == 1) {
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
        return true;
    }

    /**
     * Reads inactive test sessions from the database and informs caller of success with return boolean
     * @param testSessions An ArrayList of TestSession to populate with data from the database
     * @return true/false Whether the connection and read failed or succeeded.
     */
    public boolean readInactiveTestSessions(ArrayList<TestSession> testSessions) {
        boolean isSessionActive;
        try ( //try to create a database connection
              Connection connection = databaseConnection.getConnection();
              PreparedStatement stmt = connection.prepareStatement(GET_INACTIVE_TEST_SESSIONS_SQL);
              ResultSet rs = stmt.executeQuery()
        ) {
            while (rs.next()) { //if the connection was successful, read the result set and put into array list
                if (rs.getInt("isActive") == 1) {
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
        return true;
    }

    /**
     *  Get a test session from the database
     * @param  userID the ID of the user taking the test
     * @param  testID the ID of the test being taken
     * @return sessionID the ID of the testSession
     */
    public int getSessionID(int userID, int testID) {
        try ( //try to create a database connection
              Connection connection =  databaseConnection.getConnection();
              PreparedStatement stmt = connection.prepareStatement(GET_SESSION_SQL);
        ){
            stmt.setInt(1, userID);
            stmt.setInt(2, testID);
            ResultSet rs = stmt.executeQuery();
            int sessionID = 0;
            while (rs.next()) {
                sessionID = rs.getInt("SessionID");
            }
            return sessionID;
        }
        catch (SQLException e) { //if the connection fails, show error
            System.err.println("ERROR: " + e.getMessage());
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Set isActive to match session.getIsActive
     * @param session TestSession, the session to mark active
     */
    public void setIsActive(TestSession session) {
        int sessionID = session.getSessionID();
        int userID = session.getUserID();
        int testID = session.getTestID();
        int active = (session.getIsActive()) ? 1 : 0;
        try ( //try t create a database connection
              Connection connection =  databaseConnection.getConnection();
              PreparedStatement stmt = connection.prepareStatement(SET_IS_ACTIVE_SQL);
        ){
            stmt.setInt(1, active);
            stmt.setInt(2, sessionID);
            stmt.setInt(3, userID);
            stmt.setInt(4, testID);
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
     * Count SESSIONS of given TEST ID
     * @param tID int, the ID for the test
     * @return number of sessions of given test ID
     */
    public int countSession(int tID) {
        int cnt = -1;
        try ( //try t create a database connection
              Connection connection =  databaseConnection.getConnection();
              //Statement stmt = connection.createStatement();
              PreparedStatement stmt = connection.prepareStatement(COUNT_SESSIONS_ID_SQL);
        ){
            stmt.setInt(1, tID);
            ResultSet rs1 = stmt.executeQuery();
            if(rs1.next()) {
                cnt = rs1.getInt("total");
            }
        }
        catch (SQLException e) { //if the connection fails, show error
            System.err.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }
        return cnt;
    }


}
