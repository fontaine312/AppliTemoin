package Utilisateur;

public class Prenom implements Donnees<String>{

    public static int TAILLE_PRENOM = 30;
    protected String valeur;

    public Prenom(){}

    public Prenom(String valeur){
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
        return valeur.length()<=TAILLE_PRENOM && valeur.matches(pattern);
    }


    @Override
    public int compareTo(String c1, String c2) {
        return c1.compareTo(c2);
    }

    @Override
    public String toString() {
        return "Prenom : "+valeur;
    }

}
