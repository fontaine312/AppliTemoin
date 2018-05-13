package IHM;

import InteractionJson._UserInteraction;
import Utilisateur.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * Interface permettant a l'utilisateur de voir ses informations
 * ainsi que les modifier
 */
public class UserInformation extends JPanel {

    private Utilisateur     userCourant;
    private ArrayList<Object> listModules;
    private _UserInteraction uiInterface;
    private JButton          btParent;

    /**
     * Constructeur permettant de creer l'interface
     * @param usr Utilisateur courant de l'application
     * @param btParent Boutton depuis lequel l'interface fut appeler
     * @param listModules liste des modules transitant dans l'application client
     */
    public UserInformation(Utilisateur usr, JButton btParent, ArrayList<Object> listModules){
        super(new GridBagLayout());
        userCourant=usr;
        this.btParent=btParent;
        this.setVisible(true);
        this.listModules = listModules;

        uiInterface = (_UserInteraction) listModules.get(0);


        GridBagConstraints cs = new GridBagConstraints();
        cs.fill = GridBagConstraints.HORIZONTAL;

        ArrayList<Donnees> a = userCourant.getAll();

        int posY = 0;

        for (Donnees d : a){
            String typeDonne = d.getClass().getSimpleName();
            if (!typeDonne.equals("Login") && !typeDonne.equals("Password")){
                JLabel label = new JLabel(typeDonne+" : "+d.getValeur());
                JButton button = new JButton("Changer le "+typeDonne);

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String nouvelleVal = JOptionPane.showInputDialog("Quel est le nouveau "+typeDonne+"?");
                        if (d.checkPattern(nouvelleVal)){
                            d.setValeur(nouvelleVal);
                            switch (typeDonne.toLowerCase()){
                                case "prenom" :
                                    userCourant.getPrenom().setValeur(nouvelleVal);
                                    break;
                                case "nom" :
                                    userCourant.getNom().setValeur(nouvelleVal);
                                    break;
                                case "email" :
                                    userCourant.getEmail().setValeur(nouvelleVal);
                                    break;
                                case "telephone" :
                                    userCourant.getTelephone().setValeur(nouvelleVal);
                                    System.out.println(d.getValeur()+" : "+typeDonne);
                                    break;
                            }
                            try {
                                System.out.println(userCourant.toString());
                                System.out.println(uiInterface.toString());
                                //userCourant.getTelephone().setValeur("+33658432096");
                                uiInterface.updateUtilisateur(userCourant);
                            } catch (RemoteException e1) {
                                e1.printStackTrace();
                            }
                            label.setText(d.toString());
                        }
                        else
                            JOptionPane.showMessageDialog(UserInformation.this,"Valeur incorrect");
                    }
                });

                cs.gridx = 0;
                cs.gridy = posY;
                cs.gridwidth = 1;
                this.add(label,cs);

                cs.gridx = 1;
                cs.gridy = posY;
                cs.gridwidth = 1;
                this.add(button,cs);

                posY++;
            }
        }

        JButton btCancel = new JButton("Retour");
        cs.gridx = 0;
        cs.gridy=posY;
        cs.gridwidth=1;
        this.add(btCancel,cs);
        btCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserInformation.this.setVisible(false);
                btParent.setEnabled(true);
            }
        });

        this.setVisible(true);
        this.repaint();

    }
}
