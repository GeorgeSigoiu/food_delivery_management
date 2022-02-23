package presentation;

import business.*;
import model.Client;
import model.RegisteredUsers;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.*;
import java.awt.SystemColor;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.table.DefaultTableModel;

/**
 * Interfata grafica pentru client
 */
public class ClientView {

    private JFrame frmClient;
    private JTextField searchTxtField;
    private JTable productsTable, tableOrder;
    private JButton searchBtn, addCartBtn, deleteBtn, placeOrderBtn;
    private JComboBox criteriaBox;
    private JToggleButton baseProductsToggle, compositeProductsToggle;
    private JLabel totalCostLabel;
    private final String username;
    private List<MenuItem> products = new ArrayList<>();
    private DeliveryService ds = new DeliveryService();

    public ClientView(String username) {
        this.username = username;
        initialize();
        frmClient.setVisible(true);
    }

    /**
     * Se initializeaza toate componentele ferestrei
     */
    private void initialize() {
        frmClient = new JFrame();
        frmClient.setTitle("Client window");
        frmClient.getContentPane().setBackground(SystemColor.activeCaption);
        frmClient.getContentPane().setLayout(null);

        searchBtn = new JButton("Search");
        searchBtn.setBounds(339, 51, 85, 21);
        frmClient.getContentPane().add(searchBtn);

        searchTxtField = new JTextField();
        searchTxtField.setBounds(143, 52, 187, 19);
        frmClient.getContentPane().add(searchTxtField);
        searchTxtField.setColumns(10);

        criteriaBox = new JComboBox();
        criteriaBox.setModel(new DefaultComboBoxModel(new Object[]{"Title", "Rating", "Calories", "Fat", "Sodium", "Price"}));
        criteriaBox.setBounds(10, 51, 120, 21);
        frmClient.getContentPane().add(criteriaBox);

        JScrollPane scrollPane1 = new JScrollPane();
        scrollPane1.setBounds(10, 100, 587, 317);
        frmClient.getContentPane().add(scrollPane1);

        productsTable = new JTable();
        scrollPane1.setViewportView(productsTable);

        baseProductsToggle = new JToggleButton("Base Products");
        baseProductsToggle.setBounds(10, 415, 169, 21);
        frmClient.getContentPane().add(baseProductsToggle);

        compositeProductsToggle = new JToggleButton("Composite Products");
        compositeProductsToggle.setBounds(175, 415, 169, 21);
        frmClient.getContentPane().add(compositeProductsToggle);

        JScrollPane scrollPane2 = new JScrollPane();
        scrollPane2.setBounds(675, 100, 480, 156);
        frmClient.getContentPane().add(scrollPane2);

        tableOrder = new JTable();
        scrollPane2.setViewportView(tableOrder);

        addCartBtn = new JButton(">>");
        addCartBtn.setFont(new Font("Times New Roman", Font.BOLD, 16));
        addCartBtn.setBounds(607, 156, 58, 21);
        frmClient.getContentPane().add(addCartBtn);

        totalCostLabel = new JLabel("Total cost:");
        totalCostLabel.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        totalCostLabel.setBounds(659, 263, 334, 21);
        frmClient.getContentPane().add(totalCostLabel);

        deleteBtn = new JButton("Delete selected");
        deleteBtn.setBounds(675, 294, 128, 21);
        frmClient.getContentPane().add(deleteBtn);

        placeOrderBtn = new JButton("Place order");
        placeOrderBtn.setBounds(675, 325, 128, 21);
        frmClient.getContentPane().add(placeOrderBtn);

        JLabel purchaseLabel = new JLabel("");
        purchaseLabel.setBounds(442, 359, 298, 36);
        frmClient.getContentPane().add(purchaseLabel);


        frmClient.setBounds(100, 100, 1200, 499);
        centreWindow(frmClient);
        frmClient.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        addToggleButton1ActionListener();
        addToggleButton2ActionListener();
        addSearchActionListener();
        addAddToCartActionListener();
        addDeleteActionListener();
        addPlaceOrderActionListener();
        compositeProductMouseListener();

    }

