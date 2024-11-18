import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class LibraryGUI extends JFrame {

    private JTabbedPane mainTabbedPane;
    private JTabbedPane bookTabbedPane;

    // Book Tab
    private JPanel availableBookPanel;
    private JPanel borrowedBookPanel;
    private JPanel borrowBookPanel;

    // Available Book Panel
    private JTable availableBookTable;
    private DefaultTableModel availableBookTableModel;

    public LibraryGUI() {
        setLayout(new BorderLayout());

        // Create main tabbed pane
        mainTabbedPane = new JTabbedPane();
        mainTabbedPane.addTab("Book", createBookTab());
        mainTabbedPane.addTab("Account", createAccountTab());
        mainTabbedPane.addTab("About", createAboutTab());

        // Set default tab to Book
        mainTabbedPane.setSelectedIndex(0);

        // Add main tabbed pane to frame
        add(mainTabbedPane, BorderLayout.CENTER);

        // Set up frame properties
        setSize(1400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Library Management System");
        setVisible(true);
    }

    // Create Book Tab
    private JPanel createBookTab() {
        JPanel bookPanel = new JPanel();
        bookPanel.setLayout(new BorderLayout());

        bookTabbedPane = new JTabbedPane();
        bookTabbedPane.addTab("Available Book", createAvailableBookPanel());
        bookTabbedPane.addTab("Borrowed Book", createBorrowedBookPanel());
        bookTabbedPane.addTab("Borrow Book", createBorrowBookPanel());

        // Set default tab to Available Book
        bookTabbedPane.setSelectedIndex(0);

        bookPanel.add(bookTabbedPane, BorderLayout.CENTER);

        return bookPanel;
    }

    // Create Available Book Panel
    private JPanel createAvailableBookPanel() {
    availableBookPanel = new JPanel();
    availableBookPanel.setLayout(new BorderLayout());

    // Create table model
    availableBookTableModel = new DefaultTableModel();
    availableBookTableModel.addColumn("Name");
    availableBookTableModel.addColumn("ISBN");
    availableBookTableModel.addColumn("Quantity");

    // Read from avbk.txt file
    try (BufferedReader reader = new BufferedReader(new FileReader("D:\\lms\\libraryMS\\src\\avbk.txt"))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] bookData = line.split(",");
            availableBookTableModel.addRow(new Object[] {bookData[0], bookData[1], bookData[2]});
        }
    } 
    catch (FileNotFoundException a) {

    System.err.println("Error: File not found");

} catch (IOException e) {
        System.err.println("Error reading from file: " + e.getMessage());
    }

    // Create table
    availableBookTable = new JTable(availableBookTableModel);

    // Add table to panel
    availableBookPanel.add(new JScrollPane(availableBookTable), BorderLayout.CENTER);

    return availableBookPanel;
}

   // Create Borrowed Book Panel
   private JPanel createBorrowedBookPanel() {

    borrowedBookPanel = new JPanel();

    borrowedBookPanel.setLayout(new BorderLayout());


    // Create table model

    DefaultTableModel borrowedBookTableModel = new DefaultTableModel();

    borrowedBookTableModel.addColumn("Name");

    borrowedBookTableModel.addColumn("ID");

    borrowedBookTableModel.addColumn("Phone Number");

    borrowedBookTableModel.addColumn("Book Name");

    borrowedBookTableModel.addColumn("Book ISBN");

    borrowedBookTableModel.addColumn("Return Date");


    // Read from brw.txt file

    try (BufferedReader reader = new BufferedReader(new FileReader("D:\\lms\\libraryMS\\src\\brw.txt"))) {

        String line;

        while ((line = reader.readLine()) != null) {

            String[] borrowedBookData = line.split(",");

            borrowedBookTableModel.addRow(new Object[] {borrowedBookData[0], borrowedBookData[1], borrowedBookData[2], borrowedBookData[3], borrowedBookData[4], borrowedBookData[5]});

        }

    } catch (IOException e) {

        System.err.println("Error reading from file: " + e.getMessage());

    }


    // Create table

    JTable borrowedBookTable = new JTable(borrowedBookTableModel);


    // Add table to panel

    borrowedBookPanel.add(new JScrollPane(borrowedBookTable), BorderLayout.CENTER);


    // Create return book subtab

    JTabbedPane borrowedBookSubTabbedPane = new JTabbedPane();

    borrowedBookSubTabbedPane.addTab("Borrowed Books", borrowedBookPanel);

    borrowedBookSubTabbedPane.addTab("Return Book", createReturnBookPanel());


    // Add subtab to panel

    borrowedBookPanel = new JPanel();

    borrowedBookPanel.setLayout(new BorderLayout());

    borrowedBookPanel.add(borrowedBookSubTabbedPane, BorderLayout.CENTER);


    return borrowedBookPanel;

}

