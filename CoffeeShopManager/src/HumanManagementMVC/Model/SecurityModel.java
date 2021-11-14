package HumanManagementMVC.Model;

import HumanManagementMVC.EnumAndSubclass.*;
import java.util.Date;


/**
 * <p>{@code SecurityModel} is the model of the security staff</p>
 * <p>{@code SecurityModel} contains all of attributes of the security staff</p>
 * <p>{@code SecurityModel} extends {@code StaffModel}</p>
 * <p>This class has an constructor with 10 parameters to set all attributes for the security staff</p>
 */
public class SecurityModel extends StaffModel {

    /**
     * This attribute represents for the shift of the
     * security staff
     */
    private Shift kindShift;

    /*
    This is an instance init block to set the code for the security staff
    when create an SecurityModel object
    */ {
        setCode(Part.SECURITY);
    }

    /**
     * This constructor uses to init an {@code SecurityModel}
     * object with specific values
     *
     * @param name        a {@code String} object represents for the name uses
     *                    to set name for the security staff
     * @param address     a{@code String} object represents for the address uses
     *                    to set name for the security staff
     * @param phoneNumber a {@code String} object represents for the phone number uses
     *                    to set phone number for the security staff
     * @param idCard      a {@code String} object represents for the identity card uses
     *                    to set identity card for the security staff
     * @param yearOfBirth a {@code Date} object represents for the birth uses
     *                    to set birth for the security staff
     * @param sex         a {@code Sex} enum represents for the sex uses
     *                    to set sex for the security staff
     * @param startDate   a {@code Date} object represents for the start date uses
     *                    to set start date for the security staff
     * @param overTime    a {@code int} value represents for the amount of the overtime uses
     *                    to set  amount of overtime for the security staff
     * @param basicSalary a {@code double} value represents for the amount of the basic salary uses
     *                    to set amount of the basic salary for the security staff
     * @param kindShift   a {@code Shift} enum represents for the shift uses
     *                    to set shift for the security staff
     */
    public SecurityModel(String name,
                         String address,
                         String phoneNumber,
                         String idCard,
                         Date yearOfBirth,
                         Sex sex,
                         Date startDate,
                         int overTime,
                         double basicSalary,
                         Shift kindShift) {
        super(name, address, phoneNumber, idCard, yearOfBirth, sex, startDate, overTime, basicSalary);
        this.kindShift = kindShift;
    }

    /**
     * @param kindShift the kind kindShift
     */
    public void setKindShift(Shift kindShift) {
        this.kindShift = kindShift;
    }

    /**
     * @return the kindShift
     */
    public Shift getKindShift() {
        return kindShift;
    }

}
