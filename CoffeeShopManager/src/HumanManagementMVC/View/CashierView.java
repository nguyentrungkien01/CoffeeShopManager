package HumanManagementMVC.View;

import CenterObjects.CenterPanelView;
import HumanManagementMVC.Controller.CashierController;
import HumanManagementMVC.Controller.HumanController;
import HumanManagementMVC.Controller.ServantController;
import HumanManagementMVC.EnumAndSubclass.Part;
import InfrastructureManagementMVC.Controller.InfrastructureController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicReference;

/**
 * <p>{@code CashierBtn} is an abstract class which extends {@code CenterPanelView} </p>
 * <p>{@code CashierBtn} uses to GUI for buttons in cashier's view</p>
 */
abstract class CashierBtn extends CenterPanelView {

    /**
     * <p>this attribute represents for a mission of the cashier staff</p>
     */
    protected JButton btn;

    /**
     * <p>With each button, has a common frame</p>
     */
    protected CalMoney calMoney;

    /**
     * create an {@code Infrastructure} object to use methods of this
     */
    protected final static InfrastructureController INFRASTRUCTURE_CONTROLLER = new InfrastructureController();

    /**
     * create an {@code HumanController} object to use methods of this
     */
    protected final static HumanController CASHIER_CONTROLLER = new CashierController();

    /**
     * This constructor uses to set GUI for a mission of the cashier staff
     *
     * @param text a {@code String} object represents for the content of the button
     *
     * @param mainFrame a {@code JFrame} object represents for the frame will display
     *                  after click the button
     */
    public CashierBtn(String text, JFrame mainFrame){
        this.btn=new JButton(text);
        this.btn.setFont(new Font(Font.SERIF, Font.BOLD, 50));
        this.btn.setBackground(Color.CYAN);
        this.btn.addActionListener(e->{
            mainFrame.setVisible(false);
            this.setGUI(mainFrame);
        });
    }

    /**
     *
     * @return the btn
     */
    public JButton getBtn() {
        return btn;
    }

    /**
     * @param mainFrame the mainFrame wants to set
     */
    public abstract void setGUI(JFrame mainFrame );
}

/**
 * <p>{@code CalBillBtn} class extends {@code CashierBtn}</p>
 * <p>{@code CalBullBtn} is a final class, which uses billing task of the cashier</p>
 */
final class CalBillBtn extends CashierBtn{

    /**
     * create a {@code CalBillBtn}
     *
     * @param text a {@code String} object represents for the content of the {@code CalBillBtn}
     *
     * @param mainFrame a {@code JFrame} object represents for the frame after click the {@code CalBillBtn}
     */
    CalBillBtn(String text, JFrame mainFrame){
        super(text, mainFrame);
    }

