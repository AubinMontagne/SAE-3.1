package src;

import java.util.ArrayList;
import src.Vue.FrameAccueil;
import src.Metier.Metier;
import src.Metier.Notion;
import src.Metier.Question;
import src.Metier.Ressource;

public class Controleur{
    private Metier metier;
    private FrameAccueil frameAccueil;


    public Controleur(){
        this.metier = new Metier();
		this.frameAccueil = new FrameAccueil(this);
    }

    // Get

    public Metier getMetier(){
        return this.metier;
    }
	
	// Getters Individuels

	public Question  getQuestion (Question question)  {return this.metier.getQuestion(question);}
	public Notion    getNotion   (Notion notion)      {return this.metier.getNotion(notion);}
	public Ressource getRessource(Ressource ressource){return this.metier.getRessource(ressource);}

	// Getters Listes

	public ArrayList<Notion>    getNotions(){
        return this.metier.getNotions();
    }
    public ArrayList<Ressource> getRessources(){
        return this.metier.getRessources();
    }
    public ArrayList<Question>  getQuestions(){
        return this.metier.getQuestions();
    }

	public ArrayList<Question> getQuestionsParNotion(Notion notion){
		return this.metier.getQuestionsParNotion(notion);
	}
	public ArrayList<Notion>   getNotionsParRessource(Ressource ressource){
		return this.metier.getNotionsParRessource(ressource);
	}
    // Set

    public void setMetier(Metier metier){this.metier = metier;}

	public void ajouterNotion   (Notion notion)       {this.metier.ajouterNotion(notion);}
	public void ajouterQuestion (Question question)   {this.metier.ajouterQuestion(question);}
	public void ajouterRessource(Ressource ressource) {this.metier.ajouterRessource(ressource);}

	public void supprimerNotion   (Notion notion)      {this.metier.supprimerNotion(notion);}
	public void supprimerQuestion (Question question)  {this.metier.supprimerQuestion(question);}
	public void supprimerRessource(Ressource ressource){this.metier.supprimerRessource(ressource);}

	public void miseAJour(){
		this.metier.getNotionsFromData("./production/SAE-31/docs");
		this.metier.getRessourcesFromData("./production/SAE-31/docs");
		//this.metier.getQuestionsFromData("./production/SAE-31/docs");
	}

	public static void main(String[] args){
		Controleur controleur = new Controleur();
	}
}