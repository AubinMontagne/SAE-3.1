package Métier;

import java.util.HashMap;


public class QCM extends Question {
    private HashMap<String, Boolean> reponses;

    public QCM(String intitule, String difficulté,Ressources ressource,Notions notion,int temps )
    {
        super(intitule, difficulté, ressource, notion, temps);
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
    public void ajouterReponse(String reponse, Boolean correct)
    {
        this.reponses.put(reponse, correct);
    }
    public void enleverReponse(String reponse)
    {
        this.reponses.remove(reponse);
    }

}