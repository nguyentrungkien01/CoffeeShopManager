package ProductManagementMVC.View;

import CenterObjects.CenterPanelView;
import javax.swing.*;
import java.awt.*;

/**
 * {@code ProductView} extends {@code CenterPanelView}
 * {@code ProductView} is an abstract class which have all method to set GUI of {@code ProductView}
 */
public abstract class ProductView extends CenterPanelView {
    protected JPanel panel=new JPanel();
    private final JLabel LABEL = new JLabel("PRODUCT INFORMATION", JLabel.CENTER);
    {
        LABEL.setBackground(Color.WHITE);
        LABEL.setForeground(Color.BLACK);
        LABEL.setFont(new Font(Font.SERIF, Font.BOLD, 40));
        LABEL.setOpaque(true);
        this.panel.add(this.LABEL);
        this.panel.setMaximumSize(new Dimension(700, 100));

    }
}

