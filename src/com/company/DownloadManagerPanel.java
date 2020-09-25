package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class DownloadManagerPanel extends JPanel{
    public DownloadManagerPanel(Downloads downloads) {
        this.setBackground(Color.DARK_GRAY);
        this.setPreferredSize(new Dimension(220,400));
        ImageIcon completedImage = new ImageIcon("completed.png");
        JButton completedButton = new JButton("Completed",completedImage);
        completedButton.setFont(new Font("Arial", Font.PLAIN, 25));
        completedButton.setBackground(Color.LIGHT_GRAY);
        completedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                downloads.transFormToCompleted();
            }
        });
        ImageIcon processingImage = new ImageIcon("download.png");
        JButton processingButton = new JButton("Processing",processingImage);
        processingButton.setFont(new Font("Arial", Font.PLAIN, 25));
        processingButton.setBackground(Color.LIGHT_GRAY);
        processingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                downloads.transFormToProcessing();
            }
        });
        ImageIcon queueImage = new ImageIcon("queue.png");
        JButton queueButton = new JButton("Queue",queueImage);
        queueButton.setFont(new Font("Arial", Font.PLAIN, 25));
        queueButton.setBackground(Color.LIGHT_GRAY);
        queueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame queueFrame = new JFrame("Queue");
                queueFrame.setLocation(800,800);
                queueFrame.setSize(700,700);
                queueFrame.setLayout(new BorderLayout());
                final DefaultListModel<String> model;
                if(downloads.getProcessingDownloadsList().getModel() != null)
                    model = downloads.getProcessingDownloadsList().getModel();
                else
                    model = new DefaultListModel<>();
                JList<String> queueList = new JList<>(model);
                JPanel namesPanel = new JPanel();
                JPanel buttonsPanel = new JPanel();
                JPanel queueListPanel = new JPanel();
                queueListPanel.setLayout(new GridLayout(2,1));
                JPanel queueListButtons = new JPanel();
                queueListButtons.setLayout(new GridLayout(2,1));
                JButton remove = new JButton("Remove");
                JButton move = new JButton("Move");
                remove.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        model.remove(queueList.getSelectedIndex());
                    }
                });
                move.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JFrame choosingTargetPosition = new JFrame("Move to ...");
                        choosingTargetPosition.setLocation(600,600);
                        choosingTargetPosition.setSize(500,200);
                        choosingTargetPosition.setLayout(new BorderLayout());
                        JLabel label = new JLabel("Enter position of target position ");
                        label.setFont(new Font("Arial",Font.PLAIN,20));
                        JTextField textField = new JTextField();
                        JButton move = new JButton("Move");
                        JButton cancel = new JButton("Cancel");
                        move.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                String tmp = model.getElementAt(Integer.parseInt(textField.getText()));
                                model.setElementAt(model.getElementAt(queueList.getSelectedIndex()),Integer.parseInt(textField.getText()));
                                model.setElementAt(tmp,queueList.getSelectedIndex());
                                choosingTargetPosition.dispose();
                            }
                        });
                        cancel.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                choosingTargetPosition.dispose();
                            }
                        });
                        JPanel mainPanel = new JPanel();
                        mainPanel.setLayout(new GridLayout(1,2));
                        mainPanel.add(label);
                        mainPanel.add(textField);
                        JPanel buttonsPanel = new JPanel();
                        buttonsPanel.setLayout(new GridLayout(1,2));
                        buttonsPanel.add(move);
                        buttonsPanel.add(cancel);
                        choosingTargetPosition.add(mainPanel,BorderLayout.NORTH);
                        choosingTargetPosition.add(buttonsPanel,BorderLayout.SOUTH);
                        choosingTargetPosition.setVisible(true);
                    }
                });
                queueListButtons.add(remove);
                queueListButtons.add(move);
                queueListPanel.add(queueList);
                queueListPanel.add(queueListButtons);
                buttonsPanel.setLayout(new GridLayout(1,2));
                JButton ok = new JButton("OK");
                JButton cancel = new JButton("Cancel");
                ok.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        downloads.getProcessingDownloadsList().setModel(model);
                        queueFrame.dispose();
                    }
                });
                cancel.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        queueFrame.dispose();
                    }
                });
                buttonsPanel.add(ok);
                buttonsPanel.add(cancel);
                namesPanel.setLayout(new GridLayout(downloads.getProcessingDownloadsList().getDownloadsList().size(),1));
                JLabel[] allOfDownloads = new JLabel[downloads.getProcessingDownloadsList().getDownloadsList().size()];
                for(int i=0;i<downloads.getProcessingDownloadsList().getDownloadsList().size();i++){
                    allOfDownloads[i] = new JLabel(downloads.getProcessingDownloadsList().getDownloadsList().get(i).getFileName());
                    allOfDownloads[i].setFont(new Font("Arial",Font.PLAIN,25));
                    allOfDownloads[i].addMouseListener(new MouseListener() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            if(!model.contains(((JLabel)e.getSource()).getText()))
                                model.addElement(((JLabel)e.getSource()).getText());
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
                    namesPanel.add(allOfDownloads[i]);
                }
                queueFrame.add(namesPanel,BorderLayout.WEST);
                queueFrame.add(queueListPanel,BorderLayout.EAST);
                queueFrame.add(buttonsPanel,BorderLayout.SOUTH);
                queueFrame.setVisible(true);
            }
        });
        this.setLayout(new GridLayout(5,1));
        this.add(processingButton);
        this.add(completedButton);
        this.add(queueButton);
    }
}
