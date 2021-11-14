package HumanManagementMVC.Model;


import HumanManagementMVC.EnumAndSubclass.Part;
import HumanManagementMVC.EnumAndSubclass.Sex;

import java.util.Date;

/**
 * <p>{@code HousekeeperModel} is the model of the housekeeper staff</p>
 * <p>{@code HousekeeperModel} contains all of attributes of the housekeeper staff</p>
 * <p>{@code HousekeeperModel} extends {@code StaffModel}</p>
 * <p>This class has an constructor with 9 parameters to set all attributes for the housekeeper staff</p>
 */
public class HousekeeperModel extends StaffModel {

    /*
        This is an instance init block to set the code for the housekeeper staff
        when create an HousekeeperModel object
     */ {
        setCode(Part.HOUSEKEEPER);
    }

    /**
     * This constructor uses to init an {@code HousekeeperModel}
     * object with specific values
     *
     * @param name        a {@code String} object represents for the name uses
     *                    to set name for the housekeeper staff
     * @param address     a{@code String} object represents for the address uses
     *                    to set name for the housekeeper staff
     * @param phoneNumber a {@code String} object represents for the phone number uses
     *                    to set phone number for the housekeeper staff
     * @param idCard      a {@code String} object represents for the identity card uses
     *                    to set identity card for the housekeeper staff
     * @param yearOfBirth a {@code Date} object represents for the birth uses
     *                    to set birth for the housekeeper staff
     * @param sex         a {@code Sex} enum represents for the sex uses
     *                    to set sex for the housekeeper staff
     * @param startDate   a {@code Date} object represents for the start date uses
     *                    to set start date for the housekeeper staff
     * @param overTime    a {@code int} value represents for the amount of the overtime uses
     *                    to set  amount of overtime for the housekeeper staff
     * @param basicSalary a {@code double} value represents for the amount of the basic salary uses
     *                    to set amount of the basic salary for the housekeeper staff
     */
    public HousekeeperModel(String name,
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
