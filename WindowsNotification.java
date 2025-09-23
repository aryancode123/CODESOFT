import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class WindowNotifications {

    public static void main(String[] args) {
        // Make sure AWT is not headless
        if (GraphicsEnvironment.isHeadless()) {
            System.err.println("Graphics environment is headless. GUI operations not possible.");
            System.exit(1);
        }

        SwingUtilities.invokeLater(() -> {
            new NotificationDemo();
        });
    }
}

class NotificationDemo extends JFrame {
    private SystemTray tray;
    private TrayIcon trayIcon;

    public NotificationDemo() {
        initializeGUI();
        setupSystemTray();
    }

    private void initializeGUI() {
        setTitle("Java Notification Demo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(2, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton trayNotificationBtn = new JButton("Show System Tray Notification");
        trayNotificationBtn.addActionListener(e -> showTrayNotification());

        panel.add(trayNotificationBtn);

        add(panel);
        setVisible(true);
    }

    private void setupSystemTray() {
        if (!SystemTray.isSupported()) {
            System.out.println("System Tray not supported on this platform.");
            return;
        }
        tray = SystemTray.getSystemTray();

        Image image;
        try {
            image = Toolkit.getDefaultToolkit().getImage("icon.png");
        } catch (Exception e) {
            image = null;
        }
        if (image == null || image.getWidth(null) < 0) {
            image = createDefaultIcon();
        }

        trayIcon = new TrayIcon(image, "Notification Demo");
        trayIcon.setImageAutoSize(true);
        trayIcon.setToolTip("Java Notification Demo");

        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    private Image createDefaultIcon() {
        int size = 16;
        BufferedImage img = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        g2.setColor(Color.BLUE);
        g2.fillRect(0, 0, size, size);
        g2.setColor(Color.WHITE);
        g2.fillOval(3, 3, size - 6, size - 6);
        g2.dispose();
        return img;
    }

    private void showTrayNotification() {
        if (trayIcon != null) {
            trayIcon.displayMessage(
                "Tray Notification",
                "This is a system tray notification!",
                TrayIcon.MessageType.INFO
            );
        } else {
            JOptionPane.showMessageDialog(this,
                "System tray not available.",
                "Fallback",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
