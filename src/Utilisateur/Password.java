package Utilisateur;

public class Password implements Donnees<String> {

    public static int TAILLE_PASSWORD = 30;

    protected String valeur;

    public Password(String valeur){
        setValeur(valeur);
    }

    public Password() {

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
        }else {
            this.valeur=null;
            return false;
        }
    }

    @Override
    public Boolean checkPattern(String valeur) {
        return valeur.length()<=TAILLE_PASSWORD;
    }

    @Override
    public int compareTo(String c1, String c2) {
        return c1.compareTo(c2);
    }

    @Override
    public String toString() {
        return "Password : "+valeur;
    }

    public Boolean passwordCorrect(Password pswd){
        return pswd.getValeur()==this.getValeur();
    }

}
