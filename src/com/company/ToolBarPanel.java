package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.ArrayList;

/**
 * this class create a toolbar and is consist of buttons that control a download
 * like resuming and pausing or temporary canceling or permanently removing a download
 * also have button for setting and a box for searching between downloads
 */
public class ToolBarPanel extends JPanel {
    public ToolBarPanel(JFrame mainFrame ,Downloads downloads) {
        JToolBar toolBar = new JToolBar();
        toolBar.setBackground(new Color(230,230,230));
        toolBar.setBorderPainted(false);
        this.setLayout(new BorderLayout());
        ImageIcon newDownload = new ImageIcon("plus.png");
        JButton addNewDownload = new JButton(newDownload);
        addNewDownload.setBorderPainted(false);
        addNewDownload.setToolTipText("new download");
        addNewDownload.setBackground(new Color(230,230,230));
        addNewDownload.addActionListener(new NewDownloadHandler(downloads.getProcessingDownloadsList()));
        toolBar.add(addNewDownload);
        ImageIcon resume = new ImageIcon("resume.png");
        JButton resumeButton = new JButton(resume);
        resumeButton.setBorderPainted(false);
        resumeButton.setToolTipText("resume");
        resumeButton.setBackground(new Color(230,230,230));
        resumeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(ProcessingDownload download : downloads.getProcessingDownloadsList().getDownloadsList()){
                    if(download.isSelected() && !downloads.getProcessingDownloadsList().getWaitingDownloads().contains(download))
                        download.resume();
                }
                downloads.getProcessingDownloadsList().updateUI();
            }
        });
        toolBar.add(resumeButton);
        ImageIcon pause = new ImageIcon("pause.png");
        JButton pauseButton = new JButton(pause);
        pauseButton.setBorderPainted(false);
        pauseButton.setToolTipText("pause");
        pauseButton.setBackground(new Color(230,230,230));
        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(ProcessingDownload download : downloads.getProcessingDownloadsList().getDownloadsList()){
                    if(download.isSelected() && !downloads.getProcessingDownloadsList().getWaitingDownloads().contains(download))
                        download.pause();
                }
                downloads.getProcessingDownloadsList().updateUI();
            }
        });
        toolBar.add(pauseButton);
        ImageIcon cancel = new ImageIcon("cancel.JPG");
        JButton cancelButton = new JButton(cancel);
        cancelButton.setBorderPainted(false);
        cancelButton.setToolTipText("cancel");
        cancelButton.setBackground(new Color(230,230,230));
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(int i=0;i<downloads.getProcessingDownloadsList().getDownloadsList().size();i++){
                    if(downloads.getProcessingDownloadsList().getDownloadsList().get(i).isSelected()) {
                        try {
                            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("removed.jdm"));
                            Object object;
                            ArrayList<Object> arrayList = new ArrayList<>();
                            try {
                                int num = objectInputStream.readInt();
                                for(int j=0;j<num;j++){
                                    object = objectInputStream.readObject();
                                    arrayList.add(object);
                                }
                            } catch (ClassNotFoundException e1) {
                                e1.printStackTrace();
                            }catch (EOFException e2){
                                e2.printStackTrace();
                            }
                            objectInputStream.close();
                            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("removed.jdm"));
                            objectOutputStream.writeInt(arrayList.size() + 1);
                            for (int j=0;j<arrayList.size();j++){
                                objectOutputStream.writeObject(arrayList.get(j));
                            }
                            objectOutputStream.writeObject(downloads.getProcessingDownloadsList().getDownloadsList().get(i));
                            objectOutputStream.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        downloads.getProcessingDownloadsList().remove(downloads.getProcessingDownloadsList().getDownloadsList().get(i));
                        downloads.getProcessingDownloadsList().getDownloadsList().remove(downloads.getProcessingDownloadsList().getDownloadsList().get(i));
                        i--;
                        if(downloads.getProcessingDownloadsList().getWaitingDownloads().size() != 0){
                            DownloadThread downloadThread = new DownloadThread(downloads.getProcessingDownloadsList().getWaitingDownloads().get(0),downloads.getProcessingDownloadsList());
                            downloadThread.execute();
                            downloads.getProcessingDownloadsList().getWaitingDownloads().remove(0);
                        }
                    }
                }
                downloads.getProcessingDownloadsList().updateUI();
            }
        });
        toolBar.add(cancelButton);
        ImageIcon remove = new ImageIcon("remove.JPG");
        JButton removeButton = new JButton(remove);
        removeButton.setBorderPainted(false);
        removeButton.setToolTipText("remove");
        removeButton.setBackground(new Color(230,230,230));
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(int i=0;i<downloads.getCompletedDownloadsList().getDownloadsList().size();i++){
                    if(downloads.getCompletedDownloadsList().getDownloadsList().get(i).isSelected()) {
                        File file = new File(downloads.getCompletedDownloadsList().getDownloadsList().get(i).getPath());
                        file.delete();
                        downloads.getCompletedDownloadsList().remove(downloads.getCompletedDownloadsList().getDownloadsList().get(i));
                        downloads.getCompletedDownloadsList().getDownloadsList().remove(downloads.getCompletedDownloadsList().getDownloadsList().get(i));
                        i--;
                    }
                }
                downloads.getCompletedDownloadsList().updateUI();
            }
        });
        toolBar.add(removeButton);
        ImageIcon setting = new ImageIcon("setting.png");
        JButton settingButton = new JButton(setting);
        settingButton.setBorderPainted(false);
        settingButton.setToolTipText("setting");
        settingButton.setBackground(new Color(230,230,230));
        settingButton.addActionListener(new SettingHandler(mainFrame,downloads));
        JLabel label = new JLabel("                                                                             Search ");
        label.setFont(new Font("Arial",Font.PLAIN,25));
        JTextField searchText = new JTextField("");
        searchText.setColumns(15);
        searchText.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                if(searchText.getText().equals("")){
                    for (int i=0;i<downloads.getProcessingDownloadsList().getDownloadsList().size();i++) {
                        downloads.getProcessingDownloadsList().add(downloads.getProcessingDownloadsList().getDownloadsList().get(i));
                    }
                    downloads.updateUI();
                }
                else {
                    for (int i=0;i<downloads.getProcessingDownloadsList().getDownloadsList().size();i++) {
                        downloads.getProcessingDownloadsList().remove(downloads.getProcessingDownloadsList().getDownloadsList().get(i));
                    }
                    for (int i = 0; i < downloads.getProcessingDownloadsList().getDownloadsList().size(); i++) {
                        if (downloads.getProcessingDownloadsList().getDownloadsList().get(i).getUrl().getPath().contains(searchText.getText()) ||
                                downloads.getProcessingDownloadsList().getDownloadsList().get(i).getFileName().contains(searchText.getText())) {
                            downloads.getProcessingDownloadsList().add(downloads.getProcessingDownloadsList().getDownloadsList().get(i));
                        }
                    }
                    downloads.updateUI();
                }
            }
        });
        toolBar.add(settingButton);
        toolBar.add(label);
        toolBar.add(searchText);
        add(toolBar,BorderLayout.PAGE_START);
    }

}
