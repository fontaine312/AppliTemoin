package IHM;

import InteractionJson.UserInteraction;
import Utilisateur.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;




public class UserInformation extends JPanel {

    private boolean         activated =false;
    private Utilisateur     userCourant;
    private JButton         btParent;

    public UserInformation(Utilisateur usr, JButton btParent){
        super(new GridBagLayout());
        userCourant=usr;
        activated=true;
        this.btParent=btParent;
        this.setVisible(true);


        GridBagConstraints cs = new GridBagConstraints();
        cs.fill = GridBagConstraints.HORIZONTAL;


        JLabel loginLabel, nomLabel, prenomLabel, emailLabel;
        JButton btNom, btPrenom, btEmail;

        UserInteraction ui = new UserInteraction();



        loginLabel = new JLabel("Login : "+userCourant.getLog().getValeur());
        nomLabel = new JLabel("Nom : "+userCourant.getNom().getValeur());
        prenomLabel = new JLabel("Prenom : "+userCourant.getPrenom().getValeur());
        emailLabel = new JLabel("Email : "+userCourant.getEmail().getValeur());

        btNom = new JButton("Changer le nom");
        btPrenom = new JButton("Changer le prenom");
        btEmail = new JButton("Changer le mail");

        cs.gridx = 0;
        cs.gridy = 0;
        cs.gridwidth = 1;
        this.add(loginLabel,cs);

        cs.gridx = 0;
        cs.gridy = 1;
        cs.gridwidth = 1;
        this.add(nomLabel,cs);

        cs.gridx = 1;
        cs.gridy = 1;
        cs.gridwidth = 1;
        btNom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nouveauNom = JOptionPane.showInputDialog("Quel est le nouveau nom ?");
                if(UserInformation.this.userCourant.getNom().checkPattern(nouveauNom)){
                    UserInformation.this.userCourant.getNom().setValeur(nouveauNom);
                    ui.updateUtilisateur(UserInformation.this.userCourant);
                    nomLabel.setText(UserInformation.this.userCourant.getNom().toString());
                }
                else
                    JOptionPane.showMessageDialog(UserInformation.this,"Valeur incorrecte");
            }
        });
        this.add(btNom,cs);

        cs.gridx = 0;
        cs.gridy = 2;
        cs.gridwidth = 1;
        this.add(prenomLabel,cs);
        cs.gridx = 1;
        cs.gridy = 2;
        cs.gridwidth = 1;
        this.add(btPrenom,cs);
        btPrenom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nouveauPrenom = JOptionPane.showInputDialog("Quel est le nouveau prenom ?");
                if(userCourant.getPrenom().checkPattern(nouveauPrenom)){
                    userCourant.getPrenom().setValeur(nouveauPrenom);
                    ui.updateUtilisateur(userCourant);
                    prenomLabel.setText(userCourant.getPrenom().toString());
                }
                else
                    JOptionPane.showMessageDialog(UserInformation.this,"Valeur incorrecte");
            }
        });

        cs.gridx = 0;
        cs.gridy = 3;
        cs.gridwidth = 1;
        this.add(emailLabel,cs);
        cs.gridx = 1;
        cs.gridy = 3;
        cs.gridwidth = 1;
        this.add(btEmail,cs);
        btEmail.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nouveauMail = JOptionPane.showInputDialog("Quel est le nouveau mail ?");
                if(userCourant.getEmail().checkPattern(nouveauMail)){
                    userCourant.getEmail().setValeur(nouveauMail);
                    ui.updateUtilisateur(userCourant);
                    emailLabel.setText(userCourant.getEmail().toString());
                }
                else
                    JOptionPane.showMessageDialog(UserInformation.this,"Valeur incorrect");
            }
        });

        JButton btCancel = new JButton("Retour");
        cs.gridx = 0;
        cs.gridy=4;
        cs.gridwidth=1;
        this.add(btCancel,cs);
        btCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserInformation.this.setVisible(false);
                activated=false;
                btParent.setEnabled(true);
            }
        });

        this.setVisible(true);
        this.repaint();

    }
}
