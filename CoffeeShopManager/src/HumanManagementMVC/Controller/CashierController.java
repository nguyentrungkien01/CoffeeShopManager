package HumanManagementMVC.Controller;

import JDBCConnector.ConnectorMySQL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

/**
 * <p>This class extends the {@code HumanController} class</p>
 * <p>In this class contains all methods which the {@code Cashier}
 * object can use</p>
 */
public class CashierController extends HumanController {

    /**
     * <p>This method overrides the <code>calSalary</code>
     * method in the {@code HumanController}</p>
     * <p>This method specifies how the cashier staff
     * calculates salary </p>
     *
     * @param code a {@code String} object represents for the
     *            code of the cashier staff
     *
     * @return a {@code double} value represents for the salary of the cashier
     */
    @Override
    public double calSalary(String code) {
        double salary;//result
        double basicSalary = this.getBasicSalary(code);//get basic salary of the cashier staff

        /*
         * get all amount of each kind of day off
         * index 0 is amount of haft day off with permission
         * index 1 is amount of haft day off without permission
         * index 2 is amount of all day off with permission
         * index 3 is amount of all day off without permission
         */
        Vector<Integer> amountDayOffVector = this.getAmountDayOffArray(code);
        salary = basicSalary + this.getOvertime(code) * (basicSalary / 28) * 2;

        // haft day off (permission)
        salary -= amountDayOffVector.get(0) * (basicSalary / 28 / 2);
        // haft day off (non-permission)
        if (amountDayOffVector.get(1) != 0)
            salary -= amountDayOffVector.get(1) * (basicSalary / 28) + 300000;
        // all day off (permission)
        salary -= amountDayOffVector.get(2) * (basicSalary / 28);
        // all day off (non-permission)
        if (amountDayOffVector.get(3) != 0)
            salary -= amountDayOffVector.get(3) * (basicSalary / 28) + 400000;

        return salary;
    }

    /**
     * <p>This method uses to calculate and returns the total
     * money of the specific booked table </p>
     * <p>Firstly, this method accesses to the table whose
     * name is similar to the code argument and get all data in that</p>
     * <p>With each product in this table, using code of that
     * product to access to the {@code cost_product} table to get sale cost then
     * multiply it with amount of that product </p>
     * <p>Finally, sum of above result and return that value</p>
     *
     * @param codeTable a {@code String} object represents for a code of booked table
     *
     * @return a {@code double} value represents for total money of the booked table
     */
    public double calBill(String codeTable) {
        double result = 0;//total money of the table
        ConnectorMySQL connectorMySQL = new ConnectorMySQL();
        try {
            Statement statement = connectorMySQL.getConnection().createStatement();
            //get all list of product in the booked table
            statement.executeQuery(String.format("SELECT * FROM %s", codeTable.toLowerCase()));
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()) {
                String codeProduct = resultSet.getString("codeProduct");//get code of each product
                int amountProduct = resultSet.getInt("amountProduct");//get amount of each product

                Statement statement1 = connectorMySQL.getConnection().createStatement();
                //get all cost of product from cost_product table in database
                statement1.executeQuery(String.format("SELECT * FROM cost_product WHERE code='%s'", codeProduct));
                ResultSet resultSet1 = statement1.getResultSet();
                if (resultSet1.next())
                    result += resultSet1.getDouble("saleCost") * amountProduct;//get sale cost of the product
                statement1.close();
            }
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        connectorMySQL.close();
        return result;
    }

    /**
     * <p>This method uses to get code of the staff who booked the
     * table which its code is equal to the {@code codeTable} from
     * {@code booked_table} in database</p>
     *
     * @param codeTable a {@code String} object represents for a code
     *                 of booked table
     *
     * @return a {@code String} object represents for the code of the
     * staff who booked the table
     */
    public String getCodeStaffBookTable(String codeTable) {
        ConnectorMySQL connectorMySQL = new ConnectorMySQL();
        String code = null;
        try {
            Statement statement = connectorMySQL.getConnection().createStatement();
            //get all data from booked_table in database
            statement.executeQuery(String.format("SELECT * FROM booked_table WHERE codeTable='%s'", codeTable));
            ResultSet resultSet = statement.getResultSet();
            if (resultSet.next())
                code = resultSet.getString("codeHuman");//get code of the staff
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        connectorMySQL.close();
        return code;
    }

    /**
     * <p>This method use to statistic the revenue each month in year </br>
     * Firstly, this method accesses to database and gets all data
     * from <code>revenue</code> table specifies <code>kindSort</code>
     * argument</br>
     * Lastly, add data to a {@code Vector<String>} object and return this object
     * </p>
     *
     * @param month a {@code Integer} value represents for the number of the month
     *
     * @param year a {@code Integer} value represents for the number of the year
     *
     * @param kindSort a{@code Boolean} value represents for the kind of sort
     *
     * @return a {@code Vector<String>} object represents for the list of information
     *          of tables were booked in the specific month which equals <code>month</code> and
     *          <code>year</code> argument
     */
    public Vector<String> statisticRevenue(Integer month, Integer year, Boolean kindSort) {
        Vector<String> result = new Vector<>();
        ConnectorMySQL connectorMySQL = new ConnectorMySQL();
        try {
            Statement statement = connectorMySQL.getConnection().createStatement();
            //get data from revenue
            statement.executeQuery(
                    String.format("SELECT * FROM revenue WHERE infoDate LIKE '____%s__' AND infoDate LIKE '%s' %s",
                    "%" + month + "%",
                    year + "%",
                    (kindSort) ? "ORDER BY codeTable ASC" : "ORDER BY codeTable DESC"));
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()) {
                String row = "";
                row += resultSet.getString("codeTable");//get code of booked table
                row += "     ✿     ";
                row += resultSet.getDate("infoDate").toString();//get date when table was booked
                row += "     ✿     ";
                row += resultSet.getString("infoTime");//get hour when table was booked
                row += "     ✿     ";
                row += String.valueOf(resultSet.getDouble("amountProfit"));// get amount profit of the table
                result.add(row);
            }
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        connectorMySQL.close();
        return result;
    }

}
