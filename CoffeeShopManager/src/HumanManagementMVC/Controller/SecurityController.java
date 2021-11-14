package HumanManagementMVC.Controller;

import java.util.Vector;

/**
 * <p>This class extends the {@code HumanController} class</p>
 * <p>In this class contains all methods which the {@code SecurityController}
 * object can use</p>
 */
public class SecurityController extends HumanController {

    /**
     * <p>This method overrides the <code>calSalary</code>
     * method in the {@code HumanController}</p>
     * <p>This method specifies how the Security staff
     * calculates salary </p>
     *
     * @param code a {@code String} object represents for the
     *             code of the Security staff
     *
     * @return a {@code double} value represents for the salary of the Security
     */
    @Override
    public double calSalary(String code) {
        double salary;//result
        double basicSalary = this.getBasicSalary(code);//get basic salary of the cashier staff

        /*
         * get all amount of each kind of day off
         * index 0 is amount of haft day off with permission
         * index 1 is amount of haft day off without permission
         * index 2 is amount of all day off with permission
         * index 3 is amount of all day off without permission
         */
        Vector<Integer> amountDayOffVector = this.getAmountDayOffArray(code);
        salary = basicSalary + this.getOvertime(code) * (basicSalary / 30);

        /*
         * check kind of shift of the staff whose code is equal to code argument
         * then add salary with suitable amount money
         */
        switch (this.getShift(code)) {
            case DAY -> salary += 300000;
            case NIGHT -> salary += 400000;
            case ALL -> salary += 500000;
        }

        // haft day off (permission)
        salary -= amountDayOffVector.get(0) * (basicSalary / 30 / 2);
        // haft day off (non-permission)
        if (amountDayOffVector.get(1) != 0)
            salary -= amountDayOffVector.get(1) * (basicSalary / 30) + 100000;
        // all day off (permission)
        salary -= amountDayOffVector.get(2) * (basicSalary / 30);
        // all day off (non-permission)
        if (amountDayOffVector.get(3) != 0)
            salary -= amountDayOffVector.get(3) * (basicSalary / 30) + 200000;

        return salary;
    }
}
