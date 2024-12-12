package src.Metier;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.rtf.RTFEditorKit;

public class QCM extends Question {
	private HashMap<String, Boolean> hmReponses;

	public QCM(String intitule, Difficulte difficulte, Notion notion, int temps, int points) {
		super(intitule, difficulte, notion, temps, points);
		this.hmReponses = new HashMap<String, Boolean>();
	}

	public QCM(String intitule, Difficulte difficulte, Notion notion, int temps, int points,String explication) {
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

	public void getAsData(String directoryPath) {

	}
	public static QCM getAsInstance(String pathDirectory, Metier metier) {
		return null;
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