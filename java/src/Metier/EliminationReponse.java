package src.Metier;

import java.util.HashMap;
import java.util.Scanner;
// Au moins deux
public class EliminationReponse extends Question {
	private String                   reponseCorrecte;
	private HashMap<String,Double[]> hmReponses;

	public EliminationReponse(String intitule, Difficulte difficulte, Notion notion, int temps, int points) {
		super(intitule, difficulte, notion, temps, points);
		this.reponseCorrecte = "";
		this.hmReponses      = new HashMap<>();
	}

	public EliminationReponse(String intitule, Difficulte difficulte, Notion notion, int temps, int points, String explication) {
		super(intitule, difficulte, notion, temps, points, explication);
		this.reponseCorrecte = "";
		this.hmReponses      = new HashMap<>();
	}

	// Getters
	public String getReponseCorrecte() {return this.reponseCorrecte; }

	public HashMap<String,Double[]> getHmReponses() {return this.hmReponses; }

	public Double getLastIndex() {
		Double indexMax = 0.0;
		for (Double[] index : this.hmReponses.values()) {
			if (index[1] > indexMax) {
				indexMax = index[1];
			}
		}
		return indexMax + 1;
	}

	public boolean isReponseCorrecte(String reponse) {return this.reponseCorrecte.equals(reponse); }

	// Setters
	public void setReponseCorrecte(String reponseCorrecte) {this.reponseCorrecte = reponseCorrecte; }

	public void setHmReponses(HashMap<String,Double[]>  hmReponses) {this.hmReponses = hmReponses; }

	// Methods to add and remove incorrect responses
	public void ajouterReponse(String reponse, Double points, Double ordre){
		if (!this.hmReponses.containsKey(reponse) && ordre <= this.getLastIndex()+1) {
			this.hmReponses.put(reponse, new Double[]{points, ordre});
		}
	}

	public void removeReponse(String reponse){this.hmReponses.remove(reponse); }

	public String getAsData(){
		String res = this.getClass().getName() + ";" + super.getAsData() + ";" ;
		res += this.reponseCorrecte + ";";
		for(String reponse : this.hmReponses.keySet()) {
			res += reponse + "," + this.hmReponses.get(reponse)[0] + "," + this.hmReponses.get(reponse)[1] + "|";
		}
		return res;
	}

	public static EliminationReponse getAsInstance(String ligne, Metier metier){
		Scanner scanner = new Scanner(ligne);
		scanner.useDelimiter(";");

		String[] parts = new String[9];
		for (int i = 0; i < 9; i++) {
			parts[i] = scanner.next();
		}

		EliminationReponse er = new EliminationReponse(parts[1], Difficulte.getDifficulteByIndice(Integer.parseInt(parts[2])), metier.getNotionByNom(parts[3]), Integer.parseInt(parts[4]), Integer.parseInt(parts[5]), parts[6]);

		er.setReponseCorrecte(parts[7]);

		Scanner reponseScanner = new Scanner(parts[8]);
		reponseScanner.useDelimiter("\\|");
		while (scanner.hasNext()) {
			String reponse = scanner.next();
			Scanner eliminationScanner = new Scanner(reponse);
			eliminationScanner.useDelimiter(",");

			String[] reponseParts = new String[3];
			for (int i = 0; i < 3; i++) {
				reponseParts[i] = eliminationScanner.next();
			}

			er.ajouterReponse(reponseParts[0], Double.parseDouble(reponseParts[1]), Double.parseDouble(reponseParts[2]));
			eliminationScanner.close();
		}

		reponseScanner.close();
		scanner.close();
		return er;
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