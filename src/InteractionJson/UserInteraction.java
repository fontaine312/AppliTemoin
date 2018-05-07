package InteractionJson;

import Utilisateur.*;


import org.json.simple.*;
import org.json.simple.parser.*;

import java.io.IOException;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

import org.codehaus.jackson.map.ObjectMapper;


public class UserInteraction extends UserConnect {



    private static final String path = "C:\\Users\\yvesf\\IdeaProjects\\ProjetFramework\\src\\InteractionJson\\usr.json";

    public Boolean ajouterUtilisateur(Utilisateur utilisateur){

        JSONObject jasonObject = new JSONObject();
        JSONParser parser = new JSONParser();
        ObjectMapper mapper = new ObjectMapper();

        if(presentLogin(utilisateur.getLog())){
            System.out.println("Ce login est déjà utilisé !");
            return false;
        }

        ArrayList<Donnees> donneesUtilisateur = utilisateur.getAll();

        for(int i = 0; i<donneesUtilisateur.size(); i++){
            jasonObject.put(donneesUtilisateur.get(i).getClass().getSimpleName(), donneesUtilisateur.get(i).getValeur());
        }

        try{
            Object obj = parser.parse(new FileReader(path));

            JSONObject jsonObject =  (JSONObject) obj;
            JSONArray users = (JSONArray) jsonObject.get("Utilisateur");
            users.add(jasonObject);

            FileWriter fileWriter = new FileWriter(path);

            JSONObject finalJason = new JSONObject();
            finalJason.put("Utilisateur",users);

            String jsonPretty = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(finalJason);
            fileWriter.write(jsonPretty);
            fileWriter.close();
            System.out.println("Utlisateur "+jasonObject.get("Login")+" ajouté!");
            return true;

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        System.out.println("Erreur ajout utilisateur /!\\");

        return false;
    }

    public Boolean supprimerUtilisateur(Login login){
        JSONParser parser = new JSONParser();
        ObjectMapper mapper = new ObjectMapper();
        int index = -1;

        try{
            Object obj = parser.parse(new FileReader(path));

            JSONObject jsonObject =  (JSONObject) obj;

            JSONArray users = (JSONArray) jsonObject.get("Utilisateur");

            for(int i=0; i<users.size(); i++){
                JSONObject person = (JSONObject) users.get(i);
                if(person.get("Login").equals(login.getValeur())){ index = i; }
            }

            if(index != -1){
                JSONObject finalJason = new JSONObject();
                users.remove(index);

                FileWriter fileWriter = new FileWriter(path);

                finalJason.put("Utilisateur",users);

                fileWriter.write(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(finalJason));
                fileWriter.close();
                System.out.println("utilisateur "+login.getValeur()+" supprimé!");
                return true;
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        System.out.println("Aucun utilisateur à supprimer pour le login : "+login.getValeur());
        return false;

    }

    public Boolean updateUtilisateur(Utilisateur utilisateur){
        Utilisateur user = getUtilisateur(utilisateur.getLog());
        if(user != null){
            JSONParser parser = new JSONParser();
            ObjectMapper mapper = new ObjectMapper();
            int index = -1;
            boolean flag = false;

            try{
                Object obj = parser.parse(new FileReader(path));

                JSONObject jsonObject =  (JSONObject) obj;

                JSONArray users = (JSONArray) jsonObject.get("Utilisateur");

                JSONObject finalJason = new JSONObject();

                for(int i=0; i<users.size(); i++){
                    JSONObject person = (JSONObject) users.get(i);
                    if(person.get("Login").equals(utilisateur.getLog().getValeur())){ index = i; }
                }

                JSONObject nouveau =  (JSONObject) users.get(index);

                ArrayList<Donnees> anciennesDonnees = user.getAll();
                ArrayList<Donnees> nouvellesDonnees = utilisateur.getAll();

                for(int i = 0; i < nouvellesDonnees.size(); i++){
                    if( !nouvellesDonnees.get(i).getClass().getSimpleName().equals("Login")   && !nouvellesDonnees.get(i).getValeur().equals(anciennesDonnees.get(i).getValeur())){
                        ((JSONObject) users.get(index)).replace(nouvellesDonnees.get(i).getClass().getSimpleName(),nouvellesDonnees.get(i).getValeur());
                        flag = true;
                    }
                }
                FileWriter fileWriter = new FileWriter(path);

                finalJason.put("Utilisateur",users);

                fileWriter.write(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(finalJason));
                fileWriter.close();

                if(flag)
                    System.out.println("Les données de "+utilisateur.getLog().getValeur()+" ont été mises à jour!");
                else
                    System.out.println("Les données de "+utilisateur.getLog().getValeur()+" sont déjà à jour!");

                return true;

            } catch (IOException | ParseException e) {
                e.printStackTrace();
            }
        }
        System.out.println("L'utlisateur "+utilisateur.getLog().getValeur()+" n'existe pas, ses données n'ont donc pas été mises à jour!");
        return null;
    }

    public Utilisateur getUtilisateur(Login login) {
        JSONParser parser = new JSONParser();
        Utilisateur user = null;

        try {
            Object obj = parser.parse(new FileReader(path));

            JSONObject jsonObject = (JSONObject) obj;
            JSONArray users = (JSONArray) jsonObject.get("Utilisateur");

            boolean flag = true;

            for (Object o : users) {
                JSONObject person = (JSONObject) o;

                if (person.get("Login").equals(login.getValeur())) {
                    user = new Utilisateur();
                    user.getLog().setValeur(person.get("Login").toString());
                    user.getPswd().setValeur(person.get("Password").toString());
                    user.getNom().setValeur(person.get("Nom").toString());
                    user.getPrenom().setValeur(person.get("Prenom").toString());
                    user.getEmail().setValeur(person.get("Email").toString());
                    flag = false;
                }
            }

            if (flag)
                System.out.println("Aucun utilisateur pour le login : " + login.getValeur());

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }

        return user;
    }

    @Override
    public Boolean presentLogin(Login login) {

        JSONParser parser = new JSONParser();

        try {
            FileReader fr = new FileReader(path);
            JSONObject obj = (JSONObject) parser.parse(fr);
            JSONArray users = (JSONArray) obj.get("Utilisateur");

            for(Object o : users){
                JSONObject usr = (JSONObject) o;

                String log = (String) usr.get("Login");
                if(log.equals(login.getValeur()))
                    return true;
            }
            fr.close();
        }
        catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Boolean ajouterLogin(Login login, Password password){
        Utilisateur usr = new Utilisateur();
        usr.getLog().setValeur(login.getValeur());
        usr.getPswd().setValeur(password.getValeur());
        return ajouterUtilisateur(usr);
    }

    @Override
    public Boolean idCorrect(Login login, Password password){
        JSONParser parser = new JSONParser();
        int index = -1;

        try {
            FileReader fr = new FileReader(path);
            JSONObject obj = (JSONObject) parser.parse(fr);
            JSONArray users = (JSONArray) obj.get("Utilisateur");

            for (int i = 0; i < users.size(); i++) {
                JSONObject person = (JSONObject) users.get(i);
                if (person.get("Login").equals(login.getValeur())) {
                    index = i;
                }
            }

            if(index==-1)
                return false;

            JSONObject usr = (JSONObject) users.get(index);

            String log = (String) usr.get("Login");
            String pswd = (String) usr.get("Password");
            fr.close();
            if (log.equals(login.getValeur()) && pswd.equals(password.getValeur())) {
                System.out.println("Authentification réussit !");
                return true;
            } else {
                System.out.println("Echec authentification !");
                return false;
            }


        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return false;
    }

}
