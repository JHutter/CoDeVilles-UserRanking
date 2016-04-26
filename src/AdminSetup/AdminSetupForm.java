package AdminSetup;

import ContainerClasses.TestItem;
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
 * Create Date 4/18/2016
 *
 * @author   Jinsook Lee
 * @version  4/24/2016
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
    private DefaultListModel listModel = new DefaultListModel();
    private TestItem titem;
    //private TestItem titem;
    private boolean isTestTaken = false;

    /**
     *  Constructor
     */
    public AdminSetupForm(){

        rootPanel.setPreferredSize(new Dimension(500,350));     // set size of window

        databaseManager = new DatabaseManager();    //create instance of DatabaseManager

        testItems = new ArrayList<>();               //declare arraylist and add items to the arraylist
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
    public void addItem(){
        titem = new TestItem(1, itemTextField.getText());
        if(databaseManager.insertTestItem(titem)){   //query is executed query without error
            JOptionPane.showMessageDialog(rootPanel, itemTextField.getText()+ " is added successfully.");
            listModel.addElement(itemTextField.getText());      //the item is added to the list
            itemTextField.setText("");
        }
    }

    /**
     * Delete button delete the selected item from DB
     */
    public void deleteItem(){
        titem = new TestItem(1, (String) itemList.getSelectedValue());
        if(databaseManager.deleteTestItem(titem)){   //query is executed query without error
            JOptionPane.showMessageDialog(rootPanel, itemList.getSelectedValue()+" is deleted successfully.");
            listModel.remove(itemList.getSelectedIndex());      //the item is removed from the list
        }
    }

    /**
     * Finish button shows message and redirect to menu page
     */
    public void finishItem(){
        JOptionPane.showMessageDialog(rootPanel, "Finished.");
    }

    /**
     * Cancel button clears item Text Field and clears selection of item List
     */
    public void cancelItem(){
        itemTextField.setText("");
        itemList.clearSelection();

        JOptionPane.showMessageDialog(rootPanel, "The operation is canceled.");
    }

    /**
     * Check weather test is taken or not. After any user takes test, item can not be changed
     */
    public void ckeckTakenTest(){
        takenLabel.setVisible(false);
        if (isTestTaken)
        {
            addButton.setEnabled(false);
            deleteButton.setEnabled(false);
            takenLabel.setVisible(true);
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
     * Return root panel to add to main frame
     * @return rootPanel the root panel that holds every elements
     */
    public JPanel getRootPanel(){
        return rootPanel;
    }
}
