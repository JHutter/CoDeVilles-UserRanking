package AdminSetup;

import ContainerClasses.TestItem;
import DaoClasses.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
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
 * @version  05/11/2016
 *
 * Modification
 * 05/03/2016
 * - Minimalize message boxes - Delete dialog message for add, delete, finish button. Show dialog message only if there is an error during operation.
 * - Add ckeckTakenTest method to check any user taken test
 * - Focus on itemTextField after click add item button
 * - Add isDuplicate method to check item duplication
 * - Add isListSelected to check item is selected in the list
 * 05/11/2016
 * - Add checkListSelected method to disable delete button if there's nothing selected
 * - Update isValidLength method to remove function to wiping out a user's entry after check length of item text
 * 05/24/2016
 * - Add fields, tItemDAO, testID
 * - Update addItem, deleteItem method to use DAO to handling DB
 * - Update constructor to get TestID as parameter
 * 05/25/2016
 * - Use DAO to get session data
 * 05/28/2016
 * - Delete getSessions method and update ckeckTakenTest to use select count(*) statement
 * - Add parameter boolean isNew in constructor. Connect DB and get data only in case of test set modification
 * 05/31/2016
 * - Add Jframe parameter in constructor and modify finishItem method to finish button close only this GUI screen
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
    private JFrame frame;

    private ArrayList<TestItem> testItems;
    private DefaultListModel listModel = new DefaultListModel();
    private TestItem tItem;
    private int testID;


    /**
     *  Constructor
     *  @param num testID
     *  @param isNew define new test set or modify existing test
     */
    public AdminSetupForm(JFrame pFrame, int num, boolean isNew){
        frame = pFrame;
        rootPanel.setPreferredSize(new Dimension(500,350));     // set size of window

        testItems = new ArrayList<>();               //declare arraylist and add items to the arraylist
        testID=num;

        if (!isNew == true) {    // only if modify exsist test set, Connect and get data from Database
            getItems();
            ckeckTakenTest();
            checkListSelected();
        }

        showItemList();
        checkItemNumber();

        //define action listeners
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

        itemList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {checkListSelected();}
        });
    }

    /**
     * Get item list array from TestItemDAO
     */
    public void getItems(){
        TestItemDAO tItemDAO = DAOFactory.getTestItemDAO();
        testItems =tItemDAO.getTestItems(testID);
    }

    /**
     * Show exist items on the list from DB
     */
    public void showItemList(){
       for(TestItem tItem : testItems) {
              listModel.addElement(tItem.getItemText());
       }
        itemList.setModel(listModel);
    }



    /**
     * Check weather test is taken or not. After any user takes test, item can not be changed
     */
    public void ckeckTakenTest(){
        TestSessionDAO tSessionDAO = DAOFactory.getTestSessionDAO();
        int count = tSessionDAO.countSession(testID);
          if (count > 0) {        // if this test set taken test more than once
              addButton.setEnabled(false);
              deleteButton.setEnabled(false);
              cancelButton.setEnabled(false);
              itemTextField.setEditable(false);
              itemList.setEnabled(false);
              takenLabel.setVisible(true);
          }
    }

   /**
    *  Check the number of item. If there are less than two items , finish button should be disabled
    */
    public void  checkItemNumber(){
        if(listModel.getSize()<2){
            finishButton.setEnabled(false);
        }else{
            finishButton.setEnabled(true);
        }
    }

    private void checkListSelected() {
        if (itemList.getSelectedIndex() == -1) {
            deleteButton.setEnabled(false);
        } else {
            deleteButton.setEnabled(true);
        }
    }

    /**
     *  Check input item is not duplicated.
     *  @return  boolean if it is duplicated return true
     */
    private boolean isDuplicate() {
        if (listModel.contains(itemTextField.getText())) {
            return true;
        } else {
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
            itemTextField.requestFocus();
            return false;
        } else if(itemTextField.getText().length() == 0) {        //check input is valid
            JOptionPane.showMessageDialog(rootPanel, "Please input item to add.");
            itemTextField.requestFocus();
            return false;
        }
        return true;
    }

    /**
     *  Check list is selected to delete
     *  @return  boolean if list is selected return true
     */
    private boolean isListSelected() {
        if (itemList.getSelectedIndex() == -1)      //no item is selected in the list
        {
            JOptionPane.showMessageDialog(rootPanel, "Please select item to delete.");
            return false;
        }
        return true;
    }

    /**
     * Add button add input item to DB
     */
    public void addItem() {
        TestItemDAO tItemDAO = DAOFactory.getTestItemDAO();
        if (isValidLength())     //item is valid length
            if (!isDuplicate() && isValidLength()) {       //item is not duplicated
                tItem = new TestItem(testID, itemTextField.getText());
                if (!tItemDAO.insertTestItem(tItem)) {
                    JOptionPane.showMessageDialog(rootPanel, "Failed to add item to database.");
                    itemTextField.requestFocus();
                } else {                                                   //query is executed query without error
                    listModel.addElement(itemTextField.getText());      //the item is added to the list
                    itemTextField.setText("");
                    itemTextField.requestFocus();
                    checkItemNumber();
                }
            }else {
                JOptionPane.showMessageDialog(rootPanel, "The item is already exist. Please enter a new item.");
                itemTextField.setText("");
                itemTextField.requestFocus();
            }
    }

    /**
     * Delete button delete the selected item from DB
     */
    public void deleteItem() {
        TestItemDAO tItemDAO = DAOFactory.getTestItemDAO();
        if(isListSelected()){
            tItem = new TestItem(testID, (String) itemList.getSelectedValue());
            if (!tItemDAO.deleteTestItem(tItem)) {
                //if (!testItemDAO.deleteTestItem(tItem)) {   //query is executed query without error
                JOptionPane.showMessageDialog(rootPanel, "Failed to delete user accounts from database.");
            }else{
                listModel.remove(itemList.getSelectedIndex());      //the item is removed from the list
                checkItemNumber();
            }
        }
    }

    /**
     * Finish button close GUI
     */
    public void finishItem(){
        frame.dispose();
    }

    /**
     * Cancel button clears item Text Field and clears selection of item List
     */
    public void cancelItem(){
        itemTextField.setText("");
        itemList.clearSelection();
    }

    /**
     * Return root panel to add to main frame
     * @return rootPanel the root panel that holds every elements
     */
    public JPanel getRootPanel(){
        return rootPanel;
    }
}
