package com.halgord.swing;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLRecoverableException;
import java.sql.Statement;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;



public class TableFunctions {
    private static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:orcla";
    private static final String USERNAME = "hr";
    private static final String PASSWORD = "hr";
    static ArrayList<DvdReturner> dvdArray = new ArrayList<>();
    static ResultSet resultSet = null;
    public boolean isTrue = true;
    boolean isTrue2 = false;
    public Connection connect;
    public String  pendingDelete = null;
    public String pendingInsert = null;
    public String pendingUpdate = null;
private Connection connection = null;
private DefaultTableModel tableModel;  // Add this line
LinkedList<PreparedStatement> operations = new LinkedList<>();

    public TableFunctions() {
   
    try {
        Class.forName(JDBC_DRIVER);
        connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
        if (connection != null) {
        }
    } catch (ClassNotFoundException | SQLException e) {
        e.printStackTrace();
    }
}

    public TableFunctions(DefaultTableModel tableModel) {
    this.tableModel = tableModel;
}

    public Connection getInstance() {
    try {
        if (connection == null || connection.isClosed()) {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            connection.setAutoCommit(true); // Add this line
        }
    } catch (ClassNotFoundException | SQLException e) {
        e.printStackTrace();
    }
    return connection;
}

    public void delete(String tableName, String columnName, String id) {
    String sql = "DELETE FROM " + tableName + " WHERE " + columnName + " = ?";

    try {Connection connect = getInstance();
        PreparedStatement deleteStmt = connect.prepareStatement(sql);
        deleteStmt.setString(1, id);
        if (connect != null || !connect.isClosed()) {
            connect = getInstance();
        }
       
        if (pendingDelete != null) {
            System.out.println("Pending delete: " + pendingDelete);
            deleteStmt.executeUpdate();
            
        }
    } catch (SQLException e) {
        getInstance();
        isTrue = false;
    }
}
    public boolean insertRow(String tableName, Map<String, Object> rowData) {

    if (rowData.isEmpty()) {
        JOptionPane.showMessageDialog(null, "No data was entered. Please enter data.");
        return false;
    }
    StringBuilder columns = new StringBuilder();
    StringBuilder placeholders = new StringBuilder();
    for (String column : rowData.keySet()) {
        if (columns.length() > 0) {
            columns.append(", ");
            placeholders.append(", ");
        }
        columns.append("\"").append(column).append("\"");
        placeholders.append("?");
    }

    String sql = "INSERT INTO \"" + tableName + "\" (" + columns + ") VALUES (" + placeholders + ")";
    System.out.println(sql);

    try (Connection connect = getInstance();
         PreparedStatement updateRow = connect.prepareStatement(sql)) {
        int index = 1;
        for (Object value : rowData.values()) {
            if (value == null) {
                updateRow.setNull(index, Types.NULL);
            } else if (value instanceof String && value.toString().matches("\\d{4}-\\d{2}-\\d{2}")) {
                // If the value is a date string in the format yyyy-MM-dd, convert it to a java.sql.Date object
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date utilDate = format.parse(value.toString());
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                updateRow.setDate(index, sqlDate);
            } else {
                updateRow.setObject(index, value);
            }
            index++;
        }
        if (pendingInsert != null) {
           updateRow.executeUpdate(); 
        }
        
    } catch (Exception e) {

         JOptionPane.showMessageDialog(null, "An error occurred while inserting the row: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        isTrue = false;
        return false;
    }
    return true;
}

    public void updateRecords(String tableName, String idColumn, Map<String, String> rowData, String idString) {
        StringBuilder sql = new StringBuilder("UPDATE " + tableName + " SET ");
    
       
        // Set the columns to update
        for (String columnName : rowData.keySet()) {
            sql.append(columnName).append(" = ?, ");
        }
    
        // Remove the last comma and space
        sql.setLength(sql.length() - 2);
    
        // Add the WHERE clause
        sql.append(" WHERE ").append(idColumn).append(" = ?");
    
        try (Connection connect = getInstance();
            PreparedStatement pstmt = connect.prepareStatement(sql.toString())) {
            // Set the values for the columns to update
            int index = 1;
            for (String value : rowData.values()) {
                pstmt.setString(index++, value);
            }
    
            // Set the value for the ID
            pstmt.setString(index, idString);
    
            // Execute the update
            if (pendingUpdate != null) {
                pstmt.executeUpdate();
            }

        } catch (SQLException e) {
         JOptionPane.showMessageDialog(null, "An error occurred while inserting the row: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
         isTrue = false;
        }
    }

    public ResultSet getData(String tableName){
    ResultSet resultSet = null;
    try {
        DatabaseMetaData metadata = this.connection.getMetaData();
        ResultSet columns = metadata.getColumns(null, null, tableName, "id");
        // Default SQL query
        String sql = "SELECT * FROM " + tableName;
        if (columns.next()) {
            // id column exists, order by id
            sql = "SELECT * FROM " + tableName + " ORDER BY id";
        }
        PreparedStatement gettingData = connection.prepareStatement(sql);
        resultSet = gettingData.executeQuery();
    } catch (SQLException e){
        e.printStackTrace();
    }
    return resultSet;
}
    
    public List<String> getTableNames() {
    List<String> tables = new ArrayList<>();
    ResultSet rs = null;
    try {
        // Check if connection is closed
        if (connection == null || connection.isClosed()) {
            // Reopen the connection
            connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
        }
        DatabaseMetaData metadata = this.connection.getMetaData();
        String[] types = {"TABLE"};
        rs = metadata.getTables(null, "HR", "%", types); // Replace "your_schema" with your actual schema name
        while (rs.next()) {
            String tableName = rs.getString("TABLE_NAME");
                tables.add(tableName);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    return tables;
}
    
    public String getColumnType(String tableName, String columnName) {
        String columnType = "";
        try (Connection connect = getInstance();
             Statement stmt = connect.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM "+tableName+" FETCH FIRST 1 ROWS ONLY")) {
    
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                if (columnName.equals(metaData.getColumnName(i))) {
                    columnType = metaData.getColumnTypeName(i);
                    break;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return columnType;
    }
    public Map<String, Map<String, Object>> getColumnTypes(String tableName) throws SQLException {
    Map<String, Map<String, Object>> columnData = new HashMap<>();
    String sql = "SELECT * FROM " + tableName + " WHERE ROWNUM = 1"; // Query to get one row

    try (Connection connect = getInstance();
         PreparedStatement stmt = connect.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                Map<String, Object> columnInfo = new HashMap<>();
                int columnType = rsmd.getColumnType(i);
                String columnName = rsmd.getColumnName(i);
                int columnSize = rsmd.getColumnDisplaySize(i);

                switch (columnType) {

                        case java.sql.Types.VARCHAR:
                        case java.sql.Types.CHAR:
                        case java.sql.Types.LONGVARCHAR:
                            if (columnName.toLowerCase().contains("phone")) {
                                columnInfo.put("type", "Phone");
                            } else {
                                columnInfo.put("type", "String");
                            }
                            break;
                    case java.sql.Types.DATE:
                    case java.sql.Types.TIME:
                    case java.sql.Types.TIMESTAMP:
                        columnInfo.put("type", "Date");
                        break;
                    case java.sql.Types.NUMERIC:
                    case java.sql.Types.DECIMAL:
                        columnInfo.put("type", "Number");
                        break;
                    // Add more cases for other data types you expect
                    default:
                        columnInfo.put("type", "Other");
                        break;
                }

                columnInfo.put("size", columnSize);
                columnData.put(columnName, columnInfo);
            }
        }
    }

    return columnData;
}

    public boolean checkIfValueExists(String tableName, String columnName, String value) {
    try {
        if (connection == null || connection.isClosed()) {
            // Open a new connection here...
            connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
        }

        String query = "SELECT COUNT(*) FROM " + tableName + " WHERE " + columnName + " = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, value);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
    } catch (SQLRecoverableException e) {
        // Log the exception and retry the operation, or propagate the exception
        System.err.println("Database connection closed. Retrying...");
        return checkIfValueExists(tableName, columnName, value);
    } catch (SQLException e) {
        // Log or propagate the exception
        e.printStackTrace();
    }
    return false;
}

    
    public static ArrayList<DvdReturner> createDVDList(ResultSet resultSet){
        try {
            while (resultSet.next()){
                dvdArray.add(new DvdReturner(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getInt(4)));            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return dvdArray;
    }

    public boolean updateRecord(String tableName, Map<String, Object> rowData, String idString) {
        
        return false;
    }
    
    public void createTable() {
        // Ask the user for the table name
        String tableName = JOptionPane.showInputDialog("Enter the name of the table:");
        if (tableName == null || tableName.isEmpty()) {
            return;
        }
    
        // Ask the user for the number of columns
        int columnCount = Integer.parseInt(JOptionPane.showInputDialog("Enter the number of columns:"));
       if (columnCount == 0) {
            return;
       }
    
        // Ask the user for the names of the columns
        String[] columnNames = new String[columnCount];
        for (int i = 0; i < columnCount; i++) {
            columnNames[i] = JOptionPane.showInputDialog("Enter the name of column " + (i + 1) + ":");
            if (columnNames[i] == null || columnNames[i].isEmpty()) {
                return;
            }
        }
    
        // Create the table in the database
        try {
            String sql = "CREATE TABLE " + tableName + " (";
            for (int i = 0; i < columnCount; i++) {
                sql += columnNames[i] + " VARCHAR(255), ";
            }
            sql = sql.substring(0, sql.length() - 2) + ")"; // Remove the last comma and space, and add a closing parenthesis
    
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "The table '" + tableName + "' was created successfully!");
        } catch (SQLException e) {
            if (e.getErrorCode() == 955) {
                JOptionPane.showMessageDialog(null, "A table with the name '" + tableName + "' already exists. Please choose a different name.");
                return;
            } else {
                JOptionPane.showMessageDialog(null, "An error occurred: " + e.getMessage());
            }
            e.printStackTrace();
        }
    }


    public void exportTableData(String tableName, String filePath) throws IOException {
        String query = "SELECT * FROM " + tableName;
    
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query);
             PdfWriter writer = new PdfWriter(filePath);
             PdfDocument pdf = new PdfDocument(writer)) {
    
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
    
            // Create a table with the number of columns equal to the column count in the result set
            Table table = new Table(columnCount);
    
            // Store row data for later use
            List<String[]> rows = new ArrayList<>();
            int rowCount = 0;
            while (rs.next()) {
                rowCount++;
                String[] row = new String[columnCount];
                for (int i = 0; i < columnCount; i++) {
                    row[i] = rs.getString(i + 1);
                }
                rows.add(row);
            }
    
            // Calculate the required page size
            int rowHeight = 20;  // The height of each row in points
            int columnWidth = 50;  // The width of each column in points
            int pageWidth = (columnCount * columnWidth)+500;
            int pageHeight = rowCount * rowHeight;
    
            // Create a PageSize object with the calculated dimensions
            PageSize customSize = new PageSize(pageWidth, pageHeight);
    
            // Create the Document with the custom page size
            Document document = new Document(pdf, customSize);
    
            // Write column names
            for (int i = 1; i <= columnCount; i++) {
                table.addCell(new Cell().add(new Paragraph(metaData.getColumnName(i))));
            }
    
            // Write rows
            for (String[] row : rows) {
                for (String cellValue : row) {
                    if (cellValue == null) {
                        cellValue = "";  // Substitute default value for null
                    }
                    table.addCell(new Cell().add(new Paragraph(cellValue)));
                }
            }
    
            // Add the table to the document
            document.add(table);
        } catch (SQLException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}