package src.Metier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Questionnaire {
	private final Metier   metier;
    private String         nom;
    private Ressource      ressource;
    private HashMap<Notion, HashMap<Difficulte, Integer>> hmQuestionsParDifficulte; // Associe chaque notion à un nombre questions par difficulté de
    private int            tempsEstimée;
    private int            pointMax;
    private boolean        chronoBool;
    private List<Notion>   lstNotions;
    private List<Question> lstQuestion;

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
        this.lstNotions = new ArrayList<>();
		this.lstQuestion = new ArrayList<>();
        this.hmQuestionsParDifficulte = new HashMap<>();
        this.pointMax  = 0;
		this.metier    = metier;
    }

    public void ajouterNotion(Notion notion)
    {
        if(!lstNotions.contains(notion))
        {
            lstNotions.add(notion);
            hmQuestionsParDifficulte.put(notion,new HashMap<>());
        }
    }
	public void supprimerNotion(Notion notion)
	{
		if(lstNotions.contains(notion))
		{
			lstNotions.remove(notion);
			hmQuestionsParDifficulte.remove(notion);
		}
	}

    public void defNbQuestion(Notion notion,Difficulte difficulte,int nbQuestions)
    {
        if (!hmQuestionsParDifficulte.containsKey(notion))
        {
            ajouterNotion(notion);
        }
        hmQuestionsParDifficulte.get(notion).put(difficulte, nbQuestions);
    }

    // Get
	public String    getNom()         {return this.nom;}
    public Ressource getRessource()   {return this.ressource;}
	public List<Notion> getLstNotions()  {return this.lstNotions;}
	public List<Question> getLstQuestion() {return this.lstQuestion;}
    public Notion    getNotion(int i) {return this.lstNotions.get(i);}
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

		for (Question qs : this.lstQuestion)
		{
			this.pointMax += qs.getPoint();
			this.tempsEstimée += qs.getTemps();
		}
	}

    public void initLstQuestions()
    {
        for(Notion n : this.hmQuestionsParDifficulte.keySet())
		{
            for(Map.Entry<Difficulte, Integer> entry : this.hmQuestionsParDifficulte.get(n).entrySet())
            {
                Difficulte difficulte = entry.getKey();
                int nbQuestions = entry.getValue();
                for(int i = 0; i < nbQuestions; i++)
                {
                    Question qs = metier.getQuestionAleatoire(n, difficulte);
                    if(qs != null)
                    {
                        this.lstQuestion.add(qs);
                    }
                }
            }
		}
	}

	@Override
	public String toString(){
		String str = "Questionnaire : " + this.nom + "\n";
		str += "Ressource : " + this.ressource.toString() + "\n";
		str += "Chrono : " + this.chronoBool + "\n";
		str += "Temps estimé : " + this.tempsEstimée + "\n";
		str += "Point Max : " + this.pointMax + "\n";
		str += "Liste des notions : \n";
		for(Notion n : this.lstNotions)
		{
			str += n.toString() + "\n";
		}
		str += "\nListe des questions : \n\n";
		for(Question qs : this.lstQuestion)
		{
			str += qs.toString() + "\n";
		}

		return str;
	}
}
