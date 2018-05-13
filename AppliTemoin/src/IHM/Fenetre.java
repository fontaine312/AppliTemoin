package IHM;

import InteractionJson._UserInteraction;
import TestClient.AppliClient;
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

/**
 * Interface initiale de l'application
 */
public class Fenetre extends JFrame {


    private JButton             btnLogin = new JButton("S'identifier");
    final String                path = "Image/logo.png";
    private Utilisateur         usrCourant;
    private AppliClient         client;

    _UserInteraction            uiInterface;
    ArrayList<Object>           listModules;


    /**
     * Constructeur de l'interface initiale
     *
     * @param client Objet Appliclient qui permettra la connexion au serveur
     * @param listModules liste des modules charges par l'application client
     */
    public Fenetre(AppliClient client, ArrayList<Object> listModules){
        this.listModules = listModules;
        this.uiInterface = (_UserInteraction) this.listModules.get(0);
        this.client=client;
        initialize();
    }

    /**
     * Methode permettant d'initaliser les differents composants de l'interface
     */
    private void initialize() {
        JPanel panel = new JPanel();
        panel.add(btnLogin);
        btnLogin.addActionListener(new openLoginPage());

        try {
            //Ajout d'une image
            BufferedImage logo = ImageIO.read(new File(path));
            JLabel picLabel = new JLabel(new ImageIcon(logo));
            JPanel topPanel = new JPanel();
            //Ajout du composant
            topPanel.add(picLabel);
            this.add(topPanel,BorderLayout.NORTH);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Ajout d'un titre
        this.setTitle("Drive Entreprise");
        //Definition de la taille de la fenêtre
        this.setSize(400,500);
        //Termine le processus lorsqu'on clique sur la croix rouge
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Positionnement de la fenêtre au centre
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        //Ajout des composants
        this.getContentPane().add(panel,BorderLayout.CENTER);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    /**
     * Classe appele pour ouvrir l'interface de connexion
     * et passer a la prochaine interface si la connexion a eu lieu
     */
    class openLoginPage implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent event){
            LoginPage logP = new LoginPage(Fenetre.this,uiInterface);
            logP.setVisible(true);
            //Si l authentif a marche
            if(logP.isSucceeded()){
                btnLogin.setVisible(false);
                usrCourant = logP.getUsrCourant();

                Fenetre.this.setVisible(false);
                new DepartementInfo(usrCourant,listModules);
            }
        }
    }

    /**
     * Getter pour le client
     * @return l'attribut client
     */
    public AppliClient getClient() {
        return client;
    }
}




