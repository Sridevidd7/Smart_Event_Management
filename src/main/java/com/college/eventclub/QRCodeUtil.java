package com.college.eventclub;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.UUID;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

/**
 * QRCodeUtil - Utility class for QR Code generation and handling
 */
public class QRCodeUtil {

    private static final int QR_WIDTH = 300;
    private static final int QR_HEIGHT = 300;

    /**
     * Generate unique QR token
     */
    public static String generateQRToken() {
        return UUID.randomUUID().toString();
    }

    /**
     * Generate QR Code image from text
     */
    public static BufferedImage generateQRCodeImage(String text) throws Exception {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT);
        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }

    /**
     * Save QR Code image to file
     */
    public static String saveQRCodeImage(String qrToken, String fileName) {
        try {
            BufferedImage qrImage = generateQRCodeImage(qrToken);
            String filePath = "qrcodes/" + fileName + ".png";
            
            // Create directory if it doesn't exist
            File dir = new File("qrcodes");
            if (!dir.exists()) {
                dir.mkdirs();
            }
            
            File outputFile = new File(filePath);
            javax.imageio.ImageIO.write(qrImage, "png", outputFile);
            return filePath;
        } catch (Exception e) {
            System.err.println("Error saving QR code: " + e.getMessage());
            return null;
        }
    }

    /**
     * Display QR Code in a dialog
     */
    public static void displayQRCode(String qrToken, String title) {
        try {
            BufferedImage qrImage = generateQRCodeImage(qrToken);
            
            // Create image icon
            ImageIcon imageIcon = new ImageIcon(qrImage);
            
            // Create label with image
            JLabel imageLabel = new JLabel(imageIcon);
            
            // Create panel
            JPanel panel = new JPanel();
            panel.add(imageLabel);
            
            // Show dialog
            JOptionPane.showMessageDialog(
                null,
                panel,
                title,
                JOptionPane.INFORMATION_MESSAGE
            );
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                null,
                "Error displaying QR Code: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Display QR Code with additional info
     */
    public static void displayQRCodeWithInfo(String qrToken, String title, String info) {
        try {
            BufferedImage qrImage = generateQRCodeImage(qrToken);
            ImageIcon imageIcon = new ImageIcon(qrImage);
            JLabel imageLabel = new JLabel(imageIcon);
            
            // Create panel with info and QR code
            JPanel panel = new JPanel(new BorderLayout());
            
            JTextArea infoArea = new JTextArea(5, 30);
            infoArea.setText(info);
            infoArea.setEditable(false);
            infoArea.setLineWrap(true);
            infoArea.setWrapStyleWord(true);
            
            panel.add(new JScrollPane(infoArea), BorderLayout.NORTH);
            panel.add(imageLabel, BorderLayout.CENTER);
            
            JOptionPane.showMessageDialog(
                null,
                panel,
                title,
                JOptionPane.INFORMATION_MESSAGE
            );
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                null,
                "Error displaying QR Code: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
