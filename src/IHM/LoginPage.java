package IHM;

import InteractionJson.UserInteraction;
import Utilisateur.Login;
import Utilisateur.Password;
import Utilisateur.Utilisateur;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPage extends JDialog {

    private JTextField tfUsername;
    private JPasswordField pfPassword;
    private JLabel lbUsername;
    private JLabel lbPassword;
    private JButton btnLogin;
    private JButton btnCancel;
    private boolean succeeded;

    private Utilisateur usrCourant;

    public LoginPage(Frame parent){
        super(parent,"Login Page", true);
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
        cs.gridx = 1;
        cs.gridy = 1;
        cs.gridwidth = 2;
        pan.add(pfPassword, cs);
        pan.setBorder(new LineBorder(Color.GRAY));

        btnLogin = new JButton("Login");

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserInteraction ui = new UserInteraction();

                if(ui.idCorrect(new Login(getUsername()),new Password(getPassword()))){
                    JOptionPane.showMessageDialog(LoginPage.this,
                            "Salut toi "+getUsername()+"! Tu t'es autentifie correctement",
                            "Login",
                            JOptionPane.INFORMATION_MESSAGE);
                    succeeded = true;
                    usrCourant=ui.getUtilisateur(new Login(getUsername()));
                    dispose();
                }else{
                    JOptionPane.showMessageDialog(LoginPage.this,
                            "Login ou mot de passe Invalide mon coco",
                            "Login",
                            JOptionPane.ERROR_MESSAGE);

                    //reinitialisation des login et mdp
                    tfUsername.setText("");
                    pfPassword.setText("");
                    succeeded = false ;
                }

            }
        });

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


    public String getUsername() {
        return tfUsername.getText().trim();
    }

    public String getPassword() {
        return new String(pfPassword.getPassword());
    }

    public boolean isSucceeded() {
        return succeeded;
    }

    public Utilisateur getUsrCourant() { return usrCourant; }
}
