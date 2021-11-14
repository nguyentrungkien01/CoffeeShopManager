package CenterObjects;

import ProductManagementMVC.Enum.SaleTime;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


/**
 * <p>
 *     {@code CenterPanelView} is an abstract class.
 *     {@code CenterPanelView} specifies how some objects are displayed
 *     {@code CenterPanelView} has only JPanel attribute
 *     {@code CenterPanelView} has a default constructor and a only
 *     getter method to get attribute
 *     This class contains methods which set GUI for objects such as
 * </p>
 *     <pre>
            - {@code JPanel}
            - {@code JTextField}
            - {@code JRadioButton}
            - {@code JSpinner}
 *     </pre>

 * <p>
 *    In addition, this class contains all above objects
 * </p>
 * <p>
 *     <strong>Notices:</strong> all methods in this class is
 *     <code>static</code> (except <code>getPanel</code> method),
 *     so we can use them without create an object
 * </p>
 */
public abstract class CenterPanelView {
    /*
     *  all of sub-classes which extend CenterPanelView must use
     *  this object to contain all their components
     */
    protected JPanel centerPanel = new JPanel();

    /**
     * @return the centerPanel
     */
    public JPanel getPanel() {
        return this.centerPanel;
    }

    /**
     * This method specifies how the {@code JLabel} object is displayed
     * This method will change background, foreground, font,
     * bound, opaque of the JLabel Object
     *
     * @param label The {@code JLabel} object will be set GUI
     */
    public static void setGUILabelOfAddComponent(JLabel label) {
        label.setBackground(Color.BLACK);
        label.setForeground(new Color(202, 255, 112));
        label.setFont(new Font(Font.SERIF, Font.BOLD, 25));
        label.setBounds(10, 10, 20, 20);
        label.setOpaque(true);
    }

    /**
     * <p>This method specifies how the {@code JTextField} object
     * is displayed </p>
     * <p>This method will change background, foreground, font,
     * border of the {@code JTextField} object</p>
     *
     * @param textField The {@code JTextField} object will be set GUI
     */
    public static void setGUITextFieldAddComponent(JTextField textField) {

        /*
         * this statement adds the specified focus listener to receive focus events from
         * this textField when this textField gains input focus.
         */
        textField.addFocusListener(new FocusAdapter() {

            /**
             * When this <code>textField</code> gains input focus,this <code>textField</code>
             * will be changed background, font, foreground, border
             *
             * @param e the focus event
             */
            @Override
            public void focusGained(FocusEvent e) {
                textField.setBackground(new Color(139, 131, 120));
                textField.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
                textField.setForeground(Color.WHITE);
                textField.setBorder(new LineBorder(Color.RED));

            }

            /**
             * When this textField loses input focus, this textField will be
             * changed background, font, foreground, border
             *
             * @param e the focus event
             */
            @Override
            public void focusLost(FocusEvent e) {
                textField.setBackground(Color.WHITE);
                textField.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
                textField.setForeground(Color.BLACK);
                textField.setBorder(new LineBorder(Color.WHITE));
            }
        });
    }

    /**
     * <p>This method specifies how the {@code JComboBox} object is displayed </p>
     * <p>This method will change background, scroll of the {@code JComboBox} Object</p>
     * <p>The value of {@code JComboBox} object must be a String object </p>
     *
     * @param comboBox the {@code JComboBox} object which all values must be
     *                 {@code String} object
     */
    public static void setGUIComboBoxAddComponent(JComboBox<String> comboBox) {

        /*
         * add item listener to comboBox when the item of this object
         * is selected the comboBox will change background
         */
        comboBox.setAutoscrolls(true);
        comboBox.addItemListener(e -> comboBox.setBackground(Color.LIGHT_GRAY));
    }

    /**
     * <p>This method specifies how the {@code JRadioButton} object is displayed </p>
     * <p>This method will change background of this object</p>
     *
     * @param radioBtn the {@code JRadioButton} object
     */
    public static void setGUIRadioButtonAddComponent(JRadioButton radioBtn) {

        /*
         * add the item listener to the radioBtn.
         * check the state of the radioBtn by use getStateChange method ,
         * if the radioBtn was selected, the return value of the getStateChange
         * method will equal to the 1 == ItemEvent.SELECTED. After that the radioBtn
         * will be changed background
         */
        radioBtn.addItemListener(e -> radioBtn.setBackground(e.getStateChange() == ItemEvent.SELECTED ?
                new Color(65, 105, 225) : Color.WHITE));
    }

    /**
     * <p>This method specifies how the {@code JSpinner} Object is displayed </p>
     * <p>This method will change background, font, foreground, border of this object</p>
     *
      * @param spinner the {@code JSpinner} object
     */
    public static void setGUISpinnerAddComponent(JSpinner spinner) {

        /*
         * this statement adds the specified focus listener to receive focus events from
         * this spinner when this spinner gains input focus.
         */
        spinner.addFocusListener(new FocusAdapter() {

            /**
             * When this <code>spinner</code> gains input focus, this <code>spinner</code>
             * will be changed background, font, foreground, border
             *
             * @param e the focus event
             */
            @Override
            public void focusGained(FocusEvent e) {
                spinner.setBackground(new Color(139, 131, 120));
                spinner.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
                spinner.setForeground(Color.WHITE);
                spinner.setBorder(new LineBorder(Color.RED));

            }

            /**
             * When this <code>spinner</code> loses input focus, this <code>spinner</code>
             * will be changed background, font, foreground, border
             *
             * @param e the focus event
             */
            @Override
            public void focusLost(FocusEvent e) {
                spinner.setBackground(Color.WHITE);
                spinner.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
                spinner.setForeground(Color.BLACK);
                spinner.setBorder(new LineBorder(Color.WHITE));
            }
        });
    }

