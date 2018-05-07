package InteractionJson;

import Utilisateur.Login;
import Utilisateur.Password;

public abstract class UserConnect extends ParseurJson implements UserConnection{

    public Boolean ajouterLogin(Login login, Password password){
        return null;
    }

    public Boolean idCorrect(Login login, Password password){
        return null;
    }

}