    /**
     * This method uses to set GUI for the bill frame
     *
     * @param mainFrame the mainFrame wants to set
     */
    @Override
    public void setGUI(JFrame mainFrame) {
        //create and set attributes for the bill frame
        JFrame subFrame = new JFrame("Bill Frame");
        subFrame.setSize(new Dimension(1100, 800));
        subFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                mainFrame.setVisible(true);
                subFrame.dispose();
            }
        });
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###.## VNĐ ");//use to format number
        //set GUI and data for frame
        this.calMoney = new CalMoney(subFrame);
        this.calMoney.setCodeVector(INFRASTRUCTURE_CONTROLLER.getAllCodeBookedTable());
        this.calMoney.getCodeVector().sort(String::compareTo);
        this.calMoney.getInfoPanel().setLayout(new GridLayout(this.calMoney.getCodeVector().size() + 1, 1, 1, 1));
        this.calMoney.getHeaderLabel().setText("BILL INFORMATION");
        this.calMoney.getCodeVector().forEach(code -> {
            try {
                //add data to the label represents for the information of the booked table
                JLabel infoLabel = new JLabel(code + "     ✿     " +
                        CASHIER_CONTROLLER.getClass().getMethod("getCodeStaffBookTable", String.class).
                                invoke(CASHIER_CONTROLLER, code) + "     ✿     " +
                        decimalFormat.format(Math.round((Double) CASHIER_CONTROLLER.getClass().
                                getMethod("calBill", String.class).
                                invoke(CASHIER_CONTROLLER, code))), JLabel.CENTER);
                //set attributes of the label
                infoLabel.setBackground(Color.BLACK);
                infoLabel.setForeground(Color.ORANGE);
                infoLabel.setFont(new Font(Font.SERIF, Font.BOLD, 30));
                infoLabel.setOpaque(true);
                this.calMoney.getInfoPanel().add(infoLabel);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ignored) {
            }
            //set data for the code of booked table combo box
            this.calMoney.getComboBox().addItem(code);
            //calculate the total all of bills
            this.calMoney.getTotalValue().updateAndGet(v -> {
                try {
                    return v + Math.round((Double) CASHIER_CONTROLLER.getClass().
                            getMethod("calBill", String.class).invoke(CASHIER_CONTROLLER, code));
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ignored) {
                }
                return v;
            });

        });
        //When choose each item in the combo box, a new frame will
        // be displayed and show information of the selected booked table
        this.calMoney.getComboBox().addActionListener(e -> {
            String data = CenterPanelView.getDataComboBoxAddComponent(this.calMoney.getComboBox());
            if (data != null && !data.isEmpty()) {
                JFrame ItemFrame = new JFrame(data);
                ItemFrame.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        ItemFrame.dispose();
                    }
                });
                try {
                    // set attributes for the label which uses to store the information of the booked table
                    JLabel infoLabel = new JLabel(data + "     ✿     " +
                            CASHIER_CONTROLLER.getClass().getMethod("getCodeStaffBookTable", String.class).
                                    invoke(CASHIER_CONTROLLER, data)+ "     ✿     " +
                            decimalFormat.format(CASHIER_CONTROLLER.getClass().getMethod("calBill", String.class).
                            invoke(CASHIER_CONTROLLER, data)), JLabel.CENTER);
                    //set attributes of the label
                    infoLabel.setBackground(Color.LIGHT_GRAY);
                    infoLabel.setForeground(Color.BLACK);
                    infoLabel.setFont(new Font(Font.SERIF, Font.BOLD, 30));
                    infoLabel.setOpaque(true);
                    ItemFrame.add(infoLabel);
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ignored) {
                }
                ItemFrame.setSize(1100, 100);
                ItemFrame.setVisible(true);
                ItemFrame.setLocationRelativeTo(null);
            }
        });
        //set attributes for the total money label
        this.calMoney.getTotalLabel().setText("TOTAL: " +
                new DecimalFormat("###,###,###.## VNĐ ").format(Double.parseDouble(this.calMoney.getTotalValue().toString())));
        subFrame.setVisible(true);
        subFrame.setLocationRelativeTo(null);
    }
}

/**
 * <p>{@code CalSalaryBtn} class extends {@code CashierBtn}</p>
 * <p>{@code CalSalaryBtn} is a final class, which uses to salary task of the cashier</p>
 */
final class CalSalaryBtn extends CashierBtn{

    /**
     * create a {@code CalSalaryBtn}
     *
     * @param text a {@code String} object represents for the content of the {@code CalSalaryBtn}
     *
     * @param mainFrame a {@code JFrame} object represents for the frame after click the {@code CalSalaryBtn}
     */
    CalSalaryBtn(String text, JFrame mainFrame){
        super(text, mainFrame);
    }

