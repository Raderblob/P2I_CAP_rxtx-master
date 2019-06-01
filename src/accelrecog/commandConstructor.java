package accelrecog;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;


public class commandConstructor extends JFrame implements ActionListener{


    JComboBox<String> selectAction;
    JButton createScript;
    JButton creer;
    String urlProgramme;
    JFileChooser fc;
    String command;
    Gesture newgesture;

        public commandConstructor(Gesture gesture){
            newgesture=gesture;
            setLayout(null);
            setSize(550, 270);
            setLocation(300, 200);

            JPanel mainConteneur = new JPanel();
            mainConteneur.setLayout(null);
            mainConteneur.setBackground(new Color(55,55,60));
            JPanel bandeSup=new JPanel();
            bandeSup.setBounds(0,0,550,40);
            bandeSup.setBackground(new Color(90,90,95));
            JLabel bandeSupTxt=new JLabel();
            Font police = new Font(" Arial ",Font.BOLD,20);
            bandeSupTxt.setFont(police);
            bandeSupTxt.setBounds(200,30,100,20);
            bandeSupTxt.setText("Scripts");
            bandeSupTxt.setForeground(new Color(200,200,200));
            bandeSup.add(bandeSupTxt);
            JLabel convSelectLabel = new JLabel();
            convSelectLabel.setBounds(45, 50, 200, 20);
            convSelectLabel.setText("Sélectionnez une possibilité :");
            convSelectLabel.setForeground(Color.white);


            //liste des utilisateurs en contact
            String[] actionsPossibles = {"lancer une application", "mettre en veille","fermer le processus actif","minimiser la fenêtre"};

            //création d'une combobox des possibilités de scripts différentes
            selectAction = new JComboBox<>(actionsPossibles);
            selectAction.setSize(300,50);
            selectAction.setLocation(45,70);
            selectAction.addActionListener(this);

            //bouton permettant de créer le script choisi dans la combobox
            createScript = new JButton();
            createScript.setSize(200,30);
            createScript.setLocation(300,65);
            createScript.setText("Créer le script");
            createScript.addActionListener(this);

            // sélectionner un programme à lancer


            // bouton permettant de sélectionner une app
            creer = new JButton();
            creer.setSize(200,30);
            creer.setLocation(100,170);
            creer.setHorizontalAlignment(JButton.CENTER);
            creer.setText("Choisir l'application");
            creer.addActionListener(this);
            creer.setVisible(false);

            mainConteneur.add(convSelectLabel);
            mainConteneur.add(selectAction);
            mainConteneur.add(createScript);
            mainConteneur.add(creer);
            mainConteneur.add(bandeSup);
            mainConteneur.setVisible(true);

            setContentPane(mainConteneur);
            setVisible(true);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setResizable(false);


        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource()==selectAction){

                switch (selectAction.getSelectedItem().toString()){
                    case "lancer une application":
                        creer.setVisible(true);
                        createScript.setVisible(false);
                        break;

                    case "mettre en veille" :
                        command = "Rundll32.exe Powrprof.dll,SetSuspendState Sleep";
                        break;
                    case "fermer le processus actif" :
                        command = "";
                        break;
                    case "minimiser la fenêtre" :
                        command = "powershell -command \"& { $x = New-Object -ComObject Shell.Application; $x.minimizeall() }\" ";
                        break;

                }
            }
            if(e.getSource()== createScript){
                newgesture.myShortCut.newCmd(command);
                dispose();
            }

            if(e.getSource()==creer){
                //Sélectionner le programme
                fc = new JFileChooser();
                fc.addActionListener(this);
                int returnVal = fc.showOpenDialog(this);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    urlProgramme = file.getAbsolutePath();
                    command = urlProgramme;
                    createScript.setVisible(true);
                    creer.setVisible(false);
                }
            }
        }
    }


