package src;

import java.util.ArrayList;
import src.Metier.*;

public class Controleur
{
    private Metier metier;
    //private FrameAccueil frameAccueil;


    public Controleur()
    {
        this.metier = new Metier();
    }

    // Get

    public Metier getMetier()
    {
        return this.metier;
    }

	// Getters Individuels

	public Question getQuestion(Question question){
		return this.metier.getQuestion(question);
	}
	public Notion getNotion(Notion notion){
		return this.metier.getNotion(notion);
	}
	public Ressource getRessource(Ressource ressource){
		return this.metier.getRessource(ressource);
	}

	// Getters Listes

	public ArrayList<Notion> getNotions(){
        return this.metier.getNotions();
    }
    public ArrayList<Ressource> getRessources(){
        return this.metier.getRessources();
    }
    public ArrayList<Question> getQuestions(){
        return this.metier.getQuestions();
    }

	public Notion getNotionById(int idNotion){
		return this.metier.getNotionById(idNotion);
	}

	public ArrayList<Question> getQuestionsParIdNotion(int idNotion){
		return this.metier.getQuestionsParIdNotion(idNotion);
	}

    // Set

    public void setMetier(Metier metier)
    {
        this.metier = metier;
    }

	public void ajouterNotion(Notion notion)
	{
		this.metier.ajouterNotion(notion);
	}
	public void ajouterQuestion(Question question)
	{
		this.metier.ajouterQuestion(question);
	}
	public void ajouterRessource(Ressource ressource)
	{
		this.metier.ajouterRessource(ressource);
	}


	public void supprimerNotion(Notion notion)
	{
		this.metier.supprimerNotion(notion);
	}
	public void supprimerQuestion(Question question)
	{
		this.metier.supprimerQuestion(question);
	}
	public void supprimerRessource(Ressource ressource)
	{
		this.metier.supprimerRessource(ressource);
	}

}