package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NewDownloadHandler implements ActionListener {
    private ProcessingDownloadsList downloadList;
    public NewDownloadHandler(ProcessingDownloadsList downloadList){
        this.downloadList = downloadList;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFrame frame = new JFrame("New download");
        frame.setLocation(600,600);
        frame.setMinimumSize(new Dimension(800,600));
        frame.setPreferredSize(new Dimension(800,600));
        TextField url = new TextField("Enter download link");
        url.setFont(new Font("Arial", Font.PLAIN,20));
        TextField name = new TextField("Enter file name");
        name.setFont(new Font("Arial", Font.PLAIN,20));
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(downloadList.getCurrentDirectory());
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        JPanel nameAndFileChooserPanel = new JPanel();
        nameAndFileChooserPanel.setLayout(new BorderLayout());
        nameAndFileChooserPanel.add(name,BorderLayout.NORTH);
        nameAndFileChooserPanel.add(fileChooser,BorderLayout.CENTER);
        JPanel buttons = new JPanel();
        JButton add = new JButton("ADD");
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();
                ProcessingDownload processingDownload = null;
                try {
                    processingDownload = new ProcessingDownload(name.getText(),fileChooser.getCurrentDirectory().getPath().concat("\\").concat(name.getText()),dateFormat.format(date),new URL(url.getText()),downloadList);
                } catch (MalformedURLException e1) {
                    e1.printStackTrace();
                }
                if(downloadList.getDownloadsList().size()>6){
                    downloadList.setLayout(new GridLayout(downloadList.getDownloadsList().size()+1,1));
                    downloadList.setPreferredSize(new Dimension(400,(downloadList.getDownloadsList().size()+1)*120));
                }
                downloadList.addNewDownload(processingDownload);
                frame.dispose();
                DownloadThread downloadThread = new DownloadThread(processingDownload, downloadList);
                downloadList.getExecutorService().execute(downloadThread);
            }
        });
        JButton cancel = new JButton("Cancel");
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
        buttons.setLayout(new GridLayout(1,2));
        buttons.add(add);
        buttons.add(cancel);
        frame.setLayout(new BorderLayout(10,10));
        frame.add(url,BorderLayout.NORTH);
        frame.add(nameAndFileChooserPanel,BorderLayout.CENTER);
        frame.add(buttons,BorderLayout.SOUTH);
        frame.setVisible(true);
    }
}
