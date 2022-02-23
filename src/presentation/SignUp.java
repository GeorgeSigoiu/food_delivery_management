package presentation;

import dao.Serializator;
import model.*;

import java.awt.Dimension;

import javax.swing.*;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.Font;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Interfata pentru crearea unui nou cont de client
 */
public class SignUp {

    private JFrame frmSignup;
    private JTextField nameTxtField;
    private JTextField phoneTxtField;
    private JTextField emailTxtField;
    private JTextField idTxtField;
    private JTextField passTxtField;
    private JTextArea addressTxtField;
    private JButton registerBtn;

    public SignUp() {
        initialize();
        frmSignup.setVisible(true);
    }

    /**
     * Se initializeaza toate componentele ferestrei
     */
    private void initialize() {
        frmSignup = new JFrame();
        frmSignup.setTitle("SignUp");
        frmSignup.getContentPane().setBackground(SystemColor.activeCaption);
        frmSignup.getContentPane().setLayout(null);

        JLabel registrationLabel = new JLabel("Client registration");
        registrationLabel.setHorizontalAlignment(SwingConstants.CENTER);
        registrationLabel.setFont(new Font("Times New Roman", Font.BOLD, 17));
        registrationLabel.setBounds(10, 10, 346, 29);
        frmSignup.getContentPane().add(registrationLabel);

        JLabel nameLabel = new JLabel("Name");
        nameLabel.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        nameLabel.setBounds(24, 50, 76, 21);
        frmSignup.getContentPane().add(nameLabel);

        JLabel emailLabel = new JLabel("E-mail");
        emailLabel.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        emailLabel.setBounds(24, 81, 76, 21);
        frmSignup.getContentPane().add(emailLabel);

        JLabel phoneLabel = new JLabel("Phone");
        phoneLabel.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        phoneLabel.setBounds(24, 112, 76, 21);
        frmSignup.getContentPane().add(phoneLabel);

        JLabel addressLabel = new JLabel("Address");
        addressLabel.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        addressLabel.setBounds(24, 143, 76, 21);
        frmSignup.getContentPane().add(addressLabel);

        nameTxtField = new JTextField();
        nameTxtField.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        nameTxtField.setBounds(105, 49, 179, 22);
        frmSignup.getContentPane().add(nameTxtField);
        nameTxtField.setColumns(10);

        phoneTxtField = new JTextField();
        phoneTxtField.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        phoneTxtField.setColumns(10);
        phoneTxtField.setBounds(105, 112, 179, 22);
        frmSignup.getContentPane().add(phoneTxtField);

        emailTxtField = new JTextField();
        emailTxtField.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        emailTxtField.setColumns(10);
        emailTxtField.setBounds(105, 80, 179, 22);
        frmSignup.getContentPane().add(emailTxtField);

        addressTxtField = new JTextArea();
        addressTxtField.setLineWrap(true);
        addressTxtField.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        addressTxtField.setBounds(105, 147, 179, 58);
        frmSignup.getContentPane().add(addressTxtField);

        registerBtn = new JButton("Register");
        registerBtn.setBounds(138, 311, 85, 21);
        frmSignup.getContentPane().add(registerBtn);

        JLabel idLabel = new JLabel("ID");
        idLabel.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        idLabel.setBounds(24, 235, 76, 21);
        frmSignup.getContentPane().add(idLabel);

        JLabel passLabel = new JLabel("PASS");
        passLabel.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        passLabel.setBounds(24, 266, 76, 21);
        frmSignup.getContentPane().add(passLabel);

        idTxtField = new JTextField();
        idTxtField.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        idTxtField.setColumns(10);
        idTxtField.setBounds(105, 234, 179, 22);
        frmSignup.getContentPane().add(idTxtField);

        passTxtField = new JTextField();
        passTxtField.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        passTxtField.setColumns(10);
        passTxtField.setBounds(105, 265, 179, 22);
        frmSignup.getContentPane().add(passTxtField);
        frmSignup.setBounds(100, 100, 380, 420);

        addRegisterActionListener();

        centreWindow(frmSignup);
        frmSignup.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
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
     * Se creeaza un client cu datele completate
     * @return Client
     */
    private Client getClient() {
        Client cl = new Client();
        cl.setName(nameTxtField.getText());
        cl.setPhone(phoneTxtField.getText());
        cl.setAddress(addressTxtField.getText());
        cl.setEmail(emailTxtField.getText());
        Account acc = new Account();
        acc.setPassword(passTxtField.getText());
        acc.setUsername(idTxtField.getText());
        cl.setAccount(acc);
        return cl;
    }

    /**
     * Se verifica daca un copnt dat ca parametru exista deja in memorie
     * @param account Account, contul care se verifica
     * @return boolean
     */
    private boolean accountExists(Account account) {
        List<Client> clients = RegisteredUsers.getClients().stream()
                .filter(cl -> cl.getUsername().equals(account.getUsername()))
                .collect(Collectors.toList());
        if (clients.size() != 0) return true;
        List<Administrator> administrators = RegisteredUsers.getAdmins().stream()
                .filter(cl -> cl.getUsername().equals(account.getUsername()))
                .collect(Collectors.toList());
        if (administrators.size() != 0) return true;
        List<Employee> employees = RegisteredUsers.getEmployees().stream()
                .filter(cl -> cl.getUsername().equals(account.getUsername()))
                .collect(Collectors.toList());
        return employees.size() != 0;
    }

    /**
     * Adaugare ActionListener pe butonul de register. Se verifica datele introduse si daca sunt corecte un nou cont de
     * client este creat, altfel va aparea un mesaj de eroare specificand ca nu au fost completate toate campurile sau
     * un mesaj prin care se atentioneaza clientul ca username-ul deja exista.
     */
    private void addRegisterActionListener() {
        registerBtn.addActionListener(e -> {
            if (validateInformations()) {
                Client cl = getClient();
                if (!accountExists(cl.getAccount())) {
                    RegisteredUsers.addUser(cl);
                    Serializator.serializeClients();
                    JOptionPane.showMessageDialog(registerBtn, "Registraton successfully!", "Registration", JOptionPane.INFORMATION_MESSAGE);
                    frmSignup.setVisible(false);
                }
                else{
                    idTxtField.setText("");
                    passTxtField.setText("");
                    JOptionPane.showMessageDialog(registerBtn, "ID is already taken! Choose another one.", "Registration", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(registerBtn, "Please complete all fields!", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    /**
     * Se verifica daca datele necesare crearii unui cont de client sunt completate, return true daca toate sunt
     * completate sau false in caz contrar
     * @return boolean
     */
    private boolean validateInformations() {
        return validateComponent(nameTxtField) && validateComponent(emailTxtField) && validateComponent(phoneTxtField)
                && validateComponent(addressTxtField) && validateComponent(idTxtField) && validateComponent(passTxtField);
    }

    /**
     * Se verifica daca o componenta este null sau daca nu contine informatii scrise de client
     * @param cmp JTextField, componenta care se verifica
     * @return boolean
     */
    private boolean validateComponent(JTextField cmp) {
        return cmp != null && !cmp.getText().equals("");
    }

    /**
     * Se verifica daca o componenta este null sau daca nu contine informatii scrise de client
     * @param cmp JTextArea, componenta care se verifica
     * @return boolean
     */
    private boolean validateComponent(JTextArea cmp) {
        return cmp != null && !cmp.getText().equals("");
    }
}
