package LogInMVC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * {@code LogInView} class has methods will show GUI of log in action
 */
public class LogInView {

    /**
     * this attribute is the frame of the log in
     */
    private JFrame mainFrame;

    /**
     * this attribute contains components
     */
    private JPanel controlPanel;

    /**
     * this attribute is the log in button
     */
    private JButton logInBtn;

    /**
     * this attribute is the username text field
     */
    private final JTextField USERNAME_TEXT_FIELD = new JTextField(40);

    /**
     * this attribute is the password text field
     */
    private final JPasswordField PASSWORD_TEXT_FIELD = new JPasswordField(40);

    // create a object with initial GUI
    public LogInView() {
        prepareGUI();
    }

    /**
     * prepare initial GUI of log in screen
     * The GUi has 3 part: header (headerLabel), main(controlPanel) and footer(statysLabel, copyrightLabel)
     */
    public void prepareGUI() {
        //mainframe
        this.mainFrame = new JFrame("Coffee Shop");
        this.mainFrame.setSize(700, 500);
        this.mainFrame.getContentPane().setBackground(new Color(79, 148, 205));
        this.mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                mainFrame.dispose();
            }
        });
        mainFrame.setLayout(new GridLayout(3, 1));

        //logo header
        JLabel headerLabel = new JLabel(new ImageIcon("src\\images\\logo.png"), JLabel.CENTER);

        //status
        //component of footer panel
        JLabel statusLabel = new JLabel("Welcome to Coffee shop", JLabel.CENTER);
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD | Font.ITALIC | Font.PLAIN, 40));

        //copyright
        JLabel copyRightLabel = new JLabel("1951052091 - Nguyễn Trung Kiên - OU - 2020", JLabel.CENTER);
        copyRightLabel.setFont(new Font(Font.SERIF, Font.BOLD, 18));
        copyRightLabel.setForeground(Color.RED);
        copyRightLabel.setBackground(Color.BLACK);
        copyRightLabel.setOpaque(true);

        //footer panel
        JPanel footerPanel = new JPanel();
        footerPanel.setLayout(new GridLayout(2, 1));
        footerPanel.setBackground(new Color(79, 148, 205));
        footerPanel.add(statusLabel);
        footerPanel.add(copyRightLabel);

        //add components
        this.controlPanel = new JPanel();
        this.controlPanel.setLayout(new BoxLayout(this.controlPanel, BoxLayout.Y_AXIS));
        this.controlPanel.setOpaque(true);
        this.controlPanel.setBackground(Color.WHITE);
        this.controlPanel.setSize(300, 400);
        this.mainFrame.add(headerLabel);
        this.mainFrame.add(this.controlPanel);
        this.mainFrame.add(footerPanel);
        this.mainFrame.setVisible(true);
        this.mainFrame.setLocationRelativeTo(null);
    }

    /**
     * <p>This method uses to set fail box when the input data is invalid</p>
     *
     * @param mainFrame a {@code JFrame} object represents for the log in frame
     */
    private void setFailBoxGUI(JFrame mainFrame) {
        mainFrame.setVisible(false);
        JFrame subFrame = new JFrame("Notification");
        subFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                subFrame.dispose();
                mainFrame.setVisible(true);
            }
        });
        JLabel subLabel1 =
                new JLabel("Your account is incorrect or not exist.", JLabel.CENTER);
        JLabel subLabel2 =
                new JLabel("Please try again!", JLabel.CENTER);
        subLabel1.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 13));
        subLabel1.setForeground(Color.RED);
        subLabel1.setOpaque(true);
        subLabel2.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 13));
        subLabel2.setForeground(Color.RED);
        subLabel2.setOpaque(true);

        subFrame.setLayout(new GridLayout(3, 1));
        subFrame.setSize(400, 100);
        subFrame.add(subLabel1);
        subFrame.add(subLabel2);
        subFrame.setVisible(true);
        subFrame.setLocationRelativeTo(null);

    }

    /**
     * This method have a mission is set up position, color, size, font, default values
     * of the components and show log in GUI
     */
    public void setLogInGUI() {
        //login area
        JPanel panel = new JPanel();
        GridBagLayout layOut = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        panel.setLayout(layOut);

        gbc.fill = GridBagConstraints.HORIZONTAL;//line 1
        //user name label position
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Username: "), gbc);
        //username text field position
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(this.USERNAME_TEXT_FIELD, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;//line 2
        //password label position
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Password: "), gbc);
        //password text field position
        gbc.gridx = 1;
        gbc.gridy = 2;

        panel.add(this.PASSWORD_TEXT_FIELD, gbc);

        //button
        this.logInBtn = new JButton("Log in");
        this.logInBtn.setSize(50, 50);
        this.logInBtn.setBackground(Color.GREEN);
        this.logInBtn.setForeground(Color.BLACK);
        this.logInBtn.setFont(new Font(Font.DIALOG, Font.BOLD, 18));
        //set position for login button
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        gbc.gridy = 7;
        panel.add(this.logInBtn, gbc);

        this.controlPanel.add(panel);
        this.mainFrame.setVisible(true);

    }

    /**
     * <p>this method check when login button was clicked by user
     * then check the data which was enter by user by check() method of
     * the LogInController Object. if the data is the same with the data in
     * database we will do next action if not a dialog will appears and tell you know
     * data you just have entered is incorrect.</p>
     *
     * @param logInController one object has mission check data which just have been entered by user
     */
    public void setCheckAction(LogInController logInController) {
        this.logInBtn.addActionListener(e -> {
            boolean check = logInController.isCorrect(
                    USERNAME_TEXT_FIELD.getText(),
                    new String(PASSWORD_TEXT_FIELD.getPassword()));
            if (!check)
                setFailBoxGUI(mainFrame);
            else {
                mainFrame.dispose();
                new MainActionFrame(logInController.getCodeFromDB(
                        USERNAME_TEXT_FIELD.getText()),
                        logInController.getURLFromDB(USERNAME_TEXT_FIELD.getText()));
            }
        });
    }
}
