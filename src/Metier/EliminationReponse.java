package Metier;

import java.util.ArrayList;

public class EliminationReponse extends Question {
	private String reponseCorrecte;
	private ArrayList<String> reponsesIncorrectes;

	public EliminationReponse(String intitule, Difficulte difficulté,Notion notion,int temps,int points, Metier metier,String reponseCorrecte) {
		super(intitule, difficulté, notion, temps, points,metier);
		this.reponseCorrecte = reponseCorrecte;
		this.reponsesIncorrectes = new ArrayList<String>();
	}

	public EliminationReponse(String intitule, Difficulte difficulté,Notion notion,int temps,int points, Metier metier,String reponseCorrecte, String explication) {
		super(intitule, difficulté, notion, temps, points, metier, explication);
		this.reponseCorrecte = reponseCorrecte;
		this.reponsesIncorrectes = new ArrayList<String>();
	}
}