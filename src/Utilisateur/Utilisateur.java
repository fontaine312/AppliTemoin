package Utilisateur;

import java.util.ArrayList;

public class Utilisateur {

    private Login       log;
    private Password    pswd;
    private Nom         nom;
    private Prenom      prenom;
    private Email       email;

    public Utilisateur(){
        log = new Login();
        pswd = new Password();
        nom = new Nom();
        prenom = new Prenom();
        email = new Email();
    };

    public Utilisateur(Login log, Password pswd, Nom nom, Prenom prenom, Email email){
        this.log = log;
        this.pswd = pswd;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
    }

    public String toString(){
        return "Utilisateur :\n"+log.toString()+"\n"+pswd.toString()+"\n"+nom.toString()+"\n"+prenom.toString()+"\n"+email.toString();
    }

    public Login getLog() {
        return log;
    }

    public Password getPswd() {
        return pswd;
    }

    public Nom getNom() {
        return nom;
    }

    public Prenom getPrenom() {
        return prenom;
    }

    public Email getEmail() {
        return email;
    }

    public ArrayList<Donnees> getAll(){
        ArrayList<Donnees> arDonn = new ArrayList<Donnees>();
        arDonn.add(log);
        arDonn.add(pswd);
        arDonn.add(nom);
        arDonn.add(prenom);
        arDonn.add(email);
        return arDonn;
    }

}
