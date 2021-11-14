package InfrastructureManagementMVC.View;

import CenterObjects.CenterPanelView;
import DatePicker.DatePicker;
import HumanManagementMVC.View.HumanView;
import HumanManagementMVC.View.ManagerView;
import InfrastructureManagementMVC.Controller.InfrastructureController;
import InfrastructureManagementMVC.Enum.Material;
import InfrastructureManagementMVC.Enum.NameTable;
import InfrastructureManagementMVC.Model.TableInfoModel;
import Interface.ManagerTable;
import Interface.ManagerTaskBar;
import CenterObjects.MenuBar;
import ProductManagementMVC.View.ProductManagerView;
import ProductManagementMVC.View.ProductView;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

/**
 * <p>{@code TableManagerView} use to set GUI for table manager </p>
 * <p>This class extends the {@code InfrastructureView} class and implements
 * {@code TableManagerView} , {@code ManagerTable} interface</p>
 */
public class TableManagerView extends InfrastructureView implements ManagerTaskBar, ManagerTable {

    /**
     * this attribute use methods of the {@code InfrastructureController}
     */
    private final static InfrastructureController INFRASTRUCTURE_CONTROLLER = new InfrastructureController();

    //set layout for the table manager view
    {
        this.centerPanel.setLayout(new BoxLayout(this.centerPanel, BoxLayout.Y_AXIS));
    }

    /**
     * <p>This method uses to create a view frame for the table manager</p>
     *
     * @param code a {@code String} object represents for the code of the manager
     *
     * @param mainFrame a {@code JFrame} object represents for the view frame
     */
    public TableManagerView(String code, JFrame mainFrame) {
        this.setTaskbarMenu(code, mainFrame);
        this.centerPanel.add(this.panel);
        this.setDataTable(code, "", true);
    }

    /**
     * <p>This method uses to set GUI for  the taskbar and  menu components of the table manager view</p>
     *
     * @param code {@code String} object represents for the code of the manager
     *
     * @param mainFrame {@code JFrame} object represents for the view frame of the table manager
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
     * @param mainFrame {@code JFrame} object represents for the view frame of the manager
     *
     * @param menu a {@code JMenu} object represents for the main menu in the taskbar
     */
    @Override
    public void setMainMenuGUI(String code, JFrame mainFrame, JMenu menu) {
        JMenuItem productMenuItem = new JMenuItem("Product information");
        JMenuItem humanMenuItem = new JMenuItem("Human information");

        /*
         * switch to view of the product manager
         */
        productMenuItem.addActionListener(e -> {
            ProductView productView = new ProductManagerView(code, mainFrame);
            this.centerPanel.removeAll();
            this.centerPanel.add(productView.getPanel());
            mainFrame.setVisible(true);
        });

        /*
         * switch to view of the human manager
         */
        humanMenuItem.addActionListener(e -> {
            HumanView humanView = new ManagerView(code, mainFrame);
            this.centerPanel.removeAll();
            this.centerPanel.add(humanView.getPanel());
            mainFrame.setVisible(true);
        });

        menu.add(humanMenuItem);
        menu.add(productMenuItem);

    }

