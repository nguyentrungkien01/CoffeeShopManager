package HumanManagementMVC.Controller;

import HumanManagementMVC.EnumAndSubclass.KindOff;
import HumanManagementMVC.EnumAndSubclass.Sex;
import HumanManagementMVC.EnumAndSubclass.Shift;
import Interface.Controller;
import JDBCConnector.ConnectorMySQL;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Objects;
import java.util.Vector;

/**
 * <p>{@code ManagerController} contains all methods represent for
 * all actions of the manager</p>
 *
 * <p>{@code ManagerController} extends {@code HumanController} and
 * implements {@code Controller} interface</p>
 *
 */
public class ManagerController extends HumanController implements Controller {

    /**
     * <p>This method overrides the {@code calSalary}
     * method in the {@code HumanController}</p>
     * <p>This method specifies how the manager staff
     * calculates salary </p>
     *
     * @param code a {@code String} object represents for the code of the manager staff
     *
     * @return a {@code double} value represents for the salary of the manager
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
        salary = basicSalary + this.getOvertime(code) * (basicSalary / 28) * 2 +
                basicSalary * this.getRateProfit(code);

        // haft day off (permission)
        salary -= amountDayOffVector.get(0) * (basicSalary / 28 / 2);
        // haft day off (non-permission)
        if (amountDayOffVector.get(1) != 0)
            salary -= amountDayOffVector.get(1) * (basicSalary / 28) + 7000000;
        // all day off (permission)
        salary -= amountDayOffVector.get(2) * (basicSalary / 28);
        // all day off (non-permission)
        if (amountDayOffVector.get(3) != 0)
            salary -= amountDayOffVector.get(3) * (basicSalary / 28) + 1000000;

        return salary;
    }

    /**
     * <p>This method overrides <code>add</code> method in the {@code Controller} interface</p>
     * <p>This method uses to add a staff to database</p>
     * <p>By passing an object, that the reason why calls methods at runtime Using Java reflection</p>
     * <p></p>
     *
     * @param objModel an {@code Object} represents for an staff wants to add
     */
    @Override
    public void add(Object objModel) {
        ConnectorMySQL connectorMySQL = new ConnectorMySQL();

        /*
         * create and initial default value all of attributes of
         * the kinds of staff
         */
        String code = null;
        String shift = null;
        String part;
        Date startDate = null;
        double basicSalary = 0;
        double bonus = 0;
        double rateProfit = 0;
        int overTime = 0;
        int haftPermiss = 0, haftNonPermiss = 0, allPermiss = 0, allNonPermiss = 0;

        /*
         * use reflection to invoke methods
         */
        part = objModel.getClass().getCanonicalName().toUpperCase();
        part = part.substring(part.lastIndexOf(".") + 1, part.lastIndexOf("M"));
        try {
            code = objModel.getClass().getMethod("getCode").invoke(objModel).toString();
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ignored) {
        }
        try {
            startDate = (Date) objModel.getClass().getMethod("getStartDate").invoke(objModel);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ignored) {
        }
        try {
            shift = objModel.getClass().getMethod("getKindShift").invoke(objModel).toString();
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ignored) {
        }
        try {
            basicSalary = Double.parseDouble(objModel.getClass().getMethod("getBasicSalary").invoke(objModel).toString());
        } catch (NumberFormatException | IllegalAccessException | InvocationTargetException | NoSuchMethodException ignored) {
        }
        try {
            bonus = Double.parseDouble(objModel.getClass().getMethod("getBonus").invoke(objModel).toString());
        } catch (NumberFormatException | IllegalAccessException | InvocationTargetException | NoSuchMethodException ignored) {
        }
        try {
            rateProfit = Double.parseDouble(objModel.getClass().getMethod("getRateProfit").invoke(objModel).toString());
        } catch (NumberFormatException | IllegalAccessException | InvocationTargetException | NoSuchMethodException ignored) {
        }
        try {
            overTime = Integer.parseInt(objModel.getClass().getMethod("getOverTime").invoke(objModel).toString());
        } catch (NumberFormatException | IllegalAccessException | InvocationTargetException | NoSuchMethodException ignored) {
        }
        try {
            haftPermiss = Integer.parseInt(objModel.getClass().getMethod("getAmountDayOff", KindOff.class, boolean.class)
                    .invoke(objModel, KindOff.HAFT, true).toString());
        } catch (NumberFormatException | IllegalAccessException | InvocationTargetException | NoSuchMethodException ignored) {
        }
        try {
            haftNonPermiss = Integer.parseInt(objModel.getClass().getMethod("getAmountDayOff", KindOff.class, boolean.class)
                    .invoke(objModel, KindOff.HAFT, false).toString());
        } catch (NumberFormatException | IllegalAccessException | InvocationTargetException | NoSuchMethodException ignored) {
        }
        try {
            allPermiss = Integer.parseInt(objModel.getClass().getMethod("getAmountDayOff", KindOff.class, boolean.class)
                    .invoke(objModel, KindOff.ALL, true).toString());
        } catch (NumberFormatException | IllegalAccessException | InvocationTargetException | NoSuchMethodException ignored) {
        }
        try {
            haftNonPermiss = Integer.parseInt(objModel.getClass().getMethod("getAmountDayOff", KindOff.class, boolean.class)
                    .invoke(objModel, KindOff.ALL, false).toString());
        } catch (NumberFormatException | IllegalAccessException | InvocationTargetException | NoSuchMethodException ignored) {
        }

        /*
         * add data to database
         */
        try {
            Statement statement = connectorMySQL.getConnection().createStatement();
            //insert basic salary to database
            statement.executeUpdate(String.format(
                    "INSERT INTO basic_salary(code, basicSalary) VALUES('%s', %f)", code, basicSalary));
            //insert bonus to database
            statement.executeUpdate(String.format(
                    "INSERT INTO bonus(code, bonus) VALUES('%s', %f)", code, bonus));
            //insert overtime to database
            statement.executeUpdate(String.format(
                    "INSERT INTO overtime(code, overtime) VALUES('%s', %d)", code, overTime));
            //insert part to database
            statement.executeUpdate(String.format(
                    "INSERT INTO part(code, part) VALUES('%s', '%s')", code, part));
            //insert rate profit to database
            statement.executeUpdate(String.format(
                    "INSERT INTO rate_profit(code, rateProfit) VALUES ('%s', %f)", code, rateProfit));
            //insert sex to database
            try {
                statement.executeUpdate(String.format(
                        "INSERT INTO sex(code, sex) VALUES('%s', '%s')", code,
                        objModel.getClass().getMethod("getSex").invoke(objModel).toString()));
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ignored) {
            }
            //insert day off to database
            statement.executeUpdate(String.format(
                    "INSERT INTO shift(code, shift) VALUES ('%s', '%s')",
                    code, Objects.requireNonNullElse(shift, "")));
            statement.executeUpdate(String.format(
                    "INSERT INTO dayoff(code, haftPermiss, haftNonPermiss, allPermiss, allNonPermiss) " +
                            "VALUES ('%s', %d, %d, %d, %d)",
                    code, haftPermiss, haftNonPermiss, allPermiss, allNonPermiss));
            //insert main information to database
            try {
                statement.executeUpdate(String.format(
                        "INSERT INTO human(code, name, yearOfBirth, age, address,phoneNumber, idCard, startDate) " +
                                "VALUES ('%s', '%s', '%s', %d, '%s', '%s', '%s', '%s')",
                        code, objModel.getClass().getMethod("getName").invoke(objModel),
                        formatDate((Date) objModel.getClass().getMethod("getYearOfBirth").invoke(objModel)),
                        Integer.parseInt(objModel.getClass().getMethod("getAge").invoke(objModel).toString()),
                        objModel.getClass().getMethod("getAddress").invoke(objModel),
                        objModel.getClass().getMethod("getPhoneNumber").invoke(objModel),
                        objModel.getClass().getMethod("getIdCard").invoke(objModel),
                        formatDate((startDate))));
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ignored) {
            }
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        connectorMySQL.close();
    }

    /**
     * <p>This method provides method to remove information of an object
     * in database whose code which equals to the code argument</p>
     *
     * @param code original {@code String} represents for the code of the staff
     */
    @Override
    public void remove(String code) {
        code=code.toUpperCase();
        ConnectorMySQL connectorMySQL = new ConnectorMySQL();
        try {
            Statement statement = connectorMySQL.getConnection().createStatement();
            //get amount of part to update
            statement.executeQuery(String.format("SELECT * FROM amount_each_part WHERE partCode='%s'",
                    code.substring(0, code.indexOf("0"))));
            ResultSet resultSet = statement.getResultSet();
            if (resultSet.next()) {
                //update amount of part
                int amount = resultSet.getInt("amount");
                if (amount > 0) {
                    //remove staff from database
                    statement.executeUpdate(String.format("DELETE FROM human WHERE code ='%s'", code));
                    statement.executeUpdate(String.format("UPDATE amount_each_part SET amount=%d WHERE partCode='%s'",
                            amount - 1, code.substring(0, code.indexOf("0"))));
                    statement.executeUpdate(String.format("DELETE FROM account WHERE code='%s'", code));
                    statement.executeUpdate(String.format("DELETE FROM basic_salary WHERE code ='%s'", code));
                    statement.executeUpdate(String.format("DELETE FROM bonus WHERE code ='%s'", code));
                    statement.executeUpdate(String.format("DELETE FROM dayoff WHERE code ='%s'", code));
                    statement.executeUpdate(String.format("DELETE FROM overtime WHERE code ='%s'", code));
                    statement.executeUpdate(String.format("DELETE FROM part WHERE code ='%s'", code));
                    statement.executeUpdate(String.format("DELETE FROM rate_profit WHERE code ='%s'", code));
                    statement.executeUpdate(String.format("DELETE FROM sex WHERE code ='%s'", code));
                    statement.executeUpdate(String.format("DELETE FROM shift WHERE code ='%s'", code));
                    //update numerical order
                    statement.executeUpdate(String.format("DELETE FROM human WHERE code='%s'", code));
                    statement.executeUpdate("ALTER TABLE human DROP id");
                    statement.executeUpdate("ALTER TABLE human AUTO_INCREMENT =1");
                    statement.executeUpdate("ALTER TABLE human ADD id INT NOT NULL AUTO_INCREMENT PRIMARY KEY FIRST");

                }
            }
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        connectorMySQL.close();
    }

    /**
     * <p>This method, which only will be used in this class.
     * This method has a mission get all information of an object in database
     * then return it</p>
     *
     * @param resultSet original {@code ResultSet} object
     *
     * @return an {@code Vector<Vector<String>>} object represents for the information of the staff
     *
     * @throws SQLException This exception will throw when fail to access to database
     */
    @Override
    public Vector<Vector<String>> getModelData(ResultSet resultSet) throws SQLException {
        Vector<Vector<String>> list = new Vector<>();
        while (resultSet.next()) {
            Vector<String> row = new Vector<>();
            row.add(String.valueOf(resultSet.getInt("id")));
            row.add(resultSet.getString("code"));
            row.add(resultSet.getString("name"));
            row.add(String.valueOf(resultSet.getDate("yearOfBirth")));
            row.add(String.valueOf(resultSet.getInt("age")));
            row.add(resultSet.getString("address"));
            row.add(resultSet.getString("phoneNumber"));
            row.add(resultSet.getString("idCard"));
            row.add(String.valueOf(resultSet.getDate("startDate")));
            row.add(resultSet.getString("sex"));
            row.add(resultSet.getString("shift"));
            row.add(resultSet.getString("part"));
            row.add(String.format("%,.2f", resultSet.getDouble("basicSalary")));
            row.add(String.format("%,.2f", resultSet.getDouble("bonus")));
            row.add(String.valueOf(resultSet.getInt("haftPermiss")));
            row.add(String.valueOf(resultSet.getInt("haftNonPermiss")));
            row.add(String.valueOf(resultSet.getInt("allPermiss")));
            row.add(String.valueOf(resultSet.getInt("allNonPermiss")));
            row.add(String.valueOf(resultSet.getInt("overtime")));
            row.add(String.valueOf(resultSet.getDouble("rateProfit")));
            list.add(row);
        }
        return list;
    }

    /**
     * <p>This method will search all objects contain keyword argument then return
     * a {@code Vector<Vector<String>>} them</p>
     *
     * @param keyword original {@code String} object represents for the
     *                keyword which wants to search
     *
     * @return list of all {@code String} represents for all of data was found in database
     */
    @Override
    public Vector<Vector<String>> searchKey(String keyword) {

        ConnectorMySQL connectorMySQL = new ConnectorMySQL();
        Vector<Vector<String>> list = new Vector<>();
        if (!keyword.isEmpty())
            try {
                Statement statement = connectorMySQL.getConnection().createStatement();
                String keywordTemp = "%" + keyword + "%";
                statement.executeQuery(String.format(
                        "SELECT a.id,a.code, a.name, a.yearOfBirth, a.age, a.address, a.phoneNumber, a.idCard, a.startDate,b.sex," +
                                " c.shift, d.part, e.basicSalary, f.bonus, g.haftPermiss, g.haftNonPermiss, g.allPermiss, " +
                                "g.allNonPermiss, h.overtime, i.rateProfit " +
                                "FROM " +
                                "human a, sex b, shift c, part d, basic_salary e, bonus f, dayoff g, overtime h, rate_profit i " +
                                "WHERE " +
                                "a.code=b.code AND a.code=c.code AND a.code=d.code AND a.code=e.code AND a.code=f.code AND " +
                                "a.code=g.code AND a.code=h.code AND a.code=i.code AND (a.code LIKE '%s' OR a.name LIKE '%s' OR " +
                                "a.address LIKE '%s' OR a.phoneNumber LIKE '%s' OR a.idCard LIKE '%s' OR b.sex LIKE '%s' OR " +
                                "c.shift LIKE '%s' OR d.part LIKE '%s')",
                        keywordTemp, keywordTemp, keywordTemp, keywordTemp, keywordTemp, keywordTemp, keywordTemp, keywordTemp));
                ResultSet resultSet = statement.getResultSet();
                list = getModelData(resultSet);
                statement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        connectorMySQL.close();
        return list;
    }

    /**
     * <p>This method will search all objects which have value which is
     * in between begin argument and end argument then return
     * a {@code Vector<Vector<String>>} them</p>
     *
     * @param begin a {@code double} value represents for the begin value
     *
     * @param end a {@code double} value represents for the end value
     *
     * @return list of all {@code String} represents for all of data were
     *          found in database
     */
    @Override
    public Vector<Vector<String>> searchKey(double begin, double end) {
        Vector<Vector<String>> list = new Vector<>();
        ConnectorMySQL connectorMySQL = new ConnectorMySQL();
        try {
            Statement statement = connectorMySQL.getConnection().createStatement();
            statement.executeQuery(String.format(
                    "SELECT a.id,a.code, a.name, a.yearOfBirth, a.age, a.address, a.phoneNumber, a.idCard, a.startDate,b.sex," +
                            " c.shift, d.part, e.basicSalary, f.bonus, g.haftPermiss, g.haftNonPermiss, g.allPermiss, " +
                            "g.allNonPermiss, h.overtime, i.rateProfit " +
                            "FROM " +
                            "human a, sex b, shift c, part d, basic_salary e, bonus f, dayoff g, overtime h, rate_profit i " +
                            "WHERE " +
                            "a.code=b.code AND a.code=c.code AND a.code=d.code AND a.code=e.code AND a.code=f.code AND " +
                            "a.code=g.code AND a.code=h.code AND a.code=i.code AND ((a.age BETWEEN %d AND %d) OR " +
                            "(e.basicSalary BETWEEN %f AND %f) OR (f.bonus BETWEEN %f AND %f) OR " +
                            "(g.haftPermiss BETWEEN %d AND %d) OR (g.haftNonPermiss BETWEEN %d AND %d) OR " +
                            "(g.allPermiss BETWEEN %d AND %d) OR (g.allNonPermiss BETWEEN %d AND %d) OR " +
                            "(h.overtime BETWEEN %d AND %d) OR (i.rateProfit BETWEEN %f AND %f))",
                    (int) begin, (int) end, begin, end, begin, end, (int) begin, (int) end, (int) begin, (int) end,
                    (int) begin, (int) end, (int) begin, (int) end, (int) begin, (int) end, begin, end));
            ResultSet resultSet = statement.getResultSet();
            try {
                list = getModelData(resultSet);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            statement.close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        connectorMySQL.close();
        return list;
    }

    /**
     * <p>This method will search all of staff has birth contains
     * keyword argument equals to month then return two-dimensional
     * vector object represent all of data of staff</p>
     *
     * @param keyword o {@code int} value represents for the number keyword
     *
     * @return {@code Vector<Vector<String>>} represents for the list of all of
     *      objects was found in database
     */
    @Override
    public Vector<Vector<String>> searchKey(int keyword) {
        Vector<Vector<String>> list = new Vector<>();
        ConnectorMySQL connectorMySQL = new ConnectorMySQL();
        try {
            Statement statement = connectorMySQL.getConnection().createStatement();
            statement.executeQuery(String.format(
                    "SELECT a.id,a.code, a.name, a.yearOfBirth, a.age, a.address, a.phoneNumber, a.idCard, a.startDate,b.sex," +
                            " c.shift, d.part, e.basicSalary, f.bonus, g.haftPermiss, g.haftNonPermiss, g.allPermiss, " +
                            "g.allNonPermiss, h.overtime, i.rateProfit " +
                            "FROM " +
                            "human a, sex b, shift c, part d, basic_salary e, bonus f, dayoff g, overtime h, rate_profit i " +
                            "WHERE " +
                            "a.code=b.code AND a.code=c.code AND a.code=d.code AND a.code=e.code AND a.code=f.code AND " +
                            "a.code=g.code AND a.code=h.code AND a.code=i.code AND (a.yearOfBirth LIKE '_____%s%d%s___' )",
                    "%", keyword, "%"));
            ResultSet resultSet = statement.getResultSet();
            try {
                list = getModelData(resultSet);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            statement.close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        connectorMySQL.close();
        return list;
    }

    /**
     * Get all information of each staff was stored in database
     * and then sort them by ascending or descending with order
     * parameter or don't sort and converts all data was found to {@code String}
     *
     * @param order original {@code String} object. If the parameter is empty no sort,
     *              if sort the order parameter has format is
     *              <code>"ORDER BY " + {name column} + [ASC || DESC]</code>
     *
     * @return An {@code Vector<Vector<String>>} object which each row represents
     *          for each staff was found in database and each column represents
     *          for each information of that staff
     */
    @Override
    public Vector<Vector<String>> getAll(String order) {
        Vector<Vector<String>> list = new Vector<>();
        ConnectorMySQL connectorMySQL = new ConnectorMySQL();
        try {
            Statement statement = connectorMySQL.getConnection().createStatement();
            statement.executeQuery("SELECT a.id,a.code, a.name, a.yearOfBirth, a.age, a.address,a.phoneNumber, a.idCard, " +
                    "a.startDate,b.sex, c.shift, d.part, e.basicSalary, f.bonus, g.haftPermiss, g.haftNonPermiss, " +
                    "g.allPermiss, g.allNonPermiss, h.overtime, i.rateProfit " +
                    "FROM " +
                    "human a, sex b, shift c, part d, basic_salary e, bonus f, dayoff g, overtime h, rate_profit i " +
                    "WHERE " +
                    "a.code=b.code AND a.code=c.code AND a.code=d.code AND a.code=e.code AND a.code=f.code AND " +
                    "a.code=g.code AND a.code=h.code AND a.code=i.code " + order);
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()) {
                Vector<String> row = new Vector<>();
                row.add(String.valueOf(resultSet.getInt("id")));
                row.add(resultSet.getString("code"));
                row.add(resultSet.getString("name"));
                row.add(resultSet.getDate("yearOfBirth").toString());
                row.add(String.valueOf(resultSet.getInt("age")));
                row.add(resultSet.getNString("address"));
                row.add(resultSet.getString("phoneNumber"));
                row.add(resultSet.getString("idCard"));
                row.add(resultSet.getDate("startDate").toString());
                row.add(resultSet.getString("sex"));
                row.add(resultSet.getString("shift"));
                row.add(resultSet.getString("part"));
                row.add(String.format("%,.2f", resultSet.getDouble("basicSalary")));
                row.add(String.format("%,.2f",resultSet.getDouble("bonus")));
                row.add(String.valueOf(resultSet.getInt("haftPermiss")));
                row.add(String.valueOf(resultSet.getInt("haftNonPermiss")));
                row.add(String.valueOf(resultSet.getInt("allPermiss")));
                row.add(String.valueOf(resultSet.getInt("allNonPermiss")));
                row.add(String.valueOf(resultSet.getInt("overtime")));
                row.add(String.valueOf(resultSet.getDouble("rateProfit")));
                list.add(row);
            }
            statement.close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        connectorMySQL.close();
        return list;
    }

    /**
     * Search code of staff and update name of that staff in database
     *
     * @param code original {@code String} object -> Code of staff was stored in database
     *
     * @param name original {@code String} object -> New name will replace old name in database
     */
    public void updateName(String code, String name) {
        ConnectorMySQL connectorMySQL = new ConnectorMySQL();
        try {
            Statement statement = connectorMySQL.getConnection().createStatement();
            statement.executeUpdate(String.format("UPDATE human SET name='%s' WHERE code='%s'", name, code));
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        connectorMySQL.close();
    }

    /**
     * Search code of staff and update year of birth of that staff in database
     *
     * @param code original {@code String} object -> Code of staff was stored in database
     *
     * @param date original {@code Date} object -> New year of birth will replace
     *             old year of birth in database
     */
    public void updateYearOfBirth(String code, Date date) {
        ConnectorMySQL connectorMySQL = new ConnectorMySQL();
        try {
            Statement statement = connectorMySQL.getConnection().createStatement();
            statement.executeUpdate(String.format(
                    "UPDATE human SET yearOfBirth='%s' WHERE code='%s'", formatDate(date), code));
            statement.close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        connectorMySQL.close();
    }

    /**
     * Search code of staff and update age of that staff in database
     *
     * @param code original {@code String} object -> Code of staff was stored in database
     *
     * @param age  original {@code int} value -> New age will replace old age in database
     */
    public void updateAge(String code, int age) {
        if (age >= 0) {
            ConnectorMySQL connectorMySQL = new ConnectorMySQL();
            try {
                Statement statement = connectorMySQL.getConnection().createStatement();
                statement.executeUpdate(String.format("UPDATE human SET age=%d WHERE code='%s'", age, code));
                statement.close();
            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }
            connectorMySQL.close();
        }
    }

    /**
     * Search code of staff and update address of that staff in database
     *
     * @param code    original {@code String} object -> Code of staff was stored in database
     *
     * @param address original {@code String} object -> New address will replace old address in database
     */
    public void updateAddress(String code, String address) {
        ConnectorMySQL connectorMySQL = new ConnectorMySQL();
        try {
            Statement statement = connectorMySQL.getConnection().createStatement();
            statement.executeUpdate(String.format("UPDATE human SET address='%s' WHERE code='%s'", address, code));
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        connectorMySQL.close();
    }

    /**
     * Search code of staff and update address of that staff in database
     *
     * @param code        original String object -> Code of staff was stored in database
     * @param phoneNumber original String object -> New phone number will replace old phone number in database
     */
    public void updatePhoneNumber(String code, String phoneNumber) {
        ConnectorMySQL connectorMySQL = new ConnectorMySQL();
        try {
            Statement statement = connectorMySQL.getConnection().createStatement();
            statement.executeUpdate(String.format("UPDATE human SET phoneNumber='%s' WHERE code='%s'", phoneNumber, code));
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        connectorMySQL.close();
    }

    /**
     * Search code of staff and update idCard of that staff in database
     *
     * @param code   original {@code String} object -> Code of staff was stored in database
     *
     * @param idCard original {@code String} object -> New idCard will replace old idCard in database
     */
    public void updateIdCard(String code, String idCard) {
        ConnectorMySQL connectorMySQL = new ConnectorMySQL();
        try {
            Statement statement = connectorMySQL.getConnection().createStatement();
            statement.executeUpdate(String.format("UPDATE human SET idCard='%s' WHERE code='%s'", idCard, code));
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        connectorMySQL.close();
    }

    /**
     * Search code of staff and update start date of that staff in database
     *
     * @param code original {@code String} object -> Code of staff was stored in database
     *
     * @param date original {@code Date} object -> New start date will replace old start  in database
     */
    public void updateStartDate(String code, Date date) {
        ConnectorMySQL connectorMySQL = new ConnectorMySQL();
        try {
            Statement statement = connectorMySQL.getConnection().createStatement();
            statement.executeUpdate(String.format(
                    "UPDATE human SET startDate='%s' WHERE code='%s'", formatDate(date), code));
            statement.close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        connectorMySQL.close();
    }

    /**
     * Search code of staff and update sex of that staff in database
     *
     * @param code original {@code String} object -> Code of staff was stored in database
     *
     * @param sex  original {@code Sex} enum -> New kind of sex will replace old kind of sex in database
     */
    public void updateSex(String code, Sex sex) {
        ConnectorMySQL connectorMySQL = new ConnectorMySQL();
        try {
            Statement statement = connectorMySQL.getConnection().createStatement();
            statement.executeUpdate(String.format(
                    "UPDATE sex SET sex='%s' WHERE code='%s'", sex.toString(), code));
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        connectorMySQL.close();
    }

    /**
     * Search code of staff and update kind of shift of that staff in database
     *
     * @param code  original {@code String} object -> Code of staff was stored in database
     *
     * @param shift original {@code Shift} enum -> New kind of shift will replace old kind of shift in database
     */
    public void updateShift(String code, Shift shift) {
        ConnectorMySQL connectorMySQL = new ConnectorMySQL();
        try {
            Statement statement = connectorMySQL.getConnection().createStatement();
            statement.executeUpdate((shift == null) ?
                    String.format("UPDATE shift SET shift='%s' WHERE code='%s'", "", code) :
                    String.format("UPDATE shift SET shift='%s' WHERE code='%s'", shift.toString(), code));
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        connectorMySQL.close();
    }

    /**
     * Search code of staff and update basic salary  of that staff in database
     *
     * @param code        original {@code String} object -> Code of staff was stored in database
     *
     * @param basicSalary original {@code double } value -> New amount of basic salary
     *                    will replace old amount of basic salary in database
     */
    public void updateBasicSalary(String code, double basicSalary) {
        if (basicSalary >= 0) {
            ConnectorMySQL connectorMySQL = new ConnectorMySQL();
            try {
                Statement statement = connectorMySQL.getConnection().createStatement();
                statement.executeUpdate(String.format(
                        "UPDATE basic_salary SET basicSalary=%f WHERE code='%s'", basicSalary, code));
                statement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            connectorMySQL.close();
        }
    }

    /**
     * Search code of staff and update amount of bonus  of that staff in database
     *
     * @param code  original {@code String} object -> Code of staff was stored in database
     *
     * @param bonus original {@code double} value -> New amount of bonus  will replace
     *              old amount of bonus date in database
     */
    public void updateBonus(String code, double bonus) {
        if (bonus >= 0) {
            ConnectorMySQL connectorMySQL = new ConnectorMySQL();
            try {
                Statement statement = connectorMySQL.getConnection().createStatement();
                statement.executeUpdate(String.format(
                        "UPDATE bonus SET bonus=%f WHERE code='%s'", bonus, code));
                statement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            connectorMySQL.close();
        }
    }

    /**
     * Search code of staff and update amount of haft day of with permission  of that staff in database
     *
     * @param code        original {@code String} object -> Code of staff was stored in database
     *
     * @param haftPermiss original {@code int} value -> New amount of haft day off with permission
     *                    will replace old amount of haft day off with permission in database
     */
    public void updateHaftPermiss(String code, int haftPermiss) {
        if (haftPermiss >= 0) {
            ConnectorMySQL connectorMySQL = new ConnectorMySQL();
            try {
                Statement statement = connectorMySQL.getConnection().createStatement();
                statement.executeUpdate(String.format(
                        "UPDATE dayoff SET haftPermiss=%d WHERE code='%s'", haftPermiss, code));
                statement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            connectorMySQL.close();
        }
    }

    /**
     * Search code of staff and update amount of haft day of without permission  of that staff in database
     *
     * @param code           original {@code String} object -> Code of staff was stored in database
     *
     * @param haftNonPermiss original {@code int} value -> New amount of haft day off without permission
     *                       will replace old amount of haft day off without permission in database
     */
    public void updateHaftNonPermiss(String code, int haftNonPermiss) {
        if (haftNonPermiss >= 0) {
            ConnectorMySQL connectorMySQL = new ConnectorMySQL();
            try {
                Statement statement = connectorMySQL.getConnection().createStatement();
                statement.executeUpdate(String.format(
                        "UPDATE dayoff SET haftNonPermiss=%d WHERE code='%s'", haftNonPermiss, code));
                statement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            connectorMySQL.close();
        }
    }

    /**
     * Search code of staff and update amount of haft day of without permission  of that staff in database
     *
     * @param code          original {@code String} object -> Code of staff was stored in database
     *
     * @param allNonPermiss original {@code int} value -> New amount of all day off without permission
     *                      will replace old amount of all day off without permission in database
     */
    public void updateAllNonPermiss(String code, int allNonPermiss) {
        if (allNonPermiss >= 0) {
            ConnectorMySQL connectorMySQL = new ConnectorMySQL();
            try {
                Statement statement = connectorMySQL.getConnection().createStatement();
                statement.executeUpdate(String.format(
                        "UPDATE dayoff SET allNonPermiss=%d WHERE code='%s'", allNonPermiss, code));
                statement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            connectorMySQL.close();
        }
    }

    /**
     * Search code of staff and update amount of haft day of with permission  of that staff in database
     *
     * @param code       original {@code String} object -> Code of staff was stored in database
     *
     * @param allPermiss original {@code int}value -> New amount of all day off with permission
     *                   will replace old amount of all day off with permission in database
     */
    public void updateAllPermiss(String code, int allPermiss) {
        if (allPermiss >= 0) {
            ConnectorMySQL connectorMySQL = new ConnectorMySQL();
            try {
                Statement statement = connectorMySQL.getConnection().createStatement();
                statement.executeUpdate(String.format(
                        "UPDATE dayoff SET allPermiss=%d WHERE code='%s'", allPermiss, code));
                statement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            connectorMySQL.close();
        }
    }

    /**
     * Search code of staff and increase amount of haft day of with permission
     * by one of that staff in database
     *
     * @param code original {@code String} object -> Code of staff was stored in database
     */
    public void updateHaftPermiss(String code) {
        ConnectorMySQL connectorMySQL = new ConnectorMySQL();
        try {
            Statement statement = connectorMySQL.getConnection().createStatement();
            statement.executeQuery(String.format("SELECT dayoff.haftPermiss FROM dayoff WHERE code='%s'", code));
            ResultSet resultSet = statement.getResultSet();
            if (resultSet.next()) {
                int amount = resultSet.getInt("haftPermiss");
                statement.executeUpdate(String.format(
                        "UPDATE dayoff SET haftPermiss=%d WHERE code='%s'", amount + 1, code));
            }
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        connectorMySQL.close();
    }

    /**
     * Search code of staff and increase amount of haft day of without
     * permission by one of that staff in database
     *
     * @param code original {@code String} object -> Code of staff was stored in database
     */
    public void updateHaftNonPermiss(String code) {
        ConnectorMySQL connectorMySQL = new ConnectorMySQL();
        try {
            Statement statement = connectorMySQL.getConnection().createStatement();
            statement.executeQuery(String.format("SELECT dayoff.haftNonPermiss FROM dayoff WHERE code='%s'", code));
            ResultSet resultSet = statement.getResultSet();
            int amount;
            if (resultSet.next()) {
                amount = resultSet.getInt("haftNonPermiss");
                statement.executeUpdate(String.format(
                        "UPDATE dayoff SET haftNonPermiss=%d WHERE code='%s'", amount + 1, code));
            }
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        connectorMySQL.close();
    }

    /**
     * Search code of staff and increase amount of all day of with permission
     * by one of that staff in database
     *
     * @param code original {@code String} object -> Code of staff was stored in database
     */
    public void updateAllNonPermiss(String code) {
        ConnectorMySQL connectorMySQL = new ConnectorMySQL();
        try {
            Statement statement = connectorMySQL.getConnection().createStatement();
            statement.executeQuery(String.format("SELECT dayoff.allNonPermiss FROM dayoff WHERE code='%s'", code));
            ResultSet resultSet = statement.getResultSet();
            if (resultSet.next()) {
                int amount = resultSet.getInt("allNonPermiss");
                statement.executeUpdate(String.format(
                        "UPDATE dayoff SET allNonPermiss=%d WHERE code='%s'", amount + 1, code));
            }
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        connectorMySQL.close();
    }

    /**
     * Search code of staff and increase amount of all day of without
     * permission by one of that staff in database
     *
     * @param code original {@code String} object -> Code of staff was stored in database
     */
    public void updateAllPermiss(String code) {
        ConnectorMySQL connectorMySQL = new ConnectorMySQL();
        try {
            Statement statement = connectorMySQL.getConnection().createStatement();
            statement.executeQuery(String.format("SELECT dayoff.allPermiss FROM dayoff WHERE code='%s'", code));
            ResultSet resultSet = statement.getResultSet();
            if (resultSet.next()) {
                int amount = resultSet.getInt("allPermiss");
                statement.executeUpdate(String.format(
                        "UPDATE dayoff SET allPermiss=%d WHERE code='%s'", amount + 1, code));
            }
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        connectorMySQL.close();
    }

    /**
     * Search code of staff and update amount of over time of that staff in database
     *
     * @param code     original {@code String} object -> Code of staff was stored in database
     *
     * @param overtime original {@code int} value -> New amount of over time will
     *                 replace old amount of overtime in database
     */
    public void updateOvertime(String code, int overtime) {
        if (overtime >= 0 && overtime <= 100) {
            ConnectorMySQL connectorMySQL = new ConnectorMySQL();
            try {
                Statement statement = connectorMySQL.getConnection().createStatement();
                statement.executeUpdate(String.format(
                        "UPDATE overtime SET overtime=%d WHERE code='%s'", overtime, code));
                statement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            connectorMySQL.close();
        }
    }

    /**
     * Search code of staff and update amount of rate profit of that staff in database
     *
     * @param code       original {@code String} object -> Code of staff was stored in database
     *
     * @param rateProfit original {@code double} value -> New amount of rate profit will
     *                   replace old amount of rate profit in database
     */
    public void updateRateProfit(String code, double rateProfit) {
        if (rateProfit >= 0 && rateProfit <= 1) {
            ConnectorMySQL connectorMySQL = new ConnectorMySQL();
            try {
                Statement statement = connectorMySQL.getConnection().createStatement();
                statement.executeUpdate(String.format(
                        "UPDATE rate_profit SET rateProfit=%f WHERE code='%s'", rateProfit, code));
                statement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            connectorMySQL.close();
        }
    }

}
