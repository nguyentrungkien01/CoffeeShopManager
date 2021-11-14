package HumanManagementMVC.Model;

import HumanManagementMVC.EnumAndSubclass.*;
import java.util.Date;

/**
 * <p>{@code CookerModel} is the model of the cooker staff</p>
 * <p>{@code CookerModel} contains all of attributes of the cooker staff</p>
 * <p>{@code CookerModel} extends {@code StaffModel}</p>
 * <p>This class has an constructor with 11 parameters to set all attributes for the cooker staff</p>
 */
public class CookerModel extends StaffModel {

    /**
     * This attribute represents for the shift of the
     * cooker staff
     */
    private Shift kindShift;

    /**
     * This attribute represents for the number of the
     * bonus of the cooker staff
     */
    private double bonus;

    /*
    This is an instance init block to set the code for the cooker staff
    when create an CookerModel object
    */ {
        setCode(Part.COOKER);
    }

    /**
     * This constructor uses to init an {@code CookerModel}
     * object with specific values
     *
     * @param name        a {@code String} object represents for the name uses
     *                    to set name for the cooker staff
     * @param address     a{@code String} object represents for the address uses
     *                    to set name for the cooker staff
     * @param phoneNumber a {@code String} object represents for the phone number uses
     *                    to set phone number for the cooker staff
     * @param idCard      a {@code String} object represents for the identity card uses
     *                    to set identity card for the cooker staff
     * @param yearOfBirth a {@code Date} object represents for the birth uses
     *                    to set birth for the cooker staff
     * @param sex         a {@code Sex} enum represents for the sex uses
     *                    to set sex for the cooker staff
     * @param startDate   a {@code Date} object represents for the start date uses
     *                    to set start date for the cooker staff
     * @param overTime    a {@code int} value represents for the amount of the overtime uses
     *                    to set  amount of overtime for the cooker
     * @param basicSalary a {@code double} value represents for the amount of the basic salary uses
     *                    to set amount of the basic salary for the cooker staff
     * @param kindShift   a {@code Shift} enum represents for the shift uses
     *                    to set shift for the cooker
     * @param bonus       a {@code double} value represents for the amount of the bonus uses
     *                    to set amount of the bonus for the cooker staff
     */
    public CookerModel(String name,
                       String address,
                       String phoneNumber,
                       String idCard,
                       Date yearOfBirth,
                       Sex sex,
                       Date startDate,
                       int overTime,
                       double basicSalary,
                       Shift kindShift,
                       double bonus) {
        super(name, address, phoneNumber, idCard, yearOfBirth, sex, startDate, overTime, basicSalary);
        this.kindShift = kindShift;
        this.bonus = bonus;
    }

    /**
     * @param bonus the bonus
     */
    public void setBonus(double bonus) {
        this.bonus = bonus;
    }

    /**
     * @return the kindShift
     */
    public Shift getKindShift() {
        return kindShift;
    }

    /**
     * @return the bonus
     */
    public double getBonus() {
        return bonus;
    }

    /**
     * @param kindShift the kindShift
     */
    public void setKindShift(Shift kindShift) {
        this.kindShift = kindShift;
    }

}