    /**
     *<p>This method uses to set GUI for the sort menu</p>
     *
     * @param code {@code String} object represents for the code of the manager
     *
     * @param mainFrame {@code JFrame} object represents for the view frame of the table manager
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
        JMenuItem sortCodeItem = new JMenuItem("Sort code");
        JMenuItem sortNameItem = new JMenuItem("Sort name");
        JMenuItem sortCapacityItem = new JMenuItem("Sort capacity");
        JMenuItem sortMaterialItem = new JMenuItem("Sort material");
        JMenuItem sortPurchaseDateItem = new JMenuItem("Sort purchase date");
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
        sortCapacityItem.addActionListener(e -> {
            this.centerPanel.remove(2);
            this.setDataTable(code, "capacity", kindSort);
            mainFrame.setVisible(true);
        });
        sortMaterialItem.addActionListener(e -> {
            this.centerPanel.remove(2);
            this.setDataTable(code, "material", kindSort);
            mainFrame.setVisible(true);
        });
        sortPurchaseDateItem.addActionListener(e -> {
            this.centerPanel.remove(2);
            this.setDataTable(code, "purchaseDate", kindSort);
            mainFrame.setVisible(true);
        });
        menu.add(sortCodeItem);

        menu.add(sortNameItem);
        menu.add(sortCapacityItem);
        menu.add(sortMaterialItem);
        menu.add(sortPurchaseDateItem);
    }

    /**
     *<p>This method uses to set GUI for the search menu</p>
     *
     * @param code {@code String} object represents for the code of the manager
     *
     * @param mainFrame {@code JFrame} object represents for the view frame of the table manager
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
                this.centerPanel.remove(2);
                this.setTableGUI(new JTable(
                        INFRASTRUCTURE_CONTROLLER.searchKey(textField.getText()),
                        this.getNameColTable()));
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
                this.centerPanel.remove(2);
                this.setTableGUI(new JTable(INFRASTRUCTURE_CONTROLLER.searchKey(
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
     * @param code a {@code String} object represents for the code of the table manager
     *
     * @param mainFrame a {@code JFrame} object represents for the view frame
     *
     * @param menu a {@code JMenu} object represents for the add menu
     */
    @Override
    public void setAddMenuGUI(String code, JFrame mainFrame, JMenu menu) {
        JMenuItem menuItem = new JMenuItem("Add new table");
        menuItem.addActionListener(e -> {
            mainFrame.setVisible(false);
            JFrame subFrame = new JFrame("Adding Frame");
            JPanel panel = new JPanel();
            JPanel namePanel = new JPanel(new GridLayout(2, 1));
            JPanel capacityPanel = new JPanel(new GridLayout(2, 1));
            JPanel materialPanel = new JPanel(new GridLayout(2, 1));
            JPanel purchaseDatePanel = new JPanel(new GridLayout(2, 1));
            JPanel confirmBtnPanel = new JPanel(new GridLayout(2, 1));
            JLabel nameLabel = new JLabel("name", JLabel.LEFT);
            JLabel capacityLabel = new JLabel("Capacity", JLabel.LEFT);
            JLabel materialLabel = new JLabel("Material", JLabel.LEFT);
            JLabel purchaseDateLabel = new JLabel("Purchase Date", JLabel.LEFT);
            JButton confirmBtn = new JButton("Confirm");

            Vector<String> nameVector= new Vector<>();
            for(NameTable value : NameTable.values())
                nameVector.add(value.toString());
            JComboBox<String> nameComboBox = new JComboBox<>(nameVector);
            nameComboBox.setSelectedIndex(0);
            final String[] CAPACITY = {"1", "2", "4", "8", "10", "12"};
            JComboBox<String> capacityComboBox = new JComboBox<>(CAPACITY);
            Vector<String> materialVector= new Vector<>();
            for(Material value: Material.values())
                materialVector.add(value.toString());
            JComboBox<String> materialComboBox= new JComboBox<>(materialVector);
            materialComboBox.setSelectedIndex(0);
            DatePicker purchaseDatePicker = new DatePicker();

            subFrame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    subFrame.dispose();
                    mainFrame.setVisible(true);
                }
            });
            subFrame.setSize(1200, 400);
            subFrame.setLayout(new BoxLayout(subFrame.getContentPane(), BoxLayout.Y_AXIS));
            panel.setLayout(new GridLayout(2, 2, 50, 50));
            panel.setBackground(Color.BLACK);
            panel.setVisible(true);
            confirmBtnPanel.setBackground(Color.BLACK);
            confirmBtnPanel.setVisible(true);

            CenterPanelView.setGUILabelOfAddComponent(nameLabel);
            CenterPanelView.setGUILabelOfAddComponent(capacityLabel);
            CenterPanelView.setGUILabelOfAddComponent(materialLabel);
            CenterPanelView.setGUILabelOfAddComponent(purchaseDateLabel);
            CenterPanelView.setGUIComboBoxAddComponent(nameComboBox);
            CenterPanelView.setGUIComboBoxAddComponent(materialComboBox);
            CenterPanelView.setGUIComboBoxAddComponent(capacityComboBox);

