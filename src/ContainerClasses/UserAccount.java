package ContainerClasses;

/**
 * This class models single user account.
 * CIS 234A Dougherty
 * Creation Date: 4/13/2016
 *
 *  @author Zack
 *  @version 2016.4.13
 */
public class UserAccount {
    private int userID;
    private String email, name, password;


    //Begin Get functions
    /**
     * @return userID The ID number of the user
     */
    public int getUserID (){
        return userID;
    }
    /**
     * @return email The email address of the user
     */
    public String getEmail () {
        return email;
    }
    /**
     * @return name The name of the user
     */
    public String getName () {
        return name;
    }
    /**
     * @return password The password for the user
     */
    public String getPassword () {
        return password;
    }


    //Begin Set functions
    /**
     * @param newID The new ID number for the user
     */
    public void setUserID (int newID) {
        userID = newID;
    }
    /**
     * @param newEmail The new email address for the user
     */
    public void setEmail (String newEmail){
        email = newEmail;
    }
    /**
     * @param newName The new name for the user
     */
    public void setName (String newName){
        name = newName;
    }
    /**
     * @param newPassword The new password for the user
     */
    public void setPassword (String newPassword){
        password = newPassword;
    }


    //Begin Constructors
    /**
     * Default Constructor. Assigns default values to all fields
     */
    public UserAccount () {
        userID = 0;
        email = "no email";
        name = "no name";
        password = "no password";
    }
    /**
     * Constructor with all parameters
     * @param newID         The id number for the new user (normally set by the database)
     * @param newEmail      The email address for the new user
     * @param newName       The name of the new user
     * @param newPassword   The password for the new user
     */
    public UserAccount (int newID, String newEmail, String newName, String newPassword) {
        userID = newID;
        email = newEmail;
        name = newName;
        password = newPassword;
    }
    /**
     * Constructor with 3 parameters
     * The id number for the new user unneeded because it is normally set by the database
     * @param newEmail      The email address for the new user
     * @param newName       The name of the new user
     * @param newPassword   The password for the new user
     */
    public UserAccount (String newEmail, String newName, String newPassword) {
        userID = -1;
        email = newEmail;
        name = newName;
        password = newPassword;
    }
}
