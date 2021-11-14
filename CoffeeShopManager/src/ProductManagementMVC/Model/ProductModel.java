package ProductManagementMVC.Model;

import ProductManagementMVC.Enum.KindProduct;
import ProductManagementMVC.Enum.SaleTime;
import java.util.Date;
import java.util.Set;

/**
 * <p>{@code ProductModel} is the model of the product</p>
 * <p>{@code ProductModel} contains all of attributes of the product</p>
 * <p>{@code ProductModel} is an abstract class</p>
 * <p>This class has an constructor with 7 parameters to set all attributes for the product</p>
 */
public abstract class ProductModel {

    /**
     * This attribute represents for the code of the product
     */
    protected String code;

    /**
     * This attribute represents for the name of the product
     */
    protected String name;

    /**
     * This attribute represents for the sale cost of the product
     */
    protected double saleCost;

    /**
     * This attribute represents for the purchase cost of the product
     */
    protected double purchaseCost;

    /**
     * This attribute represents for the list of sale time of the product
     */
    protected Set<SaleTime> saleTimeSet;

    /**
     * This attribute represents for the kind of product of the product
     */
    protected KindProduct kind;

    /**
     * This attribute represents for the amount of the product
     */
    protected int amount;

    /**
     * This attribute represents for the expiration date  of the product
     */
    protected Date expirationDate;

    /**
     * This attribute represents for the manufacture date of the product
     */
    protected Date manufactureDate;

    /**
     * <p>this method uses to create an {@code ProductModel}</p>
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
     */
    public ProductModel(String name,
                 double purchaseCost,
                 double saleCost,
                 int amount,
                 Set<SaleTime> saleTimeSet,
                 Date expirationDate,
                 Date manufactureDate){
        this.name= name;
        this.purchaseCost=purchaseCost;
        this.saleCost = saleCost;
        this.amount = amount;
        this.saleTimeSet = saleTimeSet;
        this.expirationDate = expirationDate;
        this.manufactureDate = manufactureDate;
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
    public String getName() {
        return name;
    }

    /**
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return the saleCost
     */
    public double getSaleCost() {
        return saleCost;
    }

    /**
     *
     * @param saleCost the saleCost
     */
    public void setSaleCost(double saleCost) {
        this.saleCost = saleCost;
    }

    /**
     *
     * @return the saleTimeSet
     */
    public Set<SaleTime> getSaleTimeSet() {
        return saleTimeSet;
    }

    /**
     *
     * @param saleTimeSet the saleTimeSet
     */
    public void setSaleTimeSet(Set<SaleTime> saleTimeSet) {
        this.saleTimeSet = saleTimeSet;
    }

    /**
     *
     * @return the kind
     */
    public KindProduct getKind() {
        return this.kind;
    }

    /**
     *
     * @param amount the amount
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
     *
     * @return the amount
     */
    public int getAmount() {
        return amount;
    }

    /**
     *
     * @return the expirationDate
     */
    public Date getExpirationDate() {
        return expirationDate;
    }

    /**
     *
     * @return the manufactureDate
     */
    public Date getManufactureDate() {
        return manufactureDate;
    }

    /**
     *
     * @param purchaseCost the purchaseCost
     */
    public void setPurchaseCost(double purchaseCost) {
        this.purchaseCost = purchaseCost;
    }

    /**
     *
     * @return the purchaseCost
     */
    public double getPurchaseCost() {
        return purchaseCost;
    }

    /**
     *
     * @param expirationDate the expirationDate
     */
    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    /**
     *
     * @param manufactureDate the manufactureDate
     */
    public void setManufactureDate(Date manufactureDate) {
        this.manufactureDate = manufactureDate;
    }
}