            namePanel.setBackground(Color.BLACK);
            capacityPanel.setBackground(Color.BLACK);
            materialPanel.setBackground(Color.BLACK);
            purchaseDatePanel.setBackground(Color.BLACK);
            purchaseDatePicker.setBackground(Color.BLACK);
            confirmBtnPanel.setBackground(Color.BLACK);

            namePanel.add(nameLabel);
            namePanel.add(nameComboBox);
            capacityPanel.add(capacityLabel);
            capacityPanel.add(capacityComboBox);
            materialPanel.add(materialLabel);
            materialPanel.add(materialComboBox);
            purchaseDatePanel.add(purchaseDateLabel);
            purchaseDatePanel.add(purchaseDatePicker);

            panel.add(namePanel);
            panel.add(materialPanel);
            panel.add(capacityPanel);
            panel.add(purchaseDatePanel);
            confirmBtnPanel.add(new JLabel());
            confirmBtnPanel.add(confirmBtn);
            confirmBtn.setBackground(Color.GREEN);
            confirmBtn.setFont(new Font(Font.SERIF, Font.BOLD, 30));

            //action after confirm => create a new table with input data
            confirmBtn.addActionListener(e1 -> {
                NameTable name= NameTable.checkNameTable(CenterPanelView.getDataComboBoxAddComponent(nameComboBox));
                Material material=Material.checkMaterial(CenterPanelView.getDataComboBoxAddComponent(materialComboBox));
                int capacity = Integer.parseInt(CenterPanelView.getDataComboBoxAddComponent(capacityComboBox));
                Date purchaseDate = purchaseDatePicker.getValue();
                Object objModel = new TableInfoModel(name,
                        purchaseDate, material, capacity);
                INFRASTRUCTURE_CONTROLLER.add(objModel);
                subFrame.dispose();
                this.centerPanel.remove(2);
                this.setDataTable(code, "", false);
                mainFrame.setVisible(true);
            });

            subFrame.add(panel);
            subFrame.add(confirmBtnPanel);
            subFrame.setVisible(true);
            subFrame.setLocationRelativeTo(null);

        });
        menu.add(menuItem);
    }

    /**
     *<p>This method uses to set GUI for the remove menu</p>
     *
     * @param code a {@code String} object represents for the code of the table manager
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
                    INFRASTRUCTURE_CONTROLLER.remove(contentTextField.getText());
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
                        INFRASTRUCTURE_CONTROLLER.updateName(code, valueEdit);
                        break;
                    case "Capacity":
                        INFRASTRUCTURE_CONTROLLER.updateCapacity(code, Integer.parseInt(valueEdit));
                        break;
                    case "Status":
                        INFRASTRUCTURE_CONTROLLER.updateStatus(code, Boolean.parseBoolean(valueEdit));
                        break;
                    case "Material":
                        INFRASTRUCTURE_CONTROLLER.updateMaterial(code, valueEdit);
                        break;
                    case "Date of purchase":
                        try {
                            INFRASTRUCTURE_CONTROLLER.updatePurchaseDate(code,
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
     * <p>This method uses to set data for the table</p>
     *
     * @param code a {@code String} object represents for the code of the table manager
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
        listData = col.isEmpty() ? INFRASTRUCTURE_CONTROLLER.getAll("") :
                (kindSort? INFRASTRUCTURE_CONTROLLER.getAll("ORDER BY " + col + " " + " ASC") :
                        INFRASTRUCTURE_CONTROLLER.getAll("ORDER BY " + col + " " + " DESC"));
        JTable table = new JTable(listData, this.getNameColTable());
        this.updateTable(table);
        this.setTableGUI(table);
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
        for (int i = 0; i < 7; i++) {
            table.getColumnModel().getColumn(i).setMinWidth(190);
            table.getColumnModel().getColumn(i).setCellRenderer(defaultTableCellRenderer);
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
        row.add("Capacity");
        row.add("Status");
        row.add("Material");
        row.add("Date of purchase");
        return row;
    }
}
