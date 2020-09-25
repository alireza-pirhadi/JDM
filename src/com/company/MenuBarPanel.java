package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class MenuBarPanel extends JPanel {
    public MenuBarPanel(JFrame frame ,Downloads downloads){
        JMenuBar menuBar = new JMenuBar();
        JMenu download = new JMenu("Download");
        JMenu sortBy = new JMenu("Sort By");
        JMenu date = new JMenu("Date");
        JMenu name = new JMenu("Name");
        JMenu size = new JMenu("Size");
        date.setFont(new Font("Arial",Font.PLAIN,20));
        name.setFont(new Font("Arial",Font.PLAIN,20));
        size.setFont(new Font("Arial",Font.PLAIN,20));
        JMenuItem increasingDate = new JMenuItem("Increasing");
        JMenuItem decreasingDate = new JMenuItem("Decreasing");
        increasingDate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(int i=0;i<downloads.getProcessingDownloadsList().getDownloadsList().size();i++){
                    downloads.getProcessingDownloadsList().remove(downloads.getProcessingDownloadsList().getDownloadsList().get(i));
                }
                int minIndex = 0;
                for(int i=0;i<downloads.getProcessingDownloadsList().getDownloadsList().size()-1;i++){
                    minIndex = i;
                    for(int j=i+1;j<downloads.getProcessingDownloadsList().getDownloadsList().size();j++){
                        if(downloads.getProcessingDownloadsList().getDownloadsList().get(j).getCreatedTime().compareTo(downloads.getProcessingDownloadsList().getDownloadsList().get(minIndex).getCreatedTime()) < 0){
                            minIndex = j;
                        }
                    }
                    if(i==minIndex)
                        continue;
                    for(int k=minIndex;k>i;k--) {
                        ProcessingDownload tmp = downloads.getProcessingDownloadsList().getDownloadsList().get(k);
                        downloads.getProcessingDownloadsList().getDownloadsList().remove(k);
                        downloads.getProcessingDownloadsList().getDownloadsList().add(k, downloads.getProcessingDownloadsList().getDownloadsList().get(k-1));
                        downloads.getProcessingDownloadsList().getDownloadsList().remove(k-1);
                        downloads.getProcessingDownloadsList().getDownloadsList().add(k-1, tmp);
                    }
                }
                for(int i=0;i<downloads.getProcessingDownloadsList().getDownloadsList().size();i++){
                    downloads.getProcessingDownloadsList().add(downloads.getProcessingDownloadsList().getDownloadsList().get(i));
                }
                downloads.getProcessingDownloadsList().updateUI();
            }
        });
        date.add(increasingDate);
        date.add(decreasingDate);
        decreasingDate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(int i=0;i<downloads.getProcessingDownloadsList().getDownloadsList().size();i++){
                    downloads.getProcessingDownloadsList().remove(downloads.getProcessingDownloadsList().getDownloadsList().get(i));
                }
                int maxIndex = 0;
                for(int i=0;i<downloads.getProcessingDownloadsList().getDownloadsList().size()-1;i++){
                    maxIndex = i;
                    for(int j=i+1;j<downloads.getProcessingDownloadsList().getDownloadsList().size();j++){
                        if(downloads.getProcessingDownloadsList().getDownloadsList().get(j).getCreatedTime().compareTo(downloads.getProcessingDownloadsList().getDownloadsList().get(maxIndex).getCreatedTime()) > 0){
                            maxIndex = j;
                        }
                    }
                    if(i==maxIndex)
                        continue;
                    for(int k=maxIndex;k>i;k--) {
                        ProcessingDownload tmp = downloads.getProcessingDownloadsList().getDownloadsList().get(k);
                        downloads.getProcessingDownloadsList().getDownloadsList().remove(k);
                        downloads.getProcessingDownloadsList().getDownloadsList().add(k, downloads.getProcessingDownloadsList().getDownloadsList().get(k-1));
                        downloads.getProcessingDownloadsList().getDownloadsList().remove(k-1);
                        downloads.getProcessingDownloadsList().getDownloadsList().add(k-1, tmp);
                    }
                }
                for(int i=0;i<downloads.getProcessingDownloadsList().getDownloadsList().size();i++){
                    downloads.getProcessingDownloadsList().add(downloads.getProcessingDownloadsList().getDownloadsList().get(i));
                }
                downloads.getProcessingDownloadsList().updateUI();
            }
        });
        JMenuItem increasingDate1 = new JMenuItem("Increasing");
        JMenuItem decreasingDate1 = new JMenuItem("Decreasing");
        increasingDate1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(int i=0;i<downloads.getProcessingDownloadsList().getDownloadsList().size();i++){
                    downloads.getProcessingDownloadsList().remove(downloads.getProcessingDownloadsList().getDownloadsList().get(i));
                }
                int minIndex = 0;
                for(int i=0;i<downloads.getProcessingDownloadsList().getDownloadsList().size()-1;i++){
                    minIndex = i;
                    for(int j=i+1;j<downloads.getProcessingDownloadsList().getDownloadsList().size();j++){
                        if(downloads.getProcessingDownloadsList().getDownloadsList().get(j).getFileName().compareTo(downloads.getProcessingDownloadsList().getDownloadsList().get(minIndex).getFileName()) < 0){
                            minIndex = j;
                        }
                    }
                    if(i==minIndex)
                        continue;
                    for(int k=minIndex;k>i;k--) {
                        ProcessingDownload tmp = downloads.getProcessingDownloadsList().getDownloadsList().get(k);
                        downloads.getProcessingDownloadsList().getDownloadsList().remove(k);
                        downloads.getProcessingDownloadsList().getDownloadsList().add(k, downloads.getProcessingDownloadsList().getDownloadsList().get(k-1));
                        downloads.getProcessingDownloadsList().getDownloadsList().remove(k-1);
                        downloads.getProcessingDownloadsList().getDownloadsList().add(k-1, tmp);
                    }
                }
                for(int i=0;i<downloads.getProcessingDownloadsList().getDownloadsList().size();i++){
                    downloads.getProcessingDownloadsList().add(downloads.getProcessingDownloadsList().getDownloadsList().get(i));
                }
                downloads.getProcessingDownloadsList().updateUI();
            }
        });
        decreasingDate1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(int i=0;i<downloads.getProcessingDownloadsList().getDownloadsList().size();i++){
                    downloads.getProcessingDownloadsList().remove(downloads.getProcessingDownloadsList().getDownloadsList().get(i));
                }
                int maxIndex = 0;
                for(int i=0;i<downloads.getProcessingDownloadsList().getDownloadsList().size()-1;i++){
                    maxIndex = i;
                    for(int j=i+1;j<downloads.getProcessingDownloadsList().getDownloadsList().size();j++){
                        if(downloads.getProcessingDownloadsList().getDownloadsList().get(j).getFileName().compareTo(downloads.getProcessingDownloadsList().getDownloadsList().get(maxIndex).getFileName()) > 0){
                            maxIndex = j;
                        }
                    }
                    if(i==maxIndex)
                        continue;
                    for(int k=maxIndex;k>i;k--) {
                        ProcessingDownload tmp = downloads.getProcessingDownloadsList().getDownloadsList().get(k);
                        downloads.getProcessingDownloadsList().getDownloadsList().remove(k);
                        downloads.getProcessingDownloadsList().getDownloadsList().add(k, downloads.getProcessingDownloadsList().getDownloadsList().get(k-1));
                        downloads.getProcessingDownloadsList().getDownloadsList().remove(k-1);
                        downloads.getProcessingDownloadsList().getDownloadsList().add(k-1, tmp);
                    }
                }
                for(int i=0;i<downloads.getProcessingDownloadsList().getDownloadsList().size();i++){
                    downloads.getProcessingDownloadsList().add(downloads.getProcessingDownloadsList().getDownloadsList().get(i));
                }
                downloads.getProcessingDownloadsList().updateUI();
            }
        });
        name.add(increasingDate1);
        name.add(decreasingDate1);
        JMenuItem increasingDate2 = new JMenuItem("Increasing");
        JMenuItem decreasingDate2 = new JMenuItem("Decreasing");
        increasingDate2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(int i=0;i<downloads.getProcessingDownloadsList().getDownloadsList().size();i++){
                    downloads.getProcessingDownloadsList().remove(downloads.getProcessingDownloadsList().getDownloadsList().get(i));
                }
                int minIndex = 0;
                for(int i=0;i<downloads.getProcessingDownloadsList().getDownloadsList().size()-1;i++){
                    minIndex = i;
                    for(int j=i+1;j<downloads.getProcessingDownloadsList().getDownloadsList().size();j++){
                        if(downloads.getProcessingDownloadsList().getDownloadsList().get(j).getFileName().compareTo(downloads.getProcessingDownloadsList().getDownloadsList().get(minIndex).getFileName()) < 0){
                            minIndex = j;
                        }
                    }
                    if(i==minIndex)
                        continue;
                    for(int k=minIndex;k>i;k--) {
                        ProcessingDownload tmp = downloads.getProcessingDownloadsList().getDownloadsList().get(k);
                        downloads.getProcessingDownloadsList().getDownloadsList().remove(k);
                        downloads.getProcessingDownloadsList().getDownloadsList().add(k, downloads.getProcessingDownloadsList().getDownloadsList().get(k-1));
                        downloads.getProcessingDownloadsList().getDownloadsList().remove(k-1);
                        downloads.getProcessingDownloadsList().getDownloadsList().add(k-1, tmp);
                    }
                }
                for(int i=0;i<downloads.getProcessingDownloadsList().getDownloadsList().size();i++){
                    downloads.getProcessingDownloadsList().add(downloads.getProcessingDownloadsList().getDownloadsList().get(i));
                }
                downloads.getProcessingDownloadsList().updateUI();
            }
        });
        decreasingDate2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(int i=0;i<downloads.getProcessingDownloadsList().getDownloadsList().size();i++){
                    downloads.getProcessingDownloadsList().remove(downloads.getProcessingDownloadsList().getDownloadsList().get(i));
                }
                int maxIndex = 0;
                for(int i=0;i<downloads.getProcessingDownloadsList().getDownloadsList().size()-1;i++){
                    maxIndex = i;
                    for(int j=i+1;j<downloads.getProcessingDownloadsList().getDownloadsList().size();j++){
                        if(downloads.getProcessingDownloadsList().getDownloadsList().get(j).getFile().length()>downloads.getProcessingDownloadsList().getDownloadsList().get(maxIndex).getFile().length()){
                            maxIndex = j;
                        }
                    }
                    if(i==maxIndex)
                        continue;
                    for(int k=maxIndex;k>i;k--) {
                        ProcessingDownload tmp = downloads.getProcessingDownloadsList().getDownloadsList().get(k);
                        downloads.getProcessingDownloadsList().getDownloadsList().remove(k);
                        downloads.getProcessingDownloadsList().getDownloadsList().add(k, downloads.getProcessingDownloadsList().getDownloadsList().get(k-1));
                        downloads.getProcessingDownloadsList().getDownloadsList().remove(k-1);
                        downloads.getProcessingDownloadsList().getDownloadsList().add(k-1, tmp);
                    }
                }
                for(int i=0;i<downloads.getProcessingDownloadsList().getDownloadsList().size();i++){
                    downloads.getProcessingDownloadsList().add(downloads.getProcessingDownloadsList().getDownloadsList().get(i));
                }
                downloads.getProcessingDownloadsList().updateUI();
            }
        });
        size.add(increasingDate2);
        size.add(decreasingDate2);
        sortBy.add(date);
        sortBy.add(name);
        sortBy.add(size);
        JMenu help = new JMenu("Help");
        download.setMnemonic('D');
        help.setMnemonic('H');
        help.setFont(new Font("Arial", Font.PLAIN, 20));
        download.setFont(new Font("Arial", Font.PLAIN, 20));
        sortBy.setFont(new Font("Arial", Font.PLAIN, 20));
        JMenuItem newDownload = new JMenuItem("New download");
        JMenuItem resume = new JMenuItem("Resume");
        JMenuItem pause = new JMenuItem("Pause");
        JMenuItem cancel = new JMenuItem("Cancel");
        JMenuItem remove = new JMenuItem("Remove");
        JMenuItem canceledDownloads = new JMenuItem("Canceled downloads");
        JMenuItem export = new JMenuItem("Export");
        JMenuItem setting = new JMenuItem("Setting");
        JMenuItem exit = new JMenuItem("exit");
        newDownload.addActionListener(new NewDownloadHandler(downloads.getProcessingDownloadsList()));
        resume.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(ProcessingDownload download : downloads.getProcessingDownloadsList().getDownloadsList()){
                    if(download.isSelected() && !downloads.getProcessingDownloadsList().getWaitingDownloads().contains(download))
                        download.resume();
                }
                downloads.getProcessingDownloadsList().updateUI();
            }
        });
        pause.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(ProcessingDownload download : downloads.getProcessingDownloadsList().getDownloadsList()){
                    if(download.isSelected() && !downloads.getProcessingDownloadsList().getWaitingDownloads().contains(download))
                        download.pause();
                }
                downloads.getProcessingDownloadsList().updateUI();
            }
        });
        cancel.addActionListener(new ActionListener() {
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
                                for(int j=0;j<num;j++) {
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
                            downloads.getProcessingDownloadsList().getExecutorService().execute(downloadThread);
                            downloads.getProcessingDownloadsList().getWaitingDownloads().remove(0);
                        }
                    }
                }
                downloads.getProcessingDownloadsList().updateUI();
            }
        });
        remove.addActionListener(new ActionListener() {
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
        canceledDownloads.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    FileInputStream fileInputStream = new FileInputStream("removed.jdm");
                    ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                    ArrayList<ProcessingDownload> arrayList = new ArrayList<>();
                    JFrame canceledsFrame = new JFrame("Canceled downloads");
                    canceledsFrame.setLocation(600,600);
                    canceledsFrame.setPreferredSize(new Dimension(800,610));
                    canceledsFrame.setLayout(new BorderLayout());
                    JPanel panel = new JPanel();
                    JPanel buttonsPanel = new JPanel();
                    buttonsPanel.setLayout(new GridLayout(1,4));
                    JButton ok = new JButton("Add");
                    ok.setPreferredSize(new Dimension(100,50));
                    ok.setFont(new Font("Arial",Font.PLAIN,20));
                    JButton delete = new JButton("Delete");
                    delete.setPreferredSize(new Dimension(100,50));
                    delete.setFont(new Font("Arial",Font.PLAIN,20));
                    delete.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            for(int i=0;i<arrayList.size();i++) {
                                if (arrayList.get(i).isSelected()) {
                                    panel.remove(arrayList.get(i));
                                    arrayList.remove(i);
                                    i--;
                                }
                            }
                            try {
                                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("removed.jdm"));
                                oos.writeInt(arrayList.size());
                                for(int i=0;i<arrayList.size();i++){
                                    oos.writeObject(arrayList.get(i));
                                }
                                oos.close();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                    });
                    ok.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            for(int i=0;i<arrayList.size();i++) {
                                if(arrayList.get(i).isSelected()) {
                                    if (downloads.getProcessingDownloadsList().getDownloadsList().size() > 6) {
                                        downloads.getProcessingDownloadsList().setLayout(new GridLayout(downloads.getProcessingDownloadsList().getDownloadsList().size() + 1, 1));
                                        downloads.getProcessingDownloadsList().setPreferredSize(new Dimension(400, (downloads.getProcessingDownloadsList().getDownloadsList().size() + 1) * 120));
                                    }
                                    arrayList.get(i).setSelected(false);
                                    arrayList.get(i).resume();
                                    ProcessingDownload processingDownload = new ProcessingDownload(arrayList.get(i),downloads.getProcessingDownloadsList());
                                    downloads.getProcessingDownloadsList().addNewDownload(processingDownload);
                                    panel.remove(arrayList.get(i));
                                    arrayList.remove(i);
                                    i--;
                                    DownloadThread downloadThread = new DownloadThread(processingDownload, downloads.getProcessingDownloadsList());
                                    downloads.getProcessingDownloadsList().getExecutorService().execute(downloadThread);
                                }
                            }
                            try {
                                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("removed.jdm"));
                                oos.writeInt(arrayList.size());
                                for(int i=0;i<arrayList.size();i++){
                                    oos.writeObject(arrayList.get(i));
                                }
                                oos.close();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                            canceledsFrame.dispose();
                        }
                    });
                    JButton cancel = new JButton("Cancel");
                    cancel.setPreferredSize(new Dimension(100,50));
                    cancel.setFont(new Font("Arial",Font.PLAIN,20));
                    cancel.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            canceledsFrame.dispose();
                        }
                    });
                    buttonsPanel.add(ok);
                    buttonsPanel.add(delete);
                    buttonsPanel.add(cancel);
                    panel.setLayout(new GridLayout(4,1));
                    JScrollPane scrollPane = new JScrollPane();
                    canceledsFrame.add(buttonsPanel,BorderLayout.NORTH);
                    canceledsFrame.add(scrollPane,BorderLayout.CENTER);
                    scrollPane.setViewportView(panel);
                    canceledsFrame.pack();

                    canceledsFrame.setVisible(true);
                    int num = objectInputStream.readInt();
                    for(int k=0;k<num;k++) {
                        ProcessingDownload processingDownload = (ProcessingDownload) objectInputStream.readObject();
                        processingDownload.setSelected(false);
                        processingDownload.addMouseListener(new MouseListener() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                if(SwingUtilities.isLeftMouseButton(e)) {
                                    if (!((ProcessingDownload) e.getSource()).isSelected()) {
                                        ((ProcessingDownload) e.getSource()).setSelected(true);
                                    } else {
                                        ((ProcessingDownload) e.getSource()).setSelected(false);
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
                        });
                        if(arrayList.size() >= 4) {
                            panel.setLayout(new GridLayout(arrayList.size() + 1, 1));
                            panel.setPreferredSize(new Dimension(600,(arrayList.size() + 1)*120));
                        }
                        panel.add(processingDownload);
                        arrayList.add(processingDownload);
                    }
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                }
            }
        });
        export.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] filenames = {"list.jdm","CompletedList.jdm","filter.jdm","removed.jdm","settings.jdm","queue.jdm"};
                FileOutputStream fout = null;
                try {
                    fout = new FileOutputStream("export.zip");
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                }
                ZipOutputStream zipout = new ZipOutputStream(fout);
                for(int i=0;i<filenames.length;i++)
                {
                    ZipEntry ze = new ZipEntry(filenames[i]);
                    try {
                        zipout.putNextEntry(ze);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    try {
                        copy(new FileInputStream(new File(filenames[i])), zipout);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    try {
                        zipout.closeEntry();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
                try {
                    zipout.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        setting.addActionListener(new SettingHandler(frame,downloads));
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    FileOutputStream fout = new FileOutputStream("list.jdm");
                    try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(fout)) {
                        objectOutputStream.writeInt(downloads.getProcessingDownloadsList().getDownloadsList().size());
                        for(int i=0;i<downloads.getProcessingDownloadsList().getDownloadsList().size();i++) {
                            objectOutputStream.writeObject(downloads.getProcessingDownloadsList().getDownloadsList().get(i));
                        }
                        objectOutputStream.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    fout.close();
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                try {
                    FileOutputStream fout = new FileOutputStream("CompletedList.jdm");
                    try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(fout)) {
                        objectOutputStream.writeInt(downloads.getCompletedDownloadsList().getDownloadsList().size());
                        for(int i=0;i<downloads.getCompletedDownloadsList().getDownloadsList().size();i++)
                            objectOutputStream.writeObject(downloads.getCompletedDownloadsList().getDownloadsList().get(i));
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                }
                System.exit(1);
            }
        });
        newDownload.setFont(new Font("Arial", Font.PLAIN, 20));
        newDownload.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,KeyEvent.CTRL_MASK));
        resume.setFont(new Font("Arial", Font.PLAIN, 20));
        resume.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,KeyEvent.CTRL_MASK));
        pause.setFont(new Font("Arial", Font.PLAIN, 20));
        pause.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,KeyEvent.CTRL_MASK));
        cancel.setFont(new Font("Arial", Font.PLAIN, 20));
        cancel.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,KeyEvent.CTRL_MASK));
        remove.setFont(new Font("Arial", Font.PLAIN, 20));
        remove.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,KeyEvent.SHIFT_MASK));
        canceledDownloads.setFont(new Font("Arial", Font.PLAIN, 20));
        //canceledDownloads.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,KeyEvent.SHIFT_MASK));
        export.setFont(new Font("Arial", Font.PLAIN, 20));
        setting.setFont(new Font("Arial", Font.PLAIN, 20));
        setting.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,KeyEvent.CTRL_MASK));
        exit.setFont(new Font("Arial", Font.PLAIN, 20));
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,KeyEvent.CTRL_MASK));
        download.add(newDownload);
        download.add(resume);
        download.add(pause);
        download.add(cancel);
        download.add(remove);
        download.add(canceledDownloads);
        download.add(export);
        download.add(setting);
        download.add(exit);
        JMenuItem about = new JMenuItem("About");
        about.setFont(new Font("Arial", Font.PLAIN, 20));
        about.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,KeyEvent.CTRL_MASK));
        about.addActionListener(new AboutHandler());
        help.add(about);
        menuBar.add(download);
        menuBar.add(sortBy);
        menuBar.add(help);
        frame.setJMenuBar(menuBar);
    }
    private   void copy(InputStream input, OutputStream output) throws IOException {
        byte[] BUFFER = new byte[4096 * 1024];
        int bytesRead;
        while ((bytesRead = input.read(BUFFER)) != -1) {
            output.write(BUFFER, 0, bytesRead);
        }
    }
    private class AboutHandler implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            JFrame frameAbout = new JFrame("About");
            frameAbout.setLocation(600,600);
            frameAbout.setMinimumSize(new Dimension(400,400));
            frameAbout.setPreferredSize(new Dimension(600,600));
            JTextArea textArea= new JTextArea();
            textArea.setEditable(false);
            textArea.setBackground(new Color(230,230,230));
            textArea.setFont(new Font("Arial", Font.PLAIN, 20));
            textArea.append("Progrmmer's name :     Alireza Pirhadi\n");
            textArea.append("Progrmmer's ID   :     9631013\n");
            textArea.append("Started at       :     5/2/2018\n");
            textArea.append("Finished at      :     5/4/2018\n");
            textArea.append("Explanations     :     ");
            frameAbout.add(textArea);
            frameAbout.setVisible(true);
        }
    }
}
