package ProductManagementMVC.View;

import CenterObjects.CenterPanelView;
import DatePicker.DatePicker;
import HumanManagementMVC.View.HumanView;
import HumanManagementMVC.View.ManagerView;
import InfrastructureManagementMVC.View.InfrastructureView;
import InfrastructureManagementMVC.View.TableManagerView;
import Interface.ManagerTable;
import Interface.ManagerTaskBar;
import CenterObjects.MenuBar;
import ProductManagementMVC.Controller.ProductController;
import ProductManagementMVC.Enum.KindDrink;
import ProductManagementMVC.Enum.KindFood;
import ProductManagementMVC.Enum.SaleTime;
import ProductManagementMVC.Model.DrinkModel;
import ProductManagementMVC.Model.FoodModel;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

/**
 * <p>{@code ProductManagerView} use to set GUI for product manager </p>
 * <p>This class extends the {@code ProductView} class and implements
 * {@code ProductManagerView} , {@code ManagerTable} interface</p>
 */
public class ProductManagerView extends ProductView implements ManagerTaskBar, ManagerTable {

    /**
     * this attribute use methods of the {@code ProductController}
     */
    private final static ProductController PRODUCT_CONTROLLER = new ProductController();

    //set layout for the table manager view
    {
        super.centerPanel.setLayout(new BoxLayout(super.centerPanel, BoxLayout.Y_AXIS));
    }

    /**
     * <p>This method uses to create a view frame for the product manager</p>
     *
     * @param code a {@code String} object represents for the code of the manager
     *
     * @param mainFrame a {@code JFrame} object represents for the view frame
     */
    public ProductManagerView(String code, JFrame mainFrame) {
        this.setTaskbarMenu(code, mainFrame);
        super.centerPanel.add(this.panel);
        this.setDataTable(code, "id", true);
    }

    /**
     * <p>This method uses to set GUI for the taskbar and  menu components of the product manager view</p>
     *
     * @param code {@code String} object represents for the code of the manager
     *
     * @param mainFrame {@code JFrame} object represents for the view frame of the product manager
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
     * @param mainFrame {@code JFrame} object represents for the view frame of the product manager
     *
     * @param menu a {@code JMenu} object represents for the main menu in the taskbar
     */
    @Override
    public void setMainMenuGUI(String code, JFrame mainFrame, JMenu menu) {
        JMenuItem humanMenuItem = new JMenuItem("Human information");
        JMenuItem infrastructureMenuItem = new JMenuItem("Infrastructure information");

        /*
         * switch to view of the product manager
         */
        humanMenuItem.addActionListener(e -> {
            HumanView humanView = new ManagerView(code, mainFrame);
            super.centerPanel.removeAll();
            super.centerPanel.add(humanView.getPanel());
            mainFrame.setVisible(true);
        });

        /*
         * switch to view of the human manager
         */
        infrastructureMenuItem.addActionListener(e -> {
            InfrastructureView infrastructureView = new TableManagerView(code, mainFrame);
            super.centerPanel.removeAll();
            super.centerPanel.add(infrastructureView.getPanel());
            mainFrame.setVisible(true);
        });

        menu.add(humanMenuItem);
        menu.add(infrastructureMenuItem);
    }