    private void addPlaceOrderActionListener() {
        placeOrderBtn.addActionListener(e -> {
            if (products.size() == 0) {
                JOptionPane.showMessageDialog(placeOrderBtn, "An order needs to have at least 1 product!", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
            Client client = RegisteredUsers.getClients().stream().filter(c -> c.getUsername().equals(username)).collect(Collectors.toList()).get(0);
            ds.createOrder(client, products);
            JOptionPane.showMessageDialog(placeOrderBtn, "Order sent!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
            products = new ArrayList<>();
            setTableOrder();
        });
    }

    private void addDeleteActionListener() {
        deleteBtn.addActionListener(e -> {
            int row = tableOrder.getSelectedRow();
            String name = String.valueOf(tableOrder.getValueAt(row, 0));
            products = products.stream()
                    .filter(p -> (p instanceof BaseProduct) ? !((BaseProduct) p).getTitle().toLowerCase().equals(name.toLowerCase()) : !((CompositeProduct) p).getTitle().toLowerCase().equals(name.toLowerCase()))
                    .collect(Collectors.toList());
            setTableOrder();
        });
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

    private void addToggleButton1ActionListener() {
        baseProductsToggle.addActionListener(e -> {
            baseProductsToggle.setSelected(true);
            compositeProductsToggle.setSelected(false);
            setTableProductsBaseProducts(DeliveryService.getBaseProducts().stream().sorted(Comparator.comparing(p -> p.getTitle().toLowerCase())).collect(Collectors.toList()));
        });
    }

    private void addToggleButton2ActionListener() {
        compositeProductsToggle.addActionListener(e -> {
            baseProductsToggle.setSelected(false);
            compositeProductsToggle.setSelected(true);
            setTableProductsCompositeProducts(DeliveryService.getCompositeProducts().stream().sorted(Comparator.comparing(cp -> cp.getTitle().toLowerCase())).collect(Collectors.toList()));
        });
    }

    private void addSearchActionListener() {
        searchBtn.addActionListener(e -> {
            if (baseProductsToggle.isSelected()) {
                if (searchTxtField.getText().equals("")) {
                    setTable(productsTable, DeliveryService.getBaseProducts().stream().sorted(Comparator.comparing(p -> p.getTitle().toLowerCase())).collect(Collectors.toList()));
                    return;
                }
                searchFunction(DeliveryService.getBaseProducts());
            } else {
                List<CompositeProduct> compositeProducts = DeliveryService.getCompositeProducts();
                List<CompositeProduct> searchedProducts = new ArrayList<>();
                for (CompositeProduct cp : compositeProducts) {
                    List<BaseProduct> products = searchFunction(cp.getProducts());
                    assert products != null;
                    if (products.size() > 0) {
                        searchedProducts.add(cp);
                    }
                }
                setTableProductsCompositeProducts(searchedProducts);
            }
        });
    }

    private List<BaseProduct> searchFunction(List<BaseProduct> products) {
        String name;
        int minInt = 0, maxInt = 99999999;
        double minDouble = 0.0, maxDouble = 99999999.0;
        List<BaseProduct> searchedProducts;
        if (String.valueOf(criteriaBox.getSelectedItem()).equals("Title")) {
            name = searchTxtField.getText();
            searchedProducts = products.stream().filter(p -> p.getTitle().toLowerCase().contains(name.toLowerCase())).collect(Collectors.toList());
        } else if (String.valueOf(criteriaBox.getSelectedItem()).equals("Rating")) {
            String sir = searchTxtField.getText();
            if (sir.contains("<")) {
                String parse = sir.replace(" ", "").replace("<", "");
                maxDouble = Double.parseDouble(parse);
            } else if (sir.contains(">")) {
                String parse = sir.replace(" ", "").replace(">", "");
                minDouble = Double.parseDouble(parse);
            } else {
                String parse = sir.replace(" ", "");
                minDouble = Double.parseDouble(parse);
                maxDouble = minDouble;
            }
            double finalMaxDouble = maxDouble;
            double finalMinDouble = minDouble;
            searchedProducts = products.stream().filter(p -> p.getRating() <= finalMaxDouble && finalMinDouble <= p.getRating()).collect(Collectors.toList());
        } else {
            String sir = searchTxtField.getText();
            if (sir.contains("<") || sir.contains(">")) {
                if (sir.contains("<")) {
                    String parse = sir.replace(" ", "").replace("<", "");
                    maxInt = Integer.parseInt(parse);
                } else if (sir.contains(">")) {
                    String parse = sir.replace(" ", "").replace(">", "");
                    minInt = Integer.parseInt(parse);
                }
                System.out.println(minInt + " " + maxInt);
                searchedProducts = detProductsIntInterv(minInt, maxInt);
            } else {
                searchedProducts = detProductsIntVal(Integer.parseInt(sir));
            }
        }
        if (baseProductsToggle.isSelected())
            setTableProductsBaseProducts(searchedProducts.stream().sorted(Comparator.comparing(p -> p.getTitle().toLowerCase())).collect(Collectors.toList()));
        else
            return searchedProducts;
        return null;
    }

    private void setTable(JTable table, List<BaseProduct> products) {
        try {
            DefaultTableModel tableModel = new DefaultTableModel();
            tableModel.setColumnIdentifiers(createColumnIdentifiers());
            Field[] fields = Class.forName("business.BaseProduct").getDeclaredFields();
            Object[] rows = new Object[tableModel.getColumnCount()];
            products.forEach(p -> {
                try {
                    int i = 0;
                    for (Field f : fields) {
                        f.setAccessible(true);
                        rows[i++] = f.get(p);
                    }
                    tableModel.addRow(rows);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            });
            table.setModel(tableModel);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private List<BaseProduct> detProductsIntVal(int val) {
        List<BaseProduct> products = DeliveryService.getBaseProducts();
        return switch (String.valueOf(criteriaBox.getSelectedItem())) {
            case "Fat" -> products.stream().filter(p -> p.getFat() == val).collect(Collectors.toList());
            case "Sodium" -> products.stream().filter(p -> p.getSodium() == val).collect(Collectors.toList());
            case "Calories" -> products.stream().filter(p -> p.getCalories() == val).collect(Collectors.toList());
            case "Price" -> products.stream().filter(p -> p.computePrice() == val).collect(Collectors.toList());
            default -> new ArrayList<>();
        };
    }

    private List<BaseProduct> detProductsIntInterv(int minInt, int maxInt) {
        List<BaseProduct> products = DeliveryService.getBaseProducts();
        return switch (String.valueOf(criteriaBox.getSelectedItem())) {
            case "Fat" -> products.stream().filter(p -> p.getFat() <= maxInt && minInt <= p.getFat()).collect(Collectors.toList());
            case "Sodium" -> products.stream().filter(p -> p.getSodium() <= maxInt && minInt <= p.getSodium()).collect(Collectors.toList());
            case "Calories" -> products.stream().filter(p -> p.getCalories() <= maxInt && minInt <= p.getCalories()).collect(Collectors.toList());
            case "Price" -> products.stream().filter(p -> p.computePrice() <= maxInt && minInt <= p.computePrice()).collect(Collectors.toList());
            default -> new ArrayList<>();
        };
    }

    private BaseProduct getBaseProduct(JTable table, int i) {
        String title = (String) table.getModel().getValueAt(i, 0);
        Double rating = Double.valueOf(String.valueOf(table.getModel().getValueAt(i, 1)));
        Integer calories = Integer.valueOf(String.valueOf(table.getModel().getValueAt(i, 2)));
        Integer protein = Integer.valueOf(String.valueOf(table.getModel().getValueAt(i, 3)));
        Integer fat = Integer.valueOf(String.valueOf(table.getModel().getValueAt(i, 4)));
        Integer sodium = Integer.valueOf(String.valueOf(table.getModel().getValueAt(i, 5)));
        Integer price = Integer.valueOf(String.valueOf(table.getModel().getValueAt(i, 6)));
        return new BaseProduct(title, rating, calories, protein, fat, sodium, price);
    }

    private void addAddToCartActionListener() {
        addCartBtn.addActionListener(e -> {
            if (baseProductsToggle.isSelected()) {
                int[] rows = productsTable.getSelectedRows();
                for (int i : rows) products.add(getBaseProduct(productsTable, i));
            } else {
                int poz = productsTable.getSelectedRows()[0];
                String name = String.valueOf(productsTable.getValueAt(poz, 0));
                List<CompositeProduct> compositeProduct = DeliveryService.getCompositeProducts().stream().filter(cp -> cp.getTitle().toLowerCase().equals(name.toLowerCase())).collect(Collectors.toList());
                products.add(compositeProduct.get(0));
            }
            setTableOrder();
        });
    }

    private void setTableOrder() {
        try {
            DefaultTableModel tableModel = new DefaultTableModel();
            tableModel.setColumnIdentifiers(createColumnIdentifiers());
            Field[] fields = Class.forName("business.BaseProduct").getDeclaredFields();
            Object[] rows = new Object[tableModel.getColumnCount()];
            List<MenuItem> list = products.stream().filter(product -> product instanceof BaseProduct).collect(Collectors.toList());
            List<BaseProduct> bplist = new ArrayList<>();
            list.forEach(p -> bplist.add((BaseProduct) p));
            bplist.forEach(p -> {
                try {
                    int i = 0;
                    for (Field f : fields) {
                        f.setAccessible(true);
                        rows[i++] = f.get(p);
                    }
                    tableModel.addRow(rows);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            });
            list = products.stream().filter(product -> product instanceof CompositeProduct).collect(Collectors.toList());
            List<CompositeProduct> cplist = new ArrayList<>();
            list.forEach(p -> cplist.add((CompositeProduct) p));
            cplist.forEach(cp -> {
                rows[0] = cp.getTitle();
                for (int i = 1; i < tableModel.getColumnCount() - 1; i++) rows[i] = "";
                rows[tableModel.getColumnCount() - 1] = String.valueOf(cp.computePrice());
                tableModel.addRow(rows);
                cp.getProducts().forEach(pr -> {
                    try {
                        int i = 0;
                        for (Field f : fields) {
                            f.setAccessible(true);
                            rows[i++] = f.get(pr);
                        }
                        tableModel.addRow(rows);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                });
            });
            tableOrder.setModel(tableModel);
            int price = products.stream().mapToInt(MenuItem::computePrice).sum();
            totalCostLabel.setText("Total cost: " + price);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void compositeProductMouseListener() {
        productsTable.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (compositeProductsToggle.isSelected()) {
                    int selectedRow = productsTable.getSelectedRow();
                    if (selectedRow != -1) {
                        int i1 = selectedRow, i2 = selectedRow, max = productsTable.getRowCount();
                        while (i1 < max && productsTable.getValueAt(i1, 1) != null && !String.valueOf(productsTable.getValueAt(i1, 1)).equals(""))
                            i1++;
                        while (i2 > 0 && productsTable.getValueAt(i2, 1) != null && !String.valueOf(productsTable.getValueAt(i2, 1)).equals(""))
                            i2--;
                        productsTable.setRowSelectionInterval(i2, i1 - 1);
                    }
                }
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

    private void setTableProductsBaseProducts(List<BaseProduct> products) {
        try {
            DefaultTableModel tableModel = new DefaultTableModel();
            tableModel.setColumnIdentifiers(createColumnIdentifiers());
            Field[] fields = Class.forName("business.BaseProduct").getDeclaredFields();
            Object[] rows = new Object[tableModel.getColumnCount()];
            products.forEach(p -> {
                try {
                    int i = 0;
                    for (Field f : fields) {
                        f.setAccessible(true);
                        rows[i++] = f.get(p);
                    }
                    tableModel.addRow(rows);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            });
            productsTable.setModel(tableModel);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setTableProductsCompositeProducts(List<CompositeProduct> products) {
        try {
            DefaultTableModel tableModel = new DefaultTableModel();
            tableModel.setColumnIdentifiers(createColumnIdentifiers());
            Field[] fields = Class.forName("business.BaseProduct").getDeclaredFields();
            Object[] rows = new Object[tableModel.getColumnCount()];
            products.forEach(cp -> {
                rows[0] = cp.getTitle();
                for (int i = 1; i < tableModel.getColumnCount() - 1; i++) rows[i] = "";
                rows[tableModel.getColumnCount() - 1] = String.valueOf(cp.computePrice());
                tableModel.addRow(rows);
                cp.getProducts().forEach(pr -> {
                    try {
                        int i = 0;
                        for (Field f : fields) {
                            f.setAccessible(true);
                            rows[i++] = f.get(pr);
                        }
                        tableModel.addRow(rows);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                });
            });
            productsTable.setModel(tableModel);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Object[] createColumnIdentifiers() {
        try {
            Field[] fields = Class.forName("business.BaseProduct").getDeclaredFields();
            int size = fields.length;
            Object[] columnsIdentifiers = new Object[size];
            int indx = 0;
            for (Field field : fields)
                columnsIdentifiers[indx++] = field.getName();
            return columnsIdentifiers;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
