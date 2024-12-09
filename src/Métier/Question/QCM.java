package Question;

import java.util.HashMap;
import Metier.Notion;
import Metier.Ressource;


public class QCM extends Question {
    private HashMap<String, Boolean> reponses;

    public QCM(String intitule, String difficulté,Ressource ressource,Notion notion,int temps,int points)
    {
        super(intitule, difficulté, ressource, notion, temps, points);
        this.reponses = new HashMap<String, Boolean>();
    }
    public QCM(String intitule, String difficulté,Ressource ressource,Notion notion,int temps,int points, String explication)
    {
        super(intitule, difficulté, ressource, notion, temps, points, explication);
        this.reponses = new HashMap<String, Boolean>();
    }

    // Get

    public HashMap<String, Boolean> getReponses()
    {
        return this.reponses;
    }
    // Set

    public void setReponses( HashMap<String, Boolean> reponses)
    {
        this.reponses = reponses;
    }

    // Méthode pour gérer les réponse ajouter/enlever
    public void ajouterReponse(String reponse, Boolean correct)
    {
        this.reponses.put(reponse, correct);
    }
    public void enleverReponse(String reponse)
    {
        this.reponses.remove(reponse);
    }

    public static void main(String[] args) {
        System.out.println("Hello, World!");
    }

}