package HumanManagementMVC.EnumAndSubclass;

import HumanManagementMVC.Controller.*;
import HumanManagementMVC.Model.*;
import HumanManagementMVC.View.*;
import javax.swing.*;
import java.util.Date;
import java.util.Map;

/**
 * Part enum contains all kinds of part
 */
public enum Part {

    BARTENDER {
        /**
         * <p>This method uses to get model of the bartender staff</p>
         * <p>With <code>listParams</code> parameter use value with key <code>"bonus"</code>
         * to get bonus value and set the bonus value for bartender model</p>
         *
         * @param name a {@code String} object represents for the name of the staff
         * @param address a {@code String} object represents for the address of the staff
         * @param phoneNumber a {@code String} object represents for the phone number of the staff
         * @param idCard a {@code String} object represents for the identity card of the staff
         * @param birth  a {@code Date} object represents for the birth of the staff
         * @param sex  a {@code Sex} enum represents for the sex of the staff
         * @param startDate a {@code Date} object represents for the start working day of the staff
         * @param overTime a {@code int} object represents for the amount over time  of the staff
         * @param basicSalary  a {@code double} object represents for the amount basic salary of the staff
         * @param listParams a {@code Map<String, Object>} object represents for the individual
         *                   attributes of each staff
         * @return a {@code BartenderModel} object represents for the model of the bartender staff
         */
        @Override
        public HumanModel getObjectModel(String name,
                                         String address,
                                         String phoneNumber,
                                         String idCard,
                                         Date birth,
                                         Sex sex,
                                         Date startDate,
                                         int overTime,
                                         double basicSalary,
                                         Map<String, Object> listParams) {
            return new BartenderModel(
                    name, address, phoneNumber, idCard, birth, sex,
                    startDate, overTime, basicSalary,
                    Double.parseDouble(String.valueOf(listParams.get("bonus"))));
        }


        /**
         * <p>This method uses to get controller of the bartender staff</p>
         *
         * @return an {@code BartenderController} object represents for the controller of the bartender
         */
        @Override
        public Object getObjectController() {
            return new BartenderController();
        }

        /**
         * <p>This method uses to get view of the bartender staff</p>
         *
         * @param code a {@code String} object represents for the code of the staff
         * @param frame a {@code JFrame} object represents for the view frame of each staff view
         * @return an {@code BartenderView} object represents for the view of the bartender
         */
        @Override
        public Object getObjectView(String code, JFrame frame) {
            return new BartenderView();
        }
    },
    SECURITY {
        /**
         * <p>This method uses to get model of the security staff</p>
         * <p>With <code>listParams</code> parameter use value with key <code>"shift"</code>
         * to get shift value then check and set the shift value for security model</p>
         *
         * @param name a {@code String} object represents for the name of the staff
         * @param address a {@code String} object represents for the address of the staff
         * @param phoneNumber a {@code String} object represents for the phone number of the staff
         * @param idCard a {@code String} object represents for the identity card of the staff
         * @param birth  a {@code Date} object represents for the birth of the staff
         * @param sex  a {@code Sex} enum represents for the sex of the staff
         * @param startDate a {@code Date} object represents for the start working day of the staff
         * @param overTime a {@code int} object represents for the amount over time  of the staff
         * @param basicSalary  a {@code double} object represents for the amount basic salary of the staff
         * @param listParams a {@code Map<String, Object>} object represents for the individual
         *                   attributes of each staff
         * @return a {@code SecurityModel} object represents for the model of the security staff
         */
        @Override
        public Object getObjectModel(String name,
                                     String address,
                                     String phoneNumber,
                                     String idCard,
                                     Date birth,
                                     Sex sex,
                                     Date startDate,
                                     int overTime,
                                     double basicSalary,
                                     Map<String, Object> listParams) {
            return new SecurityModel(
                    name, address, phoneNumber, idCard, birth, sex,
                    startDate, overTime, basicSalary,
                    Shift.checkShift(String.valueOf(listParams.get("shift"))));
        }

        /**
         * <p>This method uses to get view of the security staff</p>
         *
         * @return a {@code SecurityController} object represents for the controller of the security
         */
        @Override
        public Object getObjectController() {
            return new SecurityController();
        }

        /**
         * <p>this method uses to get view of the security staff</p>
         *
         * @param code a {@code String} object represents for the code of the staff
         * @param frame a {@code JFrame} object represents for the view frame of each staff view
         * @return a {@code SecurityView} object represents for the view of the security
         */
        @Override
        public Object getObjectView(String code, JFrame frame) {
            return new SecurityView();
        }
    },
    SERVANT {
        /**
         * <p>This method uses to get model of the servant staff</p>
         * <p>With <code>listParams</code> parameter use value with key <code>"shift"</code>
         * to get shift value then check and set the shift value for servant model</p>
         * <p>With <code>listParams</code> parameter use value with key <code>"bonus"</code>
         * to get bonus value and set the bonus value for servant model</p>
         *
         * @param name a {@code String} object represents for the name of the staff
         * @param address a {@code String} object represents for the address of the staff
         * @param phoneNumber a {@code String} object represents for the phone number of the staff
         * @param idCard a {@code String} object represents for the identity card of the staff
         * @param birth  a {@code Date} object represents for the birth of the staff
         * @param sex  a {@code Sex} enum represents for the sex of the staff
         * @param startDate a {@code Date} object represents for the start working day of the staff
         * @param overTime a {@code int} object represents for the amount over time  of the staff
         * @param basicSalary  a {@code double} object represents for the amount basic salary of the staff
         * @param listParams a {@code Map<String, Object>} object represents for the individual
         *                   attributes of each staff
         * @return a {@code ServantModel} object represents for the model of the servant staff
         */
        @Override
        public Object getObjectModel(String name,
                                     String address,
                                     String phoneNumber,
                                     String idCard,
                                     Date birth,
                                     Sex sex,
                                     Date startDate,
                                     int overTime,
                                     double basicSalary,
                                     Map<String, Object> listParams) {
            return new ServantModel(
                    name, address, phoneNumber, idCard, birth, sex,
                    startDate, overTime, basicSalary,
                    Shift.checkShift(String.valueOf(listParams.get("shift"))),
                    Double.parseDouble(String.valueOf(listParams.get("bonus"))));
        }

        /**
         * <p>This method uses to get view of the servant staff</p>
         *
         * @return a {@code ServantController} object represents for the controller of the servant
         */
        @Override
        public Object getObjectController() {
            return new ServantController();
        }

        /**
         * <p>This method uses to get view of the servant staff</p>
         *
         * @param code a {@code String} object represents for the code of the staff
         * @param frame a {@code JFrame} object represents for the view frame of each staff view
         * @return a {@code ServantView} object represents for the view of the servant
         */
        @Override
        public Object getObjectView(String code, JFrame frame) {
            return new ServantView(code, frame);
        }
    },
    HOUSEKEEPER {
        /**
         * <p>This method uses to get model of the housekeeper staff</p>
         *
         * @param name a {@code String} object represents for the name of the staff
         * @param address a {@code String} object represents for the address of the staff
         * @param phoneNumber a {@code String} object represents for the phone number of the staff
         * @param idCard a {@code String} object represents for the identity card of the staff
         * @param birth  a {@code Date} object represents for the birth of the staff
         * @param sex  a {@code Sex} enum represents for the sex of the staff
         * @param startDate a {@code Date} object represents for the start working day of the staff
         * @param overTime a {@code int} object represents for the amount over time  of the staff
         * @param basicSalary  a {@code double} object represents for the amount basic salary of the staff
         * @param listParams a {@code Map<String, Object>} object represents for the individual
         *                   attributes of each staff
         * @return a {@code HousekeeperModel} object represents for the model of the housekeeper staff
         */
        @Override
        public Object getObjectModel(String name,
                                     String address,
                                     String phoneNumber,
                                     String idCard,
                                     Date birth,
                                     Sex sex,
                                     Date startDate,
                                     int overTime,
                                     double basicSalary,
                                     Map<String, Object> listParams) {
            return new HousekeeperModel(
                    name, address, phoneNumber, idCard, birth, sex,
                    startDate, overTime, basicSalary);
        }

        /**
         * <p>This method uses to get view of the housekeeper staff</p>
         *
         * @return a {@code HousekeeperController} object represents for the controller of the housekeeper
         */
        @Override
        public Object getObjectController() {
            return new HousekeeperController();
        }

        /**
         * <p>This method uses to get view of the housekeeper staff</p>
         *
         * @param code a {@code String} object represents for the code of the staff
         * @param frame a {@code JFrame} object represents for the view frame of each staff view
         * @return a {@code HousekeeperView} object represents for the view of the housekeeper
         */
        @Override
        public Object getObjectView(String code, JFrame frame) {
            return new HousekeeperView();
        }
    },
    CASHIER {
        /**
         * <p>This method uses to get model of the cashier staff</p>
         *
         * @param name a {@code String} object represents for the name of the staff
         * @param address a {@code String} object represents for the address of the staff
         * @param phoneNumber a {@code String} object represents for the phone number of the staff
         * @param idCard a {@code String} object represents for the identity card of the staff
         * @param birth  a {@code Date} object represents for the birth of the staff
         * @param sex  a {@code Sex} enum represents for the sex of the staff
         * @param startDate a {@code Date} object represents for the start working day of the staff
         * @param overTime a {@code int} object represents for the amount over time  of the staff
         * @param basicSalary  a {@code double} object represents for the amount basic salary of the staff
         * @param listParams a {@code Map<String, Object>} object represents for the individual
         *                   attributes of each staff
         * @return a {@code CashierModel} object represents for the model of the cashier staff
         */
        @Override
        public Object getObjectModel(String name,
                                     String address,
                                     String phoneNumber,
                                     String idCard,
                                     Date birth,
                                     Sex sex,
                                     Date startDate,
                                     int overTime,
                                     double basicSalary,
                                     Map<String, Object> listParams) {
            return new CashierModel(
                    name, address, phoneNumber, idCard, birth, sex,
                    startDate, overTime, basicSalary);
        }

        /**
         *  <p>This method uses to get view of the cashier staff</p>
         *
         * @return a {@code CashierController} object represents for the controller of the cashier
         */
        @Override
        public Object getObjectController() {
            return new CashierController();
        }

        /**
         *  <p>This method uses to get view of the cashier staff</p>
         *
         * @param code a {@code String} object represents for the code of the staff
         * @param frame a {@code JFrame} object represents for the view frame of each staff view
         * @return a {@code CashierView} object represents for the view of the cashier
         */
        @Override
        public Object getObjectView(String code, JFrame frame) {
            return new CashierView(frame);
        }
    },
    COOKER {
        /**
         * <p>This method uses to get model of the cooker staff</p>
         * <p>With <code>listParams</code> parameter use value with key <code>"shift"</code>
         * to get shift value then check and set the shift value for cooker model</p>
         * <p>With <code>listParams</code> parameter use value with key <code>"bonus"</code>
         * to get bonus value and set the bonus value for cooker model</p>
         *
         * @param name a {@code String} object represents for the name of the staff
         * @param address a {@code String} object represents for the address of the staff
         * @param phoneNumber a {@code String} object represents for the phone number of the staff
         * @param idCard a {@code String} object represents for the identity card of the staff
         * @param birth  a {@code Date} object represents for the birth of the staff
         * @param sex  a {@code Sex} enum represents for the sex of the staff
         * @param startDate a {@code Date} object represents for the start working day of the staff
         * @param overTime a {@code int} object represents for the amount over time  of the staff
         * @param basicSalary  a {@code double} object represents for the amount basic salary of the staff
         * @param listParams a {@code Map<String, Object>} object represents for the individual
         *                   attributes of each staff
         * @return a {@code CookerModel} object represents for the model of the cooker staff
         */
        @Override
        public Object getObjectModel(String name,
                                     String address,
                                     String phoneNumber,
                                     String idCard,
                                     Date birth,
                                     Sex sex,
                                     Date startDate,
                                     int overTime,
                                     double basicSalary,
                                     Map<String, Object> listParams) {
            return new CookerModel(
                    name, address, phoneNumber, idCard, birth, sex,
                    startDate, overTime, basicSalary,
                    Shift.checkShift(String.valueOf(listParams.get("shift"))),
                    Double.parseDouble(String.valueOf(listParams.get("bonus"))));
        }

        /**
         * <p>This method uses to get controller of the cooker staff</p>
         *
         * @return a {@code CookerController} object represents for the controller of the cooker
         */
        @Override
        public Object getObjectController() {
            return new CookerController();
        }

        /**
         * <p>This method uses to get view of the cooker staff</p>
         *
         * @param code a {@code String} object represents for the code of the staff
         * @param frame a {@code JFrame} object represents for the view frame of each staff view
         * @return a {@code CookerView} object represents for the view of the cooker
         */
        @Override
        public Object getObjectView(String code, JFrame frame) {
            return new CookerView();
        }
    },
    MANAGER {

        /**
         * <p>This method uses to get model of the manager staff</p>
         * <p>With <code>listParams</code> parameter use value with key <code>"rateProfit"</code>
         * to get rateProfit value then check and set the rateProfit value for manager model</p>
         * <p>With <code>listParams</code> parameter use value with key <code>"bonus"</code>
         * to get bonus value and set the bonus value for manager model</p>
         *
         *
         * @param name a {@code String} object represents for the name of the staff
         * @param address a {@code String} object represents for the address of the staff
         * @param phoneNumber a {@code String} object represents for the phone number of the staff
         * @param idCard a {@code String} object represents for the identity card of the staff
         * @param birth  a {@code Date} object represents for the birth of the staff
         * @param sex  a {@code Sex} enum represents for the sex of the staff
         * @param startDate a {@code Date} object represents for the start working day of the staff
         * @param overTime a {@code int} object represents for the amount over time  of the staff
         * @param basicSalary  a {@code double} object represents for the amount basic salary of the staff
         * @param listParams a {@code Map<String, Object>} object represents for the individual
         *                   attributes of each staff
         * @return a {@code ManagerModel} object represents for the model of the manager staff
         */
        @Override
        public Object getObjectModel(String name,
                                     String address,
                                     String phoneNumber,
                                     String idCard,
                                     Date birth,
                                     Sex sex,
                                     Date startDate,
                                     int overTime,
                                     double basicSalary,
                                     Map<String, Object> listParams) {
            return new ManagerModel(
                    name, address, phoneNumber, idCard, birth, sex,
                    startDate, overTime, basicSalary,
                    Double.parseDouble(String.valueOf(listParams.get("rateProfit"))),
                    Double.parseDouble(String.valueOf(listParams.get("bonus"))));
        }

        /**
         * <p>This method uses to get controller of the manager staff</p>
         *
         * @return a {@code ManagerController} object represents for the controller of the manager
         */
        @Override
        public Object getObjectController() {
            return new ManagerController();
        }

        /**
         * <p>This method uses to get view of the manager staff</p>
         *
         * @param code a {@code String} object represents for the code of the staff
         * @param frame a {@code JFrame} object represents for the view frame of each staff view
         * @return a {@code ManagerView} object represents for the controller of the manager
         */
        @Override
        public Object getObjectView(String code, JFrame frame) {
            return new ManagerView(code, frame);
        }
    };

