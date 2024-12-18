package src;

import java.util.ArrayList;
import java.util.HashMap;
import src.Vue.FrameAccueil;
import src.Metier.Metier;
import src.Metier.Notion;
import src.Metier.Question;
import src.Metier.Ressource;

public class               Controleur{
	private Metier metier;
    private FrameAccueil frameAccueil;

	/**
	 * Constructeur de la class Controleur
	 */
    public Controleur(){
        this.metier = new Metier();
		this.miseAJour();
		this.frameAccueil = new FrameAccueil(this);
    }

    // Getter
    public Metier getMetier(){return this.metier; }

	public Question  getQuestion (Question question)  {return this.metier.getQuestion(question);}
	public Notion    getNotion   (Notion notion)      {return this.metier.getNotion(notion);}
	public Ressource getRessource(Ressource ressource){return this.metier.getRessource(ressource);}

	public ArrayList<Notion>    getNotions(){return this.metier.getNotions(); }
    public ArrayList<Ressource> getRessources(){return this.metier.getRessources(); }
    public ArrayList<Question>  getQuestions(){return this.metier.getQuestions(); }

	public ArrayList<Question> getQuestionsParNotion (Notion notion)      {return this.metier.getQuestionsParNotion(notion);}
	public ArrayList<Notion>   getNotionsParRessource(Ressource ressource){return this.metier.getNotionsParRessource(ressource);}

    // Setter
    public void setMetier(Metier metier){this.metier = metier;}

	public void ajouterNotion   (Notion notion)       {this.metier.ajouterNotion(notion);}
	public void ajouterQuestion (Question question)   {this.metier.ajouterQuestion(question);}
	public void ajouterRessource(Ressource ressource) {this.metier.ajouterRessource(ressource);}

	public void supprimerNotion   (Notion notion)      {this.metier.supprimerNotion(notion);}
	public void supprimerQuestion (Question question)  {this.metier.supprimerQuestion(question);}
	public void supprimerRessource(Ressource ressource){this.metier.supprimerRessource(ressource);}

	public void miseAJour(){
		this.metier.getRessourcesFromData("java/data/");
		this.metier.getNotionsFromData   ("java/data/");
		this.metier.getQuestionFromData  ("java/data/");
	}

	public void miseAJourFichiers(){
		this.metier.saveRessources("java/data/");
		this.metier.saveNotions("java/data/");
		this.metier.saveQuestions("java/data/");
	}

	public void creerQuestionQCM(String intitule, int difficulte, String notion, int temps, int points, boolean vraiOuFaux, HashMap<String, Boolean> reponses)
	{
		this.metier.ajouterQuestionQCM(
				intitule,
				metier.getDifficulteByIndice(difficulte),
				metier.getNotionByNom(notion),
				temps,
				points,
				vraiOuFaux,
				reponses
		);
	}

	// Main
	public static void main(String[] args){
		Controleur controleur = new Controleur();
	}
}