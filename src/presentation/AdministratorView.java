package presentation;

import business.*;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.*;
import java.awt.SystemColor;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.awt.event.ActionEvent;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.table.DefaultTableModel;

/**
 * Interfata grafica pentru administrator
 */
public class AdministratorView {
    private File file;
    private JFrame frmAdministratorWindow;
    private JTable tableProducts, tableNewProduct;
    private JTextField crt4DayTxtField;
    private JComboBox criteriaBox;
    private JToggleButton baseProductsToggle, compositeProductsToggle;
    private final JCheckBox[] check = new JCheckBox[4];
    private JButton editBtn, saveBtn, cancelBtn, deleteBtn, addProductBtn, importBtn, generateBtn, createBtn, viewOrdersBtn;
    private JSpinner crt3ValueSpinner, crt3TimesSpinner, crt2Spinner, crt1EndSpinner, crt1StartSpinner;
    private DeliveryService ds = new DeliveryService();

    public AdministratorView() {
        initialize();
        frmAdministratorWindow.setVisible(true);
    }

    /**
     * Se initializeaza toate componentele ferestrei
     */
    private void initialize() {
        frmAdministratorWindow = new JFrame();
        frmAdministratorWindow.setTitle("Administrator window");
        frmAdministratorWindow.getContentPane().setBackground(SystemColor.activeCaption);
        frmAdministratorWindow.getContentPane().setLayout(null);

        JLabel importLabel = new JLabel("Import products");
        importLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        importLabel.setBounds(51, 21, 211, 29);
        frmAdministratorWindow.getContentPane().add(importLabel);

        importBtn = new JButton("Import");
        addImportActionListener();
        importBtn.setBounds(196, 26, 85, 21);
        frmAdministratorWindow.getContentPane().add(importBtn);


        viewOrdersBtn = new JButton("View orders");
        addViewOrderActionListener();
        viewOrdersBtn.setBounds(600, 26, 120, 21);
        frmAdministratorWindow.getContentPane().add(viewOrdersBtn);

        JLabel lblNewLabel = new JLabel("..........................................................................................................................................................................................................................................................................................................................................................");
        lblNewLabel.setBounds(10, 44, 1016, 21);
        frmAdministratorWindow.getContentPane().add(lblNewLabel);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(20, 120, 624, 405);
        frmAdministratorWindow.getContentPane().add(scrollPane);

        tableProducts = new JTable();
        scrollPane.setViewportView(tableProducts);

        baseProductsToggle = new JToggleButton("Base Products");
        baseProductsToggle.setBounds(30, 524, 222, 21);
        frmAdministratorWindow.getContentPane().add(baseProductsToggle);

        compositeProductsToggle = new JToggleButton("Composite Products");
        compositeProductsToggle.setBounds(245, 524, 203, 21);
        frmAdministratorWindow.getContentPane().add(compositeProductsToggle);

        JScrollPane scrollPane1 = new JScrollPane();
        scrollPane1.setBounds(20, 555, 624, 40);
        frmAdministratorWindow.getContentPane().add(scrollPane1);

        tableNewProduct = new JTable();
        setTableNewProduct();
        scrollPane1.setViewportView(tableNewProduct);

        addProductBtn = new JButton("Add");
        addProductBtn.setBounds(20, 605, 85, 21);
        frmAdministratorWindow.getContentPane().add(addProductBtn);

        editBtn = new JButton("Edit");
        editBtn.setBounds(654, 226, 85, 21);
        frmAdministratorWindow.getContentPane().add(editBtn);

        saveBtn = new JButton("Save");
        saveBtn.setBounds(654, 257, 85, 21);
        frmAdministratorWindow.getContentPane().add(saveBtn);

        cancelBtn = new JButton("Cancel");
        cancelBtn.setBounds(654, 288, 85, 21);
        frmAdministratorWindow.getContentPane().add(cancelBtn);

        deleteBtn = new JButton("Delete");
        deleteBtn.setBounds(654, 319, 85, 21);
        frmAdministratorWindow.getContentPane().add(deleteBtn);

        JLabel lblNewLabel1 = new JLabel("Manage products");
        lblNewLabel1.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        lblNewLabel1.setBounds(71, 75, 211, 29);
        frmAdministratorWindow.getContentPane().add(lblNewLabel1);

        JLabel lblNewLabel2 = new JLabel("Generate reports");
        lblNewLabel2.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        lblNewLabel2.setBounds(753, 75, 211, 29);
        frmAdministratorWindow.getContentPane().add(lblNewLabel2);

        check[0] = new JCheckBox("Time interval");
        check[0].setFont(new Font("Times New Roman", Font.PLAIN, 14));
        check[0].setBackground(SystemColor.activeCaption);
        check[0].setBounds(760, 124, 210, 32);
        frmAdministratorWindow.getContentPane().add(check[0]);

        check[1] = new JCheckBox("Products ordered 'n' times");
        check[1].setFont(new Font("Times New Roman", Font.PLAIN, 14));
        check[1].setBackground(SystemColor.activeCaption);
        check[1].setBounds(760, 203, 210, 29);
        frmAdministratorWindow.getContentPane().add(check[1]);

        check[2] = new JCheckBox("Clients that have ordered 'n' times");
        check[2].setFont(new Font("Times New Roman", Font.PLAIN, 14));
        check[2].setBackground(SystemColor.activeCaption);
        check[2].setBounds(760, 257, 230, 32);
        frmAdministratorWindow.getContentPane().add(check[2]);

        check[3] = new JCheckBox("Products ordered in a specific day");
        check[3].setFont(new Font("Times New Roman", Font.PLAIN, 14));
        check[3].setBackground(SystemColor.activeCaption);
        check[3].setBounds(760, 333, 230, 32);
        frmAdministratorWindow.getContentPane().add(check[3]);

        crt1StartSpinner = new JSpinner();
        crt1StartSpinner.setModel(new SpinnerNumberModel(0, 0, 24, 1));
        crt1StartSpinner.setBounds(891, 156, 56, 20);
        frmAdministratorWindow.getContentPane().add(crt1StartSpinner);

        crt1EndSpinner = new JSpinner();
        crt1EndSpinner.setBounds(891, 180, 56, 20);
        frmAdministratorWindow.getContentPane().add(crt1EndSpinner);

        JLabel lblNewLabel3 = new JLabel("Start hour");
        lblNewLabel3.setBounds(806, 159, 75, 13);
        frmAdministratorWindow.getContentPane().add(lblNewLabel3);

        JLabel lblNewLabel4 = new JLabel("End hour");
        lblNewLabel4.setBounds(806, 183, 56, 13);
        frmAdministratorWindow.getContentPane().add(lblNewLabel4);

        JLabel lblNewLabel5 = new JLabel("Times ordered");
        lblNewLabel5.setBounds(806, 242, 85, 13);
        frmAdministratorWindow.getContentPane().add(lblNewLabel5);

        crt2Spinner = new JSpinner();
        crt2Spinner.setBounds(891, 239, 56, 20);
        frmAdministratorWindow.getContentPane().add(crt2Spinner);

        JLabel lblNewLabel6 = new JLabel("Times ordered");
        lblNewLabel6.setBounds(796, 292, 85, 13);
        frmAdministratorWindow.getContentPane().add(lblNewLabel6);

        crt3TimesSpinner = new JSpinner();
        crt3TimesSpinner.setBounds(891, 289, 56, 20);
        frmAdministratorWindow.getContentPane().add(crt3TimesSpinner);

        JLabel lblNewLabel7 = new JLabel("Min. value");
        lblNewLabel7.setBounds(796, 315, 75, 13);
        frmAdministratorWindow.getContentPane().add(lblNewLabel7);

        crt3ValueSpinner = new JSpinner();
        crt3ValueSpinner.setBounds(891, 312, 56, 20);
        frmAdministratorWindow.getContentPane().add(crt3ValueSpinner);

        JLabel lblNewLabel9 = new JLabel("Day(dd/mm/yyyy)");
        lblNewLabel9.setBounds(792, 368, 103, 13);
        frmAdministratorWindow.getContentPane().add(lblNewLabel9);

        generateBtn = new JButton("Generate");
        generateBtn.setBounds(825, 429, 103, 21);
        frmAdministratorWindow.getContentPane().add(generateBtn);

        crt4DayTxtField = new JTextField();
        crt4DayTxtField.setForeground(SystemColor.desktop);
        crt4DayTxtField.setToolTipText("");
        crt4DayTxtField.setBounds(905, 365, 85, 19);
        frmAdministratorWindow.getContentPane().add(crt4DayTxtField);
        crt4DayTxtField.setColumns(10);

        JLabel lblNewLabel_4 = new JLabel("Create new composed product");
        lblNewLabel_4.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        lblNewLabel_4.setBounds(428, 600, 216, 29);
        frmAdministratorWindow.getContentPane().add(lblNewLabel_4);

        createBtn = new JButton("Create ");
        createBtn.setBounds(627, 605, 85, 21);
        frmAdministratorWindow.getContentPane().add(createBtn);

        tableProducts.setEnabled(false);
        setBtnsVisibility(false);
        addCancelActionListener();
        addEditActionListener();
        addSaveActionListener();
        addDeleteActionListener();
        addToggleButton1ActionListener();
        addToggleButton2ActionListener();
        addGenerateActionListener();
        addNewProductActionListener();
        addCreateActionListener();

        frmAdministratorWindow.setBounds(100, 100, 1050, 700);
        centreWindow(frmAdministratorWindow);
        frmAdministratorWindow.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }

