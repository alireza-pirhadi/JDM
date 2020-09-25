package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class CompletedDownload extends DownloadStatusPanel{
    private boolean isSelected = false;
    private File file;
    public CompletedDownload(String fileName ,String path ,String createdTime ,URL url){
        super(fileName,path,createdTime,url);
        file = new File(path);
        setPreferredSize(new Dimension(400,120));
        setLayout(new GridLayout(2,1));
        JLabel label = new JLabel(fileName);
        label.setFont(new Font("Arial",Font.PLAIN,35));
        JButton openingFolder = new JButton(new ImageIcon("folder.png"));
        openingFolder.setBorderPainted(false);
        openingFolder.setBackground(new Color(237,237,237));
        openingFolder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Desktop.getDesktop().open(new File(file.getPath().replace(file.getName(),"")));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        JLabel status = new JLabel(transferByteToString(getTotalSizeOfFile()));
        status.setFont(new Font("Arial",Font.PLAIN,25));
        JPanel southPanel = new JPanel();
        southPanel.setLayout(new BorderLayout());
        southPanel.add(openingFolder,BorderLayout.WEST);
        southPanel.add(status,BorderLayout.CENTER);
        this.add(label);
        this.add(southPanel);

    }
    public void setSelected(boolean isSelected){
        this.isSelected = isSelected;
    }
    public boolean isSelected(){
        return isSelected;
    }

//    public String getFileSize() {
//        long fileSize = 0;
//        String magnitude;
//        if (file.length() < 1024 * 1024){
//            fileSize = file.length() / 1024;
//            magnitude = "KB";
//        }
//        else {
//            fileSize = file.length() / 1024 / 1024;
//            magnitude = "MB";
//        }
//        return fileSize + magnitude;
//    }
    public File getFile() {
        return file;
    }

    public String getFileName() {
        return file.getName();
    }

    public String getPath() {
        return file.getPath();
    }
}
