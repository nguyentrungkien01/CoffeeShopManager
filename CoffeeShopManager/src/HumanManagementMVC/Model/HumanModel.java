package HumanManagementMVC.Model;

import HumanManagementMVC.EnumAndSubclass.*;

import java.util.Date;
import java.util.GregorianCalendar;

/**
 * <p>{@code HumanModel} is the model of the human</p>
 * <p>{@code HumanModel} contains all of attributes of the human staff</p>
 * <p>{@code HumanModel} is an abstract class</p>
 * <p>This class has an constructor with 6 parameters to set all attributes for the human staff</p>
 */
public abstract class HumanModel {

    /**
     * This attribute represents for the name of the human
     */
    protected String name;

    /**
     * This attribute represents for the address of the human
     */
    protected String address;

    /**
     * This attribute represents for the phone number of the human
     */
    protected String phoneNumber;

    /**
     * This attribute represents for the identity card of the human
     */
    protected String idCard;

    /**
     * This attribute represents for the birth of the human
     */
    protected Date yearOfBirth;

    /**
     * This attribute represents for the age of the human
     */
    protected int age;

    /**
     * This attribute represents for the sex of the human
     */
    protected Sex sex;

    /**
     * <p>This constructor uses to init an {@code HumanModel}
     * object with specific values</p>
     * <p>The <code>age</code> attribute = current year - year of birth</p>
     *
     * @param name        a {@code String} object represents for the name uses
     *                    to set name for the human
     * @param address     a{@code String} object represents for the address uses
     *                    to set name for the human
     * @param phoneNumber a {@code String} object represents for the phone number uses
     *                    to set phone number for the human
     * @param idCard      a {@code String} object represents for the identity card uses
     *                    to set identity card for the human
     * @param yearOfBirth a {@code Date} object represents for the birth uses
     *                    to set birth for the human
     * @param sex         a {@code Sex} enum represents for the sex uses
     *                    to set sex for the human
     */
    public HumanModel(String name,
                      String address,
                      String phoneNumber,
                      String idCard,
                      Date yearOfBirth,
                      Sex sex) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.idCard = idCard;
        this.yearOfBirth = yearOfBirth;
        this.sex = sex;
        String s = new GregorianCalendar().getTime().toString();
        this.setAge(Integer.parseInt(s.substring(s.lastIndexOf(" ") + 1)) -
                Integer.parseInt(this.yearOfBirth.toString().substring(0, 4)));
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the age
     */
    public int getAge() {
        return age;
    }

    /**
     * @param address the address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * @param phoneNumber the phone number
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * @return the identity card
     */
    public String getIdCard() {
        return idCard;
    }

    /**
     * @param yearOfBirth the year of birth
     */
    public void setYearOfBirth(Date yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    /**
     * @param sex the sex
     */
    public void setSex(Sex sex) {
        this.sex = sex;
    }

    /**
     * @param age the age
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * @return the uear of birth
     */
    public Date getYearOfBirth() {
        return yearOfBirth;
    }

    /**
     * @return the sex
     */
    public Sex getSex() {
        return sex;
    }

    /**
     * @param idCard the identity card
     */
    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }
}


