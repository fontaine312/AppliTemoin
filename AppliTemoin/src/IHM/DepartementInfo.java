package IHM;

import Stockage.ActionAvance;
import Stockage.TypeFichier;
import Stockage._ActionAvance;
import Tchat._EnsembleMessage;
import Utilisateur.Utilisateur;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Interface affichant l'arborescence, le chat ainsi que les documents
 * selectionne depuis l'arborescence
 */
public class DepartementInfo extends JFrame {

    private Utilisateur             usrCourant;
    private String                  rootTree = "Entreprise";
    public  JPanel                  middlePanel;
    public  JTree                   trArborescence;
    public  JLabel                  labelNomFichier;
    private JButton                 buttonDownload;
/*
    private JButton                 buttonUpload;
*/
    public  JTextArea               descriptionFichier;
    private ArrayList<Object>       listModules;
    private _EnsembleMessage        tchat;
    private _ActionAvance           actionAvance;
    private JLabel picLabel;
    private ImageIcon img;
/*
    private JButton buttonCreateDirectory;
*/

    /**
     * Constructeur permettant de creer l'interface
     * @param usrCourant Utilisateur connecter a l'application
     * @param listModules Liste des modules transitants dans l'application client
     */
    DepartementInfo(Utilisateur usrCourant, ArrayList<Object> listModules){
        this.usrCourant = usrCourant;
        this.listModules = listModules;
        this.tchat = (_EnsembleMessage) listModules.get(1);
        this.actionAvance = (_ActionAvance) listModules.get(2);
        try {
            initialize();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * Methode permettant d'initialiser les differents composants de l'interface
     * @throws RemoteException
     */
    private void initialize() throws RemoteException {

        //Ajout d'un titre
        this.setTitle("Drive Entreprise");
        //Definition de la taille de la fenêtre
        this.setSize(400,500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Positionnement de la fenêtre au centre
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);

        JButton btUsr = new JButton(usrCourant.getLog().getValeur());
        btUsr.addActionListener(new useroInfoListener(btUsr));

        JLabel lbNomEntrp = new JLabel("Nom Entreprise");
        JButton btExit = new JButton("Exit");
        btExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
                //this
            }
        });

        JPanel bp = new JPanel();
        bp.add(btUsr);
        bp.add(lbNomEntrp);
        bp.add(btExit);
        this.getContentPane().add(bp,BorderLayout.PAGE_START);

        middlePanel = new JPanel();
        middlePanel.setLayout(new GridBagLayout());

        GridBagConstraints cs = new GridBagConstraints();
        cs.fill = GridBagConstraints.HORIZONTAL;

        labelNomFichier = new JLabel();
        cs.gridx = 0;
        cs.gridy = 0;
        cs.gridwidth = 1;
        labelNomFichier.setVisible(false);
        labelNomFichier.setBackground(Color.LIGHT_GRAY);
        middlePanel.add(labelNomFichier,cs);


        //Ajout d'une image
        img = new ImageIcon();
        picLabel = new JLabel(img);
        cs.gridx = 0;
        cs.gridy = 1;
        middlePanel.add(picLabel,cs);
        picLabel.setBackground(Color.LIGHT_GRAY);


        descriptionFichier = new JTextArea();
        cs.gridx = 1;
        cs.gridy = 1;
        cs.gridwidth = 1;
        middlePanel.add(descriptionFichier,cs);
        descriptionFichier.setBackground(Color.LIGHT_GRAY);
        descriptionFichier.setVisible(false);
        descriptionFichier.setEditable(false);
        buttonDownload = new JButton("Download");
        cs.gridx = 0;
        cs.gridy = 2;
        cs.gridwidth = 1;
        middlePanel.add(buttonDownload,cs);
        buttonDownload.setVisible(false);

/*        buttonUpload = new JButton("Upload");
        cs.gridx=0;
        cs.gridy=3;
        cs.gridwidth=1;
        buttonUpload.setVisible(false);
        middlePanel.add(buttonUpload,cs);
        buttonCreateDirectory = new JButton("Create directory");
        cs.gridx=1;
        cs.gridy=3;
        cs.gridwidth=1;
        buttonCreateDirectory.setVisible(false);
        middlePanel.add(buttonCreateDirectory,cs);*/

        JPanel leftTabPanel = new JPanel();

        middlePanel.setBackground(Color.LIGHT_GRAY);
        //create the root node
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(rootTree);

        //actionAvance.goTo("Entreprise");
        //root.add(getArbo(actionAvance.listeFichier()));

        actionAvance.goTo(rootTree);
        root.add(actionAvance.refreshArbo());

        trArborescence = new JTree(root);
        trArborescence.addTreeSelectionListener(new openDoc());

        leftTabPanel.add(new JScrollPane(trArborescence));

        JPanel rightTabPanel = new JPanel();
        Chat elchat = new Chat(usrCourant.getLog().getValeur(), tchat);
        GridBagConstraints gbe = new GridBagConstraints();
        gbe.weightx = gbe.weighty =1.0D;
        gbe.fill = GridBagConstraints.CENTER;
        rightTabPanel.add(elchat,gbe);
        new Thread(elchat).start();