    /**
     * <p>This is an abstract method to get corresponding staff model with each specific part</p>
     *
     * @param name a {@code String} object represents for the name of the staff
     * @param address a {@code String} object represents for the address of the staff
     * @param phoneNumber a {@code String} object represents for the phone number of the staff
     * @param idCard a {@code String} object represents for the identity card of the staff
     * @param birth  a {@code Date} object represents for the birth of the staff
     * @param sex  a {@code Sex} enum represents for the sex of the staff
     * @param startDate a {@code Date} object represents for the start working day of the staff
     * @param overTime a {@code int} object represents for the amount over time  of the staff
     * @param basicSalary  a {@code double} object represents for the amount basic salary of the staff
     * @param listParams a {@code Map<String, Object>} object represents for the individual
     *                   attributes of each staff
     * @return an {@code Object} object represents for a model of the corresponding staff
     */
    public abstract Object getObjectModel(String name,
                                          String address,
                                          String phoneNumber,
                                          String idCard,
                                          Date birth,
                                          Sex sex,
                                          Date startDate,
                                          int overTime,
                                          double basicSalary,
                                          Map<String, Object> listParams);

    /**
     * <p>This is an abstract method to get corresponding staff controller
     * with each specific part</p>
     * @return an {@code Object} object represents for a controller of the
     *      corresponding staff
     */
    public abstract Object getObjectController();

    /**
     * <p>This is an abstract method to get corresponding staff view with each
     * specific part</p>
     * @param code a {@code String} object represents for the code of the staff
     * @param frame a {@code JFrame} object represents for the view frame of each staff view
     * @return an {@code Object} object represents for a view of the corresponding staff
     */
    public abstract Object getObjectView(String code, JFrame frame);

    /**
     * <p>This static method uses to check the part of the staff </p>
     * <p>This method loops through each element in {@code Part} enum
     * and compares it with input part and return the corresponding {@code Part} value</p>
     * @param part a {@code String} object represents for the part of the staff wants to check
     * @return a {@code Part} enum represents for the part, if the input
     *       {@code String} isn't correct return null
     */
    public static Part checkPart(String part) {
        for (Part partValue : Part.values())
            if (partValue.toString().equals(part))
                return partValue;
        return null;
    }
}
