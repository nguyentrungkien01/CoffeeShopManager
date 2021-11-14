package HumanManagementMVC.EnumAndSubclass;


/**
 * <p>DayOff class provides for users kinds of day off</p>
 * <p><pre>
 *  kindOff attribute: HAFT or ALL
 *  isPermission: true if permission, false if not permission
 *  amount: amount of day off with kindOff and status
 * </pre></p>
 * <p>this class has only 1 constructor with parameters and has no constructor without parameter</p>
 * <p>All of attributes in this class was interacted by getter and setter methods</p>
 */
public class DayOff {

    /**
     * kind of day off
     */
    private KindOff kindOff;

    /**
     * state of day off
     */
    private boolean isPermission;

    /**
     * amount of day off
     */
    private int amount;

    /**
     * <p><pre>
     * Initializes a newly object with 3 params.
     * All of attributes in the new object will be assigned with the corresponding arguments
     * </pre></p>
     *
     * @param kindOff      original KindOff enum
     * @param isPermission original primary type -> boolean
     * @param amount       original primary type -> int
     */
    public DayOff(KindOff kindOff, boolean isPermission, int amount) {
        this.kindOff = kindOff;
        this.isPermission = isPermission;
        this.amount = amount;
    }

    /**
     * @return a {@code boolean} value represents for the state of day off
     * (permission or mot permission)
     */
    public boolean isPermission() {
        return isPermission;
    }

    /**
     * @param permission a {@code boolean} object to set permission for day off
     */
    public void setPermission(boolean permission) {
        this.isPermission = permission;
    }

    /**
     * @return a {@code KindOff} object represents for the kind of day off
     */
    public KindOff getKindOff() {
        return kindOff;
    }

    /**
     * @param amount a {@code int} value to set the amount of day off
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
     * @return a {@code int} value represents for the amount of the day off
     */
    public int getAmount() {
        return amount;
    }

    /**
     * @param kindOff a {@code KindOff} object to set kind of the day off
     */
    public void setKindOff(KindOff kindOff) {
        this.kindOff = kindOff;
    }
}
