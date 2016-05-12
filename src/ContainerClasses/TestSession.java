package ContainerClasses;

/**
 * This class models single test session.
 * CIS 234A Dougherty
 * Creation Date: 4/22/2016.
 *
 *  @author Zack
 *  @version 2016.4.22
 */
public class TestSession {
    private int sessionID, testID, userID;
    private boolean isActive;

    //Begin Get functions
    /**
     * @return sessionID The ID number of the test session
     */
    public int getSessionID (){
        return sessionID;
    }
    /**
     * @return testID The ID number of the test that the session is using
     */
    public int getTestID (){
        return testID;
    }
    /**
     * @return userID The ID number of the user that is taking the test
     */
    public int getUserID (){ return userID; }
    /**
     * @return isActive Returns true if the test session is active, false if not
     */
    public boolean getIsActive (){
        return isActive;
    }

    //Begin Set functions
    /**
     * @param newSessionID The new ID number for the test session
     */
    public void setSessionID (int newSessionID) {
        sessionID = newSessionID;
    }
    /**
     * @param newTestID The new ID number for the test that the session is using
     */
    public void setTestID (int newTestID) {
        testID = newTestID;
    }
    /**
     * @param newUserID The new ID number for the user taking the test
     */
    public void setUserID (int newUserID) {
        userID = newUserID;
    }
    /**
     * @param newIsActive The new active status of the test session
     */
    public void setIsActive (boolean newIsActive) {
        isActive = newIsActive;
    }

    //Begin Constructors
    /**
     * Default Constructor. Assigns default values to all fields
     */
    public TestSession(){
        sessionID = 0;
        testID = 0;
        userID = 0;
        isActive = false;
    }
    /**
     * Constructor with all parameters
     * @param newSessionID The ID number of the new test session. Normally assigned by the database
     * @param newTestID The ID number of the test that the new session is using
     * @param newUserID The ID number of the user taking the new test
     * @param newIsActive A flag used to signal whether the new test session is being taken by a user
     */
    public TestSession(int newSessionID, int newTestID, int newUserID, boolean newIsActive){
        sessionID = newSessionID;
        testID = newTestID;
        userID = newUserID;
        isActive = newIsActive;
    }
    /**
     * Constructor with three parameters
     * The ID number of the new test session is unneeded because it is normally assigned by the database
     * @param newTestID The ID number of the test that the new session is using
     * @param newUserID The ID number of the user taking the new test
     * @param newIsActive A flag used to signal whether the new test session is being taken by a user
     */
    public TestSession(int newTestID, int newUserID, boolean newIsActive){
        sessionID = 0;
        testID = newTestID;
        userID = newUserID;
        isActive = newIsActive;
    }
    /**
     * Constructor with two parameters. Used to create a new active test that a user will immediately begin.
     * sessionID: The ID number of the new test session is unneeded because it is normally assigned by the database
     * @param newTestID The ID number of the test that the new session is using
     * @param newUserID The ID number of the user taking the new test
     * isActive: A flag used to signal whether the new test session is being taken by a user. Set to true by default to flag an in-progress test
     */
    public TestSession(int newTestID, int newUserID){
        sessionID = 0;
        testID = newTestID;
        userID = newUserID;
        isActive = true;
    }

    //being other functions

    /**
     * Toggles the test Active status to the other value. Then returns the new Active state
     * @return isActive The new flag for an in-progress test
     */
    public boolean toggleActive(){
        if(isActive){
            isActive = false;
        } else {
            isActive = true;
        }
        return isActive;
    }
}
