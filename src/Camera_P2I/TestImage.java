package Camera_P2I;

import Camera_P2I.DetectionMain;


import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.IOException;
import java.awt.*;
import javax.swing.*;
//import javax.swing.Timer;
import javax.swing.border.Border;

import Camera_P2I.DetectionMain;
import Camera_P2I.VisualizationWindow;
import accelrecog.BlueTooth;
import accelrecog.Interface;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.*;
import java.awt.event.*;


public class TestImage extends JFrame implements ActionListener {

    private JPanel status, configuration, settings;
    private JButton test;
    private JLabel statusLabel, configLabel, settingsLabel, handFollowed, bluetoothStatus, camStatus;
    private JButton camSettings, accelSettings, dBSettings, addUser, deleteUser;
    private JList<String> usersList;
    private String[] users;
    private JSeparator sep1, sep2;
    private Font titleFont;
    private JScrollPane scrollPane;
    private JCheckBox checkHandFollowed;
    private JTextField chooseUserName;
    private JLabel content;
    private boolean followed;


    //private boolean connexionEstablished;


    //private VisualizationWindow camPanel;
    private DetectionMain hand;
    public BlueTooth bluetoothPanel;

    private interrogBD baseDonnee;
    private String actualUser = "";
    public Interface accelGUI;


    public static void main(String[] args) throws IOException {

        TestImage c = new TestImage();

    }


    public TestImage() throws IOException {
        setLayout(null);
        content = new JLabel();
        content.setIcon(new javax.swing.ImageIcon(getClass().getResource("imageFond.png")));
        content.setBounds(0, 0, 855, 597);


        this.add(content);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.setSize(857, 597);
        this.setResizable(false);

        hand = new DetectionMain();
        hand.getHandCoordinates();

        titleFont = new Font("Arial", Font.PLAIN, 24);

        Border interMenu = BorderFactory.createEmptyBorder(20, 20, 20, 20);

        try {
            // Set cross-platform Java L&F (also called "Metal")
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        String[] list = null;
        try {
            list = (String[]) baseDonnee.recupererUsers().toArray();
        } catch (Exception e) {

        }

        if (list != null)
            usersList = new JList<String>(list);
        else
            usersList = new JList<String>();
        scrollPane = new JScrollPane(usersList);
        Border sepBorder = BorderFactory.createEmptyBorder(10, 0, 10, 0);


        content.setLayout(null);

        //Pane holding status
        Border statusBorder = BorderFactory.createEmptyBorder(10, 5, 10, 5);

        status = new JPanel();
        status.setLayout(new BoxLayout(status, BoxLayout.Y_AXIS));
        status.setBorder(interMenu);
        status.setOpaque(false);
        status.setBounds(0, 240, 281, 597);


        handFollowed = new JLabel("Suivi main ?");
        handFollowed.setForeground(Color.red);
        handFollowed.setAlignmentX(0.5f);
        handFollowed.setBorder(statusBorder);
        status.add(handFollowed);


        bluetoothStatus = new JLabel("Bluetooth connecté ?");
        bluetoothStatus.setForeground(Color.red);
        bluetoothStatus.setAlignmentX(0.5f);
        bluetoothStatus.setBorder(statusBorder);
        status.add(bluetoothStatus);

        camStatus = new JLabel("Caméra ok ?");
        camStatus.setForeground(Color.red);
        camStatus.setAlignmentX(0.5f);
        camStatus.setBorder(statusBorder);
        status.add(camStatus);
        //camStatus.setOpaque(false);

        status.add(Box.createVerticalGlue());

      /*  //Séparateur
        sep1 = new JSeparator(SwingConstants.VERTICAL);
        sep1.setBackground(Color.BLACK);
        sep1.setBorder(sepBorder);*/


        //Pane holding configurations
        configuration = new JPanel();
        configuration.setLayout(new BoxLayout(configuration, BoxLayout.Y_AXIS));
        configuration.setBorder(interMenu);
        configuration.setOpaque(false);
        configuration.setBounds(281, 150, 281, 597);

        configLabel = new JLabel("Configuration de l'utilisateur");
        configLabel.setFont(titleFont);
        configLabel.setOpaque(false);

        //
        // configuration.add(configLabel);
        configuration.add(scrollPane);


        JPanel boutonsConfig = new JPanel();
        boutonsConfig.setOpaque(false);


        checkHandFollowed = new JCheckBox("Suivi de la main:  ");
        checkHandFollowed.addActionListener(this);
        boutonsConfig.add(checkHandFollowed);

        addUser = new JButton("Ajouter un utilisateur");
        addUser.addActionListener(this);
        boutonsConfig.add(addUser);

        chooseUserName = new JTextField("Nom du nouvel utilisateur");
        chooseUserName.addActionListener(this);
        boutonsConfig.add(chooseUserName);

        deleteUser = new JButton("Supprimer utilisateur");
        deleteUser.addActionListener(this);
        boutonsConfig.add(deleteUser);

        configuration.add(boutonsConfig);

        configuration.add(Box.createVerticalGlue());


        //Séparateur
        sep2 = new JSeparator(SwingConstants.VERTICAL);
        sep2.setBackground(Color.BLACK);
        sep2.setBorder(sepBorder);


        //Pane holding settings
        settings = new JPanel();
        settings.setLayout(new BoxLayout(settings, BoxLayout.Y_AXIS));
        settings.setBorder(interMenu);
        settings.setOpaque(false);
        settings.setBounds(562, 250, 281, 597);


        camSettings = new JButton("Paramètres caméra");
        camSettings.addActionListener(this);
        settings.add(camSettings);
        settings.add(Box.createRigidArea(new Dimension(0, 10)));
        accelSettings = new JButton("Paramètres accéléromètre");
        accelSettings.addActionListener(this);

        settings.add(accelSettings);


        content.add(status);
        content.add(configuration);
        content.add(settings);


        this.setVisible(true);


    }

    public void updateConnexion(){
       if( bluetoothPanel.getBluetoothState()){
           bluetoothStatus.setForeground(Color.GREEN);
       }else{
           bluetoothStatus.setForeground(Color.RED);
       }
    }


    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addUser) {
            baseDonnee.insertUserSettings(chooseUserName.getText(), hand.readPreferences());
            String[] list = (String[]) baseDonnee.recupererUsers().toArray();
            usersList = new JList<String>(list);
            repaint();
        }
        if (e.getSource() == deleteUser) {
            // baseDonnee.deleteUserSettings(actualUser);
        }
        if (e.getSource() == accelSettings) {
            try {
            Thread.sleep(1000);
            accelGUI.setVisible(true);

                Thread.sleep(1000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }
        if (e.getSource() == camSettings) { // settings for the camera
            VisualizationWindow cam = new VisualizationWindow(hand);
            (new Thread(cam)).start();
            camStatus.setForeground(Color.green);
        }
        if (checkHandFollowed.isSelected()) { // suivi de la main
            followed = true;
            handFollowed.setForeground(Color.green);
            hand.setPanic(false);
        }
        if (!checkHandFollowed.isSelected()) { // Arret du suivi de la main
            followed = false;
            handFollowed.setForeground(Color.red);
            hand.setPanic(true);
        }

    }
}