    /**
     * This method uses to set GUI for the salary frame
     *
     * @param mainFrame the mainFrame wants to set
     */
    @Override
    public void setGUI(JFrame mainFrame) {
        //frame setting
        JFrame subFrame = new JFrame("Salary Frame");
        subFrame.setSize(new Dimension(1100, 800));
        subFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                mainFrame.setVisible(true);
                subFrame.dispose();
            }
        });
        //set GUI and data for frame
        this.calMoney = new CalMoney(subFrame);
        this.calMoney.setCodeVector(new ServantController().getAllCodeStaff());
        this.calMoney.getInfoPanel().setLayout(new GridLayout(calMoney.getCodeVector().size() + 1, 1, 1, 1));
        this.calMoney.getHeaderLabel().setText("SALARY INFORMATION");
        this.calMoney.getCodeVector().sort(String::compareTo);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###.## VNĐ ");
        this.calMoney.getCodeVector().forEach(code -> {
            Object objController = null;
            for (Part partValue : Part.values())
                if (partValue.toString().equals(code.substring(0, code.indexOf("0")))) {
                    objController = partValue.getObjectController();
                    break;
                }
            assert objController != null;
            try {
                //add data to the label represents for the information of the booked table
                JLabel infoLabel = new JLabel(code + "     ✿     " + new ServantController().getName(code) + "     ✿     " +
                        decimalFormat.format(Math.round((Double) objController.getClass().
                                getMethod("calSalary", String.class).invoke(objController, code))), JLabel.CENTER);
                infoLabel.setBackground(Color.BLACK);
                infoLabel.setForeground(Color.ORANGE);
                infoLabel.setFont(new Font(Font.SERIF, Font.BOLD, 30));
                infoLabel.setOpaque(true);
                this.calMoney.getInfoPanel().add(infoLabel);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ignored) {
            }
            //set data for the code of staff combo box
            this.calMoney.getComboBox().addItem(code);
            Object finalObjController = objController;
            //calculate the total all of salary of all staffs
            this.calMoney.getTotalValue().updateAndGet(v -> {
                try {
                    return v + Math.round((Double) finalObjController.getClass().getMethod("calSalary", String.class).
                            invoke(finalObjController, code));
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ignored) {
                }
                return v;
            });
        });

        //When choose each item in the combo box, a new frame will
        // be displayed and show information of the selected salary of the staff
        this.calMoney.getComboBox().addActionListener(e -> {
            String data = CenterPanelView.getDataComboBoxAddComponent(this.calMoney.getComboBox());
            if (data != null && !data.isEmpty()) {
                JFrame ItemFrame = new JFrame(data);
                ItemFrame.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        ItemFrame.dispose();
                    }
                });
                //get controller of each kind of staff to invoke method of them
                Object objController = null;
                for (Part partValue : Part.values())
                    if (partValue.toString().equals(data.substring(0, data.indexOf("0")))) {
                        objController = partValue.getObjectController();
                        break;
                    }
                try {
                    assert objController != null;
                    // set attributes for the label uses to store the information of the booked table
                    JLabel infoLabel = new JLabel(data + "     ✿     " + new ServantController().getName(data) + "     ✿     " +
                            decimalFormat.format(Math.round((Double) objController.getClass().getMethod("calSalary", String.class).
                                    invoke(objController, data))) + " VNĐ ",
                            JLabel.CENTER);
                    //set attributes for the label
                    infoLabel.setBackground(Color.LIGHT_GRAY);
                    infoLabel.setForeground(Color.BLACK);
                    infoLabel.setFont(new Font(Font.SERIF, Font.BOLD, 30));
                    infoLabel.setOpaque(true);
                    ItemFrame.add(infoLabel);
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ignored) {
                }
                ItemFrame.setSize(1200, 100);
                ItemFrame.setVisible(true);
                ItemFrame.setLocationRelativeTo(null);
            }
        });
        //set attributes for the total money label
        this.calMoney.getTotalLabel().setText("TOTAL: " +
                new DecimalFormat("###,###,###.## VNĐ ").format(Double.parseDouble(this.calMoney.getTotalValue().toString())));
        subFrame.setVisible(true);
        subFrame.setLocationRelativeTo(null);
    }

}

/**
 * <p>{@code CalRevenueBtn} class extends {@code CashierBtn}</p>
 * <p>{@code CalRevenueBtn} is a final class, which uses to revenue statistic task of the cashier</p>
 */
final class CalRevenueBtn extends CashierBtn {

