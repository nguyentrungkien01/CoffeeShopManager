package ProductManagementMVC.Controller;

import HumanManagementMVC.Controller.HumanController;
import Interface.Controller;
import JDBCConnector.ConnectorMySQL;
import ProductManagementMVC.Enum.SaleTime;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;


/**
 * <p>{@code ProductController} contains all methods represent for
 * all action of the product manager</p>
 *
 * <p>{@code ProductController} implements {@code Controller} interface</p>
 *
 */
public class ProductController implements Controller {

    /**
     * <p>This method overrides <code>add</code> method in the {@code Controller} interface</p>
     * <p>This method use to add a product to database</p>
     * <p>By passing an object, that the reason why call methods at runtime Using Java reflection</p>
     * <p></p>
     *
     * @param objModel an {@code Object} represents for an object wants to add
     */
    @Override
    public void add(Object objModel) {

        /*
         * create and initial default value all of attributes of
         * the kinds of product
         */
        String code = "";
        String name = "";
        double saleCost = 0;
        double purchaseCost = 0;
        int amount = 0;
        Set<SaleTime> saleTimeSet;
        Date expirationDate = null;
        Date manufactureDate = null;
        String kind = "";
        Vector<Integer> saleTimeVector = new Vector<>();

        /*
         * use reflection to invoke methods
         */
        for (int i = 0; i < 5; i++)
            saleTimeVector.add(0);
        try {
            code = objModel.getClass().getMethod("getCode").invoke(objModel).toString();
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ignored) {
        }
        try {
            name = objModel.getClass().getMethod("getName").invoke(objModel).toString();
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ignored) {
        }
        try {
            saleCost = (double) objModel.getClass().getMethod("getSaleCost").invoke(objModel);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ignored) {
        }
        try {
            purchaseCost = (double) objModel.getClass().getMethod("getPurchaseCost").invoke(objModel);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ignored) {
        }
        try {
            amount = (int) objModel.getClass().getMethod("getAmount").invoke(objModel);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ignored) {
        }
        try {
            saleTimeSet = (Set<SaleTime>) objModel.getClass().getMethod("getSaleTimeSet").invoke(objModel);
            saleTimeSet.forEach(e -> {
                switch (e) {
                    case MORNING -> saleTimeVector.set(0, 1);
                    case NOON -> saleTimeVector.set(1, 1);
                    case AFTERNOON -> saleTimeVector.set(2, 1);
                    case NIGHT -> saleTimeVector.set(3, 1);
                    case LATE -> saleTimeVector.set(4, 1);
                }
            });
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ignored) {
        }
        try {
            expirationDate = (Date) objModel.getClass().getMethod("getExpirationDate").invoke(objModel);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ignored) {
        }
        try {
            manufactureDate = (Date) objModel.getClass().getMethod("getManufactureDate").invoke(objModel);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ignored) {
        }
        try {
            kind = objModel.getClass().getMethod("getKindFood").invoke(objModel).toString();
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException ignored) {
        }
        try {
            kind = objModel.getClass().getMethod("getKindDrink").invoke(objModel).toString();
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException ignored) {
        }

        /*
         * add data to database
         */
        ConnectorMySQL connectorMySQL = new ConnectorMySQL();
        try {
            Statement statement = connectorMySQL.getConnection().createStatement();
            statement.executeUpdate(
                    String.format("INSERT INTO cost_product(code, saleCost, purchaseCost) VALUES('%s', %f, %f)",
                            code, saleCost, purchaseCost));
            statement.executeUpdate(String.format("INSERT INTO sale_time(code, morning, noon, afternoon, night, late) " +
                            "VALUES ('%s', %d, %d, %d, %d, %d)",
                    code,
                    saleTimeVector.get(0),
                    saleTimeVector.get(1),
                    saleTimeVector.get(2),
                    saleTimeVector.get(3),
                    saleTimeVector.get(4)));
            statement.executeUpdate(String.format("INSERT INTO amount_each_product(code, amountProduct) " +
                    "VALUES('%s',%d)", code, amount));

            statement.executeUpdate(String.format("INSERT INTO info_expir_manuf(code, expiration, manufacture) " +
                            "VALUES ('%s', '%s', '%s')",
                    code,
                    HumanController.formatDate(expirationDate),
                    HumanController.formatDate(manufactureDate))
            );
            statement.executeUpdate(String.format("INSERT INTO kind_product(code, kindProduct) " +
                    "VALUES ('%s', '%s')", code, kind));
            statement.executeUpdate(String.format("INSERT INTO product(code, name) VALUES ('%s', '%s')",
                    code, name));
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
     * @param code original {@code String} object
     */
    @Override
    public void remove(String code) {
        code=code.toUpperCase();
        ConnectorMySQL connectorMySQL = new ConnectorMySQL();
        try {
            Statement statement = connectorMySQL.getConnection().createStatement();
            statement.executeUpdate(String.format("DELETE FROM product WHERE code='%s'", code));
            statement.executeUpdate(String.format("DELETE FROM cost_product WHERE code='%s'", code));
            statement.executeUpdate(String.format("DELETE FROM kind_product WHERE code='%s'", code));
            statement.executeUpdate(String.format("DELETE FROM sale_time WHERE code='%s'", code));
            statement.executeUpdate(String.format("DELETE FROM info_expir_manuf WHERE code='%s'", code));
            statement.executeUpdate(String.format("DELETE FROM amount_each_product WHERE code='%s'", code));
            statement.executeQuery("SELECT * FROM amount_each_kind_product");
            ResultSet resultSet = statement.getResultSet();
            if (resultSet.next()) {
                if (code.substring(0, code.indexOf("0")).equalsIgnoreCase("FOOD"))
                    statement.executeUpdate(String.format("UPDATE amount_each_kind_product SET amountKindFood=%d",
                            resultSet.getInt("amountKindFood") - 1));
                if (code.substring(0, code.indexOf("0")).equalsIgnoreCase("DRINK"))
                    statement.executeUpdate(String.format("UPDATE amount_each_kind_product SET amountKindDrink=%d",
                            resultSet.getInt("amountKindDrink") - 1));

            }
            statement.executeUpdate(String.format("DELETE FROM product WHERE code ='%s'", code));
            statement.executeUpdate("ALTER TABLE product DROP id");
            statement.executeUpdate("ALTER TABLE product AUTO_INCREMENT =1");
            statement.executeUpdate("ALTER TABLE product ADD id INT NOT NULL AUTO_INCREMENT PRIMARY KEY FIRST");
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        connectorMySQL.close();
    }

    /**
     * <p>This is method, which only will be used in this class.
     * This method has a mission get all information of an object in database
     * then return it</p>
     *
     * @param resultSet original {@code ResultSet} object
     *
     * @return an {@code Vector<Vector<String>>} object
     *
     * @throws SQLException This exception will throw when fail to access to database
     */
    @Override
    public Vector<Vector<String>> getModelData(ResultSet resultSet) throws SQLException{
        Vector<Vector<String>> result = new Vector<>();
        while (resultSet.next()) {
            Vector<String> row = new Vector<>();
            row.add(String.valueOf(resultSet.getInt("id")));
            row.add(resultSet.getString("code"));
            row.add(resultSet.getString("name"));
            row.add(resultSet.getString("kindProduct"));
            Vector<Integer> saleTimeVector = new Vector<>();
            saleTimeVector.add(resultSet.getInt("morning"));
            saleTimeVector.add(resultSet.getInt("noon"));
            saleTimeVector.add(resultSet.getInt("afternoon"));
            saleTimeVector.add(resultSet.getInt("night"));
            saleTimeVector.add(resultSet.getInt("late"));
            row.add(SaleTime.getSaleTime(saleTimeVector));
            row.add(String.format("%,.2f", resultSet.getDouble("purchaseCost")));
            row.add(String.format("%,.2f", resultSet.getDouble("saleCost")));
            row.add(resultSet.getDate("expiration").toString());
            row.add(resultSet.getDate("manufacture").toString());
            row.add(String.valueOf(resultSet.getInt("amountProduct")));
            result.add(row);
        }
        return result;
    }

    /**
     * <p>This method will search all objects contain keyword argument then return
     * a {@code Vector<Vector<String>>} them</p>
     *
     * @param key original {@code String} object
     *
     * @return list of all {@code String} represents all of data was found in database
     */
    @Override
    public Vector<Vector<String>> searchKey(String key) {
        Vector<Vector<String>> resultList = new Vector<>();
        ConnectorMySQL connectorMySQL = new ConnectorMySQL();
        try {
            Statement statement = connectorMySQL.getConnection().createStatement();
            statement.executeQuery(String.format("SELECT a.id, a.code, a.name, b.purchaseCost, b.saleCost,c.morning," +
                            "c.noon, c.afternoon, c.night, c.late, d.expiration, d.manufacture, e.amountProduct, f.kindProduct " +
                            "FROM " +
                            "product a, cost_product b, sale_time c, info_expir_manuf d, amount_each_product e, kind_product f " +
                            "WHERE " +
                            "a.code=b.code AND a.code=c.code AND a.code=d.code AND a.code=e.code AND a.code=f.code AND(" +
                            "a.code LIKE '%s' OR a.name LIKE '%s' OR f.kindProduct LIKE '%s')",
                    "%" + key + "%", "%" + key + "%", "%" + key + "%"));
            ResultSet resultSet = statement.getResultSet();
            resultList = getModelData(resultSet);
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        connectorMySQL.close();
        return resultList;
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
        Vector<Vector<String>> resultList = new Vector<>();
        ConnectorMySQL connectorMySQL = new ConnectorMySQL();
        try {
            Statement statement = connectorMySQL.getConnection().createStatement();
            statement.executeQuery(String.format("SELECT a.id, a.code, a.name, b.purchaseCost, b.saleCost,c.morning, c.noon," +
                    "c.afternoon, c.night, c.late, d.expiration, d.manufacture, e.amountProduct, f.kindProduct " +
                    "FROM " +
                    "product a, cost_product b, sale_time c, info_expir_manuf d, amount_each_product e, kind_product f " +
                    "WHERE " +
                    "a.code=b.code AND a.code=c.code AND a.code=d.code AND a.code=e.code AND a.code=f.code AND(" +
                    "(b.purchaseCost BETWEEN %f AND %f) OR (b.saleCost BETWEEN %f AND %f) OR " +
                    "(e.amountProduct BETWEEN %d AND %d))", begin, end, begin, end, (int) begin, (int) end));
            ResultSet resultSet = statement.getResultSet();
            resultList = getModelData(resultSet);
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        connectorMySQL.close();
        return resultList;
    }

    /**
     * <p>This method return null</p>
     *
     * @param month a {@code int} value
     *
     * @return null value
     */
    @Override
    public Vector<Vector<String>> searchKey(int month) {
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
     *          each product was found in database and each column represents each
     *          information of that product
     */
    @Override
    public Vector<Vector<String>> getAll(String order) {
        Vector<Vector<String>> resultList = new Vector<>();
        ConnectorMySQL connectorMySQL = new ConnectorMySQL();
        try {
            Statement statement = connectorMySQL.getConnection().createStatement();
            statement.executeQuery("SELECT a.id, a.code, a.name, b.saleCost, b.purchaseCost,c.morning, c.noon, " +
                    "c.afternoon, c.night, c.late, d.expiration, d.manufacture, e.amountProduct, f.kindProduct " +
                    "FROM " +
                    "product a, cost_product b, sale_time c, info_expir_manuf d, amount_each_product e, kind_product f " +
                    "WHERE " +
                    "a.code=b.code AND a.code=c.code AND a.code=d.code AND a.code=e.code AND a.code=f.code " + order);
            ResultSet resultSet = statement.getResultSet();
            resultList = getModelData(resultSet);
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        connectorMySQL.close();
        return resultList;
    }

    /**
     * Search code of product and update name of that product in database
     *
     * @param code original {@code String} object -> Code of product was stored in database
     *
     * @param name original {@code String} object -> New name will replace old name in database
     */
    public void updateName(String code, String name) {
        ConnectorMySQL connectorMySQL = new ConnectorMySQL();
        try {
            Statement statement = connectorMySQL.getConnection().createStatement();
            statement.executeUpdate(String.format("UPDATE product SET name='%s' WHERE code='%s'", name, code));
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        connectorMySQL.close();
    }

    /**
     * Search code of product and update kindProduct of that product in database
     *
     * @param code original {@code String} object -> Code of product was stored in database
     *
     * @param kindProduct original {@code String} object -> New kindProduct will
     *                    replace old kindProduct in database
     */
    public void updateKindProduct(String code, String kindProduct) {
        ConnectorMySQL connectorMySQL = new ConnectorMySQL();
        try {
            Statement statement = connectorMySQL.getConnection().createStatement();
            statement.executeUpdate(String.format("UPDATE kind_product SET kindProduct='%s' WHERE code='%s'",
                    kindProduct, code));
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        connectorMySQL.close();
    }

    /**
     * Search code of product and update purchaseCost of that product in database
     *
     * @param code original {@code String} object -> Code of product was stored in database
     *
     * @param purchaseCost original {@code Date} object -> New purchase Cost will
     *                     replace old purchase Cost in database
     */
    public void updatePurchaseCost(String code, double purchaseCost) {
        if (purchaseCost >= 0) {
            ConnectorMySQL connectorMySQL = new ConnectorMySQL();
            try {
                Statement statement = connectorMySQL.getConnection().createStatement();
                statement.executeUpdate(String.format("UPDATE cost_product SET purchaseCost=%f WHERE code='%s'", purchaseCost, code));
                statement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            connectorMySQL.close();
        }
    }

    /**
     * Search code of product and update saleCost of that product in database
     *
     * @param code original {@code String} object -> Code of product was stored in database
     *
     * @param saleCost original {@code double} object -> New sale Cost will replace old sale Cost in database
     */
    public void updateSaleCost(String code, double saleCost) {
        ConnectorMySQL connectorMySQL = new ConnectorMySQL();
        try {
            Statement statement = connectorMySQL.getConnection().createStatement();
            statement.executeQuery(String.format("SELECT cost_product.purchaseCost FROM cost_product WHERE code='%s'", code));
            ResultSet resultSet = statement.getResultSet();
            double purchaseCost = 0;
            if (resultSet.next())
                purchaseCost = resultSet.getDouble("purchaseCost");
            if (purchaseCost <= saleCost)
                statement.executeUpdate(String.format("UPDATE cost_product SET saleCost=%f WHERE code='%s'", saleCost, code));
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        connectorMySQL.close();
    }

    /**
     * Search code of product and update saleTimeSet of that product in database
     *
     * @param code original {@code String} object -> Code of product was stored in database
     *
     * @param saleTimeSet original {@code Set<SaleTime>} object -> New sale time will
     *                    replace old sale time in database
     */
    public void updateSaleTime(String code, Set<SaleTime> saleTimeSet) {
        Vector<Integer> saleTimeVector = SaleTime.getSaleTime(saleTimeSet);
        ConnectorMySQL connectorMySQL = new ConnectorMySQL();
        try {
            Statement statement = connectorMySQL.getConnection().createStatement();
            statement.executeUpdate(String.format("UPDATE sale_time SET morning=%d WHERE code='%s'", saleTimeVector.get(0), code));
            statement.executeUpdate(String.format("UPDATE sale_time SET noon=%d WHERE code='%s'", saleTimeVector.get(1), code));
            statement.executeUpdate(String.format("UPDATE sale_time SET afternoon=%d WHERE code='%s'", saleTimeVector.get(2), code));
            statement.executeUpdate(String.format("UPDATE sale_time SET night=%d WHERE code='%s'", saleTimeVector.get(3), code));
            statement.executeUpdate(String.format("UPDATE sale_time SET late=%d WHERE code='%s'", saleTimeVector.get(4), code));
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        connectorMySQL.close();
    }

    /**
     * Search code of product and update amount of that product in database
     *
     * @param code original {@code String} object -> Code of product was stored in database
     *
     * @param amount original {@code int} object -> New amount will replace old amount in database
     */
    public void updateAmount(String code, int amount) {
        if (amount >= 0) {
            ConnectorMySQL connectorMySQL = new ConnectorMySQL();
            try {
                Statement statement = connectorMySQL.getConnection().createStatement();
                statement.executeUpdate(String.format("UPDATE amount_each_product SET amountProduct=%d WHERE code='%s'",
                        amount, code));
                statement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            connectorMySQL.close();
        }
    }

    /**
     * Search code of product and update expiration Date of that product in database
     *
     * @param code original {@code String} object -> Code of product was stored in database
     *
     * @param expirationDate original {@code Date} object -> New expirationDate will
     *                       replace old expirationDate in database
     */
    public void updateExpirationDate(String code, Date expirationDate) {
        ConnectorMySQL connectorMySQL = new ConnectorMySQL();
        try {
            Statement statement = connectorMySQL.getConnection().createStatement();
            statement.executeUpdate(String.format("UPDATE info_expir_manuf SET expiration='%s' WHERE code='%s'",
                    expirationDate, code));
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        connectorMySQL.close();
    }

    /**
     * Search code of product and update manufacture Date of that product in database
     *
     * @param code original {@code String} object -> Code of product was stored in database
     *
     * @param manufactureDate original {@code Date} object -> New manufacture Date will
     *                        replace old manufacture Date in database
     */
    public void updateManufactureDate(String code, Date manufactureDate) {
        ConnectorMySQL connectorMySQL = new ConnectorMySQL();
        try {
            Statement statement = connectorMySQL.getConnection().createStatement();
            statement.executeUpdate(String.format("UPDATE info_expir_manuf SET manufacture='%s' WHERE code='%s'",
                    manufactureDate, code));
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        connectorMySQL.close();
    }

    /**
     * <p>This method uses to get the list of the product with the specific sale time</p>
     * <p>this method get the current time and compare with the sale time of the product. i
     * f equal add to result list</p>
     *
     *
     * @param productList a {@code Vector<Vector<String>>} object represents for the product list
     *
     * @return a {@code Vector<Vector<String>>} object represents for the list of product with a specific sale time
     */
    public Vector<Vector<String>> getProductsWithSaleTime(Vector<Vector<String>> productList) {
        Vector<Vector<String>> productListResult = new Vector<>();
        int time = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        productList.forEach(product -> {
            for (String saleTime : product.get(4).split(",")) {
                boolean flag = false;
                for (SaleTime value : SaleTime.values())
                    if (saleTime.trim().equals(value.toString()) && value.getBeg() <= time && time < value.getEnd()) {
                        productListResult.add(product);
                        flag=true;
                        break;
                    }
                if(flag) break;
            }

        });
        return productListResult;
    }
}

