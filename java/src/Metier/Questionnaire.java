package src.Metier;

import java.util.ArrayList;
import java.util.List;

public class  Questionnaire
{
   private String                        nom;
   private Ressource                     ressource;
   private boolean                       chronoBool;
   private ArrayList<ArrayList<Integer>> lstNbQuestionsParDifficulte;
   private List<Notion>                  lstNotions;
   private List<Question>                lstQuestion;

   // Constructeur

	/**
	 * Constructeur de la class Questionnaire
	 * @param nom			Le nom du questionnaire
	 * @param ressource		La ressource sur laquelle est le questionnaire
	 * @param estChronom	Si le questionnaire est chronométré
	 */
	public Questionnaire(String nom, Ressource ressource, boolean estChronom)
    {
      this.nom                         = nom;
      this.ressource                   = ressource;
      this.chronoBool                  = estChronom;
      this.lstNotions                  = new ArrayList<Notion>();
      this.lstQuestion                 = new ArrayList<Question>();
      this.lstNbQuestionsParDifficulte = new ArrayList<ArrayList<Integer>>();
   }

   // Methode

	/**
	 * Methode ajouterNotion
     * Cette méthode ajoute une notion a la list des notions questionnaire
	 * @param notion La notion à ajouter
	 */
   public void ajouterNotion(Notion notion)
   {
       if (!this.lstNotions.contains(notion))
       {
           this.lstNotions.add(notion);
       }
   }

	/**
	 * Methode supprimerNotion
     * Cette méthode supprime une notion de la list des notions questionnaire
	 * @param notion La notion à supprimer
	 */
   public void supprimerNotion(Notion notion){ this.lstNotions.remove(notion); }

	/**
	 * Methode defNbQuestion
     * Cette métode définie dans la liste des question par dificulté, la questions et sa notion selon leur difficulté
	 * @param notion		La notion des questions
	 * @param difficulte	La difficulté des questions
	 * @param nbQuestion	Le nombre de questions
	 */
   public void defNbQuestion(Notion notion, Difficulte difficulte, int nbQuestion)
   {
      if (!this.lstNotions.contains(notion))
      {
		this.ajouterNotion(notion);
	  }

	  ArrayList<Integer> lstIntegerNotion = new ArrayList<Integer>();
	  lstIntegerNotion.add(lstNotions.indexOf(notion));
	  lstIntegerNotion.add(difficulte.getIndice());
	  lstIntegerNotion.add(nbQuestion);
	  this.lstNbQuestionsParDifficulte.add(lstIntegerNotion);
   }

   // Getter
   public String         getNom()           {return this.nom; }
   public Ressource      getRessource()     {return this.ressource; }
   public List<Notion>   getLstNotions()    {return this.lstNotions; }
   public List<Question> getLstQuestion()   {return this.lstQuestion; }
   public boolean        getChronoBool()    {return this.chronoBool; }

    public int getTempsEstimee()
    {
        int sumTemps = 0;
        for (Question question : this.lstQuestion) {
            sumTemps += question.getTemps();
        }
        return sumTemps;
    }

    public int getPointMax()
    {
        int sumPoints = 0;
        for (Question question : this.lstQuestion)
        {
            sumPoints += question.getPoint();
        }
        return sumPoints;
    }

	// Setter
    public void setNom       (String nom)         {this.nom        = nom; }
    public void setRessource (Ressource ressource){this.ressource  = ressource; }
    public void setChronoBool(boolean chronoBool) {this.chronoBool = chronoBool; }
    public void addQuestion  (Question question)  {this.lstQuestion.add(question); }

    // AUBIN C4EST DIFFICILE ICI
   public void initLstQuestions(Metier metier)
   {
     	for (Notion n : this.lstNotions)
         {
            ArrayList<Question> lstQuestionsNotion = new ArrayList<>(metier.getQuestionsParNotion(n));
			for (ArrayList<Integer> lstIntegerNotion : this.lstNbQuestionsParDifficulte)
            {
				if (lstIntegerNotion.get(0) == this.lstNotions.indexOf(n))
                {
					for (int i = 0; i < lstIntegerNotion.get(2); i++)
                    {
                        Question questionTemp = Metier.getQuestionAleatoire(n, Difficulte.getDifficulteByIndice(lstIntegerNotion.get(1)), lstQuestionsNotion);
                        lstQuestionsNotion.remove(questionTemp);

                        if (questionTemp != null || !this.getLstQuestion().contains(questionTemp))
                        {
                            this.addQuestion(questionTemp);
                        }
                    }
				}
			}
		}
    }

   public String toString ()
   {
       return " Le questionnaire nommée : "+ this.nom + "\nA pour ressource : " + this.ressource + "\nChrono : " + this.chronoBool;
   }
}