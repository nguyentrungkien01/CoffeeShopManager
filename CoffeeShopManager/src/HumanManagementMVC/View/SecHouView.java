package HumanManagementMVC.View;

import javax.swing.*;
import java.awt.*;

/**
 * <p>{@code SecHouView} is an abstract class and extends {@code HumanView} class</p>
 * <p>{@code SecHouView} uses to set the common GUI for security staff and housekeeper staff</p>
 */
public abstract class SecHouView extends HumanView {

    /**
     * <p>This method use to set GUI for all attributes of this class</p>
     */
    public SecHouView() {
        super.panel.removeAll();
        final JLabel LABEL = new JLabel(
                "\uD835\uDCB8ℴ\uD835\uDCBB\uD835\uDCBBℯℯ \uD835\uDCC8\uD835\uDCBDℴ\uD835\uDCC5", JLabel.CENTER);
        JLabel logoLabel = new JLabel(new ImageIcon("src\\images\\coffee.gif"));
        JPanel panelTemp = new JPanel();
        JPanel panelTemp2 = new JPanel();

        LABEL.setFont(new Font(Font.SERIF, Font.BOLD, 100));
        LABEL.setForeground(Color.CYAN);
        panelTemp.setBackground(Color.BLACK);
        panelTemp2.setLayout(new FlowLayout());
        panelTemp2.setBackground(Color.BLACK);
        super.centerPanel.setBackground(Color.BLACK);
        super.panel.setLayout(new BoxLayout(this.panel, BoxLayout.Y_AXIS));

        panelTemp.add(LABEL);
        panelTemp2.add(new JLabel(""));
        panelTemp2.add(logoLabel);
        super.panel.add(panelTemp);
        super.panel.add(panelTemp2);
        super.centerPanel.add(super.panel);
    }
}
