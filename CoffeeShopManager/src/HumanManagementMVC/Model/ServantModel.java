package HumanManagementMVC.Model;

import HumanManagementMVC.EnumAndSubclass.*;

import java.util.Date;

/**
 * <p>{@code ServantModel} is the model of the servant staff</p>
 * <p>{@code ServantModel} contains all of attributes of the servant staff</p>
 * <p>{@code ServantModel} extends {@code StaffModel}</p>
 * <p>This class has an constructor with 10 parameters to set all attributes for the servant staff</p>
 */
public class ServantModel extends StaffModel {

    /**
     * This attribute represents for the shift of the
     * servant staff
     */
    private Shift kindShift;

    /**
     * This attribute represents for the number of the
     * bonus of the servant staff
     */
    private double bonus;

    /*
     * This is an instance init block to set the code for the servant staff
     * when create an ServantModel object
     */ {
        setCode(Part.SERVANT);
    }

    /**
     * This constructor uses to init an {@code ServantModel}
     * object with specific values
     *
     * @param name        a {@code String} object represents for the name uses
     *                    to set name for the servant staff
     * @param address     a{@code String} object represents for the address uses
     *                    to set name for the servant staff
     * @param phoneNumber a {@code String} object represents for the phone number uses
     *                    to set phone number for the servant staff
     * @param idCard      a {@code String} object represents for the identity card uses
     *                    to set identity card for the servant staff
     * @param yearOfBirth a {@code Date} object represents for the birth uses
     *                    to set birth for the servant staff
     * @param sex         a {@code Sex} enum represents for the sex uses
     *                    to set sex for the servant staff
     * @param startDate   a {@code Date} object represents for the start date uses
     *                    to set start date for the servant staff
     * @param overTime    a {@code int} value represents for the amount of the overtime uses
     *                    to set  amount of overtime for the servant staff
     * @param basicSalary a {@code double} value represents for the amount of the basic salary uses
     *                    to set amount of the basic salary for the servant staff
     * @param bonus       a {@code double} value represents for the amount of the bonus uses
     *                    to set amount of the bonus for the servant staff
     */
    public ServantModel(String name,
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
}
