package mvvm;

import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DBModel implements IModel {

    String driverFullQualifiedName = "com.mysql.jdbc.Driver";
    String connectionString = "jdbc:mysql://localhost:3306/eli_almog_bar";
    Connection connection = null;
    String userName;

    /**
     * method returns the Class object associated with the class or interface with the given string name, using the given class loader.
     * @throws CostManagerException
     */
    public DBModel() throws CostManagerException {
        try {
            Class.forName(driverFullQualifiedName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new CostManagerException("problem with registering driver to the driver manager", e);
        }
    }

    /**
     * retrieves the relevant items according to the dates from the DB
     * @param item is the dates
     * @param userName is currently using user
     * @return items between the 'item' dates
     * @throws CostManagerException
     */
    @Override
    public DefaultTableModel getItemsDates(Item item, String userName) throws CostManagerException {
        try {
            //creating a connection object
            connection = DriverManager.getConnection(connectionString, "eli_almog_bar", "admin123");
            //getting a statement object
            Statement statement = connection.createStatement();
            //perform simple query
            ResultSet rs = statement.executeQuery("SELECT * FROM items WHERE `Date` BETWEEN " + item.getText() + "AND UserName LIKE '" + userName + "'");
            List<String[]> values = new LinkedList<>();
            String[] columnNames = {"ItemID", "UserName", "ItemName", "Category", "Cost", "Currency", "Date"};
            DefaultTableModel model = new DefaultTableModel(columnNames, 0);
            // row contains all the columns details
            while (rs.next()) {
                Object[] row = new String[7];

                String temp = rs.getString("ItemID");
                row[0] = temp;
                temp = rs.getString("UserName");
                row[1] = temp;
                temp = rs.getString("ItemName");
                row[2] = temp;
                temp = rs.getString("Category");
                row[3] = temp;
                temp = rs.getString("Cost");
                row[4] = temp;
                temp = rs.getString("Currency");
                row[5] = temp;
                temp = rs.getString("Date");
                row[6] = temp;

                model.addRow(row);
            }
            System.out.println("model getItemsDates Log!" + "the table has " + model.getRowCount() + " rows.");
            return model;
        } catch (SQLException e) {
            throw new CostManagerException("problem with getting items", e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new CostManagerException("problem with the connection...");
            }
        }
    }

    /**
     * retrieves all the user's items from the DB.
     * @param userName
     * @return the table of items from the DB.
     * @throws CostManagerException
     */
    @Override
    public DefaultTableModel getItems(String userName) throws CostManagerException {
        try {
            this.userName = userName;
            //creating a connection object
            connection = DriverManager.getConnection(connectionString, "eli_almog_bar", "admin123");
            //getting a statement object
            Statement statement = connection.createStatement();
            //perform simple query
            ResultSet rs = statement.executeQuery("SELECT * FROM items WHERE UserName LIKE '" + userName + "'");
            String[] columnNames = {"ItemID", "UserName", "ItemName", "Category", "Cost", "Currency", "Date"};
            DefaultTableModel model = new DefaultTableModel(columnNames, 0); // this DefaultTableModel will hold all the data from the main items table.
            while (rs.next()) {

                Object[] row = new String[7];

                String temp = rs.getString("ItemID");
                row[0] = temp;
                temp = rs.getString("UserName");
                row[1] = temp;
                temp = rs.getString("ItemName");
                row[2] = temp;
                temp = rs.getString("Category");
                row[3] = temp;
                temp = rs.getString("Cost");
                row[4] = temp;
                temp = rs.getString("Currency");
                row[5] = temp;
                temp = rs.getString("Date");
                row[6] = temp;

                model.addRow(row);
            }

            System.out.println("model getItems Log!" + "the table has " + model.getRowCount() + " rows.");
            return model;

        } catch (SQLException e) {
            throw new CostManagerException("problem with getting items", e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new CostManagerException("problem with the connection...");
            }
        }
    }

    /**
     * adds an item to the items table
     * @param item is the item to be added
     * @throws CostManagerException
     */
    @Override
    public void addItem(Item item) throws CostManagerException {
        try {
            //creating a connection object
            connection = DriverManager.getConnection(connectionString, "eli_almog_bar", "admin123");
            String sql = "INSERT INTO items (UserName, ItemName, Category, Cost, Currency) VALUES " +
                    "('" + this.userName + "', " + item.getText() + ")";
            //getting a statement object
            PreparedStatement ps = connection.prepareStatement(sql);
            int check = ps.executeUpdate();// executes the query and adds the item to the DB.
            if (check != 1){
                throw new CostManagerException("cant add item!");
            }
        } catch (SQLException e) {
            throw new CostManagerException("cant add item!");
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new CostManagerException("problem with the connection...");
            }
        }
    }

    /**
     * invoked when sign-up button is pressed
     * in case the user doesn't exist in the users DB then a new user is added to the table
     * @param item is the username and password
     * @return true if the username doesn't exist
     * @throws CostManagerException
     * @throws SQLException
     */
    public boolean addUser(Item item) throws CostManagerException {
        boolean result = false;
        try {
            //creating a connection object
            connection = DriverManager.getConnection(connectionString, "eli_almog_bar", "admin123");
            String sql = "INSERT INTO users (UserName, Password) VALUES (" + item.getText() + ")";
            //getting a statement object
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.executeUpdate();
            result = true;
        } catch (SQLException e) {
            result = false;
            throw new CostManagerException("UserName already exist!");
        } finally {
            try {
                connection.close();
                return result;
            } catch (SQLException e) {
                throw new CostManagerException("problem with the connection...");
            }
        }
    }

    /**
     * invoked when log-in button is pressed
     * checks if there's a user with a matching password in the users table in the DB.
     * @param userNamePass is the username and password
     * @return true if there's a username and a matching password
     * @throws SQLException
     * @throws CostManagerException
     */
    public boolean checkUserPass(Item userNamePass) throws CostManagerException {
        boolean result = false;
        Statement statement = null;
        try{
            String userNamePassword = userNamePass.getText();
            String userName = userNamePassword.substring(0, userNamePassword.indexOf(","));
            String password = userNamePassword.substring(userNamePassword.indexOf(",") + 1, userNamePassword.lastIndexOf(" "));
            //creating a connection object
            connection = DriverManager.getConnection(connectionString, "eli_almog_bar", "admin123");
            String sqlQuery = "SELECT * FROM users WHERE UserName ='" + userName + "' AND Password = '" + password + "'";
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlQuery);

            if (!resultSet.isBeforeFirst() ) {//this line checks if the query found something, if a username with that password exist.
                System.out.println("invalid UserName and password");
                throw new CostManagerException("invalid UserName and password");
            }
            result = true;
        } catch (SQLException e) {
            result = false;
            throw new CostManagerException("Invalid UserName and Password!");
        }
        finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new CostManagerException("problem with the connection...");
            }
            return result;
        }

    }

    /**
     * adds a new category name to the category list in the DB.
     * @param item is the name of the new category
     * @throws CostManagerException
     */
    @Override
    public void addCategory(Item item) throws CostManagerException {
        try {
            //creating a connection object
            connection = DriverManager.getConnection(connectionString, "eli_almog_bar", "admin123");
            String sql = "INSERT INTO `category` (`name`) VALUES ('" + item.getText() + "')";
            //getting a statement object
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new CostManagerException("cant add item!");
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new CostManagerException("problem with the connection...");
            }
        }
    }

    /**
     * retrieves the latest category list from the category table in the DB
     * @return the list of categories
     * @throws CostManagerException
     */
    @Override
    public List<String> getCatItems() throws CostManagerException {
        try {
            //creating a connection object
            connection = DriverManager.getConnection(connectionString, "eli_almog_bar", "admin123");
            //getting a statement object
            Statement statement = connection.createStatement();
            //perform simple query
            ResultSet rs = statement.executeQuery("SELECT * FROM category");
            List<String> values = new ArrayList<String>();
            while (rs.next()) {
                values.add(rs.getString("name"));
            }
            return values;

        } catch (SQLException e) {
            throw new CostManagerException("problem with getting items", e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * retrieves the currencies from the DB
     * @return
     * @throws CostManagerException
     */
    public List<String> getCurrItems() throws CostManagerException {
        try {
            //creating a connection object
            connection = DriverManager.getConnection(connectionString, "eli_almog_bar", "admin123");
            //getting a statement object
            Statement statement = connection.createStatement();
            //perform simple query
            ResultSet rs = statement.executeQuery("SELECT * FROM currency");
            List<String> values = new ArrayList<String>();
            while (rs.next()) {
                values.add(rs.getString("name"));
            }
            return values;

        } catch (SQLException e) {
            throw new CostManagerException("problem with getting items", e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new CostManagerException("problem with the connection...");
            }
        }
    }
}