    /**
     * <p>This method specifies how the searchBox is displayed</p>
     * <p>Change font, foreground background opaque of the label</p>
     * <p>Change font, background of the confirm button</p>
     *
     * @param label A {@code JLabel} object represents for a label of the search box.
     *
     * @param btn A {@code JButton} object represents for a confirm button of the search box
     */
    public static void setSubFrameSearchGUI(JLabel label, JButton btn) {
        label.setFont(new Font(Font.SERIF, Font.BOLD, 40));
        label.setForeground(Color.WHITE);
        label.setBackground(Color.BLACK);
        label.setOpaque(true);
        btn.setFont(new Font(Font.SERIF, Font.BOLD, 35));
        btn.setBackground(Color.GREEN);
    }

    /**
     * <p>This method will get all of input data of the {@code JTextField} object </p>
     *
     * @param textField the {@code JTextFile} object wants to get data
     *
     * @return a {@code String} object represents input data in the <code>textField</code>
     */
    public static String getDataTextFieldAddComponent(JTextField textField) {
        return textField.getText();
    }

    /**
     * <p>This method will get data of the selected item in the
     * {@code JComboBox} object</p>
     *
     * @param comboBox a {@code JComboBox} object which its items are {@code String}
     *                object represents for the {@code JComboBox} object want to get data
     *
     * @return a {@code String} object represents for input data of the selected item
     *              in the <code>comboBox</code>
     */
    public static String getDataComboBoxAddComponent(JComboBox<String> comboBox) {

        /*
         * get the selected item in the comboBox by getSelectedIndex() method,
         * if the return value doesn't equal to -1, return the item with index of the selected item,
         * otherwise, return an empty String object
         */
        return (comboBox.getSelectedIndex() != -1) ? comboBox.getItemAt(comboBox.getSelectedIndex()) : "";
    }

    /**
     * <p>This method will get data of the selected {@code JRadioButton} object</p>
     *
     * @param radioBtn a {@code JRadioButton} object which wants to get data
     *
     * @return a {@code String} object represents fpr the value of the selected radio button
     */
    public static String getDataRadioButtonAddComponent(JRadioButton radioBtn) {
        /*
         * check the state of the radioBtn by isSelected method,
         * if the radioBtn is selected return the value of the radioBtn by use getText
         * otherwise, return an empty String
         */
        return radioBtn.isSelected() ? radioBtn.getText() : "";
    }

    /**
     * <p>This method will get data of the {@code JSpinner} object</p>
     *
     * @param spinner a {@code JSpinner} object which wants to get data
     *
     * @return  a {@code String} object represents for the value of the <code>spinner</code>
     */
    public static String getDataSpinnerAddComponent(JSpinner spinner) {

        /*
         * create an String array with only element to referent and can change its data
         * then the element of the String array is initialized by default value in the spinner
         * after that, the spinner is added change listener to check if the spinner will be changed,
         * the element of the String array will be assigned to value has just changed in the spinner
         */

        String[] strings = new String[1];// initial String array with only one element

        // assign the only element of the String array with the default value of the spinner
        strings[0] = (spinner.getValue().toString());

        //reassign the value of the String element in the String array when spinner was changed
        spinner.addChangeListener(e -> strings[0] = (String) ((JSpinner) e.getSource()).getValue());

        return strings[0];
    }

    /**
     * <p>This method will get data of the selected {@code JCheckBox} object</p>
     *
     * @param checkBox a {@code JCheckBox} array which wants to get data
     *
     * @return Set<SaleTime> a {@code HashSet<SaleTime>} represents for all kind of
     *                      sale times were selected
     */
    public static Set<SaleTime> getDataCheckBoxAddComponent(JCheckBox[] checkBox) {

        /*
         * create a set of SaleTime with full value of SaleTime enum
         * Use asList of Arrays class to convert all values in the
         * SaleTime enum to HashSet.
         * In this case, must use HashSet because the SaleTime mustn't be repeated
         */
        Set<SaleTime> saleTimeSet = new HashSet<>(Arrays.asList(SaleTime.values()));

        /*
         * uses for each loop to get each element in checkBox array then check its state
         * if the element isn't selected, compare it with each value of the SaleTime enum
         * then remove all unselected SaleTime in saleTimeSel
         */
        for (JCheckBox box : checkBox) // loop each element in checkBox array
            if (!box.isSelected())  //check unselected element
                for (SaleTime saleTime : SaleTime.values())//loop each value in SaleTime enum
                    //compare value of unselected element with the each SaleTime value
                    if (saleTime.toString().equals(box.getText())) {
                        saleTimeSet.remove(saleTime);// remove unselected element
                        break;
                    }
        return saleTimeSet;
    }
}