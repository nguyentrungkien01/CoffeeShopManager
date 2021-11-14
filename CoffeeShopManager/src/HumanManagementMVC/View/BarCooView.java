package HumanManagementMVC.View;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

/**
 * <p>{@code BarCooView} is an abstract class and extends {@code HumanView} class</p>
 * <p>{@code BarCooView} uses to set the common GUI for bartender staff and cooker staff</p>
 */
public abstract class BarCooView extends HumanView {

    /**
     * <p>this attribute uses to contain list of product to display</p>
     * <p>With bartender staff, assign this attribute with list of drink </p>
     * <p>With cooker staff, assign this attribute with list of food</p>
     */
    private final Vector<Vector<String>> LIST_PRODUCT;

    /**
     * <p>this attribute uses to contain the title to display</p>
     * <p>With bartender staff, assign this attribute with title of drink </p>
     * <p>With cooker staff, assign this attribute with title of food</p>
     */
    private final JLabel LABEL_BC = new JLabel("", JLabel.CENTER);

    /**
     * <p>This constructor uses to init an {@code BarCooView}
     * object with specific values</p>]
     * <p>This constructor invoke the <code>setGUI</code> method
     * to set GUI for all attribute </p>
     *
     * @param listProduct a {@code Vector<Vector<String>>} object represents for the list of product
     *
     * @param label a {@code String} object represents for the title
     */
    public BarCooView(Vector<Vector<String>> listProduct, String label) {
        this.LIST_PRODUCT = listProduct;
        this.LABEL_BC.setText(label);
        this.setGUI();
    }

    /**
     * <p>This method use to set GUI for all attributes of this class</p>
     */
    private void setGUI() {
        //set GUI title
        this.LABEL_BC.setFont(new Font(Font.SERIF, Font.BOLD, 100));
        this.LABEL_BC.setBackground(Color.BLACK);
        this.LABEL_BC.setForeground(Color.CYAN);
        this.LABEL_BC.setOpaque(true);
        //set layout to display information of list of products
        this.centerPanel.setLayout(new BorderLayout());
        //add title to panel
        this.centerPanel.add(LABEL_BC, BorderLayout.NORTH);
        JPanel infoPanel= new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Color.BLACK);
        JScrollPane scrollPane= new JScrollPane(infoPanel);
        scrollPane.setBackground(Color.BLACK);
        scrollPane.setPreferredSize(new Dimension(900, 700));
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        /*
        set GUI for list product
                with list product:
                    +) index 0: code table
                    +) index 1: code product
                    +) index 2: name product
                    +) index 4: amount product
        */
        String[] strTemp = new String[1];
        strTemp[0] = " ";
        LIST_PRODUCT.forEach(strings -> {
            // Because each item in the list of  product has code table,
            // so this condition uses to avoid repeating the code table
            // -> each code table just shows list of products of its table
            if (!strings.get(0).equalsIgnoreCase(strTemp[0])) {
                // set GUI for the code of table
                JLabel nameTable = new JLabel(strings.get(0), JLabel.LEFT);
                strTemp[0] = strings.get(0);
                nameTable.setMinimumSize(new Dimension(900,150));
                nameTable.setForeground(Color.GREEN);
                nameTable.setBackground(Color.BLACK);
                nameTable.setFont(new Font(Font.SERIF, Font.BOLD, 40));
                nameTable.setOpaque(true);
                nameTable.setAlignmentX(Component.CENTER_ALIGNMENT);
                infoPanel.add(nameTable);
            }
            //set GUI for information of each product
            JLabel infoLabel = new JLabel(
                    strings.get(1) + "     ✿     " +
                            strings.get(2) + "     ✿     " +
                            strings.get(3), JLabel.CENTER);
            infoLabel.setMinimumSize(new Dimension(900, 100));
            infoLabel.setBackground(Color.BLACK);
            infoLabel.setForeground(Color.ORANGE);
            infoLabel.setFont(new Font(Font.SERIF, Font.BOLD, 30));
            infoLabel.setOpaque(true);
            infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            infoPanel.add(infoLabel);
        });
        this.centerPanel.add(scrollPane);
    }
}
