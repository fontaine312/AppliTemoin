import IHM.Fenetre;
import InteractionJson.UserInteraction;
import RMI.Client_RMI;
import RMI.InstanceConfig;


import javax.swing.*;
import java.awt.*;
import java.rmi.RemoteException;

public class Main {

    static class App extends Client_RMI{

        protected App(String fileName) throws Exception, RemoteException {
            super(fileName);
        }
    }

    public static void main(String[] args) {
        //InstanceConfig ic = new InstanceConfig("C:\\Users\\yvesf\\IdeaProjects\\ProjetFramework\\src\\prop.properties");
        //System.out.println(ic.getServName());
        try {
            App f = new App("C:\\Users\\yvesf\\IdeaProjects\\ProjetFramework\\src\\prop.properties");
            f.connection();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Fenetre fen = new Fenetre();
    }
}