    /**
     * create a {@code CalRevenueBtn}
     *
     * @param text a {@code String} object represents for the content of the {@code CalRevenueBtn}
     *
     * @param mainFrame a {@code JFrame} object represents for the frame after click the {@code CalRevenueBtn}
     */
    CalRevenueBtn(String text, JFrame mainFrame) {
        super(text, mainFrame);
    }

    /**
     * This method uses to set GUI for the revenue frame
     * firstly, must choose the month want to statistic revenue,
     * after that the revenue in that month will be display]
     *
     * @param mainFrame the mainFrame wants to set
     */
    @Override
    public void setGUI(JFrame mainFrame) {
        // set frame uses to choose month wants to statistic revenue
        JFrame monthChoiceFrame = new JFrame("Month Choice");
        monthChoiceFrame.setSize(400, 200);
        monthChoiceFrame.setLayout(new GridLayout(2, 1));
        monthChoiceFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                mainFrame.setVisible(true);
                monthChoiceFrame.dispose();
            }
        });
        Vector<String> month = new Vector<>();
        //set data of 12 month for the combo box
        for (int i = 1; i <= 12; i++)
            month.add(String.valueOf(i));
        JComboBox<String> monthComboBox = new JComboBox<>(month);
        JButton confirmBtn = new JButton("Confirm");
        monthComboBox.setFont(new Font(Font.SERIF, Font.BOLD, 50));
        confirmBtn.setFont(new Font(Font.SERIF, Font.BOLD, 30));
        confirmBtn.setBackground(Color.GREEN);
        //check the click action of the button, if the button was clicked,
        // the revenue statistic frame will be displayed
        confirmBtn.addActionListener(e -> {
            monthChoiceFrame.setVisible(false);
            JFrame subFrame = new JFrame("Revenue Frame");
            subFrame.setSize(new Dimension(1100, 800));
            subFrame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    mainFrame.setVisible(true);
                    subFrame.dispose();
                }
            });
            //set GUI and data for frame
            this.calMoney = new CalMoney(subFrame);
            this.calMoney.getSearchPanel().removeAll();
            try {
                this.calMoney.setCodeVector((Vector<String>) CASHIER_CONTROLLER.getClass().
                        getMethod("statisticRevenue", Integer.class, Integer.class, Boolean.class)
                        .invoke(CASHIER_CONTROLLER,
                                Integer.valueOf(CenterPanelView.getDataComboBoxAddComponent(monthComboBox)),
                                Calendar.getInstance().get(Calendar.YEAR), Boolean.TRUE));
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ignored) {
            }
            this.calMoney.getInfoPanel().setLayout(new GridLayout(calMoney.getCodeVector().size() + 1, 1, 1, 1));
            //set header for the frame
            this.calMoney.getHeaderLabel().setText(
                    "REVENUE INFORMATION " +
                            Integer.valueOf(CenterPanelView.getDataComboBoxAddComponent(monthComboBox)) +
                            "-" + Calendar.getInstance().get(Calendar.YEAR));
            this.calMoney.getCodeVector().sort(String::compareTo);
            this.calMoney.getCodeVector().forEach(value -> {
                //add data to the label represents for the information of the booked table
                JLabel infoLabel = new JLabel(value, JLabel.CENTER);
                infoLabel.setBackground(Color.BLACK);
                infoLabel.setForeground(Color.ORANGE);
                infoLabel.setFont(new Font(Font.SERIF, Font.BOLD, 30));
                infoLabel.setOpaque(true);
                this.calMoney.getInfoPanel().add(infoLabel);
                this.calMoney.getComboBox().addItem(value.substring(0, 10));
                //calculate total money
                this.calMoney.getTotalValue().updateAndGet(
                        v -> v + Math.round(Double.parseDouble(value.substring(value.lastIndexOf(" ") + 1))));
            });
            //set attributes for the total label
            this.calMoney.getTotalLabel().setText("TOTAL: " +
                    String.format("%,.2f VNĐ", Double.parseDouble(this.calMoney.getTotalValue().toString())));
            subFrame.setVisible(true);
            subFrame.setLocationRelativeTo(null);
        });
        monthChoiceFrame.add(monthComboBox);
        monthChoiceFrame.add(confirmBtn);
        monthChoiceFrame.setVisible(true);
        monthChoiceFrame.setLocationRelativeTo(null);

    }

}