    /**
     *<p>This method uses to set GUI for the sort menu</p>
     *
     * @param code {@code String} object represents for the code of the manager
     *
     * @param mainFrame {@code JFrame} object represents for the view frame of the product manager
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
        JMenuItem sortKindProductItem = new JMenuItem("Kind of product");
        JMenuItem sortSaleCostItem = new JMenuItem("Sale Cost");
        JMenuItem sortPurchaseCostItem = new JMenuItem("Purchase Cost");
        JMenuItem sortAmountItem = new JMenuItem("Amount");
        JMenuItem sortExpirationDateItem = new JMenuItem("Expiration Date");
        JMenuItem sortManufactureDateItem = new JMenuItem("Manufacture Date");
        sortCodeItem.addActionListener(e -> {
            super.centerPanel.remove(2);
            this.setDataTable(code, "code", kindSort);
            mainFrame.setVisible(true);
        });
        sortNameItem.addActionListener(e -> {
            super.centerPanel.remove(2);
            this.setDataTable(code, "name", kindSort);
            mainFrame.setVisible(true);
        });
        sortKindProductItem.addActionListener(e -> {
            super.centerPanel.remove(2);
            this.setDataTable(code, "kindProduct", kindSort);
            mainFrame.setVisible(true);
        });
        sortSaleCostItem.addActionListener(e -> {
            super.centerPanel.remove(2);
            this.setDataTable(code, "saleCost", kindSort);
            mainFrame.setVisible(true);
        });
        sortPurchaseCostItem.addActionListener(e -> {
            super.centerPanel.remove(2);
            this.setDataTable(code, "purchaseCost", kindSort);
            mainFrame.setVisible(true);
        });
        sortAmountItem.addActionListener(e -> {
            super.centerPanel.remove(2);
            this.setDataTable(code, "amountProduct", kindSort);
            mainFrame.setVisible(true);
        });
        sortExpirationDateItem.addActionListener(e -> {
            super.centerPanel.remove(2);
            this.setDataTable(code, "expiration", kindSort);
            mainFrame.setVisible(true);
        });
        sortManufactureDateItem.addActionListener(e -> {
            super.centerPanel.remove(2);
            this.setDataTable(code, "manufacture", kindSort);
            mainFrame.setVisible(true);
        });
        menu.add(sortCodeItem);
        menu.add(sortNameItem);
        menu.add(sortKindProductItem);
        menu.add(sortSaleCostItem);
        menu.add(sortPurchaseCostItem);
        menu.add(sortAmountItem);
        menu.add(sortExpirationDateItem);
        menu.add(sortManufactureDateItem);
    }

    /**
     *<p>This method uses to set GUI for the search menu</p>
     *
     * @param code {@code String} object represents for the code of the manager
     *
     * @param mainFrame {@code JFrame} object represents for the view frame of the product manager
     *
     * @param menu {@code JMenu} object represents for the sort menu in taskbar
     */
    @Override
    public void setSearchMenuGUI(String code, JFrame mainFrame, JMenu menu) {
        //create menu items of the search menu
        JMenuItem searchByStringItem = new JMenuItem("Search by keyword");
        JMenuItem searchByNumberItem = new JMenuItem("Search by range of value");
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
            JButton btn = new JButton("Confirm");
            CenterPanelView.setSubFrameSearchGUI(label, btn);
            //reset data of the table
            btn.addActionListener(e1 -> {
                super.centerPanel.remove(2);
                this.setTableGUI(new JTable(PRODUCT_CONTROLLER.searchKey(textField.getText()), this.getNameColTable()));
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
            JTextField textFieldLeft = new JTextField();
            JTextField textFieldRight = new JTextField();
            textFieldLeft.setFont(new Font(Font.SERIF, Font.BOLD, 25));
            textFieldRight.setFont(new Font(Font.SERIF, Font.BOLD, 25));
            textFieldLeft.setBackground(new Color(238, 238, 224));
            textFieldRight.setBackground(new Color(207, 207, 207));
            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(1, 2));
            panel.add(textFieldLeft);
            panel.add(textFieldRight);
            JButton btn = new JButton("Confirm");
            CenterPanelView.setSubFrameSearchGUI(label, btn);
            //reset data of the table
            btn.addActionListener(e1 -> {

                super.centerPanel.remove(2);
                this.setTableGUI(new JTable(PRODUCT_CONTROLLER.searchKey(
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
        menu.add(searchByStringItem);
        menu.add(searchByNumberItem);
    }

    /**
     * <p>This method uses to set GUI for the add menu</p>
     *
     * @param code a {@code String} object represents for the code of the product manager
     *
     * @param mainFrame a {@code JFrame} object represents for the view frame
     *
     * @param menu a {@code JMenu} object represents for the add menu
     */
    @Override
    public void setAddMenuGUI(String code, JFrame mainFrame, JMenu menu) {
        JMenuItem menuItem = new JMenuItem("Add new product");
        menuItem.addActionListener(e -> {
            mainFrame.setVisible(false);
            JFrame subFrame = new JFrame("Adding Frame");
            JPanel panel = new JPanel();
            JLabel nameLabel = new JLabel("Name", JLabel.LEFT);
            JLabel kindProductLabel = new JLabel("Kind of product", JLabel.LEFT);
            JLabel kindFoodLabel = new JLabel("Kind of food", JLabel.LEFT);
            JLabel kindDrinkLabel = new JLabel("Kind of drink", JLabel.LEFT);
            JLabel purchaseCostLabel = new JLabel("Purchase Cost", JLabel.LEFT);
            JLabel saleCostLabel = new JLabel("Sale Cost", JLabel.LEFT);
            JLabel saleTimeLabel = new JLabel("Sale Time", JLabel.LEFT);
            JLabel amountLabel = new JLabel("Amount", JLabel.LEFT);
            JLabel expirationDateLabel = new JLabel("Expiration date", JLabel.LEFT);
            JLabel manufactureDateLabel = new JLabel("Manufacture date", JLabel.LEFT);
            JButton confirmBtn = new JButton("Confirm");
            JTextField nameTextField = new JTextField("Name of food");
            JRadioButton foodRadioButton = new JRadioButton("FOOD", true);
            JRadioButton drinkRadioButton = new JRadioButton("DRINK");
            ButtonGroup kindProductButtonGroup = new ButtonGroup();
            kindProductButtonGroup.add(foodRadioButton);
            kindProductButtonGroup.add(drinkRadioButton);
            Vector<String> listKindFood= new Vector<>();
            Vector<String> listKindDrink=new Vector<>();
            for(KindFood value: KindFood.values())
                listKindFood.add(value.toString());
            for(KindDrink value: KindDrink.values())
                listKindDrink.add(value.toString());
            JComboBox<String> foodComboBox = new JComboBox<>(listKindFood);
            JComboBox<String> drinkComboBox = new JComboBox<>(listKindDrink);
            JTextField purchaseTextField = new JTextField("0");
            JTextField saleCostTextField = new JTextField("0");
            Vector<String> listSaleTime= new Vector<>();
            for(SaleTime value: SaleTime.values())
                listSaleTime.add(value.toString());
            JCheckBox[] saleTimeCheckBoxes = new JCheckBox[listSaleTime.size()];
            JPanel checkBoxPanel = new JPanel();
            for (int i = 0; i < listSaleTime.size(); i++) {
                saleTimeCheckBoxes[i] = new JCheckBox(listSaleTime.get(i));
                saleTimeCheckBoxes[i].setSelected(true);
                checkBoxPanel.add(saleTimeCheckBoxes[i]);
            }
            SpinnerModel spinnerModel = new SpinnerNumberModel(0, 0, 200, 1);
            JSpinner amountSpinner = new JSpinner(spinnerModel);
            DatePicker expirationDatePicker = new DatePicker();
            DatePicker manufactureDatePicker = new DatePicker();
            JPanel namePanel = new JPanel(new GridLayout(2, 1));
            JPanel kindProductPanel = new JPanel(new GridLayout(2, 1));
            JPanel subKindOfProductPanel = new JPanel(new FlowLayout());
            JPanel kindFoodPanel = new JPanel(new GridLayout(2, 1));
            JPanel kindDrinkPanel = new JPanel(new GridLayout(2, 1));
            JPanel purchaseCostPanel = new JPanel(new GridLayout(2, 1));
            JPanel saleCostPanel = new JPanel(new GridLayout(2, 1));
            JPanel saleTimePanel = new JPanel(new GridLayout(2, 1));
            JPanel amountPanel = new JPanel(new GridLayout(2, 1));
            JPanel expirationDatePanel = new JPanel(new FlowLayout());
            JPanel manufactureDatePanel = new JPanel(new FlowLayout());
            JPanel confirmBtnPanel = new JPanel();
            JLabel tempLabel = new JLabel();
            confirmBtnPanel.add(tempLabel);
            subFrame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    subFrame.dispose();
                    mainFrame.setVisible(true);
                }
            });
            subFrame.setSize(1200, 700);
            subFrame.setLayout(new BoxLayout(subFrame.getContentPane(), BoxLayout.Y_AXIS));
            panel.setLayout(new GridLayout(5, 2, 50, 50));
            panel.setBackground(Color.BLACK);
            panel.setVisible(true);
            confirmBtnPanel.setBackground(Color.BLACK);
            confirmBtnPanel.setVisible(true);

            CenterPanelView.setGUILabelOfAddComponent(nameLabel);
            CenterPanelView.setGUILabelOfAddComponent(kindProductLabel);
            CenterPanelView.setGUILabelOfAddComponent(kindFoodLabel);
            CenterPanelView.setGUILabelOfAddComponent(kindDrinkLabel);
            CenterPanelView.setGUILabelOfAddComponent(purchaseCostLabel);
            CenterPanelView.setGUILabelOfAddComponent(saleCostLabel);
            CenterPanelView.setGUILabelOfAddComponent(saleTimeLabel);
            CenterPanelView.setGUILabelOfAddComponent(amountLabel);
            CenterPanelView.setGUILabelOfAddComponent(expirationDateLabel);
            CenterPanelView.setGUILabelOfAddComponent(manufactureDateLabel);
            CenterPanelView.setGUITextFieldAddComponent(nameTextField);
            CenterPanelView.setGUITextFieldAddComponent(purchaseTextField);
            CenterPanelView.setGUITextFieldAddComponent(saleCostTextField);
            CenterPanelView.setGUIComboBoxAddComponent(foodComboBox);
            CenterPanelView.setGUIComboBoxAddComponent(drinkComboBox);
            CenterPanelView.setGUISpinnerAddComponent(amountSpinner);
            CenterPanelView.setGUIRadioButtonAddComponent(foodRadioButton);
            CenterPanelView.setGUIRadioButtonAddComponent(drinkRadioButton);

            namePanel.add(nameLabel);
            namePanel.add(nameTextField);
            kindProductPanel.add(kindProductLabel);
            kindProductPanel.add(subKindOfProductPanel);
            subKindOfProductPanel.add(foodRadioButton);
            subKindOfProductPanel.add(drinkRadioButton);
            kindFoodPanel.add(kindFoodLabel);
            kindFoodPanel.add(foodComboBox);
            kindDrinkPanel.add(kindDrinkLabel);
            kindDrinkPanel.add(drinkComboBox);
            purchaseCostPanel.add(purchaseCostLabel);
            purchaseCostPanel.add(purchaseTextField);
            saleCostPanel.add(saleCostLabel);
            saleCostPanel.add(saleCostTextField);
            saleTimePanel.add(saleTimeLabel);
            saleTimePanel.add(checkBoxPanel);
            amountPanel.add(amountLabel);
            amountPanel.add(amountSpinner);
            expirationDatePanel.add(expirationDateLabel);
            expirationDatePanel.add(expirationDatePicker);
            manufactureDatePanel.add(manufactureDateLabel);
            manufactureDatePanel.add(manufactureDatePicker);
            confirmBtnPanel.add(confirmBtn);

            namePanel.setBackground(Color.BLACK);
            kindProductPanel.setBackground(Color.BLACK);
            subKindOfProductPanel.setBackground(Color.BLACK);
            kindFoodPanel.setBackground(Color.BLACK);
            kindDrinkPanel.setBackground(Color.BLACK);
            purchaseCostPanel.setBackground(Color.BLACK);
            saleCostPanel.setBackground(Color.BLACK);
            checkBoxPanel.setBackground(Color.BLACK);
            saleTimePanel.setBackground(Color.BLACK);
            amountPanel.setBackground(Color.BLACK);
            expirationDatePanel.setBackground(Color.BLACK);
            expirationDatePicker.setBackground(Color.BLACK);
            manufactureDatePanel.setBackground(Color.BLACK);
            manufactureDatePicker.setBackground(Color.BLACK);
            confirmBtnPanel.setBackground(Color.BLACK);

            JPanel emptyPanel = new JPanel();
            emptyPanel.setBackground(Color.BLACK);
            panel.add(namePanel);
            panel.add(emptyPanel);
            panel.add(kindProductPanel);
            panel.add(kindFoodPanel);
            foodRadioButton.addActionListener(e1 -> {
                panel.remove(3);
                panel.add(kindFoodPanel, 3);
                kindFoodPanel.setVisible(false);
                kindFoodPanel.setVisible(true);
                subFrame.setVisible(true);
            });
            drinkRadioButton.addActionListener(e1 -> {
                panel.remove(3);
                panel.add(kindDrinkPanel, 3);
                kindDrinkPanel.setVisible(false);
                kindDrinkPanel.setVisible(true);
                subFrame.setVisible(true);
            });

            panel.add(purchaseCostPanel);
            panel.add(saleCostPanel);
            panel.add(saleTimePanel);
            panel.add(amountPanel);
            panel.add(expirationDatePanel);
            panel.add(manufactureDatePanel);

            confirmBtn.setBackground(Color.GREEN);
            confirmBtn.setFont(new Font(Font.SERIF, Font.BOLD, 30));
            confirmBtn.addActionListener(e1 -> {
                Object objModel = null;
                String name = CenterPanelView.getDataTextFieldAddComponent(nameTextField);
                double purchaseCost = Double.parseDouble(CenterPanelView.getDataTextFieldAddComponent(purchaseTextField));
                double saleCost = Double.parseDouble(CenterPanelView.getDataTextFieldAddComponent(saleCostTextField));
                int amount = Integer.parseInt(CenterPanelView.getDataSpinnerAddComponent(amountSpinner));
                Set<SaleTime> saleTimeSet = CenterPanelView.getDataCheckBoxAddComponent(saleTimeCheckBoxes);
                Date expirationDate = expirationDatePicker.getValue();
                Date manufactureDate = manufactureDatePicker.getValue();
                if (!CenterPanelView.getDataRadioButtonAddComponent(foodRadioButton).equals(""))
                    objModel = new FoodModel(
                            name, purchaseCost, saleCost, amount, saleTimeSet, expirationDate, manufactureDate,
                            CenterPanelView.getDataComboBoxAddComponent(foodComboBox).equals("VEGETARIAN") ? KindFood.VEGETARIAN : (
                                    CenterPanelView.getDataComboBoxAddComponent(foodComboBox).equals("NON_VEGETARIANS") ?
                                            KindFood.NON_VEGETARIANS : KindFood.BOTH));
                if (!CenterPanelView.getDataRadioButtonAddComponent(drinkRadioButton).equals(""))
                    objModel = new DrinkModel(
                            name, purchaseCost, saleCost, amount, saleTimeSet, expirationDate, manufactureDate,
                            CenterPanelView.getDataComboBoxAddComponent(drinkComboBox).equals("ICE") ? KindDrink.ICE : KindDrink.NON_ICE);

                PRODUCT_CONTROLLER.add(objModel);
                subFrame.dispose();
                this.centerPanel.remove(2);
                this.setDataTable(code, "", false);
                mainFrame.setVisible(true);
            });
            subFrame.add(panel, BorderLayout.CENTER);
            subFrame.add(confirmBtnPanel, BorderLayout.SOUTH);
            subFrame.setVisible(true);
            subFrame.setLocationRelativeTo(null);

        });
        menu.add(menuItem);
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
                        PRODUCT_CONTROLLER.updateName(code, valueEdit);
                        break;
                    case "Kind Of Product":
                        PRODUCT_CONTROLLER.updateKindProduct(code, valueEdit.toUpperCase());
                        break;
                    case "Purchase Cost":
                        PRODUCT_CONTROLLER.updatePurchaseCost(code, Double.parseDouble(valueEdit));
                        break;
                    case "Sale Cost":
                        PRODUCT_CONTROLLER.updateSaleCost(code, Double.parseDouble(valueEdit));
                        break;
                    case "Sale Time":
                        String[] arr = valueEdit.split(",");
                        Set<SaleTime> saleTimeSet = new HashSet<>();
                        for (String s : arr)
                            switch (s.trim().toUpperCase()) {
                                case "MORNING" -> saleTimeSet.add(SaleTime.MORNING);
                                case "NOON" -> saleTimeSet.add(SaleTime.NOON);
                                case "AFTERNOON" -> saleTimeSet.add(SaleTime.AFTERNOON);
                                case "NIGHT" -> saleTimeSet.add(SaleTime.NIGHT);
                                case "LATE" -> saleTimeSet.add(SaleTime.LATE);
                            }
                        PRODUCT_CONTROLLER.updateSaleTime(code, saleTimeSet);
                        break;
                    case "Amount":
                        PRODUCT_CONTROLLER.updateAmount(code, Integer.parseInt(valueEdit));
                        break;
                    case "Expiration Date":
                        try {
                            PRODUCT_CONTROLLER.updateExpirationDate(
                                    code,
                                    new SimpleDateFormat("yyyy-MM-dd").parse(valueEdit));
                        } catch (ParseException parseException) {
                            parseException.printStackTrace();
                        }
                        break;
                    case "Manufacture Date":
                        try {
                            PRODUCT_CONTROLLER.updateManufactureDate(code,
                                    new SimpleDateFormat("yyyy-MM-dd").parse(valueEdit));
                        } catch (ParseException parseException) {
                            parseException.printStackTrace();
                        }
                        break;
                }
            }
        });
    }

    /**
     *<p>This method uses to set GUI for the remove menu</p>
     *
     * @param code a {@code String} object represents for the code of the product manager
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
            //remove table and reset data table
            confirmBtn.addActionListener(e1 -> {
                if (contentTextField.getText() != null) {
                    PRODUCT_CONTROLLER.remove(contentTextField.getText());
                    subFrame.dispose();
                    super.centerPanel.remove(2);
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
        for (int i = 0; i < 10; i++) {
            table.getColumnModel().getColumn(i).setMinWidth(190);
            table.getColumnModel().getColumn(i).setCellRenderer(defaultTableCellRenderer);
            if (i == 2)
                table.getColumnModel().getColumn(i).setMinWidth(300);
            if (i == 4)
                table.getColumnModel().getColumn(i).setMinWidth(350);
            if (i == 8)
                table.getColumnModel().getColumn(i).setMinWidth(250);
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
        super.centerPanel.add(sp);
    }

    /**
     * <p>This method uses to set data for the table</p>
     *
     * @param code a {@code String} object represents for the code of the product manager
     *
     * @param col a {@code String} object represents for the name of column in database
     *            if the <code>col</code> is empty => no sort
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
        listData = col.isEmpty() ? PRODUCT_CONTROLLER.getAll("") :
                (kindSort ? PRODUCT_CONTROLLER.getAll("ORDER BY " + col + " " + " ASC") :
                        PRODUCT_CONTROLLER.getAll("ORDER BY " + col + " " + " DESC"));
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
        Vector<String> row = new Vector<>();
        row.add("Id");
        row.add("Code");
        row.add("Name");
        row.add("Kind Of Product");
        row.add("Sale Time");
        row.add("Purchase Cost");
        row.add("Sale Cost");
        row.add("Expiration Date");
        row.add("Manufacture Date");
        row.add("Amount");
        return row;
    }
}
