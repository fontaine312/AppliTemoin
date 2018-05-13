package TestServeur;


import InteractionJson.UserInteraction;

import Stockage.ActionAvance;
import Tchat.Tchat;

import java.rmi.RemoteException;


public class MainServer {
    public static void main(String[] args) {
        try {
            AppliServeur serv = new AppliServeur(".properties");

            System.out.println("Network connected");

            Tchat t = new Tchat();

            serv.ajoutModule("UserConnect", new UserInteraction("data/usr.json"));
            serv.ajoutModule("tchat", t);
            serv.ajoutModule("ActionAvance", new ActionAvance("Entreprise"));
            //serv.ajoutModule("");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
