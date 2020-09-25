package com.company;

import javax.swing.*;
import java.io.*;
import java.net.HttpURLConnection;

public class DownloadThread extends SwingWorker<Void,Integer>{
    private ProcessingDownload processingDownload;
    private ProcessingDownloadsList processingDownloadsList;
    private JProgressBar progressBar;
    private JLabel status;

    public DownloadThread(ProcessingDownload processingDownload ,ProcessingDownloadsList processingDownloadsList){
        this.processingDownload = processingDownload;
        this.processingDownloadsList = processingDownloadsList;
        this.progressBar = processingDownload.getProgressBar();
        this.status = processingDownload.getStatus();
    }

    @Override
    protected Void doInBackground() throws Exception {
        HttpURLConnection httpURLConnection;
        httpURLConnection = (HttpURLConnection)processingDownload.getUrl().openConnection();
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.setConnectTimeout(1000000);
        int responseCode = httpURLConnection.getResponseCode();
        Integer downloadedSize = 0;
        if(responseCode == HttpURLConnection.HTTP_OK){
            InputStream in = httpURLConnection.getInputStream();
            BufferedInputStream inputStream = new BufferedInputStream(in);
            FileOutputStream outputStream = new FileOutputStream(processingDownload.getPath());
            int flag = 0;
            int bytesRead = -1;
            int tmp = 0;
            byte[] buffer = new byte[1024];
            long end;
            long start = System.nanoTime();
            bytesRead = inputStream.read(buffer);
            while (bytesRead!= -1){
                while (!processingDownload.isInProgress()){
                    Thread.sleep(200);
                }
                outputStream.write(buffer, 0, bytesRead);
                downloadedSize += bytesRead;
                end = System.nanoTime();
                if(end - start >= Math.pow(10,8)) {
                    processingDownload.setSpeed((downloadedSize - tmp) / ((end - start)* Math.pow(10,-9)));
                    flag = 1;
                }
                processingDownload.setDownloadedSize(downloadedSize);
                bytesRead = inputStream.read(buffer);
                if(flag == 1) {
                    flag = 0;
                    start = System.nanoTime();
                    tmp = downloadedSize;
                }
            }
            outputStream.close();
            inputStream.close();
        } else{
            System.out.println("File didn't download");
        }
        return null;
    }

//    @Override
//    protected void process(List<Integer> chunks) {
//        for (Integer downloadedSize : chunks) {
//            if(downloadedSize != processingDownload.getTotalSizeOfFile()) {
//                System.out.println(downloadedSize);
//                double value = (double) downloadedSize / processingDownload.getTotalSizeOfFile();
//                progressBar.setValue((int) (value * 100));
//                progressBar.setString("" + (int) (processingDownload.getProgressBar().getPercentComplete() * 100) + "%");
//                status.setText(transferByteToString(downloadedSize) + "/" + transferByteToString(processingDownload.getTotalSizeOfFile()));
//                processingDownload.repaint();
//                processingDownload.revalidate();
//                processingDownloadsList.updateUI();
//            }
//            else{
//                ProcessingDownload tmp = processingDownload;
//                processingDownloadsList.getDownloadsList().remove(tmp);
//                processingDownloadsList.remove(tmp);
//                processingDownloadsList.updateUI();
//                System.out.println(tmp.getFile().getPath());
//                processingDownloadsList.getCompletedDownloadsList().addToCompleteds(tmp);
//                if(processingDownloadsList.getCompletedDownloadsList().getDownloadsList().size()>7){
//                    processingDownloadsList.getCompletedDownloadsList().setLayout(new GridLayout(processingDownloadsList.getCompletedDownloadsList().getDownloadsList().size(),1));
//                    processingDownloadsList.getCompletedDownloadsList().setPreferredSize(new Dimension(400,(processingDownloadsList.getCompletedDownloadsList().getDownloadsList().size())*120));
//                }
//                if(processingDownloadsList.getModel()!=null && processingDownloadsList.getModel().size() != 0)
//                    processingDownloadsList.getModel().remove(0);
//                if(processingDownloadsList.getModel()!=null)
//                    processingDownloadsList.setModel(processingDownloadsList.getModel());
//                try {
//                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("queue.jdm"));
//                    objectOutputStream.writeObject(processingDownloadsList.getModel());
//                } catch (IOException e1) {
//                    e1.printStackTrace();
//                }
//                if(processingDownloadsList.getDownloadsList().size()>6){
//                    processingDownloadsList.setLayout(new GridLayout(processingDownloadsList.getDownloadsList().size(),1));
//                    processingDownloadsList.setPreferredSize(new Dimension(400,(processingDownloadsList.getDownloadsList().size())*120));
//                }
//                processingDownloadsList.getCompletedDownloadsList().updateUI();
//            }
//        }
//    }
    private static String transferByteToString(long value){
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
}
