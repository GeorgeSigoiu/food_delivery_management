package presentation;

import javax.swing.JFrame;
import java.awt.*;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

/**
 * Interfata grafica pentru afisarea de rapoarte create de administrator
 */
public class ReportWindow {

    private JFrame frmReports;

    public ReportWindow(String mesaj) {
        initialize(mesaj);
        frmReports.setVisible(true);
    }
    /**
     * Se initializeaza toate componentele ferestrei
     */
    private void initialize(String mesaj) {
        frmReports = new JFrame();
        frmReports.setTitle("Reports");
        frmReports.getContentPane().setBackground(SystemColor.activeCaption);
        frmReports.getContentPane().setLayout(new BorderLayout(0, 0));

        JScrollPane scrollPane = new JScrollPane();
        frmReports.getContentPane().add(scrollPane, BorderLayout.CENTER);

        JTextArea textArea = new JTextArea();
        textArea.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        scrollPane.setViewportView(textArea);
        textArea.setText(mesaj);
        frmReports.setBounds(100, 100, 450, 300);
        frmReports.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        centreWindow(frmReports);
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
}

