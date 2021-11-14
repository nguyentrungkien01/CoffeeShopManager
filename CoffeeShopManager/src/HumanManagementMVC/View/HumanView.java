package HumanManagementMVC.View;

import CenterObjects.CenterPanelView;

import javax.swing.*;
import java.awt.*;

/**
 * {@code HumanView} extends {@code CenterPanelView}
 * {@code HumanView} is an abstract class which have all method to set GUI of {@code HumanModel}
 */
public abstract class HumanView extends CenterPanelView {
    protected JPanel panel=new JPanel();
    private final static JLabel LABEL = new JLabel("STAFF INFORMATION", JLabel.LEFT);
    {
        LABEL.setBackground(Color.WHITE);
        LABEL.setForeground(Color.BLACK);
        LABEL.setFont(new Font(Font.SERIF, Font.BOLD, 40));
        LABEL.setOpaque(true);
        this.panel.add(LABEL);
        this.panel.setMaximumSize(new Dimension(700, 100));
    }
}

