package LogInMVC;

import HumanManagementMVC.Controller.ManagerController;
import HumanManagementMVC.EnumAndSubclass.Part;
import HumanManagementMVC.View.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.Vector;

/**
 * {@code MainActionFrame} class contains set GUi for main actions of all staff
 */
public class MainActionFrame {
    /**
     * the attribute of the main frame
     */
    private JFrame mainFrame;

    /**
     * the attribute of the right panel
     */
    private JPanel rightPanel;

    /**
     * the attribute of the center panel
     */
    private JPanel centerPanel;

    /**
     * <p>This method uses to set GUI for the {@code MainActionFrame}</p>
     *
     * @param code a {@code String} object represents for the code of the staff
     * @param url  a {@code String} object represents for the url of the staff
     */
    public MainActionFrame(String code, String url) {
        this.prepareGUI(code);
        this.setAvatarGUI(code, url);
        this.setInfoGUI(code);
        this.setActionGUI(code);
        this.mainFrame.setVisible(true);
    }

    /**
     * <p>This method uses to check the part of the staff and  create corresponding view</p>
     *
     * @param code a {@code String} object represents for the code of the staff
     */
    private void setCenterPanel(String code) {
        for (Part part : Part.values())
            if (part.toString().equals(code.substring(0, code.indexOf("0")))) {
                HumanView objectView = (HumanView) part.getObjectView(code, this.mainFrame);
                this.centerPanel.add(objectView.getPanel());
            }
    }

