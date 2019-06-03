package Camera_P2I;

import accelrecog.BlueTooth;

import java.awt.*;
import javax.swing.*;
//import javax.swing.Timer;
import javax.swing.border.Border;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.*;
import java.awt.event.*;

public class ControlPanel extends JFrame implements ActionListener {

    private JPanel  status, configuration, settings;
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
    private JPanel content;
   // private Timer timer;

    private boolean followed;

    private boolean arduinoConnected;
    private boolean connexionEstablished;
    private boolean cameraWorking;
    private FileWriter fw;
    private FileReader fr;
    private FileWriter fwFileCreation;
    private FileReader frFileCreation;

    private File userNames; // un file avec le nom de tous les utilisateurs
    private File userFile; // un fle avec pour chaque utilisateur ses caractéristiques

    private VisualizationWindow camPanel;
    private DetectionMain hand;
     private interrogBD database;
    // private fenetreAccelerometre acceleroPanel;
     public BlueTooth bluetoothPannel;
    // private fenetreConnexion connexionPanel;


    public static void main(String[] args) throws IOException {

        ControlPanel c = new ControlPanel();


    }


    public ControlPanel() throws IOException {

       // Image background = Toolkit.getDefaultToolkit().createImage("imageFond.png");
        ImageIcon back= new ImageIcon("imageFondJPG.jpg");
        content= new JPanel();

        JLabel background= new JLabel(back);
     //   background.setBounds(20,20, 40,40);

      //  content.add(background);
      //  background.setVisible(true);

        //back.setIcon(back);

        int delay = 1000; //milliseconds

        hand= new DetectionMain();
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

        scrollPane = new JScrollPane(usersList);
        Border sepBorder = BorderFactory.createEmptyBorder(10, 0, 10, 0);

        content.setLayout(new BoxLayout(content, SwingConstants.HORIZONTAL));


        //Pane holding status
        Border statusBorder = BorderFactory.createEmptyBorder(10, 5, 10, 5);

        status = new JPanel();
        status.setLayout(new BoxLayout(status, BoxLayout.Y_AXIS));
        status.setBorder(interMenu);

        statusLabel = new JLabel("Statut de la connexion");
        statusLabel.setFont(titleFont);
        status.add(statusLabel);
        status.add(Box.createVerticalGlue());

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

        status.add(Box.createVerticalGlue());

        //Séparateur
        sep1 = new JSeparator(SwingConstants.VERTICAL);
        sep1.setBackground(Color.BLACK);
        sep1.setBorder(sepBorder);


        //Pane holding configurations
        configuration = new JPanel();
        configuration.setLayout(new BoxLayout(configuration, BoxLayout.Y_AXIS));
        configuration.setBorder(interMenu);

        configLabel = new JLabel("Configuration de l'utilisateur");
        configLabel.setFont(titleFont);

        configuration.add(configLabel);
        configuration.add(scrollPane);
        configuration.add(Box.createVerticalGlue());


        JPanel boutonsConfig = new JPanel();


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

        settingsLabel = new JLabel("Paramètres");
        settingsLabel.setFont(titleFont);
        settings.add(settingsLabel);

        settings.add(Box.createVerticalGlue());

        camSettings = new JButton("Paramètres caméra");
        camSettings.addActionListener(this);
        settings.add(camSettings);

        settings.add(Box.createVerticalGlue());

        accelSettings = new JButton("Paramètres accéléromètre");
        accelSettings.addActionListener(this);
        settings.add(accelSettings);

        settings.add(Box.createVerticalGlue());

        dBSettings = new JButton("Paramètres base de données");
        dBSettings.addActionListener(this);

        settings.add(Box.createVerticalGlue());

        this.setContentPane(background);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(13000, 5400);
        this.setVisible(true);

    }

    public void cameraState() {
        cameraWorking = hand.cameraIsOpened();
        if (cameraWorking == true) {
            // texte camera devient vert
        } else {
            //texte camera devient rouge
        }
    }


    public String[] readListUsers() throws Exception {
        ArrayList<String> tmp = new ArrayList<String>();
        Scanner scanner = new Scanner(userNames);
        while (scanner.hasNextLine()) {
            tmp.add(scanner.nextLine());
        }
        String[] ret = new String[tmp.size()];
        for (int i = 0; i < tmp.size(); i++) {
            ret[i] = tmp.get(i);
        }
        return ret;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addUser) {
            database.insertUserSettings(chooseUserName.getText(), hand.readPreferences());
        }
        if (e.getSource() == deleteUser) {
            database.deleteUserSettings(usersList.getSelectedValue());
        }

        if (e.getSource() == accelSettings) {
            // settings for the accelerometer
        }
        if (e.getSource() == camSettings) { // settings for the camera
            VisualizationWindow cam = new VisualizationWindow(hand);
            camStatus.setForeground(Color.green);
        }
        if (checkHandFollowed.isSelected()) { // suivi de la main
            followed = true;
            handFollowed.setForeground(Color.green);
        }
        if (!checkHandFollowed.isSelected()) { // Arret du suivi de la main
            followed = false;
            handFollowed.setForeground(Color.red);
        }

    }
}





