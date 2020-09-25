package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProcessingDownloadsList extends JPanel {
    private ArrayList<String> bloackedAddresses;
    private ArrayList<ProcessingDownload> downloadsList;
    private ArrayList<ProcessingDownload> waitingDownloads;
    private ExecutorService executorService;
    private DefaultListModel<String> model;
    private int maximumNumberOfProcessingDownloads = -1;
    private File currentDirectory = new File("C:\\Users\\asus\\Desktop");
    private CompletedDownloadsList completedDownloadsList;
    public ProcessingDownloadsList(CompletedDownloadsList completedDownloadsList) {
        this.executorService = Executors.newFixedThreadPool(10);
        waitingDownloads = new ArrayList<>();
        bloackedAddresses = new ArrayList<>();
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("filter.jdm"));
            Object object;
            boolean flag = true;
            while (flag) {
                try {
                    if ((object = objectInputStream.readObject()) != null)
                        bloackedAddresses.add((String) object);
                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                } catch (EOFException e2) {
                    flag = false;
                }
            }
            objectInputStream.close();
        }catch (IOException e1) {
            e1.printStackTrace();
        }
        this.setPreferredSize(new Dimension(400, 840));
        downloadsList = new ArrayList<>();
        this.setLayout(new GridLayout(7, 1));
        this.completedDownloadsList = completedDownloadsList;
    }

    public void addBlockedAddress(String address){
        bloackedAddresses.add(address);
    }

    public void deleteBlockedAddress(String address){
        bloackedAddresses.remove(address);
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }

    public void restoreQueue(){
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("queue.jdm"));
            this.model = (DefaultListModel<String>)objectInputStream.readObject();
            objectInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public DefaultListModel<String> getModel() {
        return model;
    }

    public void setModel(DefaultListModel<String> model) {
        this.model = model;
        if(model.size() == 0){
            for(int i=0;i<downloadsList.size();i++)
                if(!waitingDownloads.contains(downloadsList.get(i)))
                    downloadsList.get(i).resume();
        }
        else {
            for (int i = 0; i < downloadsList.size(); i++) {
                if (downloadsList.get(i).getFileName().equals(model.getElementAt(0))) {
                    if(!waitingDownloads.contains(downloadsList.get(i))){
                        for (int j = 0; j < downloadsList.size(); j++) {
                            if (waitingDownloads.contains(downloadsList.get(j))) {
                                waitingDownloads.remove(downloadsList.get(j));
                                downloadsList.get(j).pause();
                                break;
                            }
                        }
                    }
                    if (waitingDownloads.contains(downloadsList.get(i)))
                        waitingDownloads.remove(downloadsList.get(i));
                    downloadsList.get(i).resume();
                }
                else
                    downloadsList.get(i).pause();
            }
        }
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("queue.jdm"));
            objectOutputStream.writeObject(model);
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public CompletedDownloadsList getCompletedDownloadsList() {
        return completedDownloadsList;
    }

    public int getMaximumNumberOfProcessingDownloads() {
        return maximumNumberOfProcessingDownloads;
    }

    public File getCurrentDirectory() {
        return currentDirectory;
    }

    public void setCurrentDirectory(File currentDirectory) {
        this.currentDirectory = currentDirectory;
    }

    public ArrayList<ProcessingDownload> getDownloadsList() {
        return downloadsList;
    }

    public void setMaximumNumberOfProcessingDownloads(int maximumNumberOfProcessingDownloads) {
        this.maximumNumberOfProcessingDownloads = maximumNumberOfProcessingDownloads;
    }

    public void addNewDownload(ProcessingDownload processingDownload) {
        for(int i=0;i<bloackedAddresses.size();i++)
            if(processingDownload.getUrl().getPath().startsWith(bloackedAddresses.get(i)))
                return;
        if(maximumNumberOfProcessingDownloads != -1 && downloadsList.size() >= maximumNumberOfProcessingDownloads){
            waitingDownloads.add(processingDownload);
            processingDownload.pause();
        }
        downloadsList.add(processingDownload);
        this.add(processingDownload);
        processingDownload.addMouseListener(new SelectHandler());
        this.updateUI();
    }

    public ArrayList<ProcessingDownload> getWaitingDownloads() {
        return waitingDownloads;
    }

    private class SelectHandler implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            if(SwingUtilities.isLeftMouseButton(e)) {
                int sum = 0;
                int index = -1;
                for (int i = 0; i < downloadsList.size(); i++) {
                    if (downloadsList.get(i).isSelected()) {
                        sum++;
                        index = i;
                    }
                }
                if (!((ProcessingDownload) e.getSource()).isSelected()) {
                    if (sum == 1) {
                        downloadsList.get(index).setSelected(false);
                    }
                    ((ProcessingDownload) e.getSource()).setSelected(true);
                } else {
                    ((ProcessingDownload) e.getSource()).setBackgroundForSelection(new Color(237, 237, 237));
                    ((ProcessingDownload) e.getSource()).setSelected(false);
                }
            }
            else if(SwingUtilities.isRightMouseButton(e)){
                JFrame downnloadInformationFrame = new JFrame(((ProcessingDownload) e.getSource()).getFileName() + "Information");
                downnloadInformationFrame.setSize(new Dimension(800,600));
                downnloadInformationFrame.setLocation(800,800);
                downnloadInformationFrame.setLayout(new GridLayout(7,1));
                JLabel name = new JLabel("File : " + ((ProcessingDownload) e.getSource()).getFileName());
                JLabel downloadedSize = new JLabel("Downloaded : " + ((ProcessingDownload) e.getSource()).getDownloadedSize());
                JLabel totalSize = new JLabel("Size : " + ((ProcessingDownload) e.getSource()).getTotalSizeOfFile());
                JLabel path = new JLabel("Save to : " + ((ProcessingDownload) e.getSource()).getPath().replace(((ProcessingDownload) e.getSource()).getFileName(),""));
                JLabel createdTime = new JLabel("Created : " + ((ProcessingDownload) e.getSource()).getCreatedTime());
                JLabel url = new JLabel("URL : " + ((ProcessingDownload) e.getSource()).getUrl());
                name.setFont(new Font("Arial",Font.PLAIN,25));
                downloadedSize.setFont(new Font("Arial",Font.PLAIN,25));
                totalSize.setFont(new Font("Arial",Font.PLAIN,25));
                path.setFont(new Font("Arial",Font.PLAIN,25));
                createdTime.setFont(new Font("Arial",Font.PLAIN,25));
                url.setFont(new Font("Arial",Font.PLAIN,25));
                downnloadInformationFrame.add(name);
                downnloadInformationFrame.add(downloadedSize);
                downnloadInformationFrame.add(totalSize);
                downnloadInformationFrame.add(path);
                downnloadInformationFrame.add(createdTime);
                downnloadInformationFrame.add(url);
                downnloadInformationFrame.setVisible(true);
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