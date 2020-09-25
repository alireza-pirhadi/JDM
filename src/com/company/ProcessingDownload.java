package com.company;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.URL;

public class ProcessingDownload extends DownloadStatusPanel implements Serializable{
    private double speed = 0;
    private File file;
    private boolean isSelected = false;
    private JProgressBar progressBar;
    private boolean isInProgress = true;
    private JLabel status;
    private int downloadedSize = 0;
    public JProgressBar getProgressBar() {
        return progressBar;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public int getDownloadedSize() {
        return downloadedSize;
    }

    public JLabel getStatus() {
        return status;
    }

    public void setDownloadedSize(int downloadedSize) {
        this.downloadedSize = downloadedSize;
    }

    public ProcessingDownload(String fileName , String path , String createdTime , URL url, ProcessingDownloadsList processingDownloadsList){
        super(fileName,path,createdTime,url);
        file = new File(path);
        progressBar = new JProgressBar(JProgressBar.HORIZONTAL,0,100);
        progressBar.setValue(0);
        constructor(fileName,processingDownloadsList);
    }

    public ProcessingDownload(ProcessingDownload processingDownload ,ProcessingDownloadsList processingDownloadsList){
        super(processingDownload.getFileName(),processingDownload.getPath(),processingDownload.getCreatedTime(),processingDownload.getUrl());
        file = new File(processingDownload.getPath());
        progressBar = new JProgressBar(JProgressBar.HORIZONTAL,0,100);
        progressBar.setValue(processingDownload.progressBar.getValue());
        this.isInProgress = processingDownload.isInProgress;
        constructor(processingDownload.getFileName(),processingDownloadsList);
    }

    private void constructor(String fileName , ProcessingDownloadsList processingDownloadsList){
        setPreferredSize(new Dimension(400,120));
        setLayout(new GridLayout(3,1));
        JLabel label = new JLabel(fileName);
        label.setFont(new Font("Arial",Font.PLAIN,35));
        Timer timer = new Timer(300, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(isInProgress) {
                    double value = (double) downloadedSize / getTotalSizeOfFile();
                    progressBar.setValue((int) (value * 100));
                    progressBar.setString("" + (int) (progressBar.getPercentComplete() * 100) + "%");
                    status.setText("Speed : " + transferByteToString((long)speed) + "       " + transferByteToString(downloadedSize) + "/" + transferByteToString(getTotalSizeOfFile()));
                }
            }

        });
        ProcessingDownload tmp = this;
        progressBar.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                if (progressBar.getValue() == 100) {
                    timer.stop();
                    processingDownloadsList.getDownloadsList().remove(tmp);
                    processingDownloadsList.remove(tmp);
                    processingDownloadsList.updateUI();
                    processingDownloadsList.getCompletedDownloadsList().addToCompleteds(tmp);
                    if(processingDownloadsList.getCompletedDownloadsList().getDownloadsList().size()>7){
                        processingDownloadsList.getCompletedDownloadsList().setLayout(new GridLayout(processingDownloadsList.getCompletedDownloadsList().getDownloadsList().size(),1));
                        processingDownloadsList.getCompletedDownloadsList().setPreferredSize(new Dimension(400,(processingDownloadsList.getCompletedDownloadsList().getDownloadsList().size())*120));
                    }
                    if(processingDownloadsList.getModel()!=null && processingDownloadsList.getModel().size() == 0){
                        if(processingDownloadsList.getWaitingDownloads().size() != 0){
                            processingDownloadsList.getWaitingDownloads().get(0).resume();
                            processingDownloadsList.getWaitingDownloads().remove(0);
                        }
                    }
                    if(processingDownloadsList.getModel()!=null && processingDownloadsList.getModel().size() != 0)
                        processingDownloadsList.getModel().remove(0);
                    if(processingDownloadsList.getModel()!=null)
                        processingDownloadsList.setModel(processingDownloadsList.getModel());
                    try {
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("queue.jdm"));
                        objectOutputStream.writeObject(processingDownloadsList.getModel());
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    if(processingDownloadsList.getDownloadsList().size()>6){
                        processingDownloadsList.setLayout(new GridLayout(processingDownloadsList.getDownloadsList().size(),1));
                        processingDownloadsList.setPreferredSize(new Dimension(400,(processingDownloadsList.getDownloadsList().size())*120));
                    }
                    processingDownloadsList.getCompletedDownloadsList().updateUI();

                }
            }
        });
        progressBar.setStringPainted(true);
        timer.start();
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
        status = new JLabel(transferByteToString(downloadedSize) + "/" + transferByteToString(getTotalSizeOfFile()));
        status.setFont(new Font("Arial",Font.PLAIN,25));
        JPanel southPanel = new JPanel();
        southPanel.setLayout(new BorderLayout());
        southPanel.add(openingFolder,BorderLayout.WEST);
        southPanel.add(status,BorderLayout.CENTER);
        this.add(label);
        this.add(progressBar);
        this.add(southPanel);
    }

    public void pause(){
        isInProgress = false;
    }

    public void resume(){
        isInProgress = true;
    }

    public boolean isInProgress() {
        return isInProgress;
    }

//    public String getDownloadedSize(){
//        double res = progressBar.getPercentComplete()*Integer.parseInt(getFileSize().replace("KB","").replace("MB",""));
//        String magnitude = "MB";
//        if(getFileSize().contains("KB"))
//            magnitude = "KB";
//        return String.format("%.2f",res) + magnitude;
//    }
    public void setSelected(boolean isSelected){
        this.isSelected = isSelected;
        if(isSelected == false)
            setBackgroundForSelection(new Color(237, 237, 237));
        else
            setBackgroundForSelection(Color.LIGHT_GRAY);
    }
    public boolean isSelected(){
        return isSelected;
    }
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
