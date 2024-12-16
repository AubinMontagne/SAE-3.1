package src.Metier;

import java.util.ArrayList;
import java.util.List;

public class Questionnaire{
   private String nom;
   private Ressource ressource;
   private ArrayList<ArrayList<Integer>> lstNbQuestionsParDifficulte;
   private boolean chronoBool;
   private List<Notion> lstNotions;
   private List<Question> lstQuestion;

   public Questionnaire(String nom, Ressource ressource, boolean estChronom){
      this.nom = nom;
      this.ressource = ressource;
      this.chronoBool = estChronom;
      this.lstNotions = new ArrayList<Notion>();
      this.lstQuestion = new ArrayList<Question>();
      this.lstNbQuestionsParDifficulte = new ArrayList<ArrayList<Integer>>();
   }

   public void ajouterNotion(Notion notion){
      	if (!this.lstNotions.contains(notion)){
		 	this.lstNotions.add(notion);
	  	}
   }

   public void supprimerNotion(Notion notion){
       this.lstNotions.remove(notion);
   }

   public void defNbQuestion(Notion notion, Difficulte difficulte, int nbQuestion){
      if (!this.lstNotions.contains(notion)){
		this.ajouterNotion(notion);
	  }
	  ArrayList<Integer> lstIntegerNotion = new ArrayList<Integer>();
	  lstIntegerNotion.add(lstNotions.indexOf(notion));
	  lstIntegerNotion.add(difficulte.getIndice());
	  lstIntegerNotion.add(nbQuestion);
	  this.lstNbQuestionsParDifficulte.add(lstIntegerNotion);
   }

   public String getNom(){
      return this.nom;
   }

   public Ressource getRessource(){
      return this.ressource;
   }

   public List<Notion> getLstNotions(){
      return this.lstNotions;
   }

   public List<Question> getLstQuestion(){
      return this.lstQuestion;
   }

   public boolean getChronoBool(){
      return this.chronoBool;
   }

   public int getTempsEstimee(){
      int sumTemps = 0;
	  for (Question question : this.lstQuestion) {
		 sumTemps += question.getTemps();
	  }
	  return sumTemps;
   }

    public int getPointMax(){
      int sumPoints = 0;
	  for (Question question : this.lstQuestion) {
		 sumPoints += question.getPoint();
	  }
	  return sumPoints;
    }

    public void setNom(String nom){
      this.nom = nom;
   }

    public void setRessource(Ressource ressource){
      this.ressource = ressource;
   }

    public void setChronoBool(boolean chronoBool){
      this.chronoBool = chronoBool;
   }

    public void addQuestion(Question question){
      this.lstQuestion.add(question);
   }

   	public void initLstQuestions(Metier metier){
      	for (Notion n : this.lstNotions) {
			for (ArrayList<Integer> lstIntegerNotion : this.lstNbQuestionsParDifficulte) {
				if (lstIntegerNotion.get(0) == this.lstNotions.indexOf(n)) {
					for (int i = 0; i < lstIntegerNotion.get(2); i++) {
						this.addQuestion(metier.getQuestionAleatoire(n, Difficulte.getDifficulteByIndice(lstIntegerNotion.get(1))));
					}
				}
			}
		}
    }

    public String toString (){
       return " Le questionnaire nommée : "+ this.nom + "\nA pour ressource : " + this.ressource + "\nChrono : " + this.chronoBool;
    }
}