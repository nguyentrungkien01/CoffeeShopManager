package Interface;

import javax.swing.*;
import java.util.Vector;

/**
 * This interface contains all basic actions of data table
 */
public interface ManagerTable {
    void updateTable(JTable table);

    void setDataTable(String code, String col, boolean kindSort);

    void setTableGUI(JTable table);

    Vector<String> getNameColTable();
}
