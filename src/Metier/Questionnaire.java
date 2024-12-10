package Metier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Questionnaire {
	private final Metier   metier;
    private String         nom;
    private Ressource      ressource;
    private HashMap<Notion, HashMap<Difficulte, Integer>> questionsParDifficulte; // Associe chaque notion à un nombre questions par difficulté de
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
    public Questionnaire(String titre,Ressource ressource,boolean chronoBool, Metier metier) {
        this.nom       = titre;
        this.ressource = ressource;
        this.tempsEstimée = 0;
        this.chronoBool = chronoBool;
        this.listNotions = new ArrayList<>();
        this.questionsParDifficulte = new HashMap<>();
        this.pointMax  = 0;
		this.metier    = metier;
    }

    public void ajouterNotion(Notion notion)
    {
        if(!listNotions.contains(notion))
        {
            listNotions.add(notion);
            questionsParDifficulte.put(notion,new HashMap<>());
        }
    }
	public void supprimerNotion(Notion notion)
	{
		if(listNotions.contains(notion))
		{
			listNotions.remove(notion);
			questionsParDifficulte.remove(notion);
		}
	}

    public void defNbQuestion(Notion notion,Difficulte difficulte,int nbQuestions)
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
	public List<Notion> getlistNotions()  {return this.listNotions;}
    public Notion    getNotion(int i) {return this.listNotions.get(i);}
    public boolean   getChronoBool()  {return this.chronoBool;}
	public int       getTempsEstimée(){return this.tempsEstimée;}
	public int       getPointMax()    {return this.pointMax;}

    // Set
	public void setNom(String nom){this.nom = nom;}
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
        for(Notion n : this.questionsParDifficulte.keySet())
		{
            for(Map.Entry<Difficulte, Integer> entry : this.questionsParDifficulte.get(n).entrySet())
            {
                Difficulte difficulte = entry.getKey();
                int nbQuestions = entry.getValue();
                for(int i = 0; i < nbQuestions; i++)
                {
                    Question qs = metier.getQuestionAleatoire(n, difficulte);
                    if(qs != null)
                    {
                        this.listQuestion.add(qs);
                    }
                }
            }
		}
	}
}
