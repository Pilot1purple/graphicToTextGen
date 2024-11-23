import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class OCRApp extends JFrame {
    private static final String TESSDATA_PATH = "./tessdata";
    private final JTextArea outputTextArea;

    public OCRApp() {
        setTitle("OCR Anwendung");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        outputTextArea = new JTextArea();
        outputTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputTextArea);
        add(scrollPane, BorderLayout.CENTER);

        JButton selectButton = new JButton("Bild auswählen");
        selectButton.addActionListener(e -> selectAndProcessImage());
        add(selectButton, BorderLayout.SOUTH);
    }

    private void selectAndProcessImage() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            processImage(selectedFile);
        }
    }

    private void processImage(File imageFile) {
        Tesseract tesseract = new Tesseract();
        try {
            tesseract.setDatapath(TESSDATA_PATH);
            tesseract.setLanguage("deu"); // Für Deutsch, ändern Sie dies bei Bedarf

            String text = tesseract.doOCR(imageFile);
            outputTextArea.setText(text);
        } catch (TesseractException e) {
            outputTextArea.setText("Fehler bei der Texterkennung: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new OCRApp().setVisible(true));
    }
}