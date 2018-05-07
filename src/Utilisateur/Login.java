package Utilisateur;

public class Login implements Donnees<String> {

    public static int TAILLE_LOGIN = 30;
    protected String valeur;

    public Login(){};

    public Login(String valeur){
        setValeur(valeur);
    }

    @Override
    public String getValeur() {
        return valeur;
    }

    @Override
    public Boolean setValeur(String valeur) {
        if (checkPattern(valeur)){
            this.valeur=valeur;
            return true;
        }else{
            this.valeur=null;
            return false;
        }
    }

    @Override
    public Boolean checkPattern(String valeur) {
        String pattern = "([a-zA-Z]+)";
        return valeur.matches(pattern) && valeur.length()<=TAILLE_LOGIN;
    }

    @Override
    public int compareTo(String c1, String c2) {
        return c1.compareTo(c2);
    }

    @Override
    public String toString() {
        return "Login : "+valeur;
    }

}
