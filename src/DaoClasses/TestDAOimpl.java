package DaoClasses;

import ContainerClasses.Test;

import java.sql.*;
import java.util.ArrayList;
import DaoClasses.DatabaseConnection;

/**
 * This class implements the TestDAO class
 * CIS 234A Dougherty
 * Date created: 5/24/2016.
 *  @author JoAnne and Jinsook Lee
 *  @version 2016.5.25
 *
 * Modification
 * 2016.5.25
 *      Added insertGetTest method
 *      Added clause to INSERT_NEW_TEST_SQL
 *      Added constructor
 */

public class TestDAOimpl implements TestDAO {
    //fields
    DatabaseConnection databaseConnection;
    private static final String GET_ALL_TESTS_SQL = "SELECT TestID, TestName FROM TESTS";
    private static final String INSERT_NEW_TEST_SQL = "INSERT INTO TESTS (TestName) values (?)";
    //constructor

    //methods
    /**
     * Reads all tests from the database and informs caller of success with return boolean
     * @param tests An ArrayList of Test to populate with data from the database
     * @return true/false Whether the connection and read failed or succeeded.
     */

    public boolean readAllTests(ArrayList<Test> tests){
        try ( //try to create a database connection
              Connection connection =  databaseConnection.getConnection();
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

    public int insertGetTest(String testName){
        int id;
        try ( //try to create a database connection
              Connection connection =  databaseConnection.getConnection();
              PreparedStatement stmt = connection.prepareStatement(INSERT_NEW_TEST_SQL,
                      Statement.RETURN_GENERATED_KEYS);
        ){
            stmt.setString(1, testName);
            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating test set failed.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    //user.setId(generatedKeys.getLong(1));
                    id = generatedKeys.getInt(1);
                    //return id;
                }
                else {
                    throw new SQLException("Creating test set failed.");
                }
            }
        }
        catch (SQLException e) { //if the connection fails, show error
            System.err.println("ERROR: " + e.getMessage());
            e.printStackTrace();
            return 0;
        }
        return id;
    }

    public TestDAOimpl(){
        databaseConnection = new DatabaseConnection();
    }
}
