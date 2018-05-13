import IHM.Fenetre;
import InteractionJson.UserInteraction;
import RMI.Client_RMI;
import RMI.InstanceConfig;
import Stockage.ActionAvance;
import Stockage.ActionBasique;


import javax.swing.*;
import java.awt.*;
import java.io.File;
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
        /*try {
            App f = new App("C:\\Users\\yvesf\\IdeaProjects\\ProjetFramework\\src\\prop.properties");
            f.connection();
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        //Fenetre fen = new Fenetre();
        ActionAvance av = null;
        try {
            av = new ActionAvance();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        av.copyFile("Entreprise\\Compta\\FichePaie\\fichePaie001.txt","C:\\Users\\yvesf\\IdeaProjects");
       /* ActionBasique ab = new ActionBasique();
        String name = "Entreprise";
        ab.goTo(name);
        File[] f = ab.listeFichier();
        if (f.length!=0){
            String s =name+"\\";
            for (File fo : f){
                if (fo.isDirectory()){
                    System.out.println("Directory: " + fo.getName());
                    showFiles(fo.listFiles());
                }
                else
                    System.out.println("File :"+fo.getName());
            }
        }*/
    }

    public static void showFiles(File[] files) {
        for (File file : files) {
            if (file.isDirectory()) {
                System.out.println("Directory: " + file.getName());
                showFiles(file.listFiles()); // Calls same method again.
            } else {
                System.out.println("File: " + file.getName());
            }
        }
    }

}
