package AdminSetup;

import ContainerClasses.TestItem;
import ContainerClasses.TestSession;
import SharedFunctions.DatabaseManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.lang.String;

/**
 * The adminSetupForm class is made to control admin setup GUI screen.
 * It add/delete items for test from user and update database.
 * Items should be more than two. After any user takes test, item can not be changed.
 * Create Date 04/18/2016
 *
 * @author   Jinsook Lee
 * @version  05/03/2016
 *
 * Modification
 * 05/03/2016
 * - Minimalize message boxes - Delete dialog message for add, delete, finish button. Show dialog message only if there is an error during operation.
 * - Add ckeckTakenTest method to check any user taken test
 * - Focus on itemTextField after click add item button
 * - Add isDuplicate method to check item duplication
 * - Add isListSelected to check item is selected in the list
 */

public class AdminSetupForm {
    private JTextField itemTextField;
    private JPanel rootPanel;
    private JList itemList;
    private JButton addButton;
    private JButton deleteButton;
    private JButton finishButton;
    private JButton cancelButton;
    private JLabel takenLabel;

    private DatabaseManager databaseManager;
    private ArrayList<TestItem> testItems;
    private ArrayList<TestSession> testSessions; //add 5/1
    private DefaultListModel listModel = new DefaultListModel();
    private TestItem titem;

    /**
     *  Constructor
     */
    public AdminSetupForm(){

        rootPanel.setPreferredSize(new Dimension(500,350));     // set size of window

        databaseManager = new DatabaseManager();    //create instance of DatabaseManager

        testItems = new ArrayList<>();               //declare arraylist and add items to the arraylist
        testSessions = new ArrayList<>();               //declare arraylist and add items to the arraylist //add 5/1

        getSessions();
        getItems();

        showItemList();
        ckeckTakenTest();
        checkItemNumber();

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addItem();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteItem();
            }
        });

        finishButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                finishItem();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelItem();
            }
        });
    }

    /**
     * Get item list array from DatabaseManager class
     */
    public void getItems(){
        if(!databaseManager.readAllTestItems(testItems)){
            JOptionPane.showMessageDialog(rootPanel, "Fail to read test items");
        }
    }

    /**
     * Get session list array from DatabaseManager class
     */
    public void getSessions(){
        if(!databaseManager.readAllTestSessions(testSessions)){
            JOptionPane.showMessageDialog(rootPanel, "Fail to read test sessions");
        }
    }

    /**
     * Show exist items on the list from DB
     */
    public void showItemList(){
       for(TestItem titem : testItems) {
              listModel.addElement(titem.getItemText());
       }
        itemList.setModel(listModel);
    }

    /**
     * Add button add input item to DB
     */
    public void addItem() {
        if (isValidLength())     //item is valid length
            if (!isDuplicate() && isValidLength()) {       //item is not duplicated
                titem = new TestItem(1, itemTextField.getText());
                if (!databaseManager.insertTestItem(titem)) {
                    JOptionPane.showMessageDialog(rootPanel, "Failed to add item to database.");
                    itemTextField.requestFocus();
                } else {                                                                       //query is executed query without error
                    listModel.addElement(itemTextField.getText());      //the item is added to the list
                    itemTextField.setText("");
                    itemTextField.requestFocus();
                }
            } else {
                JOptionPane.showMessageDialog(rootPanel, "The item is already exist. Please enter a new item.");
                itemTextField.setText("");
                itemTextField.requestFocus();
            }
    }

    /**
     * Delete button delete the selected item from DB
     */
    public void deleteItem() {
        if(isListSelected()){
            titem = new TestItem(1, (String) itemList.getSelectedValue());
            if (!databaseManager.deleteTestItem(titem)) {   //query is executed query without error
                JOptionPane.showMessageDialog(rootPanel, "Failed to delete user accounts from database.");
            }else{
                listModel.remove(itemList.getSelectedIndex());      //the item is removed from the list
            }
        }
    }

    /**
     * Finish button shows message and close GUI
     */
    public void finishItem(){
        System.exit(0);
    }

    /**
     * Cancel button clears item Text Field and clears selection of item List
     */
    public void cancelItem(){
        itemTextField.setText("");
        itemList.clearSelection();
    }

    /**
     * Check weather test is taken or not. After any user takes test, item can not be changed
     */
    public void ckeckTakenTest(){
        if(!databaseManager.readAllTestSessions(testSessions)){
            JOptionPane.showMessageDialog(rootPanel, "Fail to read test items from database.");
        }
        else{
          if (testSessions.size() > 10) {        // please change here to "if (testSessions.size() > 0) " to test ckeckTakenTest method
              addButton.setEnabled(false);
              deleteButton.setEnabled(false);
              cancelButton.setEnabled(false);
              itemTextField.setEditable(false);
              itemList.setEnabled(false);
          }
          else{
              takenLabel.setVisible(false);
          }
      }
    }

   /**
    *  Check the number of item. If there are less than two items , finish button should be disabled
    */
    public void  checkItemNumber(){
        if(testItems.size()<2){
            finishButton.setEnabled(false);
        }
    }

    /**
     *  Check input item is not duplicated.
     *  @return  boolean if it is duplicated return true
     */
    private boolean isDuplicate() {
        if (listModel.contains(itemTextField.getText())) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     *  Check text length
     *  @return  boolean if text length is within 1-50 return true
     */
    private boolean isValidLength() {
        if (itemTextField.getText().length() > 50) {        //max length of text is 50
            JOptionPane.showMessageDialog(rootPanel, "Please input item text within 50 characters.");
            itemTextField.setText("");
            itemTextField.requestFocus();
            return false;
        }
        else if(itemTextField.getText().length() == 0) {        //check input is valid
            JOptionPane.showMessageDialog(rootPanel, "Please input item to add.");
            itemTextField.requestFocus();
            return false;
        }
        return true;
    }

    private boolean isListSelected()
    {
        if (itemList.getSelectedIndex() == -1)      //no item is selected in the list
        {
            JOptionPane.showMessageDialog(rootPanel, "Please select item to delete.");
            return false;
        }
        return true;
    }

    /**
     * Return root panel to add to main frame
     * @return rootPanel the root panel that holds every elements
     */
    public JPanel getRootPanel(){
        return rootPanel;
    }
}
