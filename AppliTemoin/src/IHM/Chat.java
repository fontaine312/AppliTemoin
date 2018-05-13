package IHM;

import Tchat.Message;
import Tchat.Tchat;
import Utilisateur.Login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import Tchat._EnsembleMessage;
import java.rmi.RemoteException;

/**
 * Interface de chat a travers laquelle les utilsateurs communiquent entre eux
 */
public class Chat extends JPanel implements Runnable {

    private JTextField      messageBox;
    private JButton         sendMessage;
    private JTextArea       chatBox;
    private String          loginUsrCourant;

    private _EnsembleMessage tchat;

    int nbMessageAff = 1;


    /**
     * Constructeur permettant de creer l'interface de chat
     * @param loginUsrCourant Login de l'utilisateur courant
     * @param tchat module de tchat partage par tous les clients
     */
    public Chat(String loginUsrCourant, _EnsembleMessage tchat){
        super();
        this.loginUsrCourant=loginUsrCourant;
        this.tchat = tchat;
        display();
    }

    /**
     * Methode permettant de charger les differents composants de l'interface
     */
    private void display() {
        setLayout(new BorderLayout());

        JPanel southPanel = new JPanel();
        southPanel.setLayout(new GridBagLayout());

        messageBox = new JTextField(30);
        messageBox.requestFocusInWindow();
        messageBox.addKeyListener(new enterKeyAction());

        chatBox = new JTextArea(20,30);
        chatBox.setEditable(false);
        chatBox.setFont(new Font("Arial",Font.PLAIN,15));
        chatBox.setLineWrap(true);

        sendMessage = new JButton("Envoyer");
        sendMessage.addActionListener(new sendMessageListener());

        JScrollPane jsp = new JScrollPane(chatBox);
        add(jsp, BorderLayout.CENTER);

        GridBagConstraints left = new GridBagConstraints();
        left.anchor = GridBagConstraints.LINE_START;
        left.fill = GridBagConstraints.HORIZONTAL;
        left.weightx = 512.0D;
        left.weighty = 1.0D;

        GridBagConstraints right = new GridBagConstraints();
        right.fill=GridBagConstraints.NONE;
        right.insets = new Insets(0,10,0,0);
        right.anchor = GridBagConstraints.LINE_END;
        right.weighty = 1.0D;
        right.weightx = 1.0D;

        southPanel.add(messageBox,left);
        southPanel.add(sendMessage,right);

        add(BorderLayout.SOUTH,southPanel);


    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        try{
            while (true){
                if (nbMessageAff <= tchat.getNbMessage()){
                    chatBox.append(tchat.afficher(nbMessageAff).toString()+"\n");
                    nbMessageAff++;
                }
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }catch (RemoteException re){
            re.printStackTrace();
        }
    }


    /**
     * Listener appeler lorsque l'utilisateur clique sur le boutton envoyer
     */
    private class sendMessageListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            updateTchatAfterInput();
        }
    }

    /**
     * Listener appeler lorsque l'utilisateur appuie sur la touche entree
     */
    private class enterKeyAction implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                updateTchatAfterInput();
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }


    /**
     * Methode permettant de mettre a jour le chat
     */
    private void updateTchatAfterInput() {
        if (messageBox.getText().length()<1){
            //rien faire
        }else{
            try{
                tchat.add(new Message(messageBox.getText(), new Login(loginUsrCourant)));
            }catch (Exception e1){
                e1.printStackTrace();
                }
                messageBox.setText("");
        }
        messageBox.requestFocusInWindow();
    }

}
