package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

/**
 * this class is used for setting button listener and in setting frame user can set max number of downloads that are downloading at the same time
 * or application look and feel or blocked addresses
 */
public class SettingHandler implements ActionListener {
    private Downloads downloads;
    private JFrame mainFrame;
    public SettingHandler(JFrame mainFrame ,Downloads downloads){
        this.mainFrame = mainFrame;
        this.downloads = downloads;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        JFrame frame = new JFrame("Setting");
        frame.setLocation(600,600);
        frame.setPreferredSize(new Dimension(850,650));
        frame.setMinimumSize(new Dimension(850,650));
        frame.setLayout(new BorderLayout(5,5));
        JTextField textField1 = new JTextField("MAx Number of download at the same time ");
        textField1.setBackground(new Color(230,230,230));
        textField1.setEditable(false);
        //JTextField textField2 = new JTextField(downloads.getProcessingDownloadsList().getMaximumNumberOfProcessingDownloads()==(-1) ? "Unlimited" : String.valueOf(downloads.getProcessingDownloadsList().getMaximumNumberOfProcessingDownloads()));
        JPanel maxDownload = new JPanel();
        JTextField textField2 = null;
        JFileChooser fileChooser = null;
        String[] platForms = {"Metal","Nimbus","Motif","Windows","WindowsClassic"};
        UIManager.LookAndFeelInfo[] plafs = UIManager.getInstalledLookAndFeels();
        JComboBox<JLabel> comboBox = null;
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("settings.jdm"));
            textField2 = (JTextField)objectInputStream.readObject();
            maxDownload.setLayout(new GridLayout(1,2));
            maxDownload.add(textField1);
            maxDownload.add(textField2);
            fileChooser = (JFileChooser)objectInputStream.readObject();
            comboBox = (JComboBox<JLabel>)objectInputStream.readObject();
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout(5,5));
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        centerPanel.add(fileChooser,BorderLayout.NORTH);
        JPanel lookAndFeel = new JPanel();
        lookAndFeel.setLayout(new GridLayout(1,2));
        JPanel filteredAddresses = new JPanel();
        JLabel filterLabel = new JLabel("Filtered Addresse");
        filterLabel.setFont(new Font("Arial",Font.PLAIN,20));
        JTextField address = new JTextField();
        JPanel buttons1 = new JPanel();
        buttons1.setLayout(new GridLayout(1,2));
        JButton add = new JButton("Add");
        JButton edit = new JButton("Edit");
        add.setPreferredSize(new Dimension(70,40));
        add.setFont(new Font("Arial",Font.PLAIN,17));
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ArrayList<String> arrayList = new ArrayList<>();
                    ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("filter.jdm"));
                    Object object;
                    boolean flag = true;
                    while (flag){
                        try {
                            if((object = objectInputStream.readObject()) != null)
                                arrayList.add((String)object);
                        } catch (ClassNotFoundException e1) {
                            e1.printStackTrace();
                        }
                        catch (EOFException e2){
                            flag = false;
                        }
                    }
                    objectInputStream.close();
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("filter.jdm"));
                    System.out.println(arrayList.size());
                    for(int i=0;i<arrayList.size();i++){
                        objectOutputStream.writeObject(arrayList.get(i));
                    }
                    objectOutputStream.writeObject(address.getText());
                    downloads.getProcessingDownloadsList().addBlockedAddress(address.getText());
                    objectOutputStream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        edit.setPreferredSize(new Dimension(70,40));
        edit.setFont(new Font("Arial",Font.PLAIN,17));
        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame filteredFrame = new JFrame("Filtered Addresses");
                filteredFrame.setPreferredSize(new Dimension(300,400));
                filteredFrame.setMinimumSize(new Dimension(300,400));
                filteredFrame.setLocation(500,500);
                filteredFrame.setLayout(new BorderLayout());
                JPanel checkBoxPanel = new JPanel();
                checkBoxPanel.setLayout(new GridLayout(8,1));
                filteredFrame.add(checkBoxPanel,BorderLayout.CENTER);
                JPanel buttonPanel = new JPanel();
                JButton unfilter = new JButton("Unfilter");
                unfilter.setPreferredSize(new Dimension(100,50));
                unfilter.setFont(new Font("Arial",Font.PLAIN,20));
                buttonPanel.add(unfilter);
                filteredFrame.add(buttonPanel,BorderLayout.SOUTH);
                ArrayList<JCheckBox> checkBoxes = new ArrayList<>();
                ObjectInputStream objectInputStream = null;
                try {
                    Object object;
                    boolean flag = true;
                    objectInputStream = new ObjectInputStream(new FileInputStream("filter.jdm"));
                    while (flag){
                        try {
                            if((object = objectInputStream.readObject()) != null) {
                                checkBoxes.add(new JCheckBox((String) object));
                                checkBoxPanel.add(checkBoxes.get(checkBoxes.size()-1));
                            }
                        } catch (ClassNotFoundException e1) {
                            e1.printStackTrace();
                        }catch (EOFException e2){
                            flag = false;
                        }
                    }
                    objectInputStream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                filteredFrame.setVisible(true);
                unfilter.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ArrayList<String> arrayList = new ArrayList<>();
                        ObjectInputStream objectInputStream = null;
                        try {
                            objectInputStream = new ObjectInputStream(new FileInputStream("filter.jdm"));
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                        Object object;
                        boolean flag = true;
                        while (flag){
                            try {
                                if((object = objectInputStream.readObject()) != null)
                                    arrayList.add((String)object);
                            } catch (ClassNotFoundException e1) {
                                e1.printStackTrace();
                            }
                            catch (EOFException e2){
                                flag = false;
                            } catch (IOException e2) {
                                e2.printStackTrace();
                            }
                        }
                        try {
                            objectInputStream.close();
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                        ObjectOutputStream objectOutputStream = null;
                        try {
                            objectOutputStream = new ObjectOutputStream(new FileOutputStream("filter.jdm"));
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                        System.out.println(arrayList.size());
                        for(int i=0;i<arrayList.size();i++){
                            if(!checkBoxes.get(i).isSelected()) {
                                try {
                                    objectOutputStream.writeObject(arrayList.get(i));
                                } catch (IOException e2) {
                                    e2.printStackTrace();
                                }
                            }
                            else
                                downloads.getProcessingDownloadsList().deleteBlockedAddress(arrayList.get(i));
                        }
                        try {
                            objectOutputStream.close();
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                        filteredFrame.dispose();
                    }
                });
            }
        });
        buttons1.add(add);
        buttons1.add(edit);
        filteredAddresses.setLayout(new BorderLayout());
        filteredAddresses.add(filterLabel,BorderLayout.NORTH);
        filteredAddresses.add(address,BorderLayout.CENTER);
        filteredAddresses.add(buttons1,BorderLayout.EAST);
        centerPanel.add(filteredAddresses,BorderLayout.CENTER);
        JTextField textField3 = new JTextField("Look and feel   ");
        textField3.setBackground(new Color(230,230,230));
        textField3.setEditable(false);
        lookAndFeel.add(textField3);
        lookAndFeel.add(comboBox);
        centerPanel.add(lookAndFeel,BorderLayout.SOUTH);
        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(1,2));
        JButton ok = new JButton("OK");
        JTextField finalTextField = textField2;
        JFileChooser finalFileChooser = fileChooser;
        JComboBox<JLabel> finalComboBox = comboBox;
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(!finalTextField.getText().equals("Unlimited")) {
                        downloads.getProcessingDownloadsList().setMaximumNumberOfProcessingDownloads(Integer.parseInt(finalTextField.getText()));
                        int a = Integer.parseInt(finalTextField.getText()) > downloads.getProcessingDownloadsList().getDownloadsList().size() ? downloads.getProcessingDownloadsList().getDownloadsList().size() : Integer.parseInt(finalTextField.getText());
                        for(int i=0;i<a;i++){
                            if(downloads.getProcessingDownloadsList().getWaitingDownloads().contains(downloads.getProcessingDownloadsList().getDownloadsList().get(i)));
                                downloads.getProcessingDownloadsList().getWaitingDownloads().remove(downloads.getProcessingDownloadsList().getDownloadsList().get(i));
                            downloads.getProcessingDownloadsList().getDownloadsList().get(i).resume();
                        }
                        for(int i=Integer.parseInt(finalTextField.getText());i<downloads.getProcessingDownloadsList().getDownloadsList().size();i++){
                            downloads.getProcessingDownloadsList().getDownloadsList().get(i).pause();
                            if(!downloads.getProcessingDownloadsList().getWaitingDownloads().contains(downloads.getProcessingDownloadsList().getDownloadsList().get(i)))
                                downloads.getProcessingDownloadsList().getWaitingDownloads().add(downloads.getProcessingDownloadsList().getDownloadsList().get(i));
                        }
                    }
                    else
                        downloads.getProcessingDownloadsList().setMaximumNumberOfProcessingDownloads(-1);
                    downloads.getProcessingDownloadsList().setCurrentDirectory(finalFileChooser.getCurrentDirectory());
                    UIManager.setLookAndFeel(plafs[finalComboBox.getSelectedIndex()].getClassName());
                }catch (Exception ex){
                    ex.printStackTrace();
                }finally {
                    frame.dispose();
                    try {
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("settings.jdm"));
                        objectOutputStream.writeObject(finalTextField);
                        objectOutputStream.writeObject(finalFileChooser);
                        objectOutputStream.writeObject(finalComboBox);
                        objectOutputStream.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    SwingUtilities.updateComponentTreeUI(mainFrame);
                }
            }
        });
        JButton cancel = new JButton("Cancel");
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
        buttons.add(ok);
        buttons.add(cancel);
        frame.add(maxDownload,BorderLayout.NORTH);
        frame.add(centerPanel,BorderLayout.CENTER);
        frame.add(buttons,BorderLayout.SOUTH);
        frame.setVisible(true);
    }
}
