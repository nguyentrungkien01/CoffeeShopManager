package HumanManagementMVC.View;

import CenterObjects.CenterPanelView;
import HumanManagementMVC.Controller.ServantController;
import InfrastructureManagementMVC.Controller.InfrastructureController;
import ProductManagementMVC.Controller.ProductController;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

/**
 * <p>{@code ServantView} uses to set GUI for servant staff</p>
 * <p>This class extends the {@code HumanView} class </p>
 */
public class ServantView extends HumanView {

    /**
     * this attribute uses methods of the {@code InfrastructureController}
     */
    private final static InfrastructureController INFRASTRUCTURE_CONTROLLER = new InfrastructureController();

    /**
     * this attribute uses methods of the {@code ServantController}
     */
    private final static ServantController SERVANT_CONTROLLER =new ServantController();

    /**
     * this attribute uses methods of the {@code ProductController}
     */
    private final static ProductController PRODUCT_CONTROLLER = new ProductController();

    /**
     * <p>This method uses to create a view of the servant</p>
     * @param code a {@code String} represents for the code of the servant
     *
     * @param mainFrame a {@code JFrame} represents for the view frame of the servant
     */
    public ServantView(String code, JFrame mainFrame) {
        setTableGUI(code, mainFrame);
    }

    /**
     *<p>This method uses to specific how all of tables will be displayed</p>
     * <p>if the table was selected the background was change to white color</p>
     * @param code a {@code String} represents for the code of the servant
     *
     * @param mainFrame a {@code JFrame} represents for the view frame of the servant
     */
    private void setTableGUI(String code, JFrame mainFrame) {
        JPanel mainPanel = new JPanel();
        Vector<JButton> btnVector = new Vector<>();
        int amountTable = INFRASTRUCTURE_CONTROLLER.getAmount();//get amount table
        Vector<String> nameTableVector = INFRASTRUCTURE_CONTROLLER.getAllCodeTable();//get list of code table
        for (int i = 1; i <= amountTable; i++) {
            JButton btn = new JButton(
                    nameTableVector.get(i - 1).substring(0, 5) + " " +
                            Integer.parseInt(nameTableVector.get(i - 1).substring(nameTableVector.get(i - 1).indexOf("0") + 1)));
            btn.setFont(new Font(Font.SERIF, Font.BOLD, 30));
            //change background color of the table
            if (INFRASTRUCTURE_CONTROLLER.isBooked(nameTableVector.get(i - 1))) {
                btn.setBackground(Color.WHITE);
                btn.setForeground(Color.BLACK);
            } else {
                btn.setBackground(Color.BLACK);
                btn.setForeground(Color.WHITE);
            }
            btnVector.add(btn);
            this.setActionTable(mainFrame, btn, code, nameTableVector.get(i - 1));
        }
        for (int i = 1; i <= amountTable; i++)
            mainPanel.add(btnVector.get(i - 1));
        mainPanel.setOpaque(true);
        mainPanel.setVisible(true);
        mainPanel.setBackground(Color.DARK_GRAY);
        mainPanel.setLayout(new GridLayout(amountTable / 5, 5, 5, 5));
        //set scroll panel
        JScrollPane jScrollPane = new JScrollPane(mainPanel);
        jScrollPane.setPreferredSize(new Dimension(1150, 750));
        jScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        this.centerPanel.add(jScrollPane, BorderLayout.CENTER);
    }

