package DaoClasses;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * This class handles creating a database connection
 * CIS 234A Dougherty
 * Creation Date: 5/19/2016.
 *
 *  @author Zack
 *  @version 2016.5.19
 */
public class DatabaseConnection {

    //begin fields
    Connection connection;
    boolean status;

    //begin database information strings
    private static final String DB_NAME = "234a_CoDeVilles";
    private static final String DB_URL = "jdbc:jtds:sqlserver://cisdbss.pcc.edu/" + DB_NAME;
    private static final String USERNAME = "234a_CoDeVilles";
    private static final String PASSWORD = "selliVeDoC_a432#";

    public Connection getConnection(){
        return connection;
    }

    public boolean getStatus(){
        return status;
    }

    public DatabaseConnection(){
        try {
            connection =  DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            status = true;
        } catch (SQLException e) {
            connection = null;
            status = false;
            e.printStackTrace();
        }
    }
}
