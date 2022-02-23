package presentation;

import business.BaseProduct;
import business.CompositeProduct;
import business.DeliveryService;

import java.awt.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * Reprezinta interfata pentru creare a produselor compuse (CompositeProduct)
 */
public class CreateProductView {

    private JFrame frmCreateProducts;
    private JTextField searchTxtField, titleTxtField;
    private JTable productsTable;
    private JTable insertedProductsTable;
    private JButton moveBtn, createBtn, deleteBtn, resetBtn, searchBtn;
    private JComboBox criteriaBox;
    private List<BaseProduct> products = new ArrayList<>();

    public CreateProductView() {
        initialize();
        frmCreateProducts.setVisible(true);
    }

    /**
     * Se initializeaza toate componentele ferestrei
     */
    private void initialize() {
        frmCreateProducts = new JFrame();
        frmCreateProducts.setTitle("Create products");
        frmCreateProducts.getContentPane().setBackground(SystemColor.activeCaption);
        frmCreateProducts.getContentPane().setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 79, 418, 336);
        frmCreateProducts.getContentPane().add(scrollPane);

        productsTable = new JTable();
        scrollPane.setViewportView(productsTable);

        JScrollPane scrollPane_1 = new JScrollPane();
        scrollPane_1.setBounds(506, 120, 375, 233);
        frmCreateProducts.getContentPane().add(scrollPane_1);

        insertedProductsTable = new JTable();
        setInsertedProductsTable();
        scrollPane_1.setViewportView(insertedProductsTable);

        moveBtn = new JButton(">>");
        moveBtn.setBounds(438, 192, 58, 21);
        frmCreateProducts.getContentPane().add(moveBtn);

        createBtn = new JButton("Create product");
        createBtn.setBounds(501, 363, 129, 21);
        frmCreateProducts.getContentPane().add(createBtn);

        deleteBtn = new JButton("Delete selected");
        deleteBtn.setBounds(501, 394, 129, 21);
        frmCreateProducts.getContentPane().add(deleteBtn);

        resetBtn = new JButton("Reset");
        resetBtn.setBounds(777, 363, 85, 21);
        frmCreateProducts.getContentPane().add(resetBtn);

        searchTxtField = new JTextField();
        searchTxtField.setBounds(116, 40, 206, 19);
        frmCreateProducts.getContentPane().add(searchTxtField);
        searchTxtField.setColumns(10);

        criteriaBox = new JComboBox();
        setComboBox();
        criteriaBox.setBounds(10, 39, 96, 21);
        frmCreateProducts.getContentPane().add(criteriaBox);

        searchBtn = new JButton("Search");
        searchBtn.setBounds(343, 39, 85, 21);
        frmCreateProducts.getContentPane().add(searchBtn);

        JLabel lblNewLabel = new JLabel("Title");
        lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lblNewLabel.setBounds(517, 81, 71, 29);
        frmCreateProducts.getContentPane().add(lblNewLabel);

        titleTxtField = new JTextField();
        titleTxtField.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        titleTxtField.setBounds(584, 79, 143, 29);
        frmCreateProducts.getContentPane().add(titleTxtField);
        titleTxtField.setColumns(10);
        frmCreateProducts.setBounds(100, 100, 900, 500);
        frmCreateProducts.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        centreWindow(frmCreateProducts);
        searchTxtField.setText("");
        titleTxtField.setText("");
        addResetActionListener();
        addMoveActionListener();
        addSearchActionListener();
        addCreateActionListener();
        addDeleteActionListener();
        setTable(productsTable, DeliveryService.getBaseProducts().stream().sorted((p1, p2) -> p1.getTitle().toLowerCase().compareTo(p2.getTitle().toLowerCase())).collect(Collectors.toList()));
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
     * Creeaza combobox-ul pentru search
     */
    private void setComboBox() {
        DefaultComboBoxModel model = new DefaultComboBoxModel(new Object[]{"Title", "Rating", "Calories", "Fat", "Sodium", "Price"});
        criteriaBox.setModel(model);
    }