/**
 * <p>{@code CalMoney} is a final class</p>
 * <p>{@code CalMoney} uses to show calculating money</p>
 */
final class CalMoney {
    private JPanel searchPanel;
    private JPanel infoPanel;
    private JLabel searchLabel;
    private JComboBox<String> comboBox;
    private JLabel headerLabel;
    private Vector<String> codeTableVector;
    private AtomicReference<Double> totalValue;
    private JScrollPane scrollPane;
    private JLabel totalLabel;

    CalMoney() {

    }

    CalMoney(JFrame mainFrame) {
        this();
        this.setSearchPanel(new JPanel());
        this.setInfoPanel(new JPanel());
        this.setSearchLabel(new JLabel("Code"));
        this.setComboBox(new JComboBox<>());
        this.setHeaderLabel(new JLabel("", JLabel.CENTER));
        this.setCodeTableVector(null);
        this.setTotalValue(new AtomicReference<>((double) 0));
        this.setScrollPane(new JScrollPane(getInfoPanel()));
        this.setTotalLabel(new JLabel("", JLabel.RIGHT));
        this.setGUI(mainFrame);
    }

    /**
     * set GUI for the frame after click the button
     * @param mainFrame  a {@code JFrame} object represents for the the frame after click the button
     */
    private void setGUI(JFrame mainFrame) {

        this.getSearchLabel().setForeground(Color.WHITE);
        this.getSearchPanel().setBackground(Color.BLACK);
        this.getComboBox().addItem("");
        this.getSearchPanel().add(getSearchLabel());
        this.getSearchPanel().add(getComboBox());

        //header
        this.getHeaderLabel().setBackground(Color.BLACK);
        this.getHeaderLabel().setForeground(Color.CYAN);
        this.getHeaderLabel().setFont(new Font(Font.SERIF, Font.BOLD, 50));
        this.getHeaderLabel().setOpaque(true);

        //information bill of all of booked table

        this.getInfoPanel().setBackground(Color.BLACK);
        this.getInfoPanel().add(getHeaderLabel());
        //total label

        this.getTotalLabel().setBackground(Color.GREEN);
        this.getTotalLabel().setFont(new Font(Font.SERIF, Font.BOLD, 30));
        this.getTotalLabel().setOpaque(true);

        //set scroll pane
        this.getScrollPane().setBackground(Color.CYAN);
        this.getScrollPane().setPreferredSize(new Dimension(900, 700));
        this.getScrollPane().setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.getScrollPane().setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        //add component to subFrame
        mainFrame.add(getSearchPanel(), BorderLayout.NORTH);
        mainFrame.add(getScrollPane(), BorderLayout.CENTER);
        mainFrame.add(getTotalLabel(), BorderLayout.SOUTH);

    }

    /**
     *
     * @param codeTableVector the codeVector
     */
    public void setCodeVector(Vector<String> codeTableVector) {
        this.setCodeTableVector(codeTableVector);
    }

    /**
     *
     * @return the codeVector
     */
    public Vector<String> getCodeVector() {
        return this.getCodeTableVector();
    }

    /**
     *
     * @return the comboBox
     */
    public JComboBox<String> getComboBox() {
        return this.comboBox;
    }

    /**
     *
     * @return the toalValue
     */
    public AtomicReference<Double> getTotalValue() {
        return totalValue;
    }

    /**
     *
     * @return the totalLabel
     */
    public JLabel getTotalLabel() {
        return totalLabel;
    }

    /**
     *
     * @return the infoPanel
     */
    public JPanel getInfoPanel() {
        return this.infoPanel;
    }

    /**
     *
     * @return the headerLabel
     */
    public JLabel getHeaderLabel() {
        return headerLabel;
    }

    /**
     *
     * @return the searchPanel
     */
    public JPanel getSearchPanel() {
        return searchPanel;
    }

