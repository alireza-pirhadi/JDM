package com.company;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadStatusPanel extends JPanel {
    private String createdTime;
    private URL url;
    private int totalSizeOfFile;
    public DownloadStatusPanel(String fileName ,String path ,String createdTime ,URL url){
        this.createdTime = createdTime;
        this.url = url;
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) getUrl().openConnection();
            totalSizeOfFile = httpURLConnection.getContentLength();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setBackgroundForSelection(Color color){
        this.setBackground(color);
        if(this instanceof CompletedDownload)
            this.getComponent(1).setBackground(color);
        else if(this instanceof  ProcessingDownload)
            this.getComponent(2).setBackground(color);
    }

    protected   String transferByteToString(long value){
        float res;
        String magnitude;
        if (value < 1024 * 1024){
            res = (float) (value / 1024);
            magnitude = "KB";
        }
        else {
            res = (float) value / 1024 / 1024;
            magnitude = "MB";
        }
        return "" + String.format("%.2f",res) + magnitude;
    }

    public URL getUrl() {
        return url;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public int getTotalSizeOfFile() {
        return totalSizeOfFile;
    }
}
