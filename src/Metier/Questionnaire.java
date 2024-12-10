package Metier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Questionnaire {
    private String         nom;
    private Ressource      ressource;
    private HashMap<Notion, HashMap<String, Integer>> questionsParDifficulte; // Associe chaque notion à un nombre questions par difficulté de
    private int            tempsEstimée;
    private int            pointMax;
    private boolean        chronoBool;
    private List<Notion>   listNotions;
    private List<Question> listQuestion;

    /**
     * Constructeur de la classe Questionnaire.
     *
     * @param titre      Le titre du questionnaire.
     * @param ressource  La ressource concernée par le questionnaire.
	 * @param chronoBool Le booléen pour activer le chrono.
     */
    public Questionnaire(String titre,Ressource ressource,boolean chronoBool) {
        this.nom       = titre;
        this.ressource = ressource;
        this.tempsEstimée = 0;
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
	public String    getNom()         {return this.nom;}
    public Ressource getRessource()   {return this.ressource;}
	public List<Notion> getNotions()  {return this.notions;}
    public Notion    getNotion(int i) {return this.notions.get(i);}
    public boolean   getChronoBool()  {return this.chronoBool;}
	public int       getTempsEstimée(){return this.tempsEstimée;}
	public int       getPointMax()    {return this.pointMax;}

    // Set
    public void setRessource(Ressource ressource){this.ressource  = ressource;}
	public void setChronoBool(boolean chronoBool){this.chronoBool = chronoBool;}

    public void majValeurs()
	{
		this.pointMax = 0;
		this.tempsEstimée = 0;

		for (Question qs : this.listQuestion)
		{
			this.pointMax += qs.getPoint();
			this.tempsEstimée += qs.getTemps();
		}
	}

    public void initListQuestions()
    {
        for()
    }
}