    /**
     *
     * @param searchPanel the searchPanel
     */
    public void setSearchPanel(JPanel searchPanel) {
        this.searchPanel = searchPanel;
    }

    /**
     *
     * @param infoPanel the infoPanel
     */
    public void setInfoPanel(JPanel infoPanel) {
        this.infoPanel = infoPanel;
    }

    /**
     *
     * @return the searchLabel
      */
    public JLabel getSearchLabel() {
        return searchLabel;
    }

    /**
     *
     * @param searchLabel the searchLabel
     */
    public void setSearchLabel(JLabel searchLabel) {
        this.searchLabel = searchLabel;
    }

    /**
     *
     * @param comboBox the comboBox
     */
    public void setComboBox(JComboBox<String> comboBox) {
        this.comboBox = comboBox;
    }

    /**
     *
     * @param headerLabel the headerLabel
     */
    public void setHeaderLabel(JLabel headerLabel) {
        this.headerLabel = headerLabel;
    }

    /**
     *
     * @return the codeTableVector
     */
    public Vector<String> getCodeTableVector() {
        return codeTableVector;
    }

    /**
     *
     * @param codeTableVector the codeTableVector
     */
    public void setCodeTableVector(Vector<String> codeTableVector) {
        this.codeTableVector = codeTableVector;
    }

    /**
     *
     * @param totalValue the totalValue
     */
    public void setTotalValue(AtomicReference<Double> totalValue) {
        this.totalValue = totalValue;
    }

    /**
     *
     * @return the scrollPane
     */
    public JScrollPane getScrollPane() {
        return scrollPane;
    }

    /**
     *
     * @param scrollPane the scrollPane
     */
    public void setScrollPane(JScrollPane scrollPane) {
        this.scrollPane = scrollPane;
    }

    /**
     *
     * @param totalLabel the totalLabel
     */
    public void setTotalLabel(JLabel totalLabel) {
        this.totalLabel = totalLabel;
    }
}

/**
 * {@code CashierView} class extends the {@code HumanView}
 * {@code CashierView} class uses to set view frame for the cashier staff
 */
public class CashierView extends HumanView {
    /**
     * the logo image
     */
    private final static String IMAGE_URL = "src\\images\\coffee.gif";

    /**
     * the button panel uses to contains all mission button of the cashier
     */
    private JPanel btnPanel;

    /**
     * the logo label uses to set logo for the cashier
     */
    private JLabel logoLabel;

    /**
     * the bill task button
     */
    private CashierBtn calBillBtn;

    /**
     * the salary task button
     */
    private CashierBtn calSalaryBtn;

    /**
     * the revenue statistic button
     */
    private CashierBtn calRevenueBtn;

    /**
     * default constructor
     */
    CashierView() {

    }

    /**
     * init all attributes of the {@code CashierView}
     *
     * @param mainFrame a {@code JFrame} object represents for the frame of the cashier staff
     */
    public CashierView(JFrame mainFrame) {
        this();
        this.btnPanel = new JPanel(new FlowLayout());
        this.logoLabel = new JLabel(new ImageIcon(IMAGE_URL));
        this.calSalaryBtn = new CalSalaryBtn("Calculate Salary", mainFrame);
        this.calBillBtn = new CalBillBtn("Calculate Bill", mainFrame);
        this.calRevenueBtn = new CalRevenueBtn("Statistic Profit", mainFrame);
        this.setGUI();
    }

    /**
     *   set attributes for the buttons and add buttons to the frame
     */
    private void setGUI() {
        this.btnPanel.setBackground(Color.BLACK);
        this.centerPanel.setBackground(Color.BLACK);
        this.centerPanel.setLayout(new GridLayout(2, 1));
        this.btnPanel.add(calBillBtn.getBtn());
        this.btnPanel.add(calSalaryBtn.getBtn());
        this.btnPanel.add(calRevenueBtn.getBtn());
        this.centerPanel.add(logoLabel);
        this.centerPanel.add(btnPanel);
    }


}