// Create Return Book Panel
private JPanel createReturnBookPanel() {

    JPanel returnBookPanel = new JPanel();

    returnBookPanel.setLayout(new BorderLayout());


    // Create search panel

    JPanel searchPanel = new JPanel();

    searchPanel.setLayout(new FlowLayout());


    JLabel nameLabel = new JLabel("Borrower Name:");

    JTextField nameTextField = new JTextField(20);


    JLabel idLabel = new JLabel("Borrower ID:");

    JTextField idTextField = new JTextField(20);


    JButton searchButton = new JButton("Search");

    searchButton.addActionListener(new ReturnBookButtonListener(nameTextField, idTextField));


    searchPanel.add(nameLabel);

    searchPanel.add(nameTextField);

    searchPanel.add(idLabel);

    searchPanel.add(idTextField);

    searchPanel.add(searchButton);


    returnBookPanel.add(searchPanel, BorderLayout.NORTH);


    return returnBookPanel;

}

// Search Button Listener
private class ReturnBookButtonListener implements ActionListener {

    private JTextField nameTextField;

    private JTextField idTextField;


    public ReturnBookButtonListener(JTextField nameTextField, JTextField idTextField) {

        this.nameTextField = nameTextField;

        this.idTextField = idTextField;

    }


    @Override

