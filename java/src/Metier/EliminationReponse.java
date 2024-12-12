package src.Metier;

import java.util.HashMap;
public class EliminationReponse extends Question {
	private String reponseCorrecte;
	private HashMap<String,Integer[]> hmReponses;

	/**
	 * Liste des réponses dans l'ordre d'élimination.
	 * @param reponseCorrecte La réponse correcte.
	 * @param hmReponses      Les réponses incorrectes et la réponses correcte dans l'ordre définit par le concepteur.
	 */
	public EliminationReponse(String intitule, Difficulte difficulte, Notion notion, int temps, int points) {
		super(intitule, difficulte, notion, temps, points);
		this.reponseCorrecte = "";
		this.hmReponses = new HashMap<>();
	}

	public EliminationReponse(String intitule, Difficulte difficulte, Notion notion, int temps, int points, String explication) {
		super(intitule, difficulte, notion, temps, points, explication);
		this.reponseCorrecte = "";
		this.hmReponses = new HashMap<>();
	}

	// Getters
	public String getReponseCorrecte() {
		return this.reponseCorrecte;
	}

	public HashMap<String,Integer[]> getHmReponses() {
		return this.hmReponses;
	}

	public int getLastIndex() {
		int indexMax = 0;
		for (Integer[] index : this.hmReponses.values()) {
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

	public void setHmReponses(HashMap<String,Integer[]>  hmReponses) {
		this.hmReponses = hmReponses;
	}


	// Methods to add and remove incorrect responses
	public void ajouterReponse(String reponse, int points, int ordre) {
		if (!this.hmReponses.containsKey(reponse) && ordre <= this.getLastIndex()+1) {
			this.hmReponses.put(reponse, new Integer[]{points, ordre});
		}
	}

	public void removeReponse(String reponse) {
		this.hmReponses.remove(reponse);
	}

	public void getAsData(String directoryPath) {
		
	}

	public static EliminationReponse getAsInstance(String pathDirectory, Metier metier) {
		return null;
	}

	public String toString(){
		String res = super.toString();
		res += "Reponse correcte : " + this.reponseCorrecte + "\n";
		res += "Reponses : \n";
		for (String reponse : this.hmReponses.keySet()) {
			res += reponse + " : " + this.hmReponses.get(reponse)[0] + " , " + this.hmReponses.get(reponse)[1] + "\n";
		}
		return res;
	}
}