    /**
     * Deschide o interfata in care se pot vizualiza comenzile plasate de catre clienti
     */
    private void addViewOrderActionListener() {
        viewOrdersBtn.addActionListener(e -> new OrdersView());
    }

    /**
     * Initializeaza tabelul pentru adaugarea unui produs nou
     */
    private void setTableNewProduct() {
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(createColumnIdentifiers());
        Object[] row = new Object[tableModel.getColumnCount()];
        tableModel.addRow(row);
        tableNewProduct.setModel(tableModel);
    }

    /**
     * Afiseaza in tableul principal produsele simple
     *
     * @param products lista de produse simple
     */
    private void setTableProductsBaseProducts(List<BaseProduct> products) {
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(createColumnIdentifiers());
        Field[] fields = new Field[0];
        try {
            fields = Class.forName("business.BaseProduct").getDeclaredFields();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Field[] finalFields = fields;
        Object[] rows = new Object[tableModel.getColumnCount()];
        products.forEach(p -> {
            try {
                int i = 0;
                for (Field f : finalFields) {
                    f.setAccessible(true);
                    rows[i] = f.get(p);
                    i++;
                }
                tableModel.addRow(rows);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        tableProducts.setModel(tableModel);
    }

    /**
     * Afiseaza in tabelul principal produsele compuse
     *
     * @param products lista de produse compuse
     */
    private void setTableProductsCompositeProducts(List<CompositeProduct> products) {
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(createColumnIdentifiers());
        Field[] fields = new Field[0];
        try {
            fields = Class.forName("business.BaseProduct").getDeclaredFields();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Field[] finalFields = fields;
        Object[] rows = new Object[tableModel.getColumnCount()];
        products.forEach(cp -> {
            rows[0] = cp.getTitle();
            for (int i = 1; i < tableModel.getColumnCount() - 1; i++)
                rows[i] = "";
            rows[tableModel.getColumnCount() - 1] = String.valueOf(cp.computePrice());
            tableModel.addRow(rows);
            cp.getProducts().forEach(pr -> {
                try {
                    int i = 0;
                    for (Field f : finalFields) {
                        f.setAccessible(true);
                        rows[i] = f.get(pr);
                        i++;
                    }
                    tableModel.addRow(rows);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            });
        });
        tableProducts.setModel(tableModel);
    }

    /**
     * Crearea numelor capetelor tabelelor
     *
     * @return Object[]
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
     * Adaugare ActionListener pe butonul create pentru deschiderea unei ferestre in care se creeaza produsele compuse
     */
    private void addCreateActionListener() {
        createBtn.addActionListener(e -> {
            new CreateProductView();
        });
    }

    /**
     * Adaugare ActionListener pe butonul de cancel pentru a anula modificarle aduse produselor
     */
    private void addCancelActionListener() {
        cancelBtn.addActionListener(e -> {
            setBtnsVisibility(false);
            tableProducts.setEnabled(false);
            if (compositeProductsToggle.isSelected())
                setTableProductsCompositeProducts(DeliveryService.getCompositeProducts().stream().sorted(Comparator.comparing(cp -> cp.getTitle().toLowerCase())).collect(Collectors.toList()));
            else
                setTableProductsBaseProducts(DeliveryService.getBaseProducts().stream().sorted(Comparator.comparing(p -> p.getTitle().toLowerCase())).collect(Collectors.toList()));
        });
    }

    /**
     * Adaugare ActionListener pentru butonul de adaugare a unui produs simplu nou in memorie
     */
    private void addNewProductActionListener() {
        addProductBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int columns = tableNewProduct.getColumnCount();
                for (int i = 0; i < columns; i++) {
                    Object col = tableNewProduct.getValueAt(0, i);
                    if (col == null || ((String) col).length() == 0) {
                        JOptionPane.showMessageDialog(addProductBtn, "All fileds need to be completed!", "Warning", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                }
                BaseProduct p = getBaseProduct(tableNewProduct, 0);
                ds.addNewProduct(p);
                setTableNewProduct();
            }
        });
    }

    /**
     * Obtine un produs simplu din tabele scrise in tabel
     *
     * @param table JTable, tabelul din care se incearca crearea produsului
     * @param i     int, numarul liniei unde se afla produsul
     * @return BaseProduct
     */
    private BaseProduct getBaseProduct(JTable table, int i) {
        String title = (String) table.getModel().getValueAt(i, 0);
        Double rating = Double.valueOf(String.valueOf(table.getModel().getValueAt(i, 1)));
        Integer calories = Integer.valueOf(String.valueOf(table.getModel().getValueAt(i, 2)));
        Integer protein = Integer.valueOf(String.valueOf(table.getModel().getValueAt(i, 3)));
        ;
        Integer fat = Integer.valueOf(String.valueOf(table.getModel().getValueAt(i, 4)));
        Integer sodium = Integer.valueOf(String.valueOf(table.getModel().getValueAt(i, 5)));
        Integer price = Integer.valueOf(String.valueOf(table.getModel().getValueAt(i, 6)));
        return new BaseProduct(title, rating, calories, protein, fat, sodium, price);
    }

    /**
     * Adaugare ActionListener pe butonul de generate - genereaza rapoarte in functie de ce optiuni sunt selectate
     */
    private void addGenerateActionListener() {
        generateBtn.addActionListener(e -> {
            if (check[0].isSelected()) {
                ds.generateTimeIntervalOrderReport(Integer.parseInt(String.valueOf(crt1StartSpinner.getValue())), Integer.parseInt(String.valueOf(crt1EndSpinner.getValue())));
            }
            if (check[1].isSelected()) {
                ds.generateOrderReport(Integer.parseInt(String.valueOf(crt2Spinner.getValue())));
            }
            if (check[2].isSelected()) {
                ds.generateOrderReport(Integer.parseInt(String.valueOf(crt3TimesSpinner.getValue())), Integer.parseInt(String.valueOf(crt3ValueSpinner.getValue())));
            }
            if (check[3].isSelected()) {
                String dty = crt4DayTxtField.getText();
                ds.generateOrderReport(dty);
            }
        });
    }

    /**
     * Adaugare actionListener pe butonul de toggle pentru produsele simple. Daca butonul este apasat, tabelul va afisa doar
     * produsele simple
     */
    private void addToggleButton1ActionListener() {
        baseProductsToggle.addActionListener(e -> {
            compositeProductsToggle.setSelected(false);
            baseProductsToggle.setSelected(true);
            setTableProductsBaseProducts(DeliveryService.getBaseProducts().stream().sorted(Comparator.comparing(p -> p.getTitle().toLowerCase())).collect(Collectors.toList()));
        });
    }

    /**
     * Adaugare actionListener pe butonul de toggle pentru produsele compuse. Daca butonul este apasat, tabelul va afisa doar
     * produsele compuse
     */
    private void addToggleButton2ActionListener() {
        compositeProductsToggle.addActionListener(e -> {
            compositeProductsToggle.setSelected(true);
            baseProductsToggle.setSelected(false);
            setTableProductsCompositeProducts(DeliveryService.getCompositeProducts().stream().sorted(Comparator.comparing(cp -> cp.getTitle().toLowerCase())).collect(Collectors.toList()));
        });
        tableProducts.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (compositeProductsToggle.isSelected()) {
                    int selectedRow = tableProducts.getSelectedRow();
                    if (selectedRow != -1) {
                        int i1 = selectedRow, i2 = selectedRow;
                        int max = tableProducts.getRowCount();
                        while (i1 < max && tableProducts.getValueAt(i1, 1) != null && !String.valueOf(tableProducts.getValueAt(i1, 1)).equals("")) {
                            i1++;
                        }
                        while (i2 > 0 && tableProducts.getValueAt(i2, 1) != null && !String.valueOf(tableProducts.getValueAt(i2, 1)).equals("")) {
                            i2--;
                        }
                        tableProducts.setRowSelectionInterval(i2, i1 - 1);
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

    /**
     * Setarea vizibilitatii butoanelor de save, cancel, delete
     *
     * @param val boolean, true daca butoanele sa fie vizibile, false in caz contrar
     */
    private void setBtnsVisibility(boolean val) {
        saveBtn.setVisible(val);
        cancelBtn.setVisible(val);
        deleteBtn.setVisible(val);
    }

    /**
     * Adaugare ActionListener pe butonul de edit - permite editarea produselor
     */
    private void addEditActionListener() {
        editBtn.addActionListener(e -> {
            setBtnsVisibility(true);
            tableProducts.setEnabled(true);
        });
    }

    /**
     * Adaugare ActionListener pe butonul de save - posibilitate de a salva permanent modificarile aduse produselor
     */
    private void addSaveActionListener() {
        saveBtn.addActionListener(e -> {
            setBtnsVisibility(false);
            tableProducts.setEnabled(false);
            if (baseProductsToggle.isSelected()) {
                int size = tableProducts.getRowCount();
                List<BaseProduct> products = new ArrayList<>();
                for (int i = 0; i < size; i++)
                    products.add(getBaseProduct(tableProducts, i));
                ds.editProduct(products);
            }
        });
    }

    /**
     * Adaugare ActionListener pe butonul de delete - posibilitatea de stergere a unor produse
     */
    private void addDeleteActionListener() {
        deleteBtn.addActionListener(e -> {
            if (compositeProductsToggle.isSelected()) {
                int[] rows = tableProducts.getSelectedRows();
                int poz = rows[0];
                CompositeProduct pr = new CompositeProduct(String.valueOf(tableProducts.getValueAt(poz, 0)), new ArrayList<>());
                ds.deleteProduct(pr);
                setTableProductsCompositeProducts(DeliveryService.getCompositeProducts().stream().sorted(Comparator.comparing(cp -> cp.getTitle().toLowerCase())).collect(Collectors.toList()));
            } else {
                int[] rows = tableProducts.getSelectedRows();
                for (int i : rows)
                    ds.deleteProduct(getBaseProduct(tableProducts, i));
                setTableProductsBaseProducts(DeliveryService.getBaseProducts().stream().sorted(Comparator.comparing(p -> p.getTitle().toLowerCase())).collect(Collectors.toList()));
            }
        });
    }

    /**
     * Adaugare ActionListener pe butonul de import. Modifica toate produsele din memorie, schimbandu-le cu cele importate
     */
    private void addImportActionListener() {
        importBtn.addActionListener(e -> {
            final JFileChooser fc = new JFileChooser();
            int returnVal = fc.showOpenDialog(importBtn);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                file = fc.getSelectedFile();
                ds.importProducts(file.getAbsolutePath());
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
}
