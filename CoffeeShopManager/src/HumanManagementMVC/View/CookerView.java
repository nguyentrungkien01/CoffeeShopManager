package HumanManagementMVC.View;


import HumanManagementMVC.Controller.CookerController;

/**
 * <p>{@code CookerView} uses to set GUI for cooker staff</p>
 * <p>This class is a final class and extends the {@code BarCooView} class</p>
 */
public final class CookerView extends BarCooView {

    /**
     * set content for the title
     */
    private final static String LABEL_COOKER =
            "♡\uD83C\uDD55\uD83C\uDD5E\uD83C\uDD5E\uD83C\uDD53 \uD83C\uDD5B\uD83C\uDD58\uD83C\uDD62\uD83C\uDD63♡";

    /**
     * init view of cooker staff
     */
    public CookerView() {
        super(new CookerController().getAllFood(), LABEL_COOKER);
    }
}

