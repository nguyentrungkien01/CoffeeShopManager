package HumanManagementMVC.Model;

import HumanManagementMVC.EnumAndSubclass.DayOff;
import HumanManagementMVC.EnumAndSubclass.KindOff;
import HumanManagementMVC.EnumAndSubclass.Part;
import HumanManagementMVC.EnumAndSubclass.Sex;
import JDBCConnector.ConnectorMySQL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * <p>{@code StaffModel} is the model of the staff</p>
 * <p>{@code StaffModel} contains all of attributes of the staff</p>
 * <p>{@code StaffModel} is an abstract class and extends {@code HumanModel}</p>
 * <p>This class has an constructor with 9 parameters to set all attributes for the staff</p>
 */
public abstract class StaffModel extends HumanModel {

    /**
     * This attribute represents for the code of the
     * staff
     */
    protected String code;

    /**
     * This attribute represents for the start date of the
     * staff
     */
    protected Date startDate;

    /**
     * This attribute represents for the list day off of the
     * staff
     */
    protected List<DayOff> dayOff;

    /**
     * This attribute represents for the amount overtime of the
     * staff
     */
    protected int overTime;

    /**
     * This attribute represents for the basic salary of the
     * staff
     */
    protected double basicSalary;

    /**
     * <p>This constructor uses to init an {@code StaffModel}
     * object with specific values</p>
     * <p>The <code>dayOff</code> attribute init wtih 0</p>
     *
     * @param name        a {@code String} object represents for the name uses
     *                    to set name for the staff
     * @param address     a{@code String} object represents for the address uses
     *                    to set name for the staff
     * @param phoneNumber a {@code String} object represents for the phone number uses
     *                    to set phone number for the staff
     * @param idCard      a {@code String} object represents for the identity card uses
     *                    to set identity card for the staff
     * @param yearOfBirth a {@code Date} object represents for the birth uses
     *                    to set birth for the staff
     * @param sex         a {@code Sex} enum represents for the sex uses
     *                    to set sex for the staff
     * @param startDate   a {@code Date} object represents for the start date uses
     *                    to set start date for the staff
     * @param overTime    a {@code int} value represents for the amount of the overtime uses
     *                    to set  amount of overtime for the staff
     * @param basicSalary a {@code double} value represents for the amount of the basic salary uses
     *                    to set amount of the basic salary for the staff
     */
    public StaffModel(String name,
               String address,
               String phoneNumber,
               String idCard,
               Date yearOfBirth,
               Sex sex,
               Date startDate,
               int overTime,
               double basicSalary) {
        super(name, address, phoneNumber, idCard, yearOfBirth, sex);
        List<DayOff> list = new LinkedList<>();
        list.add(new DayOff(KindOff.HAFT, true, 0));
        list.add(new DayOff(KindOff.HAFT, false, 0));
        list.add(new DayOff(KindOff.ALL, true, 0));
        list.add(new DayOff(KindOff.ALL, false, 0));
        this.startDate = startDate;
        this.dayOff = list;
        this.overTime = overTime;
        this.basicSalary = basicSalary;
    }

    public String getCode() {
        return this.code;
    }

    /**
     * <p><pre>
     *
     * This class has a instance block uses for auto increasing code and format that code.
     * The instance block will access to amount_each_part table in database to get amount of
     * instances have been create before then use that value then increase it 1 and then assign
     * the count variable to get the exactly numerical order for the new instance
     *
     *  </pre></p>
     *
     * @param part original Part enum represents kind of part
     */
    public void setCode(Part part) {
        ConnectorMySQL connectorMySQL = new ConnectorMySQL();
        try {
            Statement statement = connectorMySQL.getConnection().createStatement();
            statement.executeQuery(String.format(
                    "SELECT * FROM amount_each_part WHERE partCode='%s'", part.toString()));
            ResultSet resultSet = statement.getResultSet();
            int count = 0;
            if (resultSet.next())
                count = resultSet.getInt("amount");
            count++;
            this.code = String.format("%s%05d", part.toString(), count);
            statement.executeUpdate(String.format(
                    "UPDATE amount_each_part SET amount=%d WHERE partCode='%s'", count, part));
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        connectorMySQL.close();
    }

    /**
     * @param code the code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return the startDate
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * @return the basicSalary
     */
    public double getBasicSalary() {
        return basicSalary;
    }

    /**
     * @return the overtime
     */
    public int getOverTime() {
        return overTime;
    }

    /**
     * @return the dayOff
     */
    public List<DayOff> getDayOff() {
        return dayOff;
    }

    /**
     * @param dayOff the dayOff
     */
    public void setDayOff(List<DayOff> dayOff) {
        this.dayOff = dayOff;
    }

    /**
     * @param startDate the startDate
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * @param basicSalary the basicSalary
     */
    public void setBasicSalary(double basicSalary) {
        this.basicSalary = basicSalary;
    }

    /**
     * @param overTime the overTime
     */
    public void setOverTime(int overTime) {
        this.overTime = overTime;
    }
}
