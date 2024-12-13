package src.Metier;

import java.util.HashMap;
import java.util.Scanner;

public class QCM extends Question {
	private HashMap<String, Boolean> hmReponses;

	public QCM(String intitule, Difficulte difficulte, Notion notion, int temps, int points){
		super(intitule, difficulte, notion, temps, points);
		this.hmReponses = new HashMap<String, Boolean>();
	}

	public QCM(String intitule, Difficulte difficulte, Notion notion, int temps, int points,String explication){
		super(intitule, difficulte, notion, temps, points, explication);
		this.hmReponses = new HashMap<String, Boolean>();
	}

	// Get

	public HashMap<String, Boolean> getReponses() {
		return this.hmReponses;
	}

	public int getNbReponses(){
		return this.hmReponses.size();
	}

	// Set

	public void setHmReponses(HashMap<String, Boolean> hmReponses) {
		this.hmReponses = hmReponses;
	}

	// Méthode pour gérer les réponse ajouter/enlever
	public void ajouterReponse(String reponse, Boolean correct) {
		if (this.hmReponses.containsKey(reponse)) {
			this.hmReponses.replace(reponse, correct);
		}
		else {
			this.hmReponses.put(reponse, correct);
		}
	}

	public void enleverReponse(String reponse) {
		this.hmReponses.remove(reponse);
	}

	@Override
	public String getAsData() {
		String res = this.getClass().getName() + ";" + super.getAsData() + ";" ;
		for (HashMap.Entry<String, Boolean> entry : this.hmReponses.entrySet()) {
			res += entry.getKey() + "," + entry.getValue() + "|";
		}
		return res;
	}
	public static QCM getAsInstance(String ligne, Metier metier) {
		Scanner scanner = new Scanner(ligne);
		scanner.useDelimiter(";");

		String[] parts = new String[9];
		for (int i = 0; i < 9; i++) {
			parts[i] = scanner.next();
		}

		QCM qcm = new QCM(parts[1], Difficulte.getDifficulteByIndice(Integer.parseInt(parts[2])), metier.getNotionByNom(parts[3]), Integer.parseInt(parts[4]), Integer.parseInt(parts[5]), parts[6]);

		Scanner reponseScanner = new Scanner(parts[7]);
		reponseScanner.useDelimiter("\\|");
		while (reponseScanner.hasNext()) {
			String reponse = reponseScanner.next();
			Scanner qcmScanner = new Scanner(reponse);
			qcmScanner.useDelimiter(",");
			String[] reponseParts = new String[2];
			for (int i = 0; i < 2; i++) {
				reponseParts[i] = qcmScanner.next();
			}
			qcm.ajouterReponse(reponseParts[0], Boolean.parseBoolean(reponseParts[1]));
			qcmScanner.close();
		}

		return qcm;
	}

	public String toString() {
		String res = super.toString();
		res += "Réponses : \n";
		for (HashMap.Entry<String, Boolean> entry : this.hmReponses.entrySet()) {
			res += entry.getKey() + " -> " + entry.getValue() + "\n";
		}
		return res;
	}
}