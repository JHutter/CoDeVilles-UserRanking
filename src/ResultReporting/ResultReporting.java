package ResultReporting;

import ContainerClasses.TestResult;
import ContainerClasses.UserAccount;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Legoman60 on 4/19/2016.
 */
public class ResultReporting {
    private JComboBox nameBox;
    private JPanel rootPanel;
    private JButton finishButton;
    private JList resultList;

    private ArrayList<UserAccount> userAccounts;
    private ArrayList<TestResult> testResults;

    /**
     * Default Constructor
     */
    public ResultReporting() {
        //Set the size of the panel to agreed value
        rootPanel.setPreferredSize(new Dimension(500, 350));
    }

    /**
     * @return rootPanel the root panel holding all other content
     */
    public JPanel getRootPanel(){return rootPanel;}
}
