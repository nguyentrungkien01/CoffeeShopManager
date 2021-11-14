package InfrastructureManagementMVC.View;

import CenterObjects.CenterPanelView;

import javax.swing.*;
import java.awt.*;

/**
 * {@code InfrastructureView} extends {@code CenterPanelView}
 * {@code InfrastructureView} is an abstract class which have all method to set GUI of {@code InfrastructureModel}
 */
public abstract class InfrastructureView extends CenterPanelView {
    protected JPanel panel=new JPanel();
    private final JLabel LABEL = new JLabel("TABLE INFORMATION", JLabel.LEFT);
    {
        LABEL.setBackground(Color.WHITE);
        LABEL.setForeground(Color.BLACK);
        LABEL.setFont(new Font(Font.SERIF, Font.BOLD, 40));
        LABEL.setOpaque(true);
        this.panel.add(this.LABEL);
        this.panel.setMaximumSize(new Dimension(700, 100));

    }
}
