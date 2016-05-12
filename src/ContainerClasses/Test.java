package ContainerClasses;

/**
 * This class models single test.
 * CIS 234A Dougherty
 * Creation Date: 4/22/2016.
 *
 *  @author Zack
 *  @version 2016.4.22
 */
public class Test {
    private int testID;
    private String testName;

    //Begin Get functions
    /**
     * @return testID The ID number of the test
     */
    public int getTestID (){
        return testID;
    }
    /**
     * @return testName The name of the test
     */
    public String getTestName (){
        return testName;
    }

    //Begin Set functions
    /**
     * @param newTestID The new ID number for the test
     */
    public void setTestID (int newTestID) {
        testID = newTestID;
    }
    /**
     * @param newTestName The new name for the test
     */
    public void setTestName (String newTestName) {
        testName = newTestName;
    }

    //Begin Constructors
    /**
     * Default Constructor. Assigns default values to all fields
     */
    public Test() {
        testID = 0;
        testName = "noName";
    }
    /**
     * Constructor with all parameters
     * @param newTestID The ID number for the new test. Normally assigned by the database
     * @param newTestName The name of the new test
     */
    public Test(int newTestID, String newTestName) {
        testID = newTestID;
        testName = newTestName;
    }
    /**
     * Constructor with one parameter
     * The ID number for the new test is unneeded because it is normally assigned by the database
     * @param newTestName The name of the new test
     */
    public Test(String newTestName) {
        testID = 0;
        testName = newTestName;
    }
}
