package HumanManagementMVC.Controller;

import HumanManagementMVC.EnumAndSubclass.Shift;
import JDBCConnector.ConnectorMySQL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

/**
 * {@code HumanController} is an abstract class
 * This class contains all methods which get data of the staff
 */
public abstract class HumanController {

    /**
     * <p>This method is an abstract method </p>
     * <p>All classes extend {@code HumanController} must override this method</p>
     *
     * @param code a {@code String} represents for the code of the staff
     *
     * @return a {@code double} represents for the total salary of the
     *          staff whose code equals code argument
     */
    public abstract double calSalary(String code);

    /**
     * <p>This method is a static method <br>
     *
     *  This method uses to format a {@code Date} object with
     *  pattern is <code> yyyy-MM-dd</code> to a {@code String}
     *  </p>
     *
     * @param date a {@code Date} object which wants to format
     *
     * @return a {@code String} object represents for the date
     *          value after formatting
     */
    public static String formatDate(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

    /**
     * <p>This method uses to get shift of the staff who has code
     * equals to code argument in database. <br>
     *
     * This method accesses to the {@code shift} table and gets
     * shift of the staff in database. <br>
     *
     * The data which gets from database is an {@code String} object, so uses
     * <code>checkShift</code> method in the {@code Shift} enum
     * to convert to enum value
     * </p>
     *
     * @param code a {@code String} object represents for the code of the staff
     *
     * @return a <p>{@code Shift} enum represent for the shift of the staff
     *          whose code equals to code argument.</p>
     *          <p>If can't get data in database, returns a {@code null} value </p>
     */
    public Shift getShift(String code) {
        Shift shift = null;//create a null value for shift of the staff
        ConnectorMySQL connectorMySQL = new ConnectorMySQL();
        try {
            Statement statement = connectorMySQL.getConnection().createStatement();
            //Get shift of staff from database
            statement.executeQuery(String.format("SELECT * FROM shift WHERE code='%s'", code));
            ResultSet resultSet = statement.getResultSet();
            if (resultSet.next())
                //get shift in String form then convert it to Shift enum
                shift = Shift.checkShift(resultSet.getString("shift"));
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        connectorMySQL.close();
        return shift;
    }

    /**
     * <p>This method uses to get bonus of the staff who has code
     * which equals to code argument in database. <br>
     *
     * This method accesses to the {@code bonus} table and gets
     * amount of bonus of the staff in database. <br>
     * </p>
     *
     * @param code a {@code String} object represents for the code of the staff
     *
     * @return a <p>{@code double} value represent for the bonus of the staff
     *          whose code equals to code argument.</p>
     *          <p>If can't get data in database, returns a zero value </p>
     */
    public double getBonus(String code) {
        double bonus = 0;//create a zero value for bonus of the staff
        ConnectorMySQL connectorMySQL = new ConnectorMySQL();
        try {
            Statement statement = connectorMySQL.getConnection().createStatement();
            //Get bonus of staff from database
            statement.executeQuery(String.format("SELECT * FROM bonus WHERE code='%s'", code));
            ResultSet resultSet = statement.getResultSet();
            if (resultSet.next())
                //assign bonus with value has just got from database
                bonus = resultSet.getDouble("bonus");
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        connectorMySQL.close();
        return bonus;
    }

    /**
     * <p>This method uses to get bonus of the staff who has code
     * which equals to code argument in database. <br>
     *
     * This method accesses to the {@code rate_profit} table and gets
     * amount of rate profit of the staff in database. <br>
     * </p>
     *
     * @param code a {@code String} object represents for the code of the staff
     *
     * @return a <p>{@code double} value represent for the bonus of the staff
     *          whose code equals to code argument.</p>
     *          <p>If can't get data in database, returns a zero value </p>
     */
    public double getRateProfit(String code) {
        double rateProfit = 0;//create a zero value for rete profit of the staff
        ConnectorMySQL connectorMySQL = new ConnectorMySQL();
        try {
            Statement statement = connectorMySQL.getConnection().createStatement();
            //Get rate profit of staff from database
            statement.executeQuery(String.format("SELECT * FROM rate_profit WHERE code='%s'", code));
            ResultSet resultSet = statement.getResultSet();
            if (resultSet.next())
                //assign rateProfit with value has just got from database
                rateProfit = resultSet.getDouble("rateProfit");
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        connectorMySQL.close();
        return rateProfit;
    }

    /**
     * <p>This method uses to get amount each kind of day off of the
     * staff who has code which equals to code argument in database. <br>
     *
     * This method accesses to the {@code dayoff} table and gets
     * amount each kind of day off of the staff in database. <br>
     * </p>
     *
     * @param code a {@code String} object represents for the code of the staff
     *
     * @return a <p>{@code Vector<Integer>} object represents for the list
     *          amount each kind of day off of the staff whose code which equals
     *          to code argument.</p>
     *          <p>If can't get data in database, returns a null object </p>
     */
    public Vector<Integer> getAmountDayOffArray(String code) {
        Vector<Integer> result = new Vector<>();//create an empty vector to contain amount day off
        ConnectorMySQL connectorMySQL = new ConnectorMySQL();
        try {
            Statement statement = connectorMySQL.getConnection().createStatement();
            //Get amount day off of staff from database
            statement.executeQuery(String.format("SELECT * FROM dayoff WHERE code='%s'", code));
            ResultSet resultSet = statement.getResultSet();
            if (resultSet.next()) {
                //get amount haft day off with permission
                result.add(resultSet.getInt("haftPermiss"));
                //get amount haft day off with non-permission
                result.add(resultSet.getInt("haftNonPermiss"));
                //get amount all day off with permission
                result.add(resultSet.getInt("allPermiss"));
                //get amount all day off with non-permission
                result.add(resultSet.getInt("allNonPermiss"));
            }
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        connectorMySQL.close();
        return result;
    }

    /**
     * <p>This method uses to get amount over time of the staff who has code
     * which equals to code argument in database. <br>
     *
     * This method accesses to the {@code overtime} table and gets
     * amount of overtime of the staff in database. <br>
     * </p>
     *
     * @param code a {@code String} object represents for the code of the staff
     *
     * @return a <p>{@code int} value represents for the amount overtime of the staff
     *          whose code which equals to code argument.</p>
     *          <p>If can't get data in database, returns a zero value </p>
     */
    public int getOvertime(String code) {
        ConnectorMySQL connectorMySQL = new ConnectorMySQL();
        int overtime = 0;//create a zero value for amount overtime of the staff
        try {
            Statement statement = connectorMySQL.getConnection().createStatement();
            //Get amount overtime of staff from database
            statement.executeQuery(String.format("SELECT * FROM overtime WHERE code='%s'", code));
            ResultSet resultSet = statement.getResultSet();
            if (resultSet.next())
                //assign overtime with value has just got from database
                overtime = resultSet.getInt("overtime");
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        connectorMySQL.close();
        return overtime;
    }

    /**
     * <p>This method uses to get amount basic salary of the staff who has code
     * which equals to code argument in database. <br>
     *
     * This method accesses to the {@code basic salary} table and gets
     * amount of basic salary of the staff in database. <br>
     * </p>
     *
     * @param code a {@code String} object represents for the code of the staff
     *
     * @return a <p>{@code double} value represents for the amount basic salary of the staff
     *          whose code which equals to code argument.</p>
     *          <p>If can't get data in database, returns a zero value </p>
     */
    public double getBasicSalary(String code) {
        ConnectorMySQL connectorMySQL = new ConnectorMySQL();
        double basicSalary = 0;//create a zero value for amount basic salary of the staff
        try {
            Statement statement = connectorMySQL.getConnection().createStatement();
            //Get amount basic salary of staff from database
            statement.executeQuery(String.format("SELECT * FROM basic_salary WHERE code='%s'", code));
            ResultSet resultSet = statement.getResultSet();
            if (resultSet.next())
                //assign basicSalary with value has just got from database
                basicSalary = resultSet.getDouble("basicSalary");
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        connectorMySQL.close();
        return basicSalary;
    }

    /**
     * <p>This method uses to get specific name of the staff who has code
     * which equals to code argument in database. <br>
     *
     * This method accesses to the {@code human} table and gets
     * name of the staff in database. <br>
     * </p>
     *
     * @param code a {@code String} object represents for the code of the staff
     *
     * @return a <p>{@code String} object represents for the name of the staff
     *          whose code which equals to code argument.</p>
     *          <p>If can't get data in database, returns a null value </p>
     */
    public String getName(String code) {
        String name = null;//create a null value for name of the staff
        ConnectorMySQL connectorMySQL = new ConnectorMySQL();
        try {
            Statement statement = connectorMySQL.getConnection().createStatement();
            //Get name of staff from database
            statement.executeQuery(String.format("SELECT human.name FROM human WHERE code='%s'", code));
            ResultSet resultSet = statement.getResultSet();
            if (resultSet.next())
                //assign name with value has just got from database
                name = resultSet.getString("name");
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        connectorMySQL.close();
        return name;
    }

    /**
     * <p>This method uses to get list staff's code in database. <br>
     *
     * This method accesses to the {@code sex} table and gets
     * name of the staff in database. <br>
     * </p>
     *
     * @return a <p>{@code Vector<String>} object represents for list of staff's code</p>
     *          <p>If can't get data in database, return a null value </p>
     */
    public Vector<String> getAllCodeStaff() {
        Vector<String> result = new Vector<>();//create a null value for name of the staff
        ConnectorMySQL connectorMySQL = new ConnectorMySQL();
        try {
            Statement statement = connectorMySQL.getConnection().createStatement();
            //Get all data of staff from sex table in database
            statement.executeQuery("SELECT * FROM sex");
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next())
                //add  value has just got from database to list
                result.add(resultSet.getString("code"));
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        connectorMySQL.close();
        return result;
    }
}
