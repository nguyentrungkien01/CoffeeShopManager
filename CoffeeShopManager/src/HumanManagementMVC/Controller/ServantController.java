package HumanManagementMVC.Controller;

import JDBCConnector.ConnectorMySQL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Vector;


/**
 * <p>This class extends the {@code HumanController} class</p>
 * <p>In this class contains all methods which the {@code ServantController}
 * object can use</p>
 */
public class ServantController extends HumanController {

    /**
     * <p>This method overrides the <code>calSalary</code>
     * method in the {@code HumanController}</p>
     * <p>This method specifies how the Servant staff
     * calculates salary </p>
     *
     * @param code a {@code String} object represents for the
     *             code of the Servant staff
     * @return a {@code double} value represents for the salary of the Servant
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
        salary = basicSalary + this.getOvertime(code) * (basicSalary / 28) * 1.5 + this.getBonus(code);

        /*
         * check kind of shift of the staff whose code equals to code argument
         * then add salary with suitable amount money
         */
        switch (this.getShift(code)) {
            case DAY -> salary += 350000;
            case NIGHT -> salary += 450000;
            case ALL -> salary += 550000;
        }

        // haft day off (permission)
        salary -= amountDayOffVector.get(0) * (basicSalary / 28 / 2);
        // haft day off (non-permission)
        if (amountDayOffVector.get(1) != 0)
            salary -= amountDayOffVector.get(1) * (basicSalary / 28) + 100000;
        // all day off (permission)
        salary -= amountDayOffVector.get(2) * (basicSalary / 28);
        // all day off (non-permission)
        if (amountDayOffVector.get(3) != 0)
            salary -= amountDayOffVector.get(3) * (basicSalary / 28) + 200000;

