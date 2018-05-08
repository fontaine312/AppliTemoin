package IHM;

import Utilisateur.Utilisateur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DepartementInfo extends JFrame {

    private Utilisateur     usrCourant;
    private String          nomDepart;


    DepartementInfo(Utilisateur usrCourant, String nomDepart){
        this.usrCourant=usrCourant;
        this.nomDepart=nomDepart;
        initialize();
    }

    private void initialize() {

        //Ajout d'un titre
        this.setTitle("Drive Entreprise");
        //Definition de la taille de la fenêtre
        this.setSize(400,500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Positionnement de la fenêtre au centre
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        JButton btUsr = new JButton(usrCourant.getLog().getValeur());
        btUsr.addActionListener(new useroInfoListener(btUsr));

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
        this.getContentPane().add(bp,BorderLayout.PAGE_START);

        JPanel middlePanel = new JPanel();
        JLabel lbDepart = new JLabel(nomDepart);
        JTree trArborescence = new JTree();
        middlePanel.add(lbDepart,BorderLayout.PAGE_START);
        middlePanel.add(new JScrollPane(trArborescence),BorderLayout.WEST);
        this.getContentPane().add(middlePanel,BorderLayout.CENTER);

    }


    public class useroInfoListener implements ActionListener{
        private JButton         btUsr;

        useroInfoListener(JButton btusr){
            this.btUsr=btusr;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            UserInformation userInformation =new UserInformation(usrCourant,btUsr);
            btUsr.setEnabled(false);
            DepartementInfo.this.getContentPane().add(userInformation,BorderLayout.PAGE_END);
            DepartementInfo.this.setVisible(true);
            DepartementInfo.this.repaint();
        }
    }


}