    /**
     * <p>This method uses to prepare general GUI</p>
     *
     * @param code a {@code String} object represents for the code of the staff
     */
    public void prepareGUI(String code) {
        this.mainFrame = new JFrame("Coffee Shop");
        this.mainFrame.setSize(1500, 850);
        this.mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                mainFrame.dispose();
                LogInView logInView = new LogInView();
                logInView.setLogInGUI();
                logInView.setCheckAction(new LogInController());
            }
        });

        this.centerPanel = new JPanel();
        this.centerPanel.setLayout(new BoxLayout(this.centerPanel, BoxLayout.Y_AXIS));

        this.rightPanel = new JPanel();
        this.rightPanel.setBackground(Color.RED);
        this.rightPanel.setBackground(Color.BLACK);
        this.rightPanel.setLayout(new GridLayout(3, 1));

        JLabel copyRightLabel = new JLabel("1951052091 - Nguyễn Trung Kiên - OU - 2020", JLabel.CENTER);
        copyRightLabel.setForeground(Color.RED);
        copyRightLabel.setBackground(Color.BLACK);
        copyRightLabel.setOpaque(true);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.BLACK);
        bottomPanel.add(copyRightLabel);

        this.setCenterPanel(code);

        this.mainFrame.add(centerPanel, BorderLayout.CENTER);
        this.mainFrame.add(rightPanel, BorderLayout.EAST);
        this.mainFrame.add(bottomPanel, BorderLayout.SOUTH);
        this.mainFrame.setVisible(true);
        this.mainFrame.setLocationRelativeTo(null);
    }

    /**
     * <p>This method uses to set avatar of each staff</p>
     *
     * @param code    a {@code String} object represents for the code of the staff
     * @param pathAva a {@code String} object represents for the url of the avatar
     */
    private void setAvatarGUI(String code, String pathAva) {
        final JPanel[] AVATAR_PANEL = {new JPanel()};
        AVATAR_PANEL[0].setLayout(new BoxLayout(AVATAR_PANEL[0], BoxLayout.Y_AXIS));
        AVATAR_PANEL[0].setBackground(Color.BLACK);
        JFileChooser fileChooser = new JFileChooser();
        JButton fileBtn = new JButton("Choose File");
        JPanel fileBtnPanel = new JPanel();
        fileBtnPanel.setBackground(Color.BLACK);
        //set size for the image
        final Image[] IMAGE = new Image[]{new ImageIcon(pathAva).getImage().getScaledInstance(
                new ManagerController().getName(code).length() * 14 - 5, 200, Image.SCALE_SMOOTH)};
        //add image to label
        final JLabel[] AVATAR = {new JLabel(new ImageIcon(IMAGE[0]))};
        //action after click file button
        fileBtn.addActionListener(e -> {
            //show dialog to choose file
            int result = fileChooser.showOpenDialog(AVATAR_PANEL[0]);
            //check if the user select the file
            if (result == JFileChooser.APPROVE_OPTION) {
                //get the selected file
                File file = fileChooser.getSelectedFile();
                //set size for the image
                IMAGE[0] = new ImageIcon(file.getPath()).getImage().getScaledInstance(
                        new ManagerController().getName(code).length() * 14 - 5, 200, Image.SCALE_SMOOTH);
                //update new url of the avatar
                new LogInController().updateURL(code,
                        file.getPath().substring(file.getPath().lastIndexOf("src")).
                                replaceAll("\\\\", "\\\\\\\\"));
                //reset avatar
                AVATAR[0] = new JLabel(new ImageIcon(IMAGE[0]));
                fileBtnPanel.removeAll();
                fileBtnPanel.add(fileBtn);
                AVATAR_PANEL[0].removeAll();
                AVATAR_PANEL[0].add(AVATAR[0]);
                AVATAR_PANEL[0].add(fileBtnPanel);
                mainFrame.setVisible(true);
            } else
                try {
                    throw new Exception("Not open file");
                } catch (Exception ignored) {
                }

        });
        fileBtnPanel.add(fileBtn);
        AVATAR_PANEL[0].add(AVATAR[0]);
        AVATAR_PANEL[0].add(fileBtnPanel);

        this.rightPanel.add(AVATAR_PANEL[0]);
        mainFrame.setVisible(true);
    }

    /**
     * <p>This method uses tor set GUI for the information of each staff</p>
     * <p>The information get from database</p>
     *
     * @param code a {@code String} object represents for the code of the staff
     */
    private void setInfoGUI(String code) {
        ManagerController managerController = new ManagerController();
        JPanel mainPanel = new JPanel();
        JPanel infoPanel = new JPanel();
        JLabel birthLabel = new JLabel("", JLabel.CENTER);
        JLabel sexLabel = new JLabel("", JLabel.CENTER);
        JLabel partLabel = new JLabel("", JLabel.CENTER);
        JLabel codeLabel = new JLabel("", JLabel.CENTER);
        Vector<Vector<String>> infoData = managerController.searchKey(code);
        JLabel infoTitleLabel = new JLabel("PROFILE", JLabel.CENTER);
        JLabel nameLabel = new JLabel("", JLabel.CENTER);

        mainPanel.setBackground(Color.BLACK);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

        infoTitleLabel.setMaximumSize(new Dimension(this.rightPanel.getWidth() + 50, 100));
        infoTitleLabel.setForeground(new Color(0, 191, 255));
        infoTitleLabel.setBackground(Color.BLACK);
        infoTitleLabel.setOpaque(true);
        infoTitleLabel.setFont(new Font(Font.SERIF, Font.BOLD | Font.ITALIC, 60));

        nameLabel.setText(infoData.get(0).get(2).toUpperCase());
        birthLabel.setText("Date of birth: " + infoData.get(0).get(3));
        sexLabel.setText("Sex:  " + infoData.get(0).get(9).toLowerCase());
        partLabel.setText("Part:  " + infoData.get(0).get(11));
        codeLabel.setText(code);

        nameLabel.setFont(new Font(Font.SERIF, Font.BOLD, 30));
        nameLabel.setForeground(Color.BLACK);
        nameLabel.setOpaque(true);
        nameLabel.setBackground(Color.WHITE);

        birthLabel.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
        birthLabel.setMaximumSize(new Dimension(400, 50));

        birthLabel.setForeground(Color.BLACK);
        birthLabel.setOpaque(true);
        birthLabel.setBackground(Color.lightGray);

        sexLabel.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
        sexLabel.setMaximumSize(new Dimension(400, 50));

        sexLabel.setForeground(Color.BLACK);
        sexLabel.setOpaque(true);
        sexLabel.setBackground(Color.lightGray);

        partLabel.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
        partLabel.setMaximumSize(new Dimension(400, 50));
        partLabel.setForeground(Color.BLACK);
        partLabel.setOpaque(true);
        partLabel.setBackground(Color.lightGray);


        codeLabel.setFont(new Font(Font.SERIF, Font.BOLD, 30));
        codeLabel.setMaximumSize(new Dimension(400, 50));
        codeLabel.setForeground(new Color(84, 255, 159));
        codeLabel.setOpaque(true);
        codeLabel.setBackground(Color.BLACK);

        infoPanel.add(nameLabel);
        infoPanel.add(birthLabel);
        infoPanel.add(sexLabel);
        infoPanel.add(partLabel);

        mainPanel.add(infoTitleLabel);
        mainPanel.add(infoPanel);
        mainPanel.add(codeLabel);
        this.rightPanel.add(mainPanel);

    }

    /**
     * <p>This method uses to set GUI for the basic action of each staff</p>
     *
     * @param code a {@code String} represents for the code of the staff
     */
    private void setActionGUI(String code) {
        ManagerController managerController = new ManagerController();
        JPanel generalPanel = new JPanel(new GridLayout(2, 1));
        JPanel parentActionPanel = new JPanel();
        JPanel actionPanel = new JPanel();
        JButton absentBtn = new JButton("Absent");
        JButton showOverTimeBtn = new JButton("Over Time");
        JButton showBasicSalaryBtn = new JButton("Basic Salary");
        JButton changeAccountBtn = new JButton("Change Account");
        Image image = new ImageIcon("src\\images\\logo.png").
                getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        JLabel logo = new JLabel(new ImageIcon(image));
        logo.setMaximumSize(new Dimension(400, 150));

        generalPanel.setBackground(Color.BLACK);

        parentActionPanel.setSize(300, 200);
        parentActionPanel.setAlignmentX(-150);
        parentActionPanel.setBackground(Color.BLACK);

        actionPanel.setLayout(new GridLayout(2, 2, 3, 3));
        actionPanel.setSize(new Dimension(200, 200));
        actionPanel.setBackground(Color.BLACK);


        absentBtn.setSize(100, 200);
        showOverTimeBtn.setSize(100, 200);
        showBasicSalaryBtn.setSize(100, 200);
        changeAccountBtn.setSize(100, 200);

        absentBtn.setFont(new Font(Font.SERIF, Font.BOLD, 16));
        showOverTimeBtn.setFont(new Font(Font.SERIF, Font.BOLD, 16));
        showBasicSalaryBtn.setFont(new Font(Font.SERIF, Font.BOLD, 16));
        changeAccountBtn.setFont(new Font(Font.SERIF, Font.BOLD, 16));

        absentBtn.setMaximumSize(new Dimension(100, 200));
        showOverTimeBtn.setMaximumSize(new Dimension(100, 200));
        showBasicSalaryBtn.setMaximumSize(new Dimension(100, 200));
        changeAccountBtn.setMaximumSize(new Dimension(100, 200));

        absentBtn.addActionListener(e -> {
            JFrame subFrame = new JFrame("Absent Window");
            subFrame.setSize(600, 400);
            subFrame.getContentPane().setBackground(Color.BLACK);
            subFrame.setLayout(new GridLayout(2, 2, 20, 20));
            subFrame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    subFrame.dispose();
                }
            });
            JButton haftPermissBtn = new JButton("Haft-Day (Permission)");
            JButton haftNonPermissBtn = new JButton("Haft-Day (Non-Permission)");
            JButton allPermissBtn = new JButton("All-Day (Permission)");
            JButton allNonPermissBtn = new JButton("All-Day (Non-Permission)");

            haftNonPermissBtn.setBackground(new Color(255, 64, 64));
            haftPermissBtn.setBackground(new Color(151, 255, 255));
            allNonPermissBtn.setBackground(new Color(255, 64, 64));
            allPermissBtn.setBackground(new Color(151, 255, 255));

            haftNonPermissBtn.setFont(new Font(Font.SERIF, Font.BOLD, 20));
            haftPermissBtn.setFont(new Font(Font.SERIF, Font.BOLD, 20));
            allNonPermissBtn.setFont(new Font(Font.SERIF, Font.BOLD, 20));
            allPermissBtn.setFont(new Font(Font.SERIF, Font.BOLD, 20));

            haftPermissBtn.addActionListener(e1 -> {
                managerController.updateHaftPermiss(code);
                subFrame.dispose();
            });
            haftNonPermissBtn.addActionListener(e12 -> {
                managerController.updateHaftNonPermiss(code);
                subFrame.dispose();
            });
            allPermissBtn.addActionListener(e13 -> {
                managerController.updateAllPermiss(code);
                subFrame.dispose();
            });
            allNonPermissBtn.addActionListener(e14 -> {
                managerController.updateAllNonPermiss(code);
                subFrame.dispose();
            });

            subFrame.add(haftPermissBtn);
            subFrame.add(allPermissBtn);
            subFrame.add(haftNonPermissBtn);
            subFrame.add(allNonPermissBtn);
            subFrame.setVisible(true);
            subFrame.setLocationRelativeTo(null);
        });
        showOverTimeBtn.addActionListener(e -> {
            JFrame subFrame = new JFrame("Over Time");
            subFrame.setLayout(new GridLayout(2, 1, 20, 20));
            subFrame.getContentPane().setBackground(Color.BLACK);
            subFrame.setSize(600, 400);
            subFrame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    subFrame.dispose();
                }
            });

            JLabel notification = new JLabel("Total overtime ", JLabel.CENTER);
            notification.setFont(new Font(Font.SERIF, Font.BOLD, 50));
            notification.setBackground(Color.WHITE);
            notification.setForeground(Color.RED);
            notification.setOpaque(true);
            JLabel result = new JLabel(String.valueOf(managerController.getOvertime(code)), JLabel.CENTER);
            result.setFont(new Font(Font.SERIF, Font.BOLD, 30));
            result.setBackground(Color.WHITE);
            result.setForeground(Color.BLACK);
            result.setOpaque(true);


            subFrame.add(notification);
            subFrame.add(result);
            subFrame.setVisible(true);
            subFrame.setLocationRelativeTo(null);
        });
        showBasicSalaryBtn.addActionListener(e -> {
            JFrame subFrame = new JFrame("Basic Salary");
            subFrame.setLayout(new GridLayout(2, 1, 20, 20));
            subFrame.getContentPane().setBackground(Color.BLACK);
            subFrame.setSize(600, 400);
            subFrame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    subFrame.dispose();
                }
            });

            JLabel notification = new JLabel("Basic Salary ", JLabel.CENTER);
            notification.setFont(new Font(Font.SERIF, Font.BOLD, 50));
            notification.setBackground(Color.WHITE);
            notification.setForeground(Color.RED);
            notification.setOpaque(true);
            JLabel result = new JLabel(String.format("%,.2f", managerController.getBasicSalary(code)) + " VNĐ", JLabel.CENTER);
            result.setFont(new Font(Font.SERIF, Font.BOLD, 30));
            result.setBackground(Color.WHITE);
            result.setForeground(Color.BLACK);
            result.setOpaque(true);


            subFrame.add(notification);
            subFrame.add(result);
            subFrame.setVisible(true);
            subFrame.setLocationRelativeTo(null);
        });
        changeAccountBtn.addActionListener(e -> {
            JFrame subFrame = new JFrame("Change Account");
            subFrame.getContentPane().setBackground(Color.BLACK);
            subFrame.setSize(600, 400);
            subFrame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    subFrame.dispose();
                }
            });

            JPanel panel = new JPanel();
            JLabel username = new JLabel("Username: ", JLabel.LEFT);
            JLabel password = new JLabel("Password: ", JLabel.LEFT);
            JLabel newPassword = new JLabel("New password: ", JLabel.LEFT);
            JLabel confirmPassword = new JLabel("Confirm new password: ", JLabel.LEFT);
            JTextField usernameField = new JTextField();
            JPasswordField passwordField = new JPasswordField();
            JPasswordField newPasswordField = new JPasswordField();
            JPasswordField confirmNewPasswordField = new JPasswordField();
            JButton confirmBtn = new JButton("Confirm");

            panel.setLayout(new GridLayout(4, 2, 0, 50));
            panel.setBackground(Color.BLACK);
            panel.setOpaque(true);

            username.setForeground(Color.WHITE);
            username.setBackground(Color.BLACK);
            username.setOpaque(true);
            username.setFont(new Font(Font.SERIF, Font.BOLD, 22));
            password.setForeground(Color.WHITE);
            password.setBackground(Color.BLACK);
            password.setOpaque(true);
            password.setFont(new Font(Font.SERIF, Font.BOLD, 22));
            newPassword.setForeground(Color.WHITE);
            newPassword.setBackground(Color.BLACK);
            newPassword.setOpaque(true);
            newPassword.setFont(new Font(Font.SERIF, Font.BOLD, 22));
            confirmPassword.setForeground(Color.WHITE);
            confirmPassword.setBackground(Color.BLACK);
            confirmPassword.setOpaque(true);
            confirmPassword.setFont(new Font(Font.SERIF, Font.BOLD, 22));
            confirmBtn.setBackground(Color.GREEN);
            confirmBtn.setForeground(Color.BLACK);
            confirmBtn.setOpaque(true);
            confirmBtn.setFont(new Font(Font.SERIF, Font.BOLD, 30));

            usernameField.setFont(new Font(Font.SERIF, Font.BOLD, 20));
            passwordField.setFont(new Font(Font.SERIF, Font.BOLD, 20));
            newPasswordField.setFont(new Font(Font.SERIF, Font.BOLD, 20));
            confirmNewPasswordField.setFont(new Font(Font.SERIF, Font.BOLD, 20));

            confirmBtn.addActionListener(e15 -> {
                boolean check = new LogInController().checkAccount(
                        code, usernameField.getText(), new String(passwordField.getPassword()));
                if (check && new String(newPasswordField.getPassword()).equals(
                        new String(confirmNewPasswordField.getPassword()))) {
                    new LogInController().updateAccount(code, new String(confirmNewPasswordField.getPassword()));
                    subFrame.dispose();
                } else {
                    JFrame errorFrame = new JFrame("Error");
                    JLabel notification = new JLabel("Your data is incorrect!", JLabel.CENTER);
                    errorFrame.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosing(WindowEvent e15) {
                            errorFrame.dispose();
                        }
                    });
                    errorFrame.setSize(300, 100);
                    notification.setFont(new Font(Font.SERIF, Font.BOLD, 20));
                    notification.setForeground(Color.RED);
                    errorFrame.add(notification);
                    errorFrame.setVisible(true);
                    errorFrame.setLocationRelativeTo(null);
                }

            });

            panel.add(username);
            panel.add(usernameField);
            panel.add(password);
            panel.add(passwordField);
            panel.add(newPassword);
            panel.add(newPasswordField);
            panel.add(confirmPassword);
            panel.add(confirmNewPasswordField);

            subFrame.add(panel, BorderLayout.CENTER);
            subFrame.add(confirmBtn, BorderLayout.SOUTH);
            subFrame.setVisible(true);
            subFrame.setLocationRelativeTo(null);
        });

        actionPanel.add(absentBtn);
        actionPanel.add(showOverTimeBtn);
        actionPanel.add(showBasicSalaryBtn);
        actionPanel.add(changeAccountBtn);
        parentActionPanel.add(actionPanel);


        generalPanel.add(parentActionPanel);
        generalPanel.add(logo);
        this.rightPanel.add(generalPanel);
    }
}
