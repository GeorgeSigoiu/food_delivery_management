package presentation;

import business.DeliveryService;
import business.Order;
import dao.FileWriter1;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;

/**
 * Interfata grafica pentru angajat
 */
public class EmployeeView implements Observer {

    private JFrame frmEmployeeWindow;
    private JLabel lblNewLabel;
    private static int ord = 0;
    private static final List<Integer> ordersId = new ArrayList<>();
    private JTextArea textArea;
    public EmployeeView(String username) {
        initialize();
        frmEmployeeWindow.setVisible(true);
    }
    /**
     * Se initializeaza toate componentele ferestrei
     */
    private void initialize() {
        frmEmployeeWindow = new JFrame();
        frmEmployeeWindow.setTitle("Employee window");
        frmEmployeeWindow.getContentPane().setBackground(SystemColor.activeCaption);
        frmEmployeeWindow.getContentPane().setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(81, 29, 654, 254);
        frmEmployeeWindow.getContentPane().add(scrollPane);

        textArea = new JTextArea();
        textArea.setFont(new Font("Times New Roman", Font.PLAIN, 17));
        scrollPane.setViewportView(textArea);

        JButton button = new JButton("Ok");
        button.setBounds(362, 335, 85, 21);
        button.addActionListener(e -> {
            lblNewLabel.setText("Orders have been saved!");
            FileWriter1.generateRaport("newOrders.txt",textArea.getText());
        });
        frmEmployeeWindow.getContentPane().add(button);

        lblNewLabel = new JLabel("No new orders!");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 17));
        lblNewLabel.setBounds(78, 293, 657, 32);
        frmEmployeeWindow.getContentPane().add(lblNewLabel);
        frmEmployeeWindow.setBounds(100, 100, 830, 420);
        frmEmployeeWindow.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        centreWindow(frmEmployeeWindow);
    }

    /**
     * Seteaza textul unui label pentru a informa angajatul daca exista comenzi noi de care trebuie sa se ocupe
     * @param o Observable
     * @param arg Object, folosit ca si string, reprezinta mesajul care va fi afisat cand se observa o modificare in
     *            numarul de comenzi
     */
    @Override
    public void update(Observable o, Object arg) {
        ord++;
        ordersId.add((Integer) arg);
        if (ord == 1)
            lblNewLabel.setText("New order!");
        else
            lblNewLabel.setText("You have " + ord + " new orders!");
        setOrders();
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
     * Setter pentru vizibilitatea ferestrei angajatului
     * @param val boolean
     */
    public void setVisibility(boolean val) {
        frmEmployeeWindow.setVisible(val);
    }

    /**
     * Se verifica daca exista o anumita comanda in lista de comenzi
     * @param id int, id-ul comenzii care se verifica
     * @return boolean
     */
    private boolean existsOrder(int id){
        return ordersId.contains(id);
    }

    /**
     * Se creeaza mesajul care sa fie afisat pentru angajat. Mesajul este format din informatiile comenzii (orderID,
     * clientID, orderDate) si produsele comandate de catre un client
     * @see Order
     */
    private void setOrders() {
        StringBuilder txt=new StringBuilder();
        Set<Order> orders = DeliveryService.getOrders().keySet().stream().filter(or->existsOrder(or.getOrderID())).collect(Collectors.toSet());
        orders.forEach(order -> {
            txt.append("  Order: ").append(order.getOrderID()).append("\n  ClientID: ").append(order.getClientID()).append("\n");
            DeliveryService.getOrders().get(order).forEach(p-> txt.append("  ").append(p.toString()).append("\n"));
            txt.append("\n");
        });

        textArea.setText(txt.toString());
    }
}
