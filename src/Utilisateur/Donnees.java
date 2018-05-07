package Utilisateur;


public interface Donnees <T>{

    abstract T getValeur();
    abstract Boolean setValeur(T valeur);
    abstract Boolean checkPattern(T valeur);
    abstract int compareTo(T c1, T c2);
    abstract String toString();

}
