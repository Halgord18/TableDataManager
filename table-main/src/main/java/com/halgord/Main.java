package com.halgord;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.stream.Collectors;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;

import com.halgord.swing.Background;
// import com.itextpdf.io.font.CFFFont.Font;
// import com.itextpdf.kernel.geom.Vector;
// import com.itextpdf.layout.element.List;
import com.halgord.swing.TableFunctions;

public class Main extends javax.swing.JFrame {
    

    private Animator animatorLogin;
    private Animator animatorBody;
    private boolean signIn;
    public String TableName;
    List<String> inputDataList = new ArrayList<>(); // List to store IDs
    List<List<Object>> tableData = new ArrayList<>();
    List<String> idlist = new ArrayList<>();
    List<Map<String, Object>> Insertlist = new ArrayList<>();
    List<Map<String, String>> updateList = new ArrayList<>();
    TableFunctions newFunctions = new TableFunctions();
    DefaultTableModel tableModel = new DefaultTableModel();

    public Main() {
        initComponents();
        getContentPane().setBackground(new Color(245, 245, 245));
        TimingTarget targetLogin = new TimingTargetAdapter() {
            @Override
            public void timingEvent(float fraction) {
                if (signIn) {
                    background1.setAnimate(fraction);
                } else {
                    background1.setAnimate(1f - fraction);
                }
            }

            @Override
            public void end() {
                if (signIn) {
                    populateTable(jTable1, jScrollPane1);
                    panelLogin.setVisible(false);
                    background1.setShowPaint(true);
                    panelBody.setAlpha(0);
                    panelBody.setVisible(true);
                    animatorBody.start();
                } else {
                    enableLogin(true);
                    txtUser.grabFocus();
                }
            }
        };
        TimingTarget targetBody = new TimingTargetAdapter() {
            @Override
            public void timingEvent(float fraction) {
                if (signIn) {
                    panelBody.setAlpha(fraction);
                } else {
                    panelBody.setAlpha(1f - fraction);
                }
            }

            @Override
            public void end() {
                if (signIn == false) {
                  
                    panelBody.setVisible(false);
                    background1.setShowPaint(false);
                    background1.setAnimate(1);
                    panelLogin.setVisible(true);
                    animatorLogin.start();
                }
            }
        };
        animatorLogin = new Animator(1500, targetLogin);
        animatorBody = new Animator(500, targetBody);
        animatorLogin.setResolution(0);
        animatorBody.setResolution(0);
        jScrollPane1.getViewport().setOpaque(false);
        jScrollPane1.setViewportBorder(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        background1 = new Background();
        panelLogin = new JPanel();
        jPanel1 = new JPanel();
        jLabel1 = new JLabel();
        cmdSignIn = new com.halgord.swing.Button();
        txtUser = new com.halgord.swing.TextField();
        txtPass = new com.halgord.swing.PasswordField();
        panelBody = new com.halgord.swing.PanelTransparent();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        addButton = new javax.swing.JButton();
         updateButton = new javax.swing.JButton();
         removeButton = new javax.swing.JButton();
         commitButton = new javax.swing.JButton();
         savePointButton = new javax.swing.JButton();
         rollBackButton = new javax.swing.JButton();
         showTableButton = new javax.swing.JButton();
         CreateTableButton = new javax.swing.JButton();
         exportButton = new javax.swing.JButton();
        header1 = new com.raven.component.Header();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        background1.setLayout(new java.awt.CardLayout());

        panelLogin.setOpaque(false);

        jPanel1.setOpaque(false);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/logo.png"))); // NOI18N

        panelBody.add(jButton1);
        panelBody.add(addButton);
        panelBody.add(updateButton);
        panelBody.add(removeButton);
         panelBody.add(commitButton);
         panelBody.add(savePointButton);
         panelBody.add(rollBackButton);
         panelBody.add(showTableButton);
         panelBody.add(CreateTableButton);
         panelBody.add(exportButton);

        jButton1.setBounds(50, 100, 100, 30);
        addButton.setBounds(8, 10, 100, 30);
         updateButton.setBounds(120, 10, 100, 30);
         removeButton.setBounds(230, 10, 100, 30);
         commitButton.setBounds(10, 562, 100, 30);
         savePointButton.setBounds(120, 562, 100, 30);
         rollBackButton.setBounds(230, 562, 100, 30);
         showTableButton.setBounds(750, 10, 100, 30);
         CreateTableButton.setBounds(860, 10, 100, 30);
         exportButton.setBounds(970, 10, 100, 30);
        


        cmdSignIn.setBackground(new java.awt.Color(157, 153, 255));
        cmdSignIn.setForeground(new java.awt.Color(255, 255, 255));
        cmdSignIn.setText("Sign In");
        cmdSignIn.setEffectColor(new java.awt.Color(199, 196, 255));
        cmdSignIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdSignInActionPerformed(evt);
            }
        });

        txtUser.setBackground(new java.awt.Color(245, 245, 245));
        txtUser.setLabelText("User Name");
        txtUser.setLineColor(new java.awt.Color(131, 126, 253));
        txtUser.setSelectionColor(new java.awt.Color(157, 153, 255));

        txtPass.setBackground(new java.awt.Color(245, 245, 245));
        txtPass.setLabelText("Password");
        txtPass.setLineColor(new java.awt.Color(131, 126, 253));
        txtPass.setSelectionColor(new java.awt.Color(157, 153, 255));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtPass, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmdSignIn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 310, Short.MAX_VALUE))
                .addGap(20, 20, 20))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1)
                .addGap(20, 20, 20)
                .addComponent(txtUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addComponent(cmdSignIn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout panelLoginLayout = new javax.swing.GroupLayout(panelLogin);
        panelLogin.setLayout(panelLoginLayout);
        panelLoginLayout.setHorizontalGroup(
            panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLoginLayout.createSequentialGroup()
                .addContainerGap(427, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(428, Short.MAX_VALUE))
        );
        panelLoginLayout.setVerticalGroup(
            panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLoginLayout.createSequentialGroup()
                .addContainerGap(63, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(233, Short.MAX_VALUE))
        );

        background1.add(panelLogin, "card2");

         jButton1.setText("Sign Out");
         addButton.setText("insert");
         updateButton.setText("update");
         removeButton.setText("remove");
         commitButton.setText("commit");
         savePointButton.setText("savePoint");
         rollBackButton.setText("rollBack");
         showTableButton.setText("showTable");
         CreateTableButton.setText("CreateTable");
         exportButton.setText("export");

         
         jButton1.setBackground(Color.YELLOW);
         addButton.setBackground(Color.CYAN);
         updateButton.setBackground(Color.CYAN);
         removeButton.setBackground(Color.CYAN);
         commitButton.setBackground(Color.magenta);
         savePointButton.setBackground(Color.magenta);
         rollBackButton.setBackground(Color.magenta);
         showTableButton.setBackground(Color.PINK);
         CreateTableButton.setBackground(Color.PINK);
         exportButton.setBackground(Color.PINK);

        Font newButtonFont = new Font("Arial", Font.BOLD, 12);

        jButton1.setFont(newButtonFont);
        addButton.setFont(newButtonFont);
        updateButton.setFont(newButtonFont);
        removeButton.setFont(newButtonFont);
        commitButton.setFont(newButtonFont);
        savePointButton.setFont(newButtonFont);
        rollBackButton.setFont(newButtonFont);
        showTableButton.setFont(newButtonFont);
        CreateTableButton.setFont(newButtonFont);
        exportButton.setFont(newButtonFont);
         

         
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
       
        addButton.addActionListener(new ActionListener() { 

         @Override public void actionPerformed(ActionEvent e) {
         
         
         int columnCount = tableModel.getColumnCount();

         Map<String, String> inputData = new LinkedHashMap<>();
         for (int i = 0; i < columnCount; i++) {
             String columnName = tableModel.getColumnName(i);
             String input = JOptionPane.showInputDialog(null, "Enter " + columnName + " (" + newFunctions.getColumnType(TableName, columnName) + "):", "Input for " + columnName, JOptionPane.QUESTION_MESSAGE);
             
             if (input == null) {
                 // User clicked Cancel or closed the dialog
                 JOptionPane.showMessageDialog(null, "No input provided for " + columnName + ". Operation cancelled.", "Input Cancelled", JOptionPane.WARNING_MESSAGE);
                 return;
             }
             
               if (idlist.contains(input)&&i==0) {
                 JOptionPane.showMessageDialog(null, "ID " + input + " already exists.", "Duplicate ID", JOptionPane.WARNING_MESSAGE);
                 return;
               }
             inputData.put(columnName, input);
         }
          System.out.println(inputData+" input data");
         
         // Assume rules() is modified to take a Map
         boolean bool = rules(inputData, false, false, TableName);

         if (bool) {
             // Convert Map<String, String> to Map<String, Object>
             Map<String, Object> rowData = new HashMap<>();
             for (Map.Entry<String, String> entry : inputData.entrySet()) {
                 String columnName = entry.getKey();
                 String value = entry.getValue();
 
                 // Assuming you have a method getColumnType that returns the type of the column
                 String columnType = newFunctions.getColumnType(TableName,columnName);
                   System.out.println(columnType);
                 if (columnType.equals("int")) {
                     rowData.put(columnName, Integer.parseInt(value));
                 } else if (columnType.equals("double")) {
                     rowData.put(columnName, Double.parseDouble(value));
                 } else {
                     rowData.put(columnName, value);
                 }
             }
             Insertlist.add(rowData);
             System.out.println(Insertlist);
            //    boolean bool1 = newFunctions.insertRow(TableName, rowData); 
               if (bool) {
                 // Assume createRowObject() is a method that creates an Object[] from inputData
                 insertRowInOrder(tableModel, createRowObject(inputData));
             }
             
         } else {
             // Handle invalid input
            //  JOptionPane.showMessageDialog(null, "The input provided is invalid. Please try again.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
         }
     }
 });

         updateButton.addActionListener(new ActionListener() { 
    @Override 
    public void actionPerformed(ActionEvent e) { 
        String idString = JOptionPane.showInputDialog("Enter the ID of the row to update:"); 
        System.out.println(idString + " idString"); // Debugging print statement
        if (idString != null && !idString.isEmpty()) { 
            try { 
                if (tableModel.getRowCount() == 0) { 
                    JOptionPane.showMessageDialog(null, "The table is empty. There are no rows to update."); 
                    return; 
                }
                int rowIndex = -1;
                boolean idFound = false;
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    if (tableModel.getValueAt(i, 0).toString().equals(idString)) {
                        rowIndex = i;
                        idFound = true;
                        break;
                    }
                }
                if (!idFound) {
                    JOptionPane.showMessageDialog(null, "The ID was not found in the table. Please enter a valid ID.");
                    return;
                }

                int columnCount = tableModel.getColumnCount();
                Map<String, String> inputData = new LinkedHashMap<>();
                for (int i = 1; i < columnCount; i++) { // Start from 1 to skip the ID column
                    String columnName = tableModel.getColumnName(i);
                    String input = JOptionPane.showInputDialog("Enter new value for " + columnName);
                    inputData.put(columnName, input);
                }
                System.out.println(inputData + " input data"); // Debugging print statement

                boolean bool = rules(inputData, false, true, TableName);

                if (bool) {
                    Map<String, String> rowData = new HashMap<>();
                    rowData.put(tableModel.getColumnName(0), idString); // Retain the original ID
                    for (Map.Entry<String, String> entry : inputData.entrySet()) {
                        String columnName = entry.getKey();
                        String value = entry.getValue();
                        String columnType = newFunctions.getColumnType(TableName, columnName);
                        if (columnType.equals("int")) {
                            rowData.put(columnName, String.valueOf(Integer.parseInt(value)));
                        } else if (columnType.equals("double")) {
                            rowData.put(columnName, String.valueOf(Double.parseDouble(value)));
                        } else {
                            rowData.put(columnName, value);
                        }
                    }
                    updateList.add(rowData); // Add the updated row to updateList

                    // Update the table model
                    if (rowIndex != -1) {
                        for (int i = 0; i < columnCount; i++) {
                            String columnName = tableModel.getColumnName(i);
                            tableModel.setValueAt(rowData.get(columnName), rowIndex, i);
                        }
                    }
                } else {
                    // Handle invalid input
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid ID. Please enter a numeric ID.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "The ID field cannot be empty.");
        }
    }
}); 
       
         removeButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
          String idString = JOptionPane.showInputDialog("Enter the IDs of the rows to delete (separated by comma):");
          if (idString != null && !idString.isEmpty()) {
              // Check if the table is empty
              if (tableModel.getRowCount() == 0) {
                  JOptionPane.showMessageDialog(null, "The table is empty. There are no rows to update.");
                  return;
              }
  
              String[] idStrings = idString.split(",");
              Set<String> deletedIds = new HashSet<>();
              Set<String> notFoundIds = new HashSet<>();
              for (String idStr : idStrings) {
                  idStr = idStr.trim();
                  // Get the column name dynamically
                  String columnName = tableModel.getColumnName(0);
                  // Modify the delete method call
                //   newFunctions.delete(TableName, columnName, idStr);
                  boolean found = false;
                  for (int i = 0; i < tableModel.getRowCount(); i++) {
                      if (tableModel.getValueAt(i, 0).toString().equals(idStr)) {
                          tableModel.removeRow(i);
                          deletedIds.add(idStr);
                          idlist.remove(idStr);
                          System.out.println(idlist+" idlist"); // Debugging print statement
                          inputDataList.add(idStr);
                          found = true;
                          break;
                      }
                  }
                  if (!found) {
                      notFoundIds.add(idStr);
                  }
              }
              if (!deletedIds.isEmpty()) {
                  JOptionPane.showMessageDialog(null, "Rows with IDs [ " + String.join(", ", deletedIds) + " ] have been deleted.");
              }
              if (!notFoundIds.isEmpty()) {
                  JOptionPane.showMessageDialog(null, "Rows with IDs [ " + String.join(", ", notFoundIds) + " ] were not found.");
              }
          }
          else {
              JOptionPane.showMessageDialog(null, "The ID field cannot be empty.");
          }
      }
  });

         commitButton.addActionListener(new ActionListener() {
               @Override
               public void actionPerformed(ActionEvent e) {
                  boolean bool = false;
                  newFunctions.pendingDelete="a";
                  newFunctions.pendingInsert = "a";
                  newFunctions.pendingUpdate = "a";
                  for (Object item : Insertlist) {
                    System.out.println(item);
                    if (inputDataList.contains(item)) {
                        System.out.println(item+"contains"); // Debugging print statement
                        inputDataList.remove(item);
                    }
                }
                
                  if (inputDataList != null && !inputDataList.isEmpty()) {
                     System.out.println(inputDataList); // Debugging print statement
                     for (String idStr : inputDataList) {
                           idStr = idStr.trim();
                           // Get the column name dynamically
                           String columnName = tableModel.getColumnName(0);
                           // Call the delete method
                           newFunctions.delete(TableName, columnName, idStr);
                     }
                     // Clear the inputDataLists after deleting from database
                     bool = true;
                     newFunctions.pendingDelete=null;
                     inputDataList.clear();
                  }

                  if (Insertlist != null && !Insertlist.isEmpty()) {
                     for (Map<String, Object> rowData : Insertlist) {
                           newFunctions.insertRow(TableName, rowData);
                     }
                     // Clear the pendingInsert list after inserting into database
                     bool = true;
                     newFunctions.pendingInsert = null;
                     Insertlist.clear();
                  }

                    // Commit the updates
                    System.out.println(updateList); // Debugging print statement
                    if (!updateList.isEmpty()) {
                        for (Map<String, String> rowData : updateList) {
                            Iterator<String> columnNamesIterator = rowData.keySet().iterator();
                            String idColumnName = columnNamesIterator.next(); // get the first column name
                            String id = rowData.get(idColumnName); // get the ID value
                            while (columnNamesIterator.hasNext()) { // iterate over the rest of the columns
                                String columnName = columnNamesIterator.next();
                                Map<String, String> singleUpdate = new HashMap<>();
                                singleUpdate.put(columnName, rowData.get(columnName));
                                newFunctions.updateRecords(TableName, idColumnName, singleUpdate, id);
                            }
                        }
                        // Clear the updateList after committing the updates
                        updateList.clear();
                        newFunctions.pendingUpdate = null;
                        bool = true;
                    }
                if (newFunctions.isTrue) {
                    
                  if (bool && newFunctions.isTrue) {
                        JOptionPane.showMessageDialog(null, "Changes have been committed.");
                  }
                  else {
                     JOptionPane.showMessageDialog(null, "There are no pending changes to commit.");
                  }
               }
               newFunctions.isTrue = true;
            } 
        });

         showTableButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                   populateTable(jTable1, jScrollPane1);
            }
         });

         savePointButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               // Get the table model
               TableModel model = jTable1.getModel();

               // Save the table data to the list
               for (int i = 0; i < model.getRowCount(); i++) {
                  List<Object> row = new ArrayList<>();
                  for (int j = 0; j < model.getColumnCount(); j++) {
                        row.add(model.getValueAt(i, j));
                  }
                  tableData.add(row);
               }

               // Show a message dialog
               JOptionPane.showMessageDialog(null, "Table data saved successfully.");
            }
         });

         rollBackButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                           // Get the table model
                           TableModel model = jTable1.getModel();

                           // Create a list to save the current table data
                           List<List<Object>> currentTableData = new ArrayList<>();
                          if (!tableData.isEmpty()) {
                           // Save the current table data to the list
                           for (int i = 0; i < model.getRowCount(); i++) {
                              List<Object> row = new ArrayList<>();
                              for (int j = 0; j < model.getColumnCount(); j++) {
                                    row.add(model.getValueAt(i, j));
                              }
                              currentTableData.add(row);
                           }

                           // Check if the current table data is the same as the saved table data
                           if (currentTableData.equals(tableData)) {
                              // Show a message dialog
                              JOptionPane.showMessageDialog(null, "The current table data is the same as the saved table data. No rollback needed.");
                           } else {
                              // Clear the current table data
                              while (model.getRowCount() > 0) {
                                    ((DefaultTableModel) model).removeRow(0);
                              }

                              // Replace the current table data with the saved table data
                              for (List<Object> row : tableData) {
                                    ((DefaultTableModel) model).addRow(row.toArray());
                              }

                              // Show a message dialog
                              JOptionPane.showMessageDialog(null, "Table data rolled back successfully.");
                           }
                          }
                           
                        }
                     });

         CreateTableButton.addActionListener(new ActionListener() {
               @Override
               public void actionPerformed(ActionEvent e) {
                  newFunctions.createTable();
               }
                     });

         exportButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
         String filePath = "D:\\exportSql\\halgord.pdf"; // replace with your file path
         try {
            newFunctions.exportTableData(TableName, filePath);
            JOptionPane.showMessageDialog(null, TableName+" Table datas exported successfully.");
        } catch (IOException e1) {
            e1.printStackTrace();
        }
         
        }
     });

        javax.swing.GroupLayout panelBodyLayout = new javax.swing.GroupLayout(panelBody);
        panelBody.setLayout(panelBodyLayout);
        panelBodyLayout.setHorizontalGroup(
            panelBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBodyLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBodyLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton1)))
                .addContainerGap())
            .addComponent(header1, javax.swing.GroupLayout.DEFAULT_SIZE, 1205, Short.MAX_VALUE)
        );
        panelBodyLayout.setVerticalGroup(
            panelBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBodyLayout.createSequentialGroup()
                .addComponent(header1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 504, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap())
        );

        background1.add(panelBody, "card3");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

      public boolean rules(Map<String, String> inputData,boolean bool, boolean isUpdate, String tableName) {
              StringBuilder errorMessage = new StringBuilder();
              bool = true;
          
              Map<String, Map<String, Object>> columnData;
              try {
                  columnData = newFunctions.getColumnTypes(tableName);
              } catch (Exception e) {
                  errorMessage.append("Database error: ").append(e.getMessage()).append("\n");
                  return false;
              }
          
              SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
          
              for (Map.Entry<String, String> entry : inputData.entrySet()) {
                  String columnName = entry.getKey();
                  String value = entry.getValue();
                  Map<String, Object> columnInfo = columnData.get(columnName);
          
                  if (columnInfo == null) {
                     bool = true;
                     continue;
                  }
          
                  String columnType = (String) columnInfo.get("type");
                  int columnSize = (int) columnInfo.get("size");

               System.out.println(columnSize); // Debugging print statement
      
          if (columnType.equals("Number")) {
              if (columnName.toLowerCase().endsWith("id")) {
                  // This is an ID column, so it should not be null
                  if (value == null || value.isEmpty()) {
                      errorMessage.append("Invalid input: '").append(columnName).append("' cannot be null or empty. You entered: ").append(value).append("\n");
                      bool = false;
                  } else {
                      try {
                          Integer.parseInt(value);
                      } catch (NumberFormatException e) {
                          errorMessage.append("Invalid input: '").append(columnName).append("' should be a valid integer. You entered: ").append(value).append("\n");
                          bool = false;
                      }
                  }
              } else {
                  // This is a regular Number column, so it can be null
                  if (value != null && !value.isEmpty()) {
                      try {
                          Double.parseDouble(value);
                      } catch (NumberFormatException e) {
                          errorMessage.append("Invalid input: '").append(columnName).append("' should be a valid number. You entered: ").append(value).append("\n");
                          bool = false;
                      }
                  }
              }
          }
                  else if (columnType.equals("String") && !value.matches("[a-zA-Z_]+")) {
                      errorMessage.append("Invalid input: '").append(columnName).append("' should only contain letters. You entered: ").append(value).append("\n");
                      bool = false;
                  }
      
                  if (columnType.equals("Date")) {
                      try {
                          dateFormat.parse(value);
                      } catch (Exception e) {
                          errorMessage.append("Invalid input: '").append(columnName).append("' should be a valid date in the format yyyy-MM-dd. You entered: ").append(value).append("\n");
                          bool = false;
                      }
                  }
                  else if (columnType.equals("Phone")) {
                      if (value != null && !value.isEmpty()) {
                          if (!value.matches("^[+]?[0-9.]{1,15}$")) {
                              errorMessage.append("Invalid input: '").append(columnName).append("' should be a valid phone number. You entered: ").append(value).append("\n");
                              bool = false;
                          }
                      }
                  }
                
                  if (!columnType.equals("Date") && value.length() > columnSize) {
                      errorMessage.append("Invalid input: '").append(columnName).append("' should not exceed ").append(columnSize).append(" characters. You entered: ").append(value).append("\n");
                      bool = false;
                  }
              }
          
              if (errorMessage.length() > 0) {
                  JOptionPane.showMessageDialog(null, errorMessage.toString());
              }
          System.out.println(bool); // Debugging print statement

              return bool;
          }


      public String showTables() {
            
          List<String> tables = newFunctions.getTableNames();
          DefaultListModel<String> model = new DefaultListModel<>();
          tables.forEach(model::addElement);
          JList<String> list = new JList<>(model);
          list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
          list.setLayoutOrientation(JList.VERTICAL);
          list.setVisibleRowCount(-1);
      
          JScrollPane listScroller = new JScrollPane(list);
          listScroller.setPreferredSize(new Dimension(250, 420));
      
          JTextField searchField = new JTextField();
          searchField.getDocument().addDocumentListener(new DocumentListener() {
              public void insertUpdate(DocumentEvent e) { filter(); }
              public void removeUpdate(DocumentEvent e) { filter(); }
              public void changedUpdate(DocumentEvent e) { filter(); }
              void filter() {
          String filter = searchField.getText();
          List<String> filteredTables;
          if (filter.isEmpty()) {
              filteredTables = new ArrayList<>(tables);
          } else {
              filteredTables = tables.stream()
                  .filter(table -> table.toLowerCase().contains(filter.toLowerCase()))
                  .collect(Collectors.toList());
          }
          model.clear(); // Clear the model before adding elements to it
          filteredTables.forEach(model::addElement);
      }
      
          });
      
          JLabel searchLabel = new JLabel("Search:");
          searchLabel.setLabelFor(searchField);
      
          JPanel panel = new JPanel(new BorderLayout());
          panel.add(searchLabel, BorderLayout.NORTH);
          panel.add(searchField, BorderLayout.CENTER);
          panel.add(listScroller, BorderLayout.SOUTH);
      
          JOptionPane.showMessageDialog(
              null, panel, "Table Selection", JOptionPane.PLAIN_MESSAGE
          );
          return list.getSelectedValue();
      }
      
      public void populateTable(JTable jTable1, JScrollPane jScrollPane1) {

          inputDataList.clear();
          Insertlist.clear();
          idlist.clear();
          // Create a DefaultTableModel without column names
           tableModel = new DefaultTableModel();
           
          jTable1.setModel(tableModel);
          jTable1.setRowSorter(null); // Clear the previous sorter
          // Retrieve data from the database and populate the table
          System.out.println(idlist+" idlist");
          try {
            TableName = null;
              while (TableName == null) {
                  TableName = showTables();
                  if (TableName == null) {
                      // Handle the case where no table is selected...
                      JOptionPane.showMessageDialog(null, "No table was selected. Please select a table.");
                  }
              }
      
              ResultSet rs = newFunctions.getData(TableName);
              System.out.println(TableName);
              ResultSetMetaData rsmd = rs.getMetaData();
              int columnCount = rsmd.getColumnCount();
      
              // Retrieve column names from ResultSetMetaData and add to table model
              Vector<String> columnNames = new Vector<>();
              for (int i = 1; i <= columnCount; i++) {
                  columnNames.add(rsmd.getColumnName(i));
              }
              tableModel.setColumnIdentifiers(columnNames);
      
              // Populate the table with data
              while (rs.next()) {
                  Vector<Object> row = new Vector<>();
                  for (int i = 1; i <= columnCount; i++) {
                      row.add(rs.getObject(i));
                  }
                  tableModel.addRow(row);
                  idlist.add(rs.getString(1));
                  System.out.println(idlist+" idlist");
              }
      
              TableRowSorter<TableModel> sorter = new TableRowSorter<>(tableModel);
              jTable1.setRowSorter(sorter);
              // Set a custom Comparator for the first column
              sorter.setComparator(0, new Comparator<Object>() {
                  @Override
                  public int compare(Object o1, Object o2) {
                      if (o1 instanceof Integer && o2 instanceof Integer) {
                          Integer int1 = (Integer) o1;
                          Integer int2 = (Integer) o2;
                          return int1.compareTo(int2);
                      } else if (o1 instanceof BigDecimal && o2 instanceof BigDecimal) {
                          BigDecimal bd1 = (BigDecimal) o1;
                          BigDecimal bd2 = (BigDecimal) o2;
                          return bd1.compareTo(bd2);
                      } else if (o1 instanceof String && o2 instanceof String) {
                          String str1 = (String) o1;
                          String str2 = (String) o2;
                          return str1.compareTo(str2);
                      } else {
                          // If the objects are not integers, big decimals or strings, don't sort
                          return 0;
                      }
                  }
              });
      
      
              // Create a list of sort keys
              List<RowSorter.SortKey> sortKeys = new ArrayList<>();
              sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
      
              // Set the sort keys
              sorter.setSortKeys(sortKeys);
          } catch (Exception e) {
              JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage());
          }
      
          // Set the view for jScrollPane1
          jScrollPane1.setViewportView(jTable1);
          jTable1.setModel(tableModel);
          // Set jTable1 properties
          jTable1.setEnabled(false);
          jTable1.getTableHeader().setReorderingAllowed(false);
      }

      public void insertRowInOrder(DefaultTableModel model, Object[] newRow) {
         if (newRow.length == 0) {
             JOptionPane.showMessageDialog(null, "New row is empty");
             return;
         }
         Object firstElement = newRow[0];
         BigDecimal id = BigDecimal.ZERO;
         boolean isNumber = true;
     
         if (firstElement instanceof Integer) {
             id = BigDecimal.valueOf((Integer) firstElement);
         } else if (firstElement instanceof BigDecimal) {
             id = (BigDecimal) firstElement;
         } else if (firstElement instanceof String) {
             isNumber = false;
         } else {
             throw new IllegalArgumentException("First element of newRow must be an integer, a string, or a BigDecimal");
         }
     
         int rowCount = model.getRowCount();
     
         // Find the correct position
         int insertPos = 0;
         if (isNumber) {
             for (; insertPos < rowCount; insertPos++) {
                 Object valueAtPos = model.getValueAt(insertPos, 0);
                 BigDecimal valueAtPosAsBigDecimal;
                 if (valueAtPos instanceof Integer) {
                     valueAtPosAsBigDecimal = BigDecimal.valueOf((Integer) valueAtPos);
                 } else if (valueAtPos instanceof BigDecimal) {
                     valueAtPosAsBigDecimal = (BigDecimal) valueAtPos;
                 } else {
                     throw new IllegalArgumentException("Value at position " + insertPos + " is not a BigDecimal or an Integer");
                 }
                 if (id.compareTo(valueAtPosAsBigDecimal) < 0) {
                     break;
                 }
             }
         } else {
             insertPos = rowCount; // If not a number, insert at the end
         }
     
         // Insert the new row at the correct position
         model.insertRow(insertPos, newRow);
     }
     
     public Object[] createRowObject(Map<String, String> inputData) {
      // Convert the inputData map to an Object array
      Object[] rowObject = new Object[inputData.size()];
      int index = 0;
      for (Map.Entry<String, String> entry : inputData.entrySet()) {
          rowObject[index++] = entry.getValue();
      }
      return rowObject;
  }

    private void cmdSignInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdSignInActionPerformed
        if (!animatorLogin.isRunning()) {
            signIn = true;
            String user = txtUser.getText().trim();
            String pass = String.valueOf(txtPass.getPassword());
            boolean action = true;
            if (!user.equals("hr")) {
                txtUser.setHelperText("Please input user name");
                txtUser.grabFocus();
                action = false;
            }
            if (!pass.equals("hr")) {
                txtPass.setHelperText("Please input password");
                if (action) {
                    txtPass.grabFocus();
                }
                action = false;
            }
            if (action) {
                animatorLogin.start();
                enableLogin(false);
            }
        }
    }//GEN-LAST:event_cmdSignInActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        signIn = false;
        clearLogin();
        animatorBody.start();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void enableLogin(boolean action) {
        txtUser.setEditable(action);
        txtPass.setEditable(action);
        cmdSignIn.setEnabled(action);
    }

    public void clearLogin() {
        txtUser.setText("");
        txtPass.setText("");
        txtUser.setHelperText("");
        txtPass.setHelperText("");
    }

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.halgord.swing.Background background1;
    private com.halgord.swing.Button cmdSignIn;
    private Component header1;
    private javax.swing.JButton jButton1;
    public javax.swing.JButton addButton;
    public javax.swing.JButton updateButton;
    public javax.swing.JButton removeButton;
    public javax.swing.JButton commitButton;
    public javax.swing.JButton savePointButton;
    public javax.swing.JButton rollBackButton;
    public javax.swing.JButton showTableButton;
    public javax.swing.JButton CreateTableButton;
    public javax.swing.JButton exportButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private com.halgord.swing.PanelTransparent panelBody;
    private javax.swing.JPanel panelLogin;
    private com.halgord.swing.PasswordField txtPass;
    private com.halgord.swing.TextField txtUser;
    // End of variables declaration//GEN-END:variables
}