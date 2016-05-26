package DaoClasses;

import ContainerClasses.TestItem;
import DaoClasses.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * This class implements the methods in the TestItemDAO class
 *
 * Date created: 05/20/2016
 *  @author Jinsook Lee
 *  @version 05/20/2016
 */

public class TestItemDAOimpl implements TestItemDAO {
    //fields
    DatabaseConnection databaseConnection;

    //string constants
    private static final String GET_ALL_TEST_ITEMS_SQL = "SELECT ItemID, ItemText, TestID FROM TESTITEMS";
    private static final String INSERT_NEW_TEST_ITEM_SQL = "INSERT INTO TESTITEMS (ItemText, TestID) VALUES (?,?)";
    private static final String DELETE_TEST_ITEM_SQL = "DELETE FROM TESTITEMS WHERE ItemText = ? and TestID= ?";
    private static final String GET_TEST_ITEMS_SQL = "SELECT ItemID, ItemText FROM TESTITEMS WHERE TestID = ?";

    //begin database functions

    /**
     * Reads all test items from the database and informs caller of success with return boolean
     * @param testItems An ArrayList of TestItem to populate with data from the database
     * @return true/false Whether the connection and read failed or succeeded.
     */
    public boolean readAllTestItems(ArrayList<TestItem> testItems){
//       if(databaseConnection.getStatus()){
        try ( //try to create a database connection
              Connection connection =  databaseConnection.getConnection();
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
  //  }else{return false;}
    }

    /**
     * Add an item to TESTITEMS table
     * @param item a test item that will be added to the database
     * @return true/false Whether the connection and read failed or succeeded.
     */
    public boolean insertTestItem(TestItem item){
        try (//try to create a database connection
             Connection connection =  databaseConnection.getConnection();
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
              Connection connection =  databaseConnection.getConnection();
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

    /**
     *  get test items for a given test
     * @param  testID the ID of the test being taken
     * @return  testItems ArrayList of TestItem
     */
    public ArrayList<TestItem> getTestItems(int testID) {
        try ( //try t create a database connection
              Connection connection =  databaseConnection.getConnection();
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

    public TestItemDAOimpl(){
        databaseConnection = new DatabaseConnection();
    }
}
