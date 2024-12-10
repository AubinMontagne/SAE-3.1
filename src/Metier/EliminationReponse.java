package Metier;

import java.util.ArrayList;

public class EliminationReponse extends Question {
	private String reponseCorrecte;
	private ArrayList<String> reponses;
	private int pointsPerdusParElimination;

	/**
	 * Liste des réponses dans l'ordre d'élimination.
	 * @param reponseCorrecte La réponse correcte.
	 * @param reponses         Les réponses incorrectes et la réponses correcte dans l'ordre définit par le concepteur.
	 * @param pointsPerdusParElimination Les points perdus par élimination.
	 */
	public EliminationReponse(String intitule, Difficulte difficulté, Notion notion, int temps, int points, Metier metier, String reponseCorrecte, int pointsPerdusParElimination) {
		super(intitule, difficulté, notion, temps, points, metier);
		this.reponseCorrecte = reponseCorrecte;
		this.reponses = new ArrayList<>();
		this.pointsPerdusParElimination = pointsPerdusParElimination;
	}

	public EliminationReponse(String intitule, Difficulte difficulté, Notion notion, int temps, int points, Metier metier, String reponseCorrecte, String explication, int pointsPerdusParElimination) {
		super(intitule, difficulté, notion, temps, points, metier, explication);
		this.reponseCorrecte = reponseCorrecte;
		this.reponses = new ArrayList<>();
		this.pointsPerdusParElimination = pointsPerdusParElimination;
	}

	// Getters
	public String getReponseCorrecte() {
		return this.reponseCorrecte;
	}

	public ArrayList<String> getreponses() {
		return this.reponses;
	}

	public int getPointsPerdusParElimination() {
		return this.pointsPerdusParElimination;
	}

	public boolean isReponseCorrecte(String reponse) {
		return this.reponseCorrecte.equals(reponse);
	}

	// Setters
	public void setReponseCorrecte(String reponseCorrecte) {
		this.reponseCorrecte = reponseCorrecte;
	}

	public void setreponses(ArrayList<String> reponses) {
		this.reponses = reponses;
	}

	public void setPointsPerdusParElimination(int pointsPerdusParElimination) {
		this.pointsPerdusParElimination = pointsPerdusParElimination;
	}

	// Methods to add and remove incorrect responses
	public void addReponseIncorrecte(String reponseIncorrecte) {
		this.reponses.add(reponseIncorrecte);
	}

	public void removeReponseIncorrecte(String reponseIncorrecte) {
		this.reponses.remove(reponseIncorrecte);
	}

	public void echangerPlace(String reponse1, String reponse2) {
		int index1 = this.reponses.indexOf(reponse1);
		int index2 = this.reponses.indexOf(reponse2);
		if (index1 != -1 && index2 != -1) {
			this.reponses.set(index1, reponse2);
			this.reponses.set(index2, reponse1);
		}
	}
}