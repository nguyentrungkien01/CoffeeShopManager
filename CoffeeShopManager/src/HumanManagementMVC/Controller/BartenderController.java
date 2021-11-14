package HumanManagementMVC.Controller;

import InfrastructureManagementMVC.Controller.InfrastructureController;
import JDBCConnector.ConnectorMySQL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

/**
 * <p>This class extends the {@code HumanController} class</p>
 * <p>In this class contains all methods which the {@code Bartender}
 * object can use</p>
 */
public class BartenderController extends HumanController {

    /**
     * <p>This method overrides the {@code calSalary}
     * method in the {@code HumanController}</p>
     * <p>This method specifies how the bartender staff
     * calculates salary </p>
     *
     * @param code a {@code String} object represents for the code of the bartender staff
     *
     * @return a {@code double} value represents for the salary of the bartender
     */
    @Override
    public double calSalary(String code) {
        double salary;//result
        double basicSalary = this.getBasicSalary(code);//get basic salary of the bartender staff

        /*
         * get all amount of each kind of day off
         * index 0 is amount of haft day off with permission
         * index 1 is amount of haft day off without permission
         * index 2 is amount of all day off with permission
         * index 3 is amount of all day off without permission
         */
        Vector<Integer> amountDayOffVector = this.getAmountDayOffArray(code);
        salary = this.getBasicSalary(code) + this.getOvertime(code) * (basicSalary / 28) * 2 +
                this.getBonus(code);

        // haft day off (permission)
        salary -= amountDayOffVector.get(0) * (basicSalary / 28 / 2);
        // haft day off (non-permission)
        if (amountDayOffVector.get(1) != 0)
            salary -= amountDayOffVector.get(1) * (basicSalary / 28) + 150000;
        // all day off (permission)
        salary -= amountDayOffVector.get(2) * (basicSalary / 28);
        // all day off (non-permission)
        if (amountDayOffVector.get(3) != 0)
            salary -= amountDayOffVector.get(3) * (basicSalary / 28) + 250000;

        return salary;
    }

    /**
     * <p>This method accesses to database to get all list of drinks </p>
     * <p>Firstly, get all code of booked table in database </br>
     * Secondly, get all info about of the drink in from each code of the booked
     * table which have just got </br>
     * Lastly, add each data of each kind drink to the {@code Vector<String>}
     * then add that {@code Vector<String>} to the drink list
     * ({@code Vector<Vector<String>>})</p>
     *
     * @return a {@code Vector<Vector<String>>} represents for the list of information
     *             of the drinks which were booked
     */
    public Vector<Vector<String>> getAllDrink() {
        Vector<Vector<String>> result = new Vector<>();//create list of drinks
        ConnectorMySQL connectorMySQL = new ConnectorMySQL();

        /*
         * get list of code of booked table by using the getAllCodeBookedTable()
         * method of the InfrastructureController object
         */
        Vector<String> codeTableVector = new InfrastructureController().getAllCodeBookedTable();

        /*
         * loop each code of booked table to get data
         */
        codeTableVector.forEach(codeTable -> {
            try {
                Statement statement = connectorMySQL.getConnection().createStatement();
                statement.executeQuery(String.format("SELECT * FROM %s WHERE codeProduct LIKE '%s'",
                        codeTable.toLowerCase(), "%DRINK%"));//get data from each code booked table
                ResultSet resultSet = statement.getResultSet();
                while (resultSet.next()) {
                    //create Vector<String> object represents each information of each drink
                    Vector<String> row = new Vector<>();
                    //get code of product was stored in booked table
                    String codeProduct = resultSet.getString("codeProduct");
                    //get amount of product was stored in booked table
                    int amountProduct = resultSet.getInt("amountProduct");

                    Statement statement1 = connectorMySQL.getConnection().createStatement();
                    //get all data of product from product table by code product has just got
                    statement1.executeQuery(String.format("SELECT * FROM product WHERE code='%s'", codeProduct));
                    ResultSet resultSet1 = statement1.getResultSet();
                    //add information of drink to Vector<String>
                    if (resultSet1.next()) {
                        row.add(codeTable);
                        row.add(codeProduct);
                        row.add(resultSet1.getString("name"));
                        row.add(String.valueOf(amountProduct));
                    }
                    statement1.close();
                    // add information of drink to list
                    result.add(row);
                }
                statement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
        connectorMySQL.close();
        return result;
    }
}
