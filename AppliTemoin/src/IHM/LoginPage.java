package IHM;

import InteractionJson.UserInteraction;
import InteractionJson._UserInteraction;
import TestClient.AppliClient;
import Utilisateur.Login;
import Utilisateur.Password;
import Utilisateur.Utilisateur;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.rmi.RemoteException;

/**
 * Interface de connexion
 */
public class LoginPage extends JDialog {

    private JTextField tfUsername;
    private JPasswordField pfPassword;
    private JLabel lbUsername;
    private JLabel lbPassword;
    private JButton btnLogin;
    private JButton btnCancel;
    private boolean succeeded;
    private AppliClient client;

    private Utilisateur usrCourant;

    _UserInteraction uiInterface;


    /**
     * Constructeur de l'interface LoginPage
     * @param parent Composant depuis lequel l'interface est lance
     * @param uiInterface Module permettant de faire la connexion au serveur
     */
    public LoginPage(Frame parent, _UserInteraction uiInterface){
        super(parent,"Login Page", true);

        this.uiInterface = uiInterface;
        this.client = ((Fenetre) parent).getClient();

        usrCourant = new Utilisateur();
        JPanel pan = new JPanel(new GridBagLayout());
        GridBagConstraints cs = new GridBagConstraints();

        cs.fill = GridBagConstraints.HORIZONTAL;

        lbUsername = new JLabel("Username: ");
        cs.gridx = 0;
        cs.gridy = 0;
        cs.gridwidth = 1;
        pan.add(lbUsername, cs);

        tfUsername = new JTextField(20);
        tfUsername.addKeyListener(new checkLoginListener());
        cs.gridx = 1;
        cs.gridy = 0;
        cs.gridwidth = 2;
        pan.add(tfUsername, cs);

        lbPassword = new JLabel("Password: ");
        cs.gridx = 0;
        cs.gridy = 1;
        cs.gridwidth = 1;
        pan.add(lbPassword, cs);

        pfPassword = new JPasswordField(20);
        pfPassword.addKeyListener(new checkLoginListener());
        cs.gridx = 1;
        cs.gridy = 1;
        cs.gridwidth = 2;
        pan.add(pfPassword, cs);
        pan.setBorder(new LineBorder(Color.GRAY));

        btnLogin = new JButton("Login");

        btnLogin.addActionListener(new loginActionListener());

        btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        JPanel bp = new JPanel();
        bp.add(btnLogin);
        bp.add(btnCancel);

        getContentPane().add(pan, BorderLayout.CENTER);
        getContentPane().add(bp,BorderLayout.PAGE_END);

        //Fonction permettant de redimensioner les objets
        pack();
        setResizable(false);
        setLocationRelativeTo(parent);
    }


    /**
     * Renvoie le nom taper par l'utilisateur
     * @return le nom taper par l'utilisateur
     */
    public String getUsername() {
        return tfUsername.getText().trim();
    }

    /**
     * Renvoie le mot de passe taper par l'utilisateur
     * @return le mot de passe taper par l'utilisateur
     */
    public String getPassword() {
        return new String(pfPassword.getPassword());
    }


    /**
     * Permet de savoir si l'authentification s'est bien derouler
     * @return Vrai si l'utilisateur a reussi a se connecter
     */
    public boolean isSucceeded() {
        return succeeded;
    }

    /**
     * Renvoie l'objet utilisateur apres la connexion
     * @return l'objet utilisateur apres la connexion
     */
    public Utilisateur getUsrCourant() { return usrCourant; }


    /**
     * Listener appele lorsqu on clique sur le boutton login
     */
    private class loginActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e){
            try {
                checkLogin();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * Listener appeler lorsqu'on appuie sur la touche entree
     */
    private class checkLoginListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER){
                try {
                    checkLogin();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }

        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }

    /**
     * Methode permettant de verifier a travers le serveur que les
     * identifiants taper par l'utilisateur sont correct
     * @throws Exception
     */
    private void checkLogin() throws Exception {
        if(uiInterface.idCorrect(new Login(getUsername()),new Password(getPassword()))){
            usrCourant=uiInterface.getUtilisateur(new Login(getUsername()));
            client.connexion(usrCourant);
            JOptionPane.showMessageDialog(LoginPage.this,
                    "Salut toi "+getUsername()+"! Tu t'es autentifie correctement",
                    "Login",
                    JOptionPane.INFORMATION_MESSAGE);

            succeeded = true;
            dispose();
        }else{
            JOptionPane.showMessageDialog(LoginPage.this,
                    "Login ou mot de passe Invalide",
                    "Login",
                    JOptionPane.ERROR_MESSAGE);

            //reinitialisation des login et mdp
            tfUsername.setText("");
            pfPassword.setText("");
            succeeded = false ;
        }
    }
}
