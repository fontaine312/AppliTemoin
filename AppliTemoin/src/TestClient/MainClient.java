package TestClient;


import IHM.Fenetre;


import InteractionJson._UserInteraction;
import Stockage._ActionAvance;
import Tchat._EnsembleMessage;


import java.util.ArrayList;


public class MainClient {

    public static void main(String[] args) {
        ArrayList<Object> modules = new ArrayList<>();
        try {
            AppliClient client = new AppliClient(".properties");

            _UserInteraction userInteract = (_UserInteraction) client.getModule("UserConnect");

            _EnsembleMessage tchat = (_EnsembleMessage) client.getModule("tchat");

           _ActionAvance actionAvance = (_ActionAvance) client.getModule("ActionAvance");

            modules.add(userInteract);

            modules.add(tchat);

            modules.add(actionAvance);

            System.out.println("Connection à l'application établie.");

            new Fenetre(client,modules);

        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

}
