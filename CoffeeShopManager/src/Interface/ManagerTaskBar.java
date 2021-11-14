package Interface;

import javax.swing.*;

/**
 * This interface contains all basic actions of the task bar
 */
public interface ManagerTaskBar {
    void setTaskbarMenu(String code, JFrame mainFrame);

    void setMainMenuGUI(String code, JFrame mainFrame, JMenu menu);

    void setSortMenuGUI(String code, JFrame mainFrame, JMenu menu, boolean kindSort);

    void setSearchMenuGUI(String code, JFrame mainFrame, JMenu menu);

    void setAddMenuGUI(String code, JFrame mainFrame, JMenu menu);

    void setRemoveMenuGUI(String code, JFrame mainFramem, JMenu menu);
}
