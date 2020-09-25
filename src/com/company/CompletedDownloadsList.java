package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CompletedDownloadsList extends JPanel {
    private ArrayList<CompletedDownload> downloadsList;

    public CompletedDownloadsList() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        this.setPreferredSize(new Dimension(400, 840));
        downloadsList = new ArrayList<>();
//        downloadsList.add(new CompletedDownload("AP-MidtermProject-Phase1.pdf","C:\\Users\\asus\\Documents\\Advanced Programming\\midterm Project\\JDM",dateFormat.format(date),"https://ceit.aut.ac.ir"));
//        downloadsList.get(0).addMouseListener(new SelectHandler());
//        downloadsList.add(new CompletedDownload("cheetah.png","C:\\Users\\asus\\Documents\\Advanced Programming\\midterm Project\\JDM",dateFormat.format(date),"https://google.com"));
//        downloadsList.get(1).addMouseListener(new SelectHandler());
        this.setLayout(new GridLayout(8, 1));
//        this.add(downloadsList.get(0));
//        this.add(downloadsList.get(1));
    }

    public ArrayList<CompletedDownload> getDownloadsList() {
        return downloadsList;
    }

    public void addToCompleteds(ProcessingDownload processingDownload) {
        CompletedDownload c = new CompletedDownload(processingDownload.getFileName(),processingDownload.getPath(),processingDownload.getCreatedTime(),processingDownload.getUrl());
        downloadsList.add(c);
        this.add(c);
        c.addMouseListener(new SelectHandler());
        this.updateUI();
    }

    public void addToCompleteds(CompletedDownload c) {
        downloadsList.add(c);
        this.add(c);
        c.addMouseListener(new SelectHandler());
        this.updateUI();
    }

    private class SelectHandler implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            if(e.getClickCount() == 1) {
                if(SwingUtilities.isLeftMouseButton(e)) {
                    int sum = 0;
                    int index = -1;
                    for (int i = 0; i < downloadsList.size(); i++) {
                        if (downloadsList.get(i).isSelected()) {
                            sum++;
                            index = i;
                        }
                    }
                    if (!((CompletedDownload) e.getSource()).isSelected()) {
                        if (sum == 1) {
                            downloadsList.get(index).setSelected(false);
                            downloadsList.get(index).setBackgroundForSelection(new Color(237, 237, 237));
                        }
                        ((CompletedDownload) e.getSource()).setBackgroundForSelection(Color.LIGHT_GRAY);
                        ((CompletedDownload) e.getSource()).setSelected(true);
                    } else {
                        ((CompletedDownload) e.getSource()).setBackgroundForSelection(new Color(237, 237, 237));
                        ((CompletedDownload) e.getSource()).setSelected(false);
                    }
                }
                else if(SwingUtilities.isRightMouseButton(e)){
                    JFrame downnloadInformationFrame = new JFrame(((CompletedDownload) e.getSource()).getFileName() + "Information");
                    downnloadInformationFrame.setSize(new Dimension(800,600));
                    downnloadInformationFrame.setLocation(800,800);
                    downnloadInformationFrame.setLayout(new GridLayout(7,1));
                    JLabel name = new JLabel("File : " + ((CompletedDownload) e.getSource()).getFileName());
                    JLabel size = new JLabel("Size : " + ((CompletedDownload) e.getSource()).getTotalSizeOfFile());
                    JLabel path = new JLabel("Save to : " + ((CompletedDownload) e.getSource()).getPath().replace(((CompletedDownload) e.getSource()).getFileName(),""));
                    JLabel createdTime = new JLabel("Created : " + ((CompletedDownload) e.getSource()).getCreatedTime());
                    JLabel url = new JLabel("URL : " + ((CompletedDownload) e.getSource()).getUrl());
                    name.setFont(new Font("Arial",Font.PLAIN,25));
                    size.setFont(new Font("Arial",Font.PLAIN,25));
                    path.setFont(new Font("Arial",Font.PLAIN,25));
                    createdTime.setFont(new Font("Arial",Font.PLAIN,25));
                    url.setFont(new Font("Arial",Font.PLAIN,25));
                    downnloadInformationFrame.add(name);
                    downnloadInformationFrame.add(size);
                    downnloadInformationFrame.add(path);
                    downnloadInformationFrame.add(createdTime);
                    downnloadInformationFrame.add(url);
                    downnloadInformationFrame.setVisible(true);
                }
            }
            else if(e.getClickCount() == 2){
                try {
                    System.out.println(((CompletedDownload)e.getSource()).getFile().getPath());
                    Desktop.getDesktop().open(((CompletedDownload)e.getSource()).getFile());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }
}