package ContainerClasses;

/**
 * This class models single result.
 * CIS 234A Dougherty
 *
 *  Programmer(s): Zack
 *  Date: 4/19/2016.
 *
 */
public class TestResult {
    private int resultID, questionNumber, itemID, sessionID, result;

    //Begin Get functions
    /**
     * @return resultID The ID number of the result
     */
    public int getResultID (){
        return resultID;
    }
    /**
     * @return questionNumber The test-question number of the result
     */
    public int getQuestionNumber (){
        return questionNumber;
    }
    /**
     * @return itemID The item that the result is for
     */
    public int getItemID (){
        return itemID;
    }
    /**
     * @return sessionID The id number of the test-session that generated the result
     */
    public int getSessionID (){
        return sessionID;
    }
    /**
     * @return result The value of the result. -1 for not chosen, 0 for tie, 1 for chosen
     */
    public int getResult (){
        return result;
    }


    //Begin Set functions
    /**
     * @param newResultID The ID number of the result
     */
    public void setResultID (int newResultID){ resultID = newResultID;  }
    /**
     * @param newQuestionNumber The test-question number for the result
     */
    public void setQuestionNumber (int newQuestionNumber){
        questionNumber = newQuestionNumber;
    }
    /**
     * @param newItemID The item ID number that the user was tested on
     */
    public void setItemID (int newItemID){ itemID = newItemID;  }
    /**
     * @param newSessionID The id number of the test-session that generated the result
     */
    public void setSessionID (int newSessionID){ sessionID = newSessionID;}
    /**
     * @param newResult The value of the result. -1 for not chosen, 0 for tie, 1 for chosen
     */
    public void setResult (int newResult){
        result = newResult;
    }


    //Begin Constructors
    /**
     * Default Constructor. Assigns default values to all fields
     */
    public TestResult () {
        resultID = -1;
        questionNumber = -1;
        itemID = -1;
        sessionID  = -1;
        result = 2;
    }
    /**
     * Constructor with all parameters
     * @param newResultID The ID number of the result (normally set by the database)
     * @param newQuestionNumber The test-question number for the result
     * @param newItemID The item ID number that the user was tested on
     * @param newSessionID The id number of the test-session that generated the result
     * @param newResult The value of the result. -1 for not chosen, 0 for tie, 1 for chosen
     */
    public TestResult (int newResultID, int newQuestionNumber, int newItemID, int newSessionID, int newResult) {
        resultID = newResultID;
        questionNumber = newQuestionNumber;
        itemID = newItemID;
        sessionID  = newSessionID;
        result = newResult;
    }
    /**
     * Constructor with four parameters
     * resultID The ID number of the result which unneeded because it is normally set by the database
     * @param newQuestionNumber The test-question number for the result
     * @param newItemID The item ID number that the user was tested on
     * @param newSessionID The id number of the test-session that generated the result
     * @param newResult The value of the result. -1 for not chosen, 0 for tie, 1 for chosen
     */
    public TestResult (int newQuestionNumber, int newItemID, int newSessionID, int newResult) {
        resultID = -1;
        questionNumber = newQuestionNumber;
        itemID = newItemID;
        sessionID  = newSessionID;
        result = newResult;
    }
}
