package CenterObjects;

import javax.swing.*;
import java.awt.*;

/**
 * <p>{@code MenuBar} class specifies how many items will be displayed </p>
 * <p>{@code MenuBar} class has only constructor with any parameter</p>
 * <p>All attributes in this class were declared private</p>
 * <p>{@code MenuBar} don't have any method except <code>getter</code>
 * and <code>setter</code></p>
 */
public final class MenuBar {

    /**
     * declare an JMenuBar object to hold all of JMenu object in menu bar
     */
    private JMenuBar menuBar = new JMenuBar();

    /**
     * The mainMenu is a submenu of the menuBar
     * use to add main item
     */
    private JMenu mainMenu = new JMenu("Menu");

    /**
     * The sortMenu is a item of the menuBar
     * use to store all kinds of sort (up or down)
     */
    private JMenu sortMenu = new JMenu("Sort");

    /**
     * The sortUpMenu is a submenu of the sortMenu
     * use to add all kinds of sort up item
     */
    private JMenu sortUpMenu = new JMenu("Sort Up");

    /**
     * The sortDownMenu is a sub-menu of the sortMenu
     * use to add all kinds of sort up item
     */
    private JMenu sortDownMenu = new JMenu("Sort Down");

    /**
     * The searchMenu is a item of the menuBar
     * use to store all kinds of search
     */
    private JMenu searchMenu = new JMenu("Search");

    /**
     * The addMenu is a item of the menuBar
     * use to store all kinds of add
     */
    private JMenu addMenu = new JMenu("Add");

    /**
     * The RemoveMenu is a item of the menuBar
     * use to store all kinds of remove
     */
    private JMenu removeMenu = new JMenu("Remove");

    /**
     * The constructor of the MenuBar class. This constructor has any parameter
     * In this constructor set attributes for the menuBar and all menu items
     * and add all menu items to the menuBar
     */
    public MenuBar() {

        /*
         * set layout for the menuBar.
         * In the menuBar, there have 5 menus, that is the reason why using grid lay out
         * with 1 row and 5 columns to set lay out for menuBar
         */
        getMenuBar().setLayout(new GridLayout(1, 5));

        /*
         * Set font for all menu in the menuBar
         */
        getMainMenu().setFont(new Font(Font.SERIF, Font.BOLD, 25));
        getSortMenu().setFont(new Font(Font.SERIF, Font.BOLD, 25));
        getSearchMenu().setFont(new Font(Font.SERIF, Font.BOLD, 25));
        getAddMenu().setFont(new Font(Font.SERIF, Font.BOLD, 25));
        getRemoveMenu().setFont(new Font(Font.SERIF, Font.BOLD, 25));
        getMainMenu().setOpaque(true);
        getSortMenu().setOpaque(true);
        getSearchMenu().setOpaque(true);
        getAddMenu().setOpaque(true);
        getRemoveMenu().setOpaque(true);

        /*
         * add all menus to the menuBar
         */
        getSortMenu().add(getSortUpMenu());
        getSortMenu().add(getSortDownMenu());
        getMenuBar().add(getMainMenu());
        getMenuBar().add(getSortMenu());
        getMenuBar().add(getSearchMenu());
        getMenuBar().add(getAddMenu());
        getMenuBar().add(getRemoveMenu());
        getMenuBar().setMaximumSize(new Dimension(1200, 100));//set size for the menuBar
    }

    /**
     * @return the menuBar
     */
    public JMenuBar getMenuBar() {
        return menuBar;
    }

    /**
     * @param menuBar a JMenuBar object to assign with menuBar of this class
     */
    public void setMenuBar(JMenuBar menuBar) {
        this.menuBar = menuBar;
    }

    /**
     * @return the mainMenu
     */
    public JMenu getMainMenu() {
        return mainMenu;
    }

    /**
     * @param mainMenu a JMenu object to assign with the mainMenu of this class
     */
    public void setMainMenu(JMenu mainMenu) {
        this.mainMenu = mainMenu;
    }

    /**
     * @return the sortMenu
     */
    public JMenu getSortMenu() {
        return sortMenu;
    }

    /**
     * @param sortMenu a JMenu object to assign with the sortMenu of this class
     */
    public void setSortMenu(JMenu sortMenu) {
        this.sortMenu = sortMenu;
    }

    /**
     * @return the sortUpMenu
     */
    public JMenu getSortUpMenu() {
        return sortUpMenu;
    }

    /**
     * @param sortUpMenu a JMenu object to assign with the sortUpMenu of this class
     */
    public void setSortUpMenu(JMenu sortUpMenu) {
        this.sortUpMenu = sortUpMenu;
    }

    /**
     * @return the sortDownMenu
     */
    public JMenu getSortDownMenu() {
        return sortDownMenu;
    }

    /**
     * @param sortDownMenu a JMenu object to assign with the sortDownMenu of this class
     */
    public void setSortDownMenu(JMenu sortDownMenu) {
        this.sortDownMenu = sortDownMenu;
    }

    /**
     * @return the searchMenu
     */
    public JMenu getSearchMenu() {
        return searchMenu;
    }

    /**
     * @param searchMenu a JMenu object to assign with the searchMenu of this class
     */
    public void setSearchMenu(JMenu searchMenu) {
        this.searchMenu = searchMenu;
    }

    /**
     * @return the addMenu
     */
    public JMenu getAddMenu() {
        return addMenu;
    }

    /**
     * @param addMenu a JMenu object to assign with the addMenu of this class
     */
    public void setAddMenu(JMenu addMenu) {
        this.addMenu = addMenu;
    }

    /**
     * @return the removeMenu
     */
    public JMenu getRemoveMenu() {
        return removeMenu;
    }

    /**
     * @param removeMenu a JMenu object to assign with the removeMenu of this class
     */
    public void setRemoveMenu(JMenu removeMenu) {
        this.removeMenu = removeMenu;
    }
}