    /**
     *<p>This method uses to set action frame after book table </p>
     *
     * @param mainFrame a {@code JFrame} represents for the view frame of the servant
     *
     * @param button a {@code JButton} object represents for the table button
     *
     * @param staffCode a {@code String} represents for the code of the staff
     *
     * @param tableCode a {@code String } represents for the code of the table
     */
    private void setActionTable(JFrame mainFrame, JButton button, String staffCode, String tableCode) {
        button.addActionListener(e -> {
            JFrame subFrame = new JFrame("Booked Table");
            subFrame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    subFrame.dispose();
                }
            });
            subFrame.setSize(300, 300);
            JLabel label = new JLabel(button.getText(), JLabel.CENTER);
            label.setForeground(Color.WHITE);
            label.setBackground(Color.BLACK);
            label.setFont(new Font(Font.SERIF, Font.BOLD, 30));
            label.setOpaque(true);
            JPanel panel = new JPanel(new GridLayout(1, 2));
            JButton bookBtn = new JButton("Book");
            if (INFRASTRUCTURE_CONTROLLER.isBooked(
                    String.format("TABLE%05d", Integer.valueOf(button.getText().substring(6)))))
                bookBtn.setText("Update");
            JButton cancelBtn = new JButton("Cancel");
            bookBtn.setBackground(Color.GREEN);
            cancelBtn.setBackground(Color.RED);
            bookBtn.setFont(new Font(Font.SERIF, Font.BOLD, 30));
            cancelBtn.setFont(new Font(Font.SERIF, Font.BOLD, 30));
            //change color of the table
            bookBtn.addActionListener(e1 -> {
                String subTableCode = String.format("TABLE%05d", Integer.valueOf(button.getText().substring(6)));
                if (!INFRASTRUCTURE_CONTROLLER.isBooked(subTableCode)) {
                    SERVANT_CONTROLLER.bookTable(staffCode, tableCode);
                    button.setBackground(Color.WHITE);
                    button.setForeground(Color.BLACK);
                }
                setAddMenuGUI(tableCode, mainFrame);
                subFrame.dispose();
            });
            //action after click the cancel button
            cancelBtn.addActionListener(e2 -> {
                subFrame.dispose();
                JFrame cancelFrame = new JFrame("Option cancel");
                cancelFrame.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        cancelFrame.dispose();
                    }
                });
                cancelFrame.setSize(new Dimension(600, 100));
                cancelFrame.setLayout(new GridLayout(1, 2, 2, 2));
                JButton cancelProductBtn = new JButton("Cancel Product");
                JButton cancelTableBtn = new JButton("Cancel Table");
                cancelProductBtn.setFont(new Font(Font.SERIF, Font.BOLD, 30));
                cancelTableBtn.setFont(new Font(Font.SERIF, Font.BOLD, 30));
                cancelProductBtn.setBackground(Color.BLACK);
                cancelTableBtn.setBackground(Color.BLACK);
                cancelProductBtn.setForeground(Color.CYAN);
                cancelTableBtn.setForeground(Color.CYAN);
                cancelProductBtn.addActionListener(e21 -> {
                    setCancelProductMenuGUI(tableCode, mainFrame);
                    cancelFrame.dispose();
                });
                cancelTableBtn.addActionListener(e22 -> {
                    if (INFRASTRUCTURE_CONTROLLER.isBooked(tableCode)) {
                        JFrame subCancelFrame = new JFrame("Canceled Frame");
                        subCancelFrame.addWindowListener(new WindowAdapter() {
                            @Override
                            public void windowClosing(WindowEvent e) {
                                subCancelFrame.dispose();
                            }
                        });
                        subCancelFrame.setSize(600, 300);
                        subCancelFrame.setLayout(new GridLayout(1, 2));
                        JButton chargedBtn = new JButton("Charged");
                        JButton notChargedBtn = new JButton("Not yet charged");
                        chargedBtn.setBackground(Color.BLACK);
                        chargedBtn.setForeground(Color.WHITE);
                        chargedBtn.setFont(new Font(Font.SERIF, Font.BOLD, 30));
                        notChargedBtn.setBackground(Color.BLACK);
                        notChargedBtn.setForeground(Color.WHITE);
                        notChargedBtn.setFont(new Font(Font.SERIF, Font.BOLD, 30));
                        //cancel charged table
                        chargedBtn.addActionListener(e1 -> {
                            SERVANT_CONTROLLER.cancelTable(tableCode, true);
                            button.setBackground(Color.BLACK);
                            button.setForeground(Color.WHITE);
                            subCancelFrame.dispose();
                            cancelFrame.dispose();
                        });
                        //cancel not charged table
                        notChargedBtn.addActionListener(e1 -> {
                            SERVANT_CONTROLLER.cancelTable(tableCode, false);
                            button.setBackground(Color.BLACK);
                            button.setForeground(Color.WHITE);
                            subCancelFrame.dispose();
                            cancelFrame.dispose();
                        });
                        subCancelFrame.add(chargedBtn);
                        subCancelFrame.add(notChargedBtn);
                        subCancelFrame.setVisible(true);
                        subCancelFrame.setLocationRelativeTo(null);

                    }
                    cancelFrame.dispose();
                });

                cancelFrame.add(cancelProductBtn);
                cancelFrame.add(cancelTableBtn);
                cancelFrame.setVisible(true);
                cancelFrame.setLocationRelativeTo(null);

            });
            panel.add(bookBtn);
            panel.add(cancelBtn);
            subFrame.add(label, BorderLayout.NORTH);
            subFrame.add(panel, BorderLayout.CENTER);
            subFrame.setVisible(true);
            subFrame.setLocationRelativeTo(null);
        });
    }

    /**
     * <p>This method uses to set GUI of the add product to menu of the booked table</p>
     *
     * @param codeTable a {@code String } object represents for the code of the table
     *
     * @param mainFrame a {@code JFrame} object represents for the view frame of the servant
     */
    private void setAddMenuGUI(String codeTable, JFrame mainFrame) {
        mainFrame.setVisible(false);
        JFrame subFrame = new JFrame("Menu");
        subFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                mainFrame.setVisible(true);
                subFrame.dispose();
            }
        });
        subFrame.setSize(1400, 800);
        JPanel mainPanel = new JPanel();
        JPanel panelLeft = new JPanel();
        JPanel panelRight = new JPanel();
        JPanel confirmBtnPanel = new JPanel();
        Vector<Vector<String>> listProductLeft = PRODUCT_CONTROLLER.getProductsWithSaleTime(
                PRODUCT_CONTROLLER.getAll("ORDER BY code ASC"));
        Vector<JLabel> amountLabelVector = new Vector<>();

        /*
            LEFT PANEL
         */

        JPanel subPanelLeft = new JPanel(new GridLayout(1, 1));
        JLabel menuLabel = new JLabel("✰︵✰✼Mҽղմ✼✰︵✰", JLabel.CENTER);
        JButton confirmBtn = new JButton("Confirm");

        menuLabel.setFont(new Font(Font.SERIF, Font.BOLD, 50));
        subPanelLeft.setBackground(Color.BLACK);
        menuLabel.setForeground(Color.CYAN);
        subPanelLeft.add(menuLabel);
        panelLeft.add(subPanelLeft);
        panelLeft.setLayout(new GridLayout(listProductLeft.size() + 1, 1, 1, 1));
        mainPanel.setLayout(new GridLayout(1, 2));
        //set list product
        for (Vector<String> strings : listProductLeft) {
            JPanel infoPanel = new JPanel();
            JLabel infoLabel = new JLabel(
                    strings.get(1) + "     ✿     " +
                            strings.get(2) + "     ✿     " +
                            strings.get(6) + " VNĐ  ", JLabel.LEFT);
            JButton addBtn = new JButton(" + ");
            JButton subBtn = new JButton(" - ");
            JPanel btnPanel = new JPanel(new GridLayout(2, 1));
            JPanel subBtnPanel = new JPanel(new FlowLayout());
            JLabel resultAmountLabel = new JLabel("0", JLabel.CENTER);
            infoPanel.setBackground(Color.BLACK);
            btnPanel.setMaximumSize(new Dimension(150, 100));
            addBtn.setBackground(Color.GREEN);
            subBtn.setBackground(Color.RED);
            addBtn.setSize(50, 50);
            subBtn.setSize(50, 50);
            subBtnPanel.setSize(new Dimension(100, 200));
            subBtnPanel.setBackground(Color.BLACK);
            infoLabel.setBackground(Color.BLACK);
            infoLabel.setForeground(Color.ORANGE);
            infoLabel.setMinimumSize(new Dimension(600, 100));
            infoLabel.setFont(new Font(Font.SERIF, Font.BOLD, 20));
            infoLabel.setOpaque(true);
            amountLabelVector.add(resultAmountLabel);
            // add the product
            addBtn.addActionListener(e -> {
                int amount = Integer.parseInt(resultAmountLabel.getText());
                if (amount < Integer.parseInt(strings.get(9)))
                    resultAmountLabel.setText(String.valueOf(amount + 1));
            });
            // cancel the product
            subBtn.addActionListener(e -> {
                int amount = Integer.parseInt(resultAmountLabel.getText());
                if (amount > 0)
                    resultAmountLabel.setText(String.valueOf(amount - 1));
            });
            infoPanel.add(infoLabel, BorderLayout.CENTER);
            subBtnPanel.add(addBtn);
            subBtnPanel.add(subBtn);
            btnPanel.add(subBtnPanel);
            btnPanel.add(resultAmountLabel, BorderLayout.EAST);
            infoPanel.add(btnPanel);
            panelLeft.add(infoPanel);
            panelLeft.setVisible(true);
        }

         /*
            RIGHT PANEL
         */
        Vector<Vector<String>> listProductRight = SERVANT_CONTROLLER.getAllInfoBookedTable(codeTable);
        panelRight.setLayout(new GridLayout(listProductRight.size() + 1, 1, 1, 1));
        JLabel tableLabel = new JLabel(
                String.format("✰︵✰✼TABLE %s✼✰︵✰", codeTable.substring(codeTable.lastIndexOf("0") + 1)), JLabel.CENTER);
        panelRight.add(tableLabel);
        tableLabel.setFont(new Font(Font.SERIF, Font.BOLD, 50));
        tableLabel.setForeground(Color.CYAN);
        tableLabel.setBackground(Color.BLACK);
        tableLabel.setOpaque(true);
        // set list of booked product
        listProductRight.forEach(e -> {
            JPanel infoPanel = new JPanel();
            JLabel infoLabel = new JLabel(
                    e.get(0) + "     ✿     " +
                            e.get(1) + "     ✿     " +
                            e.get(2));
            infoPanel.setBackground(Color.BLACK);
            infoLabel.setBackground(Color.BLACK);
            infoLabel.setForeground(Color.ORANGE);
            infoLabel.setMinimumSize(new Dimension(600, 100));
            infoLabel.setFont(new Font(Font.SERIF, Font.BOLD, 20));
            infoLabel.setOpaque(true);
            infoPanel.add(infoLabel);
            panelRight.add(infoPanel);
        });
        panelLeft.setBackground(Color.WHITE);
        panelRight.setBackground(Color.WHITE);
        JScrollPane scrollPaneLeft = new JScrollPane(panelLeft);
        JScrollPane scrollPaneRight = new JScrollPane(panelRight);
        scrollPaneLeft.setPreferredSize(new Dimension(600, 750));
        scrollPaneRight.setPreferredSize(new Dimension(600, 750));
        scrollPaneLeft.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPaneLeft.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPaneRight.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPaneRight.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);


        confirmBtn.setFont(new Font(Font.SERIF, Font.BOLD, 30));
        confirmBtn.setBackground(Color.GREEN);
        confirmBtnPanel.setBackground(Color.BLACK);
        confirmBtnPanel.add(confirmBtn);
        //confirm booked the product
        confirmBtn.addActionListener(e -> {
            for (int i = 0; i < amountLabelVector.size(); i++)
                if (!amountLabelVector.get(i).getText().equals("0"))
                    SERVANT_CONTROLLER.orderProduct(
                            codeTable,
                            listProductLeft.get(i).get(1),
                            Integer.parseInt(amountLabelVector.get(i).getText()));
            mainFrame.setVisible(true);
            subFrame.dispose();

        });
        mainPanel.add(scrollPaneLeft);
        mainPanel.add(scrollPaneRight);
        subFrame.add(mainPanel, BorderLayout.CENTER);
        subFrame.add(confirmBtnPanel, BorderLayout.SOUTH);
        subFrame.setVisible(true);
        subFrame.setLocationRelativeTo(null);
    }

    /**
     * <p>This method uses to set GUI of the cancel products</p>
     *
     * @param codeTable a {@code String } object represents for the code of the table
     *
     * @param mainFrame a {@code JFrame} object represents for the view frame of the servant
     */
    private void setCancelProductMenuGUI(String codeTable, JFrame mainFrame) {
        mainFrame.setVisible(false);
        JFrame subFrame = new JFrame("Cancel Product Frame");
        subFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                subFrame.dispose();
                mainFrame.setVisible(true);
            }
        });
        Vector<Vector<String>> listProduct = SERVANT_CONTROLLER.getAllInfoBookedTable(codeTable);
        subFrame.setSize(800, 700);

        JPanel panel = new JPanel(new GridLayout(listProduct.size() + 1, 1, 1, 1));
        JLabel tableLabel = new JLabel(
                String.format("✰︵✰✼TABLE %s✼✰︵✰", codeTable.substring(codeTable.lastIndexOf("0") + 1)), JLabel.CENTER);
        tableLabel.setFont(new Font(Font.SERIF, Font.BOLD, 50));
        tableLabel.setForeground(Color.CYAN);
        tableLabel.setBackground(Color.BLACK);
        tableLabel.setOpaque(true);
        panel.add(tableLabel);
        //information list of booked product
        listProduct.forEach(e -> {
            JPanel infoPanel = new JPanel();
            JButton infoBtn = new JButton(
                    e.get(0) + "     ✿     " +
                            e.get(1) + "     ✿     " +
                            e.get(2));
            infoPanel.setBackground(Color.BLACK);
            infoBtn.setBackground(Color.BLACK);
            infoBtn.setForeground(Color.ORANGE);
            infoBtn.setBorder(new LineBorder(Color.BLACK));
            infoBtn.setMinimumSize(new Dimension(600, 100));
            infoBtn.setFont(new Font(Font.SERIF, Font.BOLD, 20));
            infoBtn.setOpaque(true);
            //after click the button, the canceled frame will be display
            infoBtn.addActionListener(e1 -> {
                JFrame amountFrame = new JFrame("Amount Frame");
                amountFrame.setSize(400, 100);
                amountFrame.setLayout(new FlowLayout());
                SpinnerModel spinnerModel = new SpinnerNumberModel(
                        0,
                        0,
                        SERVANT_CONTROLLER.getAmountSameProduct(codeTable, e.get(0)),
                        1);
                JPanel spinnerPanel = new JPanel();
                JSpinner spinner = new JSpinner(spinnerModel);
                JButton okBtn = new JButton("Ok");
                spinnerPanel.add(spinner, JPanel.CENTER_ALIGNMENT);
                spinnerPanel.setBackground(Color.BLACK);
                spinnerPanel.setOpaque(true);
                okBtn.setBackground(Color.GREEN);
                okBtn.setFont(new Font(Font.SERIF, Font.BOLD, 30));
                //confirm canceled button
                okBtn.addActionListener(e11 -> {
                    SERVANT_CONTROLLER.cancelProduct(
                            codeTable,
                            infoBtn.getText().substring(0, 10),
                            Integer.parseInt(CenterPanelView.getDataSpinnerAddComponent(spinner)));
                    amountFrame.dispose();
                    subFrame.dispose();
                    mainFrame.setVisible(true);
                });
                amountFrame.getContentPane().setBackground(Color.BLACK);
                amountFrame.add(spinnerPanel);
                amountFrame.add(okBtn);
                amountFrame.setVisible(true);
                amountFrame.setLocationRelativeTo(null);
            });
            infoPanel.add(infoBtn);
            panel.add(infoPanel);
        });
        //set scroll panel
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setPreferredSize(new Dimension(800, 700));
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        subFrame.add(scrollPane);
        subFrame.setVisible(true);
        subFrame.setLocationRelativeTo(null);
    }
}
