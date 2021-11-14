package HumanManagementMVC.Model;

import HumanManagementMVC.EnumAndSubclass.Part;
import HumanManagementMVC.EnumAndSubclass.Sex;

import java.util.Date;

/**
 * <p>{@code CashierModel} is the model of the cashier staff</p>
 * <p>{@code CashierModel} contains all of attributes of the cashier staff</p>
 * <p>{@code CashierModel} extends {@code StaffModel}</p>
 * <p>This class has an constructor with 9 parameters to set all attributes for the cashier staff</p>
 */
public class CashierModel extends StaffModel {

    /*
       This is an instance init block to set the code for the cashier staff
       when create an CashierModel object
     */
    {
        setCode(Part.CASHIER);
    }

    /**
     * This constructor uses to init an {@code CashierModel}
     * object with specific values
     *
     * @param name        a {@code String} object represents for the name uses
     *                    to set name for the cashier staff
     * @param address     a{@code String} object represents for the address uses
     *                    to set name for the cashier staff
     * @param phoneNumber a {@code String} object represents for the phone number uses
     *                    to set phone number for the cashier staff
     * @param idCard      a {@code String} object represents for the identity card uses
     *                    to set identity card for the cashier staff
     * @param yearOfBirth a {@code Date} object represents for the birth uses
     *                    to set birth for the cashier staff
     * @param sex         a {@code Sex} enum represents for the sex uses
     *                    to set sex for the cashier staff
     * @param startDate   a {@code Date} object represents for the start date uses
     *                    to set start date for the cashier staff
     * @param overTime    a {@code int} value represents for the amount of the overtime uses
     *                    to set  amount of overtime for the cashier staff
     * @param basicSalary a {@code double} value represents for the amount of the basic salary uses
     *                    to set amount of the basic salary for the cashier staff
     */
    public CashierModel(String name,
                        String address,
                        String phoneNumber,
                        String idCard,
                        Date yearOfBirth,
                        Sex sex,
                        Date startDate,
                        int overTime,
                        double basicSalary) {
        super(name, address, phoneNumber, idCard, yearOfBirth, sex, startDate, overTime, basicSalary);
    }
}
