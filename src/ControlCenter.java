import java.awt.*;
import javax.swing.*;
//import javax.swing.Timer;
import javax.swing.border.Border;
import accelrecog.BlueTooth;
import camerarecog.DetectionMain;
import camerarecog.VisualizationWindow;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.*;

import java.awt.event.*;

public class ControlCenter extends JFrame implements ActionListener {

    private JPanel content, status, configuration, settings;
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

    public VisualizationWindow camPanel;
    public DetectionMain hand;
    // private fenetreBaseDonnee databasePanel;
    // private fenetreAccelerometre acceleroPanel;
     private BlueTooth bluetoothPannel;
    // private fenetreConnexion connexionPanel;


    public static void main(String[] args) {

        new Thread(()->{
            com.sun.deploy.panel.ControlPanel uiInterface = new com.sun.deploy.panel.ControlPanel();
        }).start();

    }


    public ControlCenter() {

        int delay = 1000; //milliseconds
     //   timer = new Timer(1000, this);
      // timer.start();
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

       // users = readListUsers();

       // usersList = new JList<String>(users);
        scrollPane = new JScrollPane(usersList);
        Border sepBorder = BorderFactory.createEmptyBorder(10, 0, 10, 0);

        //Content Pane
        content = new JPanel();
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


        content.add(Box.createHorizontalGlue());
        content.add(status);
        content.add(Box.createHorizontalGlue());
        content.add(sep1);
        content.add(Box.createHorizontalGlue());
        content.add(configuration);
        content.add(Box.createHorizontalGlue());
        content.add(sep2);
        content.add(Box.createHorizontalGlue());
        content.add(settings);
        content.add(Box.createHorizontalGlue());

        this.setContentPane(content);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.setSize(1300, 540);
        //this.setResizable(false);
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


    public void userFileCreation() {
        userNames = new File("UserNames.txt");

    }


    public void newUser(int hue, String username, int saturation, int value) {
        // ursername: nom écrit par l'utilisateur
        userFile = new File(username + ".txt");
        try {
            //Création de l'objet
            fw = new FileWriter("Usernames.txt", true);
            String str = username;
            fw.write(str);
            fw.close();

            fwFileCreation = new FileWriter(userFile);
            String strB = username;
            strB += "\t hue  : " + hue + "\n";
            strB += "\t saturation  : " + saturation + "\n";
            strB += "\t value : " + value + "\n";
            fwFileCreation.write(str);
            fwFileCreation.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String readUser(String username) {
        String strB = "";
        try {
            frFileCreation = new FileReader(username + ".txt");
            int i = 0;
            while ((i = fr.read()) != -1)
                strB += (char) i;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return strB;
    }


    public void eraseUser(String username) {
        File user = new File(username + ".txt");
        user.delete();
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
            newUser(hand.getHue(), chooseUserName.getText(), hand.getSatThresh(), hand.getValThresh());
        }
        if (e.getSource() == deleteUser) {
            eraseUser(usersList.getSelectedValue());
        }
        if (e.getSource() == dBSettings) {
            // settings for the database
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





