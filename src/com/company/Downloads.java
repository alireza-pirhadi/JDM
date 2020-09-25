package com.company;

import javax.swing.*;
import java.awt.*;

public class Downloads extends JPanel {
    private ProcessingDownloadsList processingDownloadsList;
    private CompletedDownloadsList completedDownloadsList;
    public ProcessingDownloadsList getProcessingDownloadsList() {
        return processingDownloadsList;
    }

    public CompletedDownloadsList getCompletedDownloadsList() {
        return completedDownloadsList;
    }

    public Downloads(JFrame frame){
        SpringLayout springLayout = new SpringLayout();
        //this.setLayout(springLayout);
        this.setLayout(new BorderLayout());
        completedDownloadsList = new CompletedDownloadsList();
        processingDownloadsList = new ProcessingDownloadsList(completedDownloadsList);
        ToolBarPanel toolBarPanel = new ToolBarPanel(frame,this);
        this.add(toolBarPanel,BorderLayout.NORTH);
        this.add(processingDownloadsList,BorderLayout.CENTER);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(processingDownloadsList);
        this.add(scrollPane,BorderLayout.CENTER);
    }

    public void transFormToCompleted(){
        for(ProcessingDownload downloadStatusPanel : processingDownloadsList.getDownloadsList()){
            if(downloadStatusPanel.isSelected()){
                downloadStatusPanel.setSelected(false);
                downloadStatusPanel.setBackground(new Color(237,237,237));
            }
        }
        this.remove(processingDownloadsList);
        this.add(completedDownloadsList);
        ((JScrollPane)this.getComponent(1)).setViewportView(completedDownloadsList);
        this.updateUI();
    }

    public void transFormToProcessing(){
        for(CompletedDownload downloadStatusPanel : completedDownloadsList.getDownloadsList()){
            if(downloadStatusPanel.isSelected()){
                downloadStatusPanel.setSelected(false);
                downloadStatusPanel.setBackground(new Color(237,237,237));
            }
        }
        this.remove(completedDownloadsList);
        this.add(processingDownloadsList);
        ((JScrollPane)this.getComponent(1)).setViewportView(processingDownloadsList);
        this.updateUI();
    }
}
