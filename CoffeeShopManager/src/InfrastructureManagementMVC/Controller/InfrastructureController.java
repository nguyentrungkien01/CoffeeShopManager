package InfrastructureManagementMVC.Controller;

import HumanManagementMVC.Controller.HumanController;
import InfrastructureManagementMVC.Enum.Material;
import InfrastructureManagementMVC.Enum.NameTable;
import Interface.Controller;
import JDBCConnector.ConnectorMySQL;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Vector;

/**
 * <p>{@code InfrastructureController} contains all methods represent for
 * all action of the infrastructure manager</p>
 *
 * <p>{@code InfrastructureController} implements {@code Controller} interface</p>
 *
 */
public class InfrastructureController implements Controller {

    /**
     * <p>This is method, which only will be used in this class.
     * This method has a mission is getting all information of an object in database
     * then returns it</p>
     *
     * @param resultSet original {@code ResultSet} object
     *
     * @return an {@code Vector<Vector<String>>} object represents for the information
     *          of the infrastructure
     *
     * @throws SQLException This exception will throw when fail to access to database
     */
    @Override
    public Vector<Vector<String>> getModelData(ResultSet resultSet) throws SQLException {
        Vector<Vector<String>> result = new Vector<>();
        while (resultSet.next()) {
            Vector<String> row = new Vector<>();
            row.add(String.valueOf(resultSet.getInt("id")));
            row.add(resultSet.getString("code"));
            row.add(resultSet.getString("name"));
            row.add(String.valueOf(resultSet.getInt("capacity")));
            row.add(String.valueOf(resultSet.getBoolean("status")));
            row.add(resultSet.getString("material"));
            row.add(HumanController.formatDate(resultSet.getDate("purchaseDate")));
            result.add(row);
        }
        return result;
    }

