package Utilisateur;

public class Email implements Donnees<String> {

    public static int TAILLE_MAIL = 30;
    protected String valeur;

    public Email(){}

    public Email(String valeur){
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
        }else {
            this.valeur=null;
            return false;
        }
    }

    @Override
    public Boolean checkPattern(String valeur) {
        String pattern = "[a-zA-Z0-9-_]+@[a-z\\-]+\\.[a-z]{2,3}";
        return valeur.length()<=TAILLE_MAIL && valeur.matches(pattern);
    }

    @Override
    public int compareTo(String c1, String c2) {
        return c1.compareTo(c2);
    }

    @Override
    public String toString() {
        return "Email : "+valeur;
    }

}
