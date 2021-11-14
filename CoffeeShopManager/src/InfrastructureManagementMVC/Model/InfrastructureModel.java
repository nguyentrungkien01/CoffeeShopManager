package InfrastructureManagementMVC.Model;

import InfrastructureManagementMVC.Enum.NameTable;

import java.util.Date;

/**
 * <p>{@code InfrastructureModel} is the model of the infrastructure</p>
 * <p>{@code InfrastructureModel} contains all of attributes of the infrastructure</p>
 * <p>{@code InfrastructureModel} is an abstract class</p>
 * <p>This class has an constructor with 2 parameters to set all attributes for the infrastructure</p>
 */
public abstract class InfrastructureModel {

    /**
     * This attribute represents for the code of the infrastructure
     */
    protected String code;

    /**
     * This attribute represents for the purchase date of the infrastructure
     */
    protected Date purchaseDate;

    /**
     * This attribute represents for the name of the infrastructure
     */
    protected NameTable name;

    /**
     * <p>This constructor uses to init an {@code InfrastructureModel}
     * object with specific values</p>
     *
     * @param name a {@code String} object represents for the name of the infrastructure
     *
     * @param purchaseDate a {@code Date} object represents for the purchase date of the infrastructure
     */
    public InfrastructureModel(NameTable name, Date purchaseDate) {
        this.name = name;
        this.purchaseDate = purchaseDate;
    }

    /**
     *
     * @return the purchaseDate
     */
    public Date getPurchaseDate() {
        return purchaseDate;
    }

    /**
     *
     * @param purchaseDate the purchaseDate
     */
    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    /**
     *
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     *
     * @return the name
     */
    public NameTable getName() {
        return name;
    }

    /**
     *
     * @param name the name
     */
    public void setName(NameTable name) {
        this.name = name;
    }
}