        this.getContentPane().add(middlePanel,BorderLayout.CENTER);
        this.getContentPane().add(leftTabPanel,BorderLayout.WEST);
        this.getContentPane().add(rightTabPanel,BorderLayout.EAST);

    }

    /**
     * Listener sur le boutton usrCourant qui affiche l'interface permettant la modification
     * des attributs de l'utilisateur courant
     */
    public class useroInfoListener implements ActionListener{
        private JButton         btUsr;

        useroInfoListener(JButton btusr){
            this.btUsr=btusr;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            UserInformation userInformation =new UserInformation(usrCourant,btUsr,listModules);
            btUsr.setEnabled(false);
            DepartementInfo.this.getContentPane().add(userInformation,BorderLayout.PAGE_END);
            DepartementInfo.this.setVisible(true);
            DepartementInfo.this.repaint();
        }
    }

    String cheminNode;
    String nomFichier;

    /**
     * Listener sur chaque noeud de l'arbre
     * Affiche et met a jour le panel du milieu afin d'afficher les informations
     * d'un repertoire ou d'un document
     */
    private class openDoc implements TreeSelectionListener {
        /**
         * Called whenever the value of the selection changes.
         *
         * @param e the event that characterizes the change.
         */
        @Override
        public void valueChanged(TreeSelectionEvent e) {
            Object[] listPath = e.getPath().getPath();
            System.out.println(e.getPath());
            boolean isDirectory =false;
            //Recuperation du dernier noeud selectionne
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) trArborescence.getLastSelectedPathComponent();
            if (node==null){
                return;
            }
            cheminNode = "";
            nomFichier = "";

            for (int i = 0; i<listPath.length; i++){
                if (!listPath[i].toString().equals("")){
                        cheminNode+=listPath[i].toString()+"\\";
                    if (i==listPath.length-1){
                        nomFichier=listPath[i].toString();
                        if (!listPath[i].toString().contains("."))
                            isDirectory=true;
                    }
                }
            }

            if (listPath[listPath.length-1].toString().equals("")){
                for(Component c : middlePanel.getComponents())
                    c.setVisible(false);
            }

            else{

                for(Component c : middlePanel.getComponents())
                    c.setVisible(true);

                if (isDirectory){
                    labelNomFichier.setText("Directory :"+nomFichier);
                    try {
                        //Ajout d'une image
                        BufferedImage logo = ImageIO.read(new File("Image/folder.jpg"));
                        img.setImage(logo);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    //buttonUpload.addActionListener(new UploadDocument());
                    /*buttonCreateDirectory.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String nomNouveauFichier = JOptionPane.showInputDialog("Nom du fichier ?");
                            if (!nomNouveauFichier.equals("")) {
                                System.out.println(cheminNode);
                                try {

                                    if (!actionAvance.createDirectory(cheminNode.substring(0,cheminNode.length()-1),nomNouveauFichier))
                                        System.out.println("error");
                                    actionAvance.goTo(rootTree);
                                    DefaultMutableTreeNode root = new DefaultMutableTreeNode(rootTree);
                                    root.add(actionAvance.getArbo());
                                    trArborescence = new JTree(root);
                                    trArborescence.addTreeSelectionListener(new openDoc());
                                } catch (RemoteException e1) {
                                    e1.printStackTrace();
                                }
                            }
                        }
                    });*/

                    buttonDownload.setVisible(false);
                }
            /*ICI FAIRE CE KI FAUT POUR LE UPLOAD*/

                if (node.isLeaf()){

                    buttonDownload.setVisible(true);
                    cheminNode=cheminNode.substring(0,cheminNode.length()-1);
                    TypeFichier fichier = null;
                    try {
                        fichier = new TypeFichier(cheminNode,".properties");
                    } catch (RemoteException e1) {
                        e1.printStackTrace();
                    }

                    GridBagConstraints cs = new GridBagConstraints();
                    cs.fill = GridBagConstraints.HORIZONTAL;

                    labelNomFichier.setText(nomFichier);

                    descriptionFichier.setText(fichier.toString());
                    try {
                        //Ajout d'une image
                        BufferedImage logo = ImageIO.read(new File("Image/document.png"));
                        img.setImage(logo);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }

                    buttonDownload.addActionListener(new downloadDoc());
                    //buttonUpload.setVisible(false);
                    //buttonCreateDirectory.setVisible(false);
                    middlePanel.repaint();
                }

            }
        }

    }

    /**
     * Listener appeler lorsque l'utilisateur appuie sur le boutton de telechargement
     */
    private class downloadDoc implements ActionListener {
        /**
         * Invoked when an action occurs.
         *
         * @param e the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent e) {
             try {

                 JFileChooser chooser = new JFileChooser();
                 chooser.setCurrentDirectory(new java.io.File("."));
                 chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                 // disable the "All files" option.
                 chooser.setAcceptAllFileFilterUsed(false);
                 //
                 TypeFichier t = actionAvance.getFile(cheminNode);
                 System.out.println(actionAvance.getFile(cheminNode).toString());
                 if (chooser.showOpenDialog(buttonDownload) == JFileChooser.APPROVE_OPTION) {
                     System.out.println(nomFichier);
                         System.out.println(chooser.getSelectedFile().toString());
                         new File(chooser.getSelectedFile().toString(),nomFichier);
                         //copyFileUsingStream(t,new File(chooser.getSelectedFile().toString()+"\\"));
                     return;
                 }
                 else {
                     System.out.println("No Selection ");
                     return;
                 }
            } catch (RemoteException e1) {
                e1.printStackTrace();
            }

        }
    }

    private static void copyFileUsingStream (File source, File dest) throws IOException{
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(source);
            os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0){
                os.write(buffer,0,length);
            }
        }finally {
            is.close();
            os.close();
        }
    }

}
