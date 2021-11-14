package HumanManagementMVC.View;

import HumanManagementMVC.Controller.BartenderController;

/**
 * <p>{@code BartenderView} uses to set GUI for bartender staff</p>
 * <p>This class is a final class and extends the {@code BarCooView} class</p>
 */
public final class BartenderView extends BarCooView {

    /**
     * set content for the title
     */
    private final static String LABEL_BARTENDER =
            "♡\uD83C\uDD53\uD83C\uDD61\uD83C\uDD58\uD83C\uDD5D\uD83C\uDD5A \uD83C\uDD5B\uD83C\uDD58\uD83C\uDD62\uD83C\uDD63♡";

    /**
     * init view of bartender staff
     */
    public BartenderView() {
        super(new BartenderController().getAllDrink(), LABEL_BARTENDER);
    }
}

