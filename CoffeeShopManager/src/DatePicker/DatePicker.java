package DatePicker;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.SqlDateModel;

import javax.swing.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Properties;


/**
 * <p>The {@code DateFormatter} extends the {@code AbstractFormatter}</p>
 * <p>The access modifier of this class is declared default,
 * because it just has been used by the {@code DatePicker} class</p>
 * <p>The {@code DateFormatter} converted type of date value between
 * {@code String} and {@code Object}</p>
 */
class DateFormatter extends JFormattedTextField.AbstractFormatter {

    /**
     * <p>This attribute specifies formatter which the date object will be converted</p>
     * <p>This attribute is declared final</p>
     */
    private final static String PATTERN = "yyyy-MM-dd";

    /**
     * @param text a {@code String} object
     *
     * @return <code>null</code> value
     */
    @Override
    public Object stringToValue(String text) {
        return null;
    }

    /**
     * @param value The {@code Date} object which wants to convert to {@code String}
     *
     * @return a {@code String} object represents for the value of the {@code Date} object
     */
    @Override
    public String valueToString(Object value) {

        /*
         * Check the value object is null or not.
         * if the value is not null, convert the value to String with the
         * formatter specifies PATTERN
         * if null, return the null value
         */
        if (value != null) {
            Calendar calendar = (Calendar) value; //create the Calender object

            /*
             * Use the SimpleDateFormat object with the PATTERN description to format the calender
             */
            return new SimpleDateFormat(PATTERN).format(calendar.getTime());
        }
        return null;
    }
}

/**
 * <p>The {@code DatePicker} class extends the {@code JPanel} class
 * to use panel to store date table</p>
 * <p>The {@code DatePicker} class has a constructor without parameter</p>
 * <p>The {@code DatePicker} object shows the date value table
 * this table contains day value, month value, year values. </p>
 * <p>chose one value of each value(day, month, year) of the date table</p>
 */
public class DatePicker extends JPanel {

    /**
     * <p>This attribute specifies how the model of the {@code DatePicker} object</p>
     * <p>This attribute is declared final</p>
     */
    private final SqlDateModel MODEL = new SqlDateModel();

    /**
     * <p>The only constructor of the {@code DatePicker} class with any parameter</p>
     * <p>This constructor specifies how the {@code DatePicker} object is displayed</p>
     * <p>This constructor set all attributes for the {@code DatePicker} object</p>
     */
    public DatePicker() {

        /*
         * Declare all of objects need to create an DatePicker object
         */
        Calendar calendar = new GregorianCalendar(); //get current time
        String time = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());//format the current time
        Properties properties = new Properties(); //properties of the DatePicker
        JDatePanelImpl jDatePanel = new JDatePanelImpl(this.MODEL, properties); //Create panel for DatePicker object
        JDatePickerImpl jDatePicker = new JDatePickerImpl(jDatePanel, new DateFormatter());

        /*
         * Set attributes for the model model
         */
        this.MODEL.setDate(Integer.parseInt(time.substring(0, 4)),
                Integer.parseInt(time.substring(5, 7)) - 1,
                Integer.parseInt(time.substring(8)));// set initial value for DatePicker from the
        this.MODEL.setSelected(true);//set default state for the model is selected

        /*
         * Set attributes for the properties
         */
        properties.put("text.day", "Day");// set dau properties
        properties.put("text.month", "Month");// set month properties
        properties.put("text.year", "Year");// set year properties

        /*
         * Add the JDatePicker object to the panel
         * Set attributes for the panel which contains JDatePicker object
         */
        this.add(jDatePicker, JPanel.CENTER_ALIGNMENT);
        this.setSize(350, 100);
        this.setVisible(true);
    }

    /**
     * @return the selected date value was chosen in the JDatePicker
     */
    public Date getValue() {
        return this.MODEL.getValue();
    }

}
