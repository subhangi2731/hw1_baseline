

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.util.ArrayList;
import java.util.List; 

public class ExpenseTrackerView extends JFrame {

  private JTable transactionsTable;
  private JLabel amountErrorLabel;
  private JLabel categoryErrorLabel;
  private JButton addTransactionBtn;
  private JTextField amountField;
  private JTextField categoryField;
  private DefaultTableModel model;
  private List<Transaction> transactions = new ArrayList<>();
  JPanel errorPanel;

  InputValidation validation = new InputValidation();

  

  public JTable getTransactionsTable() {
    return transactionsTable;
  }

  public double getAmountField() {
    double amount = 0;

    try {
      amount = validation.validateAmount(amountField.getText());
      amountErrorLabel.setText("");
    } catch(NumberFormatException e) {

      // When amount is not a number
      amountErrorLabel.setText(e.getMessage());
      System.out.println(e.getMessage());
      return 0;
    } catch(IllegalArgumentException e) {

      // When amount is less than 0 or greater than 1000
      amountErrorLabel.setText(e.getMessage());
      System.out.println(e.getMessage());
      return 0;
    }
    return amount;
  }

  public void setAmountField(JTextField amountField) {
    this.amountField = amountField;
  }

  public String getCategoryField() {
    String category = null;

    try {
      category = validation.validateCategory(categoryField.getText());
      categoryErrorLabel.setText("");
    } catch (IllegalArgumentException e) {
      
      // When category is empty or doesn't match the predefined values
      categoryErrorLabel.setText(e.getMessage());
      System.out.println(e.getMessage());
      return null;
    }
    return category;
  }

  public void setCategoryField(JTextField categoryField) {
    this.categoryField = categoryField;
  }

  public JButton getAddTransactionBtn() {
    return addTransactionBtn;
  }
  public DefaultTableModel getTableModel() {
    return model;
  }

  public ExpenseTrackerView(DefaultTableModel model) {
    setTitle("Expense Tracker"); // Set title
    setSize(600, 400); // Make GUI larger
    this.model = model;

    addTransactionBtn = new JButton("Add Transaction");

    // Create UI components
    JLabel amountLabel = new JLabel("Amount:");
    amountField = new JTextField(10);
    
    JLabel categoryLabel = new JLabel("Category:");
    categoryField = new JTextField(10);
    transactionsTable = new JTable(model);
    
    // Amount and category error messages for UI
    amountErrorLabel = new JLabel("");
    amountErrorLabel.setForeground (Color.red);
    categoryErrorLabel = new JLabel("");
    categoryErrorLabel.setForeground (Color.red);

    // Error panel to show errors in UI
    errorPanel = new JPanel();
    errorPanel.setLayout(new BoxLayout(errorPanel, BoxLayout.Y_AXIS));
    errorPanel.add(amountErrorLabel);
    errorPanel.add(categoryErrorLabel);
    
    // Layout components
    JPanel inputPanel = new JPanel();
    inputPanel.add(amountLabel);
    inputPanel.add(amountField);
    inputPanel.add(categoryLabel); 
    inputPanel.add(categoryField);
    inputPanel.add(addTransactionBtn);

    // Panel which contains input fields and error messages
    JPanel inputErrorPanel = new JPanel();
    inputErrorPanel.setLayout(new BoxLayout(inputErrorPanel, BoxLayout.Y_AXIS));
    inputErrorPanel.add(inputPanel);
    inputErrorPanel.add(errorPanel);
  
    JPanel buttonPanel = new JPanel();
    buttonPanel.add(addTransactionBtn);
  
    // Add panels to frame
    add(inputErrorPanel, BorderLayout.NORTH);
    add(new JScrollPane(transactionsTable), BorderLayout.CENTER); 
    add(buttonPanel, BorderLayout.SOUTH);
  
    // Set frame properties
    setSize(400, 300);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
  
  }

  public void refreshTable(List<Transaction> transactions) {
      // model.setRowCount(0);
      model.setRowCount(0);
      int rowNum = model.getRowCount();
      double totalCost=0;
      for(Transaction t : transactions) {
        totalCost+=t.getAmount();
      }
  
      // Add rows from transactions list
      for(Transaction t : transactions) {
        model.addRow(new Object[]{rowNum+=1,t.getAmount(), t.getCategory(), t.getTimestamp()});

      }
      Object[] totalRow = {"Total", null, null, totalCost};
      model.addRow(totalRow);
  
      // Fire table update
      transactionsTable.updateUI();
  
    }  

  public void refresh() {

    // Get transactions from model
    List<Transaction> transactions = getTransactions();
  
    // Pass to view
    refreshTable(transactions);
  
  }

  public List<Transaction> getTransactions() {
    return transactions;
  }
  
  public void addTransaction(Transaction t) {
    transactions.add(t);
    getTableModel().addRow(new Object[]{t.getAmount(), t.getCategory(), t.getTimestamp()});
    refresh();
  }
  


  // Other view methods
}