        return salary;
    }

    /**
     * <p>This method uses to book the table </br>
     * Firstly, check the status of the table which has code
     * which equals to the <code>codeTable</code>, if the status is
     * <code>false</code>, allows the staff to book the table.<br>
     * After allowing to book the table, insert data of booked table to
     * the <code>booked_table</code> and create new table with name is
     * lowercase code of the booked table, then change status of the table
     * into <code>true</code>.</br>
     * Finally, insert information (code, date, time, profit) of the booked
     * table into <code>revenue</code> table in database.
     * </p>
     *
     * @param codeHuman a {@code String} object represents for the code of the
     *                 staff who booked table
     *
     * @param codeTable a {@code String} object represents for the code of the
     *                  booked table
     */
    public void bookTable(String codeHuman, String codeTable) {
        ConnectorMySQL connectorMySQL = new ConnectorMySQL();
        try {
            Statement statement = connectorMySQL.getConnection().createStatement();
            //get the status of the table
            statement.executeQuery(String.format("SELECT info_table.status FROM info_table WHERE code='%s'", codeTable));
            ResultSet resultSet = statement.getResultSet();
            //check the status has just got from database
            if (resultSet.next() && !resultSet.getBoolean("status")) {
                //insert information of the table to booked_table table in database
                statement.executeUpdate(String.format("INSERT INTO booked_table (codeTable, codeHuman) VALUES ('%s', '%s')",
                        codeTable, codeHuman));
                //create new table for the booked table to stored list of products
                statement.executeUpdate(String.format("CREATE TABLE %s (" +
                                "codeProduct varchar(30) not null," +
                                "amountProduct int not null)",
                        codeTable.toLowerCase()));
                //re-update status of the table to true
                statement.executeUpdate(String.format("UPDATE info_table SET status=true WHERE code='%s'", codeTable));
                //insert the booked table to revenue table to statistic revenue
                statement.executeUpdate(String.format("INSERT INTO revenue(codeTable,infoDate,infoTime, amountProfit) " +
                                "VALUES ('%s', '%s','%s',0)",
                        codeTable,
                        new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()),
                        new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime())));
            }
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        connectorMySQL.close();
    }

    /**
     * <p>This method uses to order the products for the booked table </p>
     *
     * <p><strong>Firstly</strong>, check the <code>amountProduct</code>, if the number of the product is
     * greater than zero then check the remaining number of the product in the
     * database. if it's greater than or equals to the input amount product then
     * allows to order product.</p>
     *
     * <p><strong>Secondly</strong>, check if the product is available or not. if the product is available,
     * update the number of the product was stored in database
     * <i>(new amount = old amount+ input amount)</i>
     * otherwise, insert info the product to list ordered product.</p>
     *
     * <p><strong>Thirdly</strong>, reduce the total number of the product
     * <i>(new total amount = old total amount - input amount)</i></p>
     *
     * <p><strong>Finally</strong>, select the table with the nearest time in revenue table
     * and calculate the profit <i>(new profit = old profit + (sale cost - purchase cost)* amount)</i></p>
     *
     * @param codeTable a {@code String} object represents for the table wants to order product
     *
     * @param codeProduct a {@code String} object represents for the ordered product
     *
     * @param amountProduct a {@code int} object represents for amount of the ordered product
     */
    public void orderProduct(String codeTable, String codeProduct, int amountProduct) {
        if (amountProduct > 0) {
            ConnectorMySQL connectorMySQL = new ConnectorMySQL();
            try {
                Statement statement = connectorMySQL.getConnection().createStatement();
                statement.executeQuery(String.format("SELECT * FROM  amount_each_product WHERE code='%s'", codeProduct));
                ResultSet resultSet = statement.getResultSet();
                if (resultSet.next()) {
                    int amount = resultSet.getInt("amountProduct");
                    if (amount >= amountProduct) {
                        statement.executeQuery(String.format("SELECT * FROM %s WHERE codeProduct='%s'",
                                codeTable.toLowerCase(), codeProduct));
                        resultSet = statement.getResultSet();
                        if (resultSet.next())
                            statement.executeUpdate(String.format("UPDATE %s SET amountProduct=%d WHERE codeProduct='%s'",
                                    codeTable.toLowerCase(), resultSet.getInt("amountProduct") + amountProduct, codeProduct));
                        else
                            statement.executeUpdate(String.format("INSERT INTO %s(codeProduct, amountProduct) VALUES ('%s', %d)",
                                    codeTable.toLowerCase(), codeProduct, amountProduct));
                        statement.executeUpdate(String.format("UPDATE amount_each_product SET amountProduct=%d WHERE code='%s'",
                                amount - amountProduct, codeProduct));

                        statement.executeQuery("SELECT MAX(infoTime) AS infoTime FROM revenue");
                        resultSet = statement.getResultSet();
                        if (resultSet.next()) {
                            String hour = resultSet.getString("infoTime");
                            statement.executeQuery(String.format("SELECT * FROM cost_product WHERE code='%s'", codeProduct));
                            resultSet = statement.getResultSet();
                            if (resultSet.next()) {
                                double saleCost = resultSet.getDouble("saleCost");
                                double purchaseCost = resultSet.getDouble("purchaseCost");
                                statement.executeQuery(String.format("SELECT * FROM revenue " +
                                                "WHERE codeTable='%s' AND infoDate='%s' AND infoTime='%s'",
                                        codeTable,
                                        new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()),
                                        hour));
                                resultSet = statement.getResultSet();
                                if (resultSet.next())
                                    statement.executeUpdate(String.format("UPDATE revenue SET amountProfit=%f " +
                                                    "WHERE codeTable='%s' AND infoDate='%s' AND infoTime='%s'",
                                            resultSet.getDouble("amountProfit") + (saleCost - purchaseCost) * amountProduct,
                                            codeTable,
                                            new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()),
                                            hour));

                            }
                        }
                    }
                }
                statement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            connectorMySQL.close();
        }
    }

    /**
     *<p>This method uses to cancel the number of the product which has booked before</p>
     *
     * <p><strong>Firstly</strong>, check if the input amount of the product is greater
     * than zero then allow to cancel product. After passing the condition. get amount
     * of the product which has the code equals to the input code of the product.
     * If the amount of the product in list product of the booked table is greater
     * than or equals to the input amount. update the amount in database
     * <i>(new amount = old amount - input amount)</i>, if the new amount is zero,
     * delete the product from the list of booked table</p>
     *
     * <p><strong>Finally</strong>, select the table with the nearest time in revenue table
     * the update the profit of the booked table
     * <i>new profit = old profit - input amount *( sale cost - purchase cost)</i></p>
     *
     * @param codeTable a {@code String} object represents for the code of the booked table
     *
     * @param codeProduct a {@code String} object represents for the code of the product wants to cancel
     *
     * @param amountProduct a {@code int} value represents for the amount of the product wants to cancel
     */
    public void cancelProduct(String codeTable, String codeProduct, int amountProduct) {
        if (amountProduct > 0) {
            String time= new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());//current time
            ConnectorMySQL connectorMySQL = new ConnectorMySQL();
            try {
                Statement statement = connectorMySQL.getConnection().createStatement();
                statement.executeQuery(String.format("SELECT * FROM %s WHERE codeProduct='%s'",
                        codeTable.toLowerCase(), codeProduct));
                ResultSet resultSet = statement.getResultSet();
                if (resultSet.next()) {
                    int amount = resultSet.getInt("amountProduct");
                    if (amount >= amountProduct) {
                        statement.executeQuery(String.format("SELECT * FROM amount_each_product WHERE code='%s'", codeProduct));
                        resultSet = statement.getResultSet();
                        if (resultSet.next()) {
                            statement.executeUpdate(String.format("UPDATE amount_each_product SET amountProduct=%d WHERE code='%s'",
                                    resultSet.getInt("amountProduct") + amountProduct, codeProduct));
                            statement.executeUpdate(String.format("UPDATE %s SET amountProduct=%d WHERE codeProduct='%s'",
                                    codeTable.toLowerCase(), amount - amountProduct, codeProduct));
                        }
                    }
                    if (amount == amountProduct)
                        statement.executeUpdate(String.format("DELETE FROM %s WHERE codeProduct='%s'",
                                codeTable.toLowerCase(), codeProduct));

                    statement.executeQuery(
                            String.format("SELECT MAX(infoTime) AS infoTime FROM revenue WHERE infoDate='%s'", time));
                    resultSet = statement.getResultSet();
                    if (resultSet.next()) {
                        String hour = resultSet.getString("infoTime");
                        statement.executeQuery(String.format("SELECT * FROM cost_product WHERE code='%s'", codeProduct));
                        resultSet = statement.getResultSet();
                        if (resultSet.next()) {
                            double saleCost = resultSet.getDouble("saleCost");
                            double purchaseCost = resultSet.getDouble("purchaseCost");
                            statement.executeQuery(String.format("SELECT * FROM revenue " +
                                            "WHERE codeTable='%s' AND infoDate='%s' AND infoTime='%s'",
                                    codeTable, time, hour));
                            resultSet = statement.getResultSet();
                            if (resultSet.next())
                                statement.executeUpdate(String.format("UPDATE revenue SET amountProfit=%f " +
                                                "WHERE codeTable='%s' AND infoDate='%s' AND infoTime='%s'",
                                        resultSet.getDouble("amountProfit") - amountProduct * (saleCost - purchaseCost),
                                        codeTable, time, hour));
                        }
                    }
                }

                statement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            connectorMySQL.close();
        }
    }

    // false -> not yet charged
    // true -> charged
    /**
     * <p>This method uses to cancel the booked table</p>
     * <p><strong>Firstly</strong>, check if the input status is false
     * allows to cancel the not yet charged table, otherwise charged table
     * <strong>With not charged table</strong>, when cancel table, all of products in
     * the booked tale will return to the storage <br>
     * <strong>With charged table</strong>, when cancel table, update the status of the
     * booked table is false then delete the booked table in database, and delete the table
     * in the booked_table table in database</p>
     *
     * @param codeTable a {@code String} object represents for the code of the
     *                  table wants to cancel
     *
     * @param status a {@code boolean} value represents for the status of the
     *               table wants to cancel
     */
    public void cancelTable(String codeTable, boolean status) {
        ConnectorMySQL connectorMySQL = new ConnectorMySQL();
        try {
            Statement statement = connectorMySQL.getConnection().createStatement();
            if (!status) {
                statement.executeQuery(String.format("SELECT * FROM  %s", codeTable.toLowerCase()));
                ResultSet resultSet = statement.getResultSet();
                while (resultSet.next()) {
                    String codeProduct = resultSet.getString("codeProduct");
                    int amountProduct = resultSet.getInt("amountProduct");
                    Statement statement1 = connectorMySQL.getConnection().createStatement();
                    statement1.executeQuery(String.format("SELECT * FROM amount_each_product WHERE code='%s'", codeProduct));
                    ResultSet resultSet1 = statement1.getResultSet();
                    if (resultSet1.next())
                        statement1.executeUpdate(String.format("UPDATE amount_each_product SET amountProduct=%d WHERE code='%s'",
                                amountProduct + resultSet1.getInt("amountProduct"), codeProduct));
                    statement1.close();
                }
                statement.executeUpdate(String.format("UPDATE info_table SET status=false WHERE code='%s'", codeTable));
                statement.executeUpdate(String.format("DROP TABLE %s", codeTable));
                statement.executeUpdate(String.format("DELETE FROM booked_table WHERE codeTable='%s'", codeTable));
                statement.executeQuery("SELECT MAX(infoTime) AS infoTime FROM revenue");
                resultSet = statement.getResultSet();
                if (resultSet.next())
                    statement.executeUpdate(String.format("DELETE FROM revenue WHERE codeTable='%s' AND infoDate='%s' AND infoTime='%s'",
                            codeTable,
                            new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()),
                            resultSet.getString("infoTime")));
            } else {
                statement.executeUpdate(String.format("UPDATE info_table SET status=false WHERE code='%s'", codeTable));
                statement.executeUpdate(String.format("DROP TABLE %s", codeTable));
                statement.executeUpdate(String.format("DELETE FROM booked_table WHERE codeTable='%s'", codeTable));
            }
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        connectorMySQL.close();
    }

    /**
     * <p>This method uses to get all information of the products in the
     * specific booked table in database</p>
     *
     * @param codeTable a {@code String} object represents for the booked
     *                  table wants to get information
     *
     * @return a {@code Vector<Vector<String>> }object represents for all
     * information of the booked table
     */
    public Vector<Vector<String>> getAllInfoBookedTable(String codeTable) {
        Vector<Vector<String>> result = new Vector<>();
        ConnectorMySQL connectorMySQL = new ConnectorMySQL();
        try {
            Statement statement = connectorMySQL.getConnection().createStatement();
            statement.executeQuery(String.format("SELECT * FROM %s", codeTable.toLowerCase()));
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()) {
                Vector<String> row = new Vector<>();
                row.add(resultSet.getString("codeProduct"));

                Statement statement1 = connectorMySQL.getConnection().createStatement();
                statement1.executeQuery(String.format("SELECT * FROM product WHERE code='%s'",
                        row.get(0)));
                ResultSet resultSet1 = statement1.getResultSet();
                if (resultSet1.next())
                    row.add(resultSet1.getString("name"));
                row.add(String.valueOf(resultSet.getInt("amountProduct")));
                result.add(row);
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
     * <p>This method uses to calculate total amount of the product in the booked table
     * which has the code which equals to input code product</p>
     *
     * @param codeTable a {@code String} object represents for the specific booked table
     *
     * @param codeProduct a {@code String} object represents for the product wants to sum amount
     *
     * @return a {@code int} value represents for the number of the product
     *          which it's code equals to the input code of the product
     */
    public int getAmountSameProduct(String codeTable, String codeProduct) {
        ConnectorMySQL connectorMySQL = new ConnectorMySQL();
        int sum = 0;
        try {
            Statement statement = connectorMySQL.getConnection().createStatement();
            statement.executeQuery(String.format("SELECT * FROM %s WHERE codeProduct='%s'",
                    codeTable.toLowerCase(), codeProduct));
            ResultSet resultSet = statement.getResultSet();
            if (resultSet.next())
                sum += resultSet.getInt("amountProduct");
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        connectorMySQL.close();
        return sum;
    }
}