    /**
     * Creeaza capul tabelului pentru produsele simple
     *
     * @return vector de Object, reprezinta valorile capului de tabel
     */
    private Object[] createColumnIdentifiers() {
        try {
            Field[] fields = Class.forName("business.BaseProduct").getDeclaredFields();
            int size = fields.length;
            Object[] columnsIdentifiers = new Object[size];
            int indx = 0;
            for (Field field : fields) {
                columnsIdentifiers[indx++] = field.getName();
            }
            return columnsIdentifiers;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Adauga ActionListener pe butonul de reset
     */
    private void addResetActionListener() {
        resetBtn.addActionListener(e -> {
            setInsertedProductsTable();
            titleTxtField.setText("");
            products = new ArrayList<>();
        });
    }

    /**
     * Afiseaza tabelul gol, continand doar capul de tabel
     */
    private void setInsertedProductsTable() {
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(createColumnIdentifiers());
        insertedProductsTable.setModel(tableModel);
    }

    /**
     * Adauga ActionListener pe butonul de mutare al unui produs simplu din tabelul cu toate produsele in tabelul cu produsele
     * care vor alcatui un nou produs compus
     */
    private void addMoveActionListener() {
        moveBtn.addActionListener(e -> {
            int[] selectedRows = productsTable.getSelectedRows();
            for (int i : selectedRows) {
                BaseProduct p = getProduct(productsTable, i);
                products.add(p);
            }
            setTable(insertedProductsTable, products.stream().sorted((p1, p2) -> p1.getTitle().toLowerCase().compareTo(p2.getTitle().toLowerCase())).collect(Collectors.toList()));
        });
    }

    /**
     * Populeaza un anumit tabel cu valorile dintr-o lista de produse
     * @param table JTable, tabelul care va fi populat
     * @param products lista de BaseProduct, produsele cu care se va popula tabelul
     */
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

    /**
     * Obtine produsul simplu dintr-un anumit table, de pe o anumita linie
     * @param table JTable, tabelul din care se extrage produsul
     * @param i Integer, randul de pe care se extrage produsul
     * @return BaseProduct, produsul dorit
     */
    private BaseProduct getProduct(JTable table, int i) {
        String title = (String) table.getModel().getValueAt(i, 0);
        Double rating = Double.valueOf(String.valueOf(table.getModel().getValueAt(i, 1)));
        Integer calories = Integer.valueOf(String.valueOf(table.getModel().getValueAt(i, 2)));
        Integer protein = Integer.valueOf(String.valueOf(table.getModel().getValueAt(i, 3)));
        Integer fat = Integer.valueOf(String.valueOf(table.getModel().getValueAt(i, 4)));
        Integer sodium = Integer.valueOf(String.valueOf(table.getModel().getValueAt(i, 5)));
        Integer price = Integer.valueOf(String.valueOf(table.getModel().getValueAt(i, 6)));
        BaseProduct p = new BaseProduct(title, rating, calories, protein, fat, sodium, price);
        return p;
    }

    /**
     * Adauga ActionListener pe butonul de search
     */
    private void addSearchActionListener() {
        searchBtn.addActionListener(e -> {
            if (searchTxtField.getText().equals("")) {
                setTable(productsTable, DeliveryService.getBaseProducts().stream().sorted((p1, p2) -> p1.getTitle().toLowerCase().compareTo(p2.getTitle().toLowerCase())).collect(Collectors.toList()));
                return;
            }
            List<BaseProduct> products = DeliveryService.getBaseProducts();
            String name;
            int minInt = 0, maxInt = 99999999;
            double minDouble = 0.0, maxDouble = 99999999.0;
            List<BaseProduct> searchedProducts = new ArrayList<>();
            if (String.valueOf(criteriaBox.getSelectedItem()).equals("Title")) {
                name = searchTxtField.getText();
                searchedProducts = products.stream().filter(p -> p.getTitle().toLowerCase().contains(name.toLowerCase())).collect(Collectors.toList());
            } else if (String.valueOf(criteriaBox.getSelectedItem()).equals("Rating")) {
                String sir = searchTxtField.getText();
                if (sir.contains("<")) {
                    String parse = sir.replace(" ", "").replace("<", "");
                    maxDouble = Double.parseDouble(parse);
                }
                if (sir.contains(">")) {
                    String parse = sir.replace(" ", "").replace(">", "");
                    minDouble = Double.parseDouble(parse);
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
                    }
                    if (sir.contains(">")) {
                        String parse = sir.replace(" ", "").replace(">", "");
                        minInt = Integer.parseInt(parse);
                    }
                    searchedProducts = detProductsIntInterv(minInt, maxInt);
                } else {
                    searchedProducts = detProductsIntVal(Integer.parseInt(sir));
                }
            }
            setTable(productsTable, searchedProducts.stream().sorted((p1, p2) -> p1.getTitle().toLowerCase().compareTo(p2.getTitle().toLowerCase())).collect(Collectors.toList()));
        });
    }

    /**
     * Determina produsele care indeplinesc conditia de a avea valoarea data ca parametru in functie de ce criteriu
     * de cautare a fost ales
     * @param val Integer, valoarea cautata la produse
     * @return lista de produse simple, reprezinta rezultatul cautarii
     */
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

    /**
     * Determina produsele care indeplinesc conditia de a avea valori in intervalul [minInt, maxInt] in functie de ce criteriu
     * de cautare a fost ales
     * @param minInt Integer, valoarea minima a intervalului
     * @param maxInt Integer, valoarea maxima a intervalului
     * @return lista de produse simple, reprezinta rezultatul cautarii
     */
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

    /**
     * Adauga ActionListener pe butonul de creare
     */
    private void addCreateActionListener() {
        createBtn.addActionListener(e -> {
            if (titleTxtField.getText().equals("")) {
                JOptionPane.showMessageDialog(createBtn, "Choose a title for composite product!", "Warning", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            CompositeProduct cp = new CompositeProduct(titleTxtField.getText(), products);
            DeliveryService ds = new DeliveryService();
            ds.addNewProduct(cp);
            setInsertedProductsTable();
            products = new ArrayList<>();
            titleTxtField.setText("");
            JOptionPane.showMessageDialog(createBtn, "Composite product added!", "Success", JOptionPane.INFORMATION_MESSAGE);
        });
    }

    /**
     * Adauga ActionListener pe butonul de delete
     */
    private void addDeleteActionListener() {
        deleteBtn.addActionListener(e -> {
            int[] selectedRows = insertedProductsTable.getSelectedRows();
            for (int i : selectedRows)
                products.remove(i);
            setTable(insertedProductsTable, products.stream().sorted((p1, p2) -> p1.getTitle().toLowerCase().compareTo(p2.getTitle().toLowerCase())).collect(Collectors.toList()));
        });
    }

}
