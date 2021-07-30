package br.ufrrj.samu.views;

import br.ufrrj.samu.RoundedCornerBorder;
import br.ufrrj.samu.SAMU;
import br.ufrrj.samu.controllers.LoginController;
import br.ufrrj.samu.controllers.LoginController.LoginStatus;
import br.ufrrj.samu.services.StudentService;
import br.ufrrj.samu.utils.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static br.ufrrj.samu.utils.Util.centreWindow;
import static java.util.Objects.requireNonNull;

public class LoginFrame extends JFrame {

    private static final Logger LOGGER = LogManager.getLogger(LoginFrame.class);
    private static final int FIELDS_CORNER_RADIUS = 4;

    JPanel mainJPanel;
    JPanel loginJPanel;
    JLabel usernameJLabel;
    JLabel passwordJLabel;
    JTextField usernameTextField;
    JPasswordField passwordField;
    JButton signupJButton;
    JButton signinJButton;

    private LoginController loginController;

    private String frameTitle = "SAMU - Sistema de Aux\u00EDlio a Matr\u00EDcula Universit\u00E1ria";
    private int width = 450+10;
    private int height = 500+30;

    public LoginFrame(LoginController loginController, SAMU samu) {
        super();
        frameInit();
        this.loginController = loginController;
        mainJPanel = new JPanel();
        mainJPanel.setLayout(new GridBagLayout());
//        mainJPanel.setBorder(BorderFactory.createLineBorder(Color.RED));

        loginJPanel = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                if (!isOpaque() && getBorder() instanceof RoundedCornerBorder) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setPaint(getBackground());
                    g2.fill(((RoundedCornerBorder) getBorder()).getBorderShape(
                            0, 0, getWidth() - 1, getHeight() - 1));
                    g2.dispose();
                }
                super.paintComponent(g);
            }
            @Override public void updateUI() {
                super.updateUI();
                setOpaque(false);
                setBorder(new RoundedCornerBorder(8, UIManager.getColor("Button.startBackground")));
            }
        };
        loginJPanel.setPreferredSize(new Dimension(width - 60, height - 70));
        loginJPanel.setLayout(new GridBagLayout());
        GridBagConstraints gridConstraints = new GridBagConstraints();
        loginJPanel.setOpaque(false);

        String usernameLabel = "Usu\u00E1rio";
        String passwordLabel = "Senha";
        String empty = "\u200C";

        usernameTextField = new JTextField(usernameLabel) {
            @Override protected void paintComponent(Graphics g) {
                if (!isOpaque() && getBorder() instanceof RoundedCornerBorder) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setPaint(getBackground());
                    g2.fill(((RoundedCornerBorder) getBorder()).getBorderShape(
                            0, 0, getWidth() - 1, getHeight() - 1));
                    g2.dispose();
                }
                super.paintComponent(g);
            }
            @Override public void updateUI() {
                super.updateUI();
                setOpaque(false);
                setBorder(new RoundedCornerBorder(FIELDS_CORNER_RADIUS, new Color(0x4E4C63)));
            }
        };
        usernameTextField.setHorizontalAlignment(JTextField.CENTER);
        usernameTextField.setPreferredSize(new Dimension(300, 30));
        usernameTextField.setFont(usernameTextField.getFont().deriveFont(15f));

