
import LogInMVC.LogInController;
import LogInMVC.LogInView;

public class Demo {
    public static void main(String[] args) {
        LogInView logInView = new LogInView();
        logInView.setLogInGUI();
        logInView.setCheckAction(new LogInController());
    }
}
