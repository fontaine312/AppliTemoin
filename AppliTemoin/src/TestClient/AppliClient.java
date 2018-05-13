package TestClient;

import RMI.Client_RMI;

import java.rmi.RemoteException;

public class AppliClient extends Client_RMI{

    /**
     * Création d'une instance client à l'aide de la classe d'instanciation générique Instance_RMI.
     *
     * @param fileName le nom du fichier de configuration du serveur (.properties)
     * @throws Exception
     * @throws RemoteException
     */
    protected AppliClient(String fileName) throws Exception, RemoteException {
        super(fileName);
    }

}
