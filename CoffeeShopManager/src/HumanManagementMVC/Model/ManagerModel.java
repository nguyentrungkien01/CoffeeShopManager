package HumanManagementMVC.Model;

import HumanManagementMVC.EnumAndSubclass.Part;
import HumanManagementMVC.EnumAndSubclass.Sex;
import java.util.Date;

/**
 * <p>{@code ManagerModel} is the model of the manager staff</p>
 * <p>{@code ManagerModel} contains all of attributes of the manager staff</p>
 * <p>{@code ManagerModel} extends {@code StaffModel}</p>
 * <p>This class has an constructor with 11 parameters to set all attributes for the manager staff</p>
 */
public class ManagerModel extends StaffModel {

    /**
     * This attribute represents for the number of the
     * rate profit of the manager staff
     */
    private double rateProfit;

    /**
     * This attribute represents for the number of the
     * bonus of the manager staff
     */
    private double bonus;

    /*
       This is an instance init block to set the code for the manager staff
       when create an ManagerModel object
    */ {
        setCode(Part.MANAGER);
    }

    /**
     * This constructor uses to init an {@code ManagerModel}
     * object with specific values
     *
     * @param name        a {@code String} object represents for the name uses
     *                    to set name for the manager staff
     * @param address     a{@code String} object represents for the address uses
     *                    to set name for the manager staff
     * @param phoneNumber a {@code String} object represents for the phone number uses
     *                    to set phone number for the manager staff
     * @param idCard      a {@code String} object represents for the identity card uses
     *                    to set identity card for the manager staff
     * @param yearOfBirth a {@code Date} object represents for the birth uses
     *                    to set birth for the manager staff
     * @param sex         a {@code Sex} enum represents for the sex uses
     *                    to set sex for the manager staff
     * @param startDate   a {@code Date} object represents for the start date uses
     *                    to set start date for the manager staff
     * @param overTime    a {@code int} value represents for the amount of the overtime uses
     *                    to set  amount of overtime for the manager staff
     * @param basicSalary a {@code double} value represents for the amount of the basic salary uses
     *                    to set amount of the basic salary for the manager staff
     * @param rateProfit  a {@code double} value represents for the amount of the rate profit uses
     *                    to set amount of the rate profit for the manager staff
     * @param bonus       a {@code double} value represents for the amount of the bonus uses
     *                    to set amount of the bonus for the manager staff
     */
    public ManagerModel(String name,
                        String address,
                        String phoneNumber,
                        String idCard,
                        Date yearOfBirth,
                        Sex sex,
                        Date startDate,
                        int overTime,
                        double basicSalary,
                        double rateProfit,
                        double bonus) {
        super(name, address, phoneNumber, idCard, yearOfBirth, sex, startDate, overTime, basicSalary);
        this.rateProfit = rateProfit;
        this.bonus = bonus;
    }

    /**
     * @return the bonus
     */
    public double getBonus() {
        return bonus;
    }

    /**
     * @param bonus the bonus
     */
    public void setBonus(double bonus) {
        this.bonus = bonus;
    }

    /**
     * @param rateProfit the rate profit
     */
    public void setRateProfit(double rateProfit) {
        this.rateProfit = rateProfit;
    }

    /**
     * @return the rate profit
     */
    public double getRateProfit() {
        return rateProfit;
    }
}
