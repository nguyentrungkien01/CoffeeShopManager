package HumanManagementMVC.Model;

import HumanManagementMVC.EnumAndSubclass.Part;
import HumanManagementMVC.EnumAndSubclass.Sex;
import java.util.Date;

/**
 * <p>{@code BartenderModel} is the model of the bartender staff</p>
 * <p>{@code BartenderModel} contains all of attributes of the bartender staff</p>
 * <p>{@code BartenderModel} extends {@code StaffModel}</p>
 * <p>This class has an constructor with 10 parameters to set all attributes for the bartender staff</p>
 */
public class BartenderModel extends StaffModel {

    /**
     * This attribute represents for the number of the
     * bonus of the bartender staff
     */
    private double bonus;

    /*
        This is an instance init block to set the code for the bartender staff
        when create an BartenderModel object
     */
    {
        this.setCode(Part.BARTENDER);
    }

    /**
     * This constructor uses to init an {@code BartenderModel}
     * object with specific values
     *
     * @param name        a {@code String} object represents for the name uses
     *                    to set name for the bartender staff
     * @param address     a{@code String} object represents for the address uses
     *                    to set name for the bartender staff
     * @param phoneNumber a {@code String} object represents for the phone number uses
     *                    to set phone number for the bartender staff
     * @param idCard      a {@code String} object represents for the identity card uses
     *                    to set identity card for the bartender staff
     * @param yearOfBirth a {@code Date} object represents for the birth uses
     *                    to set birth for the bartender staff
     * @param sex         a {@code Sex} enum represents for the sex uses
     *                    to set sex for the bartender staff
     * @param startDate   a {@code Date} object represents for the start date uses
     *                    to set start date for the bartender staff
     * @param overTime    a {@code int} value represents for the amount of the overtime uses
     *                    to set  amount of overtime for the bartender staff
     * @param basicSalary a {@code double} value represents for the amount of the basic salary uses
     *                    to set amount of the basic salary for the bartender staff
     * @param bonus       a {@code double} value represents for the amount of the bonus uses
     *                    to set amount of the bonus for the bartender staff
     */
    public BartenderModel(String name,
                          String address,
                          String phoneNumber,
                          String idCard,
                          Date yearOfBirth,
                          Sex sex,
                          Date startDate,
                          int overTime,
                          double basicSalary,
                          double bonus) {
        super(name, address, phoneNumber, idCard, yearOfBirth, sex, startDate, overTime, basicSalary);
        this.bonus = bonus;
    }

    /**
     * @param bonus the bonus
     */
    public void setBonus(double bonus) {
        this.bonus = bonus;
    }

    /**
     * @return the bonus
     */
    public double getBonus() {
        return bonus;
    }

}
