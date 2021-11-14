package HumanManagementMVC.View;

import CenterObjects.CenterPanelView;
import DatePicker.DatePicker;
import HumanManagementMVC.Controller.ManagerController;
import HumanManagementMVC.EnumAndSubclass.*;
import InfrastructureManagementMVC.View.InfrastructureView;
import InfrastructureManagementMVC.View.TableManagerView;
import Interface.ManagerTable;
import Interface.ManagerTaskBar;
import CenterObjects.MenuBar;
import LogInMVC.LogInController;
import ProductManagementMVC.View.ProductManagerView;
import ProductManagementMVC.View.ProductView;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import static HumanManagementMVC.EnumAndSubclass.Sex.*;



/**
 * <p>{@code ManagerView} uses to set GUI for manager staff</p>
 * <p>This class extends the {@code HumanView} class and implements
 * {@code ManagerTaskBar} , {@code ManagerTable} interface</p>
 */
public class ManagerView extends HumanView
        implements ManagerTaskBar, ManagerTable {

    /**
     * this attribute uses methods of the {@code ManagerController}
     */
    private final static ManagerController MANAGER_CONTROLLER = new ManagerController();

    //set layout for the center panel
    {
        this.centerPanel.setLayout(new BoxLayout(this.centerPanel, BoxLayout.Y_AXIS));
    }

    /**
     * This constructor uses to set components for the view frame of the manager
     *
     * @param code {@code String} object represents for the code of the manager
     *
     * @param mainFrame {@code JFrame} object represents for the frame of the manager
     */
    public ManagerView(String code, JFrame mainFrame) {
        this.setTaskbarMenu(code, mainFrame);
        this.centerPanel.add(this.panel);
        this.setDataTable(code, "id", true);
    }

    /**
     * <p>This method uses to set GUI for  the taskbar and  menu components of the manager view</p>
     *
     * @param code {@code String} object represents for the code of the manager
     *
     * @param mainFrame {@code JFrame} object represents for the view frame of the manager
     */
    @Override
    public void setTaskbarMenu(String code, JFrame mainFrame) {
        MenuBar menuBar = new MenuBar();
        this.setMainMenuGUI(code, mainFrame, menuBar.getMainMenu());
        this.setSortMenuGUI(code, mainFrame, menuBar.getSortUpMenu(), true);
        this.setSortMenuGUI(code, mainFrame, menuBar.getSortDownMenu(), false);
        this.setSearchMenuGUI(code, mainFrame, menuBar.getSearchMenu());
        this.setAddMenuGUI(code, mainFrame, menuBar.getAddMenu());
        this.setRemoveMenuGUI(code, mainFrame, menuBar.getRemoveMenu());
        //if the menu in the taskbar has position is even set the background and font the menu
        for (int i = 0; i < 5; i++)
            if (i % 2 == 0) {
                menuBar.getMenuBar().getMenu(i).setBackground(Color.BLACK);
                menuBar.getMenuBar().getMenu(i).setForeground(Color.WHITE);
            }
        this.centerPanel.add(menuBar.getMenuBar());
    }

    /**
     * <p>This method switch between product management, employee management, and facility management</p>
     *
     * @param code {@code String} object represents for the code of the manager
     *
     * @param mainFrame {@code JFrame} object represents for the view frame of the manager
     *
     * @param menu a {@code JMenu} object represents for the main menu in the taskbar
     */
    @Override
    public void setMainMenuGUI(String code, JFrame mainFrame, JMenu menu) {
        JMenuItem productMenuItem = new JMenuItem("Product information");
        JMenuItem infrastructureMenuItem = new JMenuItem("Infrastructure information");

        productMenuItem.addActionListener(e -> {
            ProductView productView = new ProductManagerView(code, mainFrame);
            this.centerPanel.removeAll();
            this.centerPanel.add(productView.getPanel());
            mainFrame.setVisible(true);
        });
        infrastructureMenuItem.addActionListener(e -> {
            InfrastructureView infrastructureView = new TableManagerView(code, mainFrame);
            this.centerPanel.removeAll();
            this.centerPanel.add(infrastructureView.getPanel());
            mainFrame.setVisible(true);
        });

        menu.add(productMenuItem);
        menu.add(infrastructureMenuItem);
    }

    /**
     *<p>This method uses to set GUI for the sort menu</p>
     *
     * @param code {@code String} object represents for the code of the staff
     *
     * @param mainFrame {@code JFrame} object represents for the view frame of the staff manager
     *
     * @param menu {@code JMenu} object represents for the sort menu in taskbar
     *
     * @param kindSort {@code boolean} value represents for the kind of sort
     *                  <ul>
     *                                <li><code>true</code>: sort up</li>
     *                                <li><code>false</code>: sort down</li>
     *                  </ul>
     */
    @Override
    public void setSortMenuGUI(String code, JFrame mainFrame, JMenu menu, boolean kindSort) {

        //create menu items of the menu bar
        JMenuItem sortCodeItem = new JMenuItem("Code");
        JMenuItem sortNameItem = new JMenuItem("Name");
        JMenuItem sortBirthItem = new JMenuItem("Birth");
        JMenuItem sortAgeItem = new JMenuItem("Age");
        JMenuItem sortAddressItem = new JMenuItem("Address");
        JMenuItem sortPhoneNumberItem = new JMenuItem("Phone Number");
        JMenuItem sortIdCardItem = new JMenuItem("Identity Card");
        JMenuItem sortStartDateItem = new JMenuItem("Starting time");
        JMenuItem sortBasicSalaryItem = new JMenuItem("Basic Salary");
        JMenuItem sortBonusItem = new JMenuItem("Bonus");
        JMenuItem sortHaftPermissItem = new JMenuItem("Haft Day Permission");
        JMenuItem sortHaftNonPermissItem = new JMenuItem("Haft Day Non-Permission");
        JMenuItem sortAllPermissItem = new JMenuItem("All Day Permission");
        JMenuItem sortAllNonPermissItem = new JMenuItem("All Day Non-Permission");
        JMenuItem sortOvertimeItem = new JMenuItem("Over Time");
        JMenuItem sortRateProfitItem = new JMenuItem("Rate Profit");

        sortCodeItem.addActionListener(e -> {
            this.centerPanel.remove(2);
            this.setDataTable(code, "code", kindSort);
            mainFrame.setVisible(true);
        });
        sortNameItem.addActionListener(e -> {
            this.centerPanel.remove(2);
            this.setDataTable(code, "name", kindSort);
            mainFrame.setVisible(true);
        });
        sortBirthItem.addActionListener(e -> {
            this.centerPanel.remove(2);
            this.setDataTable(code, "yearOfBirth", kindSort);
            mainFrame.setVisible(true);
        });
        sortAgeItem.addActionListener(e -> {
            this.centerPanel.remove(2);
            this.setDataTable(code, "age", kindSort);
            mainFrame.setVisible(true);
        });
        sortAddressItem.addActionListener(e -> {
            this.centerPanel.remove(2);
            this.setDataTable(code, "address", kindSort);
            mainFrame.setVisible(true);
        });
        sortPhoneNumberItem.addActionListener(e -> {
            this.centerPanel.remove(2);
            this.setDataTable(code, "phoneNumber", kindSort);
            mainFrame.setVisible(true);
        });
        sortIdCardItem.addActionListener(e -> {
            this.centerPanel.remove(2);
            this.setDataTable(code, "idCard", kindSort);
            mainFrame.setVisible(true);
        });
        sortStartDateItem.addActionListener(e -> {
            this.centerPanel.remove(2);
            this.setDataTable(code, "startDate", kindSort);
            mainFrame.setVisible(true);
        });
        sortBasicSalaryItem.addActionListener(e -> {
            this.centerPanel.remove(2);
            this.setDataTable(code, "basicSalary", kindSort);
            mainFrame.setVisible(true);
        });
        sortBonusItem.addActionListener(e -> {
            this.centerPanel.remove(2);
            this.setDataTable(code, "bonus", kindSort);
            mainFrame.setVisible(true);
        });
        sortHaftPermissItem.addActionListener(e -> {
            this.centerPanel.remove(2);
            this.setDataTable(code, "haftPermiss", kindSort);
            mainFrame.setVisible(true);
        });
        sortHaftNonPermissItem.addActionListener(e -> {
            this.centerPanel.remove(2);
            this.setDataTable(code, "haftNonPermiss", kindSort);
            mainFrame.setVisible(true);
        });
        sortAllPermissItem.addActionListener(e -> {
            this.centerPanel.remove(2);
            this.setDataTable(code, "allPermiss", kindSort);
            mainFrame.setVisible(true);
        });
        sortAllNonPermissItem.addActionListener(e -> {
            this.centerPanel.remove(2);
            this.setDataTable(code, "allNonPermiss", kindSort);
            mainFrame.setVisible(true);
        });
        sortOvertimeItem.addActionListener(e -> {
            this.centerPanel.remove(2);
            this.setDataTable(code, "overtime", kindSort);
            mainFrame.setVisible(true);
        });
        sortRateProfitItem.addActionListener(e -> {
            this.centerPanel.remove(2);
            this.setDataTable(code, "rateProfit", kindSort);
            mainFrame.setVisible(true);
        });

        menu.add(sortCodeItem);
        menu.add(sortNameItem);
        menu.add(sortBirthItem);
        menu.add(sortAgeItem);
        menu.add(sortAddressItem);
        menu.add(sortPhoneNumberItem);
        menu.add(sortIdCardItem);
        menu.add(sortStartDateItem);
        menu.add(sortBasicSalaryItem);
        menu.add(sortBonusItem);
        menu.add(sortHaftPermissItem);
        menu.add(sortHaftNonPermissItem);
        menu.add(sortAllPermissItem);
        menu.add(sortAllNonPermissItem);
        menu.add(sortOvertimeItem);
        menu.add(sortRateProfitItem);
    }

    /**
     *<p>This method uses to set GUI for the search menu</p>
     *
     * @param code {@code String} object represents for the code of the manager
     *
     * @param mainFrame {@code JFrame} object represents for the view frame of the manager
     *
     * @param menu {@code JMenu} object represents for the sort menu in taskbar
     */
    @Override
    public void setSearchMenuGUI(String code, JFrame mainFrame, JMenu menu) {
        //create menu items of the search menu
        JMenuItem searchByStringItem = new JMenuItem("Search by keyword");
        JMenuItem searchByNumberItem = new JMenuItem("Search by range of value");
        JMenuItem searchByDateItem = new JMenuItem("Search by birth");
        //after click the search by keyword menu item
        searchByStringItem.addActionListener(e -> {
            JFrame subFrame = new JFrame("Search box");
            subFrame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    subFrame.dispose();
                }
            });
            subFrame.setLayout(new GridLayout(3, 1));
            subFrame.setSize(400, 400);
            JLabel label = new JLabel("Enter Your Keyword ", JLabel.CENTER);
            JTextField textField = new JTextField();
            textField.setFont(new Font(Font.SERIF, Font.BOLD, 25));
            JButton btn = new JButton("Confirm");
            CenterPanelView.setSubFrameSearchGUI(label, btn);
            //reset data of the table
            btn.addActionListener(e1 -> {
                this.centerPanel.remove(2);
                this.setTableGUI(new JTable(MANAGER_CONTROLLER.searchKey(textField.getText()), this.getNameColTable()));
                mainFrame.setVisible(true);
                subFrame.dispose();
            });
            subFrame.add(label);
            subFrame.add(textField);
            subFrame.add(btn);
            subFrame.setVisible(true);
            subFrame.setLocationRelativeTo(null);
        });
        //after click the search by range menu item
        searchByNumberItem.addActionListener(e -> {
            JFrame subFrame = new JFrame("Search box");
            subFrame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    subFrame.dispose();
                }
            });
            subFrame.setLayout(new GridLayout(3, 1));
            subFrame.setSize(400, 400);
            JLabel label = new JLabel("Enter Your Keyword ", JLabel.CENTER);
            JPanel panel = new JPanel(new GridLayout(1, 2));
            JTextField textFieldLeft = new JTextField();
            JTextField textFieldRight = new JTextField();
            textFieldLeft.setFont(new Font(Font.SERIF, Font.BOLD, 25));
            textFieldRight.setFont(new Font(Font.SERIF, Font.BOLD, 25));
            textFieldLeft.setBackground(new Color(238, 238, 224));
            textFieldRight.setBackground(new Color(207, 207, 207));
            panel.add(textFieldLeft);
            panel.add(textFieldRight);
            panel.setLayout(new GridLayout(1, 2));
            JButton btn = new JButton("Confirm");
            CenterPanelView.setSubFrameSearchGUI(label, btn);
            //reset data of the table
            btn.addActionListener(e1 -> {

                this.centerPanel.remove(2);
                this.setTableGUI(new JTable(MANAGER_CONTROLLER.searchKey(
                        Double.parseDouble(textFieldLeft.getText()),
                        Double.parseDouble(textFieldRight.getText())),
                        this.getNameColTable()));
                mainFrame.setVisible(true);
                subFrame.dispose();
            });
            subFrame.add(label);
            subFrame.add(panel);
            subFrame.add(btn);
            subFrame.setVisible(true);
            subFrame.setLocationRelativeTo(null);
        });
        //after click the search by birth menu item
        searchByDateItem.addActionListener(e -> {
            JFrame subFrame = new JFrame("Search box");
            subFrame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    subFrame.dispose();
                }
            });
            subFrame.setLayout(new GridLayout(3, 1));
            subFrame.setSize(400, 400);
            JLabel label = new JLabel("Your Keyword ", JLabel.CENTER);
            final String[] MONTH = new String[]{
                    "JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE",
                    "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"};
            JComboBox<String> monthComboBox = new JComboBox<>(MONTH);
            monthComboBox.setSize(new Dimension(400, 100));
            monthComboBox.setFont(new Font(Font.SERIF, Font.BOLD, 20));
            JButton btn = new JButton("Confirm");
            CenterPanelView.setSubFrameSearchGUI(label, btn);
            //reset data of the table
            btn.addActionListener(e1 -> {
                this.centerPanel.remove(2);
                int i = 1;
                for (String month : MONTH)
                    if (CenterPanelView.getDataComboBoxAddComponent(monthComboBox).equals(month))
                        break;
                    else
                        i++;
                this.setTableGUI(new JTable(MANAGER_CONTROLLER.searchKey(i), this.getNameColTable()));
                mainFrame.setVisible(true);
                subFrame.dispose();
            });
            subFrame.add(label);
            subFrame.add(monthComboBox);
            subFrame.add(btn);
            subFrame.setVisible(true);
            subFrame.setLocationRelativeTo(null);
        });

        menu.add(searchByStringItem);
        menu.add(searchByNumberItem);
        menu.add(searchByDateItem);
    }

    /**
     * <p>Update data in cells of the data table</p>
     *
     * @param table a {@code JTable} object represents for the data table
     */
    @Override
    public void updateTable(JTable table) {
        table.getModel().addTableModelListener(e -> {
            if (table.getSelectedColumn() != -1) {
                String code = (String) table.getValueAt(table.getSelectedRow(), 1);
                String valueEdit = (String) table.getCellEditor(e.getFirstRow(), e.getColumn()).getCellEditorValue();
                switch (table.getColumnName(table.getSelectedColumn())) {
                    case "Name":
                        MANAGER_CONTROLLER.updateName(code, valueEdit);
                        break;
                    case "Date Of Birth":
                        try {
                            MANAGER_CONTROLLER.updateYearOfBirth(code,
                                    new SimpleDateFormat("yyyy-MM-dd").parse(valueEdit));
                        } catch (ParseException parseException) {
                            parseException.printStackTrace();
                        }
                        break;
                    case "Age":
                        MANAGER_CONTROLLER.updateAge(code, Integer.parseInt(valueEdit));
                        break;
                    case "Address":
                        MANAGER_CONTROLLER.updateAddress(code, valueEdit);
                        break;
                    case "Phone Number":
                        MANAGER_CONTROLLER.updatePhoneNumber(code, valueEdit);
                        break;
                    case "Identity Card":
                        MANAGER_CONTROLLER.updateIdCard(code, valueEdit);
                        break;
                    case "Start Date":
                        try {
                            MANAGER_CONTROLLER.updateStartDate(code,
                                    new SimpleDateFormat("yyyy-MM-dd").parse(valueEdit));
                        } catch (ParseException parseException) {
                            parseException.printStackTrace();
                        }
                        break;
                    case "Sex":
                        MANAGER_CONTROLLER.updateSex(code, Objects.requireNonNull(checkSex(valueEdit)));
                        break;
                    case "Shift":
                        MANAGER_CONTROLLER.updateShift(code, (!valueEdit.isEmpty()) ? Shift.checkShift(valueEdit) : null);
                        break;
                    case "Basic Salary":
                        MANAGER_CONTROLLER.updateBasicSalary(code, Double.parseDouble(valueEdit));
                        break;
                    case "Bonus":
                        MANAGER_CONTROLLER.updateBonus(code, Double.parseDouble(valueEdit));
                        break;
                    case "Haft Day Permission":
                        MANAGER_CONTROLLER.updateHaftPermiss(code, Integer.parseInt(valueEdit));
                        break;
                    case "Haft Day Non-Permission":
                        MANAGER_CONTROLLER.updateHaftNonPermiss(code, Integer.parseInt(valueEdit));
                        break;
                    case "All Day Permission":
                        MANAGER_CONTROLLER.updateAllPermiss(code, Integer.parseInt(valueEdit));
                        break;
                    case "All Day Non-Permission":
                        MANAGER_CONTROLLER.updateAllNonPermiss(code, Integer.parseInt(valueEdit));
                        break;
                    case "Over Time":
                        MANAGER_CONTROLLER.updateOvertime(code, Integer.parseInt(valueEdit));
                        break;
                    case "Rate Profit":
                        MANAGER_CONTROLLER.updateRateProfit(code, Double.parseDouble(valueEdit));
                        break;
                }
            }
        });
    }

    /**
     * <p>This method uses to set GUI for the add menu</p>
     *
     * @param code a {@code String} object represents for the code of the manager
     *
     * @param mainFrame a {@code JFrame} object represents for the view frame
     *
     * @param menu a {@code JMenu} object represents for the add menu
     */
    @Override
    public void setAddMenuGUI(String code, JFrame mainFrame, JMenu menu) {
        JMenuItem menuItem = new JMenuItem("Add new staff");
        menuItem.addActionListener(e -> {
            mainFrame.setVisible(false);
            JFrame subFrame = new JFrame("Adding Frame");
            JPanel panel = new JPanel();
            JLabel nameLabel = new JLabel("Name", JLabel.LEFT);
            JLabel dateOfBirthLabel = new JLabel("Date of birth", JLabel.LEFT);
            JLabel addressLabel = new JLabel("Address", JLabel.LEFT);
            JLabel idCardLabel = new JLabel("Identity Card", JLabel.LEFT);
            JLabel startDateLabel = new JLabel("Start Date", JLabel.LEFT);
            JLabel sexLabel = new JLabel("Sex", JLabel.LEFT);
            JLabel shiftLabel = new JLabel("Shift", JLabel.LEFT);
            JLabel partLabel = new JLabel("Part", JLabel.LEFT);
            JLabel basicSalaryLabel = new JLabel("Basic Salary", JLabel.LEFT);
            JLabel bonusLabel = new JLabel("Bonus", JLabel.LEFT);
            JLabel overTimeLabel = new JLabel("Over Time", JLabel.LEFT);
            JLabel rateProfitLabel = new JLabel("Rate Profit", JLabel.LEFT);
            JLabel passwordLabel = new JLabel("Password", JLabel.LEFT);
            JLabel phoneNumberLabel = new JLabel("Phone Number", JLabel.LEFT);
            JLabel avatarLabel = new JLabel("Avatar", JLabel.LEFT);
            JFileChooser fileChooser = new JFileChooser();
            JButton fileChooserBtn = new JButton("Choose File");
            JButton confirmBtn = new JButton("Confirm");
            JTextField nameTextField = new JTextField("Nguyễn Văn A", 30);
            DatePicker dateOfBirthPicker = new DatePicker();
            JTextField addressTextField = new JTextField("Lái Thiêu, Thuận An, Bình Dương", 30);
            JTextField idCardTextField = new JTextField("001201018887", 30);
            DatePicker startDatePicker = new DatePicker();
            Vector<JRadioButton> listSexRadioBtn = new Vector<>();
            ButtonGroup sexButtonGroup = new ButtonGroup();
            Vector<JRadioButton> listShiftRadioBtn = new Vector<>();
            ButtonGroup shiftButtonGroup = new ButtonGroup();
            Vector<String> listPartString = new Vector<>();
            JComboBox<String> partComboBox = new JComboBox<>(listPartString);
            for (Sex value : Sex.values())
                listSexRadioBtn.add(new JRadioButton(value.toString()));
            for (Shift value : Shift.values())
                listShiftRadioBtn.add(new JRadioButton(value.toString()));
            for (Part value : Part.values())
                listPartString.add(value.toString());
            JTextField basicSalaryTextField = new JTextField("5000000", 30);
            JTextField bonusJJTextField = new JTextField("500000", 30);
            SpinnerModel overTimeSpinnerModel = new SpinnerNumberModel(0, 0, 100, 1);
            JSpinner overTimeSpinner = new JSpinner(overTimeSpinnerModel);
            SpinnerModel rateProfitSpinnerModel = new SpinnerNumberModel(0, 0, 1, 0.1);
            JSpinner rateProfitSpinner = new JSpinner(rateProfitSpinnerModel);
            JTextField passwordTextField = new JTextField("123", 30);
            JTextField phoneNumberTextField = new JTextField("0123456789", 30);
            JPanel namePanel = new JPanel(new GridLayout(2, 1));
            JPanel dateOfBirthPanel = new JPanel(new FlowLayout());
            JPanel addressPanel = new JPanel(new GridLayout(2, 1));
            JPanel idCardPanel = new JPanel(new GridLayout(2, 1));
            JPanel startDatePanel = new JPanel(new FlowLayout());
            JPanel shiftPanel = new JPanel(new FlowLayout());
            JPanel sexPanel = new JPanel(new FlowLayout());
            JPanel basicSalaryPanel = new JPanel(new GridLayout(2, 1));
            JPanel partPanel = new JPanel(new GridLayout(2, 1));
            JPanel bonusPanel = new JPanel(new GridLayout(2, 1));
            JPanel overTimePanel = new JPanel(new GridLayout(2, 1));
            JPanel rateProfitPanel = new JPanel(new GridLayout(2, 1));
            JPanel passwordPanel = new JPanel(new GridLayout(2, 1));
            JPanel phoneNumberPanel = new JPanel(new GridLayout(2, 1));
            JPanel fileChooserBtnPanel = new JPanel(new GridLayout(2, 1));
            JPanel subFileChooserBtnPanel = new JPanel(new FlowLayout());
            JPanel confirmBtnPanel = new JPanel();
            subFrame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    subFrame.dispose();
                    mainFrame.setVisible(true);
                }
            });
            subFrame.setSize(1500, 850);
            panel.setLayout(new GridLayout(8, 2, 20, 20));
            panel.setBackground(Color.BLACK);
            panel.setVisible(true);
            confirmBtnPanel.setBackground(Color.BLACK);
            confirmBtnPanel.setVisible(true);

            CenterPanelView.setGUILabelOfAddComponent(nameLabel);
            CenterPanelView.setGUILabelOfAddComponent(addressLabel);
            CenterPanelView.setGUILabelOfAddComponent(dateOfBirthLabel);
            CenterPanelView.setGUILabelOfAddComponent(idCardLabel);
            CenterPanelView.setGUILabelOfAddComponent(startDateLabel);
            CenterPanelView.setGUILabelOfAddComponent(sexLabel);
            CenterPanelView.setGUILabelOfAddComponent(shiftLabel);
            CenterPanelView.setGUILabelOfAddComponent(partLabel);
            CenterPanelView.setGUILabelOfAddComponent(basicSalaryLabel);
            CenterPanelView.setGUILabelOfAddComponent(bonusLabel);
            CenterPanelView.setGUILabelOfAddComponent(overTimeLabel);
            CenterPanelView.setGUILabelOfAddComponent(rateProfitLabel);
            CenterPanelView.setGUILabelOfAddComponent(passwordLabel);
            CenterPanelView.setGUILabelOfAddComponent(phoneNumberLabel);
            CenterPanelView.setGUILabelOfAddComponent(avatarLabel);
            CenterPanelView.setGUITextFieldAddComponent(nameTextField);
            CenterPanelView.setGUITextFieldAddComponent(addressTextField);
            CenterPanelView.setGUITextFieldAddComponent(idCardTextField);
            CenterPanelView.setGUITextFieldAddComponent(basicSalaryTextField);
            CenterPanelView.setGUITextFieldAddComponent(bonusJJTextField);
            CenterPanelView.setGUITextFieldAddComponent(phoneNumberTextField);
            CenterPanelView.setGUIComboBoxAddComponent(partComboBox);
            listSexRadioBtn.forEach(CenterPanelView::setGUIRadioButtonAddComponent);
            listShiftRadioBtn.forEach(CenterPanelView::setGUIRadioButtonAddComponent);
            CenterPanelView.setGUISpinnerAddComponent(overTimeSpinner);
            CenterPanelView.setGUISpinnerAddComponent(rateProfitSpinner);

            namePanel.add(nameLabel);
            namePanel.add(nameTextField);
            dateOfBirthPanel.add(dateOfBirthLabel);
            dateOfBirthPanel.add(dateOfBirthPicker);
            addressPanel.add(addressLabel);
            addressPanel.add(addressTextField);
            idCardPanel.add(idCardLabel);
            idCardPanel.add(idCardTextField);
            startDatePanel.add(startDateLabel);
            startDatePanel.add(startDatePicker);
            listSexRadioBtn.forEach(sexButtonGroup::add);
            sexPanel.add(sexLabel);
            listSexRadioBtn.forEach(sexPanel::add);
            listShiftRadioBtn.forEach(shiftButtonGroup::add);
            shiftPanel.add(shiftLabel);
            listShiftRadioBtn.forEach(shiftPanel::add);
            partPanel.add(partLabel);
            partPanel.add(partComboBox);
            basicSalaryPanel.add(basicSalaryLabel);
            basicSalaryPanel.add(basicSalaryTextField);
            bonusPanel.add(bonusLabel);
            bonusPanel.add(bonusJJTextField);
            overTimePanel.add(overTimeLabel);
            overTimePanel.add(overTimeSpinner);
            rateProfitPanel.add(rateProfitLabel);
            rateProfitPanel.add(rateProfitSpinner);
            passwordPanel.add(passwordLabel);
            passwordPanel.add(passwordTextField);
            phoneNumberPanel.add(phoneNumberLabel);
            phoneNumberPanel.add(phoneNumberTextField);
            fileChooserBtnPanel.add(avatarLabel);
            fileChooserBtnPanel.add(subFileChooserBtnPanel);
            subFileChooserBtnPanel.add(fileChooserBtn);

            namePanel.setBackground(Color.BLACK);
            dateOfBirthPanel.setBackground(Color.BLACK);
            addressPanel.setBackground(Color.BLACK);
            idCardPanel.setBackground(Color.BLACK);
            startDatePanel.setBackground(Color.BLACK);
            sexPanel.setBackground(Color.BLACK);
            shiftPanel.setBackground(Color.BLACK);
            partPanel.setBackground(Color.BLACK);
            basicSalaryPanel.setBackground(Color.BLACK);
            bonusPanel.setBackground(Color.BLACK);
            overTimePanel.setBackground(Color.BLACK);
            rateProfitPanel.setBackground(Color.BLACK);
            passwordPanel.setBackground(Color.BLACK);
            phoneNumberPanel.setBackground(Color.BLACK);
            fileChooserBtnPanel.setBackground(Color.BLACK);
            subFileChooserBtnPanel.setBackground(Color.BLACK);

            panel.add(namePanel);
            panel.add(addressPanel);
            panel.add(idCardPanel);
            panel.add(partPanel);
            panel.add(dateOfBirthPanel);
            panel.add(startDatePanel);
            panel.add(sexPanel);
            panel.add(shiftPanel);
            panel.add(basicSalaryPanel);
            panel.add(bonusPanel);
            panel.add(overTimePanel);
            panel.add(rateProfitPanel);
            panel.add(passwordPanel);
            panel.add(phoneNumberPanel);
            panel.add(fileChooserBtnPanel);
            shiftPanel.setVisible(false);
            rateProfitPanel.setVisible(false);

            listSexRadioBtn.get(0).setSelected(true);
            listShiftRadioBtn.get(0).setSelected(true);
            partComboBox.setSelectedIndex(0);
            //Display the input box corresponding to each type of staff
            partComboBox.addItemListener(e12 -> {
                String valuePartComboBox = CenterPanelView.getDataComboBoxAddComponent(partComboBox);
                bonusPanel.setVisible(!valuePartComboBox.equals("SECURITY") && !valuePartComboBox.equals("HOUSEKEEPER") &&
                        !valuePartComboBox.equals("CASHIER"));
                rateProfitPanel.setVisible(valuePartComboBox.equals("MANAGER"));
                shiftPanel.setVisible(valuePartComboBox.equals("SERVANT") || valuePartComboBox.equals("SECURITY") ||
                        valuePartComboBox.equals("COOKER"));
                subFrame.setVisible(true);
            });
            //set avatar when change avatar
            final String[] url = new String[1];
            url[0] = "src\\\\images\\\\default.jpg";
            fileChooserBtn.addActionListener(e2 -> {
                if (fileChooser.showOpenDialog(panel) == JFileChooser.APPROVE_OPTION)
                    url[0] = fileChooser.getSelectedFile().getPath();
            });
            confirmBtn.setBackground(Color.GREEN);
            confirmBtn.setFont(new Font(Font.SERIF, Font.BOLD, 30));
            //action after confirm => create a new staff with input data
            confirmBtn.addActionListener(e1 -> {
                //get input data
                String part = CenterPanelView.getDataComboBoxAddComponent(partComboBox);
                String name = CenterPanelView.getDataTextFieldAddComponent(nameTextField);
                String address = CenterPanelView.getDataTextFieldAddComponent(addressTextField);
                String phoneNumber = CenterPanelView.getDataTextFieldAddComponent(phoneNumberTextField);
                String idCard = CenterPanelView.getDataTextFieldAddComponent(idCardTextField);
                Date birth = dateOfBirthPicker.getValue();
                Date startDate = startDatePicker.getValue();
                final Sex[] sex = new Sex[1];
                listSexRadioBtn.stream().filter(
                        AbstractButton::isSelected).collect(Collectors.toList()).forEach(
                        value -> sex[0] = Sex.checkSex(value.getText()));
                int overTime = Integer.parseInt(CenterPanelView.getDataSpinnerAddComponent(overTimeSpinner));
                double basicSalary = Double.parseDouble(CenterPanelView.getDataTextFieldAddComponent(basicSalaryTextField));
                final Shift[] shift = new Shift[1];
                listShiftRadioBtn.stream().filter(
                        AbstractButton::isSelected).collect(Collectors.toList()).forEach(
                        value -> shift[0] = Shift.checkShift(value.getText()));
                double bonus = Double.parseDouble(CenterPanelView.getDataTextFieldAddComponent(bonusJJTextField));
                double rateProfit = Double.parseDouble(CenterPanelView.getDataSpinnerAddComponent(rateProfitSpinner));
                Map<String, Object> params = new HashMap<>();
                params.put("shift", shift[0]);
                params.put("bonus", bonus);
                params.put("rateProfit", rateProfit);
                //create corresponding object model with part
                Object humanModel = null;
                for (Part value : Part.values())
                    if (part.equals(value.toString())) {
                        humanModel = value.getObjectModel(name, address, phoneNumber, idCard, birth, sex[0], startDate,
                                overTime, basicSalary, params);
                        break;
                    }
                try {
                    assert humanModel != null;
                    //create new account
                    //code staff
                    //username = lower case of code staff
                    //pass word = input password
                    new LogInController().createNewAccount(
                            humanModel.getClass().getMethod("getCode").invoke(humanModel).toString(),
                            humanModel.getClass().getMethod("getCode").invoke(humanModel).toString().toLowerCase(),
                            CenterPanelView.getDataTextFieldAddComponent(passwordTextField),
                            url[0]);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                //add staff to database
                MANAGER_CONTROLLER.add(humanModel);
                subFrame.dispose();
                centerPanel.remove(2);
                this.setDataTable(code, "", false);
                mainFrame.setVisible(true);

            });
            confirmBtnPanel.add(confirmBtn);
            subFrame.add(panel, BorderLayout.CENTER);
            subFrame.add(confirmBtnPanel, BorderLayout.SOUTH);
            subFrame.setVisible(true);
            subFrame.setLocationRelativeTo(null);
        });
        menu.add(menuItem);
    }

    /**
     *<p>This method uses to set GUI for the remove menu</p>
     *
     * @param code a {@code String} object represents for the code of the manager
     *
     * @param mainFrame a {@code JFrame} object represents for the view frame
     *
     * @param menu a {@code JMenu} object represents for the remove menu
     */
    @Override
    public void setRemoveMenuGUI(String code, JFrame mainFrame, JMenu menu) {
        JMenuItem removeByCodeItem = new JMenuItem("Remove By Code");
        removeByCodeItem.addActionListener(e -> {
            JFrame subFrame = new JFrame("Remove Frame");
            JLabel titleLabel = new JLabel("Enter code", JLabel.CENTER);
            JTextField contentTextField = new JTextField();
            JButton confirmBtn = new JButton("Confirm");
            subFrame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    subFrame.dispose();
                }
            });
            subFrame.setLayout(new GridLayout(3, 1));
            subFrame.setSize(400, 400);
            titleLabel.setFont(new Font(Font.SERIF, Font.BOLD, 30));
            titleLabel.setBackground(Color.BLACK);
            titleLabel.setForeground(Color.WHITE);
            titleLabel.setOpaque(true);
            contentTextField.setFont(new Font(Font.SERIF, Font.PLAIN, 25));
            confirmBtn.setFont(new Font(Font.SERIF, Font.BOLD, 30));
            confirmBtn.setBackground(Color.GREEN);
            //remove staff and reset data table
            confirmBtn.addActionListener(e1 -> {
                if (contentTextField.getText() != null) {
                    MANAGER_CONTROLLER.remove(contentTextField.getText());
                    subFrame.dispose();
                    centerPanel.remove(2);
                    this.setDataTable(code, "", false);
                    mainFrame.setVisible(true);

                }
            });
            subFrame.add(titleLabel);
            subFrame.add(contentTextField);
            subFrame.add(confirmBtn);
            subFrame.setVisible(true);
            subFrame.setLocationRelativeTo(null);
        });
        menu.add(removeByCodeItem);
    }

    /**
     * <p>this method uses to set GUI for the table</p>
     *
     * @param table a {@code JTable} object represents for the data table
     */
    @Override
    public void setTableGUI(JTable table) {
        JScrollPane sp = new JScrollPane(table);
        table.setFont(new Font(Font.SERIF, Font.PLAIN, 14));
        table.setGridColor(Color.BLACK);
        table.setMinimumSize(new Dimension(1500, 500));
        table.setRowHeight(50);
        //set text in data table is center
        DefaultTableCellRenderer defaultTableCellRenderer = new DefaultTableCellRenderer();
        defaultTableCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        //set width each column
        for (int i = 0; i < 20; i++) {
            table.getColumnModel().getColumn(i).setMinWidth(180);
            table.getColumnModel().getColumn(i).setCellRenderer(defaultTableCellRenderer);
            if (i == 0)
                table.getColumnModel().getColumn(i).setMinWidth(50);
            if (i >= 14 && i < 18 || i == 6 || i == 2)
                table.getColumnModel().getColumn(i).setMinWidth(300);
            if (i == 18)
                table.getColumnModel().getColumn(i).setMinWidth(200);
            if (i == 5)
                table.getColumnModel().getColumn(i).setMinWidth(300);
        }
         //set cell spacing
        table.setIntercellSpacing(
                new Dimension(table.getRowHeight() / 2, table.getRowHeight() / 5));

        table.getTableHeader().setFont(new Font(Font.SERIF, Font.BOLD, 25));
        table.getTableHeader().setBackground(Color.GREEN);
        table.getTableHeader().setBorder(new LineBorder(Color.WHITE, 5));
        table.getTableHeader().setOpaque(true);

        table.setShowGrid(true);
        table.setShowHorizontalLines(true);
        table.setSelectionBackground(new Color(255, 48, 48));
        table.setSelectionForeground(Color.BLACK);
        table.setBackground(Color.BLACK);
        table.setForeground(Color.WHITE);
        table.setGridColor(Color.WHITE);

        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        this.centerPanel.add(sp);
    }

    /**
     * <p>This method uses to set data for the table</p>
     *
     * @param code a {@code String} object represents for the code of the manager
     *
     * @param col a {@code String} object represents for the name of column in database
     *            if the <code>col</code>> is empty => no sort
     *
     * @param kindSort a {@code boolean} object represents for the kind of sort
     *                 <ul>
     *                      <li><code>true</code>: sort ascending</li>
     *                      <li><code>false</code>: sort descending</li>
     *                 </ul>
     */
    @Override
    public void setDataTable(String code, String col, boolean kindSort) {
        Vector<Vector<String>> listData;
        listData = col.isEmpty() ? MANAGER_CONTROLLER.getAll("") :
                (kindSort ? MANAGER_CONTROLLER.getAll("ORDER BY " + col + " " + " ASC") :
                        MANAGER_CONTROLLER.getAll("ORDER BY " + col + " " + " DESC"));
        JTable table = new JTable(listData, this.getNameColTable());
        this.updateTable(table);
        this.setTableGUI(table);
    }

    /**
     *<p>This method uses to get list of column name of the data table</p>
     *
     * @return a {@code Vector<String>} object represents for the list of name column of the data table
     */
    @Override
    public Vector<String> getNameColTable() {
        Vector<String> listColName = new Vector<>();
        listColName.add("Id");
        listColName.add("Code");
        listColName.add("Name");
        listColName.add("Date Of Birth");
        listColName.add("Age");
        listColName.add("Address");
        listColName.add("Phone Number");
        listColName.add("Identity Card");
        listColName.add("Start Date");
        listColName.add("Sex");
        listColName.add("Shift");
        listColName.add("Part");
        listColName.add("Basic Salary");
        listColName.add("Bonus");
        listColName.add("Haft Day Permission");
        listColName.add("Haft Day Non-Permission");
        listColName.add("All Day Permission");
        listColName.add("All Day Non-Permission");
        listColName.add("Over Time");
        listColName.add("Rate Profit");
        return listColName;
    }

}
