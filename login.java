import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class login extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public login() {
        super("Login Page");
        setLayout(new BorderLayout());

        // Create username and password fields
        JPanel fieldsPanel = new JPanel();
        fieldsPanel.setLayout(new GridLayout(2, 2));
        fieldsPanel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        fieldsPanel.add(usernameField);
        fieldsPanel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        fieldsPanel.add(passwordField);

        // Create login button
        loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                // Validate username and password
                if (username.equals("admin") && password.equals("password")) {
                    // Login successful, open main application
                    dispose();
                    new LibraryGUI().setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(login.this, "Invalid username or password");
                }
            }
        });

        // Add fields and button to frame
        add(fieldsPanel, BorderLayout.CENTER);
        add(loginButton, BorderLayout.SOUTH);

        setSize(300, 150);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new login();
            }
        });
    }
}