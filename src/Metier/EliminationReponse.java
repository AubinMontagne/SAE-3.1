package Metier;

import java.util.HashMap;

public class EliminationReponse extends Question {
	private String reponseCorrecte;
	private HashMap<String,Integer[]> reponses;

	/**
	 * Liste des réponses dans l'ordre d'élimination.
	 * @param reponseCorrecte La réponse correcte.
	 * @param reponses         Les réponses incorrectes et la réponses correcte dans l'ordre définit par le concepteur.
	 * @param pointsPerdusParElimination Les points perdus par élimination.
	 */
	public EliminationReponse(String intitule, Difficulte difficulté, Notion notion, int temps, int points, Metier metier, String reponseCorrecte) {
		super(intitule, difficulté, notion, temps, points, metier);
		this.reponseCorrecte = reponseCorrecte;
		this.reponses = new HashMap<>();
	}

	public EliminationReponse(String intitule, Difficulte difficulté, Notion notion, int temps, int points, Metier metier, String reponseCorrecte, String explication) {
		super(intitule, difficulté, notion, temps, points, metier, explication);
		this.reponseCorrecte = reponseCorrecte;
		this.reponses = new HashMap<>();
	}

	// Getters
	public String getReponseCorrecte() {
		return this.reponseCorrecte;
	}

	public HashMap<String,Integer[]> getreponses() {
		return this.reponses;
	}

	public int getLastIndex() {
		int indexMax = 0;
		for (Integer[] index : this.reponses.values()) {
			if (index[1] > indexMax) {
				indexMax = index[1];
			}
		}
		return indexMax + 1;
	}

	public boolean isReponseCorrecte(String reponse) {
		return this.reponseCorrecte.equals(reponse);
	}

	// Setters
	public void setReponseCorrecte(String reponseCorrecte) {
		this.reponseCorrecte = reponseCorrecte;
	}

	public void setreponses(HashMap<String,Integer[]>  reponses) {
		this.reponses = reponses;
	}


	// Methods to add and remove incorrect responses
	public void addReponse(String reponse, int points) {
		if (!this.reponses.containsKey(reponse)) {
			this.reponses.put(reponse, new Integer[]{points,this.getLastIndex()});
		}
	}

	public void removeReponse(String reponse) {
		this.reponses.remove(reponse);
	}

	public void echangerPlace(String reponse1, String reponse2) {
		if (this.reponses.containsKey(reponse1) && this.reponses.containsKey(reponse2)) {
			int index1 = this.reponses.get(reponse1)[1];
			int index2 = this.reponses.get(reponse2)[1];
			this.reponses.get(reponse1)[1] = index2;
			this.reponses.get(reponse2)[1] = index1;
		}
	}
}