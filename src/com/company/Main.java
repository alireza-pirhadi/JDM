package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

//import static com.sun.glass.ui.Window.NORMAL;
import static java.awt.Frame.ICONIFIED;
import static java.awt.Frame.MAXIMIZED_BOTH;

public class Main {

    public static void main(String[] args) {
        JFrame frame = new JFrame("JDM");
        frame.setLocation(400,400);
        frame.setPreferredSize(new Dimension(1400,1000));
        frame.setMinimumSize(new Dimension(600,400));
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage("cheetah.png"));
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                TrayIcon trayIcon;
                SystemTray tray;
                if(SystemTray.isSupported()) {
                    tray = SystemTray.getSystemTray();
                    Image image = Toolkit.getDefaultToolkit().getImage("cheetah.png");
                    ActionListener exitListener = new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            System.exit(0);
                        }
                    };
                    PopupMenu popup = new PopupMenu();
                    MenuItem defaultItem = new MenuItem("Exit");
                    defaultItem.addActionListener(exitListener);
                    popup.add(defaultItem);
                    defaultItem = new MenuItem("Open");
                    defaultItem.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            frame.setVisible(true);
                            frame.setExtendedState(JFrame.NORMAL);
                        }
                    });
                    popup.add(defaultItem);
                    trayIcon = new TrayIcon(image, "JDM", popup);
                    trayIcon.setImageAutoSize(true);
                    int flag = 0;
                    for (TrayIcon trayIcon1 : tray.getTrayIcons())
                        if (trayIcon1.getImage().equals(trayIcon.getImage()))
                            flag = 1;
                    if (flag == 0){
                        try {
                            tray.add(trayIcon);
                        } catch (AWTException e1) {
                            e1.printStackTrace();
                        }
                    }
                }else{
                    System.out.println("system tray not supported");
                }
                frame.setVisible(true);
            }

            public void windowClosed(WindowEvent e) {
                SystemTray tray = SystemTray.getSystemTray();
                Image image = Toolkit.getDefaultToolkit().getImage("cheetah.png");
                for (TrayIcon trayIcon1 : tray.getTrayIcons())
                    if (trayIcon1.getImage().equals(image))
                        tray.remove(trayIcon1);
            }
        });
        Downloads downloads = new Downloads(frame);
        frame.setLayout(new BorderLayout(5,5));
        MenuBarPanel menuBarPanel = new MenuBarPanel(frame, downloads);
        DownloadManagerPanel downloadManagerPanel = new DownloadManagerPanel(downloads);
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("list.jdm"));
            int num = objectInputStream.readInt();
            for (int i=0;i<num;i++){
                ProcessingDownload p = new ProcessingDownload((ProcessingDownload) objectInputStream.readObject(), downloads.getProcessingDownloadsList());
                //ProcessingDownload p = (ProcessingDownload) objectInputStream.readObject();
                downloads.getProcessingDownloadsList().addNewDownload(p);
                DownloadThread downloadThread = new DownloadThread(p,downloads.getProcessingDownloadsList());
                downloadThread.execute();
                //System.out.println(downloads.getProcessingDownloadsList().getDownloadsList().get(downloads.getProcessingDownloadsList().getDownloadsList().size()-1).isInProgress());
                if (downloads.getProcessingDownloadsList().getDownloadsList().size() > 7) {
                    downloads.getProcessingDownloadsList().setLayout(new GridLayout(downloads.getProcessingDownloadsList().getDownloadsList().size() + 1, 1));
                    downloads.getProcessingDownloadsList().setPreferredSize(new Dimension(400, (downloads.getProcessingDownloadsList().getDownloadsList().size() + 1) * 120));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("CompletedList.jdm"));
            int num = objectInputStream.readInt();
            for (int i=0;i<num;i++){
                downloads.getCompletedDownloadsList().addToCompleteds((CompletedDownload) objectInputStream.readObject());
                if(downloads.getCompletedDownloadsList().getDownloadsList().size()>7){
                    downloads.getCompletedDownloadsList().setLayout(new GridLayout(downloads.getCompletedDownloadsList().getDownloadsList().size()+1,1));
                    downloads.getCompletedDownloadsList().setPreferredSize(new Dimension(400,(downloads.getCompletedDownloadsList().getDownloadsList().size()+1)*120));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        downloads.getProcessingDownloadsList().restoreQueue();
        frame.add(downloadManagerPanel,BorderLayout.WEST);
        frame.add(downloads,BorderLayout.CENTER);
        frame.pack();
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("settings.jdm"));
            JTextField textField = (JTextField)objectInputStream.readObject();
            if(!textField.getText().equals("Unlimited"))
                downloads.getProcessingDownloadsList().setMaximumNumberOfProcessingDownloads(Integer.parseInt(textField.getText()));
            else
                downloads.getProcessingDownloadsList().setMaximumNumberOfProcessingDownloads(-1);
            JFileChooser fileChooser = (JFileChooser)objectInputStream.readObject();
            downloads.getProcessingDownloadsList().setCurrentDirectory(fileChooser.getCurrentDirectory());
            JComboBox<JLabel> comboBox = (JComboBox<JLabel>)objectInputStream.readObject();
            UIManager.LookAndFeelInfo[] plafs = UIManager.getInstalledLookAndFeels();
            UIManager.setLookAndFeel(plafs[comboBox.getSelectedIndex()].getClassName());
            SwingUtilities.updateComponentTreeUI(frame);
            objectInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        frame.setVisible(true);
    }
}
