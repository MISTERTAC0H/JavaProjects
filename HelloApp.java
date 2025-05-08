import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class HelloApp {
    private static JFrame frame;
    private static GraphicsDevice gd;
    private static boolean isFullscreen = false;
    private static JPanel controlBar;
    private static JPanel contentPanel;
    private static JLabel helloLabel;
    private static JTextField velocityField;
    private static JTextField massField;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(HelloApp::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        frame = new JFrame("Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

        initControlBar();
        initContentPanel();

        frame.setLayout(new BorderLayout());
        frame.add(controlBar, BorderLayout.NORTH);
        frame.add(contentPanel, BorderLayout.CENTER);
        frame.setSize(500, 300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        addGlobalKeyListener();
    }

    private static void initControlBar() {
        controlBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton fullscreenBtn = new JButton("Fullscreen");
        JButton windowedBtn = new JButton("Windowed");
        JButton exitBtn = new JButton("Exit");

        fullscreenBtn.addActionListener(e -> enterFullscreen());
        windowedBtn.addActionListener(e -> exitFullscreen());
        exitBtn.addActionListener(e -> System.exit(0));

        controlBar.add(fullscreenBtn);
        controlBar.add(windowedBtn);
        controlBar.add(exitBtn);
    }

    private static void initContentPanel() {
        contentPanel = new JPanel(new CardLayout());

        // Input panel
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel velocityLabel = new JLabel("Enter velocity (m/s):");
        velocityField = new JTextField(10);

        JLabel massLabel = new JLabel("Enter mass (kg):");
        massField = new JTextField(10);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> showHelloPanel());

        inputPanel.add(velocityLabel);
        inputPanel.add(velocityField);
        inputPanel.add(massLabel);
        inputPanel.add(massField);
        inputPanel.add(new JLabel()); // spacer
        inputPanel.add(submitButton);

        // Hello panel
        JPanel helloPanel = new JPanel(new BorderLayout());
        helloLabel = new JLabel("", SwingConstants.CENTER);
        helloLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> showInputPanel());

        helloPanel.add(helloLabel, BorderLayout.CENTER);
        helloPanel.add(backButton, BorderLayout.SOUTH);

        contentPanel.add(inputPanel, "Input");
        contentPanel.add(helloPanel, "Hello");
    }

    private static void showHelloPanel() {
        try {
            double velocity = Double.parseDouble(velocityField.getText().trim());
            double mass = Double.parseDouble(massField.getText().trim());
            double momentum = velocity * mass;

            helloLabel.setText("<html>" +
                    "Velocity: " + velocity + " m/s<br>" +
                    "Mass: " + mass + " kg<br>" +
                    "Momentum: " + momentum + " kg·m/s</html>");
        } catch (NumberFormatException e) {
            helloLabel.setText("Invalid input! Please enter numbers for velocity and mass.");
        }

        CardLayout cl = (CardLayout) contentPanel.getLayout();
        cl.show(contentPanel, "Hello");
    }

    private static void showInputPanel() {
        CardLayout cl = (CardLayout) contentPanel.getLayout();
        cl.show(contentPanel, "Input");
    }

    private static void enterFullscreen() {
        if (!isFullscreen) {
            frame.dispose();
            frame.setUndecorated(true);
            gd.setFullScreenWindow(frame);
            frame.setVisible(true);
            isFullscreen = true;
        }
    }

    private static void exitFullscreen() {
        if (isFullscreen) {
            gd.setFullScreenWindow(null);
            frame.dispose();
            frame.setUndecorated(false);
            frame.setSize(500, 300);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            isFullscreen = false;
        }
    }

    private static void addGlobalKeyListener() {
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
            public boolean dispatchKeyEvent(KeyEvent e) {
                if (e.getID() == KeyEvent.KEY_PRESSED && e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    SwingUtilities.invokeLater(() -> controlBar.setVisible(true));
                }
                return false;
            }
        });
    }
}