//        BorderFactory.createLineBorder()

        passwordField = new JPasswordField(passwordLabel) {
            @Override protected void paintComponent(Graphics g) {
                if (!isOpaque() && getBorder() instanceof RoundedCornerBorder) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setPaint(getBackground());
                    g2.fill(((RoundedCornerBorder) getBorder()).getBorderShape(
                            0, 0, getWidth() - 1, getHeight() - 1));
                    g2.dispose();
                }
                super.paintComponent(g);
            }
            @Override public void updateUI() {
                super.updateUI();
                setOpaque(false);
                setBorder(new RoundedCornerBorder(FIELDS_CORNER_RADIUS, new Color(0x4E4C63)));
            }
        };
        passwordField.setHorizontalAlignment(JTextField.CENTER);
        passwordField.setPreferredSize(new Dimension(300, 30));
        passwordField.setFont(passwordField.getFont().deriveFont(15f));

        usernameJLabel = new JLabel(empty, SwingConstants.CENTER);
        passwordJLabel = new JLabel(empty, SwingConstants.CENTER);

        usernameJLabel.setOpaque(false);
        usernameJLabel.setFont(usernameJLabel.getFont().deriveFont(12f));
        passwordJLabel.setOpaque(false);
        passwordJLabel.setFont(passwordJLabel.getFont().deriveFont(12f));

        usernameTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (usernameTextField.getText().equals(usernameLabel)) {
                    usernameTextField.setText("");
                    usernameJLabel.setText(usernameLabel);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (usernameTextField.getText().isEmpty()) {
                    usernameTextField.setText(usernameLabel);
                    usernameJLabel.setText(empty);
                }
            }
        });

        passwordField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (new String(passwordField.getPassword()).equals(passwordLabel)) {
                    passwordField.setText("");
                    passwordJLabel.setText(passwordLabel);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (passwordField.getPassword().length == 0) {
                    passwordField.setText(passwordLabel);
                    passwordJLabel.setText(empty);
                }
            }
        });

        String signUpStringLabel = "Fazer cadastro";
        signupJButton = new JButton(signUpStringLabel) {
            @Override protected void paintComponent(Graphics g) {
                if (!isOpaque() && getBorder() instanceof RoundedCornerBorder) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setPaint(getBackground());
                    g2.fill(((RoundedCornerBorder) getBorder()).getBorderShape(
                            0, 0, getWidth() - 1, getHeight() - 1));
                    g2.dispose();
                }
                super.paintComponent(g);
            }
            @Override public void updateUI() {
                super.updateUI();
                setOpaque(false);
                setBorder(new RoundedCornerBorder(FIELDS_CORNER_RADIUS, UIManager.getColor("Button.default.startBorderColor")));
            }
        };
        signupJButton.setFocusable(false);
        signupJButton.setRolloverEnabled(false);
        signupJButton.setFont(signupJButton.getFont().deriveFont(20f));
        signupJButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(
                    this,
                    "Cadastro ainda n\u00E3o implementado",
                    "Indispon\u00EDvel",
                    JOptionPane.WARNING_MESSAGE);
        });

        String signInStringLabel = "Entrar";
        signinJButton = new JButton(signInStringLabel);
        signinJButton.setFocusable(false);
        signinJButton.setRolloverEnabled(false);
        signinJButton.setFont(signinJButton.getFont().deriveFont(20f));
        signinJButton.setPreferredSize(new Dimension(300, 40));
        signinJButton.addActionListener(e -> {
            LOGGER.debug("Click sign in button");
            String username = usernameTextField.getText();
            String password = new String(passwordField.getPassword());
            LoginStatus loginStatus = loginController.checkPassword(username, password);
            System.out.println("loginStatus: " + loginStatus.getMessage());
            if (loginStatus != LoginStatus.SUCCESS) {
                JOptionPane.showMessageDialog(
                        this,
                        "Usuário ou senha inválidos",
                        "Falha no login",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                this.dispose();
                StudentService studentService = loginController.getStudentService();
                new HomeFrame(username, samu);
            }
        });

        JLabel samuLabel = new JLabel("SAMU", SwingConstants.CENTER);
        JLabel signInLabel = new JLabel("Fazer login", SwingConstants.CENTER);

        samuLabel.setFont(samuLabel.getFont().deriveFont(64f));
        signInLabel.setFont(signInLabel.getFont().deriveFont(24f));

        gridConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridConstraints.weightx = 1; gridConstraints.gridx = 0;

        int leftPadding = 30;
        int rightPadding = 30;

        gridConstraints.gridy = 0;
        gridConstraints.insets = new Insets(0, 0, 0, 0);
        loginJPanel.add(samuLabel, gridConstraints);

        gridConstraints.gridy = 1;
        gridConstraints.insets = new Insets(0, leftPadding, 20, rightPadding);
        loginJPanel.add(signInLabel, gridConstraints);

        gridConstraints.gridy = 2;
        gridConstraints.insets = new Insets(0, leftPadding, 0, rightPadding);
        loginJPanel.add(usernameJLabel, gridConstraints);

        gridConstraints.gridy = 3; gridConstraints.gridx = 0;
        gridConstraints.insets = new Insets(0, leftPadding, 15, rightPadding);
        loginJPanel.add(usernameTextField, gridConstraints);

        gridConstraints.gridy = 4;
        gridConstraints.insets = new Insets(0, leftPadding, 0, rightPadding);
        loginJPanel.add(passwordJLabel, gridConstraints);

        gridConstraints.gridy = 5;
        gridConstraints.insets = new Insets(0, leftPadding, 10, rightPadding);
        loginJPanel.add(passwordField, gridConstraints);

        gridConstraints.gridy = 6;
        gridConstraints.insets = new Insets(0, leftPadding, 0, rightPadding);
        loginJPanel.add(signinJButton, gridConstraints);

        gridConstraints.gridy = 7;
        gridConstraints.insets = new Insets(0, leftPadding, 0, rightPadding);
        loginJPanel.add(Util.THEME_BUTTON, gridConstraints);

        GridBagConstraints mainPanelGridConstraints = new GridBagConstraints();
        mainPanelGridConstraints.insets = new Insets(0, 0, 0, 0);
        mainJPanel.add(loginJPanel, mainPanelGridConstraints);
        this.add(mainJPanel);
        this.setVisible(true);
    }

    @Override
    protected void frameInit() {
        super.frameInit();
        this.setSize(this.width, this.height);
        this.setMinimumSize(new Dimension(this.width, this.height));
        this.setTitle(this.frameTitle);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setIconImage(new ImageIcon(requireNonNull(this.getClass().getClassLoader().getResource("friend.png"))).getImage());

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                LOGGER.info("Closing LoginFrame window");
            }
        });
        centreWindow(this);
//        this.setResizable(true);
    }

}

