package ProductManagementMVC.Model;

import JDBCConnector.ConnectorMySQL;
import ProductManagementMVC.Enum.KindDrink;
import ProductManagementMVC.Enum.KindProduct;
import ProductManagementMVC.Enum.SaleTime;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Set;

/**
 * <p>{@code DrinkModel} is the model of the drink</p>
 * <p>{@code DrinkModel} contains all of attributes of the drink</p>
 */
public class DrinkModel extends ProductModel {

    /**
     * this attributes represents for the kind drink of the drink
     */
    private KindDrink kindDrink;

    //set code for the product
    {
        ConnectorMySQL connectorMySQL = new ConnectorMySQL();
        try {
            Statement statement = connectorMySQL.getConnection().createStatement();
            statement.executeQuery("SELECT * FROM amount_each_kind_product ");
            ResultSet resultSet = statement.getResultSet();
            if (resultSet.next()) {
                int amount = resultSet.getInt("amountKindDrink");
                amount++;
                this.code = String.format("DRINK%05d", amount);
                statement.executeUpdate(String.format("UPDATE amount_each_kind_product SET amountKindDrink=%d", amount));
            }
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            this.kind = KindProduct.DRINKS;
        }
        connectorMySQL.close();
    }

    /**
     * <p>this method uses to create an {@code DrinkModel}</p>
     *
     * @param name a {@code String} object represents for the name of the product
     *
     * @param purchaseCost a {@code double} value represents for the purchase cost of the product
     *
     * @param saleCost a {@code double} value represents for the sale cost of the product
     *
     * @param amount a {@code int} value represents for the amount of the product
     *
     * @param saleTimeSet a {@code Set<SaleTime>} object represents for the sale time list of the product
     *
     * @param expirationDate a {@code Date} object represents for the expiration Date  of the product
     *
     * @param manufactureDate a {@code Date} object represents for the manufacture Date  of the product
     *
     * @param kindDrink a {@code KindDrink} object represents for the kind of drink
     */
    public DrinkModel(String name,
                      double purchaseCost,
                      double saleCost,
                      int amount,
                      Set<SaleTime> saleTimeSet,
                      Date expirationDate,
                      Date manufactureDate,
                      KindDrink kindDrink) {
        super(name, purchaseCost, saleCost, amount, saleTimeSet, expirationDate, manufactureDate);
        this.kindDrink = kindDrink;
    }

    /**
     *
     * @return the kindDrink
     */
    public KindDrink getKindDrink() {
        return kindDrink;
    }

    /**
     *
     * @param kindDrink the kindDrink
     */
    public void setKindDrink(KindDrink kindDrink) {
        this.kindDrink = kindDrink;
    }
}