    public void actionPerformed(ActionEvent e) {

        String borrowerName = nameTextField.getText();

        String borrowerID = idTextField.getText();


        // Search for borrower in brw.txt file

        try (BufferedReader reader = new BufferedReader(new FileReader("D:\\lms\\libraryMS\\src\\brw.txt"))) {

            String line;

            boolean found = false;

            while ((line = reader.readLine()) != null) {

                String[] borrowedBookData = line.split(",");

                if (borrowedBookData[0].equals(borrowerName) && borrowedBookData[1].equals(borrowerID)) {

                    found = true;


                    // Ask for confirmation

                    int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to return the book?");

                    if (response == JOptionPane.YES_OPTION) {
                        try (BufferedWriter writer = new BufferedWriter(new FileWriter("D:\\lms\\libraryMS\\src\\brw.txt"))) {

                            String tempLine;

                            while ((tempLine = reader.readLine()) != null) {

                                String[] borowedBookData = tempLine.split(",");

                                if (!borowedBookData[0].equals(borrowerName) && !borowedBookData[1].equals(borrowerID)) {

                                    writer.write(tempLine + "\n");

                                }

                            }

                        } catch (IOException ex) {

                            System.err.println("Error writing to file: " + ex.getMessage());

                        }


                        // Add book to avbk.txt file

                        try (BufferedReader avbkReader = new BufferedReader(new FileReader("D:\\lms\\libraryMS\\src\\avbk.txt"))) {

                            String avbkLine;

                            boolean bookFound = false;

                            while ((avbkLine = avbkReader.readLine()) != null) {

                                String[] bookData = avbkLine.split(",");

                                if (bookData[0].equals(borrowedBookData[3])) {

                                    bookFound = true;

                                    int quantity = Integer.parseInt(bookData[2]) + 1;

                                    try (BufferedWriter avbkWriter = new BufferedWriter(new FileWriter("D:\\lms\\libraryMS\\src\\avbk.txt"))) {

                                        String tempAvbkLine;

                                        while ((tempAvbkLine = avbkReader.readLine()) != null) {

                                            String[] tempBookData = tempAvbkLine.split(",");

                                            if (!tempBookData[0].equals(borrowedBookData[3])) {

                                                avbkWriter.write(tempAvbkLine + "\n");

                                            } else {

                                                avbkWriter.write(bookData[0] + "," + bookData[1] + "," + quantity + "\n");

                                            }

                                        }

                                    } catch (IOException ex) {

                                        System.err.println("Error writing to file: " + ex.getMessage());

                                    }

                                }

                            }

                            if (!bookFound) {

                                try (BufferedWriter avbkWriter = new BufferedWriter(new FileWriter("D:\\lms\\libraryMS\\src\\avbk.txt", true))) {

                                    avbkWriter.write(borrowedBookData[3] + "," + borrowedBookData[4] + ",1\n");

                                } catch (IOException ex) {

                                    System.err.println("Error writing to file: " + ex.getMessage());

                                }

                            }

                        } catch (IOException ex) {

                            System.err.println("Error reading from file: " + ex.getMessage());

                        }


                        // Clear search fields

                        nameTextField.setText("");

                        idTextField.setText("");

                    }

                }

            }

            if (!found) {

                // Display error message if borrower not found

                JOptionPane.showMessageDialog(null, "Borrower not found.");

            }

        } catch (IOException ex) {

            System.err.println("Error reading from file: " + ex.getMessage());

        }

    }

}
    // Create Borrow Book Panel
    private JPanel createBorrowBookPanel() {

        borrowBookPanel = new JPanel();
    
        borrowBookPanel.setLayout(new BorderLayout());
    
    
        // Create search panel
    
        JPanel searchPanel = new JPanel();
    
        searchPanel.setLayout(new FlowLayout());
    
    
        JLabel nameLabel = new JLabel("Borrower Name:");
    
        JTextField nameTextField = new JTextField(20);
    
    
        JLabel idLabel = new JLabel("Borrower ID:");
    
        JTextField idTextField = new JTextField(20);
    
    
        JLabel phoneLabel = new JLabel("Phone Number:");
    
        JTextField phoneTextField = new JTextField(20);
    
    
        JLabel bookLabel = new JLabel("Book Name:");
    
        JTextField bookTextField = new JTextField(20);
    
    
        JButton borrowButton = new JButton("Borrow");
    
        borrowButton.addActionListener(new BorrowBookButtonListener(nameTextField, idTextField, phoneTextField, bookTextField));
    
    
        searchPanel.add(nameLabel);
    
        searchPanel.add(nameTextField);
    
        searchPanel.add(idLabel);
    
        searchPanel.add(idTextField);
    
        searchPanel.add(phoneLabel);
    
        searchPanel.add(phoneTextField);
    
        searchPanel.add(bookLabel);
    
        searchPanel.add(bookTextField);
    
        searchPanel.add(borrowButton);
    
    
        borrowBookPanel.add(searchPanel, BorderLayout.NORTH);
    
    
        return borrowBookPanel;
    
    }

    private class BorrowBookButtonListener implements ActionListener {

        private JTextField nameTextField;
    
        private JTextField idTextField;
    
        private JTextField phoneTextField;
    
        private JTextField bookTextField;
    
    
        public BorrowBookButtonListener(JTextField nameTextField, JTextField idTextField, JTextField phoneTextField, JTextField bookTextField) {
    
            this.nameTextField = nameTextField;
    
            this.idTextField = idTextField;
    
            this.phoneTextField = phoneTextField;
    
            this.bookTextField = bookTextField;
    
        }
    
    
        @Override
    
        public void actionPerformed(ActionEvent e) {
    
            String borrowerName = nameTextField.getText();
    
            String borrowerID = idTextField.getText();
    
            String phoneNumber = phoneTextField.getText();
    
            String bookName = bookTextField.getText();
    
    
            // Search for book in avbk.txt file
    
            try (BufferedReader reader = new BufferedReader(new FileReader("D:\\lms\\libraryMS\\src\\avbk.txt"))) {
    
                String line;
    
                boolean found = false;
                while ((line = reader.readLine()) != null) {

                    String[] bookData = line.split(",");
    
                    if (bookData[0].equals(bookName)) {
    
                        found = true;
    
    
                        // Ask for return date
    
                        String returnDate = JOptionPane.showInputDialog("Enter return date (yyyy-mm-dd):");
    
    
                        // Ask for confirmation
    
                        int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to borrow the book?");
    
                        if (response == JOptionPane.YES_OPTION) {
    
                            // Remove book from avbk.txt file
    
                            try (BufferedWriter writer = new BufferedWriter(new FileWriter("D:\\lms\\libraryMS\\src\\avbk.txt"))) {
    
                                String tempLine;
    
                                while ((tempLine = reader.readLine()) != null) {
    
                                    String[] tempBookData = tempLine.split(",");
    
                                    if (!tempBookData[0].equals(bookName)) {
    
                                        writer.write(tempLine + "\n");
    
                                    }
    
                                }
    
                            } catch (IOException ex) {
    
                                System.err.println("Error writing to file: " + ex.getMessage());
    
                            }
    
    
                            // Add borrower to brw.txt file
    
                            try (BufferedWriter writer = new BufferedWriter(new FileWriter("D:\\lms\\libraryMS\\src\\brw.txt", true))) {
    
                                writer.write(borrowerName + "," + borrowerID + "," + phoneNumber + "," + bookName + "," + bookData[1] + "," + returnDate + "\n");
    
                            } catch (IOException ex) {
    
                                System.err.println("Error writing to file: " + ex.getMessage());
    
                            }
    
    
                            // Clear search fields
    
                            nameTextField.setText("");
    
                            idTextField.setText("");
    
                            phoneTextField.setText("");
    
                            bookTextField.setText("");
    
                        }
    
                    }
    
                }
    
                if (!found) {
    
                    // Display error message if book not found
    
                    JOptionPane.showMessageDialog(null, "Book not found.");
    
                }
    
            } catch (IOException ex) {
    
                System.err.println("Error reading from file: " + ex.getMessage());
    
            }
    
        }
    
    }

    // Create Account Tab
    private JPanel createAccountTab() {
        JPanel accountTab = new JPanel();
        accountTab.setLayout(new BorderLayout());
        accountTab.setPreferredSize(new Dimension(1400, 300)); // set preferred size
    
        JPanel accountInfoPanel = new JPanel();
        accountInfoPanel.setLayout(new GridLayout(0, 2, 20, 20)); // adjusted gaps between components
    
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 18)); // increased font size
        JTextField usernameField = new JTextField("Admin");
        usernameField.setEditable(false);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 18)); // increased font size
        usernameField.setPreferredSize(new Dimension(400, 40)); // set preferred size
    
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 18)); // increased font size
        JPasswordField passwordField = new JPasswordField("password");
        passwordField.setEditable(false);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 18)); // increased font size
        passwordField.setPreferredSize(new Dimension(400, 40)); // set preferred size
    
        JCheckBox showPasswordCheckBox = new JCheckBox("Show Password");
        showPasswordCheckBox.setFont(new Font("Arial", Font.PLAIN, 18)); // increased font size
        showPasswordCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (showPasswordCheckBox.isSelected()) {
                    passwordField.setEchoChar((char) 0);
                } else {
                    passwordField.setEchoChar('*');
                }
            }
        });
    
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 18)); // increased font size
        JTextField nameField = new JTextField("Admin User");
        nameField.setEditable(false);
        nameField.setFont(new Font("Arial", Font.PLAIN, 18)); // increased font size
        nameField.setPreferredSize(new Dimension(400, 40)); // set preferred size
    
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Arial", Font.BOLD, 18)); // increased font size
        JTextField emailField = new JTextField("admin@example.com");
        emailField.setEditable(false);
        emailField.setFont(new Font("Arial", Font.PLAIN, 18)); // increased font size
        emailField.setPreferredSize(new Dimension(400, 40)); // set preferred size
    
        JLabel phoneNumberLabel = new JLabel("Phone Number:");
        phoneNumberLabel.setFont(new Font("Arial", Font.BOLD, 18)); // increased font size
        JTextField phoneNumberField = new JTextField("123-456-7890");
        phoneNumberField.setEditable(false);
        phoneNumberField.setFont(new Font("Arial", Font.PLAIN, 18)); // increased font size
        phoneNumberField.setPreferredSize(new Dimension(400, 40)); // set preferred size
    
        accountInfoPanel.add(usernameLabel);
        accountInfoPanel.add(usernameField);
        accountInfoPanel.add(passwordLabel);
        accountInfoPanel.add(passwordField);
        accountInfoPanel.add(showPasswordCheckBox);
        accountInfoPanel.add(new JLabel()); // empty label to balance the grid
        accountInfoPanel.add(nameLabel);
        accountInfoPanel.add(nameField);
        accountInfoPanel.add(emailLabel);
        accountInfoPanel.add(emailField);
        accountInfoPanel.add(phoneNumberLabel);
        accountInfoPanel.add(phoneNumberField);
    
        accountTab.add(accountInfoPanel, BorderLayout.CENTER);
    
        return accountTab;
    }

    // Create About Tab
    private JPanel createAboutTab() {
        JPanel aboutTab = new JPanel();
        aboutTab.setLayout(new BorderLayout());
    
        JTextArea aboutTextArea = new JTextArea();
        aboutTextArea.setEditable(false);
        aboutTextArea.setLineWrap(true);
        aboutTextArea.setWrapStyleWord(true);
    
        String aboutText = "Library Management System\n\n" +
                "This system is designed to manage books and borrowers in a library. It has three main tabs: Available Books,  Account, and about.\n" +
                "The Available Books tab displays a list of available books, and the Account tab displays account information, and about tab displays about the system and the developer.\n" +
                "The system also has a Return Book feature that allows users to return borrowed books.\n\n";
    
        aboutTextArea.setText(aboutText);
    
        aboutTab.add(new JScrollPane(aboutTextArea), BorderLayout.CENTER);
    
        return aboutTab;
    }

    public static void main(String[] args) {
        new LibraryGUI();
    }
}
