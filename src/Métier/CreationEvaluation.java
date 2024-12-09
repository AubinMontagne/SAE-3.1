package Metier;

import java.util.*;
import Metier.Notion;
import Metier.Ressource;

public class CreationEvaluation
{
    private int tempsTotal;
    private boolean chrono;
    private Ressource resource;
    private List<Notion> notions;
    private Map<Notion, Map<String, Integer>> questionsParDifficulte; // Associe chaque notion à un nombre questions par difficulté de




    // Le concepteur choisit UNE ressource
    // Puis il choisit UNE ou PLUSIEURS notions
    // Pour CHAQUE notion, il précise le nombre de questions selon la difficulté
    public CreationEvaluation()
    {

    }

    public void ajouterNotion(Notion notion)
    {
        if(!notions.contains(notion))
        {
            notions.add(notion);
            questionsParDifficulte.put(notion,new HashMap<>());
        }
    }

    public void defNbQuestion(Notion notion,String difficulté,int nbQuestions)
    {
        if (!questionsParDifficulte.containsKey(notion))
        {
            ajouterNotion(notion);
        }
        questionsParDifficulte.get(notion).put(difficulte, nombre);
    }


}
