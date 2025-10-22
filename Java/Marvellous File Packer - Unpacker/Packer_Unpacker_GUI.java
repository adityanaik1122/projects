import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

class MarvellousUnpacker {
    private String PackName;
    private JTextArea outputArea;

    public MarvellousUnpacker(String packName, JTextArea outputArea) {
        this.PackName = packName;
        this.outputArea = outputArea;
    }

    private void println(String msg) {
        if (outputArea != null) {
            SwingUtilities.invokeLater(() -> {
                outputArea.append(msg + "\n");
                outputArea.setCaretPosition(outputArea.getDocument().getLength());
            });
        } else {
            System.out.println(msg);
        }
    }

    public void UnpackingActivity() {
        try {
            println("--------------------------------------------");
            println("-------- Marvellous Packer Unpacker --------");
            println("--------------------------------------------");
            println("------------ UnPacking Activity ------------");
            println("--------------------------------------------");

            File packedFile = new File(PackName);

            if (!packedFile.exists()) {
                println("Unable to access Packed file: " + PackName);
                return;
            }

            println("Packed file successfully opened: " + PackName);

            FileInputStream fiobj = new FileInputStream(packedFile);

            byte[] headerBuffer = new byte[100];
            int iCountFile = 0;
            int iRet = 0;

            // Process each file in packed data
            while ((iRet = fiobj.read(headerBuffer, 0, 100)) != -1) {
                String header = new String(headerBuffer).trim();
                String[] tokens = header.split(" ");

                if (tokens.length < 2) {
                    println("Invalid header read, stopping unpack.");
                    break;
                }

                String fileName = tokens[0];
                int fileSize = Integer.parseInt(tokens[1]);

                File outputFile = new File(fileName);
                if (!outputFile.createNewFile()) {
                    println("Warning: File " + fileName + " already exists and will be overwritten.");
                }

                FileOutputStream foobj = new FileOutputStream(outputFile);

                byte[] fileBuffer = new byte[fileSize];
                int totalRead = 0;
                while (totalRead < fileSize) {
                    int toRead = fileSize - totalRead;
                    int bytesRead = fiobj.read(fileBuffer, totalRead, toRead);
                    if (bytesRead == -1) break;
                    totalRead += bytesRead;
                }

                foobj.write(fileBuffer, 0, totalRead);
                foobj.close();

                println("File unpacked: " + fileName + " (" + totalRead + " bytes)");

                iCountFile++;
            }

            println("---------------------------------------");
            println("---------- Statistical Report ---------");
            println("---------------------------------------");
            println("Total files unpacked: " + iCountFile);
            println("---------------------------------------");
            println("--Thank you for using our application--");
            println("---------------------------------------");

            fiobj.close();

        } catch (Exception e) {
            println("Error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}


public class Packer_Unpacker_GUI extends JFrame {
    private JTextField packedFileTextField;
    private JButton browseButton, unpackButton;
    private JTextArea outputArea;

    public Packer_Unpacker_GUI() {
        setTitle("Marvellous Unpacker GUI");
        setSize(650, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Marvellous Unpacker");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        add(titleLabel, gbc);

        gbc.gridwidth = 1;

        // Packed file input
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Packed File:"), gbc);

        packedFileTextField = new JTextField(30);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(packedFileTextField, gbc);

        browseButton = new JButton("Browse...");
        gbc.gridx = 2;
        gbc.gridy = 1;
        add(browseButton, gbc);

        // Unpack button
        unpackButton = new JButton("Start Unpacking");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        add(unpackButton, gbc);

        // Output text area with scroll pane
        outputArea = new JTextArea(15, 50);
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.BOTH;
        add(scrollPane, gbc);

        // Browse button action
        browseButton.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int result = chooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = chooser.getSelectedFile();
                packedFileTextField.setText(selectedFile.getAbsolutePath());
            }
        });

        // Unpack button action
        unpackButton.addActionListener(e -> {
            String packedFile = packedFileTextField.getText().trim();
            outputArea.setText(""); // Clear output

            if (packedFile.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please select the packed file first.",
                        "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Run unpacking in a separate thread to avoid UI freezing
            new Thread(() -> {
                MarvellousUnpacker unpacker = new MarvellousUnpacker(packedFile, outputArea);
                unpacker.UnpackingActivity();
            }).start();
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Packer_Unpacker_GUI gui = new Packer_Unpacker_GUI();
            gui.setVisible(true);
        });
    }
}
