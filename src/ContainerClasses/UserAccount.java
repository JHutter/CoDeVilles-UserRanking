package ContainerClasses;
/**
 * Created by Zack on 4/13/2016.
 * This class models user accounts.
 * CIS 234A Dougherty
 */
public class UserAccount {
    private int userID;
    private String email, name, password;


    //Begin Get functions
    /**
     * @return userID The ID number of the user
     */
    private int getUserID (){
        return userID;
    }
    /**
     * @return email The email address of the user
     */
    private String getEmail () {
        return email;
    }
    /**
     * @return name The name of the user
     */
    private String getName () {
        return name;
    }
    /**
     * @return password The password for the user
     */
    private String getPassword () {
        return password;
    }


    //Begin Set functions
    /**
     * @param newID The new ID number for the user
     */
    private void setUserID (int newID) {
        userID = newID;
    }
    /**
     * @param newEmail The new email address for the user
     */
    private void setEmail (String newEmail){
        email = newEmail;
    }
    /**
     * @param newName The new name for the user
     */
    private void setName (String newName){
        name = newName;
    }
    /**
     * @param newPassword The new password for the user
     */
    private void setPassword (String newPassword){
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
     * Constructor with parameters
     * @param newID         The id number for the new user
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
}
