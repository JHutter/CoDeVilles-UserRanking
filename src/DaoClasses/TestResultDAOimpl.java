package DAOclasses;

import ContainerClasses.TestResult;
import DAOclasses.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;

/**
 * This class implements all methods listed in the TestResultDAOimpl class
 * CIS 234A Dougherty
 * Date created: 5/19/2016.
 *  @author Zack, JoAnne
 *  @version 2016.5.19
 */
public class TestResultDAOimpl implements TestResultDAO {
    //fields
    DatabaseConnection databaseConnection;

    //string constants
    private static final String GET_ALL_TEST_RESULTS_SQL = "SELECT ResultID, QuestionNumber, ItemID, SessionID, Result FROM TESTRESULTS";
    private static final String INSERT_RESULT_SQL = "INSERT INTO TESTRESULTS (QuestionNumber, ItemID, SessionID, Result)" +
            " values (?, ?, ?, ?)";

    /**
     * Reads all test results from the database and informs caller of success with return boolean
     * @param testResults An ArrayList of TestResult to populate with data from the database
     * @return true/false Whether the connection and read failed or succeeded.
     */
    @Override
    public boolean readAllTestResults(ArrayList<TestResult> testResults) {
        if(databaseConnection.getStatus()) {
            try ( //try to create a database connection
                  Connection connection =  databaseConnection.getConnection();
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
        }else{return false;}
    }

    /**
     * Add test results to database. Returns true/false success status
     * @param questionNumber int, the ID for the item
     * @param itemID int, the ID for the matched item
     * @param sessionID int, the ID for the session
     * @param result int, the result of the matchup (-1,0,1)
     * @return true/false Whether the connection and write failed or succeeded.
     */
    @Override
    public boolean insertResult(int questionNumber, int itemID, int sessionID, int result) {
        if(databaseConnection.getStatus()) {
            try ( //try t create a database connection
                  Connection connection =  databaseConnection.getConnection();
                  PreparedStatement stmt = connection.prepareStatement(INSERT_RESULT_SQL);
            ){
                stmt.setInt(1, questionNumber);
                stmt.setInt(2, itemID);
                stmt.setInt(3, sessionID);
                stmt.setInt(4, result);
                stmt.executeUpdate();
                return true;
            }
            catch (SQLException e) { //if the connection fails, show error
                System.err.println("ERROR: " + e.getMessage());
                e.printStackTrace();
                return false;
            }
        }else{return false;}
    }

    public TestResultDAOimpl(){
        databaseConnection = new DatabaseConnection();
    }
}
