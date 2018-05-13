package TestServeur;

import RMI.Serveur_RMI;

import java.rmi.RemoteException;

public class AppliServeur extends Serveur_RMI{
/**
     * Création d'une instance serveur à l'aide de la classe d'instanciation générique Instance_RMI.
     *
     * @param fileName le nom du fichier de configuration du serveur (.properties)
     * @throws RemoteException
     * @throws Exception*/


    protected AppliServeur(String fileName) throws RemoteException, Exception {
        super(fileName);
    }
}
