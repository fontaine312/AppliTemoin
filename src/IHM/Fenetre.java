package IHM;

import Utilisateur.Utilisateur;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class Fenetre extends JFrame {

    //private Panneau pan = new Panneau();
    private JButton             btnLogin = new JButton("S'identifier");
    final String                path = "C:\\Users\\yvesf\\IdeaProjects\\ProjetFramework\\Image\\logo.png";
    private ArrayList<JButton>  listDepart = new ArrayList<JButton>();
    private Utilisateur         usrCourant;
    //private CardLayout cardLayout = new CardLayout(0,0);





    public Fenetre(){
       initialize();
    }

    private void initialize() {

        //Ajout d'un titre
        this.setTitle("Drive Entreprise");
        //Definition de la taille de la fenêtre
        this.setSize(400,500);

        //Ajout d'une image
        /*BufferedImage myPicture = null;
        try {
            myPicture = ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        /*JLabel picLabel = new JLabel(new ImageIcon(myPicture));
        picLabel.setBounds(300,400,10,10);
        this.add(picLabel,BorderLayout.WEST);*/


        /*ImagePanel img = new ImagePanel();
        this.add(img,BorderLayout.PAGE_END);*/
        //Termine le processus lorsqu'on clique sur la croix rouge

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Positionnement de la fenêtre au centre
        this.setLocationRelativeTo(null);
        this.setLayout(new FlowLayout());
        this.setVisible(true);

        this.getContentPane().add(btnLogin);
        btnLogin.addActionListener(new openLoginPage());
    }

    class openLoginPage implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent event){
            LoginPage logP = new LoginPage(Fenetre.this);
            logP.setVisible(true);
            //Si l authentif a marche
            if(logP.isSucceeded()){
                btnLogin.setVisible(false);
                usrCourant = logP.getUsrCourant();

                listDepart.add(new JButton("Departement Informatique"));
                listDepart.add(new JButton("Departement GRH"));
                listDepart.add(new JButton("Departement Comptabilite"));

                listDepart.get(0).addActionListener(new openDepartment(usrCourant,listDepart.get(0).getText()));
                listDepart.get(1).setEnabled(false);
                listDepart.get(2).setEnabled(false);


                JButton btUsr = new JButton(usrCourant.getLog().getValeur());
                btUsr.addActionListener(new userInfoListener(usrCourant,btUsr));

                JLabel lbNomEntrp = new JLabel("Nom Entreprise");
                JButton btExit = new JButton("Exit");
                btExit.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.exit(0);
                    }
                });

                JPanel bp = new JPanel();
                bp.add(btUsr);
                bp.add(lbNomEntrp);
                bp.add(btExit);

                JPanel departPan = new JPanel();
                for (JButton b : listDepart){
                    departPan.add(b);
                }
                Fenetre.this.getContentPane().add(departPan,BorderLayout.CENTER);
                Fenetre.this.getContentPane().add(bp,BorderLayout.PAGE_START);

            }
        }
    }



    public class userInfoListener implements ActionListener{
        private Utilisateur     usrCourant;
        private JButton         btUsr;

        userInfoListener(Utilisateur usr, JButton btusr){
            this.usrCourant=usr;
            this.btUsr=btusr;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            UserInformation userInformation =new UserInformation(usrCourant,btUsr);
            btUsr.setEnabled(false);
            Fenetre.this.getContentPane().add(userInformation,BorderLayout.SOUTH);
            Fenetre.this.setVisible(true);
            Fenetre.this.repaint();
        }
    }

    private class openDepartment implements ActionListener {

        private Utilisateur     usrCourant;
        private String          nomDepart;

        openDepartment(Utilisateur usrCourant, String nomDepart) {
            this.usrCourant=usrCourant;
            this.nomDepart=nomDepart;
        }

        /**
         * Invoked when an action occurs.
         * @param e the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            Fenetre.this.setVisible(false);
            DepartementInfo depInf = new DepartementInfo(usrCourant,nomDepart);
        }
    }
}