    /**
     * <p>This method will search all objects contain keyword argument then return
     * a {@code Vector<Vector<String>>} them</p>
     *
     * @param key original {@code String} object represents for the String keyword
     *
     * @return list of all {@code Vector<Vector<String>>} represents all of data was found in database
     */
    @Override
    public Vector<Vector<String>> searchKey(String key) {
        Vector<Vector<String>> resultVector = new Vector<>();
        ConnectorMySQL connectorMySQL = new ConnectorMySQL();
        try {
            Statement statement = connectorMySQL.getConnection().createStatement();
            key = "%" + key + "%";
            statement.executeQuery(String.format("SELECT * FROM info_table a " +
                            "WHERE " +
                            "(a.code LIKE '%s' OR a.name LIKE '%s' OR a.capacity LIKE '%s' OR a.status LIKE '%s' " +
                            "OR a.material LIKE '%s' OR a.purchaseDate LIKE '%s')",
                    key, key, key, key, key, key));
            ResultSet resultSet = statement.getResultSet();
            resultVector = getModelData(resultSet);
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        connectorMySQL.close();
        return resultVector;
    }

    /**
     * <p>This method will search all objects which have value which is
     * in between begin argument and end argument then return
     * a {@code Vector<Vector<String>>} them</p>
     *
     * @param begin a {@code double} value represents for begin value
     *
     * @param end a {@code double} value represents for end value
     *
     * @return list of all {@code Vector<Vector<String>>} represents all of data were
     *          found in database
     */
    @Override
    public Vector<Vector<String>> searchKey(double begin, double end) {
        Vector<Vector<String>> resultVector = new Vector<>();
        ConnectorMySQL connectorMySQL = new ConnectorMySQL();
        try {
            Statement statement = connectorMySQL.getConnection().createStatement();
            statement.executeQuery(String.format("SELECT * FROM info_table a " +
                            "WHERE " +
                            "a.capacity BETWEEN %d AND %d ", (int) begin, (int) end));
            ResultSet resultSet = statement.getResultSet();
            resultVector = getModelData(resultSet);
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        connectorMySQL.close();
        return resultVector;
    }

    /**
     * <p>This method return null</p>
     *
     * @param keyword o {@code int} value
     *
     * @return null value
     */
    @Override
    public Vector<Vector<String>> searchKey(int keyword) {
        return null;
    }

    /**
     * Get all information of each infrastructure was stored in database
     * and then sort them by ascending or descending with order
     * parameter or don't sort and convert all data was found to {@code String}
     *
     * @param order original {@code String} object. If the parameter is empty no sort,
     *              if sort the order parameter has format is
     *              <code>"ORDER BY " + {name column} + [ASC || DESC]</code>
     *
     * @return An {@code Vector<Vector<String>>} object which each row represents
     *          each infrastructure was found in database and each column represents each
     *          information of that infrastructure
     */
    @Override
    public Vector<Vector<String>> getAll(String order) {
        Vector<Vector<String>> result = new Vector<>();
        ConnectorMySQL connectorMySQL = new ConnectorMySQL();
        try {
            Statement statement = connectorMySQL.getConnection().createStatement();
            statement.executeQuery("SELECT * FROM info_table " + order);
            ResultSet resultSet = statement.getResultSet();
            result = getModelData(resultSet);
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        connectorMySQL.close();
        return result;
    }

    /**
     * <p>This method overrides <code>add</code> method in the {@code Controller} interface</p>
     * <p>This method uses to add a infrastructure to database</p>
     * <p>By passing an object, that the reason why calls methods at runtime Using Java reflection</p>
     * <p></p>
     *
     * @param objModel an {@code Object} represents for an object wants to add
     */
    @Override
    public void add(Object objModel) {

        /*
         * create and initial default value all of attributes of
         * the kinds of infrastructure
         */
        String code = "";
        NameTable name = null;
        int capacity = 0;
        boolean status = false;
        Material material = null;
        Date purchaseDate = null;

        /*
         * use reflection to invoke methods
         */
        try {
            code = (String) objModel.getClass().getMethod("getCode").invoke(objModel);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ignored) {
        }
        System.out.println(code);
        try {
            name = (NameTable) objModel.getClass().getMethod("getName").invoke(objModel);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ignored) {
        }
        try {
            capacity = (int) objModel.getClass().getMethod("getCapacity").invoke(objModel);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ignored) {
        }
        try {
            status = (boolean) objModel.getClass().getMethod("getStatus").invoke(objModel);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ignored) {
        }
        try {
            material = (Material) objModel.getClass().getMethod("getMaterial").invoke(objModel);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ignored) {
        }
        try {
            purchaseDate = (Date) objModel.getClass().getMethod("getPurchaseDate").invoke(objModel);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ignored) {
        }

        /*
         * add data to database
         */
        ConnectorMySQL connectorMySQL = new ConnectorMySQL();
        try {
            Statement statement = connectorMySQL.getConnection().createStatement();
            statement.executeUpdate(String.format(
                    "INSERT INTO info_table (code, name, capacity, status,material, purchaseDate ) " +
                            "VALUES ('%s', '%s', %d, %s, '%s', '%s')",
                    code, name, capacity, status, material, HumanController.formatDate(purchaseDate)));
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        connectorMySQL.close();
    }

    /**
     * <p>This method provides method to remove information of an object
     * in database whose code equals to the code argument</p>
     *
     * @param code original String object
     */
    @Override
    public void remove(String code) {
        code=code.toUpperCase();
        ConnectorMySQL connectorMySQL = new ConnectorMySQL();
        try {
            Statement statement = connectorMySQL.getConnection().createStatement();
            statement.executeUpdate(String.format("DELETE FROM info_table WHERE code='%s'", code));
            statement.executeQuery("SELECT * FROM amount_table");
            ResultSet resultSet= statement.getResultSet();
            if(resultSet.next())
                statement.executeUpdate(String.format("UPDATE amount_table SET amount=%d",
                        resultSet.getInt("amount") - 1));
            //update the numerical order
            statement.executeUpdate("ALTER TABLE info_table DROP id");
            statement.executeUpdate("ALTER TABLE info_table AUTO_INCREMENT =1");
            statement.executeUpdate("ALTER TABLE info_table ADD id INT NOT NULL AUTO_INCREMENT PRIMARY KEY FIRST");
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        connectorMySQL.close();
    }

    /**
     * Search code of infrastructure and update name of that infrastructure in database
     *
     * @param code original {@code String} object -> Code of infrastructure was stored in database
     *
     * @param name original {@code String} object -> New name will replace old name in database
     */
    public void updateName(String code, String name) {
        if (!name.isEmpty()) {
            ConnectorMySQL connectorMySQL = new ConnectorMySQL();
            try {
                Statement statement = connectorMySQL.getConnection().createStatement();
                statement.executeUpdate(String.format("UPDATE info_table SET name='%s' WHERE code='%s'", name, code));
                statement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            connectorMySQL.close();
        }
    }

    /**
     * Search code of infrastructure and update capacity of that infrastructure in database
     *
     * @param code original {@code String} object -> Code of infrastructure was stored in database
     *
     * @param capacity original {@code int} object -> New capacity will replace old capacity in database
     */
    public void updateCapacity(String code, int capacity) {
        if (capacity > 0) {
            ConnectorMySQL connectorMySQL = new ConnectorMySQL();
            try {
                Statement statement = connectorMySQL.getConnection().createStatement();
                statement.executeUpdate(String.format("UPDATE info_table SET capacity=%d WHERE code='%s'", capacity, code));
                statement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            connectorMySQL.close();
        }
    }

    /**
     * Search code of infrastructure and update status of that infrastructure in database
     *
     * @param code original {@code String} object -> Code of infrastructure was stored in database
     *
     * @param status original {@code boolean} object -> New status will replace old status in database
     */
    public void updateStatus(String code, boolean status) {
        ConnectorMySQL connectorMySQL = new ConnectorMySQL();
        try {
            Statement statement = connectorMySQL.getConnection().createStatement();
            statement.executeUpdate(String.format("UPDATE info_table SET status=%s WHERE code='%s'", status, code));
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        connectorMySQL.close();
    }

    /**
     * Search code of infrastructure and update material of that infrastructure in database
     *
     * @param code original {@code String} object -> Code of infrastructure was stored in database
     *
     * @param material original {@code String} object -> New material will replace old material in database
     */
    public void updateMaterial(String code, String material) {
        ConnectorMySQL connectorMySQL = new ConnectorMySQL();
        try {
            Statement statement = connectorMySQL.getConnection().createStatement();
            statement.executeUpdate(String.format("UPDATE info_table SET material='%s' WHERE code='%s'", material, code));
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        connectorMySQL.close();
    }

    /**
     * Search code of infrastructure and update purchase date of that infrastructure in database
     *
     * @param code original {@code String} object -> Code of infrastructure was stored in database
     *
     * @param purchaseDate original {@code Date} object -> New purchase date will replace old purchase date in database
     */
    public void updatePurchaseDate(String code, Date purchaseDate) {
        ConnectorMySQL connectorMySQL = new ConnectorMySQL();
        try {
            Statement statement = connectorMySQL.getConnection().createStatement();
            statement.executeUpdate(String.format("UPDATE info_table SET purchaseDate='%s' WHERE code='%s'",
                    HumanController.formatDate(purchaseDate), code));
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        connectorMySQL.close();
    }

    /**
     * <p>This method uses to get amount of the table</p>
     * @return  a {@code int} value represents for the amount of the table
     */
    public int getAmount() {
        int amount = 0;
        ConnectorMySQL connectorMySQL = new ConnectorMySQL();
        try {
            Statement statement = connectorMySQL.getConnection().createStatement();
            statement.executeQuery("SELECT * FROM amount_table");
            ResultSet resultSet = statement.getResultSet();
            if (resultSet.next())
                amount = resultSet.getInt("amount");
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        connectorMySQL.close();
        return amount;
    }

    /**
     * <p>This method uses to get all code of table in database</p>
     *
     * @return a {@code Vector<String>} object represents for a list of code table
     */
    public Vector<String> getAllCodeTable() {
        ConnectorMySQL connectorMySQL = new ConnectorMySQL();
        Vector<String> resultVector = new Vector<>();
        try {
            Statement statement = connectorMySQL.getConnection().createStatement();
            statement.executeQuery("SELECT * FROM info_table");
            ResultSet resultSet = statement.getResultSet();

            while (resultSet.next())
                resultVector.add(resultSet.getString("code"));
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        connectorMySQL.close();
        return resultVector;
    }

    /**
     * <p>This method uses to get all code of booked table in database</p>
     *
     * @return a {@code Vector<String>} object represents for a list of code of booked table
     */
    public Vector<String> getAllCodeBookedTable() {
        ConnectorMySQL connectorMySQL = new ConnectorMySQL();
        Vector<String> resultVector = new Vector<>();
        try {
            Statement statement = connectorMySQL.getConnection().createStatement();
            statement.executeQuery("SELECT * FROM booked_table");
            ResultSet resultSet = statement.getResultSet();

            while (resultSet.next())
                resultVector.add(resultSet.getString("codeTable"));
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        connectorMySQL.close();
        return resultVector;
    }

    /**
     * <p>This method uses to check status of the table</p>
     *
     * @param code a {@code String} object represents for the code of table
     *
     * @return a {@code boolean} value represents for the status of the table
     */
    public boolean isBooked(String code) {
        ConnectorMySQL connectorMySQL = new ConnectorMySQL();
        boolean status = false;
        try {
            Statement statement = connectorMySQL.getConnection().createStatement();
            statement.executeQuery(String.format("SELECT info_table.status FROM info_table WHERE code='%s'", code));
            ResultSet resultSet = statement.getResultSet();
            if (resultSet.next())
                status = resultSet.getBoolean("status");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        connectorMySQL.close();
        return status;
    }

}
