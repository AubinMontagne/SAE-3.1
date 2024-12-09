package Metier;

import Question.Question;
import java.util.List;

public class Questionnaire {
    private String         nom;
    private Ressource      ressource;
    private Map<Notion, Map<String, Integer>> questionsParDifficulte; // Associe chaque notion à un nombre questions par difficulté de

    private int            tempsEstimée;
    private int            point;
    private int            pointMax;
    private boolean        chronoBool;
    private List<Notion> notions;

    //private Difficulte tab[] difficulte;
    private List<Question> listQuestion;

    /**
     * Constructeur de la classe Questionnaire.
     *
     * @param titre      Le titre du questionnaire.
     * @param ressource  La ressource concernée par le questionnaire.
     * @param notion     La notion concernée par le questionnaire.
     */
    public Questionnaire(String titre,Ressource ressource, Notion notions,int tempsEstimée,boolean chronoBool) {
        this.nom       = titre;
        this.ressource = ressource;
        this.tempsEstimée = tempsEstimée;
        this.chronoBool = chronoBool;
        this.notions = new ArrayList<>();
        this.questionsParDifficulte = new HashMap<>();
        this.point     = 0;
        this.pointMax  = 0;
    }

    public void ajouterNotion(Notion notion)
    {
        if(!notions.contains(notion))
        {
            notions.add(notion);
            questionsParDifficulte.put(notion,new HashMap<>());
        }
    }

    public void defNbQuestion(Notion notion,String difficulte,int nbQuestions)
    {
        if (!questionsParDifficulte.containsKey(notion))
        {
            ajouterNotion(notion);
        }
        questionsParDifficulte.get(notion).put(difficulte, nbQuestions);
    }

    // Get
    public Ressource getRessource() {return this.ressource;}
    public Notion getNotion()       {return this.notion;   }
    public int getTemps()           {return this.temps;    }
    public int getPoint()           {return this.point;    }

    // Set
    public void setRessource(Ressource ressource){this.ressource = ressource;}
    public void setNotion(Notion notion)         {this.notion    = notion;   }
    public void setTemps(int temps)              {this.temps     = temps;    }
    public void setPoint(int point)              {this.point     = point;    }

    // Méthode pour gérer les mofication des point
    /**
     * Méthode ajouterPoint.
     *
     * @param i Le nombre de points à ajouter à la note globale du questionnaire.
     */
    public void ajouterPoint(int i) {this.point += i;}

    /**
     * Methode ajouterPoint
     *
     * @param i Le nombre de points enlever a la note globale du questionnaire
     */
    public void enleverPoint(int i) {this.point -= i;}

    public void initListQuestions()
    {
        // A faire quand on aura le data des questions
    }
}
