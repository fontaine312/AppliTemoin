package TestServeur;


import InteractionJson.UserInteraction;
import Stockage.ActionAvance;
import Tchat.Tchat;


public class MainServer {
    public static void main(String[] args) {
        try {
            AppliServeur serv = new AppliServeur(".properties");


            Tchat t = new Tchat();
            System.out.println("Network connected");


            serv.ajoutModule("UserConnect", new UserInteraction("data/usr.json"));
            serv.ajoutModule("tchat", t);
            serv.ajoutModule("ActionAvance", new ActionAvance("C:\\Users\\yvesf\\IdeaProjects\\AppliServeur\\Entreprise"));
            //serv.ajoutModule("");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
