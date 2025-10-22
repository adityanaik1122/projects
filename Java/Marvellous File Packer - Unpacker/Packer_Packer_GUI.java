import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

// Your existing MarvellousPacker class with minor enhancement (prints redirected to GUI)
class MarvellousPacker {
    private String PackName;
    private String DirName;
    private JTextArea outputArea;  // To send logs to GUI

    public MarvellousPacker(String packName, String dirName, JTextArea outputArea) {
        this.PackName = packName;
        this.DirName = dirName;
        this.outputArea = outputArea;
    }

    private void println(String msg) {
        if (outputArea != null) {
            outputArea.append(msg + "\n");
            outputArea.setCaretPosition(outputArea.getDocument().getLength());
        } else {
            System.out.println(msg);
        }
    }

    public void PackingActivity() {
        try {
            println("--------------------------------------------------------");
            println("----------- Marvellous Packer Unpacker -----------------");
            println("--------------------------------------------------------");
            println("------------------ Packing Activity --------------------");
            println("--------------------------------------------------------");

            File fobj = new File(DirName);

            if ((fobj.exists()) && (fobj.isDirectory())) {
                println(DirName + " is successfully opened");

                File packObj = new File(PackName);
                boolean bRet = packObj.createNewFile();

                if (bRet == false) {
                    println("Unable to create pack file");
                    return;
                }

                println("Packed file gets successfully created with name : " + PackName);

                File[] arr = fobj.listFiles();

                FileOutputStream foobj = new FileOutputStream(packObj);

                byte[] buffer = new byte[1024];
                int iRet = 0;
                int iCountFile = 0;

                // Directory traversal
                for (File file : arr) {
                    String header = file.getName() + " " + file.length();

                    // Loop to form 100 bytes header
                    while (header.length() < 100) {
                        header = header + " ";
                    }

                    foobj.write(header.getBytes());

                    FileInputStream fiobj = new FileInputStream(file);

                    while ((iRet = fiobj.read(buffer)) != -1) {
                        foobj.write(buffer, 0, iRet);

                        println("File name Scanned : " + file.getName());
                        println("File size read is : " + iRet);
                    }

                    fiobj.close();
                    iCountFile++;
                }

                foobj.close();

                println("Packing activity done");

                println("--------------------------------------------------------");
                println("------------------ Statistical Report ------------------");
                println("--------------------------------------------------------");
                println("Total files Packed : " + iCountFile);
                println("--------------------------------------------------------");
                println("--------- Thank you for using our application ----------");
                println("--------------------------------------------------------");
            } else {
                println("There is no such directory");
            }
        } catch (Exception eobj) {
            println("Error occurred: " + eobj.getMessage());
            eobj.printStackTrace();
        }
    } // End of PackingActivity
}


// GUI Frame class
public class Packer_Packer_GUI extends JFrame {

    private JTextField dirTextField, packTextField;
    private JButton browseDirButton, browsePackButton, packButton;
    private JTextArea outputArea;

    public Packer_Packer_GUI() {
        setTitle("Marvellous Packer Unpacker GUI");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Marvellous Packer Unpacker");
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        add(titleLabel, gbc);

        gbc.gridwidth = 1;

        // Directory Label and field
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Directory to Pack:"), gbc);

        dirTextField = new JTextField(30);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(dirTextField, gbc);

        browseDirButton = new JButton("Browse...");
        gbc.gridx = 2;
        gbc.gridy = 1;
        add(browseDirButton, gbc);

        // Pack file Label and field
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Pack File Name:"), gbc);

        packTextField = new JTextField(30);
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(packTextField, gbc);

        browsePackButton = new JButton("Browse...");
        gbc.gridx = 2;
        gbc.gridy = 2;
        add(browsePackButton, gbc);

        // Pack button (process)
        packButton = new JButton("Start Packing");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 3;
        add(packButton, gbc);

        // Output text area with scroll pane
        outputArea = new JTextArea(15, 60);
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.BOTH;
        add(scrollPane, gbc);

        // Action listeners

        browseDirButton.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int res = chooser.showOpenDialog(this);
            if (res == JFileChooser.APPROVE_OPTION) {
                File selected = chooser.getSelectedFile();
                dirTextField.setText(selected.getAbsolutePath());
            }
        });

        browsePackButton.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Select pack file to create");
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int res = chooser.showSaveDialog(this);
            if (res == JFileChooser.APPROVE_OPTION) {
                File selected = chooser.getSelectedFile();
                packTextField.setText(selected.getAbsolutePath());
            }
        });

        packButton.addActionListener(e -> {
            String dirName = dirTextField.getText().trim();
            String packName = packTextField.getText().trim();

            outputArea.setText(""); // clear previous output

            if (dirName.isEmpty() || packName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter both Directory and Pack file name.",
                        "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Run packing in separate thread to avoid UI freeze
            new Thread(() -> {
                MarvellousPacker packer = new MarvellousPacker(packName, dirName, outputArea);
                packer.PackingActivity();
            }).start();
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Packer_Packer_GUI frame = new Packer_Packer_GUI();
            frame.setVisible(true);
        });
    }
}
