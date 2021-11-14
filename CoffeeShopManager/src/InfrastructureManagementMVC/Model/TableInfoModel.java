package InfrastructureManagementMVC.Model;

import InfrastructureManagementMVC.Enum.Material;
import InfrastructureManagementMVC.Enum.NameTable;
import JDBCConnector.ConnectorMySQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

/**
 * <p>{@code TableInfoModel} is the model of the table</p>
 * <p>{@code TableInfoModel} contains all of attributes of the table</p>
 * <p>This class has an constructor with 4 parameters to set all attributes for the table</p>
 */
public class TableInfoModel extends InfrastructureModel{

    /**
     * this attributes represents for the capacity of the table
     */
    private int capacity;

    /**
     * this attributes represents for the status of the table
     */
    private boolean status;

    /**
     * this attributes represents for the material of the table
     */
    private Material material;

    /*
     * this instance block uses to create the code for the table when create a new table
     * firstly get recently amount table in database the increase by
     * 1 and set for the code of the new table
     */
    {
        ConnectorMySQL connectorMySQL = new ConnectorMySQL();
        try {
            Statement statement = connectorMySQL.getConnection().createStatement();
            statement.executeQuery("SELECT * FROM amount_table");
            ResultSet resultSet = statement.getResultSet();
            if (resultSet.next()) {
                int amount = resultSet.getInt("amount");
                amount++;
                this.code = String.format("TABLE%05d", amount);
                statement.executeUpdate(String.format("UPDATE amount_table SET amount=%d", amount));
            }
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        connectorMySQL.close();
    }

    /**
     * <p>This method use to create a table </p>
     *
     * @param name a {@code String} object represents for the name of the table
     *
     * @param purchaseDate a {@code Date} object represents for the purchase date of the table
     *
     * @param material a {@code String} object represents for the material of the table
     *
     * @param capacity a {@code int} value represents for the capacity of the table
     */
    public TableInfoModel(NameTable name,
                          Date purchaseDate,
                          Material material,
                          int capacity){
        super(name, purchaseDate);
        this.setMaterial(material);
        this.capacity=capacity;

    }

    /**
     *
     * @return the capacity
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     *
     * @param capacity the capacity
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /**
     *
     * @return the status
     */
    public boolean isStatus() {
        return status;
    }

    /**
     *
     * @param status the status
     */
    public void setStatus(boolean status) {
        this.status = status;
    }

    /**
     *
     * @return the material
     */
    public Material getMaterial() {
        return material;
    }

    /**
     *
     * @param material the material
     */
    public void setMaterial(Material material) {
        this.material = material;
    }
}
