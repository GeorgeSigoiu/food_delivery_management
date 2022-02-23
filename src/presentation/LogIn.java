package presentation;

import model.*;

import java.awt.*;
import javax.swing.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Interfata pentru conectare in aplicatie
 */
public class LogIn {

    private JFrame frmLogIn;
    private JTextField idTxtField;
    private JPasswordField passTxtField;
    private JButton loginBtn;
    private JButton signupBtn;
    private static UserType role;
    private final EmployeeView empl;
    public LogIn(EmployeeView empl) {
        this.empl=empl;
        initialize();
        frmLogIn.setVisible(true);
    }
    /**
     * Se initializeaza toate componentele ferestrei
     */
    private void initialize() {
        frmLogIn = new JFrame();
        frmLogIn.getContentPane().setBackground(SystemColor.activeCaption);
        frmLogIn.getContentPane().setLayout(null);

        JLabel idLabel = new JLabel("username");
        idLabel.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        idLabel.setBounds(67, 68, 60, 26);
        frmLogIn.getContentPane().add(idLabel);

        JLabel passLabel = new JLabel("password");
        passLabel.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        passLabel.setBounds(67, 104, 60, 26);
        frmLogIn.getContentPane().add(passLabel);

        idTxtField = new JTextField();
        idTxtField.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        idTxtField.setBounds(146, 69, 196, 26);
        frmLogIn.getContentPane().add(idTxtField);
        idTxtField.setColumns(10);

        passTxtField = new JPasswordField();
        passTxtField.setBounds(146, 106, 196, 26);
        frmLogIn.getContentPane().add(passTxtField);

        JLabel welcomeLabel = new JLabel("Welcome to DeliveryService");
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Times New Roman", Font.BOLD, 17));
        welcomeLabel.setBounds(10, 10, 416, 38);
        frmLogIn.getContentPane().add(welcomeLabel);

        loginBtn = new JButton("Log In");
        loginBtn.setBounds(145, 171, 85, 21);
        frmLogIn.getContentPane().add(loginBtn);

        signupBtn = new JButton("Sign Up");
        signupBtn.setBounds(257, 171, 85, 21);
        frmLogIn.getContentPane().add(signupBtn);

        addLoginBtnActionListener();
        addSignupActionListener();

        frmLogIn.setTitle("Log In");
        frmLogIn.setBounds(100, 100, 450, 300);
        centreWindow(frmLogIn);
        frmLogIn.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    /**
     * Se seteaza valorile ferestrei astfel incat sa fie afisata in mijlocul ecranului
     * @param frame JFrame, interfata grafica
     */
    private void centreWindow(JFrame frame) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
    }

    /**
     * Adaugare ActionListener pe butonul de sign up. Apare o fereastra noua destinata crearii de cont nou pentru client
     */
    private void addSignupActionListener() {
        signupBtn.addActionListener(e -> new SignUp());
    }

    /**
     * Adaugare ActionListener pe butonul de log in. Daca datele introduse sunt corecte, se va deschide fereastra
     * specifica fiecarui tip de utlizator, altfel va aparea mesaj de eroare ca datele introduse nu sunt corecte.
     */
    private void addLoginBtnActionListener() {
        loginBtn.addActionListener(e -> {
            if (!validAccount()) {
                JOptionPane.showMessageDialog(loginBtn, "ID or PASS are incorrect! Try again!", "Autentification", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(loginBtn, "Autentification successfully!", "Autentification", JOptionPane.INFORMATION_MESSAGE);
                if(role.equals(UserType.ADMIN))
                    new AdministratorView();
                else if(role.equals(UserType.CLIENT))
                    new ClientView(getAccount().getUsername());
                else if(role.equals(UserType.EMPLOYEE))
                    empl.setVisibility(true);
            }
            idTxtField.setText("");
            passTxtField.setText("");
        });
    }

    /**
     * Crearea unui Account cu datele introduse pentru logare
     * @return Account
     */
    private Account getAccount() {
        return new Account(idTxtField.getText(), passTxtField.getText());
    }

    /**
     * Se verifica daca contul introdus este al unui client
     * @return boolean
     */
    private boolean isClient() {
        Account acc = getAccount();
        List<Client> clients = RegisteredUsers.getClients();
        List<Client> validClient = clients.stream()
                .filter(client -> client.getUsername().equals(acc.getUsername()) && client.getPassword().equals(acc.getPassword()))
                .collect(Collectors.toList());
        if (validClient.size() != 0) {
            role = UserType.CLIENT;
            return true;
        }
        return false;
    }

    /**
     * se verifica daca contul introdus este al unui administrator
     * @return boolean
     */
    private boolean isAdmin() {
        Account acc = getAccount();
        List<Administrator> admins = RegisteredUsers.getAdmins();
        List<Administrator> validAdmin = admins.stream()
                .filter(admn -> admn.getUsername().equals(acc.getUsername()) && admn.getPassword().equals(acc.getPassword()))
                .collect(Collectors.toList());
        if (validAdmin.size() != 0) {
            role = UserType.ADMIN;
            return true;
        }
        return false;
    }

    /**
     * Se verifica daca contul introdus este al unui angajat
     * @return boolean
     */
    private boolean isEmployee() {
        Account acc = getAccount();
        List<Employee> employees = RegisteredUsers.getEmployees();
        List<Employee> validEmployee = employees.stream()
                .filter(empl -> empl.getUsername().equals(acc.getUsername()) && empl.getPassword().equals(acc.getPassword()))
                .collect(Collectors.toList());
        if (validEmployee.size() != 0) {
            role = UserType.EMPLOYEE;
            return true;
        }
        return false;
    }

    /**
     * Se verifica daca au fost completate ambele campuri pentru logare
     * @return boolean
     */
    private boolean validAccount() {
        Account acc = getAccount();
        if (acc.getUsername() == null || acc.getPassword() == null) return false;
        if (acc.getUsername().equals("") || acc.getPassword().equals("")) return false;
        return isAdmin() || isClient() || isEmployee();
    }

    public enum UserType {
        ADMIN, CLIENT, EMPLOYEE
    }
}

