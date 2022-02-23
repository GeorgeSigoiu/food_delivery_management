package presentation;

import business.DeliveryService;
import business.MenuItem;
import business.Order;
import model.RegisteredUsers;

import java.awt.Dimension;

import javax.swing.JFrame;

import java.awt.SystemColor;
import java.awt.Toolkit;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class OrdersView {

    private JFrame frmOrders;
    private JTable table;
    private final Map<Integer, String> clients = new HashMap<>();
    private JTextArea textArea;

    public OrdersView() {
        initialize();
        frmOrders.setVisible(true);
    }


    private void initialize() {
        frmOrders = new JFrame();
        frmOrders.setTitle("Orders");
        frmOrders.getContentPane().setBackground(SystemColor.activeCaption);
        frmOrders.getContentPane().setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 74, 201, 349);
        frmOrders.getContentPane().add(scrollPane);

        table = new JTable();

        scrollPane.setViewportView(table);

        JScrollPane scrollPane_1 = new JScrollPane();
        scrollPane_1.setBounds(239, 74, 512, 349);
        frmOrders.getContentPane().add(scrollPane_1);

        textArea = new JTextArea();
        scrollPane_1.setViewportView(textArea);

        JLabel lblNewLabel = new JLabel("Clients");
        lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lblNewLabel.setBounds(21, 43, 95, 21);
        frmOrders.getContentPane().add(lblNewLabel);

        JLabel lblOrders = new JLabel("Orders");
        lblOrders.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lblOrders.setBounds(253, 43, 95, 21);
        frmOrders.getContentPane().add(lblOrders);
        frmOrders.setBounds(100, 100, 800, 500);
        frmOrders.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        centreWindow(frmOrders);
        setTableContent();
        addTableMouseListener();
    }

    /**
     * Adauga un MouseListener pe tabelul in care apar clientii. Cand un client este selectat se vor afisa comenzile plasate de acesta
     */
    private void addTableMouseListener() {
        table.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int i = table.getSelectedRow();
                int id = Integer.parseInt(String.valueOf(table.getValueAt(i, 0)));
                List<Order> orders = DeliveryService.getOrders().keySet().stream().filter(o -> o.getClientID() == id).collect(Collectors.toList());
                StringBuilder sb = new StringBuilder();
                orders.forEach(order -> {
                    sb.append("Order ").append(order.getOrderID()).append("\ndate: ").append(order.getOrderDate()).append("\n");
                    sb.append("Products:\n");
                    List<MenuItem> products = DeliveryService.getOrders().get(order);
                    AtomicInteger price = new AtomicInteger();
                    products.forEach(product -> {
                        sb.append("-").append(product.toString()).append("\n");
                        price.addAndGet(product.computePrice());
                    });
                    sb.append("Price: ").append(price).append("\n\n");
                });
                textArea.setText(sb.toString());
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }

    /**
     * Se seteaza valorile ferestrei astfel incat sa fie afisata in mijlocul ecranului
     *
     * @param frame JFrame, interfata grafica
     */
    private void centreWindow(JFrame frame) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
    }

    /**
     * Afiseaza id-ul si numele fiecarui client inregistrat
     */
    private void setTableContent() {
        Set<Order> orders = DeliveryService.getOrders().keySet();
        Set<Integer> clientsId = new HashSet<>();
        orders.forEach(o -> clientsId.add(o.getClientID()));
        clientsId.forEach(c -> clients.put(c, getClientName(c)));
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{"id", "Client name"});
        Object[] row = new Object[2];
        clients.keySet().forEach(id -> {
            row[0] = id;
            row[1] = getClientName(id);
            model.addRow(row);
        });
        table.setModel(model);
        table.getColumnModel().getColumn(0).setPreferredWidth(30);
        table.getColumnModel().getColumn(1).setPreferredWidth(160);
    }

    /**
     * Gaseste numele clientului cu un anumit id
     *
     * @param id Integer, id-ul clientului cautat
     * @return String, numele clientului
     */
    private String getClientName(Integer id) {
        return RegisteredUsers.getClients().stream().filter(cl -> cl.getClientID() == id).collect(Collectors.toList()).get(0).getName();
    }
}
